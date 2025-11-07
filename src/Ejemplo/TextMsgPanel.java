package edu.uabc.eabj.Chat;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class TextMsgPanel extends JPanel
{
    private JTextPane msg;
    
    public TextMsgPanel()
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
        msg = new JTextPane();
		 Font font = new Font("Nuevo",Font.PLAIN,18);
		 msg.setFont(font);
		
        msg.setEditable(true);
        add(new JScrollPane(msg),gbc);
    }  
    
    
    public String get_text()
    {
        String text = msg.getText();
        return text;
    }        
    
    public void set_text(String text)
    {
        msg.setText(text);
    }

}













