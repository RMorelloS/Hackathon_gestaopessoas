package com.fiap.postech.hackathon.gestaopessoas.repository;

import com.fiap.postech.hackathon.gestaopessoas.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PessoaRepository extends JpaRepository<Pessoa, String> {
}
