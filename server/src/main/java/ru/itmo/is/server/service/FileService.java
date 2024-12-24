package ru.itmo.is.server.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.BadRequestException;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import ru.itmo.is.server.config.AppProps;
import ru.itmo.is.server.dto.request.file.ImportCsvRow;
import ru.itmo.is.server.dto.request.filter.FilteredRequest;
import ru.itmo.is.server.dto.response.FileResponse;
import ru.itmo.is.server.entity.file.FileImport;
import ru.itmo.is.server.entity.security.User;
import ru.itmo.is.server.storage.FileStorage;
import ru.itmo.is.server.web.ActiveUserHolder;
import ru.itmo.is.server.worker.ImportWorker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class FileService {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private ActiveUserHolder activeUser;
    @Inject
    private AppProps props;
    @Inject
    private FileStorage fileStorage;
    @EJB
    private ImportWorker importer;
    private ExecutorService executor;

    @PostConstruct
    private void init() {
        this.executor = Executors.newFixedThreadPool(props.getImportThreadsCount());
    }

    public Optional<FileResponse> getFile(String key) {
        Path path = fileStorage.getFile(key);
        List<String> names = em.createNamedQuery("FileImport.getNameByKey", String.class)
                .setParameter("key", key)
                .getResultList();
        FileResponse resp = null;
        if (Files.exists(path) && names.size() == 1) {
            resp = new FileResponse(names.get(0), path.toFile());
        }
        return Optional.ofNullable(resp);
    }

    public List<FileImport> getFileImports(FilteredRequest filter) {
        if (!activeUser.isAdmin()) {
            filter.addCustomFilter("createdBy", activeUser.get().getLogin());
        }
        return em.createQuery(filter.toJPQL(), FileImport.class).getResultList();
    }

    public int getHistoryCount() {
        return em.createNamedQuery("FileImport.count", Long.class).getSingleResult().intValue();
    }

    @Transactional
    public void upload(String filename, MultipartFormDataInput input) {
        byte[] data;
        try {
            data = input.getFormDataPart("file", byte[].class, null);
        } catch (IOException e) {
            throw new BadRequestException("Invalid form data", e);
        }

        User user = activeUser.get();
        var fi = FileImport.of(filename, user);
        em.persist(fi);
        em.flush();
        executor.submit(() -> importer.executeSafe(user, data, fi));
    }

    public static List<ImportCsvRow> parseCSV(byte[] data) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(data))) {
            CsvToBean<ImportCsvRow> parser = new CsvToBeanBuilder<ImportCsvRow>(reader)
                    .withType(ImportCsvRow.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                    .build();
            return parser.parse();
        } catch (IllegalStateException e) {
            throw new ValidationException(e);
        }
    }
}
