package com.motofacil.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToOne(mappedBy = "moto", cascade = CascadeType.ALL)
    @JsonManagedReference // serializa a Location, evitando loop
    private Location location; // Última localização
}
