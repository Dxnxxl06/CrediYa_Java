package com.crediya.vista;

import com.crediya.servicio.*;
import com.crediya.modelo.*;
import com.crediya.modelo.enums.EstadoPrestamo;

import java.util.List;
import java.util.Scanner;

public class MenuEmpleado {
    protected Scanner scanner;
    protected AutenticacionServicio authServicio;
    protected ClienteServicio clienteServicio;
    protected PrestamoServicio prestamoServicio;
    protected PagoServicio pagoServicio;
    protected ReporteServicio reporteServicio;
    
    public MenuEmpleado(AutenticacionServicio authServicio) {
        this.scanner = new Scanner(System.in);
        this.authServicio = authServicio;
        this.clienteServicio = new ClienteServicio();
        this.prestamoServicio = new PrestamoServicio();
        this.pagoServicio = new PagoServicio();
        this.reporteServicio = new ReporteServicio();
    }
    
    public boolean mostrar() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   MENÚ EMPLEADO                            ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Gestión de Clientes                                   ║");
        System.out.println("║  2. Gestión de Préstamos                                  ║");
        System.out.println("║  3. Gestión de Pagos                                      ║");
        System.out.println("║  4. Reportes                                              ║");
        System.out.println("║  5. Cerrar Sesión                                         ║");
        System.out.println("║  6. Salir                                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1 -> menuClientes();
            case 2 -> menuPrestamos();
            case 3 -> menuPagos();
            case 4 -> menuReportes();
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
    
    protected void menuClientes() {
        System.out.println("\n═══════════════════ GESTIÓN DE CLIENTES ═══════════════════");
        System.out.println("1. Registrar Cliente");
        System.out.println("2. Buscar Cliente");
        System.out.println("3. Listar Clientes");
        System.out.println("4. Ver Préstamos de Cliente");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1 -> registrarCliente();
            case 2 -> buscarCliente();
            case 3 -> listarClientes();
            case 4 -> verPrestamosCliente();
        }
    }
    
    private void registrarCliente() {
        System.out.println("\n─── REGISTRO DE CLIENTE ───");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Documento: ");
        String documento = scanner.nextLine().trim();
        
        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();
        
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine().trim();
        
        System.out.print("Usuario para login: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();
        
        Cliente cliente = new Cliente(nombre, documento, correo, telefono);
        cliente.setDireccion(direccion);
        
        int empleadoId = authServicio.getEmpleadoActual().getId();
        
        if (clienteServicio.registrarCliente(cliente, username, password, empleadoId)) {
            System.out.println("✓ Cliente registrado exitosamente");
        } else {
            System.out.println("✗ Error al registrar cliente");
        }
    }
    
    private void buscarCliente() {
        System.out.println("\n─── BUSCAR CLIENTE ───");
        System.out.println("1. Por ID");
        System.out.println("2. Por Documento");
        System.out.println("3. Por Nombre");
        System.out.print("Opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1 -> {
                System.out.print("ID: ");
                int id = leerOpcion();
                Cliente cli = clienteServicio.buscarPorId(id);
                if (cli != null) {
                    clienteServicio.mostrarCliente(cli);
                } else {
                    System.out.println("✗ Cliente no encontrado");
                }
            }
            case 2 -> {
                System.out.print("Documento: ");
                String doc = scanner.nextLine().trim();
                Cliente cli = clienteServicio.buscarPorDocumento(doc);
                if (cli != null) {
                    clienteServicio.mostrarCliente(cli);
                } else {
                    System.out.println("✗ Cliente no encontrado");
                }
            }
            case 3 -> {
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine().trim();
                List<Cliente> clientes = clienteServicio.buscarPorNombre(nombre);
                if (clientes.isEmpty()) {
                    System.out.println("✗ No se encontraron clientes");
                } else {
                    clientes.forEach(clienteServicio::mostrarCliente);
                }
            }
        }
    }
    
    private void listarClientes() {
        List<Cliente> clientes = clienteServicio.listarTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
            return;
        }
        
