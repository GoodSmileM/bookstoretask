package com.epam.bookstore.dto;


import lombok.Data;

@Data
public class BookDTO {

    private Long Id;


    private String Author;


    private String Title;


    private double Price;


    private int totalCount;


    private int Sold;
}
