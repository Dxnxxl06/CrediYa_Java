package com.crediya.vista;

import com.crediya.servicio.ReporteServicio;
import com.crediya.utilidades.FechaUtil;
import java.time.LocalDate;
import java.util.Scanner;

public class MenuReportes {
    private Scanner scanner;
    private ReporteServicio reporteServicio;
    
    public MenuReportes() {
        this.scanner = new Scanner(System.in);
        this.reporteServicio = new ReporteServicio();
    }
    
    public void mostrar() {
        System.out.println("\n═══════════════════ MENÚ DE REPORTES ═══════════════════");
        System.out.println("1. Reporte de Préstamos Activos");
        System.out.println("2. Reporte de Préstamos Vencidos");
        System.out.println("3. Reporte de Clientes Morosos");
        System.out.println("4. Reporte de Pagos por Período");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1 -> reporteServicio.reportePrestamosActivos();
            case 2 -> reporteServicio.reportePrestamosVencidos();
            case 3 -> reporteServicio.reporteClientesMorosos();
            case 4 -> reportePagosPorPeriodo();
        }
    }
    
    private void reportePagosPorPeriodo() {
        System.out.println("\n─── REPORTE DE PAGOS POR PERÍODO ───");
        System.out.print("Fecha inicio (dd/MM/yyyy): ");
        String fechaInicioStr = scanner.nextLine().trim();
        
        System.out.print("Fecha fin (dd/MM/yyyy): ");
        String fechaFinStr = scanner.nextLine().trim();
        
        LocalDate fechaInicio = FechaUtil.parsearFecha(fechaInicioStr);
        LocalDate fechaFin = FechaUtil.parsearFecha(fechaFinStr);
        
        if (fechaInicio == null || fechaFin == null) {
            System.out.println("✗ Formato de fecha inválido");
            return;
        }
        
        if (fechaInicio.isAfter(fechaFin)) {
            System.out.println("✗ La fecha de inicio debe ser anterior a la fecha fin");
            return;
        }
        
        reporteServicio.reportePagosPorPeriodo(fechaInicio, fechaFin);
    }
    
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}