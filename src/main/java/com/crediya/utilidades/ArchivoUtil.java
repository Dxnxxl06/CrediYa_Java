package com.crediya.utilidades;

import java.io.*;
import java.nio.file.*;

public class ArchivoUtil {
    private static final String RUTA_DATOS = "src/main/resources/datos/";
    
    static {
        try {
            Files.createDirectories(Paths.get(RUTA_DATOS));
        } catch (IOException e) {
            System.err.println("Error al crear directorio de datos: " + e.getMessage());
        }
    }
    
    public static void escribirLinea(String nombreArchivo, String linea) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(RUTA_DATOS + nombreArchivo, true))) {
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en archivo: " + e.getMessage());
        }
    }
    
    public static void limpiarArchivo(String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(RUTA_DATOS + nombreArchivo, false))) {
            // Solo abre y cierra el archivo para limpiarlo
        } catch (IOException e) {
            System.err.println("Error al limpiar archivo: " + e.getMessage());
        }
    }
    
    public static String leerTodo(String nombreArchivo) {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(RUTA_DATOS + nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        }
        return contenido.toString();
    }
    
    public static boolean existeArchivo(String nombreArchivo) {
        return Files.exists(Paths.get(RUTA_DATOS + nombreArchivo));
    }
}