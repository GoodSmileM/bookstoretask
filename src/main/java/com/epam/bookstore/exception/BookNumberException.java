package com.epam.bookstore.exception;



public class BookNumberException extends RuntimeException{

    public BookNumberException() {
    }


    public BookNumberException(String message) {
        super(message);
    }


}
