package com.tales.miniautorizador.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartaoRequest {
    private Long numeroCartao;
    private String senha;
    public CartaoRequest(Long numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
    }
}
