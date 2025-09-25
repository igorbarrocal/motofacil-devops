package com.motofacil.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float x;
    private float y;

    private LocalDateTime timestamp;

    private String tag; // ex: "patio", "movimento"

    @ManyToOne
    @JoinColumn(name = "moto_id")
    @JsonBackReference // evita loop serializando a moto
    private Moto moto;

    @ManyToOne
    @JoinColumn(name = "patio_id")
    private Patio patio;

    // ===== Getters e Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }

    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public Moto getMoto() { return moto; }
    public void setMoto(Moto moto) { this.moto = moto; }

    public Patio getPatio() { return patio; }
    public void setPatio(Patio patio) { this.patio = patio; }
}
