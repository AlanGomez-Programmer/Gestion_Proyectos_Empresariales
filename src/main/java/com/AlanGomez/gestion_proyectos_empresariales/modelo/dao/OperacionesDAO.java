package com.AlanGomez.gestion_proyectos_empresariales.modelo.dao;

import com.AlanGomez.gestion_proyectos_empresariales.modelo.config.ConexionBD;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class OperacionesDAO {
    protected Connection obtenerConexion() throws SQLException{
        return ConexionBD.obtenerInstancia().obtenerConexion();
    }
}
