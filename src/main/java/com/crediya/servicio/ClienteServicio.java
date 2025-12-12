package com.crediya.servicio;

import com.crediya.dao.ClienteDAO;
import com.crediya.dao.UsuarioDAO;
import com.crediya.modelo.Cliente;
import com.crediya.modelo.Usuario;
import com.crediya.modelo.enums.TipoUsuario;
import com.crediya.utilidades.ArchivoUtil;
import java.util.List;

public class ClienteServicio {
    private ClienteDAO clienteDAO;
    private UsuarioDAO usuarioDAO;
    
    public ClienteServicio() {
        this.clienteDAO = new ClienteDAO();
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public boolean registrarCliente(Cliente cliente, String username, String password, 
                                   Integer creadoPor) {
        if (clienteDAO.buscarPorDocumento(cliente.getDocumento()) != null) {
            System.out.println("Error: Ya existe un cliente con ese documento");
            return false;
        }
        
        Usuario usuario = new Usuario(username, password, TipoUsuario.CLIENTE);
        usuario.setCreadoPor(creadoPor);
        
        if (usuarioDAO.crear(usuario)) {
            cliente.setUsuarioId(usuario.getId());
            cliente.setModificadoPor(creadoPor);
            
            if (clienteDAO.crear(cliente)) {
                guardarEnArchivo(cliente);
                return true;
            }
        }
        return false;
    }
    
    public Cliente buscarPorId(int id) {
        return clienteDAO.buscarPorId(id);
    }
    
    public Cliente buscarPorDocumento(String documento) {
        return clienteDAO.buscarPorDocumento(documento);
    }
    
    public List<Cliente> listarTodos() {
        return clienteDAO.listarTodos();
    }
    
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteDAO.buscarPorNombre(nombre);
    }
    
    public boolean actualizar(Cliente cliente) {
        if (clienteDAO.actualizar(cliente)) {
            actualizarArchivo();
            return true;
        }
        return false;
    }
    
    private void guardarEnArchivo(Cliente cliente) {
        String linea = String.format("%d,%s,%s,%s,%s",
            cliente.getId(),
            cliente.getNombre(),
            cliente.getDocumento(),
            cliente.getCorreo(),
            cliente.getTelefono()
        );
        ArchivoUtil.escribirLinea("clientes.txt", linea);
    }
    
    private void actualizarArchivo() {
        List<Cliente> clientes = listarTodos();
        ArchivoUtil.limpiarArchivo("clientes.txt");
        clientes.forEach(this::guardarEnArchivo);
    }
    
    public void mostrarCliente(Cliente c) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.printf("║ ID: %-54d ║%n", c.getId());
        System.out.printf("║ Nombre: %-50s ║%n", c.getNombre());
        System.out.printf("║ Documento: %-47s ║%n", c.getDocumento());
        System.out.printf("║ Correo: %-50s ║%n", c.getCorreo());
        System.out.printf("║ Teléfono: %-48s ║%n", c.getTelefono());
        if (c.getDireccion() != null) {
            System.out.printf("║ Dirección: %-47s ║%n", c.getDireccion());
        }
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}