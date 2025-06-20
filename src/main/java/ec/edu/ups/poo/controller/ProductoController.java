package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.models.Producto;
import ec.edu.ups.poo.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final ProductoDAO productoDAO;
    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoActualizarView productoModificarView;
    private final CarritoAnadirView carritoAnadirView;

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              ProductoEliminarView productoEliminarView,
                              ProductoActualizarView productoModificarView,
                              CarritoAnadirView carritoAnadirView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoEliminarView = productoEliminarView;
        this.productoModificarView = productoModificarView;
        this.carritoAnadirView = carritoAnadirView;

        configurarEventosAnadir();
        configurarEventosLista();
        configurarEventosEliminar();
        configurarEventosModificar();
        configurarEventosCarrito(); // por si luego lo implementas
    }

    public void configurarEventosAnadir() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
    }

    public void configurarEventosLista() {
        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });

        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
    }

    public void configurarEventosEliminar() {
        productoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoEliminar();
            }
        });

        productoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
    }

    public void configurarEventosModificar() {
        productoModificarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
                    Producto producto = productoDAO.buscarPorCodigo(codigo);
                    if (producto != null) {
                        productoModificarView.getTxtNombre().setText(producto.getNombre());
                        productoModificarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                    } else {
                        productoModificarView.mostrarMensaje("Producto no encontrado");
                    }
                } catch (NumberFormatException ex) {
                    productoModificarView.mostrarMensaje("Código inválido");
                }
            }
        });

        productoModificarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
                    String nombre = productoModificarView.getTxtNombre().getText();
                    double precio = Double.parseDouble(productoModificarView.getTxtPrecio().getText());

                    Producto producto = new Producto(codigo, nombre, precio);
                    productoDAO.actualizar(producto);
                    productoModificarView.mostrarMensaje("Producto modificado correctamente");
                } catch (NumberFormatException ex) {
                    productoModificarView.mostrarMensaje("Datos inválidos");
                }
            }
        });
    }

    public void configurarEventosCarrito() {
        // A futuro aquí agregarás la lógica del carrito
        // Ejemplo:
        // carritoAnadirView.getBtnAgregar().addActionListener(...);
    }

    private void guardarProducto() {
        try {
            int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
            String nombre = productoAnadirView.getTxtNombre().getText();
            double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

            productoDAO.crear(new Producto(codigo, nombre, precio));
            productoAnadirView.mostrarMensaje("Producto guardado correctamente");
            productoAnadirView.limpiarCampos();
            productoAnadirView.mostrarProductos(productoDAO.listarTodos());
        } catch (NumberFormatException ex) {
            productoAnadirView.mostrarMensaje("Datos inválidos");
        }
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void buscarProductoEliminar() {
        try {
            int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto != null) {
                productoEliminarView.getTxtNombre().setText(producto.getNombre());
                productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                productoEliminarView.getBtnEliminar().setEnabled(true);
            } else {
                productoEliminarView.mostrarMensaje("Producto no encontrado");
                productoEliminarView.getTxtNombre().setText("");
                productoEliminarView.getTxtPrecio().setText("");
                productoEliminarView.getBtnEliminar().setEnabled(false);
            }
        } catch (NumberFormatException ex) {
            productoEliminarView.mostrarMensaje("Código inválido");
        }
    }

    private void eliminarProducto() {
        try {
            int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto != null) {
                productoEliminarView.getTxtNombre().setText(producto.getNombre());
                productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));

                int confirmacion = JOptionPane.showConfirmDialog(
                        productoEliminarView,
                        "¿Está seguro de que desea eliminar este producto?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    productoDAO.eliminar(codigo);
                    productoEliminarView.mostrarMensaje("Producto eliminado correctamente");
                    productoEliminarView.limpiarCampos();
                } else {
                    productoEliminarView.mostrarMensaje("Eliminación cancelada");
                }
            } else {
                productoEliminarView.mostrarMensaje("Producto no encontrado");
            }
        } catch (NumberFormatException ex) {
            productoEliminarView.mostrarMensaje("Código inválido");
        }
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

}