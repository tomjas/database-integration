package com.database.integration.mysql.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CharacterAlreadyExistingException extends RuntimeException {
    public CharacterAlreadyExistingException(String message) {
        super(message);
    }
}
