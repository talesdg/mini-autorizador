package com.tales.miniautorizador.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
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
