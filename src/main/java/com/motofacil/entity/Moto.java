package com.motofacil.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;

    @NotBlank
    private String modelo;

    private String chassi;

    @Column(unique = true)
    private String codigo;

    @NotBlank
    private String categoria;

    @NotBlank
    private String status; // "patio", "mecanica", "pendente"

    private String descricao;

    @ManyToOne
    private Patio patio;

    @OneToOne
    private Location location; // Última localização
}
