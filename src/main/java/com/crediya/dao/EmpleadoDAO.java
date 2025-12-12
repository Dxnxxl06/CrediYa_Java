package com.crediya.dao;

import com.crediya.modelo.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    
    public boolean crear(Empleado empleado) {
        String sql = "INSERT INTO empleados (usuario_id, nombre, documento, rol, correo, salario, " +
                    "fecha_contratacion, activo, modificado_por) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setObject(1, empleado.getUsuarioId());
            stmt.setString(2, empleado.getNombre());
            stmt.setString(3, empleado.getDocumento());
            stmt.setString(4, empleado.getRol());
            stmt.setString(5, empleado.getCorreo());
            stmt.setDouble(6, empleado.getSalario());
            stmt.setDate(7, Date.valueOf(empleado.getFechaContratacion()));
            stmt.setBoolean(8, empleado.isActivo());
            stmt.setObject(9, empleado.getModificadoPor());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        empleado.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al crear empleado: " + e.getMessage());
        }
        return false;
    }
    
    public Empleado buscarPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerEmpleado(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
        }
        return null;
    }
    
    public Empleado buscarPorDocumento(String documento) {
        String sql = "SELECT * FROM empleados WHERE documento = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, documento);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerEmpleado(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado por documento: " + e.getMessage());
        }
        return null;
    }
    
    public Empleado buscarPorUsuarioId(int usuarioId) {
        String sql = "SELECT * FROM empleados WHERE usuario_id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerEmpleado(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado por usuario: " + e.getMessage());
        }
        return null;
    }
    
    public List<Empleado> listarTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados WHERE activo = TRUE ORDER BY nombre";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                empleados.add(extraerEmpleado(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        }
        return empleados;
    }
    
    public List<Empleado> buscarPorNombre(String nombre) {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados WHERE nombre LIKE ? AND activo = TRUE";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                empleados.add(extraerEmpleado(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar empleados por nombre: " + e.getMessage());
        }
        return empleados;
    }
    
    public boolean actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, rol = ?, correo = ?, salario = ?, " +
                    "modificado_por = ? WHERE id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getRol());
            stmt.setString(3, empleado.getCorreo());
            stmt.setDouble(4, empleado.getSalario());
            stmt.setObject(5, empleado.getModificadoPor());
            stmt.setInt(6, empleado.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
        }
        return false;
    }
    
    private Empleado extraerEmpleado(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setId(rs.getInt("id"));
        
        int usuarioId = rs.getInt("usuario_id");
        if (!rs.wasNull()) {
            empleado.setUsuarioId(usuarioId);
        }
        
        empleado.setNombre(rs.getString("nombre"));
        empleado.setDocumento(rs.getString("documento"));
        empleado.setRol(rs.getString("rol"));
        empleado.setCorreo(rs.getString("correo"));
        empleado.setSalario(rs.getDouble("salario"));
        empleado.setActivo(rs.getBoolean("activo"));
        
        Date fechaContratacion = rs.getDate("fecha_contratacion");
        if (fechaContratacion != null) {
            empleado.setFechaContratacion(fechaContratacion.toLocalDate());
        }
        
        return empleado;
    }
}