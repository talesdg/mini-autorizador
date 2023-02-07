package com.tales.miniautorizador.exception;

import com.tales.miniautorizador.enums.AutorizacaoRetorno;

public class UnprocessableException extends RuntimeException{

    private AutorizacaoRetorno response;

    public UnprocessableException(AutorizacaoRetorno response){
        this.response = response;
    }
    public AutorizacaoRetorno getResponse(){
        return this.response;
    }
}
