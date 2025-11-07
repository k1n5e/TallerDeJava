package edu.uabc.eabj.Chat;

/*
javac -d bin -sourcepath src src/edu/uabc/eabj/Chat/Datagramas.java
java -cp bin edu.uabc.eabj.Chat.Datagramas
*/


import java.net.*;
import java.util.Scanner;

public class Datagramas 
{
	public static Thread enviar;
	public static Thread recibir;
	public static DatagramSocket ds;    
	public static int port = 10000;
	public static int buffer_size = 1024;   
	

	public static void main(String[] args)
	{
		ds = null;
		try
		{
			ds = new DatagramSocket(port);
		}
		catch(SocketException e)
		{
				System.out.println("No se pudo crear el DatagramSocket");
				return;
		}
		
		String addr;
		Scanner input = new Scanner(System.in);
		System.out.print("\n Captura ip remota: ");
		addr = input.nextLine();
		
		HiloEnviar hiloenviar = new HiloEnviar(ds,addr,port,buffer_size);
		HiloRecibir hilorecibir = new HiloRecibir(ds,buffer_size);
		
		enviar = new Thread(hiloenviar);
		recibir = new Thread(hilorecibir);
		
		enviar.start();
		recibir.start();
	}
}
     