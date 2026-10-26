package com.AlanGomez.gestion_proyectos_empresariales.controlador;

import com.AlanGomez.gestion_proyectos_empresariales.modelo.clases.Empleado;
import com.AlanGomez.gestion_proyectos_empresariales.modelo.clases.EmpleadoProyecto;
import com.AlanGomez.gestion_proyectos_empresariales.modelo.clases.Proyecto;
import com.AlanGomez.gestion_proyectos_empresariales.modelo.dao.EmpleadoDAO;
import com.AlanGomez.gestion_proyectos_empresariales.modelo.dao.EmpleadoProyectoDAO;
import com.AlanGomez.gestion_proyectos_empresariales.modelo.dao.ProyectosDAO;
import com.AlanGomez.gestion_proyectos_empresariales.utilidades.EntradaConsola;
import com.AlanGomez.gestion_proyectos_empresariales.utilidades.ManejadorErroresBD;
import com.AlanGomez.gestion_proyectos_empresariales.utilidades.TipoTexto;
import com.AlanGomez.gestion_proyectos_empresariales.vista.VistaEmpleadoProyecto;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class EmpleadoProyectoControlador {
    
    private static final int OPCION_VOLVER = 0;
    private static final int HORAS_MINIMAS = 1;
    private static final int HORAS_MAXIMAS = 40;
    
    private final VistaEmpleadoProyecto vista;
    private final EmpleadoProyectoDAO empleadoProyectoDAO;
    private final EmpleadoDAO empleadoDAO;
    private final ProyectosDAO proyectosDAO;
    
    public EmpleadoProyectoControlador(VistaEmpleadoProyecto vista, EmpleadoProyectoDAO empleadoProyectoDAO,
                                       EmpleadoDAO empleadoDAO, ProyectosDAO proyectosDAO){
        this.vista = vista;
        this.empleadoProyectoDAO = empleadoProyectoDAO;
        this.empleadoDAO = empleadoDAO;
        this.proyectosDAO = proyectosDAO;
    }
    
    public void iniciar(){
        int opcion;
        do {
            opcion = vista.menuEmpleadoProyecto();
            switch (opcion) {
                case 1 -> asignar();
                case 2 -> consultarIntegrantes();
                case 3 -> consultarProyectosEmpleado();
                case 4 -> actualizar();
                case 5 -> retirar();
                case OPCION_VOLVER -> System.out.println("Volviendo al menú principal...");
            }
        } while (opcion != OPCION_VOLVER);
    }
    
    // RF-07: Asignar empleado a proyecto
    private void asignar(){
        System.out.println("\n--- Asignar empleado a proyecto ---");
        try {
            Empleado empleado = solicitarEmpleadoExistente();
            if (empleado == null){
                return;
            }
            Proyecto proyecto = solicitarProyectoExistente();
            if (proyecto == null){
                return;
            }
            if (empleadoProyectoDAO.buscarAsignacion(empleado.getId(), proyecto.getId()) != null){
                System.out.println("Error: " + empleado.getNombre() + " ya está asignado al proyecto " + proyecto.getNombre() + ".");
                return;
            }
            
            String rol = EntradaConsola.ingresarTexto(TipoTexto.ALFANUMERICO, "Rol en el proyecto");
            LocalDate fechaAsignacion = EntradaConsola.ingresarFecha("Fecha de asignación");
            int horas = solicitarHoras();
            
            EmpleadoProyecto asignacion = new EmpleadoProyecto(rol, fechaAsignacion, horas);
            if (empleadoProyectoDAO.asignarProyecto(empleado, proyecto, asignacion)){
                System.out.println(empleado.getNombre() + " fue asignado al proyecto " + proyecto.getNombre() + ".");
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    // RF-08: Consultar integrantes de un proyecto
    private void consultarIntegrantes(){
        try {
            Proyecto proyecto = solicitarProyectoExistente();
            if (proyecto == null){
                return;
            }
            Map<EmpleadoProyecto, Empleado> integrantes = empleadoProyectoDAO.listar(proyecto.getId());
            if (integrantes.isEmpty()){
                System.out.println("El proyecto " + proyecto.getNombre() + " no tiene empleados asignados.");
                return;
            }
            
            String formato = "%-5s %-25s %-20s %-20s %-6s %-12s%n";
            System.out.println("\nIntegrantes del proyecto: " + proyecto.getNombre());
            System.out.printf(formato, "ID", "Empleado", "Cargo", "Rol", "Horas", "Asignado");
            System.out.println("-".repeat(93));
            for (Map.Entry<EmpleadoProyecto, Empleado> entrada : integrantes.entrySet()){
                EmpleadoProyecto asignacion = entrada.getKey();
                Empleado empleado = entrada.getValue();
                System.out.printf(formato,
                        asignacion.getEmpleadoId(), empleado.getNombre(), empleado.getCargo(),
                        asignacion.getRol(), asignacion.getHorasSemanales(), asignacion.getFechaAsignacion());
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    // RF-09: Consultar proyectos de un empleado
    private void consultarProyectosEmpleado(){
        try {
            Empleado empleado = solicitarEmpleadoExistente();
            if (empleado == null){
                return;
            }
            Map<EmpleadoProyecto, Proyecto> proyectos = empleadoProyectoDAO.listarProyectos(empleado.getId());
            if (proyectos.isEmpty()){
                System.out.println(empleado.getNombre() + " no tiene proyectos asignados.");
                return;
            }
            
            String formato = "%-5s %-30s %-20s %-6s %-12s%n";
            System.out.println("\nProyectos de: " + empleado.getNombre() + " (" + empleado.getCargo() + ")");
            System.out.printf(formato, "ID", "Proyecto", "Rol", "Horas", "Estado");
            System.out.println("-".repeat(77));
            for (Map.Entry<EmpleadoProyecto, Proyecto> entrada : proyectos.entrySet()){
                EmpleadoProyecto asignacion = entrada.getKey();
                Proyecto proyecto = entrada.getValue();
                System.out.printf(formato,
                        proyecto.getId(), proyecto.getNombre(), asignacion.getRol(),
                        asignacion.getHorasSemanales(), proyecto.getEstado().getNombre());
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    // RF-10: Modificar una asignación (rol y horas) sin duplicarla
    private void actualizar(){
        try {
            EmpleadoProyecto asignacion = solicitarAsignacionExistente();
            if (asignacion == null){
                return;
            }
            System.out.println("Rol actual: " + asignacion.getRol()
                    + " | Horas semanales actuales: " + asignacion.getHorasSemanales());
            
            int opcion = vista.menuActualizarAsignacion();
            switch (opcion) {
                case 1 -> {
                    asignacion.setRol(EntradaConsola.ingresarTexto(TipoTexto.ALFANUMERICO, "Nuevo rol"));
                    asignacion.setHorasSemanales(solicitarHoras());
                }
                case 2 -> asignacion.setRol(EntradaConsola.ingresarTexto(TipoTexto.ALFANUMERICO, "Nuevo rol"));
                case 3 -> asignacion.setHorasSemanales(solicitarHoras());
                case OPCION_VOLVER -> {
                    System.out.println("Actualización cancelada.");
                    return;
                }
            }
            
            if (empleadoProyectoDAO.actualizarAsignacion(asignacion)){
                System.out.println("Asignación actualizada correctamente.");
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    // RF-11: Retirar empleado de un proyecto (solo elimina la asignación)
    private void retirar(){
        try {
            Empleado empleado = solicitarEmpleadoExistente();
            if (empleado == null){
                return;
            }
            Proyecto proyecto = solicitarProyectoExistente();
            if (proyecto == null){
                return;
            }
            if (empleadoProyectoDAO.buscarAsignacion(empleado.getId(), proyecto.getId()) == null){
                System.out.println("Error: " + empleado.getNombre() + " no está asignado al proyecto " + proyecto.getNombre() + ".");
                return;
            }
            if (!EntradaConsola.confirmar("¿Seguro que desea retirar a " + empleado.getNombre()
                    + " del proyecto " + proyecto.getNombre() + "?")){
                System.out.println("Operación cancelada.");
                return;
            }
            if (empleadoProyectoDAO.eliminarEmpleadoAsignado(empleado, proyecto)){
                System.out.println("Empleado retirado del proyecto. El empleado y el proyecto se conservan.");
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    // Regla de negocio: horas mayores que 0 y que no superen 40
    private int solicitarHoras(){
        return EntradaConsola.ingresarEnteroEnRango("Horas semanales", HORAS_MINIMAS, HORAS_MAXIMAS);
    }
    
    private Empleado solicitarEmpleadoExistente() throws SQLException{
        int id = EntradaConsola.ingresarEntero("ID del empleado");
        Empleado empleado = empleadoDAO.buscarPorId(id);
        if (empleado == null){
            System.out.println("No existe un empleado con el ID " + id + ".");
        }
        return empleado;
    }
    
    private Proyecto solicitarProyectoExistente() throws SQLException{
        int id = EntradaConsola.ingresarEntero("ID del proyecto");
        Proyecto proyecto = proyectosDAO.buscarPorId(id);
        if (proyecto == null){
            System.out.println("No existe un proyecto con el ID " + id + ".");
        }
        return proyecto;
    }
    
    private EmpleadoProyecto solicitarAsignacionExistente() throws SQLException{
        int empleadoId = EntradaConsola.ingresarEntero("ID del empleado");
        int proyectoId = EntradaConsola.ingresarEntero("ID del proyecto");
        EmpleadoProyecto asignacion = empleadoProyectoDAO.buscarAsignacion(empleadoId, proyectoId);
        if (asignacion == null){
            System.out.println("No existe una asignación del empleado " + empleadoId + " en el proyecto " + proyectoId + ".");
        }
        return asignacion;
    }
}
