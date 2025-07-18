package ec.edu.ups.poo.models;

import ec.edu.ups.poo.controller.util.Excepciones.ValidacionException;
import java.io.Serializable;

/**
 * Representa un producto disponible para la venta en la aplicación.
 * <p>
 * Esta clase es serializable para permitir su persistencia en archivos binarios.
 * </p>
 */
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int codigo;
    private String nombre;
    private double precio;

    /**
     * Constructor por defecto.
     */
    public Producto() {
    }

    /**
     * Construye un nuevo producto con sus atributos básicos.
     *
     * @param codigo El código único del producto.
     * @param nombre El nombre del producto.
     * @param precio El precio del producto.
     */
    public Producto(int codigo, String nombre, double precio) {
        this.setCodigo(codigo);
        this.setNombre(nombre);
        this.setPrecio(precio);
    }

    /**
     * Establece el código del producto.
     * @param codigo el nuevo código.
     * @throws ValidacionException si el código es menor o igual a cero.
     */
    public final void setCodigo(int codigo) {
        if (codigo <= 0) {
            throw new ValidacionException("error.producto.codigo.invalido");
        }
        this.codigo = codigo;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre el nuevo nombre.
     * @throws ValidacionException si el nombre es nulo o está vacío.
     */
    public final void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("error.producto.nombre.vacio");
        }
        this.nombre = nombre;
    }

    /**
     * Establece el precio del producto.
     * @param precio el nuevo precio.
     * @throws ValidacionException si el precio es menor o igual a cero.
     */
    public final void setPrecio(double precio) {
        if (precio <= 0) {
            throw new ValidacionException("error.producto.precio.invalido");
        }
        this.precio = precio;
    }

    /**
     * Obtiene el código del producto.
     * @return el código.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre del producto.
     * @return el nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio del producto.
     * @return el precio.
     */
    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " - $" + precio;
    }
}