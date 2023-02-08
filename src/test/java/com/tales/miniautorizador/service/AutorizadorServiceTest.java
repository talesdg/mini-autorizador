package com.tales.miniautorizador.service;

import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.entity.Cartao;
import com.tales.miniautorizador.enums.AutorizacaoRetorno;
import com.tales.miniautorizador.exception.UnprocessableException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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
        assertEquals(new BigDecimal("500.00"),this.cartao.getSaldo());
    }
    @Test()
    public void performSeveralTransactionsWaitingInsufficientBalance(){
        List<TransacaoRequest> transactions = new ArrayList<>();
        transactions.add(new TransacaoRequest(6549873025634501L,"1234",new BigDecimal("100.00")));
        transactions.add(new TransacaoRequest(6549873025634501L,"1234",new BigDecimal("200.00")));
        transactions.add(new TransacaoRequest(6549873025634501L,"1234",new BigDecimal("300.00")));
        UnprocessableException exception = assertThrows(UnprocessableException.class,
                () -> {
                    for (TransacaoRequest transacao : transactions) {
                        autorizadorService.performTransaction(transacao);
                    }
                });
        assertEquals(AutorizacaoRetorno.SALDO_INSUFICIENTE,
                exception.getResponse());
    }
    @Test()
    public void performTransactionWithInvalidPassword(){
        TransacaoRequest transacao = new TransacaoRequest(6549873025634501L, "12345", new BigDecimal("200.00"));
        assertEquals(AutorizacaoRetorno.SENHA_INVALIDA,
                getUnprocessableException(transacao).getResponse());
    }
    @Test()
    public void performTransactionWithCardNonexistent(){
        TransacaoRequest transacao = new TransacaoRequest(12323434L, "1234", new BigDecimal("200.00"));
        assertEquals(AutorizacaoRetorno.CARTAO_INEXISTENTE,
                getUnprocessableException(transacao).getResponse());
    }

    private UnprocessableException getUnprocessableException(TransacaoRequest transacao) {
        UnprocessableException exception = assertThrows(UnprocessableException.class,
                () -> autorizadorService.performTransaction(transacao));
        return exception;
    }
}
