package com.sammid37.health_person.service;

import com.sammid37.health_person.dto.PessoaDTO;
import com.sammid37.health_person.task.PessoaTask;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    private PessoaTask pessoaTask;

    @InjectMocks
    private PessoaService pessoaService;

    // -------------------------------------------------------------------------
    // incluir
    // -------------------------------------------------------------------------

    @Test
    void incluir_deveRetornarPessoaSalva() {
        // Arrange
        PessoaDTO entrada = umaPessoaDTO(null, "Ana Silva", "111.111.111-11", "F");
        PessoaDTO esperado = umaPessoaDTO(1L, "Ana Silva", "111.111.111-11", "F");
        when(pessoaTask.incluir(entrada)).thenReturn(esperado);

        // Act
        PessoaDTO resultado = pessoaService.incluir(entrada);

        // Assert
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("Ana Silva");
        verify(pessoaTask, times(1)).incluir(entrada);
    }

    // -------------------------------------------------------------------------
    // alterar
    // -------------------------------------------------------------------------

    @Test
    void alterar_deveRetornarPessoaAtualizada() {
        // Arrange
        Long id = 1L;
        PessoaDTO entrada = umaPessoaDTO(null, "Ana Souza", "111.111.111-11", "F");
        PessoaDTO esperado = umaPessoaDTO(id, "Ana Souza", "111.111.111-11", "F");
        when(pessoaTask.alterar(id, entrada)).thenReturn(esperado);

        // Act
        PessoaDTO resultado = pessoaService.alterar(id, entrada);

        // Assert
        assertThat(resultado.getNome()).isEqualTo("Ana Souza");
        verify(pessoaTask, times(1)).alterar(id, entrada);
    }

    @Test
    void alterar_quandoIdInexistente_deveLancarEntityNotFoundException() {
        // Arrange
        Long idInexistente = 999L;
        PessoaDTO entrada = umaPessoaDTO(null, "Ninguém", "000.000.000-00", "M");
        when(pessoaTask.alterar(idInexistente, entrada))
                .thenThrow(new EntityNotFoundException("Pessoa não encontrada"));

        // Act & Assert
        assertThatThrownBy(() -> pessoaService.alterar(idInexistente, entrada))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pessoa não encontrada");
    }

    // -------------------------------------------------------------------------
    // excluir
    // -------------------------------------------------------------------------

    @Test
    void excluir_deveDelegarParaTask() {
        // Arrange
        Long id = 1L;
        doNothing().when(pessoaTask).excluir(id);

        // Act
        pessoaService.excluir(id);

        // Assert
        verify(pessoaTask, times(1)).excluir(id);
    }

    // -------------------------------------------------------------------------
    // pesquisar
    // -------------------------------------------------------------------------

    @Test
    void pesquisar_deveRetornarListaFiltradaPorNome() {
        // Arrange
        String nome = "Ana";
        List<PessoaDTO> esperado = List.of(
                umaPessoaDTO(1L, "Ana Silva", "111.111.111-11", "F"),
                umaPessoaDTO(2L, "Ana Costa", "222.222.222-22", "F")
        );
        when(pessoaTask.pesquisar(nome)).thenReturn(esperado);

        // Act
        List<PessoaDTO> resultado = pessoaService.pesquisar(nome);

        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(p -> p.getNome().contains("Ana"));
        verify(pessoaTask, times(1)).pesquisar(nome);
    }

    @Test
    void pesquisar_quandoNenhumResultado_deveRetornarListaVazia() {
        // Arrange
        String nome = "Inexistente";
        when(pessoaTask.pesquisar(nome)).thenReturn(List.of());

        // Act
        List<PessoaDTO> resultado = pessoaService.pesquisar(nome);

        // Assert
        assertThat(resultado).isEmpty();
    }

    // -------------------------------------------------------------------------
    // findAll
    // -------------------------------------------------------------------------

    @Test
    void findAll_deveRetornarTodasAsPessoas() {
        // Arrange
        List<PessoaDTO> esperado = List.of(
                umaPessoaDTO(1L, "Ana Silva", "111.111.111-11", "F"),
                umaPessoaDTO(2L, "Carlos Lima", "333.333.333-33", "M")
        );
        when(pessoaTask.findAll()).thenReturn(esperado);

        // Act
        List<PessoaDTO> resultado = pessoaService.findAll();

        // Assert
        assertThat(resultado).hasSize(2);
        verify(pessoaTask, times(1)).findAll();
    }

    // -------------------------------------------------------------------------
    // findById
    // -------------------------------------------------------------------------

    @Test
    void findById_quandoIdExistente_deveRetornarPessoa() {
        // Arrange
        Long id = 1L;
        PessoaDTO esperado = umaPessoaDTO(id, "Ana Silva", "111.111.111-11", "F");
        when(pessoaTask.findById(id)).thenReturn(esperado);

        // Act
        PessoaDTO resultado = pessoaService.findById(id);

        // Assert
        assertThat(resultado.getId()).isEqualTo(id);
        assertThat(resultado.getNome()).isEqualTo("Ana Silva");
        verify(pessoaTask, times(1)).findById(id);
    }

    @Test
    void findById_quandoIdInexistente_deveLancarEntityNotFoundException() {
        // Arrange
        Long idInexistente = 999L;
        when(pessoaTask.findById(idInexistente))
                .thenThrow(new EntityNotFoundException("Pessoa não encontrada"));

        // Act & Assert
        assertThatThrownBy(() -> pessoaService.findById(idInexistente))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pessoa não encontrada");
    }

    // -------------------------------------------------------------------------
    // findByCpf
    // -------------------------------------------------------------------------

    @Test
    void findByCpf_quandoCpfExistente_deveRetornarPessoa() {
        // Arrange
        String cpf = "111.111.111-11";
        PessoaDTO esperado = umaPessoaDTO(1L, "Ana Silva", cpf, "F");
        when(pessoaTask.findByCpf(cpf)).thenReturn(esperado);

        // Act
        PessoaDTO resultado = pessoaService.findByCpf(cpf);

        // Assert
        assertThat(resultado.getCpf()).isEqualTo(cpf);
        verify(pessoaTask, times(1)).findByCpf(cpf);
    }

    @Test
    void findByCpf_quandoCpfInexistente_deveLancarEntityNotFoundException() {
        // Arrange
        String cpfInexistente = "000.000.000-00";
        when(pessoaTask.findByCpf(cpfInexistente))
                .thenThrow(new EntityNotFoundException("Pessoa não encontrada"));

        // Act & Assert
        assertThatThrownBy(() -> pessoaService.findByCpf(cpfInexistente))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pessoa não encontrada");
    }

    // -------------------------------------------------------------------------
    // calcularPesoIdeal
    // -------------------------------------------------------------------------

    @Test
    void calcularPesoIdeal_quandoSexoMasculino_deveRetornarPesoCalculado() {
        // Arrange
        Long id = 1L;
        double pesoEsperado = (72.7 * 1.75) - 58; // fórmula masculina
        when(pessoaTask.calcularPesoIdeal(id)).thenReturn(pesoEsperado);

        // Act
        Double resultado = pessoaService.calcularPesoIdeal(id);

        // Assert
        assertThat(resultado).isEqualTo(pesoEsperado);
        verify(pessoaTask, times(1)).calcularPesoIdeal(id);
    }

    @Test
    void calcularPesoIdeal_quandoSexoFeminino_deveRetornarPesoCalculado() {
        // Arrange
        Long id = 2L;
        double pesoEsperado = (62.1 * 1.65) - 44.7; // fórmula feminina
        when(pessoaTask.calcularPesoIdeal(id)).thenReturn(pesoEsperado);

        // Act
        Double resultado = pessoaService.calcularPesoIdeal(id);

        // Assert
        assertThat(resultado).isEqualTo(pesoEsperado);
        verify(pessoaTask, times(1)).calcularPesoIdeal(id);
    }

    @Test
    void calcularPesoIdeal_quandoIdInexistente_deveLancarEntityNotFoundException() {
        // Arrange
        Long idInexistente = 999L;
        when(pessoaTask.calcularPesoIdeal(idInexistente))
                .thenThrow(new EntityNotFoundException("Pessoa não encontrada"));

        // Act & Assert
        assertThatThrownBy(() -> pessoaService.calcularPesoIdeal(idInexistente))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pessoa não encontrada");
    }

    // -------------------------------------------------------------------------
    // helpers
    // -------------------------------------------------------------------------

    private PessoaDTO umaPessoaDTO(Long id, String nome, String cpf, String sexo) {
        PessoaDTO dto = new PessoaDTO();
        dto.setId(id);
        dto.setNome(nome);
        dto.setDataNasc(LocalDate.of(1990, 1, 1));
        dto.setCpf(cpf);
        dto.setSexo(sexo);
        dto.setAltura(1.70);
        dto.setPeso(65.0);
        return dto;
    }
}
