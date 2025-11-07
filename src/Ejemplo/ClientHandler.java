package edu.uabc.eabj.Chat;

/*
javac -d bin -sourcepath src src/edu/uabc/eabj/Chat/*.java
java -cp bin edu.uabc.eabj.Chat.Main
*/


import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable
{ 
    private List<ClientHandler> clientes;
    private Socket socket;
	private String username;
    private BufferedReader in;

    public ClientHandler(Socket socket,List<ClientHandler> clientes) 
    {
        this.clientes = clientes;
        this.socket = socket;
		this.username = "";
        try 
        {
            //SE CREA UN BUFFER PARA LA RECEPCION DE MENSAJES DEL SERVIDOR
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }       
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void run() 
    {
        String message;
        try 
        {
            //ESPERA PARA RECEPCION DE MENSAJES
            while (socket.isConnected() && (message = in.readLine()) != null) 
            {
				System.out.println("Mensaje recibido: " + message);
				if(message.startsWith("username:"))
				{
					String tmp = message.replace("username:","");
					username = tmp;
				}
				else
				{
					//DISTRIBUCION DE MENSAJES PARA LOS DEMAS CLIENTES
					sendToOtherClients(socket, message);
				}
            }
        } 
        catch (IOException e) 
        {
			try 
			{
				socket.close();
			}  catch (IOException f) {}
            System.out.println("Cliente desconectado: " + socket);
        }
    }

    private void sendToOtherClients(Socket sender, String message) 
    {
		Socket socket;
        for (ClientHandler client : clientes) 
        {
			socket = client.get_Socket();
            if (socket != sender) 
            {
                try 
                {
                    //SE CREA UN BUFFER PARA TRANSMISION DE MENSAJES PARA CADA CLIENTE
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    //ENVIAR MENSAJE
                    out.println(message);
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
    }
	
	public Socket get_Socket()
	{
		return socket;
	}
	
	public String get_username()
	{
		return new String(username);
	}
}

     