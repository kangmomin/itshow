package com.itshow.demo.exception;

public class PostNotFoundException extends Exception {

    private final String message = "post not found";

    @Override
    public String getMessage() {
        return message;
    }
}

