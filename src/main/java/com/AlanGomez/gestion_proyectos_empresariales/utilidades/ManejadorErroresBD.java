package com.AlanGomez.gestion_proyectos_empresariales.utilidades;

import java.sql.SQLException;

public final class ManejadorErroresBD {
    
    // Códigos de error de MySQL
    private static final String ESTADO_TRIGGER = "45000";
    private static final int DUPLICADO = 1062;
    private static final int TIENE_REGISTROS_RELACIONADOS = 1451;
    private static final int REFERENCIA_INEXISTENTE = 1452;
    private static final int TEXTO_MUY_LARGO = 1406;
    private static final int RESTRICCION_CHECK = 3819;
    
    private ManejadorErroresBD(){
        // Clase de utilidad: no debe instanciarse
    }
    
    // Traduce los errores de MySQL a mensajes entendibles para el usuario
    public static void mostrarError(SQLException e){
        if (ESTADO_TRIGGER.equals(e.getSQLState())){
            System.out.println(e.getMessage()); // Mensaje definido en el trigger
            return;
        }
        String mensaje = switch (e.getErrorCode()){
            case DUPLICADO -> "Error: Ya existe un registro con ese dato (correo, nombre de proyecto o asignación repetida).";
            case TIENE_REGISTROS_RELACIONADOS -> "Error: No se puede eliminar porque tiene asignaciones relacionadas.";
            case REFERENCIA_INEXISTENTE -> "Error: El empleado o el proyecto indicado no existe.";
            case TEXTO_MUY_LARGO -> "Error: Uno de los textos supera la longitud permitida.";
            case RESTRICCION_CHECK -> "Error: Un dato no cumple las reglas de la base de datos.";
            default -> "Error de base de datos: " + e.getMessage();
        };
        System.out.println(mensaje);
    }
}
