package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/PFC";
    private static final String USER = "root";
    private static final String PASSWORD = "senai2024";

    public static Connection conectar() {

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado ao banco!");

        } catch (SQLException e) {

            System.out.println("Erro ao conectar!");
            e.printStackTrace();

        }

        return conn;
    }
}