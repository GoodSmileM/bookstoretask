package com.epam.bookstore.dao;

import com.epam.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


import java.io.Serializable;


public interface BookDao extends JpaRepository<Book,Long>,Serializable {

}
