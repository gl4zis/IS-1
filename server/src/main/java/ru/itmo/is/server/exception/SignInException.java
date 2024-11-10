package ru.itmo.is.server.exception;

public class SignInException extends RuntimeException {
    public SignInException() {
        super("Invalid login or password");
    }
}
