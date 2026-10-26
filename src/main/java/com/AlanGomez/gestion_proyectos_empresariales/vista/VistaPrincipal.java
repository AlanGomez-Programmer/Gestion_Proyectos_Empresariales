package com.AlanGomez.gestion_proyectos_empresariales.vista;

import com.AlanGomez.gestion_proyectos_empresariales.utilidades.EntradaConsola;

public class VistaPrincipal {
    
    public int menuPrincipal(){
        System.out.println("""
                
                ===== GESTIÓN DE PROYECTOS EMPRESARIALES =====
                1. Empleados
                2. Proyectos
                3. Asignaciones (Empleado - Proyecto)
                0. Salir""");
        return EntradaConsola.ingresarEnteroEnRango("Seleccione una opción", 0, 3);
    }
}
