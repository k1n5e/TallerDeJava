package proyecto_final;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Inicio extends JPanel implements ActionListener, FocusListener {
    private App app;
    private Font font;
    private JTextField credencial_tf;
    private JPasswordField pass_tf;
    private JButton iniciar_btn;
    private JButton registrar_btn;
    private JButton salir_btn; // ← Agregado como variable de clase
    private JLabel mensaje_lbl;
    public GridBagConstraints gbc;

    public Inicio(App app) {
        super();
        this.app = app;
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        font = new Font("Arial", Font.PLAIN, 18);
        JLabel temp;

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        temp = new JLabel("Correo o usuario:");
        temp.setFont(font);
        add(temp, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        credencial_tf = new JTextField(20);
        credencial_tf.setFont(font);
        credencial_tf.addFocusListener(this);
        add(credencial_tf, gbc);

        // password
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        temp = new JLabel("Contrasenaa:");
        temp.setFont(font);
        add(temp, gbc);

        // campo para password
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        pass_tf = new JPasswordField(20);
        pass_tf.setFont(font);
        pass_tf.addFocusListener(this);
        add(pass_tf, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mensaje_lbl = new JLabel(" ");
        mensaje_lbl.setFont(new Font("Arial", Font.ITALIC, 14));
        mensaje_lbl.setForeground(Color.RED);
        add(mensaje_lbl, gbc);

        // boton registrar
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        registrar_btn = new JButton("Registrar");
        registrar_btn.setFont(font);
        registrar_btn.setActionCommand("Registrar");
        registrar_btn.addActionListener(this);
        registrar_btn.addFocusListener(this);
        add(registrar_btn, gbc);

        // boton salir
        gbc.gridy = 4;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        salir_btn = new JButton("Salir"); // ← Ahora es variable de clase
        salir_btn.setFont(font);
        salir_btn.setActionCommand("Salir");
        salir_btn.addActionListener(this);
        salir_btn.addFocusListener(this);
        add(salir_btn, gbc);

        // boton iniciar
        gbc.gridy = 4;
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        iniciar_btn = new JButton("Iniciar sesion");
        iniciar_btn.setFont(font);
        iniciar_btn.setActionCommand("Iniciar");
        iniciar_btn.addActionListener(this);
        iniciar_btn.addFocusListener(this);
        add(iniciar_btn, gbc);
    }

    public void defaultFocus() {
        setVisible(false);
        setVisible(true);
        credencial_tf.requestFocus();
    }

    public void mostrarMensaje(String mensaje, boolean esError) {
        mensaje_lbl.setText(mensaje);
        mensaje_lbl.setForeground(esError ? Color.RED : Color.BLUE);
    }

    public void limpiarCampos() {
        credencial_tf.setText("");
        pass_tf.setText("");
        mensaje_lbl.setText(" ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Iniciar")) {
            String credencial = credencial_tf.getText().trim();
            String password = new String(pass_tf.getPassword()).trim();

            if (credencial.isEmpty() || password.isEmpty()) {
                mostrarMensaje("Debe ingresar todos los datos.", true);
                return;
            }

            Usuario usuario = app.autenticarUsuario(credencial, password);
            if (usuario != null) {
                app.setUsuarioActual(usuario);
                app.setMenuJuego();
                limpiarCampos();
            } else {
                mostrarMensaje("Credenciales incorrectas", true);
            }

        } else if (command.equals("Registrar")) {
            limpiarCampos();
            app.setRegistro();
        } else if (command.equals("Salir")) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "Salir del programa?",
                    "Salir",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public void focusGained(FocusEvent e) {
        if (credencial_tf.isFocusOwner())
            System.out.printf("\n CAMPO CREDENCIAL SELECCIONADO");
        if (pass_tf.isFocusOwner())
            System.out.printf("\n CAMPO CONTRASEÑA SELECCIONADO");
        if (registrar_btn.isFocusOwner())
            System.out.printf("\n BOTON REGISTRAR SELECCIONADO");
        if (iniciar_btn.isFocusOwner())
            System.out.printf("\n BOTON INICIAR SESION SELECCIONADO");
        if (salir_btn.isFocusOwner())
            System.out.printf("\n BOTON SALIR SELECCIONADO");
    }

    public void focusLost(FocusEvent e) {
        // System.out.printf("\n FOCUS LOST: %s",e);
    }
}