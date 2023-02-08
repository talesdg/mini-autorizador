package com.tales.miniautorizador.controller;

import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.service.AutorizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tales.miniautorizador.utils.AutorizadorConstants.*;

@RestController
public class AutorizadorController {
    @Autowired
    private AutorizadorService autorizadorService;

    @PostMapping(URL_CREATE_NEW_CARD)
    public ResponseEntity<Object> createNewCard(@RequestBody TransacaoRequest body) {
        return autorizadorService.createNewCard(body.getNumCartao(), body.getSenha());
    }
    @GetMapping(URL_CARD_BALANCE)
    public ResponseEntity<Object> getCardBalance(@PathVariable Long numeroCartao) {
        return autorizadorService.getCardBalance(numeroCartao);
    }
    @PostMapping(URL_TRANSACOES)
    public ResponseEntity<Object> performTransaction(@RequestBody TransacaoRequest body) {
        return autorizadorService.performTransaction(body);
    }
}
