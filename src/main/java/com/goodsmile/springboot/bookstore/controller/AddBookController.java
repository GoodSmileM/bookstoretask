package com.goodsmile.springboot.bookstore.controller;

import com.goodsmile.springboot.bookstore.model.Book;
import com.goodsmile.springboot.bookstore.service.BookSServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddBookController {
    @Autowired
    BookSServiceImpl bookSService;

    @PostMapping("/api/addnewbook")
    public void addNewBook(Book book){


    }
}
