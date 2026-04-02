package com.sammid37.health_person.mapper;

import com.sammid37.health_person.dto.PessoaDTO;
import com.sammid37.health_person.model.Pessoa;
import com.sammid37.health_person.model.enums.Sexo;
import org.springframework.stereotype.Component;

@Component
public class PessoaMapper {
    public Pessoa toEntity(PessoaDTO dto) {
        Sexo sexoEnum = dto.getSexo() != null ? Sexo.valueOf(dto.getSexo().toUpperCase()) : null;

        return new Pessoa(
                null,
                dto.getNome(),
                dto.getDataNasc(),
                dto.getCpf(),
                sexoEnum,
                dto.getAltura(),
                dto.getPeso()
        );
    }

    public PessoaDTO toDTO(Pessoa pessoa) {
        String sexoString = pessoa.getSexo() != null ? pessoa.getSexo().name() : null;

        return new PessoaDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getDataNasc(),
                pessoa.getCpf(),
                sexoString,
                pessoa.getAltura(),
                pessoa.getPeso()
        );
    }
}
