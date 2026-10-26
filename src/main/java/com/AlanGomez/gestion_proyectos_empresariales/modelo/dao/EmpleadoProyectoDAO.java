package com.AlanGomez.gestion_proyectos_empresariales.modelo.dao;

import com.AlanGomez.gestion_proyectos_empresariales.modelo.clases.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
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
    
    // Busca una asignación por su llave compuesta; devuelve null si no existe
    public EmpleadoProyecto buscarAsignacion(int empleadoId, int proyectoId) throws SQLException{
        String sql = """
            SELECT empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales
            FROM Empleado_Proyecto
            WHERE empleado_id = ? AND proyecto_id = ?
            """;
        try(
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
           ){
            ps.setInt(1, empleadoId);
            ps.setInt(2, proyectoId);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return mapear(rs);
                }
                return null;
            }
        }
    }
 
    // Integrantes de un proyecto: asignación -> empleado
    public Map<EmpleadoProyecto, Empleado> listar(int proyectoId) throws SQLException{
        String sql = """
            SELECT EP.empleado_id, EP.proyecto_id, EP.rol, EP.fecha_asignacion, EP.horas_semanales,
                   E.nombre, E.cargo
            FROM Empleado_Proyecto EP
            INNER JOIN Empleados E ON E.id = EP.empleado_id
            WHERE EP.proyecto_id = ?
            ORDER BY E.nombre
            """;
        Map<EmpleadoProyecto, Empleado> listas = new LinkedHashMap<>();
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setInt(1, proyectoId);
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    listas.put(mapear(rs), mapearEmpleado(rs));
                }
            }
            return listas;
        }
    }

    // Proyectos de un empleado: asignación -> proyecto
    public Map<EmpleadoProyecto, Proyecto> listarProyectos(int empleadoId) throws SQLException {
        String sql = """
            SELECT EP.empleado_id, EP.proyecto_id, EP.rol, EP.fecha_asignacion, EP.horas_semanales,
                   P.id           AS p_id,
                   P.nombre       AS p_nombre,
                   P.descripcion  AS p_descripcion,
                   P.fecha_inicio AS p_fecha_inicio,
                   P.fecha_fin    AS p_fecha_fin,
                   P.estado       AS p_estado
            FROM Empleado_Proyecto EP
            INNER JOIN Proyectos P ON P.id = EP.proyecto_id
            WHERE EP.empleado_id = ?
            ORDER BY P.nombre
            """;
        Map<EmpleadoProyecto, Proyecto> listas = new LinkedHashMap<>();
        try (
                Connection con = obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setInt(1, empleadoId);
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    listas.put(mapear(rs), mapearProyecto(rs));
                }
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
                PreparedStatement ps = con.prepareStatement(sql)
           ){
            ps.setString(1, empleadoProyecto.getRol());
            ps.setInt(2, empleadoProyecto.getHorasSemanales());
            ps.setInt(3, empleadoProyecto.getEmpleadoId());
            ps.setInt(4, empleadoProyecto.getProyectoId());
            return ps.executeUpdate() == 1;
        }
    }
    
    public boolean eliminarEmpleadoAsignado(Empleado empleado, Proyecto proyecto) throws SQLException {
        String sql = "DELETE FROM Empleado_Proyecto WHERE empleado_id = ? AND proyecto_id = ?";
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
    
    public Proyecto mapearProyecto(ResultSet rs) throws SQLException {
        return new Proyecto(
                rs.getInt("p_id"),
                rs.getString("p_nombre"),
                rs.getString("p_descripcion"),
                rs.getObject("p_fecha_inicio", LocalDate.class),
                rs.getObject("p_fecha_fin", LocalDate.class),
                Estado.valueOf(rs.getString("p_estado"))
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
