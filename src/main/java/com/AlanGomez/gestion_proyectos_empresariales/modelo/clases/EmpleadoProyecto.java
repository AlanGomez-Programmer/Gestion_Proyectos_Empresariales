package com.AlanGomez.gestion_proyectos_empresariales.modelo.clases;

import java.time.LocalDate;

public class EmpleadoProyecto {

    private static final int ID_SIN_ASIGNAR = 0;
    
    private int empleadoId;
    private int proyectoId;
    private String rol;
    private LocalDate fechaAsignacion;
    private int horasSemanales;
    
    public EmpleadoProyecto(int empleadoId, int proyectoId, String rol, LocalDate fechaAsignacion, int horasSemanales){
        this.empleadoId = empleadoId;
        this.proyectoId = proyectoId;
        this.rol = rol;
        this.fechaAsignacion = fechaAsignacion;
        this.horasSemanales = horasSemanales;
    }
    
    public EmpleadoProyecto(String rol, LocalDate fechaAsignacion, int horasSemanales){
        this(ID_SIN_ASIGNAR, ID_SIN_ASIGNAR, rol, fechaAsignacion, horasSemanales);
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public int getProyectoId() {
        return proyectoId;
    }

    public String getRol() {
        return rol;
    }
    
    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public int getHorasSemanales() {
        return horasSemanales;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }
}
