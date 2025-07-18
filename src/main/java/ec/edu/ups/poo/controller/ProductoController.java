package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.controller.util.Excepciones.*;
import ec.edu.ups.poo.controller.util.FormateadorUtils;
import ec.edu.ups.poo.models.Producto;
import ec.edu.ups.poo.view.CarritoAnadirView;
import ec.edu.ups.poo.view.ProductoActualizarView;
import ec.edu.ups.poo.view.ProductoAnadirView;
import ec.edu.ups.poo.view.ProductoEliminarView;
import ec.edu.ups.poo.view.ProductoListaView;
import java.util.List;
import java.util.Locale;

/**
 * Controlador que gestiona la lógica de negocio para las operaciones de productos.
 * <p>
 * Esta clase actúa como intermediario entre las vistas relacionadas con productos
 * (añadir, listar, actualizar, eliminar) y la capa de acceso a datos (ProductoDAO).
 * </p>
 */
public class ProductoController {
    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final CarritoAnadirView carritoAnadirView;
    private final ProductoActualizarView productoActualizarView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoDAO productoDAO;

    /**
     * Construye un nuevo ProductoController con las dependencias necesarias.
     *
     * @param productoDAO          El objeto DAO para la persistencia de productos.
     * @param productoAnadirView   La vista para añadir nuevos productos.
     * @param productoListaView    La vista para listar y buscar productos.
     * @param carritoAnadirView    La vista del carrito que necesita buscar productos.
     * @param productoActualizarView La vista para actualizar productos existentes.
     * @param productoEliminarView La vista para eliminar productos.
     */
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

