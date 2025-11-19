package br.com.jvbarbosa.store.service.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
