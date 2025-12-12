package com.crediya.modelo;

import com.crediya.modelo.enums.EstadoPrestamo;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Prestamo {
    private int id;
    private int clienteId;
    private int empleadoId;
    private double monto;
    private double interes;
    private double montoTotal;
    private int cuotas;
    private double valorCuota;
    private double saldoPendiente;
    private LocalDate fechaInicio;
    private int fechaCortePago;
    private EstadoPrestamo estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Integer modificadoPor;
    private String clienteNombre;
    private String empleadoNombre;
    
    public Prestamo() {}
    
    public Prestamo(int clienteId, int empleadoId, double monto, double interes, 
                   int cuotas, int fechaCortePago) {
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.monto = monto;
        this.interes = interes;
        this.cuotas = cuotas;
        this.fechaCortePago = fechaCortePago;
        this.fechaInicio = LocalDate.now();
        this.estado = EstadoPrestamo.ACTIVO;
        calcularMontos();
    }
    
    public void calcularMontos() {
        double interesCalculado = monto * (interes / 100);
        this.montoTotal = monto + interesCalculado;
        this.valorCuota = montoTotal / cuotas;
        this.saldoPendiente = montoTotal;
    }
    
    public void registrarPago(double montoPago) {
        this.saldoPendiente -= montoPago;
        if (saldoPendiente <= 0) {
            this.saldoPendiente = 0;
            this.estado = EstadoPrestamo.PAGADO;
        }
    }
    
    public boolean esVencido() {
        LocalDate hoy = LocalDate.now();
        int diaActual = hoy.getDayOfMonth();
        if (diaActual > fechaCortePago && estado == EstadoPrestamo.ACTIVO) {
            return true;
        }
        return false;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }
    
    public int getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(int empleadoId) { this.empleadoId = empleadoId; }
    
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    
    public double getInteres() { return interes; }
    public void setInteres(double interes) { this.interes = interes; }
    
    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }
    
    public int getCuotas() { return cuotas; }
    public void setCuotas(int cuotas) { this.cuotas = cuotas; }
    
    public double getValorCuota() { return valorCuota; }
    public void setValorCuota(double valorCuota) { this.valorCuota = valorCuota; }
    
    public double getSaldoPendiente() { return saldoPendiente; }
    public void setSaldoPendiente(double saldoPendiente) { 
        this.saldoPendiente = saldoPendiente; 
    }
    
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public int getFechaCortePago() { return fechaCortePago; }
    public void setFechaCortePago(int fechaCortePago) { 
        this.fechaCortePago = fechaCortePago; 
    }
    
    public EstadoPrestamo getEstado() { return estado; }
    public void setEstado(EstadoPrestamo estado) { this.estado = estado; }
    
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
    
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { 
        this.clienteNombre = clienteNombre; 
    }
    
    public String getEmpleadoNombre() { return empleadoNombre; }
    public void setEmpleadoNombre(String empleadoNombre) { 
        this.empleadoNombre = empleadoNombre; 
    }
    
    @Override
    public String toString() {
        return String.format("Prestamo[id=%d, monto=%.2f, montoTotal=%.2f, cuotas=%d, " +
                           "saldo=%.2f, estado=%s]",
                           id, monto, montoTotal, cuotas, saldoPendiente, estado);
    }
}
