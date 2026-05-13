package com.mycompany.sistemadeestoque.views;

import com.mycompany.sistemadeestoque.dao.ProdutoDAO;
import com.mycompany.sistemadeestoque.models.Produto;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


// Exemplo de como a tela de edição deve receber os dados
public class EditarProdutoView extends JDialog {
    private JTextField txtNome, txtTipo, txtValor, txtQuantidade;
    private Produto produtoRef;

    public EditarProdutoView(Produto p) {
        this.produtoRef = p;
        
        setTitle("Editar Produto: " + p.getNome());
        setModal(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout()); // Usando GridBag para melhor controle

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Inicializando os Campos com os dados do Produto ---
        txtNome = new JTextField(p.getNome(), 20);
        txtTipo = new JTextField(p.getTipo(), 20);
        txtValor = new JTextField(String.valueOf(p.getValor()), 20);
        txtQuantidade = new JTextField(String.valueOf(p.getQuantidadeEstoque()), 20);

        // --- Adicionando os componentes (Grid por Grid) ---
        
        // Linha 0: Nome
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        add(txtNome, gbc);

        // Linha 1: Tipo
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        add(txtTipo, gbc);

        // Linha 2: Valor
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Valor:"), gbc);
        gbc.gridx = 1;
        add(txtValor, gbc);

        // Linha 3: Quantidade
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Quantidade:"), gbc);
        gbc.gridx = 1;
        add(txtQuantidade, gbc);

        // Linha 4: Botão Salvar
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa as duas colunas
        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setBackground(new Color(0, 123, 255));
        btnSalvar.setForeground(Color.WHITE);
        add(btnSalvar, gbc);

        // --- Ação do Botão ---
        btnSalvar.addActionListener(e -> {
            try {
                // Atualiza o objeto com os novos valores da tela
                produtoRef.setNome(txtNome.getText());
                produtoRef.setTipo(txtTipo.getText());
                produtoRef.setValor(Float.parseFloat(txtValor.getText()));
                produtoRef.setQuantidadeEstoque(Integer.parseInt(txtQuantidade.getText()));

                // Chama o método atualizar que você já fez no DAO
                ProdutoDAO dao = new ProdutoDAO();
                dao.atualizar(produtoRef);

                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                dispose(); // Fecha esta janela
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao validar dados: " + ex.getMessage());
            }
        });
    }
}