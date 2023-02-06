package com.example.miniautorizador.service;

import com.example.miniautorizador.MiniAutorizadorApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MiniAutorizadorApplication.class)
public class AutorizadorServiceTest {

    // criação de um cartão
    // verificação do saldo do cartão recém-criado
    // realização de diversas transações, verificando-se o saldo em seguida, até que o sistema retorne informação de saldo insuficiente
    // realização de uma transação com senha inválida
    // realização de uma transação com cartão inexistente
}
