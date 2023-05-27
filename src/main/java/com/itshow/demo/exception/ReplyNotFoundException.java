package com.itshow.demo.exception;

public class ReplyNotFoundException extends Exception {

    private final String message = "reply not found";

    @Override
    public String getMessage() { return message; }
}
