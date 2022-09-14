package com.epam.bookstore.dto.common;

import com.epam.bookstore.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUserToken {

    private String token;

    private String username;
}


