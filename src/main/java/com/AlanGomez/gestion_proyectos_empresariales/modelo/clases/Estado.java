package com.AlanGomez.gestion_proyectos_empresariales.modelo.clases;

public enum Estado {
    
    PLANIFICADO("PLANIFICADO"),
    EN_CURSO("EN_CURSO"),
    FINALIZADO("FINALIZADO");
    
    private String nombre;
    
    private Estado(String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    
}
