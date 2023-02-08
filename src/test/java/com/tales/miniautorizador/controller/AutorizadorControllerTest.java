package com.tales.miniautorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tales.miniautorizador.controller.scenario.AutorizadorScenario;
import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.enums.AutorizacaoRetorno;
import com.tales.miniautorizador.service.AutorizadorService;
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

    @ParameterizedTest(name = "{index}. {arguments}")
    @MethodSource
    public void whenPerformTransaction(AutorizadorScenario scenario) throws Exception {
        autorizadorService.createNewCard(6549873025634501L, "1234");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(
                new TransacaoRequest(scenario.getNumCartao(), scenario.getSenha(),scenario.getValor())
        );
        mockMvc.perform(post("/transacoes").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(scenario.getStatus())
                .andExpect(content().string("\""+scenario.getExpectedResponse()+"\""));
        autorizadorService.deleteCard(6549873025634501L);
    }

    private static Stream<AutorizadorScenario> whenPerformTransaction(){
        List<AutorizadorScenario> lista = new ArrayList<>();
        lista.add(new AutorizadorScenario("OK",status().is2xxSuccessful(),new BigDecimal("10.00"),"1234",6549873025634501L,AutorizacaoRetorno.OK));
        lista.add(new AutorizadorScenario("CARTAO_INEXISTENTE",status().is4xxClientError(),new BigDecimal("10.00"),"1234",6549873025634502L,AutorizacaoRetorno.CARTAO_INEXISTENTE));
        lista.add(new AutorizadorScenario("SALDO_INSUFICIENTE",status().is4xxClientError(),new BigDecimal("1000.00"),"1234",6549873025634501L,AutorizacaoRetorno.SALDO_INSUFICIENTE));
        lista.add(new AutorizadorScenario("SENHA_INVALIDA",status().is4xxClientError(),new BigDecimal("10.00"),"12345",6549873025634501L,AutorizacaoRetorno.SENHA_INVALIDA));
        return lista.stream();
    }

    @ParameterizedTest(name = "{index}. {arguments}")
    @MethodSource
    public void whenCheckBalance(AutorizadorScenario scenario) throws Exception {
        autorizadorService.createNewCard(6549873025634501L, "1234");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(
                new TransacaoRequest(scenario.getNumCartao(), scenario.getSenha(),scenario.getValor())
        );
        mockMvc.perform(get("/cartoes/"+scenario.getNumCartao()).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(scenario.getStatus())
                .andExpect(content().string(scenario.getExpectedResponse().toString()))
        ;
        autorizadorService.deleteCard(6549873025634501L);
    }
    private static Stream<AutorizadorScenario> whenCheckBalance(){
        List<AutorizadorScenario> lista = new ArrayList<>();
        lista.add(new AutorizadorScenario("OK",status().is2xxSuccessful(),null,"1234",6549873025634501L,new BigDecimal("500.00")));
        lista.add(new AutorizadorScenario("NOT_FOUND",status().is4xxClientError(),null,"1234",6549873025634502L,""));
        return lista.stream();
    }

    @ParameterizedTest(name = "{index}. {arguments}")
    @MethodSource
    public void whenCreateNewCard(AutorizadorScenario scenario) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(
                new TransacaoRequest(scenario.getNumCartao(), scenario.getSenha(),scenario.getValor())
        );
        mockMvc.perform(post("/cartoes").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(scenario.getStatus())
                .andExpect(jsonPath("numCartao").value(scenario.getNumCartao()))
                .andExpect(jsonPath("senha").value(scenario.getSenha()))
        ;
    }

    private static Stream<AutorizadorScenario> whenCreateNewCard(){
        List<AutorizadorScenario> lista = new ArrayList<>();
        lista.add(new AutorizadorScenario("OK",status().is2xxSuccessful(),null,"1234",6549873025634503L,null));
        lista.add(new AutorizadorScenario("NOT_FOUND",status().is4xxClientError(),null,"1234",6549873025634503L,null));
        return lista.stream();
    }

}
