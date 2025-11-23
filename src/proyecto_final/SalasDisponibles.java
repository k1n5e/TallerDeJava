package edu.uabc.AEFC.Proyecto;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class SalasDisponibles extends JPanel implements ActionListener, FocusListener {
    private App app;
    private Font font;
    private JLabel titulo_lbl;
    private JPanel salasPanel;
    private JScrollPane scrollPane;
    private JButton actualizar_btn;
    private JButton regresar_btn;
    private JLabel mensaje_lbl;
    public GridBagConstraints gbc;
    private List<JButton> botonesUnirse;

    public SalasDisponibles(App app) {
        super();
        this.app = app;
        this.botonesUnirse = new ArrayList<>();
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        font = new Font("Arial", Font.PLAIN, 18);

        // titulo
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        titulo_lbl = new JLabel("SALAS DISPONIBLES");
        titulo_lbl.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo_lbl, gbc);

        // scroll
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        salasPanel = new JPanel();
        salasPanel.setLayout(new BoxLayout(salasPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(salasPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        add(scrollPane, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        // mensaje
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mensaje_lbl = new JLabel(" ");
        mensaje_lbl.setFont(new Font("Arial", Font.ITALIC, 14));
        mensaje_lbl.setForeground(Color.BLUE);
        add(mensaje_lbl, gbc);

        // boton actualizar
        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        actualizar_btn = new JButton("Actualizar");
        actualizar_btn.setFont(font);
        actualizar_btn.setBackground(Color.RED);
        actualizar_btn.setForeground(Color.WHITE);
        actualizar_btn.setActionCommand("Actualizar");
        actualizar_btn.addActionListener(this);
        actualizar_btn.addFocusListener(this);
        add(actualizar_btn, gbc);

        // boton regresar
        gbc.gridy = 3;
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        regresar_btn = new JButton("Regresar");
        regresar_btn.setFont(font);
        regresar_btn.setBackground(Color.RED);
        regresar_btn.setForeground(Color.WHITE);
        regresar_btn.setActionCommand("Regresar");
        regresar_btn.addActionListener(this);
        regresar_btn.addFocusListener(this);
        add(regresar_btn, gbc);
    }

    public void defaultFocus() {
        setVisible(false);
        setVisible(true);
        actualizar_btn.requestFocus();
        actualizarListaSalas();
    }

    public void actualizarListaSalas() {
        // Limpiar botones anteriores
        for (JButton boton : botonesUnirse) {
            boton.removeActionListener(this);
        }
        botonesUnirse.clear();
        salasPanel.removeAll();

        List<Sala> salas = app.getGestorSalas().getTodasLasSalas();

        if (salas.isEmpty()) {
            JLabel noSalasLabel = new JLabel("Aun no hay salas creadas.");
            noSalasLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            noSalasLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            salasPanel.add(noSalasLabel);

            JLabel instruccionLabel = new JLabel("Puedes crear una sala desde el menu inicial.");
            instruccionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            instruccionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            salasPanel.add(instruccionLabel);

            titulo_lbl.setText("NO HAY SALAS DISPONIBLES");
        } else {
            titulo_lbl.setText("SALAS DISPONIBLES - " + salas.size() + " SALAS EN TOTAL");

            for (Sala sala : salas) {
                salasPanel.add(crearPanelSala(sala));
                salasPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        salasPanel.revalidate();
        salasPanel.repaint();
    }

    private JPanel crearPanelSala(Sala sala) {
        JPanel panelSala = new JPanel();
        panelSala.setLayout(new BoxLayout(panelSala, BoxLayout.Y_AXIS));
        panelSala.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panelSala.setBackground(Color.WHITE);
        panelSala.setMaximumSize(new Dimension(600, 100));

        // informaion para las salas
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String tipo = sala.isEsPrivada() ? "PRIVADA" : "PUBLICA";
        String estado = sala.tieneEspacio() ? "DISPONIBLE" : "LLENA";
        Color colorEstado = sala.tieneEspacio() ? Color.GREEN : Color.RED;

        JLabel infoLabel = new JLabel(String.format(
                "%s - Sala No. %d | Jugadores: %d/%d | %s | %s",
                sala.getNombreAnfitrion(), sala.getNumeroSala(),
                sala.getJugadoresActuales(), sala.getCapacidad(), tipo, estado));
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoLabel.setForeground(colorEstado);
        infoPanel.add(infoLabel);

        panelSala.add(infoPanel);

        // Botón Unirse
        if (sala.tieneEspacio()) {
            JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton unirseBtn = new JButton("Unirse");
            unirseBtn.setBackground(Color.RED);
            unirseBtn.setForeground(Color.WHITE);
            unirseBtn.setFont(new Font("Arial", Font.BOLD, 12));
            unirseBtn.setActionCommand("Unirse_" + sala.getNumeroSala());
            unirseBtn.addActionListener(this);
            unirseBtn.addFocusListener(this);

            botonPanel.add(unirseBtn);
            panelSala.add(botonPanel);
            botonesUnirse.add(unirseBtn);
        }

        return panelSala;
    }

    public void mostrarMensaje(String mensaje, boolean esError) {
        mensaje_lbl.setText(mensaje);
        mensaje_lbl.setForeground(esError ? Color.RED : Color.BLUE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.startsWith("Unirse_")) {
            // Extraer el número de sala del comando
            int numeroSala = Integer.parseInt(command.substring(7));
            unirseASalaEspecifica(numeroSala);
        } else {
            switch (command) {
                case "Refrescar":
                    actualizarListaSalas();
                    mostrarMensaje("Lista actualizada", false);
                    break;

                case "Regresar":
                    app.setMenuJuego();
                    break;
            }
        }
    }

    private void unirseASalaEspecifica(int numeroSala) {
        Sala sala = app.getGestorSalas().buscarSala(numeroSala);

        if (sala == null) {
            mostrarMensaje("Sala no encontrada", true);
            return;
        }

        if (!sala.tieneEspacio()) {
            mostrarMensaje("La sala esta llena", true);
            return;
        }

        String contrasena = "";
        if (sala.isEsPrivada()) {
            contrasena = JOptionPane.showInputDialog(this,
                    "Contrasena:",
                    "Contrasena requerida",
                    JOptionPane.QUESTION_MESSAGE);
            if (contrasena == null)
                return;
        }

        if (app.unirseASala(numeroSala, contrasena)) {
            mostrarMensaje("Te has unido a la sala " + numeroSala, false);
            actualizarListaSalas();
            app.setMapa(sala);
        } else {
            mostrarMensaje("No se pudo acceder. Verifique la contrasena.", true);
        }
    }

    public void focusGained(FocusEvent e) {
        if (actualizar_btn.isFocusOwner())
            System.out.printf("\n BOTON REFRESCAR SELECCIONADO");
        if (regresar_btn.isFocusOwner())
            System.out.printf("\n BOTON REGRESAR SELECCIONADO");

        // Para los botones de unirse individuales
        for (JButton boton : botonesUnirse) {
            if (boton.isFocusOwner()) {
                System.out.printf("\n BOTON UNIRSE A SALA SELECCIONADO");
                break;
            }
        }
    }

    public void focusLost(FocusEvent e) {
        // System.out.printf("\n FOCUS LOST: %s",e);
    }
}