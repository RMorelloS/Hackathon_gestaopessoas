package com.fiap.postech.hackathon.gestaopessoas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pessoa {
    @NotBlank(message="País de origem não pode estar vazio")
    private String paisOrigem;
    @NotBlank(message="CPF não pode estar vazio")
    @CPF
    @Id
    private String cpf;
    @NotBlank(message="Passaporte não pode estar vazio")
    private String passaporte;
    @NotBlank(message="Nome completo não pode estar vazio")
    private String nomeCompleto;
    private LocalDate dataNascimento;
    @NotBlank(message="Endereço não pode estar vazio")
    private String enderecoPaisOrigem;
    @NotBlank(message="Telefone não pode estar vazio")
    private String telefone;
    @NotBlank(message="Email não pode estar vazio")
    @Email
    private String email;

}
