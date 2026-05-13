package com.mycompany.sistemadeestoque.views;

import com.mycompany.sistemadeestoque.dao.UsuarioDAO;
import com.mycompany.sistemadeestoque.models.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ItemEvent;

public class UsuarioLoginView extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JLabel lblMensagem;

    public UsuarioLoginView() {

        setTitle("Login - Sistema de Estoque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Fundo principal
        getContentPane().setBackground(new Color(15, 23, 42));
        setLayout(new GridBagLayout());

        // =========================
        // CARD PRINCIPAL
        // =========================
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(new Color(255, 255, 255));
        card.setPreferredSize(new Dimension(420, 520));

        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(35, 35, 35, 35)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // =========================
        // ÍCONE / TOPO
        // =========================
        JLabel lblIcon = new JLabel("📦", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 55));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        card.add(lblIcon, gbc);

        // =========================
        // TÍTULO
        // =========================
        JLabel lblTitulo = new JLabel("Bem-vindo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(30, 41, 59));

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        card.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel(
                "Faça login para acessar o sistema",
                SwingConstants.CENTER
        );

        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(100, 116, 139));

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 25, 0);
        card.add(lblSubtitulo, gbc);

        // =========================
        // USUÁRIO
        // =========================
        gbc.gridwidth = 1;

        JLabel lblUsuario = new JLabel("Usuário");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUsuario.setForeground(new Color(51, 65, 85));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        card.add(lblUsuario, gbc);

        txtUsuario = new JTextField(15);
        txtUsuario.setPreferredSize(new Dimension(250, 42));
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        card.add(txtUsuario, gbc);

        // =========================
        // SENHA
        // =========================
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSenha.setForeground(new Color(51, 65, 85));

        gbc.gridy = 5;
        gbc.gridwidth = 1;
        card.add(lblSenha, gbc);

        txtSenha = new JPasswordField(15);
        txtSenha.setPreferredSize(new Dimension(250, 42));
        txtSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtSenha.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        card.add(txtSenha, gbc);

        // =========================
        // MOSTRAR SENHA
        // =========================
        JCheckBox chkMostrarSenha = new JCheckBox("Mostrar senha");
        chkMostrarSenha.setBackground(Color.WHITE);
        chkMostrarSenha.setFocusPainted(false);
        chkMostrarSenha.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        chkMostrarSenha.addItemListener(e -> {
            txtSenha.setEchoChar(
                    e.getStateChange() == ItemEvent.SELECTED
                            ? (char) 0
                            : '•'
            );
        });

        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(2, 0, 10, 0);
        card.add(chkMostrarSenha, gbc);

        // =========================
        // BOTÃO LOGIN
        // =========================
        btnLogin = new JButton("ENTRAR");

        btnLogin.setPreferredSize(new Dimension(250, 45));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnLogin.setBackground(new Color(37, 99, 235));
        btnLogin.setForeground(Color.WHITE);

        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);

        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy = 8;
        gbc.insets = new Insets(15, 0, 10, 0);
        card.add(btnLogin, gbc);

        // =========================
        // BOTÃO CADASTRO
        // =========================
        JButton btnCadastrar = new JButton("Não tem conta? Cadastre-se");

        btnCadastrar.setBorderPainted(false);
        btnCadastrar.setContentAreaFilled(false);

        btnCadastrar.setForeground(new Color(37, 99, 235));

        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCadastrar.setFont(new Font("Segoe UI", Font.BOLD, 12));

        gbc.gridy = 9;
        gbc.insets = new Insets(5, 0, 10, 0);
        card.add(btnCadastrar, gbc);

        // =========================
        // MENSAGEM
        // =========================
        lblMensagem = new JLabel(" ", SwingConstants.CENTER);

        lblMensagem.setFont(new Font("Segoe UI", Font.BOLD, 12));

        gbc.gridy = 10;
        gbc.insets = new Insets(10, 0, 0, 0);
        card.add(lblMensagem, gbc);

        // =========================
        // ADICIONA O CARD
        // =========================
        add(card);

        // =========================
        // AÇÕES
        // =========================
        this.getRootPane().setDefaultButton(btnLogin);

        btnCadastrar.addActionListener(e ->
                new UsuarioCadastroView().setVisible(true)
        );

        btnLogin.addActionListener(e -> realizarLogin());

        pack();
        setLocationRelativeTo(null);
    }

    // --- Lógica inalterada 
    private void realizarLogin() {

        try {

            lblMensagem.setText("");

            String usuario = txtUsuario.getText().trim();
            String senha = new String(txtSenha.getPassword()).trim();

            if (usuario.isEmpty() || senha.isEmpty()) {
                throw new IllegalArgumentException("Preencha todos os campos");
            }

            UsuarioDAO dao = new UsuarioDAO();

            Usuario user = dao.login(usuario, senha);

            if (user != null) {

                lblMensagem.setForeground(new Color(0, 150, 0));
                lblMensagem.setText("Bem-vindo!");

                abrirHome(user);

            } else {

                lblMensagem.setForeground(Color.RED);
                lblMensagem.setText("Usuário ou senha inválidos");
            }

        } catch (IllegalArgumentException ex) {

            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText(ex.getMessage());
        }
    }

    //metodo abre a pagina home e fecha o login 
    private void abrirHome(Usuario usuario) {

        new HomeView(usuario).setVisible(true);
        dispose();
    }
}