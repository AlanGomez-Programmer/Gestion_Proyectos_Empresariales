package com.AlanGomez.gestion_proyectos_empresariales;

import com.AlanGomez.gestion_proyectos_empresariales.controlador.EmpleadoControlador;
import com.AlanGomez.gestion_proyectos_empresariales.controlador.EmpleadoProyectoControlador;
import com.AlanGomez.gestion_proyectos_empresariales.controlador.ProyectoControlador;
import com.AlanGomez.gestion_proyectos_empresariales.modelo.config.ConexionBD;
import com.AlanGomez.gestion_proyectos_empresariales.modelo.dao.EmpleadoDAO;
import com.AlanGomez.gestion_proyectos_empresariales.modelo.dao.EmpleadoProyectoDAO;
import com.AlanGomez.gestion_proyectos_empresariales.modelo.dao.ProyectosDAO;
import com.AlanGomez.gestion_proyectos_empresariales.vista.VistaEmpleado;
import com.AlanGomez.gestion_proyectos_empresariales.vista.VistaEmpleadoProyecto;
import com.AlanGomez.gestion_proyectos_empresariales.vista.VistaPrincipal;
import com.AlanGomez.gestion_proyectos_empresariales.vista.VistaProyecto;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    
    private static final int OPCION_SALIR = 0;

    public static void main(String[] args) {
        if (!verificarConexion()){
            return;
        }
        
        // Modelo (DAO)
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        ProyectosDAO proyectosDAO = new ProyectosDAO();
        EmpleadoProyectoDAO empleadoProyectoDAO = new EmpleadoProyectoDAO();
        
        // Vista + Controladores
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        EmpleadoControlador empleadoControlador =
                new EmpleadoControlador(new VistaEmpleado(), empleadoDAO);
        ProyectoControlador proyectoControlador =
                new ProyectoControlador(new VistaProyecto(), proyectosDAO);
        EmpleadoProyectoControlador empleadoProyectoControlador =
                new EmpleadoProyectoControlador(new VistaEmpleadoProyecto(), empleadoProyectoDAO,
                        empleadoDAO, proyectosDAO);
        
        // Menú principal
        int opcion;
        do {
            opcion = vistaPrincipal.menuPrincipal();
            switch (opcion) {
                case 1 -> empleadoControlador.iniciar();
                case 2 -> proyectoControlador.iniciar();
                case 3 -> empleadoProyectoControlador.iniciar();
                case OPCION_SALIR -> System.out.println("Saliendo del sistema...");
            }
        } while (opcion != OPCION_SALIR);
    }
    
    // Comprueba la configuración y la conexión antes de mostrar los menús
    private static boolean verificarConexion(){
        try (Connection con = ConexionBD.obtenerInstancia().obtenerConexion()) {
            if (con.isValid(2)){
                System.out.println("Conexión con la base de datos establecida.");
                return true;
            }
            System.out.println("La conexión con la base de datos no es válida.");
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la base de datos: " + e.getMessage());
            System.out.println("Revise que MySQL esté encendido y los datos de db.properties.");
        } catch (ExceptionInInitializerError e) {
            System.out.println("Error de configuración: " + e.getCause().getMessage());
        }
        return false;
    }
}
