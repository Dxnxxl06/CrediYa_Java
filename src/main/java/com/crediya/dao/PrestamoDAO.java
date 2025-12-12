package com.crediya.dao;

import com.crediya.modelo.Prestamo;
import com.crediya.modelo.enums.EstadoPrestamo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    
    public boolean crear(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (cliente_id, empleado_id, monto, interes, monto_total, " +
                    "cuotas, valor_cuota, saldo_pendiente, fecha_inicio, fecha_corte_pago, estado, " +
                    "modificado_por) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, prestamo.getClienteId());
            stmt.setInt(2, prestamo.getEmpleadoId());
            stmt.setDouble(3, prestamo.getMonto());
            stmt.setDouble(4, prestamo.getInteres());
            stmt.setDouble(5, prestamo.getMontoTotal());
            stmt.setInt(6, prestamo.getCuotas());
            stmt.setDouble(7, prestamo.getValorCuota());
            stmt.setDouble(8, prestamo.getSaldoPendiente());
            stmt.setDate(9, Date.valueOf(prestamo.getFechaInicio()));
            stmt.setInt(10, prestamo.getFechaCortePago());
            stmt.setString(11, prestamo.getEstado().name());
            stmt.setObject(12, prestamo.getModificadoPor());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        prestamo.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al crear préstamo: " + e.getMessage());
        }
        return false;
    }
    
    public Prestamo buscarPorId(int id) {
        String sql = "SELECT p.*, c.nombre AS cliente_nombre, e.nombre AS empleado_nombre " +
                    "FROM prestamos p " +
                    "INNER JOIN clientes c ON p.cliente_id = c.id " +
                    "INNER JOIN empleados e ON p.empleado_id = e.id " +
                    "WHERE p.id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerPrestamo(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar préstamo: " + e.getMessage());
        }
        return null;
    }
    
    public List<Prestamo> listarTodos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS cliente_nombre, e.nombre AS empleado_nombre " +
                    "FROM prestamos p " +
                    "INNER JOIN clientes c ON p.cliente_id = c.id " +
                    "INNER JOIN empleados e ON p.empleado_id = e.id " +
                    "ORDER BY p.fecha_inicio DESC";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                prestamos.add(extraerPrestamo(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
        }
        return prestamos;
    }
    
    public List<Prestamo> listarPorCliente(int clienteId) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS cliente_nombre, e.nombre AS empleado_nombre " +
                    "FROM prestamos p " +
                    "INNER JOIN clientes c ON p.cliente_id = c.id " +
                    "INNER JOIN empleados e ON p.empleado_id = e.id " +
                    "WHERE p.cliente_id = ? ORDER BY p.fecha_inicio DESC";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                prestamos.add(extraerPrestamo(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar préstamos por cliente: " + e.getMessage());
        }
        return prestamos;
    }
    
    public List<Prestamo> listarPorEstado(EstadoPrestamo estado) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS cliente_nombre, e.nombre AS empleado_nombre " +
                    "FROM prestamos p " +
                    "INNER JOIN clientes c ON p.cliente_id = c.id " +
                    "INNER JOIN empleados e ON p.empleado_id = e.id " +
                    "WHERE p.estado = ? ORDER BY p.fecha_inicio DESC";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, estado.name());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                prestamos.add(extraerPrestamo(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar préstamos por estado: " + e.getMessage());
        }
        return prestamos;
    }
    
    public boolean actualizar(Prestamo prestamo) {
        String sql = "UPDATE prestamos SET saldo_pendiente = ?, estado = ?, modificado_por = ? " +
                    "WHERE id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, prestamo.getSaldoPendiente());
            stmt.setString(2, prestamo.getEstado().name());
            stmt.setObject(3, prestamo.getModificadoPor());
            stmt.setInt(4, prestamo.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar préstamo: " + e.getMessage());
        }
        return false;
    }
    
    public boolean actualizarEstado(int prestamoId, EstadoPrestamo estado) {
        String sql = "UPDATE prestamos SET estado = ? WHERE id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, estado.name());
            stmt.setInt(2, prestamoId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado del préstamo: " + e.getMessage());
        }
        return false;
    }
    
    private Prestamo extraerPrestamo(ResultSet rs) throws SQLException {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(rs.getInt("id"));
        prestamo.setClienteId(rs.getInt("cliente_id"));
        prestamo.setEmpleadoId(rs.getInt("empleado_id"));
        prestamo.setMonto(rs.getDouble("monto"));
        prestamo.setInteres(rs.getDouble("interes"));
        prestamo.setMontoTotal(rs.getDouble("monto_total"));
        prestamo.setCuotas(rs.getInt("cuotas"));
        prestamo.setValorCuota(rs.getDouble("valor_cuota"));
        prestamo.setSaldoPendiente(rs.getDouble("saldo_pendiente"));
        prestamo.setFechaCortePago(rs.getInt("fecha_corte_pago"));
        prestamo.setEstado(EstadoPrestamo.valueOf(rs.getString("estado")));
        
        Date fechaInicio = rs.getDate("fecha_inicio");
        if (fechaInicio != null) {
            prestamo.setFechaInicio(fechaInicio.toLocalDate());
        }
        
        prestamo.setClienteNombre(rs.getString("cliente_nombre"));
        prestamo.setEmpleadoNombre(rs.getString("empleado_nombre"));
        
        return prestamo;
    }
}