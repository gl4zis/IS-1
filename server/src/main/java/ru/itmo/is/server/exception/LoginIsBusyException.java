package ru.itmo.is.server.exception;

public class LoginIsBusyException extends RuntimeException {
    public LoginIsBusyException(String login) {
        super("Login '" + login + "' is busy");
    }
}
