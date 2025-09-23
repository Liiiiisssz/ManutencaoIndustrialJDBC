package org.manutencaoindustrial.dao;

import org.manutencaoindustrial.model.Maquina;
import org.manutencaoindustrial.model.OrdemManutencao;
import org.manutencaoindustrial.model.Tecnico;
import org.manutencaoindustrial.util.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdemManutencaoDAO {

    public static void criar(OrdemManutencao ordemManutencao) throws SQLException{
        String query = """
                INSERT INTO OrdemManutencao
                (idMaquina, idTecnico, dataSolicitacao, status)
                VALUES (?, ?, ?, ?);
                UPDATE Maquina SET status = 'EM_MANUTENCAO' WHERE id = ?;
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, ordemManutencao.getMaquina().getId());
            stmt.setInt(2, ordemManutencao.getTecnico().getId());
            stmt.setDate(3, Date.valueOf(ordemManutencao.getDataSolicitacao()));
            stmt.setString(4, ordemManutencao.getStatus());
            stmt.setInt(5, ordemManutencao.getMaquina().getId());
            stmt.executeUpdate();
        }
    }

    public static List<OrdemManutencao> listarOrdemManutencao(){
        List<OrdemManutencao> ordens = new ArrayList<>();
        List<Maquina> listMaquina = MaquinaDAO.listarMaquinas();
        List<Tecnico> listTecnico = TecnicoDAO.listarTecnicos();

        String query = """
                SELECT 
                id, idMaquina, idTecnico, dataSolicitacao, status
                FROM OrdemManutencao
                WHERE status = 'PENDENTE'
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Maquina maquina = new Maquina();
                Tecnico tecnico = new Tecnico();

                int id = rs.getInt("id");
                int idMaquina = rs.getInt("idMaquina");
                for(Maquina m : listMaquina){
                    if(m.getId() == idMaquina){
                        maquina = m;
                        break;
                    }
                }
                int idTecnico = rs.getInt("idTecnico");
                for(Tecnico t : listTecnico){
                    if(t.getId() == idTecnico){
                        tecnico = t;
                        break;
                    }
                }
                LocalDate data = rs.getDate("dataSolicitacao").toLocalDate();
                String status = rs.getString("status");

                var ordem = new OrdemManutencao(id, maquina, tecnico, data, status);
                ordens.add(ordem);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ordens;
    }

    public static void atualizarStatus(OrdemManutencao ordem){
        String query = """
                UPDATE OrdemManutencao
                SET status = 'EXECUTADA'
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, ordem.getId());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
