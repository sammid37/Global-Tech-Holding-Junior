package com.sammid37.health_person.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @AllArgsConstructor
public class ErroResposta {
    private LocalDateTime timestamp;
    private Integer status;
    private String erro;
    private String caminho;
    private List<CampoErro> errosDetalhados;

    public ErroResposta(Integer status, String erro, String caminho) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.erro = erro;
        this.caminho = caminho;
    }
}