    /**
     * Asigna los listeners de eventos a los botones de todas las vistas gestionadas.
     */
    private void configurarEventosEnVistas() {
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());
        productoListaView.getBtnBuscar().addActionListener(e -> buscarProducto());
        productoListaView.getBtnListar().addActionListener(e -> listarProductos());
        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProductoPorCodigo());
        productoActualizarView.getBtnBuscar().addActionListener(e -> buscarProductoParaActualizar());
        productoActualizarView.getBtnActualizar().addActionListener(e -> actualizarProducto());
        productoEliminarView.getBtnBuscar().addActionListener(e -> buscarProductoParaEliminar());
        productoEliminarView.getBtnEliminar().addActionListener(e -> eliminarProducto());
    }

    /**
     * Obtiene los datos de la vista de añadir, crea un nuevo producto y lo persiste.
     * Muestra mensajes de confirmación o error.
     */
    private void guardarProducto() {
        try {
            int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
            String nombre = productoAnadirView.getTxtNombre().getText();
            String precioStr = productoAnadirView.getTxtPrecio().getText().replace(',', '.');
            double precio = Double.parseDouble(precioStr);

            Producto nuevoProducto = new Producto(codigo, nombre, precio);

            productoDAO.crear(nuevoProducto);

            productoAnadirView.mostrarMensaje(productoAnadirView.getMensajeInternacionalizacion().get("mensaje.producto_guardado"));
            productoAnadirView.limpiarCampos();
            listarProductos();

        } catch (NumberFormatException e) {
            productoAnadirView.mostrarMensaje(productoAnadirView.getMensajeInternacionalizacion().get("mensaje.valores_numericos"));
        } catch (ValidacionException e) {
            String mensaje = productoAnadirView.getMensajeInternacionalizacion().get(e.getMessage());
            productoAnadirView.mostrarMensaje(mensaje);
        } catch (DatoExistenteException e) {
            int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
            String template = productoAnadirView.getMensajeInternacionalizacion().get(e.getMessage());
            productoAnadirView.mostrarMensaje(String.format(template, codigo));
        }
    }

    /**
     * Obtiene los datos de la vista de actualizar, modifica un producto y persiste los cambios.
     * Muestra mensajes de confirmación o error.
     */
    private void actualizarProducto() {
        try {
            int codigo = Integer.parseInt(productoActualizarView.getTxtCodigo().getText().trim());
            String nombre = productoActualizarView.getTxtNombre().getText().trim();
            String precioTexto = productoActualizarView.getTxtPrecio().getText().trim().replace(',', '.');
            double precio = Double.parseDouble(precioTexto);

            Producto productoActualizado = new Producto(codigo, nombre, precio);

            productoDAO.actualizar(productoActualizado);

            productoActualizarView.mostrarMensaje(productoActualizarView.getMensajeInternacionalizacion().get("mensaje.producto_actualizado"));
            productoActualizarView.limpiarCampos();
            listarProductos();

        } catch (NumberFormatException e) {
            productoActualizarView.mostrarMensaje(productoActualizarView.getMensajeInternacionalizacion().get("mensaje.precio_invalido"));
        } catch (ValidacionException | DatoNoEncontradoException e) {
            String mensaje = productoActualizarView.getMensajeInternacionalizacion().get(e.getMessage());
            productoActualizarView.mostrarMensaje(mensaje);
        }
    }

    /**
     * Elimina un producto basado en el código proporcionado en la vista de eliminar.
     * Solicita confirmación antes de la eliminación.
     */
    private void eliminarProducto() {
        try {
            int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText().trim());
            if (!productoEliminarView.confirmarEliminacion()) {
                return;
            }
            productoDAO.eliminar(codigo);
            productoEliminarView.mostrarMensaje(productoEliminarView.getMensajeInternacionalizacion().get("mensaje.producto_eliminado"));
            productoEliminarView.limpiarCampos();
            listarProductos();
        } catch (NumberFormatException e) {
            productoEliminarView.mostrarMensaje(productoEliminarView.getMensajeInternacionalizacion().get("mensaje.codigo_entero"));
        } catch (DatoNoEncontradoException e) {
            String mensaje = productoEliminarView.getMensajeInternacionalizacion().get(e.getMessage());
            productoEliminarView.mostrarMensaje(mensaje);
        }
    }

    /**
     * Busca productos por nombre y actualiza la tabla en la vista de lista.
     */
    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    /**
     * Obtiene todos los productos del DAO y los muestra en la vista de lista.
     */
    public void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    /**
     * Busca un producto por su código desde la vista para añadir a un carrito.
     * Si lo encuentra, muestra su nombre y precio en los campos correspondientes.
     */
    private void buscarProductoPorCodigo() {
        try {
            int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto == null) {
                carritoAnadirView.mostrarMensaje(carritoAnadirView.getMensajeInternacionalizacion().get("mensaje.producto_no_encontrado"));
                carritoAnadirView.getTxtNombre().setText("");
                carritoAnadirView.getTxtPrecio().setText("");
            } else {
                Locale locale = carritoAnadirView.getMensajeInternacionalizacion().getLocale();
                carritoAnadirView.getTxtNombre().setText(producto.getNombre());
                carritoAnadirView.getTxtPrecio().setText(FormateadorUtils.formatearMoneda(producto.getPrecio(), locale));
            }
        } catch (NumberFormatException e) {
            carritoAnadirView.mostrarMensaje(carritoAnadirView.getMensajeInternacionalizacion().get("mensaje.codigo_invalido"));
        }
    }

    /**
     * Busca un producto por código para cargarlo en la vista de actualización.
     */
    private void buscarProductoParaActualizar() {
        try {
            int codigo = Integer.parseInt(productoActualizarView.getTxtCodigo().getText().trim());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto == null) {
                productoActualizarView.mostrarMensaje(productoActualizarView.getMensajeInternacionalizacion().get("mensaje.producto_no_existe"));
                productoActualizarView.limpiarCampos();
            } else {
                productoActualizarView.getTxtNombre().setText(producto.getNombre());
                productoActualizarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            }
        } catch (NumberFormatException e) {
            productoActualizarView.mostrarMensaje(productoActualizarView.getMensajeInternacionalizacion().get("mensaje.codigo_entero"));
        }
    }

    /**
     * Busca un producto por código para cargarlo en la vista de eliminación.
     */
    private void buscarProductoParaEliminar() {
        try {
            int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText().trim());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto == null) {
                productoEliminarView.mostrarMensaje(productoEliminarView.getMensajeInternacionalizacion().get("mensaje.producto_no_existe"));
                productoEliminarView.limpiarCampos();
            } else {
                productoEliminarView.getTxtNombre().setText(producto.getNombre());
                productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            }
        } catch (NumberFormatException e) {
            productoEliminarView.mostrarMensaje(productoEliminarView.getMensajeInternacionalizacion().get("mensaje.codigo_entero"));
        }
    }
}