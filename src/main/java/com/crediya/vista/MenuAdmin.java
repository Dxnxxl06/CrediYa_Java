package com.crediya.vista;

import com.crediya.servicio.*;
import com.crediya.modelo.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuAdmin {
    private Scanner scanner;
    private AutenticacionServicio authServicio;
    private EmpleadoServicio empleadoServicio;
    private ClienteServicio clienteServicio;
    private PrestamoServicio prestamoServicio;
    private PagoServicio pagoServicio;
    private ReporteServicio reporteServicio;
    
    public MenuAdmin(AutenticacionServicio authServicio) {
        this.scanner = new Scanner(System.in);
        this.authServicio = authServicio;
        this.empleadoServicio = new EmpleadoServicio();
        this.clienteServicio = new ClienteServicio();
        this.prestamoServicio = new PrestamoServicio();
        this.pagoServicio = new PagoServicio();
        this.reporteServicio = new ReporteServicio();
    }
    
    public boolean mostrar() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   MENÚ ADMINISTRADOR                       ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Gestión de Empleados                                  ║");
        System.out.println("║  2. Gestión de Clientes                                   ║");
        System.out.println("║  3. Gestión de Préstamos                                  ║");
        System.out.println("║  4. Gestión de Pagos                                      ║");
        System.out.println("║  5. Reportes                                              ║");
        System.out.println("║  6. Crear Cuenta Admin/Empleado                           ║");
        System.out.println("║  7. Cerrar Sesión                                         ║");
        System.out.println("║  8. Salir                                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1 -> menuEmpleados();
            case 2 -> menuClientes();
            case 3 -> menuPrestamos();
            case 4 -> menuPagos();
            case 5 -> menuReportes();
            case 6 -> crearCuentaUsuario();
            case 7 -> {
                authServicio.cerrarSesion();
                System.out.println("✓ Sesión cerrada");
            }
            case 8 -> {
                return false;
            }
            default -> System.out.println("⚠ Opción no válida");
        }
        
        return true;
    }
    
    private void menuEmpleados() {
        System.out.println("\n═══════════════════ GESTIÓN DE EMPLEADOS ═══════════════════");
        System.out.println("1. Registrar Empleado");
        System.out.println("2. Buscar Empleado");
        System.out.println("3. Listar Todos los Empleados");
        System.out.println("4. Actualizar Empleado");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1 -> registrarEmpleado();
            case 2 -> buscarEmpleado();
            case 3 -> listarEmpleados();
            case 4 -> actualizarEmpleado();
        }
    }
    
    private void registrarEmpleado() {
        System.out.println("\n─── REGISTRO DE EMPLEADO ───");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Documento: ");
        String documento = scanner.nextLine().trim();
        
        System.out.print("Rol: ");
        String rol = scanner.nextLine().trim();
        
        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();
        
        System.out.print("Salario: ");
        double salario = leerDouble();
        
        System.out.print("Usuario para login: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();
        
        Empleado empleado = new Empleado(nombre, documento, rol, correo, salario);
        empleado.setFechaContratacion(LocalDate.now());
        
        int empleadoActualId = authServicio.getEmpleadoActual().getId();
        
        if (empleadoServicio.registrarEmpleado(empleado, username, password, false, empleadoActualId)) {
            System.out.println("✓ Empleado registrado exitosamente");
        } else {
            System.out.println("✗ Error al registrar empleado");
        }
    }
    
    private void buscarEmpleado() {
        System.out.println("\n─── BUSCAR EMPLEADO ───");
        System.out.println("1. Por ID");
        System.out.println("2. Por Documento");
        System.out.println("3. Por Nombre");
        System.out.print("Opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1 -> {
                System.out.print("ID: ");
                int id = leerOpcion();
                Empleado emp = empleadoServicio.buscarPorId(id);
                if (emp != null) {
                    empleadoServicio.mostrarEmpleado(emp);
                } else {
                    System.out.println("✗ Empleado no encontrado");
                }
            }
            case 2 -> {
                System.out.print("Documento: ");
                String doc = scanner.nextLine().trim();
                Empleado emp = empleadoServicio.buscarPorDocumento(doc);
                if (emp != null) {
                    empleadoServicio.mostrarEmpleado(emp);
                } else {
                    System.out.println("✗ Empleado no encontrado");
                }
            }
            case 3 -> {
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine().trim();
                List<Empleado> empleados = empleadoServicio.buscarPorNombre(nombre);
                if (empleados.isEmpty()) {
                    System.out.println("✗ No se encontraron empleados");
                } else {
                    empleados.forEach(empleadoServicio::mostrarEmpleado);
                }
            }
        }
    }
    
    private void listarEmpleados() {
        List<Empleado> empleados = empleadoServicio.listarTodos();
        
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados");
            return;
        }
        
        System.out.println("\n═══════════════════ LISTA DE EMPLEADOS ═══════════════════");
        empleados.forEach(empleadoServicio::mostrarEmpleado);
        System.out.printf("\nTotal: %d empleados%n", empleados.size());
    }
    
    private void actualizarEmpleado() {
        System.out.print("ID del empleado a actualizar: ");
        int id = leerOpcion();
        
        Empleado emp = empleadoServicio.buscarPorId(id);
        if (emp == null) {
            System.out.println("✗ Empleado no encontrado");
            return;
        }
        
        empleadoServicio.mostrarEmpleado(emp);
        
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = scanner.nextLine().trim();
        if (!nombre.isEmpty()) emp.setNombre(nombre);
        
        System.out.print("Nuevo rol (Enter para mantener): ");
        String rol = scanner.nextLine().trim();
        if (!rol.isEmpty()) emp.setRol(rol);
        
        System.out.print("Nuevo correo (Enter para mantener): ");
        String correo = scanner.nextLine().trim();
        if (!correo.isEmpty()) emp.setCorreo(correo);
        
        System.out.print("Nuevo salario (0 para mantener): ");
        double salario = leerDouble();
        if (salario > 0) emp.setSalario(salario);
        
        emp.setModificadoPor(authServicio.getEmpleadoActual().getId());
        
        if (empleadoServicio.actualizar(emp)) {
            System.out.println("✓ Empleado actualizado exitosamente");
        } else {
            System.out.println("✗ Error al actualizar empleado");
        }
    }
    
    private void menuClientes() {
        System.out.println("\n═══════════════════ GESTIÓN DE CLIENTES ═══════════════════");
        System.out.println("1. Registrar Cliente");
        System.out.println("2. Buscar Cliente");
        System.out.println("3. Listar Todos los Clientes");
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
        
        int empleadoActualId = authServicio.getEmpleadoActual().getId();
        
        if (clienteServicio.registrarCliente(cliente, username, password, empleadoActualId)) {
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
    
    private void menuPrestamos() {
        MenuEmpleado menuEmp = new MenuEmpleado(authServicio);
        menuEmp.menuPrestamos();
    }
    
    private void menuPagos() {
        MenuEmpleado menuEmp = new MenuEmpleado(authServicio);
        menuEmp.menuPagos();
    }
    
    private void menuReportes() {
        MenuReportes menuRep = new MenuReportes();
        menuRep.mostrar();
    }
    
    private void crearCuentaUsuario() {
        System.out.println("\n─── CREAR CUENTA USUARIO ───");
        System.out.println("1. Crear cuenta Admin");
        System.out.println("2. Crear cuenta Empleado");
        System.out.print("Opción: ");
        
        int opcion = leerOpcion();
        boolean esAdmin = (opcion == 1);
        
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Documento: ");
        String documento = scanner.nextLine().trim();
        
        System.out.print("Rol: ");
        String rol = scanner.nextLine().trim();
        
        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();
        
        System.out.print("Salario: ");
        double salario = leerDouble();
        
        System.out.print("Usuario: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();
        
        Empleado empleado = new Empleado(nombre, documento, rol, correo, salario);
        empleado.setFechaContratacion(LocalDate.now());
        
        int creadoPor = authServicio.getEmpleadoActual().getId();
        
        if (empleadoServicio.registrarEmpleado(empleado, username, password, esAdmin, creadoPor)) {
            System.out.printf("✓ Cuenta %s creada exitosamente%n", esAdmin ? "Admin" : "Empleado");
        } else {
            System.out.println("✗ Error al crear cuenta");
        }
    }
    
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private double leerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}