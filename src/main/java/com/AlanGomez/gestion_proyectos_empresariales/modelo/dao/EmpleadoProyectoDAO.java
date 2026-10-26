package com.AlanGomez.gestion_proyectos_empresariales.modelo.dao;

import com.AlanGomez.gestion_proyectos_empresariales.modelo.clases.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EmpleadoProyectoDAO extends OperacionesDAO implements Mapear<ResultSet, EmpleadoProyecto>{
    
    public boolean asignarProyecto(Empleado empleado, Proyecto proyecto, EmpleadoProyecto empleadoProyecto) throws SQLException{
        String sql = """ 
            INSERT INTO Empleado_Proyecto(empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales)
            VALUES(?, ?, ?, ?, ?)
        """;
        try(
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
           ){
            ps.setInt(1, empleado.getId());
            ps.setInt(2, proyecto.getId());
            ps.setString(3, empleadoProyecto.getRol());
            ps.setObject(4, empleadoProyecto.getFechaAsignacion());
            ps.setInt(5, empleadoProyecto.getHorasSemanales());
            return ps.executeUpdate() == 1;
        }
    }
 
    public Map<EmpleadoProyecto, Empleado> listar(int id) throws SQLException{
        String sql = """
            SELECT EP.empleado_id, EP.proyecto_id, EP.rol, EP.fecha_asignacion, EP.horas_semanales,
                   E.nombre, E.cargo
            FROM Empleado_Proyecto EP
            INNER JOIN Empleado E ON E.id = EP.empleado_id
            WHERE EP.proyecto_id = ?
        """;
        Map<EmpleadoProyecto, Empleado> listas = new HashMap<>();
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            ){
            ps.setInt(1, id);
            while(rs.next()){
                listas.put(mapear(rs), mapearEmpleado(rs));
            }
            return listas;
        }
    }

    public Map<Empleado, EmpleadoProyecto> listarProyectos(int id) throws SQLException {
        String sql = """
            SELECT E.nombre, E.cargo, EP.nombre, EP.rol
            FROM Empleado_Proyecto EP
            INNER JOIN Empleados E ON E.id = EP.id
            WHERE E.id = ?
        """;
        Map<Empleado, EmpleadoProyecto> listas = new HashMap<>();
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            ){
            while(rs.next()){
                listas.put(mapearEmpleado(rs), mapear(rs));
            }
            return listas;
        }
    }
    
    public boolean actualizarAsignacion(EmpleadoProyecto empleadoProyecto) throws SQLException {
        String sql = """
            UPDATE Empleado_Proyecto SET rol = ?, horas_semanales = ?
            WHERE empleado_id = ? AND proyecto_id = ?    
        """;
        try(
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
           ){
            ps.setString(1, empleadoProyecto.getRol());
            ps.setInt(2, empleadoProyecto.getHorasSemanales());
            ps.setInt(3, empleadoProyecto.getEmpleadoId());
            ps.setInt(4, empleadoProyecto.getProyectoId());
            return ps.executeUpdate() == 1;
        }
    }
    
    public boolean eliminarEmpleadoAsignado(Empleado empleado, Proyecto proyecto) throws SQLException {
        String sql = """
            DELETE FROM Empleado_Proyecto WHERE empleado_id = ? AND empleado_id = ?;
        """;
        try(
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
           ){
            ps.setInt(1, empleado.getId());
            ps.setInt(2, proyecto.getId());
            return ps.executeUpdate() == 1;
        }
    }
    
    public Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        return new Empleado(
                rs.getString("nombre"),
                rs.getString("cargo")
        );
    }
    
    @Override
    public EmpleadoProyecto mapear(ResultSet rs) throws SQLException {
        return new EmpleadoProyecto(
                rs.getInt("empleado_id"), 
                rs.getInt("proyecto_id"), 
                rs.getString("rol"),
                rs.getObject("fecha_asignacion", LocalDate.class),
                rs.getInt("horas_semanales")
        );
    }   
}
