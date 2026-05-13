package com.mycompany.sistemadeestoque.models;

public class Usuario {

    private int id;
    private String usuario;
    private String senha; // senha pura (usada só antes de gerar hash)

    public Usuario() {
    }

    public Usuario(int id, String usuario, String senha) {
        this.id = id;
        setUsuario(usuario);
        setSenha(senha);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
        if (usuario.length() < 3) {
            throw new IllegalArgumentException("Usuário deve ter pelo menos 3 caracteres");
        }
        this.usuario = usuario.trim();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        if (senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres");
        }
        this.senha = senha.trim();
    }
}