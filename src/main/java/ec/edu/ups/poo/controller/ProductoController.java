package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.Service.CarritoService;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.models.Producto;
import ec.edu.ups.poo.view.ProductoListaView;
import ec.edu.ups.poo.view.ProductoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ProductoController {
    private final ProductoView vista;
    private final ProductoListaView listaVista;
    private final ProductoDAO productoDAO;
    private final CarritoService carritoService;

    public ProductoController(ProductoDAO productoDAO, ProductoView vista,
                              ProductoListaView listaVista, CarritoService carritoService) {
        this.productoDAO = productoDAO;
        this.vista = vista;
        this.listaVista = listaVista;
        this.carritoService = carritoService;
        configurarEventos();
    }

    private void configurarEventos() {
        vista.getBtnAgregar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        vista.getBtnModificar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                modificarProducto();
            }
        });

        vista.getBtnEliminar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        vista.getBtnCalcularTotal().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                calcularTotal();
            }
        });

        listaVista.getBtnBuscar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        listaVista.getBtnListar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });
    }

    private void agregarProducto() {
        int codigo = Integer.parseInt(vista.getTxtCodigo().getText());
        String nombre = vista.getTxtNombre().getText();
        double precioBase = Double.parseDouble(vista.getTxtPrecio().getText());
        double descuento = vista.getTxtDescuento().getText().isEmpty() ?
                0 : Double.parseDouble(vista.getTxtDescuento().getText());

        double precioFinal = precioBase * (1 - (descuento / 100));
        Producto producto = new Producto(codigo, nombre, precioFinal);

        productoDAO.crear(producto);
        carritoService.agregarProducto(producto, 1);

        vista.mostrarMensaje("Producto agregado con descuento aplicado");
        vista.limpiarCampos();
    }

    private void modificarProducto() {
        int codigo = Integer.parseInt(vista.getTxtCodigo().getText());
        String nombre = vista.getTxtNombre().getText();
        double precioBase = Double.parseDouble(vista.getTxtPrecio().getText());
        double descuento = vista.getTxtDescuento().getText().isEmpty() ?
                0 : Double.parseDouble(vista.getTxtDescuento().getText());

        double precioFinal = precioBase * (1 - (descuento / 100));
        Producto producto = new Producto(codigo, nombre, precioFinal);

        productoDAO.actualizar(producto);
        vista.mostrarMensaje("Producto modificado con descuento aplicado");
    }

    private void eliminarProducto() {
        int codigo = Integer.parseInt(vista.getTxtCodigo().getText());
        productoDAO.eliminar(codigo);
        carritoService.eliminarProducto(codigo);
        vista.mostrarMensaje("Producto eliminado");
        vista.limpiarCampos();
    }

    private void calcularTotal() {
        double descuento = vista.getTxtDescuento().getText().isEmpty() ?
                0 : Double.parseDouble(vista.getTxtDescuento().getText());
        double total = carritoService.calcularTotalConDescuento(descuento);
        vista.mostrarMensaje("Total a pagar: $" + total);
    }

    private void buscarProducto() {
        int codigo = Integer.parseInt(listaVista.getTxtBuscar().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto != null) {
            listaVista.cargarDatos(List.of(producto));
        } else {
            listaVista.mostrarMensaje("Producto no encontrado");
        }
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listar();
        listaVista.cargarDatos(productos);
    }
}