package com.database.integration.mysql.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchCharacterException extends RuntimeException {
    public NoSuchCharacterException(String message) {
        super(message);
    }
}
