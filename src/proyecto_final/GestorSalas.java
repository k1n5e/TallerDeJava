package edu.uabc.AEFC.Proyecto;

import java.util.*;
import java.io.*;

public class GestorSalas {
    private TreeSet<Sala> salas;
    private String archivoSalas = "salas_juego.txt";
    private Random random;

    public GestorSalas() {
        salas = new TreeSet<>(Comparator.comparingInt(Sala::getNumeroSala));
        random = new Random();
        cargarSalas();
    }

    public int generarNumeroSala() {
        int numeroSala;
        do {
            numeroSala = 1000 + random.nextInt(9000);
        } while (existeSala(numeroSala));
        return numeroSala;
    }

    private boolean existeSala(int numeroSala) {
        return salas.stream().anyMatch(sala -> sala.getNumeroSala() == numeroSala);
    }

    public Sala crearSala(String nombreAnfitrion, String contraseña, int capacidad) {
        int numeroSala = generarNumeroSala();
        Sala nuevaSala = new Sala(numeroSala, nombreAnfitrion, contraseña, capacidad);
        salas.add(nuevaSala);
        guardarSalas();
        return nuevaSala;
    }

    public Sala buscarSala(int numeroSala) {
        return salas.stream()
                .filter(sala -> sala.getNumeroSala() == numeroSala)
                .findFirst()
                .orElse(null);
    }

    public List<Sala> getSalasPublicas() {
        List<Sala> publicas = new ArrayList<>();
        for (Sala sala : salas) {
            if (!sala.isEsPrivada() && sala.tieneEspacio()) {
                publicas.add(sala);
            }
        }
        return publicas;
    }

    public List<Sala> getTodasLasSalas() {
        return new ArrayList<>(salas);
    }

    public boolean eliminarSala(int numeroSala) {
        Sala sala = buscarSala(numeroSala);
        if (sala != null) {
            salas.remove(sala);
            guardarSalas();
            return true;
        }
        return false;
    }

    private void guardarSalas() {
        try (FileOutputStream fos = new FileOutputStream(archivoSalas);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(salas);
        } catch (IOException e) {
            System.err.println("Error guardando salas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarSalas() {
        File archivo = new File(archivoSalas);
        if (!archivo.exists()) {
            return;
        }

        try (FileInputStream fis = new FileInputStream(archivoSalas);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            salas = (TreeSet<Sala>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error cargando salas: " + e.getMessage());
            salas = new TreeSet<>(Comparator.comparingInt(Sala::getNumeroSala));
        }
    }
}