package com.epam.bookstore.service;

import com.epam.bookstore.dao.BookDao;
import com.epam.bookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookDao bookDao;

    @Override
    public void addNewBook(Book book) {
        bookDao.save(book);

    }

    @Override
    public void addBook(Book book) {
        bookDao.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookDao.findById(id);

    }

    @Override
    public List<Book> getAll() {
        return bookDao.findAll();
    }
}
