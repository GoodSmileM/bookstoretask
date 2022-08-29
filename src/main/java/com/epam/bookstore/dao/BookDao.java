package com.epam.bookstore.dao;

import com.epam.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.io.Serializable;
import java.util.List;


public interface BookDao extends JpaRepository<Book, Long>, Serializable {

    List<Book> findByCategory(String category);

    @Query(value = "select * from book_info where category=?1 and (id like ?2 or author like ?2 or title like ?2)", nativeQuery = true)
    List<Book> findByCategoryAndKeyword(String category, String keyword);

    @Query(value = "select sum(sold) from book_info where category=?1 ", nativeQuery = true)
    Integer findSoldByCategory(String category);

    @Query(value = "select sum(sold) from book_info where category=?1 and (id like ?2 or author like ?2 or title like ?2)", nativeQuery = true)
    Integer findSoldByCategoryAndKeyword(String category, String keyword);


}
