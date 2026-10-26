package com.AlanGomez.gestion_proyectos_empresariales.modelo.clases;

public class Empleado {
    
    private static final int ID_SIN_ASIGNAR = 0;
    
    private int id;
    private String nombre;
    private String correo;
    private String cargo;
    
    public Empleado(int id, String nombre, String correo, String cargo){
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.cargo = cargo;
    }
    
    public Empleado(String nombre, String correo, String cargo){
        this(ID_SIN_ASIGNAR, nombre, correo, cargo);
    }
    
    public Empleado(String nombre, String cargo){
        this.nombre = nombre;
        this.cargo = cargo;
    }
            
    public Empleado(String nombre){
        this.nombre = nombre;
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

    public String getCargo() {
        return cargo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
