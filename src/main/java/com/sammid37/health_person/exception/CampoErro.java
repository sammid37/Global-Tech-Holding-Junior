package com.sammid37.health_person.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CampoErro {
    private String campo;
    private String mensagem;
}