package com.tales.miniautorizador.entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Cartao {
    private Long id;
    private Integer numCartao;
    private String senha;
    private BigDecimal saldo;

    public Cartao(Integer numCartao, String senha){
        this.numCartao = numCartao;
        this.senha = senha;
        this.saldo = new BigDecimal("500.00");
    }
}
