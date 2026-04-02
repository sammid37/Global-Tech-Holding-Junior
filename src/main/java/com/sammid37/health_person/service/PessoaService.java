package com.sammid37.health_person.service;

import com.sammid37.health_person.dto.PessoaDTO;
import com.sammid37.health_person.task.PessoaTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Double calcularPesoIdeal(Long id) {
        return pessoaTask.calcularPesoIdeal(id);
    }
}
