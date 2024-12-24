package ru.itmo.is.server.storage;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.RandomStringUtils;
import ru.itmo.is.server.config.AppProps;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class FileStorage {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private AppProps props;

    public String saveFile(byte[] data) throws IOException {
        String key = generateFileKey();
        Path path = Paths.get(props.getFileStoragePath(), key);

        try (OutputStream out = Files.newOutputStream(path)) {
            out.write(data);
        }
        return key;
    }

    public Path getFile(String key) {
        return Paths.get(props.getFileStoragePath(), key);
    }

    // Костыль, зато быстро написано
    private String generateFileKey() {
        String key;
        int attempts = 0;
        int maxAttempts = 100;
        do {
            key = RandomStringUtils.random(8, true, true);
            attempts++;
            if (attempts > maxAttempts) {
                throw new RuntimeException("Unable to generate a unique key after " + maxAttempts + " attempts");
            }
        } while (!isDownloadKeyUnique(key));
        return key;
    }

    private boolean isDownloadKeyUnique(String key) {
        return em.createNamedQuery("FileImport.isKeyUnique", boolean.class)
                .setParameter("key", key)
                .getSingleResult();
    }
}
