package com.AlanGomez.gestion_proyectos_empresariales.modelo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public record ConfiguracionBD (String url, String usuario, String contrasenia) {

    private static final String ARCHIVO_CONFIG = "db.properties";

    public static ConfiguracionBD cargar(){
        Properties propiedades = new Properties();
        try (InputStream entrada = ConfiguracionBD.class.getClassLoader()
                .getResourceAsStream(ARCHIVO_CONFIG)){
            if(entrada == null){
                throw new IllegalStateException("No se encontró " + ARCHIVO_CONFIG + " en src/main/resources");
            }
            propiedades.load(entrada);
          
        } catch (IOException e) {
            throw new IllegalStateException("Error al leer " + ARCHIVO_CONFIG, e);
        } 
        
        return new ConfiguracionBD(
                obtenerObligatoria(propiedades, "db.url"),
                obtenerObligatoria(propiedades, "db.usuario"),
                obtenerObligatoria(propiedades, "db.contrasenia")
        );
    }
    
    private static String obtenerObligatoria(Properties propiedades, String clave){
        String valor = propiedades.getProperty(clave);
        if (valor == null || valor.isBlank()){
            throw new IllegalStateException(
                    "Falta la clave '" + clave + "' en " + ARCHIVO_CONFIG
            );
        }
        return valor.trim();
    }
}
