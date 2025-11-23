package edu.uabc.AEFC.Proyecto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Sala implements Serializable {
    private static final long serialVersionUID = 2L;

    private int numeroSala;
    private String nombreAnfitrion;
    private String contraseña;
    private int capacidad;
    private int jugadoresActuales;
    private boolean esPrivada;
    private Set<String> jugadoresConectados;

    public Sala(int numeroSala, String nombreAnfitrion, String contraseña, int capacidad) {
        this.numeroSala = numeroSala;
        this.nombreAnfitrion = nombreAnfitrion;
        this.contraseña = contraseña;
        this.capacidad = capacidad;
        this.jugadoresActuales = 1;
        this.esPrivada = (contraseña != null && !contraseña.trim().isEmpty());
        this.jugadoresConectados = new HashSet<>();
        this.jugadoresConectados.add(nombreAnfitrion);
    }

    // Getters
    public int getNumeroSala() {
        return numeroSala;
    }

    public String getNombreAnfitrion() {
        return nombreAnfitrion;
    }

    public String getContraseña() {
        return contraseña;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getJugadoresActuales() {
        return jugadoresActuales;
    }

    public boolean isEsPrivada() {
        return esPrivada;
    }

    public Set<String> getJugadoresConectados() {
        return jugadoresConectados;
    }

    public boolean tieneEspacio() {
        return jugadoresActuales < capacidad;
    }

    public boolean unirJugador(String nombreJugador, String contraseñaIngresada) {
        if (!tieneEspacio())
            return false;
        if (esPrivada && !this.contraseña.equals(contraseñaIngresada))
            return false;

        jugadoresConectados.add(nombreJugador);
        jugadoresActuales++;
        return true;
    }

    @Override
    public String toString() {
        String tipo = esPrivada ? "Privada" : "Publica";
        return String.format("%s No.%d %d/%d %s",
                nombreAnfitrion, numeroSala, jugadoresActuales, capacidad, tipo);
    }
}