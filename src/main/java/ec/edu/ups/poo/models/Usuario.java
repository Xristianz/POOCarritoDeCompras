package ec.edu.ups.poo.models;

import ec.edu.ups.poo.controller.util.Excepciones.ValidacionException;
import java.io.Serializable;

/**
 * Representa a un usuario del sistema.
 * <p>
 * Contiene información personal, credenciales de acceso y el rol que determina
 * sus permisos. La clase es serializable para permitir su persistencia.
 * </p>
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String contrasenia;
    private Rol rol;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String fechaNacimiento;

    /**
     * Constructor por defecto.
     */
    public Usuario() {
    }

    /**
     * Construye un nuevo usuario con todos sus datos.
     * <p>
     * Lanza una {@link ValidacionException} si los campos requeridos (username,
     * nombre, apellido, contraseña) están vacíos o son nulos.
     * </p>
     * @param username        Nombre de usuario (debe ser único, ej. cédula).
     * @param contrasenia     Contraseña de acceso.
     * @param rol             Rol del usuario (ADMINISTRADOR o USUARIO).
     * @param nombre          Nombre real del usuario.
     * @param apellido        Apellido real del usuario.
     * @param correo          Correo electrónico.
     * @param telefono        Número de teléfono.
     * @param fechaNacimiento Fecha de nacimiento en formato de texto.
     */
    public Usuario(String username, String contrasenia, Rol rol, String nombre, String apellido,
                   String correo, String telefono, String fechaNacimiento) {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidacionException("error.usuario.username.vacio");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("error.usuario.nombre.vacio");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new ValidacionException("error.usuario.apellido.vacio");
        }
        if (contrasenia == null || contrasenia.trim().isEmpty()){
            throw new ValidacionException("error.usuario.contrasenia.vacia");
        }

        this.username = username;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene el nombre de usuario.
     * @return el username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     * @param username el nuevo username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene la contraseña.
     * @return la contraseña.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Establece la contraseña.
     * @param contrasenia la nueva contraseña.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Obtiene el rol del usuario.
     * @return el {@link Rol}.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * @param rol el nuevo {@link Rol}.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return el nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre el nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del usuario.
     * @return el apellido.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del usuario.
     * @param apellido el nuevo apellido.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el correo del usuario.
     * @return el correo.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo del usuario.
     * @param correo el nuevo correo.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el teléfono del usuario.
     * @return el teléfono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del usuario.
     * @param telefono el nuevo teléfono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     * @return la fecha de nacimiento.
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del usuario.
     * @param fechaNacimiento la nueva fecha de nacimiento.
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", contrasenia='" + "********" + '\'' +
                ", rol=" + rol +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                '}';
    }
}