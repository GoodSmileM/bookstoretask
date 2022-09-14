package com.epam.bookstore.service;

import com.epam.bookstore.dto.RegisterDTO;
import com.epam.bookstore.dto.common.ResponseUserToken;
import com.epam.bookstore.entity.User;

public interface AuthService {
    /**
     * 注册用户
     */
    User register(RegisterDTO registerDTO);

    /**
     * 登陆
     * @return
     */
    ResponseUserToken login(String username, String password);

    /**
     * 登出
     */
    void logout(String token);
}
