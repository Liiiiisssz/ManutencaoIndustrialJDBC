package org.manutencaoindustrial.service;

import org.manutencaoindustrial.dao.MaquinaDAO;
import org.manutencaoindustrial.dao.OrdemManutencaoDAO;
import org.manutencaoindustrial.dao.PecaDAO;
import org.manutencaoindustrial.dao.TecnicoDAO;
import org.manutencaoindustrial.model.*;
import org.manutencaoindustrial.util.Erros;
import org.manutencaoindustrial.view.View;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Cadastro {

    public static Maquina cadastrarMaquina(Scanner sc){
        List<Maquina> listMaquina = MaquinaDAO.validar();
        boolean valido = false;
        View.texto(" __________________________________");
        View.cabecalho("|        CADASTRAR  MÁQUINA        |");
        View.cabecalho("|__________________________________|");

        String nome = null;
        String setor = null;

        while(!valido){
            View.texto("Nome:");
            nome = sc.nextLine();
            View.texto("Setor:");
            setor = sc.nextLine();
            if(listMaquina.isEmpty()){
                valido = true;
            } else {
                for(Maquina m : listMaquina){
                    if (!m.getNome().equalsIgnoreCase(nome) || !m.getSetor().equalsIgnoreCase(setor)){
                        valido = true;
                        break;
                    }
                }
            }
            if(!valido){
                View.texto("Máquina já cadastrada.");
            }
        }
        String status = "OPERACIONAL";

        return new Maquina(nome, setor, status);
    }

    public static Tecnico cadastrarTecnico(Scanner sc){
        List<Tecnico> listTecnico = TecnicoDAO.listarTecnicos();
        boolean valido = false;
        View.texto(" __________________________________");
        View.cabecalho("|        CADASTRAR  TÉCNICO        |");
        View.cabecalho("|__________________________________|");

        String nome = null;
        String especialidade = null;

        while(!valido){
            View.texto("Nome:");
            nome = sc.nextLine();
            View.texto("Especialidade: ");
            especialidade = sc.nextLine();
            if(listTecnico.isEmpty()){
                valido = true;
            } else {
                for(Tecnico t : listTecnico){
                    if(!t.getNome().equalsIgnoreCase(nome) || !t.getEspecialidade().equalsIgnoreCase(especialidade)){
                        valido = true;
                        break;
                    }
                }
            }
            if(!valido){
                View.texto("Técnico já cadastrado.");
            }
        }
        return new Tecnico(nome, especialidade);
    }

    public static Peca cadastrarPeca(Scanner sc){
        List<Peca> listPeca = PecaDAO.listPeca();
        boolean valido = false;
        View.texto(" __________________________________");
        View.cabecalho("|          CADASTRAR PEÇA          |");
        View.cabecalho("|__________________________________|");

        String nome = null;
        double estoque = 0;
        while(!valido){
            View.texto("Nome:");
            nome = sc.nextLine();
            if(listPeca.isEmpty()){
                valido = true;
            } else {
                for(Peca p : listPeca){
                    if(!p.getNome().equalsIgnoreCase(nome)){
                        valido = true;
                        break;
                    }
                }
                if(!valido){
                    View.texto("Peça já cadastrada.");
                }
            }
        }
        while(estoque <= 0){
            View.texto("Quantidade em estoque:");
            estoque = Erros.entradaDouble();
            if(estoque <= 0){
                View.texto("O estoque não pode ser menor que 0.");
            }
        }
        return new Peca(nome, estoque);
    }

    public static OrdemManutencao criarOrdemManutencao(Scanner sc){
        List<Maquina> listMaquina = MaquinaDAO.listarMaquinas();
        List<Tecnico> listTecnico = TecnicoDAO.listarTecnicos();
        boolean valido = false;
        View.texto(" __________________________________");
        View.cabecalho("|    CRIAR ORDEM DE MANUTENÇÃO     |");
        View.cabecalho("|__________________________________|");

        Maquina maquina = null;
        Tecnico tecnico = null;

        if(listMaquina.isEmpty()) {
            View.texto("Nenhuma máquina disponível.");
            return null;
        } else {
            View.texto("Máquinas disponíveis:");
            for(Maquina m : listMaquina){
                View.texto("-----------------------------");
                System.out.println(m);
            }
            while(!valido){
                View.texto("ID da máquina:");
                int id = Erros.entradaInt();

                for(Maquina m : listMaquina){
                    if(m.getId() == id){
                        maquina = m;
                        valido = true;
                        break;
                    }
                }
                if(!valido){
                    View.texto("Máquina inválida.");
                }
            }
            if(listTecnico.isEmpty()){
                View.texto("Nenhum técnico disponível.");
            } else {
                View.texto("Técnicos disponíveis:");
                for(Tecnico t : listTecnico){
                    View.texto("-----------------------------");
                    System.out.println(t);
                }
                valido = false;
                while(!valido){
                    View.texto("ID do técnico:");
                    int id = Erros.entradaInt();

                    for(Tecnico t : listTecnico){
                        if(t.getId() == id){
                            tecnico = t;
                            valido = true;
                            break;
                        }
                    }
                }
            }
            LocalDate data = LocalDate.now();
            String status = "PENDENTE";

            return new OrdemManutencao(maquina, tecnico, data, status);
        }
    }

    public static OrdemPeca criarOrdemPeca(Scanner sc){
        List<OrdemManutencao> listOrdemManutencao = OrdemManutencaoDAO.listarOrdemManutencao();
        boolean valido = false;
        View.texto(" __________________________________");
        View.cabecalho("|      ASSOCIAR PEÇAS À ORDEM      |");
        View.cabecalho("|__________________________________|");

        if(listOrdemManutencao.isEmpty()){
            View.texto("Nenhuma ordem de manutenção disponível.");
        } else {
            View.texto("Ordens de manutenção disponíveis:");
            for(OrdemManutencao o : listOrdemManutencao){
                View.texto("-----------------------------");
                System.out.println(o);
            }
            while(!valido){
                View.texto("ID da ordem de manutenção:");
                int id = Erros.entradaInt();

                for(OrdemManutencao o : listOrdemManutencao){
                    if(o.getId() == id){

                    }
                }
            }
        }
    }
}
