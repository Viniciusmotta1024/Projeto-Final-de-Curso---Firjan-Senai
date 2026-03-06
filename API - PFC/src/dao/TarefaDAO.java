package dao;

import database.Conexao;
import model.Tarefa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    // CREATE
    public void criarTarefa(Tarefa tarefa) {

        String sql = "INSERT INTO tarefas (titulo, descricao, status) VALUES (?, ?, ?)";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setString(3, tarefa.getStatus());

            stmt.executeUpdate();

            System.out.println("Tarefa criada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // READ
    public List<Tarefa> listarTarefas() {

        List<Tarefa> lista = new ArrayList<>();

        String sql = "SELECT * FROM tarefas";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Tarefa tarefa = new Tarefa();

                tarefa.setId(rs.getInt("id"));
                tarefa.setTitulo(rs.getString("titulo"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setStatus(rs.getString("status"));

                lista.add(tarefa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    // UPDATE
    public void atualizarTarefa(Tarefa tarefa) {

        String sql = "UPDATE tarefas SET titulo = ?, descricao = ?, status = ? WHERE id = ?";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setString(3, tarefa.getStatus());
            stmt.setInt(4, tarefa.getId());

            stmt.executeUpdate();

            System.out.println("Tarefa atualizada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // DELETE
    public void deletarTarefa(int id) {

        String sql = "DELETE FROM tarefas WHERE id = ?";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("Tarefa deletada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}