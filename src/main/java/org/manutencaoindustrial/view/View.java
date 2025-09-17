package org.manutencaoindustrial.view;

public class View {

    public static void menu(){
        System.out.println("\n __________________________________");
        System.out.println("| SISTEMA DE MANUTENÇÃO INDUSTRIAL |");
        System.out.println("|----------------------------------|");
        System.out.println("| 1. Cadastrar máquina             |");
        System.out.println("| 2. Cadastrar técnico             |");
        System.out.println("| 3. Cadastrar peça                |");
        System.out.println("| 4. Criar ordem de manutenção     |");
        System.out.println("| 5. Associar peças à ordem        |");
        System.out.println("| 6. Executar manutenção           |");
        System.out.println("|----------------------------------|");
        System.out.println("| 0. Sair                          |");
        System.out.println("|__________________________________|");
    }

    public static void opcao(){
        System.out.print("> ");
    }

    public static void texto(String texto){
        System.out.println("\n" + texto);
    }

    public static void cabecalho(String texto){
        System.out.println(texto);
    }
}
