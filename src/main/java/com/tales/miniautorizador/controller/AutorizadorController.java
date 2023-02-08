package com.tales.miniautorizador.controller;

import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.service.AutorizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AutorizadorController {
    @Autowired
    private AutorizadorService autorizadorService;

    @GetMapping("/cartoes/{numeroCartao}")
    public ResponseEntity<Object> getCardBalance(@PathVariable Long numeroCartao) {
        return autorizadorService.getCardBalance(numeroCartao);
    }

    @PostMapping("/transacoes")
    public ResponseEntity<Object> performTransaction(@RequestBody TransacaoRequest body) {
        return autorizadorService.performTransaction(body);
    }

}
