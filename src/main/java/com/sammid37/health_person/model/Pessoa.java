package com.sammid37.health_person.model;

import com.sammid37.health_person.model.enums.Sexo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pessoa")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "data_nasc", nullable = false)
    private LocalDate dataNasc;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private Sexo sexo;

    @Column(nullable = false)
    private Double altura;

    @Column(nullable = false)
    private Double peso;
}
