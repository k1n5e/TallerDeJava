package edu.uabc.eabj.Chat;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class TextChatPanel extends JPanel
{
    private JTextPane chat;
    
    public TextChatPanel()
    {        
        setLayout(new GridBagLayout());        
        GridBagConstraints gbc = new GridBagConstraints();        
        //MARGENES
        gbc.insets.left = 10;
        gbc.insets.right = 10;
        gbc.insets.top = 10;
        gbc.insets.bottom = 10;
         // Configuración clave
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.9; // Expandir en horizontal
        gbc.weighty = 0.9; // Expandir en vertical
        gbc.gridwidth = 1; // El botón ocupará  1 columnas
        gbc.fill = GridBagConstraints.BOTH; // Rellenar completamente
        gbc.anchor = GridBagConstraints.CENTER; // Ubica el botón
        chat = new JTextPane();
        chat.setEditable(false);
		 Font font = new Font("Nuevo",Font.PLAIN,18);
		 chat.setFont(font);
        add(new JScrollPane(chat),gbc);
    }
    
    public synchronized void agregar(String msg)
    {
        String text = chat.getText();
        text = text.concat("\n"+msg);
        chat.setText(text);
    }
}