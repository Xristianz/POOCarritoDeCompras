package ec.edu.ups.poo.Service;

import ec.edu.ups.poo.models.ItemCarrito;
import ec.edu.ups.poo.models.Producto;

import java.util.List;

public interface CarritoService {
    void agregarProducto(Producto producto, int cantidad);
    void eliminarProducto(int codigoProducto);
    void vaciarCarrito();
    double calcularTotal();
    double calcularTotalConDescuento(double porcentajeDescuento);
    List<ItemCarrito> obtenerItems();
    boolean estaVacio();
}
