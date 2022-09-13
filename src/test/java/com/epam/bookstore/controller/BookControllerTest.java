package com.epam.bookstore.controller;

import com.epam.bookstore.dto.BookDTO;
import com.epam.bookstore.dto.SellDTO;
import com.epam.bookstore.entity.Book;
import com.epam.bookstore.exception.BookErrorException;
import com.epam.bookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
class BookControllerTest {


    @InjectMocks
    private BookController bookController;

    @Mock
    private BookServiceImpl bookService;
    private static final BookDTO NONEXISTENT_BOOK_DTO = new BookDTO(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 100, 0);
    private static final Book NONEXISTENT_BOOK_ENTITY = new Book(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 100, 0);
    private static final Book EXISTENT_BOOK_ENTITY = new Book(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 100, 100);
    private static final BookDTO EXISTENT_BOOK_DTO = new BookDTO(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 100, 100);
    private static final Book UPDATE_BOOK_ENTITY = new Book(1L, "Marquez", "One Hundred Years of Solitude", "novel", 200, 100, 0);
    private static final BookDTO UPDATE_BOOK_DTO = new BookDTO(1L, "Marquez", "One Hundred Years of Solitude", "novel", 200, 100, 0);
    private static final Book AMOUNT_UPDATED_BOOK_ENTITY = new Book(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 200, 0);
    private static final Book SELL_ONE_BOOK_ENTITY = new Book(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 99, 101);
    private static final Book SELL_BOOKS_ENTITY = new Book(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 50, 150);
    private static final Book ANOTHER_BOOK_ENTITY = new Book(2L, "Jack London", "The Call of the Wild", "novel", 200, 100, 100);
    private static final SellDTO SELL_DTO = new SellDTO(1L, 50);
    private static final List<Book> BOOK_ENTITY_LIST = new ArrayList<>();
    private static final Long ID = 1L;
    private static final Long ID_WRONG = 2L;
    private static final Integer TOTALCOUNT = 100;
    private static final Integer AMOUNT = 200;
    private static final Integer SOLD_AMOUNT = 200;
    private static final String CATEGORY = "Novel";
    private static final String KEYWORD = "Marquez";

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
        Mockito.when(bookService.addBook(anyLong(), anyInt()))
                .thenReturn(AMOUNT_UPDATED_BOOK_ENTITY);
        Assertions.assertEquals(AMOUNT_UPDATED_BOOK_ENTITY, bookController.addBook(ID, AMOUNT).getBody().getData());
    }

    @Test
    void getByIdSuccess() {
        Mockito.when(bookService.findById(any()))
                .thenReturn(EXISTENT_BOOK_ENTITY);
        Assertions.assertNotNull(bookController.getById(ID).getBody().getData());
    }

    @Test
    void getAllBooks() {
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookService.getAll())
                .thenReturn(BOOK_ENTITY_LIST);
        Assertions.assertNotNull(bookController.getAllBooks().getBody().getData());
    }

    @Test
    void getBookNumber() {
        Mockito.when(bookService.findById(any()))
                .thenReturn(EXISTENT_BOOK_ENTITY);
        Assertions.assertEquals(TOTALCOUNT, ((Book) bookController.getById(ID).getBody().getData()).getTotalCount());
    }

    @Test
    void updateBookSuccess() {
        Mockito.when(bookService.updateBook(any(), anyLong()))
                .thenReturn(UPDATE_BOOK_ENTITY);
        Assertions.assertEquals(UPDATE_BOOK_ENTITY, bookController.updateBook(UPDATE_BOOK_DTO, ID).getBody().getData());
    }

    @Test
    void updateBookError() {
        Mockito.when(bookService.updateBook(any(), anyLong()))
                .thenReturn(UPDATE_BOOK_ENTITY);
        try {
            bookController.updateBook(UPDATE_BOOK_DTO, ID_WRONG);
            Assertions.fail("no expected exception thrown");
        } catch (BookErrorException e) {
            Assertions.assertEquals(-2, e.getCode());
        }
    }

    @Test
    void sellBook() {
        Mockito.when(bookService.sellOneBook(anyLong()))
                .thenReturn(SELL_ONE_BOOK_ENTITY);
        Assertions.assertEquals(SELL_ONE_BOOK_ENTITY, bookController.sellBook(ID).getBody().getData());
    }

    @Test
    void sellBooks() {
        Mockito.when(bookService.sellBooks(any()))
                .thenReturn(SELL_BOOKS_ENTITY);
        Assertions.assertEquals(SELL_BOOKS_ENTITY, bookController.sellBooks(SELL_DTO).getBody().getData());
    }

    @Test
    void getBookByKeywordAndCategory() {
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookService.findByCategoryAndKeyword(any(),any()))
                .thenReturn(BOOK_ENTITY_LIST);
        Assertions.assertEquals(BOOK_ENTITY_LIST, bookController.getBookByKeywordAndCategory(KEYWORD,CATEGORY).getBody().getData());
    }

    @Test
    void getBookNumberByKeywordAndCategory() {
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookService.countSoldByCategoryAndKeyword(any(),any()))
                .thenReturn(SOLD_AMOUNT);
        Assertions.assertEquals(SOLD_AMOUNT, bookController.getBookNumberByKeywordAndCategory(KEYWORD,CATEGORY).getBody().getData());
    }
}