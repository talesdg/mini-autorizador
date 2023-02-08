package com.tales.miniautorizador.controller.scenario;

import com.tales.miniautorizador.enums.AutorizacaoRetorno;
import lombok.Getter;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
public class AutorizadorScenario {
    private String name;
    @Getter
    private ResultMatcher status;
    @Getter
    private BigDecimal valor;
    @Getter
    private String senha;
    @Getter
    private Long numCartao;
    @Getter
    private AutorizacaoRetorno expectedResponse;

    public AutorizadorScenario(String name, ResultMatcher status, BigDecimal valor, String senha, Long numCartao, AutorizacaoRetorno expectedResponse) {
        this.name = name;
        this.status = status;
        this.valor = valor;
        this.senha = senha;
        this.numCartao = numCartao;
        this.expectedResponse = expectedResponse;
    }
}
