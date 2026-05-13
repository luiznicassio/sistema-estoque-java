package com.mycompany.sistemadeestoque.dao;
import com.mycompany.sistemadeestoque.util.Conexao;
import com.mycompany.sistemadeestoque.models.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    
    //criar 
    public boolean cadastrar(Produto p){
        String sql = "insert into produtos(nome,tipo,valor,quantidade) values (?,?,?,?)";
        
        try(Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);  ){
            
            ps.setString(1, p.getNome());           
            ps.setString(2, p.getTipo());
            ps.setFloat(3, p.getValor());
            ps.setInt(4, p.getQuantidadeEstoque());
            
            ps.executeUpdate();
            
            System.out.println("Produto cadastrado");
        }catch(SQLException e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    //listar
    public  List<Produto> listar(){
       List<Produto> listaDeProduto = new ArrayList<>();
       String sql = "select * from produtos";
       
       try(Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql); ){
          
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                float valor = rs.getFloat("valor");
                int quantidade = rs.getInt("quantidade");
                
                Produto p = new Produto(id, nome, tipo, valor, quantidade);
                
                listaDeProduto.add(p);
            }
          
       }catch(SQLException e){
           System.out.println(e.getMessage());
       }
       return listaDeProduto;
    }
    
   //deletar
   public boolean apagar(int id){
       String sql = "delete from produtos where id = ?";
       
       try(Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);){
           
           ps.setInt(1, id);
           ps.executeUpdate();
           
           
       }catch(SQLException e){
           System.out.println(e.getMessage());
           return false;
       }
       return true;
   }
   
   //atualizar 
   public boolean atualizar(Produto p){
       String sql = "update produtos set nome = ?, tipo = ?, valor = ?, quantidade = ? where id = ?";
       
       try(Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)){
           
            ps.setString(1, p.getNome());
            ps.setString(2, p.getTipo());
            ps.setFloat(3, p.getValor());
            ps.setInt(4, p.getQuantidadeEstoque());
            ps.setInt(5,p.getID());
            
            ps.executeUpdate();
            System.out.println("Dados do produto atualizado");
            return true;
         }catch(SQLException e){
           System.out.println(e.getMessage());
       }
       return false;
   }
   
   //buscar, pesquisa
   public List<Produto> buscarPorNome(String nome){
       List<Produto> lista = new ArrayList<>();
       
       String sql = "select * from produtos where nome like ?";
       
       try(Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)){
           
          ps.setString(1,"%"+nome+"%");
          ResultSet rs = ps.executeQuery();
          
          while(rs.next()){
              Produto p = new Produto();
              p.setID(rs.getInt("id"));
              p.setNome(rs.getString("nome"));
              p.setQuantidadeEstoque(rs.getInt("quantidade"));
              p.setValor(rs.getFloat("valor"));
              p.setTipo(rs.getString("tipo"));
              
              lista.add(p);
          }
       }catch(SQLException e){
           System.out.println(e.getMessage());
       }
       return  lista;
   }
   
   //baixa 
   public boolean atualizarQuantidade(int id, int novaQuantidade) {
    String sql = "UPDATE produtos SET quantidade = ? WHERE id = ?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, novaQuantidade);
        ps.setInt(2, id);

        ps.executeUpdate();
        return true;

    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return false;
    }
}
   
 //filtrar   
   public List<Produto> filtrarProdutos(String tipo, Double precoMin, Double precoMax) {
    List<Produto> lista = new ArrayList<>();
    
    StringBuilder sql = new StringBuilder("SELECT * FROM produtos WHERE 1=1");
    
    // Adiciona filtros apenas se os valores forem preenchidos
    if (tipo != null && !tipo.equals("Todos")) {
        sql.append(" AND tipo = ?");
    }
    if (precoMin != null) {
        sql.append(" AND valor >= ?");
    }
    if (precoMax != null) {
        sql.append(" AND valor <= ?");
    }

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
        
        int i = 1;
        if (tipo != null && !tipo.equals("Todos")) stmt.setString(i++, tipo);
        if (precoMin != null) stmt.setDouble(i++, precoMin);
        if (precoMax != null) stmt.setDouble(i++, precoMax);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Produto p = new Produto();
            p.setID(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setTipo(rs.getString("tipo"));
            p.setValor(rs.getFloat("valor"));
            p.setQuantidadeEstoque(rs.getInt("quantidade"));
            lista.add(p);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
    }
   
   //total de produtos cadastrados no estoque
   public int totalDeProdutos() {
    return listar().size();
   }
   //valor do estoque
   public float valorTotal() {

    List<Produto> lista = listar();

    float valorTotal = 0;

    for (Produto p : lista) {

        valorTotal +=
                p.getValor()
                * p.getQuantidadeEstoque();
    }

    return valorTotal;
    }
   
   //estoque baixo 
   public int estoqueBaixo(){
    List<Produto> lista = listar();
    int contador = 0;
    
    for (Produto p :lista){
        
        if(p.getQuantidadeEstoque() <= 5 ){
            contador = contador + 1 ;
        }
    }
    return contador;
   }
   
}
