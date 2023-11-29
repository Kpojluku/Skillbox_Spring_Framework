package ru.goltsov.education.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String format) {
        super(format);
    }
}
