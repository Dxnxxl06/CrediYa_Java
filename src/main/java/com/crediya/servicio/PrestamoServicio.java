package com.crediya.servicio;

import com.crediya.dao.PrestamoDAO;
import com.crediya.modelo.Prestamo;
import com.crediya.modelo.enums.EstadoPrestamo;
import com.crediya.utilidades.ArchivoUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PrestamoServicio {
    private PrestamoDAO prestamoDAO;
    
    public PrestamoServicio() {
        this.prestamoDAO = new PrestamoDAO();
    }
    
    public boolean crearPrestamo(Prestamo prestamo) {
        prestamo.calcularMontos();
        
        if (prestamoDAO.crear(prestamo)) {
            guardarEnArchivo(prestamo);
            return true;
        }
        return false;
    }
    
    public Prestamo buscarPorId(int id) {
        return prestamoDAO.buscarPorId(id);
    }
    
    public List<Prestamo> listarTodos() {
        return prestamoDAO.listarTodos();
    }
    
    public List<Prestamo> listarPorCliente(int clienteId) {
        return prestamoDAO.listarPorCliente(clienteId);
    }
    
    public List<Prestamo> listarActivos() {
        return prestamoDAO.listarPorEstado(EstadoPrestamo.ACTIVO);
    }
    
    public List<Prestamo> listarVencidos() {
        List<Prestamo> activos = listarActivos();
        LocalDate hoy = LocalDate.now();
        int diaActual = hoy.getDayOfMonth();
        
        return activos.stream()
            .filter(p -> diaActual > p.getFechaCortePago())
            .collect(Collectors.toList());
    }
    
    public boolean actualizarEstado(int prestamoId, EstadoPrestamo estado) {
        return prestamoDAO.actualizarEstado(prestamoId, estado);
    }
    
    public boolean actualizar(Prestamo prestamo) {
        if (prestamoDAO.actualizar(prestamo)) {
            actualizarArchivo();
            return true;
        }
        return false;
    }
    
    public void verificarYActualizarVencidos() {
        List<Prestamo> activos = listarActivos();
        LocalDate hoy = LocalDate.now();
        int diaActual = hoy.getDayOfMonth();
        
        for (Prestamo p : activos) {
            if (diaActual > p.getFechaCortePago() && p.getSaldoPendiente() > 0) {
                p.setEstado(EstadoPrestamo.VENCIDO);
                prestamoDAO.actualizar(p);
            }
        }
    }
    
    private void guardarEnArchivo(Prestamo prestamo) {
        String linea = String.format("%d,%d,%d,%.2f,%.2f,%.2f,%d,%.2f,%s",
            prestamo.getId(),
            prestamo.getClienteId(),
            prestamo.getEmpleadoId(),
            prestamo.getMonto(),
            prestamo.getInteres(),
            prestamo.getMontoTotal(),
            prestamo.getCuotas(),
            prestamo.getSaldoPendiente(),
            prestamo.getEstado()
        );
        ArchivoUtil.escribirLinea("prestamos.txt", linea);
    }
    
    private void actualizarArchivo() {
        List<Prestamo> prestamos = listarTodos();
        ArchivoUtil.limpiarArchivo("prestamos.txt");
        prestamos.forEach(this::guardarEnArchivo);
    }
    
    public void mostrarPrestamo(Prestamo p) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.printf("║ ID Préstamo: %-46d ║%n", p.getId());
        System.out.printf("║ Cliente: %-49s ║%n", p.getClienteNombre());
        System.out.printf("║ Empleado: %-48s ║%n", p.getEmpleadoNombre());
        System.out.printf("║ Monto Original: $%-41.2f ║%n", p.getMonto());
        System.out.printf("║ Interés: %.2f%% %49s║%n", p.getInteres(), "");
        System.out.printf("║ Monto Total: $%-44.2f ║%n", p.getMontoTotal());
        System.out.printf("║ Cuotas: %-50d ║%n", p.getCuotas());
        System.out.printf("║ Valor Cuota: $%-44.2f ║%n", p.getValorCuota());
        System.out.printf("║ Saldo Pendiente: $%-40.2f ║%n", p.getSaldoPendiente());
        System.out.printf("║ Fecha Inicio: %-44s ║%n", p.getFechaInicio());
        System.out.printf("║ Día de Pago: %-45d ║%n", p.getFechaCortePago());
        System.out.printf("║ Estado: %-50s ║%n", p.getEstado());
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}