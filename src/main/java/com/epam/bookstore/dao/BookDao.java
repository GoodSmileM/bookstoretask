package com.epam.bookstore.dao;

import com.epam.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.io.Serializable;
import java.util.List;


public interface BookDao extends JpaRepository<Book, Long>, Serializable {

    List<Book> findByCategory(String category);

    @Query(value = "select * from book_info where category=?2 and (id like ?1 or author like ?1 or title like ?1)", nativeQuery = true)
    List<Book> findByCategoryAndKeyword(String keyword, String category);
}
