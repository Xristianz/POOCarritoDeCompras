package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.Service.CarritoService;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.models.Producto;
import ec.edu.ups.poo.view.ProductoView;

public class ProductoController {
    private ProductoView productoView;
    private ProductoDAO productoDAO;
    private CarritoService carritoService;

    public ProductoController(ProductoDAO productoDAO, ProductoView productoView, CarritoService carritoService) {
        this.productoDAO = productoDAO;
        this.productoView = productoView;
        this.carritoService = carritoService;
        configurarEventos();
    }

    private void configurarEventos() {
        productoView.getBtnAceptar().addActionListener(e -> guardarProducto());
        productoView.getBtnEliminar().addActionListener(e -> eliminarProducto());
        productoView.getBtnCalcularTotal().addActionListener(e -> calcularTotal());
    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoView.getTxtCodigo().getText());
        String nombre = productoView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoView.getTxtPrecio().getText());

        Producto producto = new Producto(codigo, nombre, precio);
        productoDAO.crear(producto);
        carritoService.agregarProducto(producto, 1);

        System.out.println("Producto guardado: " + producto);

        productoView.mostrarMensaje("Producto guardado y agregado al carrito correctamente");
        productoView.limpiarCampos();
    }

    private void eliminarProducto() {

            int codigo = Integer.parseInt(productoView.getTxtCodigo().getText());
            productoDAO.eliminar(codigo);
            productoView.mostrarMensaje("Producto eliminado correctamente");
            productoView.limpiarCampos();

    }

    private void calcularTotal() {

            double descuento = Double.parseDouble(productoView.getTxtDescuento().getText()); // ← corrección aquí
            double total = carritoService.calcularTotalConDescuento(descuento);
            productoView.mostrarMensaje("Total con descuento: $" + total);

            productoView.mostrarMensaje("Error: ingrese un valor válido de descuento.");

    }
}