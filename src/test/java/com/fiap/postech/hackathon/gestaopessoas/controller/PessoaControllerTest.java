package com.fiap.postech.hackathon.gestaopessoas.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiap.postech.hackathon.gestaopessoas.config.PessoaGenerator;
import com.fiap.postech.hackathon.gestaopessoas.model.Pessoa;
import com.fiap.postech.hackathon.gestaopessoas.service.PessoaService;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.client.MultipartBodyBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers= PessoaController.class)
public class PessoaControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private PessoaService pessoaService;

    private Pessoa mockPessoa;

    public PessoaControllerTest(){
        mockPessoa = PessoaGenerator.geraPessoa();
    }

    @Test
    public void devePermitirCadastrarPessoa() throws Exception {
        Mockito.when(pessoaService.cadastrarPessoa(Mockito.any())).thenReturn(mockPessoa);

        mockMvc.perform(MockMvcRequestBuilders.post("/gestaoPessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PessoaGenerator.geraPessoaJson()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.cpf").value(mockPessoa.getCpf()));

    }

    @Test
    public void deveRetornarErroQuandoCPFVazio() throws Exception {
        Mockito.when(pessoaService.cadastrarPessoa(Mockito.any())).thenThrow(new ConstraintViolationException("CPF inválido", null, "CPF"));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/gestaoPessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PessoaGenerator.geraPessoaJson()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String responseContent = result.getResponse().getContentAsString();

        Assertions.assertTrue(responseContent.contains("CPF inválido"));

    }

    @Test
    public void devePermitirObterPessoaPorCPF() throws Exception {
        Mockito.when(pessoaService.getPessoaByCPF(Mockito.anyString())).thenReturn(mockPessoa);

        mockMvc.perform(MockMvcRequestBuilders.get("/gestaoPessoas/" + mockPessoa.getCpf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.cpf").value(mockPessoa.getCpf()));

    }


    @Test
    public void deveRetornarErroQuandoBuscarCPFInvalido() throws Exception {
        Mockito.when(pessoaService.getPessoaByCPF(Mockito.any())).thenThrow(new RuntimeException("Usuário não encontrado"));

        ResultActions resultActions =  mockMvc.perform(MockMvcRequestBuilders.get("/gestaoPessoas/" + mockPessoa.getCpf()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String responseContent = result.getResponse().getContentAsString();

        Assertions.assertTrue(responseContent.contains("Usuário não encontrado"));

    }



    @Test
    public void devePermitirDeletarPessoa() throws Exception {
        Mockito.when(pessoaService.deletarPessoa(Mockito.anyString())).thenReturn(mockPessoa.getCpf());

        mockMvc.perform(MockMvcRequestBuilders.delete("/gestaoPessoas/" + mockPessoa.getCpf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mockPessoa.getCpf()));
    }

    @Test
    public void deveRetornarErroQuandoDeletarCPFInvalido() throws Exception {
        Mockito.when(pessoaService.deletarPessoa(Mockito.any())).thenThrow(new RuntimeException("Usuário não encontrado"));

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.delete("/gestaoPessoas/" + mockPessoa.getCpf()))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String responseContent = result.getResponse().getContentAsString();

        Assertions.assertTrue(responseContent.contains("Usuário não encontrado"));

    }


    @Test
    public void devePermitirAtualizarPessoa() throws Exception {
        Mockito.when(pessoaService.atualizarPessoa(Mockito.anyString(), Mockito.any())).thenReturn(mockPessoa);

        mockMvc.perform(MockMvcRequestBuilders.put("/gestaoPessoas/" + mockPessoa.getCpf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(PessoaGenerator.geraPessoaJson()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.cpf").value(mockPessoa.getCpf()));
    }


    @Test
    public void deveRetornarErroQuandoAtualizarUsuarioCPFNulo() throws Exception {
        Mockito.when(pessoaService.atualizarPessoa(Mockito.anyString(), Mockito.any())).thenThrow(new ConstraintViolationException("CPF inválido", null, "CPF"));

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.put("/gestaoPessoas/" + mockPessoa.getCpf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(PessoaGenerator.geraPessoaJson()))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String responseContent = result.getResponse().getContentAsString();

        Assertions.assertTrue(responseContent.contains("CPF inválido"));

    }



}
