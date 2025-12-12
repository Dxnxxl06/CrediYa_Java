package com.crediya.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Empleado {
    private int id;
    private Integer usuarioId;
    private String nombre;
    private String documento;
    private String rol;
    private String correo;
    private double salario;
    private LocalDate fechaContratacion;
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Integer modificadoPor;
    
    public Empleado() {}
    
    public Empleado(String nombre, String documento, String rol, String correo, double salario) {
        this.nombre = nombre;
        this.documento = documento;
        this.rol = rol;
        this.correo = correo;
        this.salario = salario;
        this.activo = true;
        this.fechaContratacion = LocalDate.now();
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    
    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(LocalDate fechaContratacion) { 
        this.fechaContratacion = fechaContratacion; 
    }
    
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
        return String.format("Empleado[id=%d, nombre=%s, documento=%s, rol=%s, salario=%.2f]",
                           id, nombre, documento, rol, salario);
    }
}
