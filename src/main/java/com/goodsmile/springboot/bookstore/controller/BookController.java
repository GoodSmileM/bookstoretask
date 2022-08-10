package com.goodsmile.springboot.bookstore.controller;

import com.goodsmile.springboot.bookstore.model.Book;
import com.goodsmile.springboot.bookstore.service.BookSServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    BookSServiceImpl bookSService;

    @PostMapping("/api/addnewbook")
    public void addNewBook(@RequestBody Book book){
        bookSService.addNewBook(book);

    }

    @PostMapping("/api/addook")
    public void addBook(@RequestBody Book book){
        bookSService.addNewBook(book);

    }



    @GetMapping("/api/book/{id}")
    public String getById(@PathVariable("id") Long id){
        Optional<Book> result = bookSService.findById(id);
        if(result.isPresent())
            return result.get().toString();
        else return "not found";
    }



}
