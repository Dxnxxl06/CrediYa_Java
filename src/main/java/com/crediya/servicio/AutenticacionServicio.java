package com.crediya.servicio;

import com.crediya.dao.UsuarioDAO;
import com.crediya.dao.EmpleadoDAO;
import com.crediya.dao.ClienteDAO;
import com.crediya.modelo.Usuario;
import com.crediya.modelo.Empleado;
import com.crediya.modelo.Cliente;
import com.crediya.modelo.enums.TipoUsuario;

public class AutenticacionServicio {
    private UsuarioDAO usuarioDAO;
    private EmpleadoDAO empleadoDAO;
    private ClienteDAO clienteDAO;
    private Usuario usuarioActual;
    private Empleado empleadoActual;
    private Cliente clienteActual;
    
    public AutenticacionServicio() {
        this.usuarioDAO = new UsuarioDAO();
        this.empleadoDAO = new EmpleadoDAO();
        this.clienteDAO = new ClienteDAO();
    }
    
    public boolean iniciarSesion(String username, String password) {
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        
        if (usuario != null && usuario.getPassword().equals(password) && usuario.isActivo()) {
            usuarioActual = usuario;
            usuarioDAO.actualizarUltimoAcceso(usuario.getId());
            
            if (usuario.getTipoUsuario() == TipoUsuario.EMPLEADO || 
                usuario.getTipoUsuario() == TipoUsuario.ADMIN) {
                empleadoActual = empleadoDAO.buscarPorUsuarioId(usuario.getId());
            } else if (usuario.getTipoUsuario() == TipoUsuario.CLIENTE) {
                clienteActual = clienteDAO.buscarPorUsuarioId(usuario.getId());
            }
            
            return true;
        }
        return false;
    }
    
    public void cerrarSesion() {
        usuarioActual = null;
        empleadoActual = null;
        clienteActual = null;
    }
    
    public boolean registrarUsuario(String username, String password, TipoUsuario tipo, 
                                   Integer creadoPor) {
        if (usuarioDAO.buscarPorUsername(username) != null) {
            System.out.println("Error: El nombre de usuario ya existe");
            return false;
        }
        
        Usuario nuevoUsuario = new Usuario(username, password, tipo);
        nuevoUsuario.setCreadoPor(creadoPor);
        
        return usuarioDAO.crear(nuevoUsuario);
    }
    
    public boolean estaAutenticado() {
        return usuarioActual != null;
    }
    
    public boolean esAdmin() {
        return usuarioActual != null && 
               usuarioActual.getTipoUsuario() == TipoUsuario.ADMIN;
    }
    
    public boolean esEmpleado() {
        return usuarioActual != null && 
               (usuarioActual.getTipoUsuario() == TipoUsuario.EMPLEADO ||
                usuarioActual.getTipoUsuario() == TipoUsuario.ADMIN);
    }
    
    public boolean esCliente() {
        return usuarioActual != null && 
               usuarioActual.getTipoUsuario() == TipoUsuario.CLIENTE;
    }
    
    public Usuario getUsuarioActual() { return usuarioActual; }
    public Empleado getEmpleadoActual() { return empleadoActual; }
    public Cliente getClienteActual() { return clienteActual; }
}