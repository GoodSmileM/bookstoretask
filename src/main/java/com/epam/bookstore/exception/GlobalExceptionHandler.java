package com.epam.bookstore.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IdNotMatchException.class)
    public ModelAndView idException(Exception e){
        e.printStackTrace();
        ModelAndView mv=new ModelAndView();
        mv.addObject("message",e.getMessage());
        mv.setViewName("error");
        return mv;
    }
}
