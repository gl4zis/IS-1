package ru.itmo.is.server.exception;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException() {
        super("Permission denied");
    }
}
