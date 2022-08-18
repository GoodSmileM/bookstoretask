package com.epam.bookstore.controller;

import com.epam.bookstore.dto.BookDTO;
import com.epam.bookstore.model.Book;
import com.epam.bookstore.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    BookServiceImpl bookService;

    @PostMapping("/api/addnewbook")
    public void addNewBook(@RequestBody Book book){
        bookService.addNewBook(book);

    }

    @PostMapping("/api/addook")
    public void addBook(@RequestBody Book book){
        bookService.addNewBook(book);

    }



    @GetMapping("/api/book/{id}")
    public String getById(@PathVariable("id") Long id){
        Optional<Book> result = bookService.findById(id);
        if(result.isPresent())
            return result.get().toString();
        else return "not found";
    }

    @GetMapping("api/book/book-list")
    public String getAllBooks(){
        return bookService.getAll().toString();
    }

    @GetMapping("api/number-of-books/{id}")
    public String getBookNumber(@PathVariable("id")Long id){
        Optional<Book> result = bookService.findById(id);
        Book book=result.get();
        return String.valueOf(book.getTotalCount());
    }

    @PostMapping("api/book/{id}")
    public String updateBook(BookDTO bookDTO){
        return "";
    }


    @PostMapping("api/sell-book/{id}")
    public String sellBook(@PathVariable("id")Long id){
        Optional<Book> result = bookService.findById(id);
        Book book=result.get();
        return "";
    }




}


