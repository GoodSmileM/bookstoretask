package com.goodsmile.springboot.bookstore.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "BookInfo")
public class Book {
    @Id
    @Column(name = "bood_id")
    private int bookId;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_price")
    private double bookPrice;

    @Column(name = "book_totalcount")
    private int bookTotalCount;

    @Column(name = "book_sold")
    private int bookSold;
}
