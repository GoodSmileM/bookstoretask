package com.epam.bookstore.service;

import com.epam.bookstore.dto.BookDTO;
import com.epam.bookstore.dto.SellDTO;
import com.epam.bookstore.entity.Book;


import java.util.List;


public interface BookService {
    Book addNewBook(BookDTO bookDTO);

    Book addBook(Long id, int amount);

    Book findById(Long id);

    Book updateBook(BookDTO bookDTO, Long id);

    Book sellOneBook(Long id);

    Book sellBooks(SellDTO sellDTO);

    List<Book> getAll();

    List<Book> findByCategoryAndKeyword(String keyword, String category);

    Integer countSoldByCategoryAndKeyword(String keyword, String category);
}
