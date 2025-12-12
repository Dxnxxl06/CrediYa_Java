package com.crediya.servicio;

import com.crediya.dao.EmpleadoDAO;
import com.crediya.dao.UsuarioDAO;
import com.crediya.modelo.Empleado;
import com.crediya.modelo.Usuario;
import com.crediya.modelo.enums.TipoUsuario;
import com.crediya.utilidades.ArchivoUtil;
import java.util.List;

public class EmpleadoServicio {
    private EmpleadoDAO empleadoDAO;
    private UsuarioDAO usuarioDAO;
    
    public EmpleadoServicio() {
        this.empleadoDAO = new EmpleadoDAO();
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public boolean registrarEmpleado(Empleado empleado, String username, String password, 
                                    boolean esAdmin, Integer creadoPor) {
        if (empleadoDAO.buscarPorDocumento(empleado.getDocumento()) != null) {
            System.out.println("Error: Ya existe un empleado con ese documento");
            return false;
        }
        
        TipoUsuario tipoUsuario = esAdmin ? TipoUsuario.ADMIN : TipoUsuario.EMPLEADO;
        Usuario usuario = new Usuario(username, password, tipoUsuario);
        usuario.setCreadoPor(creadoPor);
        
        if (usuarioDAO.crear(usuario)) {
            empleado.setUsuarioId(usuario.getId());
            empleado.setModificadoPor(creadoPor);
            
            if (empleadoDAO.crear(empleado)) {
                guardarEnArchivo(empleado);
                return true;
            }
        }
        return false;
    }
    
    public Empleado buscarPorId(int id) {
        return empleadoDAO.buscarPorId(id);
    }
    
    public Empleado buscarPorDocumento(String documento) {
        return empleadoDAO.buscarPorDocumento(documento);
    }
    
    public List<Empleado> listarTodos() {
        return empleadoDAO.listarTodos();
    }
    
    public List<Empleado> buscarPorNombre(String nombre) {
        return empleadoDAO.buscarPorNombre(nombre);
    }
    
    public boolean actualizar(Empleado empleado) {
        if (empleadoDAO.actualizar(empleado)) {
            actualizarArchivo();
            return true;
        }
        return false;
    }
    
    private void guardarEnArchivo(Empleado empleado) {
        String linea = String.format("%d,%s,%s,%s,%s,%.2f",
            empleado.getId(),
            empleado.getNombre(),
            empleado.getDocumento(),
            empleado.getRol(),
            empleado.getCorreo(),
            empleado.getSalario()
        );
        ArchivoUtil.escribirLinea("empleados.txt", linea);
    }
    
    private void actualizarArchivo() {
        List<Empleado> empleados = listarTodos();
        ArchivoUtil.limpiarArchivo("empleados.txt");
        empleados.forEach(this::guardarEnArchivo);
    }
    
    public void mostrarEmpleado(Empleado e) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.printf("║ ID: %-54d ║%n", e.getId());
        System.out.printf("║ Nombre: %-50s ║%n", e.getNombre());
        System.out.printf("║ Documento: %-47s ║%n", e.getDocumento());
        System.out.printf("║ Rol: %-53s ║%n", e.getRol());
        System.out.printf("║ Correo: %-50s ║%n", e.getCorreo());
        System.out.printf("║ Salario: $%-48.2f ║%n", e.getSalario());
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}