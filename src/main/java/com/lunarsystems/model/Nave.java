package com.lunarsystems.model;

import java.io.Serializable;
import org.dizitart.no2.objects.Id;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type"
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = NaveCargueira.class, name = "cargueira"),
  @JsonSubTypes.Type(value = NaveTripulada.class, name = "tripulada")
})

public abstract class Nave implements Serializable {

    @Id
    protected String id; // <--- MUDE DE PRIVATE PARA PROTECTED
    
    private String modelo;
    private int capacidadeTripulantes;

    public Nave() {
    }

    public Nave(String id, String modelo, int capacidadeTripulantes) {
        this.id = id;
        this.modelo = modelo;
        this.capacidadeTripulantes = capacidadeTripulantes;
    }

    public String getId() { return id; }
    public String getModelo() { return modelo; }
    public int getCapacidadeTripulantes() { return capacidadeTripulantes; }

    @Override
    public String toString() {
        return modelo + " (ID=" + id + ", cap=" + capacidadeTripulantes + ")";
    }
}