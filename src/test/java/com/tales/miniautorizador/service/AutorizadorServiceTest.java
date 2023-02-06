package com.tales.miniautorizador.service;

import com.tales.miniautorizador.entity.Cartao;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest()
public class AutorizadorServiceTest {

    private Cartao cartao;
    @Autowired
    private AutorizadorService autorizadorService;

    @BeforeEach
    public void setUp(){
        this.cartao = autorizadorService.createNewCard(123234345, "1234");
    }

    @Test()
    public void verifyNewCardBalance(){
        Assert.assertEquals(new BigDecimal("500.00"),this.cartao.getSaldo());
    }
    // realização de diversas transações, verificando-se o saldo em seguida, até que o sistema retorne informação de saldo insuficiente
    // realização de uma transação com senha inválida
    // realização de uma transação com cartão inexistente
}
