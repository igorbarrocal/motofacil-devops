package com.motofacil.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Patio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @Column(unique = true)
    private String codigoUnico;

    @NotBlank
    private String esp32Central;

    @ElementCollection
    private List<Float> coordenadasExtremidade; // x1,y1,x2,y2,... ou só [x1,y1,x2,y2,x3,y3,x4,y4]

    @ManyToOne
    private Administrador administrador;
}
