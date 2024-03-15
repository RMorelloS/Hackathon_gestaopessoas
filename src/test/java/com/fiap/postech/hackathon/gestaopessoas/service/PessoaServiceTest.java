package com.fiap.postech.hackathon.gestaopessoas.service;


import com.fiap.postech.hackathon.gestaopessoas.config.PessoaGenerator;
import com.fiap.postech.hackathon.gestaopessoas.controller.PessoaController;
import com.fiap.postech.hackathon.gestaopessoas.model.Pessoa;
import com.fiap.postech.hackathon.gestaopessoas.repository.PessoaRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers= PessoaService.class)

public class PessoaServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaRepository pessoaRepository;


    @Autowired
    private PessoaService pessoaService;

    private Pessoa mockPessoa;

    public PessoaServiceTest(){
        mockPessoa = PessoaGenerator.geraPessoa();
    }


    @Test
    public void devePermitirCadastrarPessoa() throws Exception {
        Mockito.when(pessoaRepository.save(Mockito.any())).thenReturn(mockPessoa);
        var resultado = pessoaService.cadastrarPessoa(mockPessoa);
        assertEquals(resultado.getCpf(),mockPessoa.getCpf());
    }

    @Test
    public void deveRetornarErroQuandoCadastrarPessoaCPFInvalido() throws Exception {
        var pessoaCPFInvalido = mockPessoa;
        pessoaCPFInvalido.setCpf("123");
        assertThrows(ConstraintViolationException.class, () -> {
            pessoaService.cadastrarPessoa(pessoaCPFInvalido);
        }
        );
    }



    @Test
    public void devePermitirObterPessoaPorCPF() throws Exception {
        Mockito.when(pessoaRepository.getReferenceById(Mockito.anyString())).thenReturn(mockPessoa);
        var resultado = pessoaService.getPessoaByCPF(mockPessoa.getCpf());
        assertEquals(resultado.getCpf(),mockPessoa.getCpf());
    }

    @Test
    public void devePermitirDeletarPessoa() throws Exception {
        doNothing().when(pessoaRepository).deleteById(Mockito.anyString());
        var resultado = pessoaService.deletarPessoa(mockPessoa.getCpf());
        assertEquals(resultado,mockPessoa.getCpf());
    }

    @Test
    public void devePermitirAtualizarPessoa() throws Exception {
        var mockPessoaAtualizado = PessoaGenerator.geraPessoa();
        mockPessoaAtualizado.setPaisOrigem("Chile");
        Mockito.when(pessoaRepository.save(Mockito.any())).thenReturn(mockPessoaAtualizado);
        Mockito.when(pessoaRepository.getReferenceById(Mockito.any())).thenReturn(mockPessoa);

        var resultado = pessoaService.atualizarPessoa(mockPessoa.getCpf(), mockPessoaAtualizado);
        assertEquals(resultado.getPaisOrigem(),mockPessoaAtualizado.getPaisOrigem());
    }


    @Test
    public void deveRetornarErroQuandoAtualizarPessoaCPFInvalido() throws Exception {
        var pessoaCPFInvalido = mockPessoa;
        pessoaCPFInvalido.setCpf("123");
        assertThrows(ConstraintViolationException.class, () -> {
                    pessoaService.atualizarPessoa(pessoaCPFInvalido.getCpf(), pessoaCPFInvalido);
                }
        );
    }


}
