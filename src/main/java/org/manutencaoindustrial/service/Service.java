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

                case 4 ->{
                    var ordemManutencao = Cadastro.criarOrdemManutencao(sc);
                    if(ordemManutencao != null){
                        try{
                            OrdemManutencaoDAO.criar(ordemManutencao);
                            View.texto("Ordem de manutenção criada com sucesso!");
                        } catch (SQLException e){
                            View.texto("Não foi possível criar a ordem de manutenção.");
                        }
                    }
                }
                case 5 ->{
                    var ordemPeca = Cadastro.criarOrdemPeca(sc);
                    if(ordemPeca != null){
                        try{
                            OrdemPecaDAO.criar(ordemPeca);
                            View.texto("Associação realizada com sucesso!");
                        } catch (SQLException e){
                            View.texto("Não foi possível associar a peça.");
                            e.printStackTrace();
                        }
                    }
                }
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
