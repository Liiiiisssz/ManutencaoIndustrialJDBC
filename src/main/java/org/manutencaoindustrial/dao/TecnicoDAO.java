package org.manutencaoindustrial.dao;

import org.manutencaoindustrial.model.Tecnico;
import org.manutencaoindustrial.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDAO {

    public static void cadastrar(Tecnico tecnico) throws SQLException{
        String query = """
                INSERT INTO Tecnico (nome, especialidade)
                VALUES (?, ?)
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, tecnico.getNome());
            stmt.setString(2, tecnico.getEspecialidade());
            stmt.executeUpdate();
        }
    }

    public static List<Tecnico> listarTecnicos(){
        List<Tecnico> tecnicos = new ArrayList<>();
        String query = """
                SELECT id, nome, especialidade
                FROM Tecnico
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String especialidade = rs.getString("especialidade");

                var tecnico = new Tecnico(id, nome, especialidade);
                tecnicos.add(tecnico);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return tecnicos;
    }
}
