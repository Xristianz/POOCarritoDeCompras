package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.models.Producto;
import ec.edu.ups.poo.view.*;
import ec.edu.ups.poo.controller.util.FormateadorUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;

public class ProductoController {
    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final CarritoAnadirView carritoAnadirView;
    private final ProductoActualizarView productoActualizarView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              CarritoAnadirView carritoAnadirView,
                              ProductoActualizarView productoActualizarView,
                              ProductoEliminarView productoEliminarView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.carritoAnadirView = carritoAnadirView;
        this.productoActualizarView = productoActualizarView;
        this.productoEliminarView = productoEliminarView;
        this.configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });

        carritoAnadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorCodigo();
            }
        });

        productoActualizarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoParaActualizar();
            }
        });

        productoActualizarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        productoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoParaEliminar();
            }
        });

        productoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
    }

    private void guardarProducto() {
        try {
            if (productoAnadirView.getTxtCodigo().getText().isEmpty() ||
                    productoAnadirView.getTxtNombre().getText().isEmpty() ||
                    productoAnadirView.getTxtPrecio().getText().isEmpty()) {
                productoAnadirView.mostrarMensaje(
                        productoAnadirView.getMensajeInternacionalizacion().get("mensaje.campos_requeridos")
                );
                return;
            }

            int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
            String nombre = productoAnadirView.getTxtNombre().getText();
            double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

            if (productoDAO.buscarPorCodigo(codigo) != null) {
                productoAnadirView.mostrarMensaje(
                        productoAnadirView.getMensajeInternacionalizacion().get("mensaje.producto_existente")
                );
                return;
            }

            if (precio <= 0) {
                productoAnadirView.mostrarMensaje(
                        productoAnadirView.getMensajeInternacionalizacion().get("mensaje.precio_invalido")
                );
                return;
            }

            productoDAO.crear(new Producto(codigo, nombre, precio));
            productoAnadirView.mostrarMensaje(
                    productoAnadirView.getMensajeInternacionalizacion().get("mensaje.producto_guardado")
            );
            productoAnadirView.limpiarCampos();
            listarProductos();
        } catch (NumberFormatException e) {
            productoAnadirView.mostrarMensaje(
                    productoAnadirView.getMensajeInternacionalizacion().get("mensaje.valores_numericos")
            );
        }
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    public void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    private void buscarProductoPorCodigo() {
        try {
            int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto == null) {
                carritoAnadirView.mostrarMensaje(
                        carritoAnadirView.getMensajeInternacionalizacion().get("mensaje.producto_no_encontrado")
                );
                carritoAnadirView.getTxtNombre().setText("");
                carritoAnadirView.getTxtPrecio().setText("");
            } else {
                Locale locale = carritoAnadirView.getMensajeInternacionalizacion().getLocale();
                carritoAnadirView.getTxtNombre().setText(producto.getNombre());
                carritoAnadirView.getTxtPrecio().setText(
                        FormateadorUtils.formatearMoneda(producto.getPrecio(), locale)
                );
            }
        } catch (NumberFormatException e) {
            carritoAnadirView.mostrarMensaje(
                    carritoAnadirView.getMensajeInternacionalizacion().get("mensaje.codigo_invalido")
            );
        }
    }

    private boolean esNumero(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        for (char c : texto.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean esNumeroDecimal(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        boolean puntoEncontrado = false;
        for (char c : texto.toCharArray()) {
            if (c == '.') {
                if (puntoEncontrado) {
                    return false;
                }
                puntoEncontrado = true;
            } else if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private void buscarProductoParaActualizar() {
        String codigoTexto = productoActualizarView.getTxtCodigo().getText().trim();

        if (codigoTexto.isEmpty()) {
            productoActualizarView.mostrarMensaje(
                    productoActualizarView.getMensajeInternacionalizacion().get("mensaje.codigo_requerido")
            );
            return;
        }

        if (!esNumero(codigoTexto)) {
            productoActualizarView.mostrarMensaje(
                    productoActualizarView.getMensajeInternacionalizacion().get("mensaje.codigo_entero")
            );
            return;
        }

        int codigo = Integer.parseInt(codigoTexto);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto == null) {
            productoActualizarView.mostrarMensaje(
                    productoActualizarView.getMensajeInternacionalizacion().get("mensaje.producto_no_existe")
            );
            productoActualizarView.getTxtNombre().setText("");
            productoActualizarView.getTxtPrecio().setText("");
        } else {
            productoActualizarView.getTxtNombre().setText(producto.getNombre());
            productoActualizarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
        }
    }

    private void actualizarProducto() {
        String codigoTexto = productoActualizarView.getTxtCodigo().getText().trim();
        String nombre = productoActualizarView.getTxtNombre().getText().trim();
        String precioTexto = productoActualizarView.getTxtPrecio().getText().trim();

        if (codigoTexto.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty()) {
            productoActualizarView.mostrarMensaje(
                    productoActualizarView.getMensajeInternacionalizacion().get("mensaje.campos_requeridos")
            );
            return;
        }

        if (!esNumero(codigoTexto)) {
            productoActualizarView.mostrarMensaje(
                    productoActualizarView.getMensajeInternacionalizacion().get("mensaje.codigo_entero")
            );
            return;
        }

        if (!esNumeroDecimal(precioTexto)) {
            productoActualizarView.mostrarMensaje(
                    productoActualizarView.getMensajeInternacionalizacion().get("mensaje.precio_invalido")
            );
            return;
        }

        int codigo = Integer.parseInt(codigoTexto);
        double precio = Double.parseDouble(precioTexto);

        if (precio <= 0) {
            productoActualizarView.mostrarMensaje(
                    productoActualizarView.getMensajeInternacionalizacion().get("mensaje.precio_mayor_cero")
            );
            return;
        }

        if (productoDAO.buscarPorCodigo(codigo) == null) {
            productoActualizarView.mostrarMensaje(
                    productoActualizarView.getMensajeInternacionalizacion().get("mensaje.producto_no_existe")
            );
            return;
        }

        Producto productoActualizado = new Producto(codigo, nombre, precio);
        productoDAO.actualizar(productoActualizado);

        productoActualizarView.mostrarMensaje(
                productoActualizarView.getMensajeInternacionalizacion().get("mensaje.producto_actualizado")
        );
        productoActualizarView.limpiarCampos();

        if (productoListaView.isVisible()) {
            listarProductos();
        }
    }

    private void buscarProductoParaEliminar() {
        String codigoTexto = productoEliminarView.getTxtCodigo().getText().trim();

        if (codigoTexto.isEmpty()) {
            productoEliminarView.mostrarMensaje(
                    productoEliminarView.getMensajeInternacionalizacion().get("mensaje.codigo_requerido")
            );
            return;
        }

        if (!esNumero(codigoTexto)) {
            productoEliminarView.mostrarMensaje(
                    productoEliminarView.getMensajeInternacionalizacion().get("mensaje.codigo_entero")
            );
            return;
        }

        int codigo = Integer.parseInt(codigoTexto);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto == null) {
            productoEliminarView.mostrarMensaje(
                    productoEliminarView.getMensajeInternacionalizacion().get("mensaje.producto_no_existe")
            );
            productoEliminarView.getTxtNombre().setText("");
            productoEliminarView.getTxtPrecio().setText("");
        } else {
            productoEliminarView.getTxtNombre().setText(producto.getNombre());
            productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
        }
    }

    private void eliminarProducto() {
        String codigoTexto = productoEliminarView.getTxtCodigo().getText().trim();

        if (codigoTexto.isEmpty()) {
            productoEliminarView.mostrarMensaje(
                    productoEliminarView.getMensajeInternacionalizacion().get("mensaje.codigo_requerido")
            );
            return;
        }

        if (!esNumero(codigoTexto)) {
            productoEliminarView.mostrarMensaje(
                    productoEliminarView.getMensajeInternacionalizacion().get("mensaje.codigo_entero")
            );
            return;
        }

        int codigo = Integer.parseInt(codigoTexto);

        if (productoDAO.buscarPorCodigo(codigo) == null) {
            productoEliminarView.mostrarMensaje(
                    productoEliminarView.getMensajeInternacionalizacion().get("mensaje.producto_no_existe")
            );
            return;
        }

        if (!productoEliminarView.confirmarEliminacion()) {
            return;
        }

        productoDAO.eliminar(codigo);
        productoEliminarView.mostrarMensaje(
                productoEliminarView.getMensajeInternacionalizacion().get("mensaje.producto_eliminado")
        );
        productoEliminarView.limpiarCampos();

        if (productoListaView.isVisible()) {
            listarProductos();
        }
    }
}