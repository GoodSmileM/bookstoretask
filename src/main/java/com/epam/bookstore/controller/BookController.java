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


@RestController
public class BookController {
    @Autowired
    BookServiceImpl bookService;


    @PostMapping("/api/addnewbook")
    public ResponseEntity<ResultBody> addNewBook(@RequestBody BookDTO bookDTO) {
        Book book = bookService.addNewBook(bookDTO);
        return ResponseEntity.ok(ResultBody.success(book).message("已成功添加书本"));

    }

    @PostMapping("/api/addbook")
    public ResponseEntity<ResultBody> addBook(Long id, int amount) {
        Book book = bookService.addBook(id, amount);
        return ResponseEntity.ok(ResultBody.success(book).message("书本已添加"));
    }


    @GetMapping("/api/book/{id}")
    public ResponseEntity<ResultBody> getById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(ResultBody.success(bookService.findById(id)));

    }

    @GetMapping("api/book/book-list")
    public ResponseEntity<ResultBody> getAllBooks() {
        return ResponseEntity.ok(ResultBody.success(bookService.getAll()));
    }

    @GetMapping("api/number-of-books/{id}")
    public ResponseEntity<ResultBody> getBookNumber(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(ResultBody.success(book.getTotalCount()));
    }

    @PostMapping("api/book/{id}")
    public ResponseEntity<ResultBody> updateBook(@RequestBody BookDTO bookDTO, @PathVariable("id") Long id) throws BookErrorException {
        if (bookDTO.getId() == null || bookDTO.getId() == 0 || bookDTO.getId() == id) {
            Book book = bookService.updateBook(bookDTO, id);
            return ResponseEntity.ok(ResultBody.success(book));
        } else if (!bookDTO.getId().equals(id)) throw new BookErrorException(ResultEnum.ID_NOT_MATCH);
        else throw new BookErrorException(ResultEnum.UNEXPECTED_ERROR);

    }


    @PostMapping("api/sell-book/{id}")
    public ResponseEntity<ResultBody> sellBook(@PathVariable("id") Long id) throws BookErrorException {
        return ResponseEntity.ok(ResultBody.success(bookService.sellOneBook(id)));
    }

    @PostMapping("api/sell-books")
    public ResponseEntity<ResultBody> sellBooks(@RequestBody SellDTO sellDTO) throws BookErrorException {
        return ResponseEntity.ok(ResultBody.success(bookService.sellBooks(sellDTO)).message("售卖成功"));
    }

    @GetMapping("api/books")
    public ResponseEntity<ResultBody> getBookByKeywordAndCategory(String keyword, String category) {
        return ResponseEntity.ok(ResultBody.success(bookService.findByCategoryAndKeyword(keyword, category)));
    }

    @GetMapping("/api/number-of-books")
    public ResponseEntity<ResultBody> getBookNumberByKeywordAndCategory(String keyword, String category) {
        return ResponseEntity.ok(ResultBody.success(bookService.countSoldByCategoryAndKeyword(keyword, category)));
    }

}


