package com.epam.bookstore.dto.common;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
public class ResultBody {
    private Integer code;
    private String message;
    private Boolean success;
    private Object data;

    public ResultBody(Object data) {
        this.data = data;
    }

    public ResultBody(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultBody success(Object data) {
        return new ResultBody(data).success(true).code(0);
    }

    public static ResultBody error(Integer code, String message) {
        return new ResultBody(code, message).success(false);
    }

    public ResultBody code(Integer code) {
        this.code = code;
        return this;
    }

    public ResultBody message(String message) {
        this.message = message;
        return this;
    }

    public ResultBody success(Boolean success) {
        this.success = success;
        return this;
    }
}


