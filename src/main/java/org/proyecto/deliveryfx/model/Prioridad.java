package org.proyecto.deliveryfx.model;

public enum Prioridad {

    PREMIUM(1),    // Mayor prioridad
    RAPIDO(2),
    ESTANDAR(3);   // Menor prioridad

    private final int nivel;

    Prioridad(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }
}