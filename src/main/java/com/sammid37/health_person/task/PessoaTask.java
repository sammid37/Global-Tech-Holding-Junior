package com.sammid37.health_person.task;

import com.sammid37.health_person.dto.PessoaDTO;
import com.sammid37.health_person.mapper.PessoaMapper;
import com.sammid37.health_person.model.Pessoa;
import com.sammid37.health_person.model.enums.Sexo;
import com.sammid37.health_person.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PessoaTask {

    private final PessoaRepository repository;
    private final PessoaMapper mapper;

    public PessoaDTO incluir(PessoaDTO dto) {
        Pessoa pessoa = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(pessoa));
    }

    public PessoaDTO alterar(Long id, PessoaDTO dto) {
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));

        pessoa.setNome(dto.getNome());
        pessoa.setDataNasc(dto.getDataNasc());
        pessoa.setCpf(dto.getCpf());
        pessoa.setSexo(Sexo.valueOf(dto.getSexo()));
        pessoa.setAltura(dto.getAltura());
        pessoa.setPeso(dto.getPeso());

        return mapper.toDTO(repository.save(pessoa));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public List<PessoaDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public PessoaDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
    }

    public List<PessoaDTO> pesquisar(String nome) {
        return repository.findAll().stream()
                .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /** Ponto extra: calcula o peso ideal conforme sexo */
    public Double calcularPesoIdeal(Long id) {
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));

        if ("M".equals(pessoa.getSexo())) {
            return (72.7 * pessoa.getAltura()) - 58;
        } else {
            return (62.1 * pessoa.getAltura()) - 44.7;
        }
    }
}