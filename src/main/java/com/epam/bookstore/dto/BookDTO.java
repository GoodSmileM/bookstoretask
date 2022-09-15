package com.epam.bookstore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDTO {

    private Long id;


    private String author;


    private String title;


    private String category;


    private double price;


    private int totalCount;


    private int sold;
}
