package ru.itmo.is.server.dto.response;

import java.io.File;

public record FileResponse(String name, File file) {
}
