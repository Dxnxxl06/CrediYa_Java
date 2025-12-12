package com.crediya.utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FechaUtil {
    
    private static final DateTimeFormatter FORMATO_FECHA = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private static final DateTimeFormatter FORMATO_ISO = 
        DateTimeFormatter.ISO_LOCAL_DATE;
    
    public static LocalDate parsearFecha(String fecha) {
        try {
            return LocalDate.parse(fecha, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(fecha, FORMATO_ISO);
            } catch (DateTimeParseException ex) {
                System.err.println("Error al parsear fecha: " + fecha);
                return null;
            }
        }
    }
    
    public static String formatearFecha(LocalDate fecha) {
        if (fecha == null) return "";
        return fecha.format(FORMATO_FECHA);
    }
    
    public static String formatearFechaISO(LocalDate fecha) {
        if (fecha == null) return "";
        return fecha.format(FORMATO_ISO);
    }
    
    public static boolean esFechaFutura(LocalDate fecha) {
        return fecha.isAfter(LocalDate.now());
    }
    
    public static boolean esFechaPasada(LocalDate fecha) {
        return fecha.isBefore(LocalDate.now());
    }
    
    public static boolean esFechaValida(String fecha) {
        return parsearFecha(fecha) != null;
    }
    
    public static LocalDate obtenerFechaActual() {
        return LocalDate.now();
    }
    
    public static int obtenerDiaDelMes() {
        return LocalDate.now().getDayOfMonth();
    }
    
    public static int obtenerMes() {
        return LocalDate.now().getMonthValue();
    }
    
    public static int obtenerAnio() {
        return LocalDate.now().getYear();
    }
}