package com.tales.miniautorizador.controller;

import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.service.AutorizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutorizadorController {
    @Autowired
    private AutorizadorService autorizadorService;

    @PostMapping("/transacoes")
    public ResponseEntity<Object> performTransaction(@RequestBody TransacaoRequest body) {
        return autorizadorService.performTransaction(body);
    }

}
