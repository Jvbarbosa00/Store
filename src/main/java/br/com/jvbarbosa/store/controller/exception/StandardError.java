package br.com.jvbarbosa.store.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class StandardError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
