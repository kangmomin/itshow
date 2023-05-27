package com.itshow.demo.exception;

public class FavoriteNotFoundException extends Exception {
    private final String message = "favorite not found";

    @Override
    public String getMessage() {
        return message;
    }
}
