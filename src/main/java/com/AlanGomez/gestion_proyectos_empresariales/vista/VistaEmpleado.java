package com.AlanGomez.gestion_proyectos_empresariales.vista;

import com.AlanGomez.gestion_proyectos_empresariales.utilidades.EntradaConsola;

public class VistaEmpleado {
    
    public int menuEmpleado(){
        System.out.println("""
                
                ===== EMPLEADOS =====
                1. Registrar empleado
                2. Listar empleados
                3. Buscar empleado por ID
                4. Actualizar empleado
                5. Eliminar empleado
                0. Volver al menú principal""");
        return EntradaConsola.ingresarEnteroEnRango("Seleccione una opción", 0, 5);
    }
    
    public int menuActualizarEmpleado(){
        System.out.println("""
                
                --- ¿Qué desea actualizar? ---
                1. Todos los datos
                2. Nombre
                3. Correo
                4. Cargo
                0. Cancelar""");
        return EntradaConsola.ingresarEnteroEnRango("Seleccione una opción", 0, 4);
    }
}
