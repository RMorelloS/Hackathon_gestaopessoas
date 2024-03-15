package com.fiap.postech.hackathon.gestaopessoas.service;

import com.fiap.postech.hackathon.gestaopessoas.model.Pessoa;
import com.fiap.postech.hackathon.gestaopessoas.repository.PessoaRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private Validator validator;

    public Pessoa getPessoaByCPF(String cpf) {
        try {
            return pessoaRepository.getReferenceById(cpf);
        }catch(Exception e){
            throw e;
        }
    }

    public Pessoa cadastrarPessoa(Pessoa pessoa) {
        var violations = validator.validate(pessoa);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        return pessoaRepository.save(pessoa);
    }

    public String deletarPessoa(String cpf) {
        try{
            pessoaRepository.deleteById(cpf);
            return cpf;
        }catch(Exception e){
            throw e;
        }
    }

    public Pessoa atualizarPessoa(String cpf, Pessoa pessoa) {
        var violations = validator.validate(pessoa);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var pessoaAtualizado = getPessoaByCPF(cpf);
        pessoaAtualizado.setEmail(pessoa.getEmail());
        pessoaAtualizado.setPassaporte(pessoa.getPassaporte());
        pessoaAtualizado.setTelefone(pessoa.getTelefone());
        pessoaAtualizado.setDataNascimento(pessoa.getDataNascimento());
        pessoaAtualizado.setNomeCompleto(pessoa.getNomeCompleto());
        pessoaAtualizado.setPaisOrigem(pessoa.getPaisOrigem());
        pessoaAtualizado.setEnderecoPaisOrigem(pessoa.getEnderecoPaisOrigem());
        pessoaRepository.save(pessoaAtualizado);
        return pessoaAtualizado;
    }

    public List<Pessoa> obterListaPessoas() {
        return pessoaRepository.findAll();
    }
}
