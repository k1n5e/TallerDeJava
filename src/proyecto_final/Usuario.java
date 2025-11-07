package proyecto_final;

import java.util.Objects;
import java.io.Serializable;

public class Usuario implements Comparable<Usuario>, Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String apellido;
    private String correo;
    private String username;
    private String password;

    public Usuario(String nombre, String apellido, String correo, String username, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.username = username;
        this.password = password;
    }

    // getters
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int compareTo(Usuario usuario) {
        return this.correo.compareTo(usuario.correo);
    }

    public String toString() {
        return "Nombre: " + nombre + "\nApellido: " + apellido + "\nCorreo: " + correo + "\nUsuario: " + username;
    }
}