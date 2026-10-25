package com.AlanGomez.gestion_proyectos_empresariales.modelo.clases;

import java.time.LocalDate;

public class Proyecto {
    
    private static final int ID_SIN_ASIGNAR = 0;
    
    private int id;
    private String nombre;
    private String descripcion;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private Estado estado;
    
    public Proyecto(int id, String nombre, String descripcion, LocalDate fecha_inicio, LocalDate fecha_fin, Estado estado){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.estado = estado;
    }
    
    public Proyecto(String nombre, String descripcion, LocalDate fecha_inicio, LocalDate fecha_fin, Estado estado){
        this(ID_SIN_ASIGNAR, nombre, descripcion, fecha_inicio, fecha_fin, estado); 
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

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
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

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
     
}
