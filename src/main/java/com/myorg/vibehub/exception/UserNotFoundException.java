package com.myorg.vibehub.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User Not Found!");
    }
    public UserNotFoundException(String m) {
        super(m);
    }
}
