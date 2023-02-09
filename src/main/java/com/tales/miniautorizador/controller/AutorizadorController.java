package com.tales.miniautorizador.controller;

import com.tales.miniautorizador.dto.CartaoRequest;
import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.service.AutorizadorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tales.miniautorizador.utils.AutorizadorConstants.*;

@RestController
public class AutorizadorController {
    @Autowired
    private AutorizadorService autorizadorService;

    @ApiOperation(value = "Realizar criação de um novo cartão")
    @PostMapping(URL_CREATE_NEW_CARD)
    public ResponseEntity<Object> createNewCard(@RequestBody CartaoRequest body) {
        return autorizadorService.createNewCard(body.getNumeroCartao(), body.getSenha());
    }
    @ApiOperation(value = "Consultar saldo do criação")
    @GetMapping(URL_CARD_BALANCE)
    public ResponseEntity<Object> getCardBalance(@PathVariable Long numeroCartao) {
        return autorizadorService.getCardBalance(numeroCartao);
    }
    @ApiOperation(value = "Realizar transação com o cartão informado")
    @PostMapping(URL_TRANSACOES)
    public ResponseEntity<Object> performTransaction(@RequestBody TransacaoRequest body) {
        return autorizadorService.performTransaction(body);
    }
}
