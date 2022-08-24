package com.epam.bookstore.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Data
@Entity
@Table(name = "book_info")
public class Book {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private double price;

    @Column(name = "total_count")
    private int totalCount;

    @Column(name = "sold")
    private int sold;


}
