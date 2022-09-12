package com.epam.bookstore.controller;

import com.epam.bookstore.dto.LoginDTO;
import com.epam.bookstore.dto.common.ResultBody;
import com.epam.bookstore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    ResponseEntity<ResultBody> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(ResultBody.success(authService.login(loginDTO.getUsername(), loginDTO.getPassword())));
    }

    @GetMapping("/logout")
    ResponseEntity<ResultBody> logout() {
        //TODO 注销
        return null;
    }

    @PostMapping("/register")
    ResponseEntity<ResultBody> register() {
        //TODO 注销
        return null;
    }
}
