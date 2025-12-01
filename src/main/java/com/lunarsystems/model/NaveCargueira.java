package com.lunarsystems.model;


public class NaveCargueira extends Nave {
private double capacidadeCargaKg;

public NaveCargueira() {
}

public NaveCargueira(String id, String modelo, int capacidadeTripulantes, double capacidadeCargaKg) {
super(id, modelo, capacidadeTripulantes);
this.capacidadeCargaKg = capacidadeCargaKg;
}


public double getCapacidadeCargaKg() { return capacidadeCargaKg; }
}