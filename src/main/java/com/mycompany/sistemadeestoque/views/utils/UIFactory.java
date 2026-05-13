package com.mycompany.sistemadeestoque.views.utils;

import javax.swing.*;
import java.awt.*;

public class UIFactory {

    public static JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(52, 58, 64));
        btn.setForeground(Color.WHITE);
        return btn;
    }

    public static JPanel criarCard(String titulo, String valor) {
        JPanel card = new JPanel();
        card.setBackground(new Color(248, 249, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(Color.DARK_GRAY);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.BOLD, 22));
        lblValor.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);

        return card;
    }
}