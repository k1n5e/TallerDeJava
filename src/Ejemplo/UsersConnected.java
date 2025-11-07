package edu.uabc.eabj.Chat;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class UsersConnected extends JPanel
{
    private JTextPane usuarios;
    
    public UsersConnected()
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
        usuarios = new JTextPane();
        usuarios.setEditable(false);
		
		 Font font = new Font("Nuevo",Font.PLAIN,18);
		 usuarios.setFont(font);
        add(new JScrollPane(usuarios),gbc);
    }
	
	public synchronized void setUsers(String txt)
	{
		txt = txt.replace(",","\n");
		usuarios.setText(txt);
	}

}