package com.tales.miniautorizador.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransacaoRequest {
    private Long numCartao;
    private String senha;
    private BigDecimal valor;

    public TransacaoRequest(Long numCartao, String senha, BigDecimal valor) {
        this.numCartao = numCartao;
        this.senha = senha;
        this.valor = valor;
    }
}
