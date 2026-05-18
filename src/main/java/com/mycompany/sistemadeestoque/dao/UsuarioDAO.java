package com.mycompany.sistemadeestoque.dao;

import com.mycompany.sistemadeestoque.util.Conexao;
import com.mycompany.sistemadeestoque.models.Usuario;
import com.mycompany.sistemadeestoque.util.SenhaUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    //Método de cadastro de usuário.
    public boolean cadastrar(Usuario user){
        String sql = "insert into usuarios (usuario, senha_hash) values (?, ?)";
       
        if(usuarioExiste(user.getUsuario())){
            throw new IllegalArgumentException("Usuário já existe!");
        }

        try(
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){

            String senhaHash = SenhaUtil.criptografarSenha(user.getSenha());

            ps.setString(1, user.getUsuario());
            ps.setString(2, senhaHash);

            ps.executeUpdate();

            System.out.println("Usuário cadastrado com sucesso!");

        }catch(SQLException e){
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            return false;
        }
        return true;
    }

    //Método de login de usuário.
    public Usuario login(String usuario, String senha){

        String sql = "select * from usuarios where usuario = ?";

        try(
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){

            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                String senhaHashBanco = rs.getString("senha_hash");

                boolean senhaValida =
                        SenhaUtil.verificarSenha(senha, senhaHashBanco);

                if(senhaValida){

                    Usuario user = new Usuario();
                    user.setId(rs.getInt("id"));
                    user.setUsuario(rs.getString("usuario"));

                    System.out.println("Login realizado com sucesso!");

                    return user;
                }
            }

            System.out.println("Login inválido");
            return null;

        }catch(SQLException e){
            System.out.println("Erro no login: " + e.getMessage());
            return null;
        }
    }
    
    //Método que verifica se o nome de usuário existe no banco de dados.
    private boolean usuarioExiste(String usuario){
        String sql = "select id from usuarios where usuario = ?";

        try(
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){

            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        }catch(SQLException e){
            System.out.println("Erro ao verificar usuário: " + e.getMessage());
            return false;
        }
    }
}