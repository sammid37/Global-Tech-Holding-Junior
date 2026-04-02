package com.sammid37.health_person.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PessoaDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate dataNasc;

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @Pattern(regexp = "(?i)^[MF]$", message = "Sexo deve ser M ou F")
    private String sexo;

    @NotNull
    private Double altura;

    @NotNull
    private Double peso;
}
