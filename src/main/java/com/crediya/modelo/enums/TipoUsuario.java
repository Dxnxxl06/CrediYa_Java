package com.crediya.modelo.enums;

public enum TipoUsuario {
    ADMIN("Administrador"),
    EMPLEADO("Empleado"),
    CLIENTE("Cliente");
    
    private final String descripcion;
    
    TipoUsuario(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}