package com.itshow.demo.exception;

public class AlreadyExistIdException extends Exception {
    public String getMessage() {
        return "already exist login id";
    }
}
