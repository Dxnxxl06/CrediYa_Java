package com.crediya.servicio;

import com.crediya.modelo.Prestamo;
import com.crediya.modelo.Pago;
import com.crediya.modelo.Cliente;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteServicio {
    private PrestamoServicio prestamoServicio;
    private PagoServicio pagoServicio;
    private ClienteServicio clienteServicio;
    
    public ReporteServicio() {
        this.prestamoServicio = new PrestamoServicio();
        this.pagoServicio = new PagoServicio();
        this.clienteServicio = new ClienteServicio();
    }
    
    public void reportePrestamosActivos() {
        List<Prestamo> activos = prestamoServicio.listarActivos();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          REPORTE DE PRÉSTAMOS ACTIVOS                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        if (activos.isEmpty()) {
            System.out.println("No hay préstamos activos");
            return;
        }
        
        activos.forEach(prestamoServicio::mostrarPrestamo);
        
        double totalPrestado = activos.stream()
            .mapToDouble(Prestamo::getMontoTotal)
            .sum();
        double totalPendiente = activos.stream()
            .mapToDouble(Prestamo::getSaldoPendiente)
            .sum();
        
        System.out.printf("\nTotal préstamos activos: %d%n", activos.size());
        System.out.printf("Total monto prestado: $%.2f%n", totalPrestado);
        System.out.printf("Total saldo pendiente: $%.2f%n", totalPendiente);
    }
    
    public void reportePrestamosVencidos() {
        List<Prestamo> vencidos = prestamoServicio.listarVencidos();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          REPORTE DE PRÉSTAMOS VENCIDOS                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        if (vencidos.isEmpty()) {
            System.out.println("✓ No hay préstamos vencidos");
            return;
        }
        
        vencidos.forEach(prestamoServicio::mostrarPrestamo);
        
        double totalVencido = vencidos.stream()
            .mapToDouble(Prestamo::getSaldoPendiente)
            .sum();
        
        System.out.printf("\n⚠ Total préstamos vencidos: %d%n", vencidos.size());
        System.out.printf("⚠ Total saldo vencido: $%.2f%n", totalVencido);
    }
    
    public void reporteClientesMorosos() {
        List<Prestamo> vencidos = prestamoServicio.listarVencidos();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          REPORTE DE CLIENTES MOROSOS                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        if (vencidos.isEmpty()) {
            System.out.println("✓ No hay clientes morosos");
            return;
        }
        
        Map<Integer, List<Prestamo>> porCliente = vencidos.stream()
            .collect(Collectors.groupingBy(Prestamo::getClienteId));
        
        System.out.printf("%-30s %-15s %-15s%n", "Cliente", "Préstamos", "Deuda Total");
        System.out.println("══════════════════════════════════════════════════════════");
        
        porCliente.forEach((clienteId, prestamos) -> {
            Cliente cliente = clienteServicio.buscarPorId(clienteId);
            double deudaTotal = prestamos.stream()
                .mapToDouble(Prestamo::getSaldoPendiente)
                .sum();
            
            System.out.printf("%-30s %-15d $%-14.2f%n",
                            cliente.getNombre(),
                            prestamos.size(),
                            deudaTotal);
        });
    }
    
    public void reportePagosPorPeriodo(LocalDate inicio, LocalDate fin) {
        List<Pago> todosPagos = pagoServicio.listarTodos();
        
        List<Pago> pagosPeriodo = todosPagos.stream()
            .filter(p -> !p.getFechaPago().isBefore(inicio) && 
                        !p.getFechaPago().isAfter(fin))
            .collect(Collectors.toList());
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          REPORTE DE PAGOS POR PERÍODO                      ║");
        System.out.printf("║  Desde: %-50s║%n", inicio);
        System.out.printf("║  Hasta: %-50s║%n", fin);
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        if (pagosPeriodo.isEmpty()) {
            System.out.println("No hay pagos registrados en este período");
            return;
        }
        
        double totalRecaudado = pagosPeriodo.stream()
            .mapToDouble(Pago::getMonto)
            .sum();
        
        System.out.printf("Total de pagos: %d%n", pagosPeriodo.size());
        System.out.printf("Total recaudado: $%.2f%n", totalRecaudado);
    }
}