package com.epam.bookstore.dto;


import lombok.Data;

@Data
public class BookDTO {

    private Long id;


    private String author;


    private String title;


    private double price;


    private int totalCount;


    private int sold;
}
