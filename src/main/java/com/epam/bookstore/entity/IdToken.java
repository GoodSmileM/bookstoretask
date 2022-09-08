package com.epam.bookstore.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "id_token")
public class IdToken {
    @Id
    @Column(name = "id")
    private Long Id;

    @Column(name = "token")
    private String token;
}
