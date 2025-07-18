package ec.edu.ups.poo.models;

import ec.edu.ups.poo.controller.util.Excepciones.ValidacionException;
import java.io.Serializable;

/**
 * Representa una línea o ítem dentro de un {@link Carrito}.
 * <p>
 * Asocia un {@link Producto} con una cantidad específica.
 * </p>
 */
public class ItemCarrito implements Serializable {
    private static final long serialVersionUID = 1L;
    private Producto producto;
    private int cantidad;

    /**
     * Constructor por defecto.
     */
    public ItemCarrito() {
    }

    /**
     * Construye un nuevo ítem de carrito.
     * @param producto El {@code Producto} de este ítem.
     * @param cantidad La cantidad de este producto.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        setProducto(producto);
        setCantidad(cantidad);
    }

    /**
     * Establece el producto para este ítem.
     * @param producto El {@code Producto} a establecer.
     * @throws ValidacionException si el producto es nulo.
     */
    public final void setProducto(Producto producto) {
        if (producto == null) {
            throw new ValidacionException("error.producto.nulo");
        }
        this.producto = producto;
    }

    /**
     * Establece la cantidad para este ítem.
     * @param cantidad La cantidad a establecer.
     * @throws ValidacionException si la cantidad es menor o igual a cero.
     */
    public final void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new ValidacionException("error.cantidad.invalida");
        }
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el producto de este ítem.
     * @return el {@code Producto} asociado.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Obtiene la cantidad de este ítem.
     * @return la cantidad.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Calcula el subtotal para este ítem (precio del producto * cantidad).
     * @return el valor del subtotal.
     */
    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return producto.toString() + " x " + cantidad + " = $" + getSubtotal();
    }
}