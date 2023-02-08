package com.tales.miniautorizador.exception;

public class UnprocessableException extends RuntimeException{

    private Object response;

    public UnprocessableException(Object response){
        this.response = response;
    }
    public Object getResponse(){
        return this.response;
    }
}
