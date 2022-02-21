package com.example.petshop.utils;

//import org.apache.commons.lang3.StringUtils;

public class Result {
    public static final String STATUS_SUCCESS = "0000";
    public static final String STATUS_ERROR = "0001";
    private String message;
    private Object data;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    Result(String message, Object data, String code) {
        this.message = message;
        this.data = data;
        this.code = code == null ? STATUS_SUCCESS : code;

    }

    public static Result success(Object data) {
        return Result.success(data, "success");
    }
    public static Result success(Object data, String message) {
        return Result.success(data, "success", "");
    }
    public static Result success(Object data, String message, String code) {
        return new Result(message, data, code);
    }

    public static Result error() {
        return Result.error("");
    }
    public static Result error(String message) {
        return Result.error(message, null);
    }
    public static Result error(String message, Object data) {
        return Result.error(message, data, "");
    }
    public static Result error(String message, Object data, String code) {
        return new Result(message, data, code == null ? STATUS_ERROR : code);
    }
}
