package com.goodsmile.springboot.bookstore.service;

import com.goodsmile.springboot.bookstore.model.Book;

import java.util.Optional;

public interface BookService {
    void addNewBook(Book book);
    void addBook(Book book);
    Optional<Book> findById(Long id);
    Book[] getAll();
}
