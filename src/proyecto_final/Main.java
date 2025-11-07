package proyecto_final;

//HOLA MUNDO

/*
 *      Compile: javac -d bin -sourcepath proyecto_final/*.java
 *      Eject:   java -cp bin proyecto_final.Main
 */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Main {
    public static void main(String[] args) {
        try {
            // CARGAR INTERFAZ DE SISTEMA
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception error) {
            System.out.println(error);
        }

        SwingUtilities.invokeLater(() -> {
            App aplicacion = new App("Sistema de Usuarios - Taller 7");
        });
    }
}
