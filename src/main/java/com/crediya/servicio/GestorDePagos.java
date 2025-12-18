package com.crediya.servicio;

import java.math.BigDecimal;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class GestorDePagos {
    private final String url = "jdbc:mysql://localhost:3306/crediya_db";
    private final String usuario = "root";
    private final String password = "admin";
    
    
    public void registrarPago(int prestamoId, BigDecimal montoPagado, LocalDate fechaPago) 
        throws prestamoNoEncontradoException {
            Connection conexion = null;
            PreparedStatement psVerificar = null;
            PreparedStatement psInsertarPago = null;
            PreparedStatement psActualizarPrestamo = null;
            try {
                conexion = DriverManager.getConnection(url, usuario, password);
                String sqlVerficar = """
                        SELECT saldo_pendiente, estado
                        FROM prestamos
                        WHERE id = ?
                        """;
                psVerificar = conexion,PreparedStatement(sqlVerficar);
                psVerificar.setInt(1, prestamoId);
                rs = psVerificar.executeQuery();
                } if (rs.next()) {
                throw new {
                    prestamoNoEncontradoException("No se encontro un prestamo con ID:" + prestamoId)
                }
                BigDecimal
            }
    }
    
}
