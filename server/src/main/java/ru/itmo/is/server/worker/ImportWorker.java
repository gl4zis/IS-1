package ru.itmo.is.server.worker;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
public class ImportWorker {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private FileStorage fs;
    @Inject
    private EntityValidator<Person> personValidator;
    @Inject
    private EntityValidator<Location> locationValidator;
    @Inject
    private EntityValidator<Coordinates> coordValidator;

    public void executeSafe(User importer, byte[] data, FileImport fi) {
        try {
            execute(importer, data, fi);
            em.merge(fi);
        } catch (Exception e) {
            em.merge(fi);
            log.error("Import task failed caused by {}", e.getClass().getSimpleName());
        }
    }

    @Transactional
    private void execute(User importer, byte[] data, FileImport fi) throws Exception {
        try {
            var counters = importEntities(FileService.parseCSV(data), importer);
            var downloadKey = fs.saveFile(data);
            fi.success(downloadKey, counters);
        } catch (ValidationException e) {
            fi.validationFailed();
            throw e;
        } catch (Exception e) {
            fi.insertFailed();
            throw e;
        }
    }

    @Transactional
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
