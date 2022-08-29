package com.epam.bookstore.service.impl;

import com.epam.bookstore.dao.BookDao;
import com.epam.bookstore.entity.Book;
import com.epam.bookstore.enums.ResultEnum;
import com.epam.bookstore.exception.BookErrorException;
import com.epam.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public Book findById(Long id) {
        Optional<Book> result = bookDao.findById(id);
        if (result.isPresent())
            return result.get();
        else throw new BookErrorException(ResultEnum.ID_NOT_FOUND);
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

    @Override
    public Integer findSoldByCategoryAndKeyword(String keyword, String category) {
        Integer result;
        if (StringUtils.hasText(category))
            result = bookDao.findSoldByCategory(category);

        else result = bookDao.findSoldByCategoryAndKeyword(keyword, category);


        return result;

    }
}
