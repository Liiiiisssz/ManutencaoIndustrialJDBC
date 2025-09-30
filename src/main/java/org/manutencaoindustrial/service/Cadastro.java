package org.manutencaoindustrial.service;

import org.manutencaoindustrial.dao.*;
import org.manutencaoindustrial.model.*;
import org.manutencaoindustrial.util.Conexao;
import org.manutencaoindustrial.util.Erros;
import org.manutencaoindustrial.view.View;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cadastro {

    public static void cadastrarMaquina(Scanner sc){
        boolean valido = false;
        View.texto(" __________________________________");
        View.cabecalho("|        CADASTRAR  MÁQUINA        |");
        View.cabecalho("|__________________________________|");

        while(!valido) {
            View.texto("Nome:");
            String nome = sc.nextLine();
            View.texto("Setor:");
            String setor = sc.nextLine();

            var maquina = new Maquina(nome, setor, "OPERACIONAL");
            if (!nome.isEmpty() && !setor.isEmpty()) {
                if (!MaquinaDAO.validar(maquina)) {
                    valido = true;
                    try{
                        MaquinaDAO.cadastrar(maquina);
                        View.texto("Máquina cadastrada com sucesso!");
                    } catch (SQLException e){
                        e.printStackTrace();
                    }
                }
                if (!valido) {
                    View.texto("Máquina já cadastrada.");
                }
            } else {
                View.texto("Os campos não podem ficar em branco.");
            }
        }
    }

    public static void cadastrarTecnico(Scanner sc){
        boolean valido = false;
        View.texto(" __________________________________");
        View.cabecalho("|        CADASTRAR  TÉCNICO        |");
        View.cabecalho("|__________________________________|");

        while(!valido){
            View.texto("Nome:");
            String nome = sc.nextLine();
            View.texto("Especialidade: ");
            String especialidade = sc.nextLine();

            var tecnico = new Tecnico(nome, especialidade);
            if(!nome.isEmpty() && !especialidade.isEmpty()){
                if(!TecnicoDAO.validar(tecnico)){
                    valido = true;
                    try{
                        TecnicoDAO.cadastrar(tecnico);
                        View.texto("Técnico cadastrado com sucesso!");
                    } catch (SQLException e){
                        e.printStackTrace();
                    }
                }
                if(!valido){
                    View.texto("Técnico já cadastrado.");
                }
            } else {
                View.texto("Os campos não podem ficar em branco.");
            }
        }
    }

    public static void cadastrarPeca(Scanner sc){
        boolean valido = false;
        View.texto(" __________________________________");
        View.cabecalho("|          CADASTRAR PEÇA          |");
        View.cabecalho("|__________________________________|");

        while(!valido){
            View.texto("Nome:");
            String nome = sc.nextLine();
            if(!nome.isEmpty()){
                double estoque = 0;
                while(estoque <= 0){
                    View.texto("Quantidade em estoque:");
                    estoque = Erros.entradaDouble();
                }
                if(estoque <= 0){
                    View.texto("O estoque não pode ser menor que 0.");
                } else {
                    var peca = new Peca(nome, estoque);
                    if(!PecaDAO.validar(peca)){
                        valido = true;
                        try{
                            PecaDAO.cadastrar(peca);
                            View.texto("Peça cadastrada com sucesso!");
                        } catch (SQLException e){
                            e.printStackTrace();
                        }
                    } else {
                        View.texto("Peça já cadastrada.");
                    }
                }
            } else {
                View.texto("Os campos não podem ficar em branco");
            }
        }
    }

    public static void criarOrdemManutencao(Scanner sc){
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
                    if(!valido){
                        View.texto("Técnico inválido.");
                    }
                }
            }
            LocalDate data = LocalDate.now();
            var ordem = new OrdemManutencao(maquina, tecnico, data, "PENDENTE");

            Connection conn = null;
            try{
                conn = Conexao.conectar();
                conn.setAutoCommit(false);

                OrdemManutencaoDAO.criar(ordem);
                MaquinaDAO.atualizarStatus(maquina, "EM_MANUTENCAO");

                conn.commit();
                View.texto("Ordem de manutenção criada com sucesso!");
            } catch (SQLException e){
                try{
                    conn.rollback();
                    conn.close();
                } catch (SQLException e2){
                    e2.printStackTrace();
                }
                View.texto("Erro ao conectar ao banco");
            } finally {
                try{
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void criarOrdemPeca(Scanner sc){
        List<OrdemManutencao> listOrdemManutencao = OrdemManutencaoDAO.listarOrdemManutencao();
        List<Peca> listPeca = PecaDAO.listPeca();
        boolean valido = false;
        View.texto(" __________________________________");
        View.cabecalho("|      ASSOCIAR PEÇAS À ORDEM      |");
        View.cabecalho("|__________________________________|");

        OrdemManutencao ordemManutencao = null;
        Peca peca = null;

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
                        ordemManutencao = o;
                        valido = true;
                        break;
                    }
                }
                if(!valido){
                    View.texto("Ordem de manutenção inválida.");
                }
            }
            valido = false;
            if(listPeca.isEmpty()){
                View.texto("Nenhuma peça disponível.");
            } else {
                View.texto("Peças disponíveis:");
                for(Peca p : listPeca){
                    View.texto("-----------------------------");
                    System.out.println(p);
                }
                while(!valido){
                    View.texto("ID da peça:");
                    int id = Erros.entradaInt();

                    for(Peca p : listPeca){
                        if(p.getId() == id){
                            peca = p;
                            valido = true;
                            break;
                        }
                    }
                    if(!valido){
                        View.texto("Peça inválida.");
                    }
                }
                double quantidade = 0;
                while(quantidade <= 0){
                    View.texto("Quantidade de peças necessária:");
                    quantidade = Erros.entradaDouble();
                }
                var ordemPeca = new OrdemPeca(ordemManutencao, peca, quantidade);
                try{
                    OrdemPecaDAO.criar(ordemPeca);
                    View.texto("Associação criada com sucesso!");
                } catch (SQLException e){
                    View.texto("Não foi possível associar a peça.");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void executarManutencao(Scanner sc){
        List<OrdemManutencao> listOrdem = OrdemManutencaoDAO.listarOrdemManutencao();
        List<OrdemPeca> listOrdemPeca = OrdemPecaDAO.listarOrdemPeca();
        List<Peca> listPeca = PecaDAO.listPeca();
        List<Maquina> listMaquina = MaquinaDAO.listarMaquinas();
        boolean valido = false;

        View.texto(" __________________________________");
        View.cabecalho("|       EXECUTAR  MANUTENÇÃO       |");
        View.cabecalho("|__________________________________|");

        OrdemManutencao ordemManutencao = new OrdemManutencao();
        OrdemPeca ordemPeca = new OrdemPeca();
        Peca peca = new Peca();
        Maquina maquina = new Maquina();

        if(listOrdem.isEmpty()){
            View.texto("Nenhuma ordem de manutenção ativa.");
        } else {
            View.texto("Ordens de manutenção disponíveis:");
            for(OrdemManutencao o : listOrdem){
                System.out.println(o);
            }
            while(!valido){
                View.texto("ID da ordem de manutenção:");
                int id = Erros.entradaInt();

                for(OrdemManutencao o : listOrdem){
                    if(o.getId() == id){
                        ordemManutencao = o;
                        valido = true;
                        break;
                    }
                }
                if(!valido){
                    View.texto("Ordem de manutenção inválida.");
                }
            }
            valido = false;
            for(OrdemPeca o : listOrdemPeca){
                if(o.getOrdem().getId() == ordemManutencao.getId()){
                    ordemPeca = o;
                    valido = true;
                }
            }
            if(!valido){
                View.texto("Ordem de manutenção não possui uma peça cadastrada.");

            } else {
                for(Peca p : listPeca){
                    System.out.println(p);
                    if(p.getId() == ordemPeca.getPeca().getId()){
                        peca = p;
                    }
                }
                for(Maquina m : listMaquina){
                    if(m.getId() == ordemManutencao.getMaquina().getId()){
                        maquina = m;
                    }
                }

                if(!OrdemPecaDAO.verificarEstoque(ordemPeca)){
                    View.texto("Quantidade em estoque menor do que quantidade necessária.");
                } else {
                    PecaDAO.atualizarEstoque(peca, ordemPeca);
                    OrdemManutencaoDAO.atualizarStatus(ordemManutencao);
                    MaquinaDAO.atualizarStatus(maquina, "OPERACIONAL");
                    View.texto("Manutenção executada com sucesso!");
                }
            }
        }
    }
}
