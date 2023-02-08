package com.tales.miniautorizador.service;

import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.entity.Cartao;
import com.tales.miniautorizador.enums.AutorizacaoRetorno;
import org.junit.Assert;
import org.junit.jupiter.api.*;
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
        this.cartao = autorizadorService.createNewCard(6549873025634501L, "1234");
    }

    @AfterEach
    public void setOut(){
        autorizadorService.deleteCard(6549873025634501L);
    }

    @Test()
    public void verifyNewCardBalance(){
        Assert.assertEquals(new BigDecimal("500.00"),this.cartao.getSaldo());
    }
    @Test()
    public void performSeveralTransactionsWaitingInsufficientBalance(){
        List<TransacaoRequest> transactions = new ArrayList<>();
        transactions.add(new TransacaoRequest(6549873025634501L,"1234",new BigDecimal("100.00")));
        transactions.add(new TransacaoRequest(6549873025634501L,"1234",new BigDecimal("200.00")));
        transactions.add(new TransacaoRequest(6549873025634501L,"1234",new BigDecimal("300.00")));
        AutorizacaoRetorno retorno = AutorizacaoRetorno.OK;
        for (TransacaoRequest transacao : transactions) {
            retorno = autorizadorService.performTransaction(transacao);
        }
        Assert.assertEquals(AutorizacaoRetorno.SALDO_INSUFICIENTE,retorno);
    }
    @Test()
    public void performTransactionWithInvalidPassword(){
        TransacaoRequest transacao = new TransacaoRequest(6549873025634501L, "12345", new BigDecimal("200.00"));
        AutorizacaoRetorno retorno = autorizadorService.performTransaction(transacao);
        Assert.assertEquals(AutorizacaoRetorno.SENHA_INVALIDA,retorno);
    }
    @Test()
    public void performTransactionWithCardNonexistent(){
        TransacaoRequest transacao = new TransacaoRequest(12323434L, "1234", new BigDecimal("200.00"));
        AutorizacaoRetorno retorno = autorizadorService.performTransaction(transacao);
        Assert.assertEquals(AutorizacaoRetorno.CARTAO_INEXISTENTE,retorno);
    }
}
