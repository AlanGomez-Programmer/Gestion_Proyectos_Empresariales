package com.AlanGomez.gestion_proyectos_empresariales.controlador;

import com.AlanGomez.gestion_proyectos_empresariales.modelo.clases.Empleado;
import com.AlanGomez.gestion_proyectos_empresariales.modelo.dao.EmpleadoDAO;
import com.AlanGomez.gestion_proyectos_empresariales.utilidades.EntradaConsola;
import com.AlanGomez.gestion_proyectos_empresariales.utilidades.ManejadorErroresBD;
import com.AlanGomez.gestion_proyectos_empresariales.utilidades.TipoTexto;
import com.AlanGomez.gestion_proyectos_empresariales.vista.VistaEmpleado;
import java.sql.SQLException;
import java.util.List;

public class EmpleadoControlador {
    
    private static final int OPCION_VOLVER = 0;
    private static final String FORMATO_FILA = "%-5s %-25s %-30s %-20s%n";
    
    private final VistaEmpleado vista;
    private final EmpleadoDAO empleadoDAO;
    
    public EmpleadoControlador(VistaEmpleado vista, EmpleadoDAO empleadoDAO){
        this.vista = vista;
        this.empleadoDAO = empleadoDAO;
    }
    
    public void iniciar(){
        int opcion;
        do {
            opcion = vista.menuEmpleado();
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
    
    private void registrar(){
        System.out.println("\n--- Registrar empleado ---");
        String nombre = EntradaConsola.ingresarTexto(TipoTexto.SOLO_LETRAS, "Nombre");
        String correo = EntradaConsola.ingresarTexto(TipoTexto.CORREO, "Correo").toLowerCase();
        String cargo = EntradaConsola.ingresarTexto(TipoTexto.ALFANUMERICO, "Cargo");
        try {
            if (empleadoDAO.crear(new Empleado(nombre, correo, cargo))){
                System.out.println("Empleado registrado correctamente.");
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    private void listar(){
        try {
            List<Empleado> empleados = empleadoDAO.listar();
            if (empleados.isEmpty()){
                System.out.println("No hay empleados registrados.");
                return;
            }
            mostrarEncabezado();
            empleados.forEach(this::mostrarEmpleado);
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    private void buscar(){
        try {
            Empleado empleado = solicitarEmpleadoExistente();
            if (empleado != null){
                mostrarEncabezado();
                mostrarEmpleado(empleado);
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    private void actualizar(){
        try {
            Empleado empleado = solicitarEmpleadoExistente();
            if (empleado == null){
                return;
            }
            mostrarEncabezado();
            mostrarEmpleado(empleado);
            
            int opcion = vista.menuActualizarEmpleado();
            if (opcion == OPCION_VOLVER){
                System.out.println("Actualización cancelada.");
                return;
            }
            
            boolean actualizado = switch (opcion) {
                case 1 -> {
                    empleado.setNombre(EntradaConsola.ingresarTexto(TipoTexto.SOLO_LETRAS, "Nuevo nombre"));
                    empleado.setCorreo(EntradaConsola.ingresarTexto(TipoTexto.CORREO, "Nuevo correo").toLowerCase());
                    empleado.setCargo(EntradaConsola.ingresarTexto(TipoTexto.ALFANUMERICO, "Nuevo cargo"));
                    yield empleadoDAO.actualizarTodo(empleado);
                }
                case 2 -> {
                    empleado.setNombre(EntradaConsola.ingresarTexto(TipoTexto.SOLO_LETRAS, "Nuevo nombre"));
                    yield empleadoDAO.actualizarNombre(empleado);
                }
                case 3 -> {
                    empleado.setCorreo(EntradaConsola.ingresarTexto(TipoTexto.CORREO, "Nuevo correo").toLowerCase());
                    yield empleadoDAO.actualizarCorreo(empleado);
                }
                case 4 -> {
                    empleado.setCargo(EntradaConsola.ingresarTexto(TipoTexto.ALFANUMERICO, "Nuevo cargo"));
                    yield empleadoDAO.actualizarCargo(empleado);
                }
                default -> false;
            };
            
            if (actualizado){
                System.out.println("Empleado actualizado correctamente.");
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    private void eliminar(){
        try {
            Empleado empleado = solicitarEmpleadoExistente();
            if (empleado == null){
                return;
            }
            if (!EntradaConsola.confirmar("¿Seguro que desea eliminar a " + empleado.getNombre() + "?")){
                System.out.println("Eliminación cancelada.");
                return;
            }
            if (empleadoDAO.eliminar(empleado)){
                System.out.println("Empleado eliminado correctamente.");
            }
        } catch (SQLException e) {
            ManejadorErroresBD.mostrarError(e);
        }
    }
    
    private Empleado solicitarEmpleadoExistente() throws SQLException{
        int id = EntradaConsola.ingresarEntero("ID del empleado");
        Empleado empleado = empleadoDAO.buscarPorId(id);
        if (empleado == null){
            System.out.println("No existe un empleado con el ID " + id + ".");
        }
        return empleado;
    }
    
    private void mostrarEncabezado(){
        System.out.printf(FORMATO_FILA, "ID", "Nombre", "Correo", "Cargo");
        System.out.println("-".repeat(82));
    }
    
    private void mostrarEmpleado(Empleado empleado){
        System.out.printf(FORMATO_FILA,
                empleado.getId(), empleado.getNombre(), empleado.getCorreo(), empleado.getCargo());
    }
}
