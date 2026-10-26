package com.AlanGomez.gestion_proyectos_empresariales.modelo.dao;

import com.AlanGomez.gestion_proyectos_empresariales.modelo.clases.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProyectosDAO extends OperacionesDAO implements Mapear<ResultSet, Proyecto>{

    public boolean crear(Proyecto proyecto) throws SQLException{
        String sql = "INSERT INTO Proyectos(nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES (?,?,?,?,?)";
        try (
                Connection con = obtenerConexion();
                PreparedStatement sp = con.prepareStatement(sql);
            ){
            sp.setString(1, proyecto.getNombre());
            sp.setString(2, proyecto.getDescripcion());
            sp.setObject(3, proyecto.getFechaInicio());
            sp.setObject(4, proyecto.getFechaFin());
            sp.setObject(5, proyecto.getEstado());
            return sp.executeUpdate() == 1;
        }
    }
    
    public List<Proyecto> listar() throws SQLException {
        String sql = "SELECT * FROM Proyectos ORDER BY id";
        List<Proyecto> lista = new ArrayList<>();
        try(
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
    
    public Proyecto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Proyectos WHERE id = ?";
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            ){
            if (rs.next()){
                return mapear(rs);
            }
            return null;
        }
    }
    
    public boolean actulizarTodo(Proyecto proyecto) throws SQLException{
        String sql = "UPDATE Proyectos SET nombre = ?, descripcion = ?, fecha_fin = ?, estado = ? WHERE id = ?";
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setString(1, proyecto.getNombre());
            ps.setString(2, proyecto.getDescripcion());
            ps.setObject(3, proyecto.getFechaFin());
            ps.setObject(4, proyecto.getEstado());
            ps.setInt(5, proyecto.getId());
            return ps.executeUpdate() == 1;
        }
    }
    
    public boolean actulizarFechaFin(Proyecto proyecto) throws SQLException{
        String sql = "UPDATE Proyectos SET fecha_fin = ? WHERE id = ?";
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setObject(1, proyecto.getFechaFin());
            ps.setInt(2, proyecto.getId());
            return ps.executeUpdate() == 1;
        }
    }
    
    public boolean actulizarEstado(Proyecto proyecto) throws SQLException{
        String sql = "UPDATE Proyectos SET estado = ? WHERE id = ?";
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setString(1, proyecto.getNombre());
            ps.setObject(2, proyecto.getEstado());
            return ps.executeUpdate() == 1;
        }
    }
    
    public boolean eliminar(Proyecto proyecto) throws SQLException{
        String sql = "DELETE FROM Proyectos WHERE id = ?";
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setInt(1, proyecto.getId());
            return ps.executeUpdate() == 1;
        }
    }
    
    @Override
    public Proyecto mapear(ResultSet rs) throws SQLException {
        return new Proyecto(
                rs.getInt("id"), 
                rs.getString("nombre"), 
                rs.getString("descripcion"), 
                rs.getObject("fecha_inicio", LocalDate.class), 
                rs.getObject("fecha_fin", LocalDate.class), 
                Estado.valueOf(rs.getString("estado"))
        );
    }

    
    
}
