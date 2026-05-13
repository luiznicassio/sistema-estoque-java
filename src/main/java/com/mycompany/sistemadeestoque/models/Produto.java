
package com.mycompany.sistemadeestoque.models;

public class Produto {
    private int id; 
    private String nome; 
    private String tipo; 
    private float valor;
    private int quantidadeEstoque; 
    
    public Produto(){}
    
    public Produto(int id, String nome, String tipo, float valor, 
            int quantidadeEstoque){
        setID(id);
        setNome(nome);
        setTipo(tipo);
        setValor(valor);
        setQuantidadeEstoque(quantidadeEstoque);
    
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    public int getID(){
        return id;
    }
    
    public void setNome(String nome){
        if(nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Nome obrigatorio");
        }
        if(nome.length() < 3){
            throw new IllegalArgumentException("Nome deve ter mais de 3 caracteres");
        }
        this.nome = nome.trim();
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setTipo(String tipo){
        if(tipo == null || tipo.trim().isEmpty()){
            throw new IllegalArgumentException("Tipo do produto obrigatorio");
        }
        this.tipo = tipo.trim();
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public void setValor(float valor){
        if(valor <= 0 ){
            throw new IllegalArgumentException("Valor do produto deve ser maior que 0");
        }
        this.valor = valor;
    }
    
    public float getValor(){
       return valor;
    }
    
    public void setQuantidadeEstoque(int quantidadeEstoque){
        if(quantidadeEstoque < 0 ){
            throw new IllegalArgumentException("A quantidade não pode ser negativa");
        } 
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
    public int getQuantidadeEstoque(){
        return quantidadeEstoque;
    }
}
