package com.mycompany.sistemadeestoque.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Classe de conexão com o banco de dados.
public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/estoque_java";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            //Testar conexão.
            //System.out.println("Conexão com o banco de dados realizada com sucesso!");
            return conn;
        } catch (SQLException e) {
           
              throw new RuntimeException("Erro ao conectar ao banco de dados.", e);
        }
    }
}
