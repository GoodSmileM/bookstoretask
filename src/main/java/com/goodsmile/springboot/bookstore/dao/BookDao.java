package com.goodsmile.springboot.bookstore.dao;

import com.goodsmile.springboot.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface BookDao extends JpaRepository<Book,Long>,Serializable {
}
