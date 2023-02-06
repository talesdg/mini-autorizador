package com.tales.miniautorizador.service;

import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.entity.Cartao;
import com.tales.miniautorizador.enums.AutorizacaoRetorno;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AutorizadorService {
    public Cartao createNewCard(Integer numCartao, String senha){
        return new Cartao(numCartao,senha);
    }

    public AutorizacaoRetorno performTransaction(TransacaoRequest transacao, Cartao cartao) {
        if(hasCredito(transacao, cartao)){
            return AutorizacaoRetorno.SALDO_INSUFICIENTE;
        }
        cartao.performDebit(transacao.getValor());
        return AutorizacaoRetorno.OK;
    }

    private static boolean hasCredito(TransacaoRequest transacao, Cartao cartao) {
        BigDecimal saldo = cartao.getSaldo().subtract(transacao.getValor());
        return saldo.compareTo(cartao.getSaldo()) == -1;
    }
}
