package com.slava.exceptions;

public class MatchesNotFoundException extends RuntimeException{
    public MatchesNotFoundException(String message) {
        super(message);
    }
}
