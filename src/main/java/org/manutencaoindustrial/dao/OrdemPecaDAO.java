package org.manutencaoindustrial.dao;

import org.manutencaoindustrial.model.*;
import org.manutencaoindustrial.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdemPecaDAO {

    public static void criar(OrdemPeca ordemPeca) throws SQLException{
        String query = """
                INSERT INTO OrdemPeca
                (idOrdem, idPeca, quantidade)
                VALUES (?, ?, ?)
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, ordemPeca.getOrdem().getId());
            stmt.setInt(2, ordemPeca.getPeca().getId());
            stmt.setDouble(3, ordemPeca.getQuantidade());
            stmt.executeUpdate();
        }
    }

    public static boolean verificarEstoque(OrdemPeca ordemPeca){
        double estoque = 0;
        double quantidade = 0;
        String query = """
                SELECT 
                    op.quantidade,
                    p.estoque
                FROM OrdemPeca op
                JOIN Peca p ON op.idPeca = p.id
                WHERE op.idOrdem = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, ordemPeca.getOrdem().getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                estoque = rs.getDouble("estoque");
                quantidade = rs.getDouble("quantidade");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        if(estoque >= quantidade){
            return true;
        } else {
            return false;
        }
    }

    public static List<OrdemPeca> listarOrdemPeca(){
        List<OrdemPeca> ordens = new ArrayList<>();
        List<OrdemManutencao> listOrdemManutencao = OrdemManutencaoDAO.listarOrdemManutencao();
        List<Peca> listPeca = PecaDAO.listPeca();

        String query = """
                SELECT
                    op.idOrdem, op.idPeca, op.quantidade,
                    p.nome AS peca_nome,
                    p.estoque AS peca_estoque
                FROM OrdemPeca op
                JOIN Peca p ON op.idPeca = p.id
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                OrdemManutencao ordemManutencao = new OrdemManutencao();
                Peca peca = new Peca();

                int idOrdem = rs.getInt("idOrdem");
                for(OrdemManutencao o : listOrdemManutencao){
                    if(o.getId() == idOrdem){
                        ordemManutencao = o;
                        break;
                    }
                }
                int idPeca = rs.getInt("idPeca");
                for(Peca p : listPeca){
                    if(p.getId() == idPeca){
                        peca = p;
                        break;
                    }
                }
                double quantidade = rs.getDouble("quantidade");

                var ordem = new OrdemPeca(ordemManutencao, peca, quantidade);
                ordens.add(ordem);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ordens;
    }
}
