package com.tales.miniautorizador.service;

import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.entity.Cartao;
import com.tales.miniautorizador.enums.AutorizacaoRetorno;
import com.tales.miniautorizador.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class AutorizadorService {

    @Autowired
    private CartaoRepository cartaoRepository;
    public Cartao createNewCard(Long numCartao, String senha){
        return cartaoRepository.save(new Cartao(numCartao,senha));
    }

    public AutorizacaoRetorno performTransaction(TransacaoRequest transacao) {
        Cartao cartao = cartaoRepository.findByNumCartao(transacao.getNumCartao());
        if(cartao == null){
            return AutorizacaoRetorno.CARTAO_INEXISTENTE;
        }
        if(!transacao.getSenha().equals(cartao.getSenha())){
            return AutorizacaoRetorno.SENHA_INVALIDA;
        }
        if(hasCredito(transacao, cartao)){
            return AutorizacaoRetorno.SALDO_INSUFICIENTE;
        }
        cartao.performDebit(transacao.getValor());
        cartaoRepository.save(cartao);
        return AutorizacaoRetorno.OK;
    }

    private static boolean hasCredito(TransacaoRequest transacao, Cartao cartao) {
        BigDecimal saldo = cartao.getSaldo().subtract(transacao.getValor());
        return saldo.compareTo(BigDecimal.valueOf(0L)) == -1;
    }
    @Transactional
    public void deleteCard(Long numCartao){
        cartaoRepository.deleteByNumCartao(numCartao);
    }
}
