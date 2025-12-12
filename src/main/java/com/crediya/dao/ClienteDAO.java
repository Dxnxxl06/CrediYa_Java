package com.crediya.dao;

import com.crediya.modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public boolean crear(Cliente cliente) {
        String sql = "INSERT INTO clientes (usuario_id, nombre, documento, correo, telefono, " +
                    "direccion, activo, modificado_por) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setObject(1, cliente.getUsuarioId());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getDocumento());
            stmt.setString(4, cliente.getCorreo());
            stmt.setString(5, cliente.getTelefono());
            stmt.setString(6, cliente.getDireccion());
            stmt.setBoolean(7, cliente.isActivo());
            stmt.setObject(8, cliente.getModificadoPor());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cliente.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al crear cliente: " + e.getMessage());
        }
        return false;
    }
    
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerCliente(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }
    
    public Cliente buscarPorDocumento(String documento) {
        String sql = "SELECT * FROM clientes WHERE documento = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, documento);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerCliente(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por documento: " + e.getMessage());
        }
        return null;
    }
    
    public Cliente buscarPorUsuarioId(int usuarioId) {
        String sql = "SELECT * FROM clientes WHERE usuario_id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerCliente(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por usuario: " + e.getMessage());
        }
        return null;
    }
    
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE activo = TRUE ORDER BY nombre";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clientes.add(extraerCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return clientes;
    }
    
    public List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nombre LIKE ? AND activo = TRUE";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                clientes.add(extraerCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar clientes por nombre: " + e.getMessage());
        }
        return clientes;
    }
    
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, correo = ?, telefono = ?, " +
                    "direccion = ?, modificado_por = ? WHERE id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getCorreo());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getDireccion());
            stmt.setObject(5, cliente.getModificadoPor());
            stmt.setInt(6, cliente.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
        return false;
    }
    
    private Cliente extraerCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        
        int usuarioId = rs.getInt("usuario_id");
        if (!rs.wasNull()) {
            cliente.setUsuarioId(usuarioId);
        }
        
        cliente.setNombre(rs.getString("nombre"));
        cliente.setDocumento(rs.getString("documento"));
        cliente.setCorreo(rs.getString("correo"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setDireccion(rs.getString("direccion"));
        cliente.setActivo(rs.getBoolean("activo"));
        
        return cliente;
    }
}