package br.com.sicredi.cooperativismo.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExeption extends RuntimeException{

    public NotFoundExeption(String ex) {
        super(ex);
    }

    private static final long serialVersionUID = 1L;
}