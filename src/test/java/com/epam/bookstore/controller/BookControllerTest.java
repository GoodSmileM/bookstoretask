package com.epam.bookstore.controller;

import com.epam.bookstore.dao.BookDao;
import com.epam.bookstore.dto.BookDTO;
import com.epam.bookstore.entity.Book;
import com.epam.bookstore.service.BookService;
import com.epam.bookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookServiceImpl bookService;
    private static final BookDTO NONEXISTENT_BOOK_DTO = new BookDTO(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 100, 0);
    private static final Book NONEXISTENT_BOOK_ENTITY = new Book(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 100, 0);

    @BeforeEach
    public void init() {
        bookController = new BookController(bookService);
        System.out.println("test start");
    }

    @Test
    void addNewBook() {
        Mockito.when(bookService.addNewBook(any()))
                .thenReturn(NONEXISTENT_BOOK_ENTITY);
        Assertions.assertEquals(NONEXISTENT_BOOK_ENTITY, bookController.addNewBook(NONEXISTENT_BOOK_DTO).getBody().getData());
    }

    @Test
    void addBook() {
    }

    @Test
    void getById() {
    }

    @Test
    void getAllBooks() {
    }

    @Test
    void getBookNumber() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void sellBook() {
    }

    @Test
    void sellBooks() {
    }

    @Test
    void getBookByKeywordAndCategory() {
    }

    @Test
    void getBookNumberByKeywordAndCategory() {
    }
}