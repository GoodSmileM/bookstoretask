package com.epam.bookstore.controller;


import com.epam.bookstore.dto.LoginDTO;
import com.epam.bookstore.dto.RegisterDTO;
import com.epam.bookstore.dto.common.ResultBody;
import com.epam.bookstore.enums.ResultEnum;
import com.epam.bookstore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    ResponseEntity<ResultBody> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(ResultBody.success(authService.login(loginDTO.getUsername(), loginDTO.getPassword())));
    }


    @GetMapping("/signout")
    ResponseEntity<ResultBody> logout(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (token == null) {
            return ResponseEntity.ok(ResultBody.error(ResultEnum.UNAUTHORIZED));
        }
        authService.logout(token);
        return ResponseEntity.ok(ResultBody.success(0).message("注销成功"));
    }

    @PostMapping("/register")
    ResponseEntity<ResultBody> register(@RequestBody RegisterDTO registerDTO) {
        if (!StringUtils.hasText(registerDTO.getUsername())||!StringUtils.hasText(registerDTO.getPassword())) {
            return ResponseEntity.ok(ResultBody.error(ResultEnum.ILLEGAL_PARAM));
        }
        return ResponseEntity.ok(ResultBody.success(authService.register(registerDTO)).message("注册成功"));
    }
}
