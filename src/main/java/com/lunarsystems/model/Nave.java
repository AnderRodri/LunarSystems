package com.lunarsystems.model;


import java.io.Serializable;


public abstract class Nave implements Serializable {
private String id;
private String modelo;
private int capacidadeTripulantes;


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