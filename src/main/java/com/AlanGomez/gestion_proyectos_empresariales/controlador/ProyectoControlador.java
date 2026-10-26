package com.AlanGomez.gestion_proyectos_empresariales.controlador;

import com.AlanGomez.gestion_proyectos_empresariales.modelo.clases.Estado;
import com.AlanGomez.gestion_proyectos_empresariales.modelo.clases.Proyecto;
import com.AlanGomez.gestion_proyectos_empresariales.modelo.dao.ProyectosDAO;
import com.AlanGomez.gestion_proyectos_empresariales.utilidades.EntradaConsola;
import com.AlanGomez.gestion_proyectos_empresariales.utilidades.ManejadorErroresBD;
import com.AlanGomez.gestion_proyectos_empresariales.utilidades.TipoTexto;
import com.AlanGomez.gestion_proyectos_empresariales.vista.VistaProyecto;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProyectoControlador {
    
    private static final int OPCION_VOLVER = 0;
    private static final String FORMATO_FILA = "%-5s %-30s %-12s %-12s %-12s%n";
    
    private final VistaProyecto vista;
    private final ProyectosDAO proyectosDAO;
    
    public ProyectoControlador(VistaProyecto vista, ProyectosDAO proyectosDAO){
        this.vista = vista;
        this.proyectosDAO = proyectosDAO;
    }
    
    public void iniciar(){
        int opcion;
        do {
            opcion = vista.menuProyecto();
            switch (opcion) {
                case 1 -> registrar();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> actualizar();
                case 5 -> eliminar();
                case OPCION_VOLVER -> System.out.println("Volviendo al menú principal...");
            }
        } while (opcion != OPCION_VOLVER);
    }
    
    // RF-04: Registrar proyectos
    private void registrar(){
        System.out.println("\n--- Registrar proyecto ---");
        String nombre = EntradaConsola.ingresarTexto(TipoTexto.ALFANUMERICO, "Nombre");
        String descripcion = EntradaConsola.ingresarTexto(TipoTexto.ALFANUMERICO, "Descripción");
        LocalDate fechaInicio = EntradaConsola.ingresarFecha("Fecha de inicio");
        LocalDate fechaFin = solicitarFechaFin(fechaInicio);
        Estado estado = vista.menuEstado();
        try {
            if (proyectosDAO.crear(new Proyecto(nombre, descripcion, fechaInicio, fechaFin, estado))){
                System.out.println("Proyecto registrado correctamente.");
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    // RF-05: Listar proyectos
    private void listar(){
        try {
            List<Proyecto> proyectos = proyectosDAO.listar();
            if (proyectos.isEmpty()){
                System.out.println("No hay proyectos registrados.");
                return;
            }
            mostrarEncabezado();
            proyectos.forEach(this::mostrarProyecto);
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    // RF-05: Buscar proyecto por ID
    private void buscar(){
        try {
            Proyecto proyecto = solicitarProyectoExistente();
            if (proyecto != null){
                mostrarDetalle(proyecto);
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    // RF-06: Actualizar proyectos
    private void actualizar(){
        try {
            Proyecto proyecto = solicitarProyectoExistente();
            if (proyecto == null){
                return;
            }
            mostrarDetalle(proyecto);
            
            int opcion = vista.menuActualizarProyecto();
            if (opcion == OPCION_VOLVER){
                System.out.println("Actualización cancelada.");
                return;
            }
            
            boolean actualizado = switch (opcion) {
                case 1 -> {
                    proyecto.setNombre(EntradaConsola.ingresarTexto(TipoTexto.ALFANUMERICO, "Nuevo nombre"));
                    proyecto.setDescripcion(EntradaConsola.ingresarTexto(TipoTexto.ALFANUMERICO, "Nueva descripción"));
                    proyecto.setFechaFin(solicitarFechaFin(proyecto.getFechaInicio()));
                    proyecto.setEstado(vista.menuEstado());
                    yield proyectosDAO.actualizarTodo(proyecto);
                }
                case 2 -> {
                    proyecto.setNombre(EntradaConsola.ingresarTexto(TipoTexto.ALFANUMERICO, "Nuevo nombre"));
                    yield proyectosDAO.actualizarNombre(proyecto);
                }
                case 3 -> {
                    proyecto.setDescripcion(EntradaConsola.ingresarTexto(TipoTexto.ALFANUMERICO, "Nueva descripción"));
                    yield proyectosDAO.actualizarDescripcion(proyecto);
                }
                case 4 -> {
                    proyecto.setFechaFin(solicitarFechaFin(proyecto.getFechaInicio()));
                    yield proyectosDAO.actualizarFechaFin(proyecto);
                }
                case 5 -> {
                    proyecto.setEstado(vista.menuEstado());
                    yield proyectosDAO.actualizarEstado(proyecto);
                }
                default -> false;
            };
            
            if (actualizado){
                System.out.println("Proyecto actualizado correctamente.");
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    // RF-06: Eliminar proyectos (la llave foránea impide eliminar si tiene asignaciones)
    private void eliminar(){
        try {
            Proyecto proyecto = solicitarProyectoExistente();
            if (proyecto == null){
                return;
            }
            if (!EntradaConsola.confirmar("¿Seguro que desea eliminar el proyecto " + proyecto.getNombre() + "?")){
                System.out.println("Eliminación cancelada.");
                return;
            }
            if (proyectosDAO.eliminar(proyecto)){
                System.out.println("Proyecto eliminado correctamente.");
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    // Regla de negocio: la fecha de fin no puede ser anterior a la de inicio
    private LocalDate solicitarFechaFin(LocalDate fechaInicio){
        while (true){
            LocalDate fechaFin = EntradaConsola.ingresarFecha("Fecha de finalización");
            if (!fechaFin.isBefore(fechaInicio)){
                return fechaFin;
            }
            System.out.println("Error: La fecha de finalización no puede ser anterior a la de inicio (" + fechaInicio + ").");
        }
    }
    
    // Pide un ID y devuelve el proyecto, o null si no existe
    private Proyecto solicitarProyectoExistente() throws SQLException{
        int id = EntradaConsola.ingresarEntero("ID del proyecto");
        Proyecto proyecto = proyectosDAO.buscarPorId(id);
        if (proyecto == null){
            System.out.println("No existe un proyecto con el ID " + id + ".");
        }
        return proyecto;
    }
    
    private void mostrarEncabezado(){
        System.out.printf(FORMATO_FILA, "ID", "Nombre", "Inicio", "Fin", "Estado");
        System.out.println("-".repeat(75));
    }
    
    private void mostrarProyecto(Proyecto proyecto){
        System.out.printf(FORMATO_FILA,
                proyecto.getId(), proyecto.getNombre(), proyecto.getFechaInicio(),
                proyecto.getFechaFin(), proyecto.getEstado().getNombre());
    }
    
    private void mostrarDetalle(Proyecto proyecto){
        System.out.println("\nID:          " + proyecto.getId());
        System.out.println("Nombre:      " + proyecto.getNombre());
        System.out.println("Descripción: " + proyecto.getDescripcion());
        System.out.println("Inicio:      " + proyecto.getFechaInicio());
        System.out.println("Fin:         " + proyecto.getFechaFin());
        System.out.println("Estado:      " + proyecto.getEstado().getNombre());
    }
}