        System.out.println("\n═══════════════════ LISTA DE CLIENTES ═══════════════════");
        clientes.forEach(clienteServicio::mostrarCliente);
        System.out.printf("\nTotal: %d clientes%n", clientes.size());
    }
    
    private void verPrestamosCliente() {
        System.out.print("ID del cliente: ");
        int clienteId = leerOpcion();
        
        Cliente cliente = clienteServicio.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("✗ Cliente no encontrado");
            return;
        }
        
        clienteServicio.mostrarCliente(cliente);
        
        List<Prestamo> prestamos = prestamoServicio.listarPorCliente(clienteId);
        
        if (prestamos.isEmpty()) {
            System.out.println("\nNo tiene préstamos registrados");
        } else {
            System.out.println("\n═══════════════ PRÉSTAMOS DEL CLIENTE ═══════════════");
            prestamos.forEach(prestamoServicio::mostrarPrestamo);
        }
    }
    
    protected void menuPrestamos() {
        System.out.println("\n═══════════════════ GESTIÓN DE PRÉSTAMOS ═══════════════════");
        System.out.println("1. Crear Préstamo");
        System.out.println("2. Buscar Préstamo");
        System.out.println("3. Listar Préstamos Activos");
        System.out.println("4. Listar Préstamos Vencidos");
        System.out.println("5. Cambiar Estado de Préstamo");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1 -> crearPrestamo();
            case 2 -> buscarPrestamo();
            case 3 -> listarPrestamosActivos();
            case 4 -> listarPrestamosVencidos();
            case 5 -> cambiarEstadoPrestamo();
        }
    }
    
    private void crearPrestamo() {
        System.out.println("\n─── CREAR PRÉSTAMO ───");
        System.out.print("ID del cliente: ");
        int clienteId = leerOpcion();
        
        Cliente cliente = clienteServicio.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("✗ Cliente no encontrado");
            return;
        }
        
        clienteServicio.mostrarCliente(cliente);
        
        System.out.print("Monto del préstamo: $");
        double monto = leerDouble();
        
        System.out.print("Interés (%): ");
        double interes = leerDouble();
        
        System.out.print("Número de cuotas: ");
        int cuotas = leerOpcion();
        
        System.out.print("Día de pago (1-31): ");
        int diaPago = leerOpcion();
        
        int empleadoId = authServicio.getEmpleadoActual().getId();
        
        Prestamo prestamo = new Prestamo(clienteId, empleadoId, monto, interes, cuotas, diaPago);
        prestamo.setModificadoPor(empleadoId);
        
        if (prestamoServicio.crearPrestamo(prestamo)) {
            System.out.println("\n✓ Préstamo creado exitosamente");
            prestamoServicio.mostrarPrestamo(prestamo);
        } else {
            System.out.println("✗ Error al crear préstamo");
        }
    }
    
    private void buscarPrestamo() {
        System.out.print("ID del préstamo: ");
        int id = leerOpcion();
        
        Prestamo prestamo = prestamoServicio.buscarPorId(id);
        if (prestamo != null) {
            prestamoServicio.mostrarPrestamo(prestamo);
        } else {
            System.out.println("✗ Préstamo no encontrado");
        }
    }
    
    private void listarPrestamosActivos() {
        List<Prestamo> activos = prestamoServicio.listarActivos();
        
        if (activos.isEmpty()) {
            System.out.println("No hay préstamos activos");
            return;
        }
        
        System.out.println("\n═══════════════ PRÉSTAMOS ACTIVOS ═══════════════");
        activos.forEach(prestamoServicio::mostrarPrestamo);
        System.out.printf("\nTotal: %d préstamos activos%n", activos.size());
    }
    
    private void listarPrestamosVencidos() {
        prestamoServicio.verificarYActualizarVencidos();
        
        List<Prestamo> vencidos = prestamoServicio.listarVencidos();
        
        if (vencidos.isEmpty()) {
            System.out.println("✓ No hay préstamos vencidos");
            return;
        }
        
        System.out.println("\n═══════════════ PRÉSTAMOS VENCIDOS ═══════════════");
        vencidos.forEach(prestamoServicio::mostrarPrestamo);
        System.out.printf("\n⚠ Total: %d préstamos vencidos%n", vencidos.size());
    }
    
    private void cambiarEstadoPrestamo() {
        System.out.print("ID del préstamo: ");
        int id = leerOpcion();
        
        Prestamo prestamo = prestamoServicio.buscarPorId(id);
        if (prestamo == null) {
            System.out.println("✗ Préstamo no encontrado");
            return;
        }
        
        prestamoServicio.mostrarPrestamo(prestamo);
        
        System.out.println("\nEstados disponibles:");
        System.out.println("1. ACTIVO");
        System.out.println("2. VENCIDO");
        System.out.println("3. PAGADO");
        System.out.print("Nuevo estado: ");
        
        int opcion = leerOpcion();
        EstadoPrestamo nuevoEstado = switch (opcion) {
            case 1 -> EstadoPrestamo.ACTIVO;
            case 2 -> EstadoPrestamo.VENCIDO;
            case 3 -> EstadoPrestamo.PAGADO;
            default -> null;
        };
        
        if (nuevoEstado != null) {
            if (prestamoServicio.actualizarEstado(id, nuevoEstado)) {
                System.out.println("✓ Estado actualizado exitosamente");
            } else {
                System.out.println("✗ Error al actualizar estado");
            }
        } else {
            System.out.println("⚠ Opción inválida");
        }
    }
    
    protected void menuPagos() {
        System.out.println("\n═══════════════════ GESTIÓN DE PAGOS ═══════════════════");
        System.out.println("1. Registrar Pago");
        System.out.println("2. Ver Historial de Pagos");
        System.out.println("3. Listar Todos los Pagos");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1 -> registrarPago();
            case 2 -> verHistorialPagos();
            case 3 -> listarTodosPagos();
        }
    }
    
    private void registrarPago() {
        System.out.println("\n─── REGISTRAR PAGO ───");
        System.out.print("ID del préstamo: ");
        int prestamoId = leerOpcion();
        
        Prestamo prestamo = prestamoServicio.buscarPorId(prestamoId);
        if (prestamo == null) {
            System.out.println("✗ Préstamo no encontrado");
            return;
        }
        
        prestamoServicio.mostrarPrestamo(prestamo);
        
        if (prestamo.getSaldoPendiente() <= 0) {
            System.out.println("⚠ Este préstamo ya está completamente pagado");
            return;
        }
        
        System.out.printf("Saldo pendiente: $%.2f%n", prestamo.getSaldoPendiente());
        System.out.print("Monto del pago: $");
        double monto = leerDouble();
        
        System.out.print("Método de pago (EFECTIVO/TARJETA/TRANSFERENCIA): ");
        String metodo = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Referencia (opcional): ");
        String referencia = scanner.nextLine().trim();
        
        System.out.print("Observaciones (opcional): ");
        String observaciones = scanner.nextLine().trim();
        
        Pago pago = new Pago(prestamoId, monto, metodo);
        pago.setReferencia(referencia.isEmpty() ? null : referencia);
        pago.setObservaciones(observaciones.isEmpty() ? null : observaciones);
        pago.setRegistradoPor(authServicio.getEmpleadoActual().getId());
        
        if (pagoServicio.registrarPago(pago)) {
            System.out.println("✓ Pago registrado exitosamente");
        } else {
            System.out.println("✗ Error al registrar pago");
        }
    }
    
    private void verHistorialPagos() {
        System.out.print("ID del préstamo: ");
        int prestamoId = leerOpcion();
        
        Prestamo prestamo = prestamoServicio.buscarPorId(prestamoId);
        if (prestamo == null) {
            System.out.println("✗ Préstamo no encontrado");
            return;
        }
        
        prestamoServicio.mostrarPrestamo(prestamo);
        pagoServicio.mostrarHistorialPagos(prestamoId);
    }
    
    private void listarTodosPagos() {
        List<Pago> pagos = pagoServicio.listarTodos();
        
        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados");
            return;
        }
        
        System.out.println("\n═══════════════ LISTA DE PAGOS ═══════════════");
        System.out.printf("%-8s %-30s %-12s %-15s%n", "ID", "Cliente", "Fecha", "Monto");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        for (Pago p : pagos) {
            System.out.printf("%-8d %-30s %-12s $%-14.2f%n",
                            p.getId(),
                            p.getClienteNombre(),
                            p.getFechaPago(),
                            p.getMonto());
        }
        
        System.out.printf("\nTotal: %d pagos registrados%n", pagos.size());
    }
    
    private void menuReportes() {
        MenuReportes menuRep = new MenuReportes();
        menuRep.mostrar();
    }
    
    protected int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    protected double leerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}