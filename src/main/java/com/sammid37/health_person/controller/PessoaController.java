package com.sammid37.health_person.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sammid37.health_person.dto.PessoaDTO;
import com.sammid37.health_person.service.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pessoas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Pessoa", description = "Endpoints para o gerenciamento de Pessoas")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @Operation(summary = "Adiciona uma nova pessoa.", description = "Adiciona um nova pessoa.")
    @ApiResponse(responseCode = "200", description = "Pessoa adicionada com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class)))
    @PostMapping
    public ResponseEntity<PessoaDTO> incluir(@RequestBody @Valid PessoaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.incluir(dto));
    }

    @PutMapping("/editar/{id}")
    @Operation(summary = "Atualiza o cadastro de uma Pessoa.", description = "Atualiza os dados de uma pessoa específica dado um ID.")
    @ApiResponse(responseCode = "200", description = "Pessoa atualizado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class)))
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrado.")
    public ResponseEntity<PessoaDTO> alterar(
            @Parameter(description = "ID do cadastro de uma pessoa a ser atualizado.", example = "1")
            @PathVariable Long id,
            @RequestBody @Valid PessoaDTO dto) {
        return ResponseEntity.ok(pessoaService.alterar(id, dto));
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta uma pessoa do sistema.", description = "Remove uma pessoa específica dado um ID.")
    @ApiResponse(responseCode = "204", description = "Pessoa deletada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrado.")
    public ResponseEntity<Void> deletePessoa(@Parameter(description = "ID da pessoa a ser removida do sistema.", example = "1") @PathVariable Long id) {
        pessoaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Pesquisa pessoas pelo nome.", description = "Retorna a lista de pessoas cujo nome corresponde ao parâmetro informado.")
    @ApiResponse(responseCode = "200", description = "Lista de pessoas encontradas.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class)))
    public ResponseEntity<List<PessoaDTO>> pesquisar(
            @Parameter(description = "Nome (ou parte do nome) da pessoa a ser pesquisada.", example = "Ana")
            @RequestParam String nome) {
        return ResponseEntity.ok(pessoaService.pesquisar(nome));
    }

    @GetMapping("/{id}/peso-ideal")
    @Operation(summary = "Calcula o peso ideal de uma pessoa.", description = "Retorna o peso ideal calculado para a pessoa com o ID informado.")
    @ApiResponse(responseCode = "200", description = "Peso ideal calculado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada.")
    public ResponseEntity<Double> calcularPesoIdeal(
            @Parameter(description = "ID da pessoa para calcular o peso ideal.", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(pessoaService.calcularPesoIdeal(id));
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Busca uma pessoa pelo CPF.", description = "Retorna os dados de uma pessoa específica dado o seu CPF.")
    @ApiResponse(responseCode = "200", description = "Pessoa encontrada no sistema.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class)))
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada no sistema.")
    public ResponseEntity<PessoaDTO> getPessoaByCpf(
            @Parameter(description = "CPF da pessoa a ser encontrada.", example = "123.456.789-00")
            @PathVariable String cpf) {
        return ResponseEntity.ok(pessoaService.findByCpf(cpf));
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista todas as pessoas cadastradas.", description = "Retorna a lista de todas as pessoas cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de pessoas cadastradas retornada com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class)))
    public ResponseEntity<List<PessoaDTO>> getAllPessoa() {
        return ResponseEntity.ok(pessoaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Exibe dados de uma pessoa específica.", description = "Busca por uma pessoa específica dado o ID do seu cadastro.")
    @ApiResponse(responseCode = "200", description = "Pessoa encontrada no sistema.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class)))
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada no sistema.")
    public ResponseEntity<PessoaDTO> getPessoaById(
            @Parameter(description = "ID da pessoa a ser encontrada.", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(pessoaService.findById(id));
    }
}
