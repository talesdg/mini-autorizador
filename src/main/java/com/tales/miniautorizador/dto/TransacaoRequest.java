package com.tales.miniautorizador.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class TransacaoRequest {
    private Long numeroCartao;
    private String senha;
    private BigDecimal valor;

    public TransacaoRequest(Long numeroCartao, String senha, BigDecimal valor) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.valor = valor;
    }
}
