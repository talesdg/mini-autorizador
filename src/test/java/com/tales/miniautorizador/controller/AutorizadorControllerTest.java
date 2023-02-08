package com.tales.miniautorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tales.miniautorizador.controller.scenario.AutorizadorScenario;
import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.enums.AutorizacaoRetorno;
import com.tales.miniautorizador.service.AutorizadorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.tales.miniautorizador.utils.AutorizadorConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AutorizadorControllerTest {
    @Autowired
    private AutorizadorService autorizadorService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        autorizadorService.deleteCard(NUM_CARTAO);
        autorizadorService.createNewCard(NUM_CARTAO_NEW, SENHA);
    }

    @AfterEach
    public void setOut(){
        autorizadorService.deleteCard(NUM_CARTAO_NEW);
    }

    @ParameterizedTest(name = "{index}. {arguments}")
    @MethodSource
    public void whenPerformTransaction(AutorizadorScenario scenario) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(
                new TransacaoRequest(scenario.getNumCartao(), scenario.getSenha(),scenario.getValor())
        );
        mockMvc.perform(post(URL_TRANSACOES).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(scenario.getStatus())
                .andExpect(content().string("\""+scenario.getExpectedResponse()+"\""));
    }

    private static Stream<AutorizadorScenario> whenPerformTransaction(){
        List<AutorizadorScenario> lista = new ArrayList<>();
        lista.add(new AutorizadorScenario("OK",status().is2xxSuccessful(),new BigDecimal(VALOR_100), SENHA,NUM_CARTAO_NEW,AutorizacaoRetorno.OK));
        lista.add(new AutorizadorScenario("CARTAO_INEXISTENTE",status().is4xxClientError(),new BigDecimal(VALOR_100), SENHA,NUM_CARTAO,AutorizacaoRetorno.CARTAO_INEXISTENTE));
        lista.add(new AutorizadorScenario("SALDO_INSUFICIENTE",status().is4xxClientError(),new BigDecimal(VALOR_1000), SENHA,NUM_CARTAO_NEW,AutorizacaoRetorno.SALDO_INSUFICIENTE));
        lista.add(new AutorizadorScenario("SENHA_INVALIDA",status().is4xxClientError(),new BigDecimal(VALOR_100),SENHA_INCORRETA,NUM_CARTAO_NEW,AutorizacaoRetorno.SENHA_INVALIDA));
        return lista.stream();
    }

    @ParameterizedTest(name = "{index}. {arguments}")
    @MethodSource
    public void whenCheckBalance(AutorizadorScenario scenario) throws Exception {
        mockMvc.perform(get(URL_CARD_BALANCE_+scenario.getNumCartao()))
                .andExpect(scenario.getStatus())
                .andExpect(content().string(scenario.getExpectedResponse().toString()))
        ;
    }
    private static Stream<AutorizadorScenario> whenCheckBalance(){
        List<AutorizadorScenario> lista = new ArrayList<>();
        lista.add(new AutorizadorScenario("OK",status().is2xxSuccessful(),null, SENHA,NUM_CARTAO_NEW,new BigDecimal(VALOR_500)));
        lista.add(new AutorizadorScenario("NOT_FOUND",status().is4xxClientError(),null, SENHA,NUM_CARTAO,""));
        return lista.stream();
    }

    @ParameterizedTest(name = "{index}. {arguments}")
    @MethodSource
    public void whenCreateNewCard(AutorizadorScenario scenario) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(
                new TransacaoRequest(scenario.getNumCartao(), scenario.getSenha(),scenario.getValor())
        );
        if(scenario.getExpectedResponse().equals(true)){
            mockMvc.perform(post(URL_CREATE_NEW_CARD).content(jsonString).contentType(MediaType.APPLICATION_JSON));
        }
        mockMvc.perform(post(URL_CREATE_NEW_CARD).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(scenario.getStatus())
                .andExpect(jsonPath("numCartao").value(scenario.getNumCartao()))
                .andExpect(jsonPath("senha").value(scenario.getSenha()))
        ;
    }

    private static Stream<AutorizadorScenario> whenCreateNewCard(){
        List<AutorizadorScenario> lista = new ArrayList<>();
        lista.add(new AutorizadorScenario("OK",status().is2xxSuccessful(),null, SENHA,NUM_CARTAO,false));
        lista.add(new AutorizadorScenario("NOT_FOUND",status().is4xxClientError(),null, SENHA,NUM_CARTAO,true));
        return lista.stream();
    }

}
