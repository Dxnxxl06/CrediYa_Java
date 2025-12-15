package com.crediya.vista;

import com.crediya.servicio.*;
import com.crediya.modelo.*;
import java.util.List;
import java.util.Scanner;

public class MenuCliente {
    private Scanner scanner;
    private AutenticacionServicio authServicio;
    private PrestamoServicio prestamoServicio;
    private PagoServicio pagoServicio;
    
    public MenuCliente(AutenticacionServicio authServicio) {
        this.scanner = new Scanner(System.in);
        this.authServicio = authServicio;
        this.prestamoServicio = new PrestamoServicio();
        this.pagoServicio = new PagoServicio();
    }
    
    public boolean mostrar() {
        Cliente clienteActual = authServicio.getClienteActual();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   MENÚ CLIENTE                             ║");
        System.out.printf("║  Bienvenido: %-46s║%n", clienteActual.getNombre());
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Ver Mis Préstamos                                     ║");
        System.out.println("║  2. Ver Detalle de Préstamo                               ║");
        System.out.println("║  3. Ver Historial de Pagos                                ║");
        System.out.println("║  4. Ver Mis Datos                                         ║");
        System.out.println("║  5. Cerrar Sesión                                         ║");
        System.out.println("║  6. Salir                                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1 -> verMisPrestamos();
            case 2 -> verDetallePrestamo();
            case 3 -> verHistorialPagos();
            case 4 -> verMisDatos();
            case 5 -> {
                authServicio.cerrarSesion();
                System.out.println("✓ Sesión cerrada");
            }
            case 6 -> {
                return false;
            }
            default -> System.out.println("⚠ Opción no válida");
        }
        
        return true;
    }
    
    private void verMisPrestamos() {
        Cliente cliente = authServicio.getClienteActual();
        List<Prestamo> prestamos = prestamoServicio.listarPorCliente(cliente.getId());
        
        if (prestamos.isEmpty()) {
            System.out.println("\nNo tiene préstamos registrados");
            return;
        }
        
        System.out.println("\n═══════════════════ MIS PRÉSTAMOS ═══════════════════");
        prestamos.forEach(prestamoServicio::mostrarPrestamo);
        
        double totalPendiente = prestamos.stream()
            .mapToDouble(Prestamo::getSaldoPendiente)
            .sum();
        
        System.out.printf("\nTotal préstamos: %d%n", prestamos.size());
        System.out.printf("Saldo total pendiente: $%.2f%n", totalPendiente);
    }
    
    private void verDetallePrestamo() {
        System.out.print("ID del préstamo: ");
        int prestamoId = leerOpcion();
        
        Prestamo prestamo = prestamoServicio.buscarPorId(prestamoId);
        
        if (prestamo == null) {
            System.out.println("✗ Préstamo no encontrado");
            return;
        }
        
        if (prestamo.getClienteId() != authServicio.getClienteActual().getId()) {
            System.out.println("⚠ No tiene permisos para ver este préstamo");
            return;
        }
        
        prestamoServicio.mostrarPrestamo(prestamo);
    }
    
    private void verHistorialPagos() {
        System.out.print("ID del préstamo: ");
        int prestamoId = leerOpcion();
        
        Prestamo prestamo = prestamoServicio.buscarPorId(prestamoId);
        
        if (prestamo == null) {
            System.out.println("✗ Préstamo no encontrado");
            return;
        }
        
        if (prestamo.getClienteId() != authServicio.getClienteActual().getId()) {
            System.out.println("⚠ No tiene permisos para ver este préstamo");
            return;
        }
        
        prestamoServicio.mostrarPrestamo(prestamo);
        pagoServicio.mostrarHistorialPagos(prestamoId);
    }
    
    private void verMisDatos() {
        Cliente cliente = authServicio.getClienteActual();
        ClienteServicio clienteServicio = new ClienteServicio();
        
        System.out.println("\n═══════════════════ MIS DATOS ═══════════════════");
        clienteServicio.mostrarCliente(cliente);
    }
    
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}