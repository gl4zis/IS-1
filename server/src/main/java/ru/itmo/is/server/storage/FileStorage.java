package ru.itmo.is.server.storage;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import ru.itmo.is.server.config.AppProps;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
@ApplicationScoped
public class FileStorage {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private AppProps props;
    private MinioClient minioClient;

    @PostConstruct
    private void init() {
        minioClient = MinioClient.builder()
                .endpoint(String.format("http://%s:%d", props.getMinioHost(), props.getMinioPort()))
                .credentials(props.getMinioUser(), props.getMinioPassword())
                .build();
    }

    public String saveFile(byte[] data) throws IOException {
        try {
            String key = RandomStringUtils.random(8, true, true);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(props.get("minio.bucket"))
                            .object(key)
                            .stream(new ByteArrayInputStream(data), data.length, -1)
                            .contentType("text/plain")
                            .build()
            );
            return key;
        } catch (Exception e) {
            log.error("Cannot save file caused by {}", e.getClass().getSimpleName());
            throw new IOException(e);
        }
    }

    public InputStream getFile(String objectName) throws IOException {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(props.get("minio.bucket"))
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Cannot get file caused by {}", e.getClass().getSimpleName());
            throw new IOException(e);
        }
    }
}
