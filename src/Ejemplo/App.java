package edu.uabc.eabj.Chat;

/*
javac -d bin -sourcepath src src/edu/uabc/eabj/Chat/*.java
java -cp bin edu.uabc.eabj.Chat.Main
*/
import java.util.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;



public class App extends JFrame 
{
    private Inicio panelPrincipal;
    private Chat chat;
    
    
    public App(String label)
    {
        super(label);  
        setLayout(new GridLayout(1,1));
        setSize(1080,720);
        setMinimumSize(new Dimension(640,480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        
        
        panelPrincipal = new Inicio();        
        
        setContentPane(panelPrincipal);	
        setVisible(true);
    }    
    
    public void setInicio()
	{		
		chat.setVisible(false);
		panelPrincipal.setVisible(true);
		setContentPane(panelPrincipal);		
	}
    
	public void setChat(Chat chat)
	{		
        this.chat = chat;
		panelPrincipal.setVisible(false);
		this.chat.setVisible(true);
		setContentPane(this.chat);		
	}
	    
}