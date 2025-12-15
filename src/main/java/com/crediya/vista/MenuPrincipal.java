package com.crediya.vista;

import com.crediya.servicio.AutenticacionServicio;
import com.crediya.modelo.enums.TipoUsuario;
import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner;
    private AutenticacionServicio authServicio;
    private MenuAdmin menuAdmin;
    private MenuEmpleado menuEmpleado;
    private MenuCliente menuCliente;
    
    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.authServicio = new AutenticacionServicio();
        this.menuAdmin = new MenuAdmin(authServicio);
        this.menuEmpleado = new MenuEmpleado(authServicio);
        this.menuCliente = new MenuCliente(authServicio);
    }
    
    public void iniciar() {
        mostrarBanner();
        
        boolean continuar = true;
        while (continuar) {
            if (!authServicio.estaAutenticado()) {
                continuar = mostrarMenuLogin();
            } else {
                continuar = redirigirMenuPorRol();
            }
        }
        
        System.out.println("\n¡Gracias por usar CrediYa! Hasta pronto.");
        scanner.close();
    }
    
    private void mostrarBanner() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                            ║");
        System.out.println("║                 SISTEMA CREDIYA S.A.S.                     ║");
        System.out.println("║          Sistema de Gestión de Préstamos y Cobros         ║");
        System.out.println("║                                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
    
    private boolean mostrarMenuLogin() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    MENÚ PRINCIPAL                          ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Iniciar Sesión                                         ║");
        System.out.println("║  2. Registrarse (Cliente)                                  ║");
        System.out.println("║  3. Salir                                                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1 -> iniciarSesion();
            case 2 -> registrarCliente();
            case 3 -> {
                return false;
            }
            default -> System.out.println("⚠ Opción no válida");
        }
        
        return true;
    }
    
    private void iniciarSesion() {
        System.out.println("\n═══════════════════ INICIO DE SESIÓN ═══════════════════");
        System.out.print("Usuario: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();
        
        if (authServicio.iniciarSesion(username, password)) {
            System.out.println("✓ Inicio de sesión exitoso");
            System.out.println("Bienvenido, " + authServicio.getUsuarioActual().getUsername());
        } else {
            System.out.println("✗ Usuario o contraseña incorrectos");
        }
    }
    
    private void registrarCliente() {
        System.out.println("\n═══════════════════ REGISTRO DE CLIENTE ═══════════════════");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Documento: ");
        String documento = scanner.nextLine().trim();
        
        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();
        
        System.out.print("Dirección (opcional): ");
        String direccion = scanner.nextLine().trim();
        
        System.out.print("Usuario: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();
        
        if (authServicio.registrarUsuario(username, password, TipoUsuario.CLIENTE, null)) {
            System.out.println("✓ Cliente registrado exitosamente");
            System.out.println("Ya puede iniciar sesión");
        } else {
            System.out.println("✗ Error al registrar cliente");
        }
    }
    
    private boolean redirigirMenuPorRol() {
        if (authServicio.esAdmin()) {
            return menuAdmin.mostrar();
        } else if (authServicio.esEmpleado()) {
            return menuEmpleado.mostrar();
        } else if (authServicio.esCliente()) {
            return menuCliente.mostrar();
        }
        
        authServicio.cerrarSesion();
        return true;
    }
    
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    protected Scanner getScanner() {
        return scanner;
    }
}