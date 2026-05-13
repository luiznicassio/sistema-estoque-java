package com.mycompany.sistemadeestoque.views.paineis;

import com.mycompany.sistemadeestoque.dao.ProdutoDAO;
import com.mycompany.sistemadeestoque.models.Produto;
import com.mycompany.sistemadeestoque.views.utils.UIFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CadastroProdutoPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable tabelaRecentes;

    public CadastroProdutoPanel() {
        // Configuração principal do Painel (Otimizado para 1200x600)
        setLayout(new BorderLayout());
        setBackground(new Color(242, 244, 246));

        // --- 1. PAINEL LATERAL (FORMULÁRIO DE CADASTRO) ---
        JPanel painelEsquerdo = new JPanel(new BorderLayout());
        painelEsquerdo.setPreferredSize(new Dimension(420, 600)); // Largura ideal para 1200px
        painelEsquerdo.setBackground(Color.WHITE);
        painelEsquerdo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(226, 232, 240)));

        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setOpaque(false);
        formContent.setBorder(new EmptyBorder(50, 45, 50, 45)); // Respiro lateral elegante

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // Cabeçalho
        JLabel lblTitulo = new JLabel("Novo Produto");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(45, 55, 72));
        gbc.gridy = 0; gbc.insets = new Insets(0, 0, 8, 0);
        formContent.add(lblTitulo, gbc);

        JLabel lblSub = new JLabel("Preencha os dados para atualizar o inventário.");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSub.setForeground(new Color(113, 128, 150));
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 45, 0);
        formContent.add(lblSub, gbc);

        // Campos de Texto
        JTextField txtNome = criarCampo("Ex: Notebook Gamer");
        gbc.gridy = 2; gbc.insets = new Insets(10, 0, 5, 0);
        formContent.add(criarLabel("Nome do Produto"), gbc);
        gbc.gridy = 3; formContent.add(txtNome, gbc);

        JTextField txtTipo = criarCampo("Ex: Eletrônicos");
        gbc.gridy = 4; formContent.add(criarLabel("Categoria"), gbc);
        gbc.gridy = 5; formContent.add(txtTipo, gbc);

        // Linha Dupla: Preço e Quantidade lado a lado
        JPanel rowDados = new JPanel(new GridLayout(1, 2, 20, 0));
        rowDados.setOpaque(false);
        
        JTextField txtValor = criarCampo("0.00");
        JTextField txtQtd = criarCampo("0");
        
        JPanel pPreco = new JPanel(new BorderLayout(0, 5)); pPreco.setOpaque(false);
        pPreco.add(criarLabel("Preço (R$)"), BorderLayout.NORTH); pPreco.add(txtValor, BorderLayout.CENTER);
        
        JPanel pQtd = new JPanel(new BorderLayout(0, 5)); pQtd.setOpaque(false);
        pQtd.add(criarLabel("Estoque Inicial"), BorderLayout.NORTH); pQtd.add(txtQtd, BorderLayout.CENTER);

        rowDados.add(pPreco);
        rowDados.add(pQtd);

        gbc.gridy = 6; gbc.insets = new Insets(15, 0, 15, 0);
        formContent.add(rowDados, gbc);

        // Botão Salvar (Usando sua Factory)
        JButton btnSalvar = UIFactory.criarBotao("Cadastrar no Sistema");
        btnSalvar.setBackground(new Color(45, 55, 72)); // Azul Escuro Corporativo
        gbc.gridy = 7; gbc.insets = new Insets(35, 0, 15, 0);
        formContent.add(btnSalvar, gbc);

        JLabel lblMsg = new JLabel("", SwingConstants.CENTER);
        lblMsg.setFont(new Font("SansSerif", Font.BOLD, 12));
        gbc.gridy = 8; formContent.add(lblMsg, gbc);

        painelEsquerdo.add(formContent, BorderLayout.NORTH);

        // --- 2. PAINEL CENTRAL (VISUALIZAÇÃO DE DADOS) ---
        JPanel painelDireito = new JPanel(new BorderLayout());
        painelDireito.setOpaque(false);
        painelDireito.setBorder(new EmptyBorder(50, 60, 50, 60)); // Margens amplas para 1200px

        JLabel lblLista = new JLabel("Itens Adicionados Recentemente");
        lblLista.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblLista.setForeground(new Color(45, 55, 72));
        lblLista.setBorder(new EmptyBorder(0, 0, 25, 0));

        // Tabela Estilizada
        String[] colunas = {"ID", "Nome", "Categoria", "Valor Unitário", "Quantidade"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        tabelaRecentes = new JTable(tableModel);
        configurarTabela(tabelaRecentes);

        JScrollPane scroll = new JScrollPane(tabelaRecentes);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        scroll.getViewport().setBackground(Color.WHITE);

        painelDireito.add(lblLista, BorderLayout.NORTH);
        painelDireito.add(scroll, BorderLayout.CENTER);

        // --- LÓGICA DE AÇÕES ---
        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();
                String tipo = txtTipo.getText().trim();
                String valStr = txtValor.getText().replace(",", ".");

                if (nome.isEmpty() || tipo.isEmpty() || valStr.isEmpty() || txtQtd.getText().isEmpty()) {
                    throw new IllegalArgumentException("⚠️ Preencha todos os campos.");
                }

                Produto p = new Produto();
                p.setNome(nome);
                p.setTipo(tipo);
                p.setValor(Float.parseFloat(valStr));
                p.setQuantidadeEstoque(Integer.parseInt(txtQtd.getText()));

                if (new ProdutoDAO().cadastrar(p)) {
                    lblMsg.setForeground(new Color(38, 166, 91));
                    lblMsg.setText("✅ Produto cadastrado com sucesso!");
                    limparCampos(txtNome, txtTipo, txtValor, txtQtd);
                    atualizarTabela();
                }

            } catch (Exception ex) {
                lblMsg.setForeground(new Color(229, 62, 62));
                lblMsg.setText(ex.getMessage());
            }
        });

        // Carga inicial
        atualizarTabela();

        add(painelEsquerdo, BorderLayout.WEST);
        add(painelDireito, BorderLayout.CENTER);
    }

    private JLabel criarLabel(String texto) {
        JLabel l = new JLabel(texto.toUpperCase());
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        l.setForeground(new Color(113, 128, 150));
        return l;
    }

    private JTextField criarCampo(String placeholder) {
        JTextField f = new JTextField();
        f.setPreferredSize(new Dimension(0, 42));
        f.setFont(new Font("SansSerif", Font.PLAIN, 14));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        return f;
    }

    private void configurarTabela(JTable t) {
        t.setRowHeight(45); // Linhas altas para leitura confortável
        t.setFont(new Font("SansSerif", Font.PLAIN, 14));
        t.setSelectionBackground(new Color(235, 244, 255));
        t.setSelectionForeground(Color.BLACK);
        t.setShowVerticalLines(false);
        t.setGridColor(new Color(240, 240, 240));
        t.setFocusable(false);
        
        // Cabeçalho da Tabela
        t.getTableHeader().setPreferredSize(new Dimension(0, 45));
        t.getTableHeader().setBackground(new Color(248, 249, 250));
        t.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        t.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)));
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Produto> lista = new ProdutoDAO().listar();
        
        // Inversão: Últimos cadastrados no topo
        for (int i = lista.size() - 1; i >= 0; i--) {
            Produto p = lista.get(i);
            tableModel.addRow(new Object[]{
                p.getID(), 
                p.getNome().toUpperCase(), 
                p.getTipo(), 
                "R$ " + String.format("%.2f", p.getValor()), 
                p.getQuantidadeEstoque()
            });
            // Limite de 10 registros para a visualização rápida
            if (tableModel.getRowCount() >= 10) break;
        }
    }

    private void limparCampos(JTextField... campos) {
        for (JTextField c : campos) c.setText("");
        campos[0].requestFocus();
    }
}