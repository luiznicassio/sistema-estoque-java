package com.mycompany.sistemadeestoque.views;

import com.mycompany.sistemadeestoque.dao.ProdutoDAO;
import com.mycompany.sistemadeestoque.models.Produto;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EditarProdutoView extends JDialog {
    private JTextField txtNome, txtValor, txtQuantidade;
    private JComboBox<String> comboTipo; // Substituído JTextField por JComboBox
    private Produto produtoRef;

    public EditarProdutoView(Produto p) {
        this.produtoRef = p;
        
        setTitle("Editar Produto: " + p.getNome());
        setModal(true);
        setSize(400, 320); // Ajustado levemente a altura para acomodar bem o ComboBox
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Inicializando os Campos com os dados do Produto ---
        txtNome = new JTextField(p.getNome(), 20);
        
        // Inicializa o ComboBox com as opções e define a categoria atual do produto
        comboTipo = new JComboBox<>(new String[]{"Todos", "Eletrônicos", "Limpeza", "Alimentos"});
        comboTipo.setSelectedItem(p.getTipo()); 
        
        txtValor = new JTextField(String.valueOf(p.getValor()), 20);
        txtQuantidade = new JTextField(String.valueOf(p.getQuantidadeEstoque()), 20);

        // --- Adicionando os componentes (Grid por Grid) ---
        
        // Linha 0: Nome
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        add(txtNome, gbc);

        // Linha 1: Categoria / Tipo
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 1;
        add(comboTipo, gbc); // Adicionando o ComboBox no lugar do JTextField

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
        gbc.gridwidth = 2;
        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setBackground(new Color(0, 123, 255));
        btnSalvar.setForeground(Color.WHITE);
        add(btnSalvar, gbc);

        // --- Ação do Botão ---
        btnSalvar.addActionListener(e -> {
            try {
                // Validação básica de preenchimento
                String nome = txtNome.getText().trim();
                String tipoSelecionado = (String) comboTipo.getSelectedItem();
                
                if (nome.isEmpty() || tipoSelecionado == null) {
                    throw new IllegalArgumentException("Preencha todos os campos obrigatórios.");
                }

                // Atualiza o objeto com os novos valores da tela
                produtoRef.setNome(nome);
                produtoRef.setTipo(tipoSelecionado); // Resgata o valor selecionado no ComboBox
                produtoRef.setValor(Float.parseFloat(txtValor.getText().replace(",", ".")));
                produtoRef.setQuantidadeEstoque(Integer.parseInt(txtQuantidade.getText()));

                // Chama o método atualizar do seu DAO
                ProdutoDAO dao = new ProdutoDAO();
                dao.atualizar(produtoRef);

                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                dispose(); // Fecha a janela de edição
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Verifique se o valor e a quantidade foram digitados corretamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao validar dados: " + ex.getMessage());
            }
        });
    }
}