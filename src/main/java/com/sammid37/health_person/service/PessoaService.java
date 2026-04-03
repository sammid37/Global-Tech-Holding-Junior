package com.sammid37.health_person.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sammid37.health_person.dto.PessoaDTO;
import com.sammid37.health_person.task.PessoaTask;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaService {
    private final PessoaTask pessoaTask;
    
    public PessoaDTO incluir(PessoaDTO dto) {
        return pessoaTask.incluir(dto);
    }
    
    public PessoaDTO alterar(Long id, PessoaDTO dto) {
        return pessoaTask.alterar(id, dto);
    }
    
    public void excluir(Long id) {
        pessoaTask.excluir(id);
    }
    
    public List<PessoaDTO> pesquisar(String nome) {
        return pessoaTask.pesquisar(nome);
    }
    
    public List<PessoaDTO> findAll() {
        return pessoaTask.findAll();
    }

    public PessoaDTO findById(Long id) {
        return pessoaTask.findById(id);
    }

    public PessoaDTO findByCpf(String cpf) {
        return pessoaTask.findByCpf(cpf);
    }

    public Double calcularPesoIdeal(Long id) {
        return pessoaTask.calcularPesoIdeal(id);
    }
}
