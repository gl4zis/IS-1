package ru.itmo.is.server.config;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@ApplicationScoped
@Log4j2
public class AppProps {
    private final static List<String> FILES = List.of("secret.properties");

    private final Properties properties;

    public AppProps() {
        properties = new Properties();
        FILES.forEach(filename -> {
            try (var stream = this.getClass().getClassLoader().getResourceAsStream(filename)) {
                properties.load(stream);
            } catch (IOException e) {
                log.warn("Cannot load properties from {}", filename, e);
            }
        });
    }

    public String getAccessKey() {
        return properties.getProperty("jwt.access.key");
    }
}
