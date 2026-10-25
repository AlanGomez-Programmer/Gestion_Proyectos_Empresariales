package com.AlanGomez.gestion_proyectos_empresariales.modelo.clases;

import java.time.LocalDate;

public class Empleado_Proyecto {

    private static final int ID_SIN_ASIGNAR = 0;
    
    private int empleado_id;
    private int proyecto_id;
    private String rol;
    private LocalDate fecha_asignacion;
    
    public Empleado_Proyecto(int empleado_id, int proyecto_id, String rol, LocalDate fecha_asignacion){
        this.empleado_id = empleado_id;
        this.proyecto_id = proyecto_id;
        this.rol = rol;
        this.fecha_asignacion = fecha_asignacion;
    }
    
    
    public Empleado_Proyecto(String rol, LocalDate fecha_asignacion){
        this(ID_SIN_ASIGNAR, ID_SIN_ASIGNAR, rol, fecha_asignacion);
    }

    public static int getID_SIN_ASIGNAR() {
        return ID_SIN_ASIGNAR;
    }

    public int getEmpleado_id() {
        return empleado_id;
    }

    public int getProyecto_id() {
        return proyecto_id;
    }

    public String getRol() {
        return rol;
    }

    public LocalDate getFecha_asignacion() {
        return fecha_asignacion;
    }

    public void setEmpleado_id(int empleado_id) {
        this.empleado_id = empleado_id;
    }

    public void setProyecto_id(int proyecto_id) {
        this.proyecto_id = proyecto_id;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setFecha_asignacion(LocalDate fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }
    
}
