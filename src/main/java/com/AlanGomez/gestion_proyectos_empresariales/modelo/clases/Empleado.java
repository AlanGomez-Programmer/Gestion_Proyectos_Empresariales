package com.AlanGomez.gestion_proyectos_empresariales.modelo.clases;

public class Empleado {
    
    private int id;
    private String nombre;
    private String correo;
    
    public Empleado(int id, String nombre, String correo){
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }
    
    public Empleado(String nombre, String correo){
        this.nombre = nombre;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }
    
    
}
