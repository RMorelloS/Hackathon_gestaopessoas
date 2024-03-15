package com.fiap.postech.hackathon.gestaopessoas.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.postech.hackathon.gestaopessoas.model.Pessoa;

import java.time.LocalDate;
import java.util.Date;

public class PessoaGenerator {
    public static Pessoa geraPessoa(){



        var pessoa = new Pessoa();

        pessoa.setPaisOrigem("Brasil");
        pessoa.setCpf("29299530041");
        pessoa.setPassaporte("ABC123456");
        pessoa.setNomeCompleto("João da Silva");
        pessoa.setDataNascimento(LocalDate.parse("1990-01-01"));
        pessoa.setEnderecoPaisOrigem("Rua das Flores, 123");
        pessoa.setTelefone("1234-5678");
        pessoa.setEmail("joao@example.com");
        return pessoa;
    }

    public static String geraPessoaJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(geraPessoa());
    }
}
