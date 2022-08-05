package com.goodsmile.springboot.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddBookController {

    @PostMapping("/api/addnewbook")
    public void addNewBook(){

    }
}
