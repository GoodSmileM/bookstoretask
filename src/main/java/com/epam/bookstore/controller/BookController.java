package com.epam.bookstore.controller;

import com.epam.bookstore.dto.BookDTO;
import com.epam.bookstore.dto.SellDTO;
import com.epam.bookstore.exception.BookNumberException;
import com.epam.bookstore.exception.IdNotMatchException;
import com.epam.bookstore.entity.Book;
import com.epam.bookstore.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    BookServiceImpl bookService;

    private static final String ID_NOT_FOUND = "id not found";

    @PostMapping("/api/addnewbook")
    public void addNewBook(@RequestBody BookDTO bookDTO) {
        Book book = new Book();
        book.setId(book.getId());
        book.setTitle(bookDTO.getTitle());
        book.setCategory(bookDTO.getCategory());
        book.setAuthor(bookDTO.getAuthor());
        book.setTotalCount(bookDTO.getTotalCount());
        book.setSold(bookDTO.getSold());
        bookService.addNewBook(book);

    }

    @PostMapping("/api/addbook")
    public void addBook(Long id, int amount) {
        bookService.addBook(id, amount);

    }


    @GetMapping("/api/book/{id}")
    public String getById(@PathVariable("id") Long id) {
        Optional<Book> result = bookService.findById(id);
        if (result.isPresent())
            return result.get().toString();
        else return "not found";
    }

    @GetMapping("api/book/book-list")
    public String getAllBooks() {
        return bookService.getAll().toString();
    }

    @GetMapping("api/number-of-books/{id}")
    public String getBookNumber(@PathVariable("id") Long id) {
        Optional<Book> result = bookService.findById(id);
        if (!result.isPresent())
            return ID_NOT_FOUND;
        Book book = result.get();
        return String.valueOf(book.getTotalCount());
    }

    @PostMapping("api/book/{id}")
    public String updateBook(@RequestBody BookDTO bookDTO, @PathVariable("id") Long id) throws IdNotMatchException {


        if (bookDTO.getId() == null || bookDTO.getId() == 0) {
            Optional<Book> result = bookService.findById(id);
            if (!result.isPresent())
                return ID_NOT_FOUND;
            Book book = result.get();
            book.setAuthor(bookDTO.getAuthor());
            book.setTitle(bookDTO.getTitle());
            book.setCategory(bookDTO.getCategory());
            book.setTotalCount(bookDTO.getTotalCount());
            book.setSold(bookDTO.getSold());
            bookService.addNewBook(book);
            return "book updated";
        } else if (!bookDTO.getId().equals(id)) throw new IdNotMatchException("输入id不匹配");
        else return "error";

    }


    @PostMapping("api/sell-book/{id}")
    public String sellBook(@PathVariable("id") Long id) throws BookNumberException {
        Optional<Book> result = bookService.findById(id);
        if (!result.isPresent())
            return ID_NOT_FOUND;
        Book book = result.get();
        if (book.getSold() > 0) {
            book.setTotalCount(book.getTotalCount() - 1);
            book.setSold(book.getSold() + 1);
            return "sold";
        } else throw new BookNumberException("sold out");

    }

    @PutMapping("api/sell-books")
    public String sellBooks(SellDTO sellDTO) throws BookNumberException {
        Long id = sellDTO.getId();
        int amount = sellDTO.getNumber();
        Optional<Book> result = bookService.findById(id);
        if (!result.isPresent())
            return ID_NOT_FOUND;
        Book book = result.get();
        if (book.getTotalCount() < amount) throw new BookNumberException("sold out");
        book.setTotalCount(book.getTotalCount() - amount);
        book.setSold(book.getSold() + amount);
        return "sell success";


    }

    @GetMapping("api/books?keyword=keyword&category=category")
    public List<Book> getBookByKeywordAndCategory(String keyword, String category) {

        return bookService.findByCategoryAndKeyword(keyword, category);
    }


}


