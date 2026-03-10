package controller;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import dao.TarefaDAO;
import model.Tarefa;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiServidor {

    public static void iniciar() throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/tarefas", (HttpExchange exchange) -> {

            // CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            TarefaDAO dao = new TarefaDAO();

            String metodo = exchange.getRequestMethod();

            try {

                // LISTAR TAREFAS
                if ("GET".equals(metodo)) {

                    List<Tarefa> lista = dao.listarTarefas();

                    StringBuilder json = new StringBuilder("[");
                    for (int i = 0; i < lista.size(); i++) {

                        Tarefa t = lista.get(i);

                        json.append("{")
                                .append("\"id\":").append(t.getId()).append(",")
                                .append("\"titulo\":\"").append(t.getTitulo()).append("\",")
                                .append("\"descricao\":\"").append(t.getDescricao()).append("\",")
                                .append("\"status\":\"").append(t.getStatus()).append("\"")
                                .append("}");

                        if (i < lista.size() - 1) {
                            json.append(",");
                        }
                    }

                    json.append("]");

                    byte[] response = json.toString().getBytes(StandardCharsets.UTF_8);

                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, response.length);

                    OutputStream os = exchange.getResponseBody();
                    os.write(response);
                    os.close();
                }

                // CRIAR TAREFA
                else if ("POST".equals(metodo)) {

                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)
                    );

                    String body = br.readLine();
                    br.close();

                    String titulo = body.split("\"titulo\":\"")[1].split("\"")[0];
                    String descricao = body.split("\"descricao\":\"")[1].split("\"")[0];

                    Tarefa nova = new Tarefa(titulo, descricao, "Pendente");

                    dao.criarTarefa(nova);

                    String response = "Tarefa criada";

                    exchange.sendResponseHeaders(200, response.length());

                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }

                // DELETAR TAREFA
                else if ("DELETE".equals(metodo)) {

                    String query = exchange.getRequestURI().getQuery();

                    if (query != null && query.contains("id=")) {

                        int id = Integer.parseInt(query.split("=")[1]);

                        dao.deletarTarefa(id);

                        String response = "Tarefa deletada";

                        exchange.sendResponseHeaders(200, response.length());

                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }
                }

                // ATUALIZAR STATUS
                else if ("PUT".equals(metodo)) {

                    String query = exchange.getRequestURI().getQuery();

                    if (query != null && query.contains("id=")) {

                        int id = Integer.parseInt(query.split("=")[1]);

                        dao.atualizarStatus(id, "Concluída");

                        String response = "Status atualizado";

                        exchange.sendResponseHeaders(200, response.length());

                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }
                }

            } catch (Exception e) {

                e.printStackTrace();

                String response = "Erro interno no servidor";

                exchange.sendResponseHeaders(500, response.length());

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }

        });

        server.start();

        System.out.println("Servidor rodando em http://localhost:8080");
    }
}