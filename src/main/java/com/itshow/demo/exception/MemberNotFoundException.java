package com.itshow.demo.exception;

import lombok.AllArgsConstructor;

public class MemberNotFoundException extends Exception {

    private final String message = "id or password is not exist";

    @Override
    public String getMessage() {
        return message;
    }
}
