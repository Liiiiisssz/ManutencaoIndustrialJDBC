package org.manutencaoindustrial.util;

import org.manutencaoindustrial.view.View;

import java.util.Scanner;

public class Erros {
    public static final Scanner sc = new Scanner(System.in);
    private static String entrada;

    public static int entradaInt(){
        while(true){
            View.opcao();
            entrada = sc.nextLine();
            try{
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e){
                View.texto("Entrada inválida!");
            }
        }
    }

    public static double entradaDouble(){
        while(true){
            View.opcao();
            entrada = sc.nextLine();
            try{
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e){
                View.texto("Entrada inválida!");
            }
        }
    }
}
