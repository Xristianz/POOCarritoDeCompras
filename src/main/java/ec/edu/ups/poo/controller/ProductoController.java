package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.models.Producto;
import ec.edu.ups.poo.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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

        // Configurar eventos para eliminar producto
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
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();

        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    private void buscarProductoPorCodigo() {
        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            carritoAnadirView.mostrarMensaje("No se encontro el producto");
            carritoAnadirView.getTxtNombre().setText("");
            carritoAnadirView.getTxtPrecio().setText("");
        } else {
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
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
                    return false; // Más de un punto
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
            productoActualizarView.mostrarMensaje("Debe ingresar un código");
            return;
        }

        if (!esNumero(codigoTexto)) {
            productoActualizarView.mostrarMensaje("El código debe ser un número entero");
            return;
        }

        int codigo = Integer.parseInt(codigoTexto);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto == null) {
            productoActualizarView.mostrarMensaje("No existe un producto con ese código");
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


        // Validación de campos vacíos
        if (codigoTexto.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty()) {
            productoActualizarView.mostrarMensaje("Todos los campos son obligatorios");
            return;
        }

        // Validación de código numérico
        if (!esNumero(codigoTexto)) {
            productoActualizarView.mostrarMensaje("El código debe ser un número entero");
            return;
        }

        // Validación de precio numérico
        if (!esNumeroDecimal(precioTexto)) {
            productoActualizarView.mostrarMensaje("El precio debe ser un número válido (ej: 10.50)");
            return;
        }

        int codigo = Integer.parseInt(codigoTexto);
        double precio = Double.parseDouble(precioTexto);

        // Validación de precio positivo
        if (precio <= 0) {
            productoActualizarView.mostrarMensaje("El precio debe ser mayor a cero");
            return;
        }

        // Verificar si el producto existe
        if (productoDAO.buscarPorCodigo(codigo) == null) {
            productoActualizarView.mostrarMensaje("No existe un producto con ese código");
            return;
        }

        // Crear y actualizar el producto
        Producto productoActualizado = new Producto(codigo, nombre, precio);
        productoDAO.actualizar(productoActualizado);

        productoActualizarView.mostrarMensaje("Producto actualizado correctamente");
        productoActualizarView.limpiarCampos();

        // Actualizar lista de productos si es necesario
        if (productoListaView.isVisible()) {
            productoListaView.cargarDatos(productoDAO.listarTodos());
        }
    }
    private void buscarProductoParaEliminar() {
        String codigoTexto = productoEliminarView.getTxtCodigo().getText().trim();

        if (codigoTexto.isEmpty()) {
            productoEliminarView.mostrarMensaje("Debe ingresar un código");
            return;
        }

        if (!esNumero(codigoTexto)) {
            productoEliminarView.mostrarMensaje("El código debe ser un número entero");
            return;
        }

        int codigo = Integer.parseInt(codigoTexto);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto == null) {
            productoEliminarView.mostrarMensaje("No existe un producto con ese código");
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
            productoEliminarView.mostrarMensaje("Debe ingresar un código");
            return;
        }

        if (!esNumero(codigoTexto)) {
            productoEliminarView.mostrarMensaje("El código debe ser un número entero");
            return;
        }

        int codigo = Integer.parseInt(codigoTexto);

        // Verificar si el producto existe
        if (productoDAO.buscarPorCodigo(codigo) == null) {
            productoEliminarView.mostrarMensaje("No existe un producto con ese código");
            return;
        }

        // Confirmar eliminación
        if (!productoEliminarView.confirmarEliminacion()) {
            return;
        }

        // Eliminar el producto
        productoDAO.eliminar(codigo);
        productoEliminarView.mostrarMensaje("Producto eliminado correctamente");
        productoEliminarView.limpiarCampos();

        // Actualizar lista de productos si es necesario
        if (productoListaView.isVisible()) {
            productoListaView.cargarDatos(productoDAO.listarTodos());
        }
    }


}