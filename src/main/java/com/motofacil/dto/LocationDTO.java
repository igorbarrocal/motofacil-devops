package com.motofacil.dto;

public class LocationDTO {
    private float x;
    private float y;
    private Long motoId;
    private Long patioId;   // ID do pátio
    private String tag;     // Ex: "patio", "movimento", etc

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }

    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public Long getMotoId() { return motoId; }
    public void setMotoId(Long motoId) { this.motoId = motoId; }

    public Long getPatioId() { return patioId; }
    public void setPatioId(Long patioId) { this.patioId = patioId; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
}
