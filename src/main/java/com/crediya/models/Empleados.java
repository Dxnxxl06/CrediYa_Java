package com.crediya.models;

public class Empleados {
    
    private int id;
    private String nombre;
    private String documento;
    private String rol;
    private String correo;
    private String salario;

    // Constructor completo
    public Empleados(int id, String nombre, String documento, String rol, String correo, String salario) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.rol = rol;
        this.correo = correo;
        this.salario = salario;
    }

    // Constructor sin ID (para nuevos empleados)
    public Empleados(String nombre, String documento, String rol, String correo, String salario) {
        this.nombre = nombre;
        this.documento = documento;
        this.rol = rol;
        this.correo = correo;
        this.salario = salario;
    }

    // Constructor vacío
    public Empleados() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Empleados{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", documento='" + documento + '\'' +
                ", rol='" + rol + '\'' +
                ", correo='" + correo + '\'' +
                ", salario='" + salario + '\'' +
                '}';
    }
}