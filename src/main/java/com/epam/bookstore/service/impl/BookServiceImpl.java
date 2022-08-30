package com.epam.bookstore.service.impl;

import com.epam.bookstore.dao.BookDao;
import com.epam.bookstore.dto.BookDTO;
import com.epam.bookstore.dto.SellDTO;
import com.epam.bookstore.dto.common.ResultBody;
import com.epam.bookstore.entity.Book;
import com.epam.bookstore.enums.ResultEnum;
import com.epam.bookstore.exception.BookErrorException;
import com.epam.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public Book addNewBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setCategory(bookDTO.getCategory());
        book.setPrice(bookDTO.getPrice());
        book.setAuthor(bookDTO.getAuthor());
        book.setTotalCount(bookDTO.getTotalCount());
        book.setSold(bookDTO.getSold());
        bookDao.save(book);
        return book;
    }

    @Override
    public Book addBook(Long id, int amount) {
        Optional<Book> result = bookDao.findById(id);
        if (result.isPresent()) {
            Book book;
            book = result.get();
            book.setTotalCount(book.getTotalCount() + amount);
            bookDao.save(book);
            return book;
        } else throw new BookErrorException(ResultEnum.ID_NOT_FOUND);
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> result = bookDao.findById(id);
        if (result.isPresent())
            return result.get();
        else throw new BookErrorException(ResultEnum.ID_NOT_FOUND);
    }

    @Override
    public Book updateBook(BookDTO bookDTO, Long id) {
        Book book;
        Optional<Book> result = bookDao.findById(id);
        if (result.isPresent())
            book = result.get();
        else throw new BookErrorException(ResultEnum.ID_NOT_FOUND);
        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getTitle());
        book.setPrice(bookDTO.getPrice());
        book.setCategory(bookDTO.getCategory());
        book.setTotalCount(bookDTO.getTotalCount());
        book.setSold(bookDTO.getSold());
        bookDao.save(book);
        return book;
    }

    @Override
    public Book sellOneBook(Long id) {
        Book book;
        Optional<Book> result = bookDao.findById(id);
        if (result.isPresent())
            book = result.get();
        else throw new BookErrorException(ResultEnum.ID_NOT_FOUND);
        if (book.getTotalCount() > 0) {
            book.setTotalCount(book.getTotalCount() - 1);
            book.setSold(book.getSold() + 1);
            bookDao.save(book);
            return book;
        } else throw new BookErrorException(ResultEnum.BOOK_SOLD_OUT);
    }

    @Override
    public Book sellBooks(SellDTO sellDTO) {
        Long id = sellDTO.getId();
        int amount = sellDTO.getNumber();
        Book book;
        Optional<Book> result = bookDao.findById(id);
        if (result.isPresent())
            book = result.get();
        else throw new BookErrorException(ResultEnum.ID_NOT_FOUND);
        if (book.getTotalCount() < amount) throw new BookErrorException(ResultEnum.BOOK_SOLD_OUT);
        else {
            book.setTotalCount(book.getTotalCount() - amount);
            book.setSold(book.getSold() + amount);
            bookDao.save(book);
            return book;
        }
    }

    @Override
    public List<Book> getAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> findByCategoryAndKeyword(String keyword, String category) {
        List<Book> books;
        if (!StringUtils.hasText(keyword))
            books = bookDao.findByCategory(category);
        else books = bookDao.findByCategoryAndKeyword(keyword, category);
        return books;
    }

    @Override
    public Integer countSoldByCategoryAndKeyword(String keyword, String category) {
        Integer result;
        if (!StringUtils.hasText(keyword))
            result = bookDao.countSoldByCategory(category);
        else result = bookDao.countSoldByCategoryAndKeyword(keyword, category);
        return result;
    }
}
