package com.sammid37.health_person.validation;

import com.sammid37.health_person.dto.PessoaDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PessoaDTOValidacaoTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // -------------------------------------------------------------------------
    // CPF — CpfValidator
    // -------------------------------------------------------------------------

    @Test
    void cpf_valido_devePassarNaValidacao() {
        PessoaDTO dto = umDTO("529.982.247-25", LocalDate.of(1990, 1, 1));
        Set<ConstraintViolation<PessoaDTO>> violations = validator.validateProperty(dto, "cpf");
        assertThat(violations).isEmpty();
    }

    @Test
    void cpf_apenasDigitosValido_devePassarNaValidacao() {
        PessoaDTO dto = umDTO("52998224725", LocalDate.of(1990, 1, 1));
        Set<ConstraintViolation<PessoaDTO>> violations = validator.validateProperty(dto, "cpf");
        assertThat(violations).isEmpty();
    }

    @Test
    void cpf_digitosVerificadoresErrados_deveRetornarViolacao() {
        PessoaDTO dto = umDTO("529.982.247-99", LocalDate.of(1990, 1, 1));
        Set<ConstraintViolation<PessoaDTO>> violations = validator.validateProperty(dto, "cpf");
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("CPF inválido");
    }

    @Test
    void cpf_sequenciaRepetida_deveRetornarViolacao() {
        PessoaDTO dto = umDTO("111.111.111-11", LocalDate.of(1990, 1, 1));
        Set<ConstraintViolation<PessoaDTO>> violations = validator.validateProperty(dto, "cpf");
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("CPF inválido");
    }

    @Test
    void cpf_formatoErrado_deveRetornarViolacaoDePattern() {
        PessoaDTO dto = umDTO("1234", LocalDate.of(1990, 1, 1));
        Set<ConstraintViolation<PessoaDTO>> violations = validator.validateProperty(dto, "cpf");
        assertThat(violations).isNotEmpty();
    }

    // -------------------------------------------------------------------------
    // Data de Nascimento — @PastOrPresent
    // -------------------------------------------------------------------------

    @Test
    void dataNasc_noPassado_devePassarNaValidacao() {
        PessoaDTO dto = umDTO("529.982.247-25", LocalDate.of(1990, 6, 15));
        Set<ConstraintViolation<PessoaDTO>> violations = validator.validateProperty(dto, "dataNasc");
        assertThat(violations).isEmpty();
    }

    @Test
    void dataNasc_hoje_devePassarNaValidacao() {
        PessoaDTO dto = umDTO("529.982.247-25", LocalDate.now());
        Set<ConstraintViolation<PessoaDTO>> violations = validator.validateProperty(dto, "dataNasc");
        assertThat(violations).isEmpty();
    }

    @Test
    void dataNasc_noFuturo_deveRetornarViolacao() {
        PessoaDTO dto = umDTO("529.982.247-25", LocalDate.now().plusDays(1));
        Set<ConstraintViolation<PessoaDTO>> violations = validator.validateProperty(dto, "dataNasc");
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Data de nascimento não pode ser no futuro");
    }

    @Test
    void dataNasc_dataDistanteNoFuturo_deveRetornarViolacao() {
        PessoaDTO dto = umDTO("529.982.247-25", LocalDate.of(2099, 12, 31));
        Set<ConstraintViolation<PessoaDTO>> violations = validator.validateProperty(dto, "dataNasc");
        assertThat(violations).hasSize(1);
    }

    // -------------------------------------------------------------------------
    // helpers
    // -------------------------------------------------------------------------

    private PessoaDTO umDTO(String cpf, LocalDate dataNasc) {
        PessoaDTO dto = new PessoaDTO();
        dto.setNome("Teste");
        dto.setCpf(cpf);
        dto.setDataNasc(dataNasc);
        dto.setSexo("M");
        dto.setAltura(1.75);
        dto.setPeso(70.0);
        return dto;
    }
}
