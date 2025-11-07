package proyecto_final;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Registro extends JPanel implements ActionListener, FocusListener {
    private App app;
    private Font font;
    private JTextField nombre_tf;
    private JTextField apellido_tf;
    private JTextField correo_tf;
    private JTextField username_tf;
    private JPasswordField pass_tf;
    private JButton registrar_btn;
    private JButton cancelar_btn;
    private JLabel mensaje_lbl;
    public GridBagConstraints gbc;

    public Registro(App app) {
        super();
        this.app = app;
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        font = new Font("Arial", Font.PLAIN, 18);
        JLabel temp;

        // titulo
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        temp = new JLabel("REGISTRO DE USUARIO");
        temp.setFont(new Font("Arial", Font.BOLD, 24));
        add(temp, gbc);

        // etiqueta nombre
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        temp = new JLabel("Nombre:");
        temp.setFont(font);
        add(temp, gbc);

        // campo nombre
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        nombre_tf = new JTextField(20);
        nombre_tf.setFont(font);
        nombre_tf.addFocusListener(this);
        add(nombre_tf, gbc);

        // etiqueta apellido
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        temp = new JLabel("Apellido:");
        temp.setFont(font);
        add(temp, gbc);

        // campo apellido
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        apellido_tf = new JTextField(20);
        apellido_tf.setFont(font);
        apellido_tf.addFocusListener(this);
        add(apellido_tf, gbc);

        // etiqueta correo
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        temp = new JLabel("Correo:");
        temp.setFont(font);
        add(temp, gbc);

        // campo correo
        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        correo_tf = new JTextField(20);
        correo_tf.setFont(font);
        correo_tf.addFocusListener(this);
        add(correo_tf, gbc);

        // etiqueta username
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        temp = new JLabel("Usuario:");
        temp.setFont(font);
        add(temp, gbc);

        // campo username
        gbc.gridy = 4;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        username_tf = new JTextField(20);
        username_tf.setFont(font);
        username_tf.addFocusListener(this);
        add(username_tf, gbc);

        // etiqueta password
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        temp = new JLabel("Contrasena:");
        temp.setFont(font);
        add(temp, gbc);

        // campo password
        gbc.gridy = 5;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        pass_tf = new JPasswordField(20);
        pass_tf.setFont(font);
        pass_tf.addFocusListener(this);
        add(pass_tf, gbc);

        // mensaje
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mensaje_lbl = new JLabel(" ");
        mensaje_lbl.setFont(new Font("Arial", Font.ITALIC, 14));
        mensaje_lbl.setForeground(Color.RED);
        add(mensaje_lbl, gbc);

        // boton cancelar
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        cancelar_btn = new JButton("Cancelar");
        cancelar_btn.setFont(font);
        cancelar_btn.setActionCommand("Cancelar");
        cancelar_btn.addActionListener(this);
        cancelar_btn.addFocusListener(this);
        add(cancelar_btn, gbc);

        // boton registrar
        gbc.gridy = 7;
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        registrar_btn = new JButton("Registrar");
        registrar_btn.setFont(font);
        registrar_btn.setActionCommand("Registrar");
        registrar_btn.addActionListener(this);
        registrar_btn.addFocusListener(this);
        add(registrar_btn, gbc);
    }

    public void defaultFocus() {
        setVisible(false);
        setVisible(true);
        nombre_tf.requestFocus();
    }

    public void mostrarMensaje(String mensaje, boolean esError) {
        mensaje_lbl.setText(mensaje);
        // para determinar si algun dato es correcto o no, si es incorrecto rojo, sino
        // azul
        mensaje_lbl.setForeground(esError ? Color.RED : Color.BLUE);
    }

    public void limpiarCampos() {
        nombre_tf.setText("");
        apellido_tf.setText("");
        correo_tf.setText("");
        username_tf.setText("");
        pass_tf.setText("");
        mensaje_lbl.setText(" ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Registrar")) {
            String nombre = nombre_tf.getText().trim();
            String apellido = apellido_tf.getText().trim();
            String correo = correo_tf.getText().trim();
            String username = username_tf.getText().trim();
            String password = new String(pass_tf.getPassword()).trim();

            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || username.isEmpty()
                    || password.isEmpty()) {
                mostrarMensaje("Todos los campos son obligatorios.", true);
                return;
            }

            String resultado = app.registrarUsuario(nombre, apellido, correo, username, password);

            if (resultado.startsWith("OK:")) {
                mostrarMensaje("Usuario registrado exitosamente", false);
                JOptionPane.showMessageDialog(this, "Usuario registrado.",
                        "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                app.setInicio();
            } else {
                mostrarMensaje(resultado.substring(6), true);
            }

        } else if (command.equals("Cancelar")) {
            limpiarCampos();
            app.setInicio();
        }
    }

    public void focusGained(FocusEvent e) {
        if (nombre_tf.isFocusOwner())
            System.out.printf("\n CAMPO NOMBRE SELECCIONADO");
        if (apellido_tf.isFocusOwner())
            System.out.printf("\n CAMPO APELLIDO SELECCIONADO");
        if (correo_tf.isFocusOwner())
            System.out.printf("\n CAMPO CORREO SELECCIONADO");
        if (username_tf.isFocusOwner())
            System.out.printf("\n CAMPO USUARIO SELECCIONADO");
        if (pass_tf.isFocusOwner())
            System.out.printf("\n CAMPO CONTRASENA SELECCIONADO");
        if (cancelar_btn.isFocusOwner())
            System.out.printf("\n BOTON CANCELAR SELECCIONADO");
        if (registrar_btn.isFocusOwner())
            System.out.printf("\n BOTON REGISTRAR SELECCIONADO");
    }

    public void focusLost(FocusEvent e) {
        // System.out.printf("\n FOCUS LOST: %s",e);
    }
}