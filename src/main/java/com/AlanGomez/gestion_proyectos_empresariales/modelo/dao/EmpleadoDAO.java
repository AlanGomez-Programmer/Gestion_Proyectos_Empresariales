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
        String sql = "INSERT INTO Empleados(nombre, correo, cargo) VALUES (?, ?, ?)";
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getCorreo());
            ps.setString(3, empleado.getCargo());
            return ps.executeUpdate() == 1;
        }
    }
    
    public List<Empleado> listar() throws SQLException{
        String sql = "SELECT id, nombre, correo, cargo FROM Empleados ORDER BY id";
        List<Empleado> lista = new ArrayList<>();
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            ){
            while(rs.next()){
                lista.add(mapear(rs));
            }
            return lista;
        }
    }

    public Empleado buscarPorId(int id) throws SQLException{
        String sql = "SELECT id, nombre, correo, cargo FROM Empleados WHERE id = ?";
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return mapear(rs);
                }
                return null;
            }
        }
    }
    
    public boolean actualizarTodo(Empleado empleado) throws SQLException{
        String sql = "UPDATE Empleados SET nombre = ?, correo = ?, cargo = ? WHERE id = ?";
        try(
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
           ){
           ps.setString(1, empleado.getNombre());
           ps.setString(2, empleado.getCorreo());
           ps.setString(3, empleado.getCargo());
           ps.setInt(4, empleado.getId());
           return ps.executeUpdate() == 1;
        }
    }
    
    public boolean actualizarNombre(Empleado empleado) throws SQLException{
        String sql = "UPDATE Empleados SET nombre = ? WHERE id = ?";
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setString(1, empleado.getNombre());
            ps.setInt(2, empleado.getId());
            return ps.executeUpdate() == 1;
        }
    }
    
    public boolean actualizarCorreo(Empleado empleado) throws SQLException{
        String sql = "UPDATE Empleados SET correo = ? WHERE id = ?";
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setString(1, empleado.getCorreo());
            ps.setInt(2, empleado.getId());
            return ps.executeUpdate() == 1;
        }
    }
    
    public boolean actualizarCargo(Empleado empleado) throws SQLException{
        String sql = "UPDATE Empleados SET cargo = ? WHERE id = ?";
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setString(1, empleado.getCargo());
            ps.setInt(2, empleado.getId());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminar(Empleado empleado) throws SQLException{
        String sql = "DELETE FROM Empleados WHERE id = ?";
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setInt(1, empleado.getId());
            return ps.executeUpdate() == 1;
        }
    }
    
    @Override
    public Empleado mapear(ResultSet rs) throws SQLException{
        return new Empleado(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("cargo")
        );
    }
}
