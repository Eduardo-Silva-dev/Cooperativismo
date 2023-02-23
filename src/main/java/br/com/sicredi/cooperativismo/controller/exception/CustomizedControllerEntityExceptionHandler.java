package br.com.sicredi.cooperativismo.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedControllerEntityExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionController> handleAllExceptions(
            Exception ex, WebRequest request) {

        ExceptionController exceptionController = new ExceptionController(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionController, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundExeption.class)
    public final ResponseEntity<ExceptionController> handleNotFoundExceptions(
            Exception ex, WebRequest request) {

        ExceptionController exceptionController = new ExceptionController(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionController, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public final ResponseEntity<ExceptionController> handleBadRequestExceptions(
            Exception ex, WebRequest request) {

        ExceptionController exceptionController = new ExceptionController(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionController, HttpStatus.BAD_REQUEST);
    }

}