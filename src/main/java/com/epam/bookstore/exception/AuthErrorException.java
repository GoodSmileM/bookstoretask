package com.epam.bookstore.exception;

import com.epam.bookstore.enums.ResultEnum;

public class AuthErrorException extends RuntimeException{
    private Integer code;
    private String msg;

    public AuthErrorException(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }
}
