package com.mycompany.sistemadeestoque.dao;

import com.mycompany.sistemadeestoque.util.Conexao;
import com.mycompany.sistemadeestoque.models.Usuario;
import com.mycompany.sistemadeestoque.util.SenhaUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean cadastrar(Usuario user){
        String sql = "INSERT INTO usuarios (usuario, senha_hash) VALUES (?, ?)";
       
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

    public Usuario login(String usuario, String senha){

        String sql = "SELECT * FROM usuarios WHERE usuario = ?";

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

    private boolean usuarioExiste(String usuario){
        String sql = "SELECT id FROM usuarios WHERE usuario = ?";

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