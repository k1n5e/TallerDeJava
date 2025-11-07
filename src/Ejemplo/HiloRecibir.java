package edu.uabc.eabj.Chat;

/*
javac -d bin -sourcepath src src/edu/uabc/eabj/Chat/Datagramas.java
java -cp bin edu.uabc.eabj.Chat.Datagramas
*/

	import java.net.*;
	import java.io.IOException;

	public class HiloRecibir implements Runnable
	{
		private DatagramSocket ds; 
		private byte[] buffer;
		
		public HiloRecibir(DatagramSocket ds,int size)
		{
			this.ds = ds;
			buffer = new byte[size];
		}
		
		public void run()
		{		
			while(true) 
			{
				DatagramPacket p = new DatagramPacket(buffer, buffer.length);
				try
				{
					ds.receive(p);
				}
				catch(IOException e)
				{
					System.out.println("Error al recibir");
					if(ds.isClosed())
						return;
				}
				System.out.println("Recepcion :"+new String(p.getData(), 0, p.getLength()));
			}
		}

	}