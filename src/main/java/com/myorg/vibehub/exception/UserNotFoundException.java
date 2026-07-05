package com.myorg.vibehub.exception;

public class UserNotFoundException extends RuntimeException {

    //default constructor
    public UserNotFoundException() {
        super("User Not Found!");
    }

    //parameterised constructor
    public UserNotFoundException(String m) {
        super(m);
    }
}
