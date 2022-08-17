package com.epam.bookstore.service;

import com.epam.bookstore.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void addNewBook(Book book);
    void addBook(Book book);
    Optional<Book> findById(Long id);
    List<Book> getAll();
}
