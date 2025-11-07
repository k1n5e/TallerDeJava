package edu.uabc.eabj.Chat;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Inicio extends JPanel  implements ActionListener
{
    private JTextField username_tf;
    private JTextField ip_tf;
    private JTextField port_tf;
    private JButton connect_btn;
    private JButton host_btn;
    private Font font;
    private Server server;
    
	public Inicio()
	{
		super();
        setLayout(new GridBagLayout());
         // Crear un objeto GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();

        // Configurar el valor por defecto para los GridBagConstraints
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Los componentes se expandirán en dirección horizontal
        //MARGENES
        gbc.insets.left = 10;
        gbc.insets.right = 10;
        gbc.insets.top = 10;
        
        font = new Font("Nuevo",Font.PLAIN,18);
        JLabel temp;
        
        // Etiqueta Nombre
        gbc.gridy = 0; // Fila 0
        gbc.gridx = 0; // Fila 0, columna 0
        gbc.gridwidth = 1; // Un solo espacio horizontal
        temp = new JLabel("NOMBRE:");
        temp.setFont(font);
        add(temp, gbc);
        // Campo de texto Nombre
        gbc.gridy = 0; // Fila 0
        gbc.gridx = 1; // Columna 1
        gbc.gridwidth = 2; // Ocupa dos columnas
        username_tf = new JTextField(20);
        username_tf.setText("SirAlbert");
        username_tf.setFont(font);
        add(username_tf, gbc);        
        
        // Etiqueta IP
        gbc.gridy = 1; // Fila 1
        gbc.gridx = 0; // Fila 1, columna 0
        gbc.gridwidth = 1; // Un solo espacio horizontal
        temp = new JLabel("IP:");
        temp.setFont(font);
        add(temp, gbc);
        // Campo de texto IP
        gbc.gridy = 1; // Fila 0
        gbc.gridx = 1; // Columna 1
        gbc.gridwidth = 2; // Ocupa dos columnas
        ip_tf = new JTextField(20);
        ip_tf.setText("127.0.0.1");
        ip_tf.setFont(font);
        add(ip_tf, gbc);
               
        
        // Etiqueta PORT
        gbc.gridy = 2; // Fila 2
        gbc.gridx = 0; // Fila 2, columna 0
        gbc.gridwidth = 1; // Un solo espacio horizontal
        temp = new JLabel("PORT:");
        temp.setFont(font);
        add(temp, gbc);
        // Campo de texto PORT
        gbc.gridy = 2; // Fila 2
        gbc.gridx = 1; // Columna 1
        gbc.gridwidth = 2; // Ocupa dos columnas
        port_tf = new JTextField(20);
        port_tf.setText("10010");
        port_tf.setFont(font);
        add(port_tf, gbc);
    
        // Botón de Host
        gbc.gridy = 3; // Fila 3
        gbc.gridx = 0; // Fila 3, columna 0
        gbc.gridwidth = 1; // El botón ocupará las 1 columnas
        gbc.anchor = GridBagConstraints.WEST; // Ubica el botón
        gbc.fill = GridBagConstraints.NONE; // No ajusta el tamaño
        host_btn = new JButton("Host");
        host_btn.setFont(font);
        host_btn.setActionCommand("Host");
        host_btn.addActionListener(this);
        add(host_btn, gbc);
        
        // Botón de Conectar
        gbc.gridy = 3; // Fila 3
        gbc.gridx = 2; // Fila 3, columna 2
        gbc.gridwidth = 1; // El botón ocupará las 1 columnas
        gbc.anchor = GridBagConstraints.EAST; // Ubica el botón
        gbc.fill = GridBagConstraints.NONE; // No ajusta el tamaño
        connect_btn = new JButton("Conectar");
        connect_btn.setFont(font);
        connect_btn.setActionCommand("Conectar");
        connect_btn.addActionListener(this);
        add(connect_btn, gbc);
                
        
	}
	
   
    @Override
    public void actionPerformed(ActionEvent e)
    {         
        
        String command = e.getActionCommand();
        
        if(command.equals( connect_btn.getActionCommand() ) )
        {
            System.out.println("Conectar");
            
            Container tmp = getParent();
			while(tmp!= null && !tmp.getClass().getSimpleName().equals("App") )
			{
				tmp = tmp.getParent();
			}
			if(tmp!= null)
			{			
                //EL USUARIO SE CONECTA A UN HOST EXTERNO
                String username = username_tf.getText();
                String server = ip_tf.getText();
                int port = Integer.parseInt(port_tf.getText());
				App app = (App)tmp;
                Chat chat = new Chat(username,server,port);
				app.setChat(chat);
			}
        }
        else if(command.equals( host_btn.getActionCommand() ) )
        {
            Container tmp = getParent();
			while(tmp!= null && !tmp.getClass().getSimpleName().equals("App") )
			{
				tmp = tmp.getParent();
			}
			if(tmp!= null)
			{	
                //EL USUARIO SERA EL HOST
                String server = ip_tf.getText();
                int port = Integer.parseInt(port_tf.getText());
                this.server = new Server(port);
                String username = username_tf.getText();
                Chat chat = new Chat(username,server,port);
				App app = (App)tmp;
				app.setChat(chat);
            }
        }
    }
}
	
	
	
	

	
	
