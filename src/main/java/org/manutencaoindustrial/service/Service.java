package org.manutencaoindustrial.service;

import org.manutencaoindustrial.dao.*;
import org.manutencaoindustrial.util.Erros;
import org.manutencaoindustrial.view.View;

import java.sql.SQLException;

import static org.manutencaoindustrial.util.Erros.sc;

public class Service {

    public static void executar(){
        int opcao = -1;
        while(opcao != 0){
            View.menu();
            opcao = Erros.entradaInt();
            switch (opcao){
                case 1 ->
                    Cadastro.cadastrarMaquina(sc);

                case 2 ->
                    Cadastro.cadastrarTecnico(sc);

                case 3 ->
                    Cadastro.cadastrarPeca(sc);

                case 4 ->
                    Cadastro.criarOrdemManutencao(sc);

                case 5 ->
                    Cadastro.criarOrdemPeca(sc);

                case 6 ->
                    Cadastro.executarManutencao(sc);

                case 0 ->{
                    opcao = 0;
                    View.texto("Sistema encerrado.");
                }
            }
        }
    }
}
