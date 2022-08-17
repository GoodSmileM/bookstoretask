package com.epam.bookstore.model;

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
    @Column(name = "id")
    private int Id;

    @Column(name = "author")
    private String Author;

    @Column(name = "title")
    private String Title;

    @Column(name = "price")
    private double Price;

    @Column(name = "totalcount")
    private int TotalCount;

    @Column(name = "sold")
    private int Sold;
}
