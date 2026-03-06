import dao.TarefaDAO;
import model.Tarefa;

public class Main {

    public static void main(String[] args) {

        TarefaDAO dao = new TarefaDAO();

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Estudar API");
        tarefa.setDescricao("Finalizar CRUD");
        tarefa.setStatus("Pendente");

        dao.criarTarefa(tarefa);

    }
}