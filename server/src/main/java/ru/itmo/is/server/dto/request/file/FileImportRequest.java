package ru.itmo.is.server.dto.request.file;

public record FileImportRequest(String name, byte[] data) {
}
