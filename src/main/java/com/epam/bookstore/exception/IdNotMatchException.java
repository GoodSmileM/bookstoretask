package com.epam.bookstore.exception;

public class IdNotMatchException extends RuntimeException {
    public IdNotMatchException() {
    }

    public IdNotMatchException(String message) {
        super(message);
    }
}
