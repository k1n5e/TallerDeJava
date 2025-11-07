package edu.uabc.eabj.Chat;

/*
javac -d bin -sourcepath src src/edu/uabc/eabj/Chat/*.java
java -cp bin edu.uabc.eabj.Chat.Main
*/


import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable
{ 
	//LISTA PARA ALMACENAR LOS CLIENTES CONECTADOS
    private static List<ClientHandler> clientes = new ArrayList<>();
    private ServerSocket s_socket;
    private int port;
    private Thread thread;
    private Thread conectados;
    
    public Server(int port)
    {
        this.port = port;
        thread = new Thread(this);
        thread.start();
		//Thread de conectados
        conectados = new Thread(new Conectados(clientes));
        conectados.start();
    }
    
    public void run()
    {
        ClientHandler tmp;
        try 
        {
			//SERVIDOR
			//ESTABLECER PUERTO QUE ESCUCHARA PETICIONES
            s_socket = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port);

            while (true) 
            {
				//ESPERAR CONEXION CON CLIENTE
                Socket clientSocket = s_socket.accept();
				clientSocket.setKeepAlive(false);
				tmp = new ClientHandler(clientSocket,clientes);				
                clientes.add(tmp);
                System.out.println("Nuevo cliente conectado: " + clientSocket);
				//CREAR THREAD PARA MANEJAR LA COMUNICACION ENTRE
				//EL SERVIDOR Y EL CLIENTE
                new Thread(tmp).start();
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
    }
}
     