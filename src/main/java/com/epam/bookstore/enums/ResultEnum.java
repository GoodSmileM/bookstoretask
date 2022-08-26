package com.epam.bookstore.enums;

public enum ResultEnum {
    ID_NOT_FOUND(-1, "ID未找到"),
    ID_NOT_MATCH(-2,"ID不匹配"),
    BOOK_SOLD_OUT(-3,"书已售完"),
    UNEXPECTED_ERROR(-4,"未知错误");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
