package com.fiap.postech.hackathon.gestaopessoas.controller;

import com.fiap.postech.hackathon.gestaopessoas.model.Pessoa;
import com.fiap.postech.hackathon.gestaopessoas.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestaoPessoas")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;
    @GetMapping("/{CPF}")
    public ResponseEntity<?> getPessoaByCPF(@PathVariable String CPF){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pessoaService.getPessoaByCPF(CPF));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> obterListaPessoas(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pessoaService.obterListaPessoas());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping()
    @Transactional
    public ResponseEntity<?> cadastrarPessoa(@RequestBody Pessoa pessoa){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pessoaService.cadastrarPessoa(pessoa));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{CPF}")
    public ResponseEntity<?> deletarPessoa(@PathVariable String CPF){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pessoaService.deletarPessoa(CPF));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{CPF}")
    public ResponseEntity<?> atualizarPessoa(@PathVariable String CPF, @RequestBody Pessoa pessoa){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pessoaService.atualizarPessoa(CPF, pessoa));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
