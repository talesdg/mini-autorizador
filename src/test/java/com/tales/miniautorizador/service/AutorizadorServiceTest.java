package com.tales.miniautorizador.service;

import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.entity.Cartao;
import com.tales.miniautorizador.enums.AutorizacaoRetorno;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @Test()
    public void performSeveralTransactionsWaitingInsufficientBalance(){
        List<TransacaoRequest> transactions = new ArrayList<>();
        transactions.add(new TransacaoRequest(123234345,"1234",new BigDecimal("100.00")));
        transactions.add(new TransacaoRequest(123234345,"1234",new BigDecimal("200.00")));
        transactions.add(new TransacaoRequest(123234345,"1234",new BigDecimal("300.00")));
        AutorizacaoRetorno retorno = AutorizacaoRetorno.OK;
        for (TransacaoRequest transacao : transactions) {
            retorno = autorizadorService.performTransaction(transacao, this.cartao);
        }
        Assert.assertEquals(AutorizacaoRetorno.SALDO_INSUFICIENTE,retorno);
    }
    // realização de uma transação com senha inválida
    // realização de uma transação com cartão inexistente
}
