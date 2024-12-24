package ru.itmo.is.server.dto.response;

import java.io.InputStream;

public record FileResponse(String name, InputStream data) {
}
