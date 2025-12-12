package com.crediya.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pago {
    private int id;
    private int prestamoId;
    private double monto;
    private LocalDate fechaPago;
    private String metodoPago;
    private String referencia;
    private String observaciones;
    private Integer registradoPor;
    private LocalDateTime fechaRegistro;
    private String clienteNombre;
    private String registradoPorNombre;
    
    public Pago() {}
    
    public Pago(int prestamoId, double monto, String metodoPago) {
        this.prestamoId = prestamoId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.fechaPago = LocalDate.now();
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getPrestamoId() { return prestamoId; }
    public void setPrestamoId(int prestamoId) { this.prestamoId = prestamoId; }
    
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    
    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }
    
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    
    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { 
        this.observaciones = observaciones; 
    }
    
    public Integer getRegistradoPor() { return registradoPor; }
    public void setRegistradoPor(Integer registradoPor) { 
        this.registradoPor = registradoPor; 
    }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { 
        this.fechaRegistro = fechaRegistro; 
    }
    
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { 
        this.clienteNombre = clienteNombre; 
    }
    
    public String getRegistradoPorNombre() { return registradoPorNombre; }
    public void setRegistradoPorNombre(String registradoPorNombre) { 
        this.registradoPorNombre = registradoPorNombre; 
    }
    
    @Override
    public String toString() {
        return String.format("Pago[id=%d, prestamoId=%d, monto=%.2f, fecha=%s, metodo=%s]",
                           id, prestamoId, monto, fechaPago, metodoPago);
    }
}