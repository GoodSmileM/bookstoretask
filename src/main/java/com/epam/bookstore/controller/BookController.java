package com.epam.bookstore.controller;

import com.epam.bookstore.dto.BookDTO;
import com.epam.bookstore.dto.SellDTO;
import com.epam.bookstore.dto.common.ResultBody;
import com.epam.bookstore.enums.ResultEnum;
import com.epam.bookstore.exception.BookErrorException;
import com.epam.bookstore.entity.Book;
import com.epam.bookstore.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    BookServiceImpl bookService;


    @PostMapping("/api/addnewbook")
    public ResponseEntity<ResultBody> addNewBook(@RequestBody BookDTO bookDTO) {
        Book book = new Book();
        book.setId(book.getId());
        book.setTitle(bookDTO.getTitle());
        book.setCategory(bookDTO.getCategory());
        book.setAuthor(bookDTO.getAuthor());
        book.setTotalCount(bookDTO.getTotalCount());
        book.setSold(bookDTO.getSold());
        bookService.addNewBook(book);
        return ResponseEntity.ok(ResultBody.success(book).message("已成功添加书本"));

    }

    @PostMapping("/api/addbook")
    public ResponseEntity<ResultBody> addBook(Long id, int amount) {
        Book book = bookService.addBook(id, amount);
        return ResponseEntity.ok(ResultBody.success(book).message("书本已添加"));
    }


    @GetMapping("/api/book/{id}")
    public ResponseEntity<ResultBody> getById(@PathVariable("id") Long id) {
        Optional<Book> result = bookService.findById(id);
        if (result.isPresent())
            return ResponseEntity.ok(ResultBody.success(result.get()));
        else throw new BookErrorException(ResultEnum.ID_NOT_FOUND);
    }

    @GetMapping("api/book/book-list")
    public ResponseEntity<ResultBody> getAllBooks() {
        return ResponseEntity.ok(ResultBody.success(bookService.getAll()));
    }

    @GetMapping("api/number-of-books/{id}")
    public ResponseEntity<ResultBody> getBookNumber(@PathVariable("id") Long id) {
        Optional<Book> result = bookService.findById(id);
        if (!result.isPresent())
            throw new BookErrorException(ResultEnum.ID_NOT_FOUND);
        Book book = result.get();
        return ResponseEntity.ok(ResultBody.success(book));
    }

    @PostMapping("api/book/{id}")
    public ResponseEntity<ResultBody> updateBook(@RequestBody BookDTO bookDTO, @PathVariable("id") Long id) throws BookErrorException {


        if (bookDTO.getId() == null || bookDTO.getId() == 0) {
            Optional<Book> result = bookService.findById(id);
            if (!result.isPresent())
                throw new BookErrorException(ResultEnum.ID_NOT_FOUND);
            Book book = result.get();
            book.setAuthor(bookDTO.getAuthor());
            book.setTitle(bookDTO.getTitle());
            book.setCategory(bookDTO.getCategory());
            book.setTotalCount(bookDTO.getTotalCount());
            book.setSold(bookDTO.getSold());
            bookService.addNewBook(book);
            return ResponseEntity.ok(ResultBody.success(book));
        } else if (!bookDTO.getId().equals(id)) throw new BookErrorException(ResultEnum.ID_NOT_MATCH);
        else throw new BookErrorException(ResultEnum.UNEXPECTED_ERROR);

    }


    @PostMapping("api/sell-book/{id}")
    public ResponseEntity<ResultBody> sellBook(@PathVariable("id") Long id) throws BookErrorException {
        Optional<Book> result = bookService.findById(id);
        if (!result.isPresent())
            throw new BookErrorException(ResultEnum.ID_NOT_FOUND);
        Book book = result.get();
        if (book.getSold() > 0) {
            book.setTotalCount(book.getTotalCount() - 1);
            book.setSold(book.getSold() + 1);
            return ResponseEntity.ok(ResultBody.success(book).message("售卖成功"));
        } else throw new BookErrorException(ResultEnum.BOOK_SOLD_OUT);

    }

    @PutMapping("api/sell-books")
    public ResponseEntity<ResultBody> sellBooks(SellDTO sellDTO) throws BookErrorException {
        Long id = sellDTO.getId();
        int amount = sellDTO.getNumber();
        Optional<Book> result = bookService.findById(id);
        if (!result.isPresent())
            throw new BookErrorException(ResultEnum.ID_NOT_MATCH);
        Book book = result.get();
        if (book.getTotalCount() < amount) throw new BookErrorException(ResultEnum.BOOK_SOLD_OUT);
        book.setTotalCount(book.getTotalCount() - amount);
        book.setSold(book.getSold() + amount);
        return ResponseEntity.ok(ResultBody.success(book).message("售卖成功"));


    }

    @GetMapping("api/books?keyword=keyword&category=category")
    public ResponseEntity<ResultBody> getBookByKeywordAndCategory(String keyword, String category) {

        return ResponseEntity.ok(ResultBody.success(bookService.findByCategoryAndKeyword(keyword, category)));
    }


}


