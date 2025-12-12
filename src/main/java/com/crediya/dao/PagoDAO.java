package com.crediya.dao;

import com.crediya.modelo.Pago;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {
    
    public boolean crear(Pago pago) {
        String sql = "INSERT INTO pagos (prestamo_id, monto, fecha_pago, metodo_pago, " +
                    "referencia, observaciones, registrado_por) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, pago.getPrestamoId());
            stmt.setDouble(2, pago.getMonto());
            stmt.setDate(3, Date.valueOf(pago.getFechaPago()));
            stmt.setString(4, pago.getMetodoPago());
            stmt.setString(5, pago.getReferencia());
            stmt.setString(6, pago.getObservaciones());
            stmt.setObject(7, pago.getRegistradoPor());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        pago.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al crear pago: " + e.getMessage());
        }
        return false;
    }
    
    public Pago buscarPorId(int id) {
        String sql = "SELECT pg.*, c.nombre AS cliente_nombre, e.nombre AS registrado_por_nombre " +
                    "FROM pagos pg " +
                    "INNER JOIN prestamos p ON pg.prestamo_id = p.id " +
                    "INNER JOIN clientes c ON p.cliente_id = c.id " +
                    "LEFT JOIN empleados e ON pg.registrado_por = e.id " +
                    "WHERE pg.id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerPago(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar pago: " + e.getMessage());
        }
        return null;
    }
    
    public List<Pago> listarPorPrestamo(int prestamoId) {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT pg.*, c.nombre AS cliente_nombre, e.nombre AS registrado_por_nombre " +
                    "FROM pagos pg " +
                    "INNER JOIN prestamos p ON pg.prestamo_id = p.id " +
                    "INNER JOIN clientes c ON p.cliente_id = c.id " +
                    "LEFT JOIN empleados e ON pg.registrado_por = e.id " +
                    "WHERE pg.prestamo_id = ? ORDER BY pg.fecha_pago DESC";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, prestamoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                pagos.add(extraerPago(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pagos por préstamo: " + e.getMessage());
        }
        return pagos;
    }
    
    public List<Pago> listarTodos() {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT pg.*, c.nombre AS cliente_nombre, e.nombre AS registrado_por_nombre " +
                    "FROM pagos pg " +
                    "INNER JOIN prestamos p ON pg.prestamo_id = p.id " +
                    "INNER JOIN clientes c ON p.cliente_id = c.id " +
                    "LEFT JOIN empleados e ON pg.registrado_por = e.id " +
                    "ORDER BY pg.fecha_pago DESC";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                pagos.add(extraerPago(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar todos los pagos: " + e.getMessage());
        }
        return pagos;
    }
    
    private Pago extraerPago(ResultSet rs) throws SQLException {
        Pago pago = new Pago();
        pago.setId(rs.getInt("id"));
        pago.setPrestamoId(rs.getInt("prestamo_id"));
        pago.setMonto(rs.getDouble("monto"));
        pago.setMetodoPago(rs.getString("metodo_pago"));
        pago.setReferencia(rs.getString("referencia"));
        pago.setObservaciones(rs.getString("observaciones"));
        
        Date fechaPago = rs.getDate("fecha_pago");
        if (fechaPago != null) {
            pago.setFechaPago(fechaPago.toLocalDate());
        }
        
        int registradoPor = rs.getInt("registrado_por");
        if (!rs.wasNull()) {
            pago.setRegistradoPor(registradoPor);
        }
        
        pago.setClienteNombre(rs.getString("cliente_nombre"));
        pago.setRegistradoPorNombre(rs.getString("registrado_por_nombre"));
        
        return pago;
    }
}