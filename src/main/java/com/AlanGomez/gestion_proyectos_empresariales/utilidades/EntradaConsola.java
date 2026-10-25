package com.AlanGomez.gestion_proyectos_empresariales.utilidades;

import java.util.Objects;
import java.util.Scanner;

public class EntradaConsola {
    
    private static Scanner input = new Scanner(System.in);
    
    // Método para ingresar texto
    public static String ingresarTexto(TipoTexto tipo, String mensaje){
        Objects.requireNonNull(tipo, "El tipo de texto no puede ser nulo");
        while(true){
            System.out.print(mensaje + ": ");
            String entrada = input.nextLine().trim();
            if (entrada.isEmpty()){
                System.out.println("Error: El texto no puede estar vacío");
            } else if (!tipo.esValido(entrada)){
                System.out.printf("Error: %s. %n", tipo.getMensajeError());
            } else {
                return entrada;
            }
        }
    }
    
    // Método para ingresar números enteros
    public static int ingresarEntero(String mensaje){
        while(true){
            int num = Integer.parseInt(ingresarTexto(TipoTexto.ALFANUMERICO, mensaje));
            boolean numeroAceptado = (num > 0);
            if (!numeroAceptado){
                System.out.println("Error: No se permiten números menores o iguales a 0");
            } else {
                return num;
            }
        }
    }    
}
