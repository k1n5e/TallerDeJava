package edu.uabc.eabj.Chat;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Cliente implements Runnable
{
    private String serverAddress;
    private int port;
    private Exchanger<String> ex;
    private TextChatPanel chat_area;
	private UsersConnected users;
    private String username;
	private boolean correr;
	private Socket sc;
    public Cliente(String username,String serverAddress, int port,Exchanger<String> ex,TextChatPanel chat_area,UsersConnected users)
    {
        this.serverAddress = serverAddress;
        this.port = port;
        this.ex = ex;
        this.chat_area = chat_area;
		this.users = users;
		this.username = username;
    }
	
	public synchronized void setCorrer(boolean c)
	{
		correr = false;
		try{sc.close();}catch(Exception e){}
	}
    
    public void run() 
    {  
        //try-with-resources
        //LOS ELEMENTOS ENTRE ( ... ) SE CIERRAN AL TERMINAR EL TRY
        //Socket,BufferedReader,PrintWriter
        try ( Socket socket = new Socket(serverAddress, port);
             BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) 
		{
			sc = socket;
            System.out.println("Conectado al servidor.");
			try
                {
                    //SE RECIBE EL MENSAJE DEL TEXT AREA                   
                    //SE ENVIA AL SERVIDOR
                    out.println("username:"+username);
					System.out.println(username);
                }catch(Exception er)
                {
                    correr= false;                    
                    try{socket.close();}catch(IOException f){}
                }
				
            // THREAD PARA LECTURA DE MENSAJES DEL SERVIDOR
            new Thread(() -> 
			{
                String serverMessage;
                try 
                {
                    while ((serverMessage = serverIn.readLine()) != null) 
                    {            
						if(serverMessage.startsWith("SERVER:"))
						{
							String tmp = serverMessage.replace("SERVER:","");
							users.setUsers(tmp);
							
						}
                        else
						{
							//SE MUESTRA EL MENSAJE RECIBIDO EN EL CHAT
							chat_area.agregar(serverMessage);
						}
                    }
                } 
                catch (IOException e) 
                {
                    System.out.println("Conexión cerrada.");
                    try{socket.close();}catch(IOException f){}
                }
            }).start();

            // ENVIO DE MENSAJES AL SERVIDOR
            String userInput = null;
            boolean correr = true;
            while(correr)
            {
                try
                {
                    //SE RECIBE EL MENSAJE DEL TEXT AREA
                    userInput = ex.exchange(new String());
                    //SE ENVIA AL SERVIDOR
                    out.println(userInput);
                }catch(InterruptedException er)
                {
                    correr= false;                    
                    try{socket.close();}catch(IOException f){}
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
