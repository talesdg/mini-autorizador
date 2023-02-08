package com.tales.miniautorizador.exception.handler;

import com.tales.miniautorizador.exception.NotFoundException;
import com.tales.miniautorizador.exception.UnprocessableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnprocessableException.class)
    public ResponseEntity<Object> unprocessable(UnprocessableException e){
        return new ResponseEntity<>(e.getResponse(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFound(NotFoundException e){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
