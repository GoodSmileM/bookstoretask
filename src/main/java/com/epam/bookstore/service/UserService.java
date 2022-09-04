package com.epam.bookstore.service;

import com.epam.bookstore.dto.common.ResultBody;
import com.epam.bookstore.entity.User;

public interface UserService {
    ResultBody login(User user);
}
