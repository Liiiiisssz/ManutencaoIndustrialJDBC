package org.manutencaoindustrial.dao;

import org.manutencaoindustrial.model.Maquina;
import org.manutencaoindustrial.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaquinaDAO {

    public static void cadastrar(Maquina maquina) throws SQLException{
        String query = """
                INSERT INTO Maquina(nome, setor, status)
                VALUES (?, ?, ?)
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getSetor());
            stmt.setString(3, maquina.getStatus());
            stmt.executeUpdate();
        }
    }

    public static boolean validar(Maquina maquina) {
        String query = """
                SELECT COUNT(0) AS linhas
                FROM Maquina
                WHERE nome = ?
                AND setor = ?
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getSetor());
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt("linhas") > 0) {
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static List<Maquina> listarMaquinas(){
        List<Maquina> maquinas = new ArrayList<>();
        String query = """
                SELECT id, nome, setor, status
                FROM Maquina
                WHERE status = 'OPERACIONAL'
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String setor = rs.getString("setor");
                String status = rs.getString("status");

                var maquina = new Maquina(id, nome, setor, status);
                maquinas.add(maquina);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return maquinas;
    }

    public static void atualizarStatus(Maquina maquina, String status){
        String query = """
                UPDATE Maquina
                SET status = ?
                WHERE id = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, status);
            stmt.setInt(2, maquina.getId());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
