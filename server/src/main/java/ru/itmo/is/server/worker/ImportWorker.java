package ru.itmo.is.server.worker;

import com.opencsv.exceptions.CsvException;
import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import jakarta.validation.ValidationException;
import lombok.extern.log4j.Log4j2;
import ru.itmo.is.server.dto.request.file.ImportCsvRow;
import ru.itmo.is.server.entity.Coordinates;
import ru.itmo.is.server.entity.Location;
import ru.itmo.is.server.entity.Person;
import ru.itmo.is.server.entity.file.FileImport;
import ru.itmo.is.server.entity.file.InsertCounter;
import ru.itmo.is.server.entity.security.User;
import ru.itmo.is.server.service.FileService;
import ru.itmo.is.server.storage.FileStorage;
import ru.itmo.is.server.validation.entity.EntityValidator;

import java.util.List;

@Log4j2
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ImportWorker {
    @PersistenceContext
    private EntityManager em;
    @Resource
    private SessionContext context;
    @Inject
    private FileStorage fs;
    @Inject
    private EntityValidator<Person> personValidator;
    @Inject
    private EntityValidator<Location> locationValidator;
    @Inject
    private EntityValidator<Coordinates> coordValidator;

    public void execute(User importer, byte[] data, FileImport fi) {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            var counters = importEntities(FileService.parseCSV(data), importer);
            var downloadKey = fs.saveFile(data);
            fi.success(downloadKey, counters);
            ut.commit();
        } catch (ValidationException e) {
            rollback(ut);
            defaultLog(e);
            fi.validationFailed();
        } catch (Exception e) {
            rollback(ut);
            defaultLog(e);
            if (e.getCause() instanceof CsvException) {
                fi.validationFailed();
            } else {
                fi.insertFailed();
            }
        } finally {
            mergeFi(ut, fi);
        }
    }

    private void defaultLog(Exception e) {
        log.error("Import task failed caused by {}", e.getClass().getSimpleName());
    }

    private void rollback(UserTransaction ut) {
        try {
            ut.rollback();
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    private void mergeFi(UserTransaction ut, FileImport fi) {
        try {
            ut.begin();
            em.merge(fi);
            ut.commit();
        } catch (Exception e) {
            rollback(ut);
        }
    }

    private InsertCounter importEntities(List<ImportCsvRow> imports, User importer) {
        InsertCounter counter = new InsertCounter();
        imports.forEach(row -> {
            var personO = row.getPerson();
            if (personO.isPresent()) {
                var person = personO.get();
                person.setCreatedBy(importer);
                personValidator.validate(person);
                var l = person.getLocation();
                var c = person.getCoordinates();
                c.setCreatedBy(importer);
                if (l != null) {
                    l.setCreatedBy(importer);
                    locationValidator.validate(l);
                    em.persist(l);
                    counter.incLocation();
                }
                coordValidator.validate(c);
                em.persist(c);
                counter.incCoordinates();

                em.persist(person);
                counter.incPerson();
            } else {
                throw new ValidationException();
            }
        });
        return counter;
    }
}
