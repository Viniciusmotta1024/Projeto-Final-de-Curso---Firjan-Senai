package dao;

import database.Conexao;
import model.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    // CRIAR TAREFA
    public void criarTarefa(Tarefa tarefa){

        String sql = "INSERT INTO tarefas (titulo, descricao, prioridade, status, data_limite) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setString(3, tarefa.getPrioridade());
            stmt.setString(4, tarefa.getStatus());

            if(tarefa.getDataLimite() == null || tarefa.getDataLimite().isEmpty()){
                stmt.setNull(5, java.sql.Types.DATE);
            }else{
                stmt.setString(5, tarefa.getDataLimite());
            }

            stmt.executeUpdate();

        }catch(Exception e){
            System.out.println("Erro ao criar tarefa:");
            e.printStackTrace();
        }
    }


    // LISTAR TAREFAS
    public List<Tarefa> listarTarefas(){

        List<Tarefa> lista = new ArrayList<>();

        String sql = "SELECT * FROM tarefas ORDER BY id";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while(rs.next()){

                Tarefa tarefa = new Tarefa();

                tarefa.setId(rs.getInt("id"));
                tarefa.setTitulo(rs.getString("titulo"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setPrioridade(rs.getString("prioridade"));
                tarefa.setStatus(rs.getString("status"));
                tarefa.setDataLimite(rs.getString("data_limite"));

                lista.add(tarefa);
            }

        }catch(Exception e){
            System.out.println("Erro ao listar tarefas:");
            e.printStackTrace();
        }

        return lista;
    }


    // ATUALIZAR STATUS
    public void atualizarStatus(int id, String status){

        String sql = "UPDATE tarefas SET status = ? WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, status);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        }catch(Exception e){
            System.out.println("Erro ao atualizar status:");
            e.printStackTrace();
        }

    }


    // DELETAR TAREFA
    public void deletarTarefa(int id){

        String sql = "DELETE FROM tarefas WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id);

            stmt.executeUpdate();

        }catch(Exception e){
            System.out.println("Erro ao deletar tarefa:");
            e.printStackTrace();
        }

    }

}