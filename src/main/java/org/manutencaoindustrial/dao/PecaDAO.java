package org.manutencaoindustrial.dao;

import org.manutencaoindustrial.model.OrdemPeca;
import org.manutencaoindustrial.model.Peca;
import org.manutencaoindustrial.model.Tecnico;
import org.manutencaoindustrial.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {

    public static void cadastrar(Peca peca) throws SQLException{
        String query = """
                INSERT INTO Peca (nome, estoque)
                VALUES (?, ?)
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getEstoque());
            stmt.executeUpdate();
        }
    }

    public static boolean validar(Peca peca) {
        String query = """
                SELECT COUNT(0) AS linhas
                FROM Peca
                WHERE nome = ?
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, peca.getNome());
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt("linhas") > 0) {
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static List<Peca> listPeca(){
        List<Peca> pecas = new ArrayList<>();
        String query = """
                SELECT id, nome, estoque
                FROM Peca
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double estoque = rs.getDouble("estoque");

                var peca = new Peca(id, nome, estoque);
                pecas.add(peca);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return pecas;
    }

    public static void atualizarEstoque(Peca peca, OrdemPeca ordemPeca){
        String query = """
                UPDATE Peca 
                SET estoque = estoque - ?
                WHERE id = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setDouble(1, ordemPeca.getQuantidade());
            stmt.setInt(2, peca.getId());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
