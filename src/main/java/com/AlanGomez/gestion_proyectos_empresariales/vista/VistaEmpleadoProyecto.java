package com.AlanGomez.gestion_proyectos_empresariales.vista;

import com.AlanGomez.gestion_proyectos_empresariales.utilidades.EntradaConsola;

public class VistaEmpleadoProyecto {
    
    public int menuEmpleadoProyecto(){
        System.out.println("""
                
                ===== ASIGNACIONES (EMPLEADO - PROYECTO) =====
                1. Asignar empleado a proyecto
                2. Consultar integrantes de un proyecto
                3. Consultar proyectos de un empleado
                4. Modificar asignación
                5. Retirar empleado de un proyecto
                0. Volver al menú principal""");
        return EntradaConsola.ingresarEnteroEnRango("Seleccione una opción", 0, 5);
    }
    
    public int menuActualizarAsignacion(){
        System.out.println("""
                
                --- ¿Qué desea actualizar? ---
                1. Rol y horas semanales
                2. Rol
                3. Horas semanales
                0. Cancelar""");
        return EntradaConsola.ingresarEnteroEnRango("Seleccione una opción", 0, 3);
    }
}
