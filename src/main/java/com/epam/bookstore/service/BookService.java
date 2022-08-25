package com.epam.bookstore.service;

import com.epam.bookstore.entity.Book;


import java.util.List;
import java.util.Optional;

public interface BookService {
    void addNewBook(Book book);

    void addBook(Long id, int amount);

    Optional<Book> findById(Long id);

    List<Book> getAll();

    List<Book> findByCategoryAndKeyword(String keyword, String category);
}
