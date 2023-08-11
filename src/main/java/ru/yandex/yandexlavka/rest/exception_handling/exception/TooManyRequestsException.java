package ru.yandex.yandexlavka.rest.exception_handling.exception;

public class TooManyRequestsException extends RuntimeException {

    public TooManyRequestsException() {
    }

    public TooManyRequestsException(String message) {
        super(message);
    }

    public TooManyRequestsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyRequestsException(Throwable cause) {
        super(cause);
    }
}
