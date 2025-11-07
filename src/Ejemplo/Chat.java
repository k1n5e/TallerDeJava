package edu.uabc.eabj.Chat;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.concurrent.*;

public class Chat extends JPanel  implements ActionListener
{
    private TextChatPanel chat_area;
    private UsersConnected users;
    private TextMsgPanel chat_msg;
    private JButton enviar_btn;
    private JButton salir_btn;
    private String username;
    private String server;
    private int port;
    private Cliente cliente;
    private boolean enviar;
    private Exchanger<String> exgrC ;
    private String mensaje;
    private Thread thread1;
    
	public Chat(String username,String server, int port)
	{
		super();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.username = username;      
        // Configuración clave
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.8; // Expandir en horizontal
        gbc.weighty = 0.7; // Expandir en vertical
        gbc.gridwidth = 1; // El botón ocupará  1 columnas
        gbc.fill = GridBagConstraints.BOTH; // Rellenar completamente
        gbc.anchor = GridBagConstraints.CENTER; // Ubica el botón
        chat_area = new TextChatPanel();
        //chat_area.setBackground(Color.CYAN);
        add(chat_area, gbc);
                
        
        // Configuración clave
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.weightx = 0.2; // Expandir en horizontal
        gbc.weighty = 0.7; // Expandir en vertical
        gbc.gridwidth = 1; // El botón ocupará  1 columnas
        gbc.fill = GridBagConstraints.BOTH; // Rellenar completamente
        gbc.anchor = GridBagConstraints.CENTER; // Ubica el botón
        users     = new UsersConnected();
        //users.setBackground(Color.BLUE);
        add(users, gbc);
        
        
     
        // Configuración clave
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.25; // Expandir en horizontal
        gbc.weighty = 0.3; // Expandir en vertical
        gbc.gridwidth = 1; // El botón ocupará  2 columnas
        gbc.gridheight = 3; // El botón ocupará  1 fila
        //gbc.fill = GridBagConstraints.BOTH; // Rellenar completamente
        gbc.anchor = GridBagConstraints.CENTER; // Ubica el botón        
        chat_msg  = new TextMsgPanel();
        //chat_msg.setBackground(Color.RED);
        add(chat_msg, gbc);
        
        
        
        // Configuración clave
        gbc.gridy = 1;
        gbc.gridx = 1;
        //gbc.weightx = 0.25; // Expandir en horizontal
        //gbc.weighty = 0.3; // Expandir en vertical
        gbc.gridwidth = 1; // El botón ocupará  2 columnas
        gbc.gridheight = 1; // El botón ocupará  1 fila
        gbc.fill = GridBagConstraints.NONE; // Rellenar completamente
        gbc.anchor = GridBagConstraints.CENTER; // Ubica el botón        
        enviar_btn = new JButton("Enviar");
        enviar_btn.setActionCommand("Enviar");
        enviar_btn.addActionListener(this);
        add(enviar_btn, gbc);
        
        
        // Configuración clave
        gbc.gridy = 3;
        gbc.gridx = 1;
        //gbc.weightx = 0.25; // Expandir en horizontal
        //gbc.weighty = 0.3; // Expandir en vertical
        gbc.gridwidth = 1; // El botón ocupará  2 columnas
        gbc.gridheight = 1; // El botón ocupará  1 fila
        gbc.fill = GridBagConstraints.NONE; // Rellenar completamente
        gbc.anchor = GridBagConstraints.CENTER; // Ubica el botón   
        salir_btn = new JButton("Salir");
        salir_btn.setActionCommand("Salir");
        salir_btn.addActionListener(this);
        add(salir_btn, gbc);
        
        
        exgrC = new Exchanger<String>();
        cliente = new Cliente(username,server,port,exgrC,chat_area,users);  
        thread1 = new Thread(cliente);
        thread1.start();
        enviar = false;
	}
  
    
    @Override
    public void actionPerformed(ActionEvent e)
    {         
        
        String command = e.getActionCommand();
        
        if(command.equals( enviar_btn.getActionCommand() ) )
        {            
            //SE OBTIENE EL MENSAJE EN EL TEXTAREA
            
            String text;
            String msg = chat_msg.get_text();
            System.out.println("Enviar: "+msg);
            text = "["+username+"]: "+msg;
            try
            { 
                //SE ENVIA EL MENSAJE AL THREAD DEL CLIENTE
                text = exgrC.exchange(text);   
                text = "["+username+"]: "+msg;             
                chat_area.agregar(text);
            }
            catch(InterruptedException er){}
            chat_msg.set_text("");
        }
        else if(command.equals( salir_btn.getActionCommand() ) )
        {   
            
            thread1.interrupt();
			cliente.setCorrer(false);
			try{thread1.join();}catch(Exception ee){}
			
            System.out.println("Salir");
            Container tmp = getParent();
			while(tmp!= null && !tmp.getClass().getSimpleName().equals("App") )
			{
				tmp = tmp.getParent();
			}
			if(tmp!= null)
			{			
				App app = (App)tmp;
				app.setInicio();
			}
        } 
    }    
    
   
}
	
	
	
	

	
	
