package com.AlanGomez.gestion_proyectos_empresariales.modelo.dao;

import java.sql.SQLException;

@FunctionalInterface
public interface Mapear <ObjetoRecibir, ObjetoDevolver>{
    // Método que recibe un objeto y devuelve 
    ObjetoDevolver mapear(ObjetoRecibir rs) throws SQLException;
}
