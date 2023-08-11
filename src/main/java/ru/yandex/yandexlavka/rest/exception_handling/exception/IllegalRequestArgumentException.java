package ru.yandex.yandexlavka.rest.exception_handling.exception;

public class IllegalRequestArgumentException extends IllegalArgumentException {

    public IllegalRequestArgumentException() {
    }

    public IllegalRequestArgumentException(String s) {
        super(s);
    }

    public IllegalRequestArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalRequestArgumentException(Throwable cause) {
        super(cause);
    }
}
