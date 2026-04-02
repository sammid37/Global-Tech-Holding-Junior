package com.sammid37.health_person.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarErroDeValidacao(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<CampoErro> errosDeCampo = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> new CampoErro(erro.getField(), erro.getDefaultMessage()))
                .collect(Collectors.toList());

        ErroResposta resposta = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação nos dados enviados",
                request.getRequestURI()
        );
        resposta.setErrosDetalhados(errosDeCampo);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResposta> tratarEntidadeNaoEncontrada(EntityNotFoundException ex, HttpServletRequest request) {

        ErroResposta resposta = new ErroResposta(
                HttpStatus.NOT_FOUND.value(),
                "Registro não encontrado no sistema",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErroResposta> tratarRotaNaoEncontrada(NoResourceFoundException ex, HttpServletRequest request) {

        ErroResposta resposta = new ErroResposta(
                HttpStatus.NOT_FOUND.value(),
                "Rota não encontrada: " + request.getRequestURI(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

}