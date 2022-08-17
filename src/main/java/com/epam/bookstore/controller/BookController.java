package com.epam.bookstore.controller;

import com.epam.bookstore.model.Book;
import com.epam.bookstore.service.BookServiceImpl;
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

    @GetMapping("api/book/booklist")
    public String getAllBooks(){
        return bookService.getAll().toString();
    }


}
