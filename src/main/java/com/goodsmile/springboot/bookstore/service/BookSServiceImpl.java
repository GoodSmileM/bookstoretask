package com.goodsmile.springboot.bookstore.service;

import com.goodsmile.springboot.bookstore.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookSServiceImpl implements BookService{
    @Autowired
    private BookDao bookDao;
}
