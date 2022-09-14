package com.epam.bookstore.exception;


import com.epam.bookstore.dto.common.ResultBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookErrorException.class)
    public ResponseEntity<ResultBody> bookNumberExceptionHandler(BookErrorException e) {
        return ResponseEntity.ok(ResultBody.error(e.getCode(), e.getMsg()));
    }

    //处理用户权限有关的异常
    @ExceptionHandler(AuthErrorException.class)
    public ResponseEntity<ResultBody> AuthExceptionHandler(AuthErrorException e) {
        return ResponseEntity.ok(ResultBody.error(e.getCode(), e.getMsg()));
    }
}
