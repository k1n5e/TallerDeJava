package edu.uabc.eabj.Chat;

/*
javac -d bin -sourcepath src src/edu/uabc/eabj/Chat/Datagramas.java
java -cp bin edu.uabc.eabj.Chat.Datagramas
*/

import java.net.*;
import java.io.IOException;

public class HiloEnviar implements Runnable
{
	private DatagramSocket ds; 
	private int buffer_size;	
	private byte[] buffer;
	private InetAddress addr;
	private int port;
	
	public HiloEnviar(DatagramSocket ds,String addr,int port,int size)
	{
		
		this.ds = ds;
		try
		{
			this.addr = InetAddress.getByName(addr);			
		}catch(UnknownHostException e){}
		
		this.port = port;
		
		this.buffer_size = size;
		buffer = new byte[buffer_size];
		
	}
	
	
	public void run()
	{
		
		int pos=0;
		int c = 0;
		while (true) 
		{
			try
			{
				c = System.in.read();
			}
			catch(IOException e){}
			switch (c)
			{
				case -1:
						System.out.println("Server Quits.");
						ds.close();
						return;
				case '\r':
						break;
				case '\n':
						try
						{
							ds.send(new DatagramPacket(buffer,pos,addr,port));
						}
						catch(IOException e)
						{
							System.out.println("Error al enviar");
						}
						pos=0;
						break;
				default:
						buffer[pos++] = (byte) c;
			}
		}			
	}
}