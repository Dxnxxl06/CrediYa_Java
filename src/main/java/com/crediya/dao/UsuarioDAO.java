package com.crediya.dao;

import com.crediya.modelo.Usuario;
import com.crediya.modelo.enums.TipoUsuario;
import java.sql.*;

public class UsuarioDAO {
    
    public boolean crear(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, password, tipo_usuario, activo, creado_por) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getTipoUsuario().name());
            stmt.setBoolean(4, usuario.isActivo());
            stmt.setObject(5, usuario.getCreadoPor());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
        }
        return false;
    }
    
    public Usuario buscarPorUsername(String username) {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }
    
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por ID: " + e.getMessage());
        }
        return null;
    }
    
    public boolean actualizarUltimoAcceso(int usuarioId) {
        String sql = "UPDATE usuarios SET ultimo_acceso = NOW() WHERE id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar último acceso: " + e.getMessage());
        }
        return false;
    }
    
    private Usuario extraerUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
        usuario.setActivo(rs.getBoolean("activo"));
        
        Timestamp fechaCreacion = rs.getTimestamp("fecha_creacion");
        if (fechaCreacion != null) {
            usuario.setFechaCreacion(fechaCreacion.toLocalDateTime());
        }
        
        Timestamp ultimoAcceso = rs.getTimestamp("ultimo_acceso");
        if (ultimoAcceso != null) {
            usuario.setUltimoAcceso(ultimoAcceso.toLocalDateTime());
        }
        
        int creadoPor = rs.getInt("creado_por");
        if (!rs.wasNull()) {
            usuario.setCreadoPor(creadoPor);
        }
        
        return usuario;
    }
}
