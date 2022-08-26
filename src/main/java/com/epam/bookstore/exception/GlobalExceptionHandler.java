package com.epam.bookstore.exception;


import com.epam.bookstore.dto.common.ResultBody;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookErrorException.class)
    public ResultBody bookNumberExceptionHandler(BookErrorException e) {
        return ResultBody.error(e.getCode(), e.getMsg());
    }
}
