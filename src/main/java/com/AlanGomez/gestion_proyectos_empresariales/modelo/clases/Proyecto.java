package com.AlanGomez.gestion_proyectos_empresariales.modelo.clases;

import java.time.LocalDate;

public class Proyecto {
    
    private static final int ID_SIN_ASIGNAR = 0;
    
    private int id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Estado estado;
    
    public Proyecto(int id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, Estado estado){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }
    
    public Proyecto(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, Estado estado){
        this(ID_SIN_ASIGNAR, nombre, descripcion, fechaInicio, fechaFin, estado); 
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
     
}
