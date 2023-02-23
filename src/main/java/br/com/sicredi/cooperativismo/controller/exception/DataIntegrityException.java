package br.com.sicredi.cooperativismo.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataIntegrityException extends RuntimeException{

    public DataIntegrityException(String ex) {
        super(ex);
    }

    private static final long serialVersionUID = 1L;
}