package com.mycompany.sistemadeestoque.views;

import com.mycompany.sistemadeestoque.models.Usuario;
import com.mycompany.sistemadeestoque.views.paineis.CadastroProdutoPanel;
import com.mycompany.sistemadeestoque.views.paineis.DashboardPanel;
import com.mycompany.sistemadeestoque.views.paineis.ListaProdutosPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

//Classe View Home do usuário.
public class HomeView extends JFrame {

    //Variável com os dados do usuário.
    private Usuario usuarioLogado;
    //Variável do painel de conteúdo.
    private JPanel painelConteudo;

    //Cores do sistema.
    private final Color COR_SIDEBAR = new Color(15, 23, 42);
    private final Color COR_HOVER = new Color(30, 41, 59);
    private final Color COR_BOTAO = new Color(15, 23, 42);
    private final Color COR_TEXTO = Color.WHITE;
    private final Color COR_TOPO = Color.WHITE;
    private final Color COR_BACKGROUND = new Color(241, 245, 249);

    //Construtor com os dados do usuário.
    public HomeView(Usuario usuario) {
        this.usuarioLogado = usuario;

        // CONFIGURAÇÃO DA JANELA
        setTitle("Sistema de Estoque");
        setSize(1600, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COR_BACKGROUND);

        
        // SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(240, getHeight()));
        sidebar.setBackground(COR_SIDEBAR);
        sidebar.setLayout(new BorderLayout());

        // LOGO / TÍTULO
        JPanel painelLogo = new JPanel();
        painelLogo.setBackground(COR_SIDEBAR);
        painelLogo.setBorder(new EmptyBorder(30, 20, 30, 20));
        painelLogo.setLayout(new BoxLayout(painelLogo, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("ESTOQUE");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));

        JLabel lblSubtitulo = new JLabel("Painel Administrativo");
        lblSubtitulo.setForeground(new Color(148, 163, 184));
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));

        painelLogo.add(lblTitulo);
        painelLogo.add(Box.createVerticalStrut(5));
        painelLogo.add(lblSubtitulo);

        // MENU
        JPanel menu = new JPanel();
        menu.setBackground(COR_SIDEBAR);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(10, 15, 10, 15));

        JButton btnDashboard = criarBotaoMenu("📊 Dashboard e Saida");
        JButton btnProdutos = criarBotaoMenu("📦 Lista de Produtos");
        JButton btnCadastrarProduto = criarBotaoMenu("➕ Cadastrar Produto");
        JButton btnSair = criarBotaoMenu("🚪 Sair");

        menu.add(btnDashboard);
        menu.add(Box.createVerticalStrut(10));

        menu.add(btnProdutos);
        menu.add(Box.createVerticalStrut(10));

        menu.add(btnCadastrarProduto);

        // RODAPÉ SIDEBAR
        JPanel rodapeSidebar = new JPanel();
        rodapeSidebar.setBackground(COR_SIDEBAR);
        rodapeSidebar.setLayout(new BorderLayout());
        rodapeSidebar.setBorder(new EmptyBorder(20, 15, 20, 15));

        rodapeSidebar.add(btnSair, BorderLayout.SOUTH);

        sidebar.add(painelLogo, BorderLayout.NORTH);
        sidebar.add(menu, BorderLayout.CENTER);
        sidebar.add(rodapeSidebar, BorderLayout.SOUTH);

        add(sidebar, BorderLayout.WEST);

        // TOPO (HEADER)
        JPanel topo = new JPanel();
        topo.setPreferredSize(new Dimension(getWidth(), 70));
        topo.setBackground(COR_TOPO);
        topo.setLayout(new BorderLayout());
        topo.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0,
                new Color(226, 232, 240)
        ));

        JPanel infoUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        infoUsuario.setOpaque(false);

        JLabel lblBemVindo = new JLabel("Bem-vindo, " + usuarioLogado.getUsuario());
        lblBemVindo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblBemVindo.setForeground(new Color(30, 41, 59));

        JLabel lblDescricao = new JLabel("Gerencie seu estoque de forma prática");
        lblDescricao.setForeground(new Color(100, 116, 139));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        textos.add(lblBemVindo);
        textos.add(lblDescricao);

        infoUsuario.add(textos);

        topo.add(infoUsuario, BorderLayout.WEST);

        add(topo, BorderLayout.NORTH);

        // CONTEÚDO PRINCIPAL
        painelConteudo = new JPanel();
        painelConteudo.setLayout(new BorderLayout());
        painelConteudo.setBackground(COR_BACKGROUND);
        painelConteudo.setBorder(new EmptyBorder(15, 15, 15, 15));

        add(painelConteudo, BorderLayout.CENTER);

        //Botões e seus eventos.
           // Abre o dashboard
            btnDashboard.addActionListener(e -> trocarPainel(new DashboardPanel()));
            // Abre a lista de produtos
            btnProdutos.addActionListener(e -> trocarPainel(new ListaProdutosPanel()));
            // Abre o cadastro de produtos
            btnCadastrarProduto.addActionListener(e -> trocarPainel(new CadastroProdutoPanel()));
            // Faz o logout do sistema
            btnSair.addActionListener(e -> sair());

        //Chama o método que carrega a página inicial do sistema: DashboardPanel.
        trocarPainel(new DashboardPanel());
    }

    
     //Método que troca o painel.
    private void trocarPainel(JPanel novoPainel) {
        painelConteudo.removeAll();
        painelConteudo.add(novoPainel, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    
    //Método que cria o botão.
    private JButton criarBotaoMenu(String texto) {
        JButton botao = new JButton(texto);

        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);

        botao.setForeground(COR_TEXTO);
        botao.setBackground(COR_BOTAO);

        botao.setFont(new Font("SansSerif", Font.PLAIN, 15));
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        botao.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Hover
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setOpaque(true);
                botao.setBackground(COR_HOVER);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(COR_BOTAO);
            }
        });

        return botao;
    }

    
    //Método que realiza o logout do sistema.
    private void sair() {
        dispose();
        new UsuarioLoginView().setVisible(true);
    }
}