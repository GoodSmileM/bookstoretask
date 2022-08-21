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

    @PostMapping("/api/addbook")
    public void addBook(Long id,int amount){
        bookService.addBook(id,amount);

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
    public String updateBook(@RequestBody BookDTO bookDTO,@PathVariable("id") Long id){
        if(bookDTO.getId()==null){
            Book book=bookService.findById(id).get();
            book.setAuthor(bookDTO.getAuthor());
            book.setTitle(bookDTO.getTitle());
            book.setTotalCount(bookDTO.getTotalCount());
            book.setSold(bookDTO.getSold());
            bookService.addNewBook(book);
            return "book updated";
        }
        else if(bookDTO.getId()!=id) return "id wrong";
        else {
            Book book=bookService.findById(id).get();
            book.setAuthor(bookDTO.getAuthor());
            book.setTitle(bookDTO.getTitle());
            book.setTotalCount(bookDTO.getTotalCount());
            book.setSold(bookDTO.getSold());
            bookService.addNewBook(book);
            return "book updated";
        }


    }


    @PostMapping("api/sell-book/{id}")
    public String sellBook(@PathVariable("id")Long id){
        Optional<Book> result = bookService.findById(id);
        Book book=result.get();
        if(book.getSold()>0){
            book.setSold(book.getSold()-1);
            return "sold";
        }

        else return "sold out";

    }




}


