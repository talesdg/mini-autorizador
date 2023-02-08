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

import static com.tales.miniautorizador.utils.AutorizadorConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest()
public class AutorizadorServiceTest {

    private Cartao cartao;
    @Autowired
    private AutorizadorService autorizadorService;

    @BeforeEach
    public void setUp(){
        this.cartao = (Cartao) autorizadorService.createNewCard(NUM_CARTAO, SENHA).getBody();
    }

    @AfterEach
    public void setOut(){
        autorizadorService.deleteCard(NUM_CARTAO);
    }

    @Test()
    public void verifyNewCardBalance(){
        assertEquals(new BigDecimal(VALOR_500),this.cartao.getSaldo());
    }
    @Test()
    public void performSeveralTransactionsWaitingInsufficientBalance(){
        List<TransacaoRequest> transactions = new ArrayList<>();
        transactions.add(new TransacaoRequest(NUM_CARTAO, SENHA,new BigDecimal(VALOR_100)));
        transactions.add(new TransacaoRequest(NUM_CARTAO, SENHA,new BigDecimal(VALOR_200)));
        transactions.add(new TransacaoRequest(NUM_CARTAO, SENHA,new BigDecimal(VALOR_300)));
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
        TransacaoRequest transacao = new TransacaoRequest(NUM_CARTAO, SENHA_INCORRETA, new BigDecimal(VALOR_200));
        assertEquals(AutorizacaoRetorno.SENHA_INVALIDA,
                getUnprocessableException(transacao).getResponse());
    }
    @Test()
    public void performTransactionWithCardNonexistent(){
        TransacaoRequest transacao = new TransacaoRequest(NUM_CARTAO_INEXISTENTE, SENHA, new BigDecimal(VALOR_200));
        assertEquals(AutorizacaoRetorno.CARTAO_INEXISTENTE,
                getUnprocessableException(transacao).getResponse());
    }

    private UnprocessableException getUnprocessableException(TransacaoRequest transacao) {
        UnprocessableException exception = assertThrows(UnprocessableException.class,
                () -> autorizadorService.performTransaction(transacao));
        return exception;
    }
}
