package com.epam.bookstore.service.impl;

import com.epam.bookstore.dao.BookDao;
import com.epam.bookstore.entity.Book;
import com.epam.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public void addNewBook(Book book) {
        bookDao.save(book);

    }

    @Override
    public Book addBook(Long id, int amount) {
        Book book = bookDao.findById(id).get();
        book.setTotalCount(book.getTotalCount() + amount);
        bookDao.save(book);
        return book;

    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookDao.findById(id);

    }

    @Override
    public List<Book> getAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> findByCategoryAndKeyword(String keyword, String category) {
        List<Book> books;
        if (keyword == null || keyword.isEmpty())
            books = bookDao.findByCategory(category);
        else books = bookDao.findByCategoryAndKeyword(keyword, category);

        return books;
    }
}
