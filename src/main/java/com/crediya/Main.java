package com.crediya;

import com.crediya.dao.ConexionDB;
import com.crediya.vista.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("       Iniciando Sistema CrediYa - Gestión de Préstamos    ");
        System.out.println("════════════════════════════════════════════════════════════");
        
        // Verificar conexión a la base de datos
        if (!ConexionDB.verificarConexion()) {
            System.err.println("\n✗ ERROR: No se pudo conectar a la base de datos");
            System.err.println("Verifica que MySQL esté ejecutándose y que la base de datos 'crediya_db' exista");
            System.err.println("\nPara crear la base de datos, ejecuta el script: crediya_db.sql");
            return;
        }
        
        System.out.println("✓ Conexión a base de datos exitosa\n");
        
        // Iniciar la aplicación
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.iniciar();
        
        // Cerrar conexión al finalizar
        ConexionDB.cerrarConexion();
        
        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("       Sistema CrediYa cerrado correctamente               ");
        System.out.println("════════════════════════════════════════════════════════════");
    }
}