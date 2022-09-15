package com.epam.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDTO {

    private String username;

    private String password;

    private Long id;

    private Long roleId;
}
