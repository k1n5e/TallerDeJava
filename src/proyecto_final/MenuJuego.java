package edu.uabc.AEFC.Proyecto;

import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuJuego extends JPanel implements ActionListener, FocusListener {
    private App app;
    private Font font;
    private JLabel info_lbl;
    private JButton crearSala_btn;
    private JButton unirseJuego_btn;
    private JButton verSalas_btn;
    private JButton cerrar_btn;
    private JLabel mensaje_lbl;
    public GridBagConstraints gbc;

    public MenuJuego(App app) {
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
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        temp = new JLabel("SERPIENTES Y ESCALERAS");
        temp.setFont(new Font("Arial", Font.BOLD, 24));
        add(temp, gbc);

        // informacion del usuario
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        info_lbl = new JLabel();
        info_lbl.setFont(new Font("Arial", Font.PLAIN, 16));
        info_lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(info_lbl, gbc);

        // boton crear sala
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        crearSala_btn = new JButton("Crear Sala");
        crearSala_btn.setFont(font);
        crearSala_btn.setActionCommand("CrearSala");
        crearSala_btn.addActionListener(this);
        crearSala_btn.addFocusListener(this);
        add(crearSala_btn, gbc);

        // boton unirse a juego
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        unirseJuego_btn = new JButton("Unirse a Juego");
        unirseJuego_btn.setFont(font);
        unirseJuego_btn.setActionCommand("UnirseJuego");
        unirseJuego_btn.addActionListener(this);
        unirseJuego_btn.addFocusListener(this);
        add(unirseJuego_btn, gbc);

        // boton ver salas
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        verSalas_btn = new JButton("Salas Disponibles");
        verSalas_btn.setFont(font);
        verSalas_btn.setActionCommand("VerSalas");
        verSalas_btn.addActionListener(this);
        verSalas_btn.addFocusListener(this);
        add(verSalas_btn, gbc);

        // boton cerrar sesion
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        cerrar_btn = new JButton("Cerrar Sesion");
        cerrar_btn.setFont(font);
        cerrar_btn.setActionCommand("Cerrar");
        cerrar_btn.addActionListener(this);
        cerrar_btn.addFocusListener(this);
        add(cerrar_btn, gbc);

        // boton salir
        gbc.gridy = 6;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        JButton salir_btn = new JButton("Salir");
        salir_btn.setFont(font);
        salir_btn.setActionCommand("Salir");
        salir_btn.addActionListener(this);
        salir_btn.addFocusListener(this);
        add(salir_btn, gbc);

        // mensaje
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mensaje_lbl = new JLabel(" ");
        mensaje_lbl.setFont(new Font("Arial", Font.ITALIC, 14));
        mensaje_lbl.setForeground(Color.BLUE);
        add(mensaje_lbl, gbc);
    }

    public void defaultFocus() {
        setVisible(false);
        setVisible(true);
        crearSala_btn.requestFocus();
    }

    public void actualizarInformacion() {
        Usuario usuario = app.getUsuarioActual();
        if (usuario != null) {
            info_lbl.setText("Bienvenido " + usuario.getNombre() + " " + usuario.getApellido() + "!");
        }
    }

    public void mostrarMensaje(String mensaje, boolean esError) {
        mensaje_lbl.setText(mensaje);
        mensaje_lbl.setForeground(esError ? Color.RED : Color.BLUE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "CrearSala":
                crearSala();
                break;

            case "UnirseJuego":
                unirseASala();
                break;

            case "VerSalas":
                app.setSalasDisponibles();
                break;

            case "Cerrar":
                app.cerrarSesion();
                app.setInicio();
                break;

            case "Salir":
                int quest = JOptionPane.showConfirmDialog(this,
                        "El programa se cerrara, ¿esta seguro?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (quest == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                break;
        }
    }

    // mtodos
    private void crearSala() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField capacidadField = new JTextField("4");
        JPasswordField passwordField = new JPasswordField();
        JCheckBox privadaCheck = new JCheckBox("Sala privada");

        panel.add(new JLabel("Capacidad: "));
        panel.add(capacidadField);
        panel.add(new JLabel("Contrsena: "));
        panel.add(passwordField);
        panel.add(new JLabel(""));
        panel.add(privadaCheck);

        int result = JOptionPane.showConfirmDialog(this, panel, "Crear Nueva Sala", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int capacidad = Integer.parseInt(capacidadField.getText());
                String contrasena = privadaCheck.isSelected() ? new String(passwordField.getPassword()) : "";

                if (capacidad < 2 || capacidad > 8) {
                    mostrarMensaje("La capacidad debe estar entre 2 y 8 jugadores.", true);
                    return;
                }

                Sala nuevaSala = app.crearSala(contrasena, capacidad);
                mostrarMensaje("Sala " + nuevaSala.getNumeroSala() + " creada.", false);
                app.setMapa(nuevaSala);
            } catch (NumberFormatException ex) {
                mostrarMensaje("Capacidad invalida", true);
            }
        }
    }

    private void unirseASala() {
        String numeroSalaStr = JOptionPane.showInputDialog(this,
                "Numero de sala: ",
                "Unirse a Sala",
                JOptionPane.QUESTION_MESSAGE);

        if (numeroSalaStr != null && !numeroSalaStr.trim().isEmpty()) {
            try {
                int numeroSala = Integer.parseInt(numeroSalaStr);
                Sala sala = app.getGestorSalas().buscarSala(numeroSala);

                if (sala == null) {
                    mostrarMensaje("Sala no encontrada", true);
                    return;
                }

                String contrasena = "";
                if (sala.isEsPrivada()) {
                    contrasena = JOptionPane.showInputDialog(this,
                            "Sala privada", "Contrasena requerida", JOptionPane.QUESTION_MESSAGE);
                    if (contrasena == null) {
                        return;
                    }
                }

                if (app.unirseASala(numeroSala, contrasena)) {
                    mostrarMensaje("Te has unido a la sala " + numeroSala, false);
                    app.setMapa(sala);
                } else {
                    mostrarMensaje("No fue posible hacer la operacion. Verifique la informacion.", true);
                }
            } catch (NumberFormatException ex) {
                mostrarMensaje("Numero de sala invalido", true);
            }
        }
    }

    public void focusGained(FocusEvent e) {
        if (crearSala_btn.isFocusOwner())
            System.out.printf("\n BOTON CREAR SALA SELECCIONADO");
        if (unirseJuego_btn.isFocusOwner())
            System.out.printf("\n BOTON UNIRSE A JUEGO SELECCIONADO");
        if (cerrar_btn.isFocusOwner())
            System.out.printf("\n BOTON CERRAR SESION SELECCIONADO");
    }

    public void focusLost(FocusEvent e) {
        // System.out.printf("\n FOCUS LOST: %s",e);
    }
}