package com.AlanGomez.gestion_proyectos_empresariales.modelo.dao;

import com.AlanGomez.gestion_proyectos_empresariales.modelo.clases.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO extends OperacionesDAO implements Mapear<ResultSet, Empleado>{
    
    public boolean crear(Empleado empleado) throws SQLException{
        String sql = "INSERT INTO Empleados(nombre, correo) VALUES (?, ?)";
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getCorreo());
            return ps.executeUpdate() == 1;
        }
    }
    
    public List<String> listar() throws SQLException{
        String sql = "SELECT nombre, correo FROM Empleados ORDER BY id";
        List<String> lista = new ArrayList<>();
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            ){
            while(rs.next()){
                lista.add();
                retun;
            }
        }
    }

    @Override
    public Empleado mapear(ResultSet rs) throws SQLException{
        
    }
}
