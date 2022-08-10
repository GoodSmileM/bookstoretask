package com.goodsmile.springboot.bookstore.service;

import com.goodsmile.springboot.bookstore.dao.BookDao;
import com.goodsmile.springboot.bookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

@Service
public class BookSServiceImpl implements BookService{
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
