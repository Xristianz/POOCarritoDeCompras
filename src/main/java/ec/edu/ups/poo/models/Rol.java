package ec.edu.ups.poo.models;

import java.io.Serializable;

/**
 * Define los roles que puede tener un {@link Usuario} en el sistema.
 * <p>
 * Los roles determinan los permisos y el nivel de acceso del usuario
 * dentro de la aplicación.
 * </p>
 */
public enum Rol implements Serializable {
    /**
     * Rol con acceso completo a todas las funcionalidades del sistema,
     * incluyendo la gestión de usuarios y productos.
     */
    ADMINISTRADOR,
    /**
     * Rol con acceso limitado, principalmente para realizar compras y
     * gestionar sus propios datos y carritos.
     */
    USUARIO
}