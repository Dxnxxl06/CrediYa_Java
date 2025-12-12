package com.crediya.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/crediya_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "admin";
    
    private static Connection conexion = null;
    
    private ConexionDB() {}
    
    public static Connection obtenerConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("✓ Conexión a base de datos establecida");
            }
            return conexion;
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver MySQL no encontrado");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos");
            e.printStackTrace();
            return null;
        }
    }
    
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("✓ Conexión a base de datos cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión");
            e.printStackTrace();
        }
    }
    
    public static boolean verificarConexion() {
        try {
            Connection conn = obtenerConexion();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
