package edu.uabc.AEFC.Proyecto;

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class App extends JFrame {
    private Inicio panelPrincipal;
    private Registro panelSegundario;
    private MenuJuego panelMenuJuego;
    private SalasDisponibles panelSalasDisponibles;
    private Mapa panelMapa;
    private TreeSet<Usuario> usuarios;
    private Usuario usuarioActual;
    private GestorSalas gestorSalas;
    private String arch_usuarios = "serpientes_y_escaleras.txt";

    public App(String label) {
        super(label);
        gestorSalas = new GestorSalas();
        setLayout(new GridLayout(1, 1));
        setSize(1080, 720);
        setMinimumSize(new Dimension(640, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cargarUsuarios();

        panelPrincipal = new Inicio(this);
        panelSegundario = new Registro(this);
        panelMenuJuego = new MenuJuego(this);
        panelSalasDisponibles = new SalasDisponibles(this);

        panelMapa = new Mapa(this);
        setInicio();
        setVisible(true);
    }

    public void setSalasDisponibles() {
        getContentPane().setVisible(false);
        setContentPane(panelSalasDisponibles);
        panelSalasDisponibles.defaultFocus();
        getContentPane().setVisible(true);
    }

    public GestorSalas getGestorSalas() {
        return gestorSalas;
    }

    public Sala crearSala(String contrasena, int capacidad) {
        Usuario usuario = getUsuarioActual();
        return gestorSalas.crearSala(usuario.getUsername(), contrasena, capacidad);
    }

    public boolean unirseASala(int numeroSala, String contrasena) {
        Usuario usuario = getUsuarioActual();
        Sala sala = gestorSalas.buscarSala(numeroSala);
        if (sala != null) {
            return sala.unirJugador(usuario.getUsername(), contrasena);
        }
        return false;
    }

    public void setInicio() {
        getContentPane().setVisible(false);
        setContentPane(panelPrincipal);
        panelPrincipal.defaultFocus();
        getContentPane().setVisible(true);
    }

    public void setRegistro() {
        getContentPane().setVisible(false);
        setContentPane(panelSegundario);
        panelSegundario.defaultFocus();
        getContentPane().setVisible(true);
    }

    public void setMenuJuego() {
        getContentPane().setVisible(false);
        setContentPane(panelMenuJuego);
        panelMenuJuego.actualizarInformacion();
        panelMenuJuego.defaultFocus();
        getContentPane().setVisible(true);
    }
    public void setMapa(Sala sala){
        panelMapa.configurarSala(sala); 
        getContentPane().setVisible(false);
        setContentPane(panelMapa);
        panelMapa.requestFocus(); 
        getContentPane().setVisible(true);
    }

    private boolean existeUsuario(String correo, String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equals(correo)) {
                return true;
            }
            if (usuario.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public String registrarUsuario(String nombre, String apellido, String correo, String username, String password) {
        if (existeUsuario(correo, username)) {
            return "ERROR: Correo o nombre de usuario ya ocupados.";
        }

        Usuario nuevoUsuario = new Usuario(nombre, apellido, correo, username, password);
        usuarios.add(nuevoUsuario);
        guardarUsuarios();
        return "OK: Usuario registrado.";
    }

    public Usuario autenticarUsuario(String credencial, String password) {
        for (Usuario usuario : usuarios) {
            if ((usuario.getCorreo().equals(credencial) || usuario.getUsername().equals(credencial))
                    && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    private void guardarUsuarios() {
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(arch_usuarios);
                java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos)) {
            oos.writeObject(usuarios);
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(this, "Error guardando usuarios: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarUsuarios() {
        java.io.File archivo = new java.io.File(arch_usuarios);
        if (!archivo.exists()) {
            usuarios = new TreeSet<>();
            return;
        }

        try (java.io.FileInputStream fis = new java.io.FileInputStream(arch_usuarios);
                java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis)) {
            usuarios = (TreeSet<Usuario>) ois.readObject();
        } catch (java.io.IOException | ClassNotFoundException e) {
            usuarios = new TreeSet<>();
        }
    }
}