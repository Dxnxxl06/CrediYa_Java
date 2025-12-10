package com.crediya.models;

import java.time.LocalDate;

public class Prestamos {
    
    private int id;
    private int clienteId;
    private int empleadoId;
    private double montoPrincipal;
    private double tasaInteres;
    private int plazoMeses;
    private double montoTotal;
    private double cuotaMensual;
    private double saldoPendiente;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;

    // Constructor completo
    public Prestamos(int id, int clienteId, int empleadoId, double montoPrincipal, 
                     double tasaInteres, int plazoMeses, double montoTotal, 
                     double cuotaMensual, double saldoPendiente, String estado, 
                     LocalDate fechaInicio, LocalDate fechaVencimiento) {
        this.id = id;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.montoPrincipal = montoPrincipal;
        this.tasaInteres = tasaInteres;
        this.plazoMeses = plazoMeses;
        this.montoTotal = montoTotal;
        this.cuotaMensual = cuotaMensual;
        this.saldoPendiente = saldoPendiente;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Constructor para crear nuevo préstamo (con cálculos automáticos)
    public Prestamos(int clienteId, int empleadoId, double montoPrincipal, 
                     double tasaInteres, int plazoMeses) {
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.montoPrincipal = montoPrincipal;
        this.tasaInteres = tasaInteres;
        this.plazoMeses = plazoMeses;
        this.estado = "PENDIENTE";
        this.fechaInicio = LocalDate.now();
        this.fechaVencimiento = this.fechaInicio.plusMonths(plazoMeses);
        
        // Cálculos automáticos
        calcularMontoTotal();
        calcularCuotaMensual();
        this.saldoPendiente = this.montoTotal;
    }

    // Constructor vacío
    public Prestamos() {
    }

    // Métodos de cálculo
    private void calcularMontoTotal() {
        double interes = montoPrincipal * (tasaInteres / 100);
        this.montoTotal = montoPrincipal + interes;
    }

    private void calcularCuotaMensual() {
        if (plazoMeses > 0) {
            this.cuotaMensual = montoTotal / plazoMeses;
        }
    }

    public void actualizarSaldo(double montoPagado) {
        this.saldoPendiente -= montoPagado;
        if (this.saldoPendiente <= 0) {
            this.saldoPendiente = 0;
            this.estado = "PAGADO";
        }
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public double getMontoPrincipal() {
        return montoPrincipal;
    }

    public void setMontoPrincipal(double montoPrincipal) {
        this.montoPrincipal = montoPrincipal;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(double cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return "Prestamos{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", empleadoId=" + empleadoId +
                ", montoPrincipal=" + montoPrincipal +
                ", tasaInteres=" + tasaInteres +
                ", plazoMeses=" + plazoMeses +
                ", montoTotal=" + montoTotal +
                ", cuotaMensual=" + cuotaMensual +
                ", saldoPendiente=" + saldoPendiente +
                ", estado='" + estado + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaVencimiento=" + fechaVencimiento +
                '}';
    }
}