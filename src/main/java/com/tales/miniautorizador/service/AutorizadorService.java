package com.tales.miniautorizador.service;

import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.entity.Cartao;
import com.tales.miniautorizador.enums.AutorizacaoRetorno;
import com.tales.miniautorizador.exception.UnprocessableException;
import com.tales.miniautorizador.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Object> performTransaction(TransacaoRequest transacao) throws UnprocessableException{
        Cartao cartao = cartaoRepository.findByNumCartao(transacao.getNumCartao());
        validaCartaoExistente(cartao);
        validaSenhaInvalida(transacao, cartao);
        validaSaldo(transacao, cartao);
        cartao.performDebit(transacao.getValor());
        cartaoRepository.save(cartao);
        return new ResponseEntity<>(AutorizacaoRetorno.OK, HttpStatus.CREATED);
    }

    private static void validaSaldo(TransacaoRequest transacao, Cartao cartao) {
        if(hasCredito(transacao, cartao)){
            throw new UnprocessableException(AutorizacaoRetorno.SALDO_INSUFICIENTE);
        }
    }

    private static void validaSenhaInvalida(TransacaoRequest transacao, Cartao cartao) {
        if(!transacao.getSenha().equals(cartao.getSenha())){
            throw new UnprocessableException(AutorizacaoRetorno.SENHA_INVALIDA);
        }
    }

    private static void validaCartaoExistente(Cartao cartao) {
        if(cartao == null){
            throw new UnprocessableException(AutorizacaoRetorno.CARTAO_INEXISTENTE);
        }
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
