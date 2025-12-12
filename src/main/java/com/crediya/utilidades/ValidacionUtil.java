package com.crediya.utilidades;

import java.util.regex.Pattern;

public class ValidacionUtil {
    
    private static final Pattern PATTERN_EMAIL = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private static final Pattern PATTERN_TELEFONO = 
        Pattern.compile("^[0-9]{7,15}$");
    
    private static final Pattern PATTERN_DOCUMENTO = 
        Pattern.compile("^[0-9]{6,15}$");
    
    public static boolean esEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return PATTERN_EMAIL.matcher(email.trim()).matches();
    }
    
    public static boolean esTelefonoValido(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        String telefonoLimpio = telefono.replaceAll("[\\s-]", "");
        return PATTERN_TELEFONO.matcher(telefonoLimpio).matches();
    }
    
    public static boolean esDocumentoValido(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return false;
        }
        return PATTERN_DOCUMENTO.matcher(documento.trim()).matches();
    }
    
    public static boolean esMontoValido(double monto) {
        return monto > 0 && monto <= 1000000000;
    }
    
    public static boolean esInteresValido(double interes) {
        return interes >= 0 && interes <= 100;
    }
    
    public static boolean esCuotasValido(int cuotas) {
        return cuotas > 0 && cuotas <= 360;
    }
    
    public static boolean esDiaDelMesValido(int dia) {
        return dia >= 1 && dia <= 31;
    }
    
    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
    
    public static boolean esTextoValido(String texto, int longitudMinima) {
        return esTextoValido(texto) && texto.trim().length() >= longitudMinima;
    }
    
    public static boolean esTextoValido(String texto, int longitudMinima, int longitudMaxima) {
        if (!esTextoValido(texto)) return false;
        int longitud = texto.trim().length();
        return longitud >= longitudMinima && longitud <= longitudMaxima;
    }
    
    public static String limpiarTexto(String texto) {
        if (texto == null) return "";
        return texto.trim();
    }
}