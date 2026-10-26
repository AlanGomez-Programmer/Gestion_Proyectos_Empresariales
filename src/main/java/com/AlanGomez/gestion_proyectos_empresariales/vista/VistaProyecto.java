package com.AlanGomez.gestion_proyectos_empresariales.vista;

import com.AlanGomez.gestion_proyectos_empresariales.modelo.clases.Estado;
import com.AlanGomez.gestion_proyectos_empresariales.utilidades.EntradaConsola;

public class VistaProyecto {
    
    public int menuProyecto(){
        System.out.println("""
                
                ===== PROYECTOS =====
                1. Registrar proyecto
                2. Listar proyectos
                3. Buscar proyecto por ID
                4. Actualizar proyecto
                5. Eliminar proyecto
                0. Volver al menú principal""");
        return EntradaConsola.ingresarEnteroEnRango("Seleccione una opción", 0, 5);
    }
    
    public int menuActualizarProyecto(){
        System.out.println("""
                
                --- ¿Qué desea actualizar? ---
                1. Todos los datos
                2. Nombre
                3. Descripción
                4. Fecha de finalización
                5. Estado
                0. Cancelar""");
        return EntradaConsola.ingresarEnteroEnRango("Seleccione una opción", 0, 5);
    }
    
    public Estado menuEstado(){
        Estado[] estados = Estado.values();
        System.out.println("\n--- Estado del proyecto ---");
        for (Estado estado : estados){
            System.out.println((estado.ordinal() + 1) + ". " + estado.getNombre());
        }
        int opcion = EntradaConsola.ingresarEnteroEnRango("Seleccione el estado", 1, estados.length);
        return estados[opcion - 1];
    }
}
