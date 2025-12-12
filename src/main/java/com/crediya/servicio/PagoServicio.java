package com.crediya.servicio;

import com.crediya.dao.PagoDAO;
import com.crediya.dao.PrestamoDAO;
import com.crediya.modelo.Pago;
import com.crediya.modelo.Prestamo;
import com.crediya.modelo.enums.EstadoPrestamo;
import com.crediya.utilidades.ArchivoUtil;
import java.util.List;

public class PagoServicio {
    private PagoDAO pagoDAO;
    private PrestamoDAO prestamoDAO;
    
    public PagoServicio() {
        this.pagoDAO = new PagoDAO();
        this.prestamoDAO = new PrestamoDAO();
    }
    
    public boolean registrarPago(Pago pago) {
        Prestamo prestamo = prestamoDAO.buscarPorId(pago.getPrestamoId());
        
        if (prestamo == null) {
            System.out.println("Error: Préstamo no encontrado");
            return false;
        }
        
        if (prestamo.getEstado() == EstadoPrestamo.PAGADO) {
            System.out.println("Error: El préstamo ya está completamente pagado");
            return false;
        }
        
        if (pago.getMonto() > prestamo.getSaldoPendiente()) {
            System.out.println("Advertencia: El monto del pago excede el saldo pendiente");
            System.out.printf("Saldo pendiente: $%.2f%n", prestamo.getSaldoPendiente());
            return false;
        }
        
        if (pagoDAO.crear(pago)) {
            prestamo.registrarPago(pago.getMonto());
            
            if (prestamo.getSaldoPendiente() <= 0) {
                prestamo.setEstado(EstadoPrestamo.PAGADO);
            } else if (prestamo.getEstado() == EstadoPrestamo.VENCIDO) {
                prestamo.setEstado(EstadoPrestamo.ACTIVO);
            }
            
            prestamoDAO.actualizar(prestamo);
            guardarEnArchivo(pago);
            
            System.out.printf("✓ Pago registrado exitosamente. Saldo pendiente: $%.2f%n", 
                            prestamo.getSaldoPendiente());
            return true;
        }
        return false;
    }
    
    public List<Pago> listarPorPrestamo(int prestamoId) {
        return pagoDAO.listarPorPrestamo(prestamoId);
    }
    
    public List<Pago> listarTodos() {
        return pagoDAO.listarTodos();
    }
    
    public void mostrarHistorialPagos(int prestamoId) {
        List<Pago> pagos = listarPorPrestamo(prestamoId);
        
        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados para este préstamo");
            return;
        }
        
        System.out.println("\n═══════════════════ HISTORIAL DE PAGOS ═══════════════════");
        System.out.printf("%-8s %-12s %-15s %-20s%n", 
                        "ID", "Fecha", "Monto", "Método");
        System.out.println("══════════════════════════════════════════════════════════");
        
        double totalPagado = 0;
        for (Pago p : pagos) {
            System.out.printf("%-8d %-12s $%-14.2f %-20s%n",
                            p.getId(),
                            p.getFechaPago(),
                            p.getMonto(),
                            p.getMetodoPago());
            totalPagado += p.getMonto();
        }
        
        System.out.println("══════════════════════════════════════════════════════════");
        System.out.printf("Total Pagado: $%.2f%n", totalPagado);
        System.out.println("══════════════════════════════════════════════════════════\n");
    }
    
    private void guardarEnArchivo(Pago pago) {
        String linea = String.format("%d,%d,%.2f,%s,%s",
            pago.getId(),
            pago.getPrestamoId(),
            pago.getMonto(),
            pago.getFechaPago(),
            pago.getMetodoPago()
        );
        ArchivoUtil.escribirLinea("pagos.txt", linea);
    }
}