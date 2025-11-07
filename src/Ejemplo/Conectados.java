package edu.uabc.eabj.Chat;

/*
javac -d bin -sourcepath src src/edu/uabc/eabj/Chat/*.java
java -cp bin edu.uabc.eabj.Chat.Main
*/


import java.io.*;
import java.net.*;
import java.util.*;

public class Conectados implements Runnable
{ 
    private List<ClientHandler> clientes;
    private BufferedReader in;
	
    public Conectados(List<ClientHandler> clientes) 
    {
        this.clientes = clientes;
    }
	
    public void run() 
    {
		while(true)
		{
			String message = "SERVER:";
			for (ClientHandler cH: clientes) 
			{    
				Socket client = cH.get_Socket();
				String username = cH.get_username();
				System.out.println("USER: "+username+" "+client.isConnected()+" "+client.isClosed()+" "+client);
				if(!client.isClosed() && !username.equals(""))
				{
					message  += "["+client.getInetAddress().toString()+"]: "+username +",";
				}
			}
			
			for (ClientHandler cH: clientes) 
			{
				
				Socket client = cH.get_Socket();
				if (!client.isClosed()) 
				{
					try 
					{
						//SE CREA UN BUFFER PARA TRANSMISION DE MENSAJES PARA CADA CLIENTE
						PrintWriter out = new PrintWriter(client.getOutputStream(), true);
						//ENVIAR MENSAJE
						out.println(message);
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}				
				}
			}
			
			try
			{
				Thread.sleep(1000);
			}catch(InterruptedException e){}
			
		}	                
    }

}
     