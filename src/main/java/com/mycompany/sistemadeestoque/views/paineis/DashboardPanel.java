package com.mycompany.sistemadeestoque.views.paineis;

import com.mycompany.sistemadeestoque.dao.ProdutoDAO;
import com.mycompany.sistemadeestoque.models.Produto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class DashboardPanel extends JPanel {
    
    // CORES DO SISTEMA
    private final Color BACKGROUND = new Color(241, 245, 249);
    private final Color CARD = Color.WHITE;
    private final Color PRIMARY = new Color(15, 23, 42);
    private final Color PRIMARY_HOVER = new Color(30, 41, 59);
    private final Color BORDER = new Color(226, 232, 240);
    private final Color TEXT = new Color(30, 41, 59);
    private final Color TEXT_LIGHT = new Color(100, 116, 139);

    //Construtor. 
    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel container = new JPanel(new BorderLayout(0, 25));
        container.setOpaque(false);

    
        JPanel topo = new JPanel();
        topo.setLayout(new BoxLayout(topo, BoxLayout.Y_AXIS));
        topo.setOpaque(false);
        JLabel lblTitulo = new JLabel("Dashboard");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblTitulo.setForeground(TEXT);
        JLabel lblSubtitulo = new JLabel(
                "Visualize informações do estoque"
        );

        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setForeground(TEXT_LIGHT);
        topo.add(lblTitulo);
        topo.add(Box.createVerticalStrut(5));
        topo.add(lblSubtitulo);

       
        //Puxa o total de produtos de um método no DAO.
        String totalDeProdutos = String.valueOf(new ProdutoDAO().totalDeProdutos());
        //Cria o painel de cards.
        JPanel painelCards = new JPanel(new GridLayout(1, 3, 20, 0));
        painelCards.setOpaque(false);
        painelCards.add(
                criarCardResumo(
                        "Total de Produtos",
                        totalDeProdutos,
                        "📦"
                )
        );
        //Puxa o número de produtos com estoque baixo de um método no DAO.
        String estoqueBaixo = String.valueOf(new ProdutoDAO().estoqueBaixo());
        //Adiciona o segundo card no painel.
        painelCards.add(
                criarCardResumo(
                        "Estoque Baixo",
                        estoqueBaixo,
                        "⚠️"
                )
        );
        //Puxa o valor total dos produtos somados de um método do DAO.
        String valorTotal = String.valueOf(new ProdutoDAO().valorTotal());
        //Adiciona o terceiro card no painel.
        painelCards.add(
                criarCardResumo(
                        "Valor Total",
                        "R$ " + valorTotal,
                        "💰"
                )
        );


       // ÁREA DE BUSCA
        JPanel painelBusca = criarCard();
        painelBusca.setLayout(new BorderLayout());
        painelBusca.setBorder(new EmptyBorder(25, 25, 25, 25));

        // TEXTO
        JPanel textosBusca = new JPanel();
        textosBusca.setOpaque(false);
        textosBusca.setLayout(new BoxLayout(
            textosBusca,
            BoxLayout.Y_AXIS
        ));

        //Saida de produtos 
        textosBusca.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblSaida = new JLabel("Saída de Produtos");
        lblSaida.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblSaida.setForeground(TEXT);
        lblSaida.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescricao = new JLabel(
            "Busque um produto e registre saída"
        );

        lblDescricao.setForeground(TEXT_LIGHT);

        lblDescricao.setAlignmentX(Component.LEFT_ALIGNMENT);

        textosBusca.add(lblSaida);

        textosBusca.add(Box.createVerticalStrut(5));

        textosBusca.add(lblDescricao);

        // LINHA BUSCA
        JPanel linhaBusca = new JPanel(
            new FlowLayout(
                FlowLayout.LEFT,
                15,
                0
            )
        );

        linhaBusca.setOpaque(false);

        linhaBusca.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField txtBusca = criarCampoBusca();
        JButton btnBuscar = criarBotao("Buscar");
        linhaBusca.add(txtBusca);
        linhaBusca.add(btnBuscar);

        // CONTAINER
        JPanel buscaContainer = new JPanel();
        buscaContainer.setOpaque(false);
        buscaContainer.setLayout(new BoxLayout(
            buscaContainer,
            BoxLayout.Y_AXIS
        ));

        buscaContainer.add(textosBusca);
        buscaContainer.add(Box.createVerticalStrut(20));
        buscaContainer.add(linhaBusca);
        painelBusca.add(buscaContainer, BorderLayout.CENTER);

        
        // PRODUTOS
        JPanel painelProdutos = new JPanel();
        painelProdutos.setLayout(new BoxLayout(
                painelProdutos,
                BoxLayout.Y_AXIS
        ));
        painelProdutos.setBackground(BACKGROUND);
        JScrollPane scroll = new JScrollPane(painelProdutos);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BACKGROUND);
        scroll.getVerticalScrollBar().setUnitIncrement(20);

        // PLACEHOLDER Do textFild da busca
        txtBusca.setText("Buscar produto...");

        txtBusca.setForeground(Color.GRAY);
        txtBusca.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (txtBusca.getText().equals("Buscar produto...")) {

                    txtBusca.setText("");

                    txtBusca.setForeground(TEXT);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (txtBusca.getText().isEmpty()) {

                    txtBusca.setText("Buscar produto...");

                    txtBusca.setForeground(Color.GRAY);
                }
            }
        });

        // EVENTO BUSCAR
        btnBuscar.addActionListener(e -> {

            String nome = txtBusca.getText().equals("Buscar produto...")? "": txtBusca.getText();

            painelProdutos.removeAll();

            if (nome.isEmpty()) {
                JLabel vazio = new JLabel("Digite um nome para buscar.");
                vazio.setForeground(TEXT_LIGHT);
                painelProdutos.add(vazio);

            } else {
                //Faz a busca a partir de um método da DAO que tem como parâmetro o nome do produto.
                List<Produto> produtos = new ProdutoDAO().buscarPorNome(nome);

                //Se a lista estiver vazia, ele retorna a mensagem de produtos não encontrados.
                if (produtos.isEmpty()) {
                    JLabel vazio = new JLabel("Nenhum produto encontrado.");
                    vazio.setForeground(TEXT_LIGHT);
                    painelProdutos.add(vazio);

                } else {

                    for (Produto p : produtos) {
                        //Se a lista tiver produtos, o for cria os cards e os exibe para o usuário.
                        painelProdutos.add(criarCardProduto(p));

                        painelProdutos.add( Box.createVerticalStrut(15));
                    }
                }
            }

            painelProdutos.revalidate();
            painelProdutos.repaint();
        });

        // MONTAGEM
        JPanel topoDashboard = new JPanel(new BorderLayout(0, 25));
        topoDashboard.setOpaque(false);
        topoDashboard.add(topo, BorderLayout.NORTH);
        topoDashboard.add(painelCards, BorderLayout.CENTER);
        JPanel conteudo = new JPanel(new BorderLayout(0, 20));
        conteudo.setOpaque(false);
        conteudo.add(painelBusca, BorderLayout.NORTH);
        conteudo.add(scroll, BorderLayout.CENTER);
        container.add(topoDashboard, BorderLayout.NORTH);
        container.add(conteudo, BorderLayout.CENTER);
        add(container, BorderLayout.CENTER);
    }

    // CARD RESUMO
    private JPanel criarCardResumo(
            String titulo,
            String valor,
            String emoji
    ) {

        JPanel card = criarCard();

        card.setLayout(new BorderLayout());

        card.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel lblEmoji = new JLabel(emoji);

        lblEmoji.setFont(new Font("SansSerif", Font.PLAIN, 28));

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setForeground(TEXT_LIGHT);

        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel lblValor = new JLabel(valor);

        lblValor.setForeground(TEXT);

        lblValor.setFont(new Font("SansSerif", Font.BOLD, 30));

        JPanel textos = new JPanel();

        textos.setOpaque(false);

        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        textos.add(lblTitulo);

        textos.add(Box.createVerticalStrut(10));

        textos.add(lblValor);

        card.add(lblEmoji, BorderLayout.NORTH);

        card.add(textos, BorderLayout.CENTER);

        return card;
    }

    // CARD PRODUTO
    private JPanel criarCardProduto(Produto p) {

        JPanel card = criarCard();
        card.setLayout(new BorderLayout(20, 0));
        card.setBorder(new EmptyBorder(20, 25, 20, 25));
        card.setMaximumSize(new Dimension(
                Integer.MAX_VALUE,
                120
        ));

        
        // INFO
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(
                info,
                BoxLayout.Y_AXIS
        ));

        JLabel lblNome = new JLabel(p.getNome());
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblNome.setForeground(TEXT);
        JLabel lblTipo = new JLabel(
                "Categoria: " + p.getTipo()
        );

        lblTipo.setForeground(TEXT_LIGHT);
        JLabel lblQuantidade = new JLabel(
                "Estoque: " + p.getQuantidadeEstoque()
        );

        lblQuantidade.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                14
        ));

        if (p.getQuantidadeEstoque() <= 5) {
            lblQuantidade.setForeground(
                    new Color(220, 38, 38)
            );

        } else {

            lblQuantidade.setForeground(
                    new Color(22, 163, 74)
            );
        }

        info.add(lblNome);
        info.add(Box.createVerticalStrut(5));
        info.add(lblTipo);
        info.add(Box.createVerticalStrut(10));
        info.add(lblQuantidade);

        // AÇÕES
        JPanel acoes = new JPanel(new FlowLayout(
                FlowLayout.RIGHT,
                10,
                10
        ));

        acoes.setOpaque(false);
        JTextField txtSaida = new JTextField();
        txtSaida.setPreferredSize(new Dimension(70, 40));
        txtSaida.setHorizontalAlignment(JTextField.CENTER);
        txtSaida.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(5, 10, 5, 10)
        ));

        JButton btnBaixa = criarBotao("Dar baixa");
        btnBaixa.setPreferredSize(new Dimension(130, 40));

        //Baixa de produtos (saída de produtos).
        btnBaixa.addActionListener(e -> {
            try {
                String textoSaida = txtSaida.getText();
                //Confere se a string não está vazia.
                if (textoSaida.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Digite uma quantidade."
                    );

                    return;
                }
                
                //Converte o valor da string para um número inteiro (int).
                int saida = Integer.parseInt(textoSaida);

                //Realiza a regra da saída, em que o valor da saída não pode ser maior que o do estoque.
                //E também o valor da saída não pode ser negativo ou zero. 
                if (saida > 0 && saida <= p.getQuantidadeEstoque()) {

                    //Tela de confirmação 
                    int confirmacao = JOptionPane.showConfirmDialog(
                                    this,
                                    "Deseja registrar a saída de "
                                            + saida
                                            + " unidade(s) de:\n"
                                            + p.getNome()
                                            + "?",
                                    "Confirmar saída",
                                    JOptionPane.YES_NO_OPTION
                            );

                    if (confirmacao == JOptionPane.YES_OPTION) {

                        //Faz a subtração do valor do estoque.
                        int novoEstoque = p.getQuantidadeEstoque() - saida;

                        //Realiza a atualização do valor do estoque no banco.
                        if (new ProdutoDAO().atualizarQuantidade(p.getID(),novoEstoque)) {

                            p.setQuantidadeEstoque(novoEstoque);

                            lblQuantidade.setText("Estoque: " + novoEstoque);

                            //Limpa o TextField do valor.
                            txtSaida.setText("");

                            JOptionPane.showMessageDialog(this,"Saída registrada com sucesso!");
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(this,"Quantidade inválida.");
                }

            } catch (NumberFormatException ex) {
                //Esse catch captura as exceções de números e retorna uma mensagem de erro.
                JOptionPane.showMessageDialog(this,"Digite apenas números inteiros.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"Erro: " + ex.getMessage());
            }
        });

        acoes.add(txtSaida);
        acoes.add(btnBaixa);

        // MONTAGEM CARD
        card.add(info, BorderLayout.WEST);
        card.add(acoes, BorderLayout.EAST);
        return card;
    }

    // BOTÃO
    private JButton criarBotao(String texto) {

        JButton botao = new JButton(texto);

        botao.setBackground(PRIMARY);

        botao.setForeground(Color.WHITE);

        botao.setFocusPainted(false);

        botao.setBorderPainted(false);

        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                14
        ));

        botao.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(
                    java.awt.event.MouseEvent evt
            ) {

                botao.setBackground(PRIMARY_HOVER);
            }

            @Override
            public void mouseExited(
                    java.awt.event.MouseEvent evt
            ) {

                botao.setBackground(PRIMARY);
            }
        });

        return botao;
    }

    // CAMPO BUSCA
    private JTextField criarCampoBusca() {

        JTextField campo = new JTextField();

        campo.setPreferredSize(new Dimension(320, 42));

        campo.setFont(new Font(
                "SansSerif",
                Font.PLAIN,
                14
        ));

        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(8, 15, 8, 15)
        ));

        return campo;
    }

    // CARD BASE
    private JPanel criarCard() {

        JPanel panel = new JPanel();

        panel.setBackground(CARD);

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(0, 0, 0, 0)
        ));

        return panel;
    }
}