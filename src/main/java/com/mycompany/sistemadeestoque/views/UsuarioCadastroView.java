package com.mycompany.sistemadeestoque.views;

import com.mycompany.sistemadeestoque.models.Usuario;
import com.mycompany.sistemadeestoque.dao.UsuarioDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ItemEvent;

public class UsuarioCadastroView extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnSalvar;

    private JLabel lblMensagem;

    public UsuarioCadastroView() {

        setTitle("Cadastro de Usuário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Fundo da janela
        getContentPane().setBackground(new Color(15, 23, 42));
        setLayout(new GridBagLayout());

        // =========================
        // CARD PRINCIPAL
        // =========================
        JPanel card = new JPanel(new GridBagLayout());

        card.setBackground(Color.WHITE);

        card.setPreferredSize(new Dimension(420, 500));

        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(35, 35, 35, 35)
        ));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // =========================
        // ÍCONE
        // =========================
        JLabel lblIcon = new JLabel("👤", SwingConstants.CENTER);

        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 55));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        gbc.insets = new Insets(0, 0, 10, 0);

        card.add(lblIcon, gbc);

     
        // TÍTULO
        JLabel lblTitulo = new JLabel("Criar Conta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(30, 41, 59));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        card.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel(
                "Cadastre-se para acessar o sistema",
                SwingConstants.CENTER
        );

        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(100, 116, 139));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 25, 0);
        card.add(lblSubtitulo, gbc);

      
        // USUÁRIO
       
        gbc.gridwidth = 1;

        JLabel lblUsuario = new JLabel("Usuário");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUsuario.setForeground(new Color(51, 65, 85));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        card.add(lblUsuario, gbc);
        txtUsuario = new JTextField();
        txtUsuario.setPreferredSize(new Dimension(250, 42));
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        card.add(txtUsuario, gbc);


        // SENHA

        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSenha.setForeground(new Color(51, 65, 85));

        gbc.gridy = 5;
        gbc.gridwidth = 1;
        card.add(lblSenha, gbc);
        txtSenha = new JPasswordField();
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
        gbc.insets = new Insets(2, 0, 10, 0);
        card.add(chkMostrarSenha, gbc);

        // BOTÃO CADASTRAR
        btnSalvar = new JButton("CADASTRAR");
        btnSalvar.setPreferredSize(new Dimension(250, 45));
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.setBackground(new Color(16, 185, 129));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 8;
        gbc.insets = new Insets(15, 0, 10, 0);
        card.add(btnSalvar, gbc);

        // MENSAGEM
        lblMensagem = new JLabel(" ", SwingConstants.CENTER);
        lblMensagem.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridy = 9;
        gbc.insets = new Insets(10, 0, 0, 0);
        card.add(lblMensagem, gbc);

        
        // ADICIONA CARD
        add(card);
        // AÇÃO
        btnSalvar.addActionListener(e -> salvarUsuario());

        pack();

        setLocationRelativeTo(null);
    }
    // LÓGICA ORIGINAL

    private void salvarUsuario() {

        try {
            lblMensagem.setText("");
            
            Usuario usuario = new Usuario();
            usuario.setUsuario(txtUsuario.getText());
            usuario.setSenha(new String(txtSenha.getPassword()));
            
            UsuarioDAO dao = new UsuarioDAO();
            Boolean res = dao.cadastrar(usuario);
            
            if(res){
                JOptionPane.showMessageDialog(
                    this,
                    "Usuário cadastrado com sucesso!",
                    "Cadastro realizado",
                    JOptionPane.INFORMATION_MESSAGE
                 );
                dispose();
            }
            
            lblMensagem.setForeground(new Color(0, 150, 0));
            lblMensagem.setText("Usuário cadastrado com sucesso!");

            // limpa campos
            txtUsuario.setText("");
            txtSenha.setText("");
            

        } catch (IllegalArgumentException ex) {
            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText(ex.getMessage());
        }
    }
}