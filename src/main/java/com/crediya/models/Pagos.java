package com.crediya.models;

import java.time.LocalDateTime;

public class Pagos {
    
    private int id;
    private int prestamoId;
    private double montoPagado;
    private LocalDateTime fechaPago;
    private String metodoPago;
    private String comprobante;
    private String observaciones;

    // Constructor completo
    public Pagos(int id, int prestamoId, double montoPagado, LocalDateTime fechaPago, 
                 String metodoPago, String comprobante, String observaciones) {
        this.id = id;
        this.prestamoId = prestamoId;
        this.montoPagado = montoPagado;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.comprobante = comprobante;
        this.observaciones = observaciones;
    }

    // Constructor para nuevo pago simple
    public Pagos(int prestamoId, double montoPagado, String metodoPago) {
        this.prestamoId = prestamoId;
        this.montoPagado = montoPagado;
        this.metodoPago = metodoPago;
        this.fechaPago = LocalDateTime.now();
        this.comprobante = "";
        this.observaciones = "";
    }

    // Constructor para nuevo pago con detalles
    public Pagos(int prestamoId, double montoPagado, String metodoPago, 
                 String comprobante, String observaciones) {
        this.prestamoId = prestamoId;
        this.montoPagado = montoPagado;
        this.metodoPago = metodoPago;
        this.comprobante = comprobante;
        this.observaciones = observaciones;
        this.fechaPago = LocalDateTime.now();
    }

    // Constructor vacío
    public Pagos() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(int prestamoId) {
        this.prestamoId = prestamoId;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Pagos{" +
                "id=" + id +
                ", prestamoId=" + prestamoId +
                ", montoPagado=" + montoPagado +
                ", fechaPago=" + fechaPago +
                ", metodoPago='" + metodoPago + '\'' +
                ", comprobante='" + comprobante + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}