import controller.ApiServidor;

public class Main {

    public static void main(String[] args) {

        try {

            System.out.println("Iniciando servidor...");
            ApiServidor.iniciar();
            System.out.println("API rodando em http://localhost:8080/tarefas");

        } catch (Exception e) {

            System.out.println("Erro ao iniciar servidor:");
            e.printStackTrace();

        }

    }

}