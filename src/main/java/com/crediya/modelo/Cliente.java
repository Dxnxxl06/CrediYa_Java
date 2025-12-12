package com.crediya.modelo;

import java.time.LocalDateTime;

public class Cliente {
    private int id;
    private Integer usuarioId;
    private String nombre;
    private String documento;
    private String correo;
    private String telefono;
    private String direccion;
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Integer modificadoPor;
    
    public Cliente() {}
    
    public Cliente(String nombre, String documento, String correo, String telefono) {
        this.nombre = nombre;
        this.documento = documento;
        this.correo = correo;
        this.telefono = telefono;
        this.activo = true;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { 
        this.fechaCreacion = fechaCreacion; 
    }
    
    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { 
        this.fechaModificacion = fechaModificacion; 
    }
    
    public Integer getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(Integer modificadoPor) { 
        this.modificadoPor = modificadoPor; 
    }
    
    @Override
    public String toString() {
        return String.format("Cliente[id=%d, nombre=%s, documento=%s, telefono=%s]",
                           id, nombre, documento, telefono);
    }
}
