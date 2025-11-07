package edu.uabc.eabj.Chat;

/*
javac -d bin -sourcepath src src/edu/uabc/eabj/Chat/*.java
java -cp bin edu.uabc.eabj.Chat.Main
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;


public class Main
{
    public static void main(String []args)
    {
        if(args.length>0 && args[0].equals("server"))
        {            
            prog2();
        }
        else
            prog1();
    }
    
    public static void prog1()
    {        
        try
        {         
            //CARGAR INTERFAZ DE SISTEMA
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //REVERTIR
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(Exception error)
        {
            System.out.println(error);
        }       
	
		SwingUtilities.invokeLater( () ->		
		{
			App aplicacion = new App("Ejemplo");
			
		});        
    }
    
    public static void prog2()
    {
        new Server(10010);
        
    }
}


