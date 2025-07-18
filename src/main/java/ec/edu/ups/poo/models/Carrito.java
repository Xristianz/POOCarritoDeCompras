package ec.edu.ups.poo.models;

import ec.edu.ups.poo.controller.util.Excepciones.DatoNoEncontradoException;
import ec.edu.ups.poo.controller.util.Excepciones.ValidacionException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Representa un carrito de compras asociado a un usuario.
 * <p>
 * Contiene una lista de {@link ItemCarrito}, gestiona los cálculos de totales
 * y mantiene una referencia al usuario propietario.
 * </p>
 */
public class Carrito implements Serializable {

    private static final long serialVersionUID = 1L;
    private final double IVA = 0.12;
    private static int contador = 0;
    private int codigo;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;
    private Usuario usuario;

    /**
     * Construye un nuevo carrito de compras para un usuario específico.
     * <p>
     * Asigna un código único autoincremental, establece la fecha de creación actual
     * e inicializa una lista de ítems vacía.
     * </p>
     * @param usuario El {@link Usuario} propietario del carrito.
     * @throws ValidacionException si el usuario proporcionado es nulo.
     */
    public Carrito(Usuario usuario) {
        if (usuario == null) {
            throw new ValidacionException("error.usuario.nulo");
        }
        this.usuario = usuario;
        contador++;
        this.codigo = contador;
        this.items = new ArrayList<>();
        this.fechaCreacion = new GregorianCalendar();
    }

    /**
     * Agrega un producto con una cantidad específica al carrito.
     *
     * @param producto El {@link Producto} a agregar.
     * @param cantidad La cantidad del producto.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        items.add(new ItemCarrito(producto, cantidad));
    }

    /**
     * Elimina un producto del carrito basado en su código.
     *
     * @param codigoProducto El código del producto a eliminar.
     * @throws DatoNoEncontradoException si no se encuentra ningún ítem con ese código de producto.
     */
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                return;
            }
        }
        throw new DatoNoEncontradoException("error.item.no.encontrado");
    }

    /**
     * Obtiene el código del carrito.
     * @return el código único del carrito.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del carrito. Usado principalmente por la capa de persistencia.
     * @param codigo el nuevo código para el carrito.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la fecha y hora de creación del carrito.
     * @return un objeto {@link GregorianCalendar} con la fecha de creación.
     */
    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación. Usado principalmente por la capa de persistencia.
     * @param fechaCreacion la nueva fecha de creación.
     */
    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Obtiene el usuario propietario del carrito.
     * @return el objeto {@link Usuario} asociado.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Elimina todos los ítems del carrito.
     */
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Obtiene la lista de todos los ítems en el carrito.
     * @return una {@code List<ItemCarrito>}.
     */
    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    /**
     * Verifica si el carrito no tiene ítems.
     * @return {@code true} si el carrito está vacío, {@code false} en caso contrario.
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Calcula la suma de los subtotales de todos los ítems antes de impuestos.
     * @return el valor del subtotal.
     */
    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    /**
     * Calcula el valor del IVA (12%) sobre el subtotal.
     * @return el valor del IVA.
     */
    public double calcularIVA() {
        return calcularSubtotal() * IVA;
    }

    /**
     * Calcula el costo total de la compra (subtotal + IVA).
     * @return el valor total.
     */
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    /**
     * Comprueba si este carrito pertenece a un usuario específico.
     * @param usuario El usuario a comprobar.
     * @return {@code true} si el username coincide, {@code false} en caso contrario.
     */
    public boolean perteneceA(Usuario usuario) {
        return this.usuario.getUsername().equals(usuario.getUsername());
    }

    @Override
    public String toString() {
        return "Carrito{" +
                "IVA=" + IVA +
                ", codigo=" + codigo +
                ", fechaCreacion=" + fechaCreacion +
                ", items=" + items +
                '}';
    }
}