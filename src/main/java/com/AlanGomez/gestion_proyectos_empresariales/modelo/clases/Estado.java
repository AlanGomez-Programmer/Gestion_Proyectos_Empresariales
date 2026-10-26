package com.AlanGomez.gestion_proyectos_empresariales.modelo.clases;

public enum Estado {
    
    PLANIFICADO("Planificado"),
    EN_CURSO("En curso"),
    FINALIZADO("Finalizado");
    
    private final String nombre;
    
    private Estado(String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
}
