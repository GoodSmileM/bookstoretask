package com.epam.bookstore.service.impl;

import com.epam.bookstore.dao.BookDao;
import com.epam.bookstore.dto.BookDTO;
import com.epam.bookstore.dto.SellDTO;
import com.epam.bookstore.entity.Book;
import com.epam.bookstore.exception.BookErrorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BookServiceImplTest {


    @InjectMocks

    private BookServiceImpl bookService;

    @Mock
    private BookDao bookDao;
    private static final BookDTO NONEXISTENT_BOOK_DTO = new BookDTO(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 100, 0);
    private static final Book NONEXISTENT_BOOK_ENTITY = new Book(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 100, 0);
    private static final Book EXISTENT_BOOK_ENTITY = new Book(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 100, 100);
    private static final Book UPDATE_BOOK_ENTITY = new Book(1L, "Marquez", "One Hundred Years of Solitude", "novel", 200, 100, 0);
    private static final BookDTO UPDATE_BOOK_DTO = new BookDTO(1L, "Marquez", "One Hundred Years of Solitude", "novel", 200, 100, 0);
    private static final Book WRONG_BOOK_ENTITY = new Book(2L, "Jack London", "The Call of the Wild", "novel", 200, 100, 100);
    private static final BookDTO WRONG_BOOK_DTO = new BookDTO(2L, "Jack London", "The Call of the Wild", "novel", 200, 100, 100);
    private static final Book ZERO_BOOK_ENTITY = new Book(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 0, 0);
    private static final BookDTO ZERO_BOOK_DTO = new BookDTO(1L, "Marquez", "One Hundred Years of Solitude", "novel", 100, 0, 0);
    private static final SellDTO SELL_DTO = new SellDTO(1L, 100);

    private static final String TITLE = "One Hundred Years of Solitude";
    private static final String AUTHOR = "Marquez";
    private static final String CATEGORY = "Novel";
    private static final String KEYWORD = "Marquez";
    private static final int TOTAL_COUNT = 101;
    private static final double UPDATE_PRICE = 200;
    private static final Long ID = 1L;
    private static final Long NEW_ID = 2L;
    private static final List<Book> BOOK_ENTITY_LIST = new ArrayList<>();
    private static final int SOLD = 100;
    private static final int TOTAL_SOLD = 200;

    @BeforeEach
    public void init() {
        bookService = new BookServiceImpl(bookDao);
        System.out.println("test start");
    }

    @Test
    void addNewBook() {
        Mockito.when(bookDao.save(any()))
                .thenReturn(NONEXISTENT_BOOK_ENTITY);
        Assertions.assertEquals(AUTHOR, bookService.addNewBook(NONEXISTENT_BOOK_DTO).getAuthor());
    }

    @Test
    void addBook() {
        Mockito.when((bookDao.findById(any())))
                .thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));
        Mockito.when(bookDao.save(any()))
                .thenReturn(EXISTENT_BOOK_ENTITY);
        Assertions.assertEquals(TOTAL_COUNT, bookService.addBook(1L, 1).getTotalCount());
    }

    @Test
    void findByIdSuccess() {
        Mockito.when(bookDao.findById(any()))
                .thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));
        Assertions.assertNotNull(bookService.findById(ID));
    }

    @Test
    void findByIdError() {
        Mockito.when(bookDao.findById(any()))
                .thenReturn(Optional.empty());
        try {
            bookService.findById(ID);
            Assertions.fail("no expected exception thrown");
        } catch (BookErrorException e) {
            Assertions.assertEquals(-1, e.getCode());
        }
    }

    @Test
    void updateBookSuccess() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(UPDATE_BOOK_ENTITY));
        Mockito.when(bookDao.save(any())).thenReturn(UPDATE_BOOK_ENTITY);
        Assertions.assertEquals(UPDATE_PRICE, bookService.updateBook(UPDATE_BOOK_DTO, ID).getPrice());
    }

    @Test
    void updateBookError() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(WRONG_BOOK_ENTITY));
        try {
            bookService.updateBook(WRONG_BOOK_DTO, ID);
            Assertions.fail("no expected exception thrown");
        } catch (BookErrorException e) {
            Assertions.assertEquals(-2, e.getCode());
        }


    }

    @Test
    void sellOneBookSuccess() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));
        Mockito.when(bookDao.save(any())).thenReturn(EXISTENT_BOOK_ENTITY);
        Assertions.assertNotNull(bookService.sellOneBook(ID));
    }

    @Test
    void sellOneBookError() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(ZERO_BOOK_ENTITY));
        Mockito.when(bookDao.save(any())).thenReturn(ZERO_BOOK_ENTITY);
        try {
            bookService.sellOneBook(ID);
            Assertions.fail("no expected exception thrown");
        } catch (BookErrorException e) {
            Assertions.assertEquals(-3, e.getCode());
        }
    }

    @Test
    void sellBooksError() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(ZERO_BOOK_ENTITY));
        Mockito.when(bookDao.save(any())).thenReturn(ZERO_BOOK_ENTITY);
        try {
            bookService.sellBooks(SELL_DTO);
            Assertions.fail("no expected exception thrown");
        } catch (BookErrorException e) {
            Assertions.assertEquals(-3, e.getCode());
        }
    }

    @Test
    void getAll() {
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookDao.findAll()).thenReturn(BOOK_ENTITY_LIST);
        Assertions.assertFalse(bookService.getAll().isEmpty());
    }

    @Test
    void findByCategoryOnly() {
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_ENTITY);
        BOOK_ENTITY_LIST.add(WRONG_BOOK_ENTITY);
        System.out.println(BOOK_ENTITY_LIST);
        Mockito.when(bookDao.findByCategory(CATEGORY)).thenReturn(BOOK_ENTITY_LIST);
        Assertions.assertFalse(bookService.findByCategoryAndKeyword("", CATEGORY).isEmpty());
    }

    @Test
    void findByCategoryAndKeyword() {
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookDao.findByCategoryAndKeyword(CATEGORY, KEYWORD)).thenReturn(BOOK_ENTITY_LIST);
        Assertions.assertFalse(bookService.findByCategoryAndKeyword(CATEGORY, KEYWORD).isEmpty());
    }

    @Test
    void countByCategoryOnly() {
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_ENTITY);
        BOOK_ENTITY_LIST.add(WRONG_BOOK_ENTITY);
        Mockito.when(bookDao.countSoldByCategory(CATEGORY)).thenReturn(TOTAL_SOLD);
        Assertions.assertEquals(TOTAL_SOLD, bookService.countSoldByCategoryAndKeyword("", CATEGORY));
    }

    @Test
    void countByCategoryAndKeyword() {
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookDao.countSoldByCategoryAndKeyword(KEYWORD, CATEGORY)).thenReturn(SOLD);
        Assertions.assertEquals(SOLD, bookService.countSoldByCategoryAndKeyword(KEYWORD, CATEGORY));
    }
}