package com.mycompany.sistemadeestoque.views.paineis;

import com.mycompany.sistemadeestoque.dao.ProdutoDAO;
import com.mycompany.sistemadeestoque.models.Produto;
import com.mycompany.sistemadeestoque.views.EditarProdutoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaProdutosPanel extends JPanel {

    private JTable tabela;
    private DefaultTableModel model;
    private ProdutoDAO dao;

    // Componentes dos Novos Filtros
    private JComboBox<String> comboTipo;
    private JTextField txtPrecoMin;
    private JTextField txtPrecoMax;

    public ListaProdutosPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dao = new ProdutoDAO();

       
        // PAINEL SUPERIOR (FILTROS) - NOVO
        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        painelFiltros.setBorder(BorderFactory.createTitledBorder("Filtrar Busca"));

        comboTipo = new JComboBox<>(new String[]{"Todos", "Eletrônicos", "Limpeza", "Alimentos"});
        txtPrecoMin = new JTextField(6);
        txtPrecoMax = new JTextField(6);
        JButton btnAplicarFiltro = new JButton("Filtrar");
        JButton btnLimparFiltro = new JButton("Limpar");

        painelFiltros.add(new JLabel("Tipo:"));
        painelFiltros.add(comboTipo);
        painelFiltros.add(new JLabel("Valor Min:"));
        painelFiltros.add(txtPrecoMin);
        painelFiltros.add(new JLabel("Valor Max:"));
        painelFiltros.add(txtPrecoMax);
        painelFiltros.add(btnAplicarFiltro);
        painelFiltros.add(btnLimparFiltro);

        // CONFIGURAÇÃO DA TABELA
        String[] colunas = {"ID", "Nome", "Tipo", "Valor", "Quantidade"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabela = new JTable(model);
        tabela.setRowHeight(30);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);

        carregarDadosTabela();

        JScrollPane scroll = new JScrollPane(tabela);

        // PAINEL INFERIOR (AÇÕES)
        JPanel painelInferior = new JPanel(new BorderLayout());
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel lblSelecionado = new JLabel("Nenhum produto selecionado");
        lblSelecionado.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblSelecionado.setForeground(Color.GRAY);
        lblSelecionado.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        btnExcluir.setBackground(new Color(220, 53, 69));
        btnExcluir.setForeground(Color.WHITE);

        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        // --- EVENTOS DOS FILTROS ---
        btnAplicarFiltro.addActionListener(e -> filtrarDados());
        
        btnLimparFiltro.addActionListener(e -> {
            comboTipo.setSelectedIndex(0);
            txtPrecoMin.setText("");
            txtPrecoMax.setText("");
            carregarDadosTabela();
        });

        // Evento seleção da tabela
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tabela.getSelectedRow();
                if (linha != -1) {
                    String nome = tabela.getValueAt(linha, 1).toString();
                    lblSelecionado.setText("Produto Selecionado: " + nome);
                    lblSelecionado.setForeground(new Color(0, 123, 255));
                } else {
                    lblSelecionado.setText("Nenhum produto selecionado");
                    lblSelecionado.setForeground(Color.GRAY);
                }
            }
        });

        // Ação Editar
        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um produto!");
                return;
            }

            Produto p = new Produto();
            p.setID((int) tabela.getValueAt(linha, 0));
            p.setNome(tabela.getValueAt(linha, 1).toString());
            p.setTipo(tabela.getValueAt(linha, 2).toString());

            String valorLimpo = tabela.getValueAt(linha, 3).toString()
                    .replace("R$ ", "").replace(",", ".");

            p.setValor(Float.parseFloat(valorLimpo));
            p.setQuantidadeEstoque(Integer.parseInt(tabela.getValueAt(linha, 4).toString()));

            EditarProdutoView telaEdit = new EditarProdutoView(p);
            telaEdit.setVisible(true);

            carregarDadosTabela(); 
        });

        // Ação Excluir
        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um produto para excluir.");
                return;
            }

            int id = (int) tabela.getValueAt(linha, 0);
            String nome = tabela.getValueAt(linha, 1).toString();

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja excluir o produto Nome: " + nome + " ID: " + id + "?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.apagar(id)) {
                    JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
                    carregarDadosTabela();
                }
            }
        });

        painelInferior.add(lblSelecionado, BorderLayout.WEST);
        painelInferior.add(painelBotoes, BorderLayout.EAST);

        // MONTAGEM FINAL
        add(painelFiltros, BorderLayout.NORTH); // Filtros no topo
        add(scroll, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }

    private void carregarDadosTabela() {
        preencherTabela(dao.listar());
    }

    private void filtrarDados() {
        try {
            String tipo = comboTipo.getSelectedItem().toString();
            Double min = txtPrecoMin.getText().isEmpty() ? null : Double.parseDouble(txtPrecoMin.getText().replace(",", "."));
            Double max = txtPrecoMax.getText().isEmpty() ? null : Double.parseDouble(txtPrecoMax.getText().replace(",", "."));

            // Aqui chama o método que você criou no DAO
            List<Produto> filtrados = dao.filtrarProdutos(tipo, min, max);
            preencherTabela(filtrados);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe valores numéricos válidos nos filtros de preço.");
        }
    }

    private void preencherTabela(List<Produto> lista) {
        model.setRowCount(0); 
        for (Produto p : lista) {
            Object[] linha = {
                p.getID(),
                p.getNome(),
                p.getTipo(),
                "R$ " + String.format("%.2f", p.getValor()),
                p.getQuantidadeEstoque()
            };
            model.addRow(linha);
        }
    }
}