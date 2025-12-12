package com.crediya.modelo.enums;

public enum EstadoPrestamo {
    ACTIVO("Activo"),
    PAGADO("Pagado"),
    VENCIDO("Vencido");
    
    private final String descripcion;
    
    EstadoPrestamo(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}