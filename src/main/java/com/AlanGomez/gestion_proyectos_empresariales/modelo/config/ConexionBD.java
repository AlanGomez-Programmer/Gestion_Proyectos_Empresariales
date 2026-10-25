package com.AlanGomez.gestion_proyectos_empresariales.modelo.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {
    
    private static final String ARCHIVO_CONFIG = "db.propiertes";
    private static final ConexionBD INSTANCIA = new ConexionBD();
    private Connection conexion;
    
    private ConexionBD(){
        
    }
    
    public ConexionBD obtenerInstancia(){
        return INSTANCIA;
    }
    
    private Properties cargarPropiedades(){
        Properties propiedades = new Properties();
        try (InputStream entrada = ConexionBD.class.getClassLoader()
                .getResourceAsStream(ARCHIVO_CONFIG)) { 
            if (entrada == null) {
                throw new IllegalStateException("No se encontro " + ARCHIVO_CONFIG + 
                        " en src/main/resources");   
            }
            propiedades.load(entrada);
            return propiedades;
        } catch (IOException e) {
            throw new IllegalStateException("Error al leer " + ARCHIVO_CONFIG, e);
        }
    }
    
    public Connection obtenerConexion() throws SQLException{
        if (conexion == null || conexion.isClosed()){
           conexion = DriverManager.getConnection(
                   cargarPropiedades().getProperty("db.url"),
                   cargarPropiedades().getProperty("db.usuario"),
                   cargarPropiedades().get
           );
        }
        return conexion;
    }
    
}
