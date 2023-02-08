package com.tales.miniautorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tales.miniautorizador.dto.TransacaoRequest;
import com.tales.miniautorizador.enums.AutorizacaoRetorno;
import com.tales.miniautorizador.service.AutorizadorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AutorizadorControllerTest {
    @Autowired
    private AutorizadorService autorizadorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPerformTransaction() throws Exception {
        autorizadorService.createNewCard(6549873025634501L, "1234");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(
                new TransacaoRequest(6549873025634501L,"1234",new BigDecimal("10.00"))
        );
        mockMvc.perform(post("/transacoes").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("\""+AutorizacaoRetorno.OK+"\""));
        autorizadorService.deleteCard(6549873025634501L);
    }

}
