package com.tales.miniautorizador.service;

import com.tales.miniautorizador.entity.Cartao;
import org.springframework.stereotype.Service;

@Service
public class AutorizadorService {
    public Cartao createNewCard(Integer numCartao, String senha){
        return new Cartao(numCartao,senha);
    }
}
