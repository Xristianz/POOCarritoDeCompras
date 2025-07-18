package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.DAO.CarritoDAO;
import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.controller.util.Excepciones.*;
import ec.edu.ups.poo.controller.util.FormateadorUtils;
import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.*;
import ec.edu.ups.poo.view.CarritoActualizarView;
import ec.edu.ups.poo.view.CarritoAnadirView;
import ec.edu.ups.poo.view.CarritoEliminarView;
import ec.edu.ups.poo.view.CarritoListaView;
import ec.edu.ups.poo.view.DetalleCarritoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;

/**
 * Controlador que gestiona todas las interacciones relacionadas con los carritos de compra.
 * <p>
 * Se encarga de la lógica de negocio para crear, actualizar, eliminar, y listar carritos,
 * mediando entre las vistas ({@code Carrito...View}) y los DAOs ({@code CarritoDAO}, {@code ProductoDAO}).
 * </p>
 */
public class CarritoController {
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private final CarritoListaView listaView;
    private final CarritoActualizarView actualizarView;
    private final CarritoEliminarView eliminarView;
    private final DetalleCarritoView detalleView;
    /**
     * Carrito en proceso de creación en la vista de añadir.
     */
    private Carrito carritoParaAnadir;
    /**
     * Carrito cargado para ser modificado en la vista de actualizar.
     */
    private Carrito carritoParaActualizar;
    /**
     * El usuario autenticado que está realizando las operaciones.
     */
    private final Usuario usuario;

    /**
     * Construye el controlador del carrito e inicializa sus dependencias.
     *
     * @param carritoDAO      DAO para la persistencia de datos de carritos.
     * @param productoDAO     DAO para la persistencia de datos de productos.
     * @param carritoAnadirView Vista para crear nuevos carritos.
     * @param listaView       Vista para listar y buscar carritos existentes.
     * @param actualizarView  Vista para modificar un carrito existente.
     * @param eliminarView    Vista para eliminar un carrito.
     * @param detalleView     Vista para mostrar los detalles de un carrito.
     * @param usuario         El usuario actualmente autenticado en la sesión.
     */
    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView,
                             CarritoListaView listaView,
                             CarritoActualizarView actualizarView,
                             CarritoEliminarView eliminarView,
                             DetalleCarritoView detalleView,
                             Usuario usuario) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.listaView = listaView;
        this.actualizarView = actualizarView;
        this.eliminarView = eliminarView;
        this.detalleView = detalleView;
        this.usuario = usuario;
        this.carritoParaAnadir = new Carrito(usuario);
        this.configurarEventosEnVistas();
    }

    /**
     * Configura y asigna los ActionListeners a todos los componentes interactivos
     * de las vistas gestionadas por este controlador.
     */
    private void configurarEventosEnVistas() {
        carritoAnadirView.getBtnAnadir().addActionListener(e -> anadirProducto());
        carritoAnadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());
        carritoAnadirView.getBtnEliminarItem().addActionListener(e -> eliminarItem());
        carritoAnadirView.getBtnActualizarCantidad().addActionListener(e -> actualizarCantidad());
        listaView.getBtnBuscar().addActionListener(e -> buscarCarrito());
        listaView.getBtnListar().addActionListener(e -> listarCarritos());
        listaView.getBtnVerDetalles().addActionListener(e -> verDetallesCarrito());
        actualizarView.getBtnBuscarCarrito().addActionListener(e -> buscarCarritoActualizar());
        actualizarView.getBtnBuscarProducto().addActionListener(e -> buscarProductoActualizar());
        actualizarView.getBtnAgregarProducto().addActionListener(e -> agregarProductoActualizar());
        actualizarView.getBtnEliminarProducto().addActionListener(e -> eliminarProductoActualizar());
        actualizarView.getBtnActualizarCantidad().addActionListener(e -> actualizarCantidadActualizar());
        actualizarView.getBtnGuardar().addActionListener(e -> guardarCarritoActualizar());
        eliminarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());
        eliminarView.getBtnActualizarLista().addActionListener(e -> actualizarListaCarritos());
    }

    /**
     * Guarda el carrito actualmente en construcción en la persistencia de datos.
     * Solicita confirmación al usuario antes de proceder.
     */
    private void guardarCarrito() {
        try {
            MensajeInternacionalizacionHandler handler = carritoAnadirView.getMensajeInternacionalizacion();
            int opcion = JOptionPane.showConfirmDialog(carritoAnadirView,
                    handler.get("mensaje.confirmar_guardar"),
                    handler.get("titulo.confirmar"),
                    JOptionPane.YES_NO_OPTION);

            if (opcion != JOptionPane.YES_OPTION) return;

            carritoDAO.crear(carritoParaAnadir);

            carritoAnadirView.mostrarMensaje(handler.get("mensaje.carrito_guardado"));
            this.carritoParaAnadir = new Carrito(usuario);
            cargarProductos();
            mostrarTotales();

        } catch (ValidacionException e) {
            String mensaje = carritoAnadirView.getMensajeInternacionalizacion().get(e.getMessage());
            carritoAnadirView.mostrarMensaje(mensaje);
        }
    }

    /**
     * Añade un producto al carrito en construcción ({@code carritoParaAnadir}).
     */
    private void anadirProducto() {
        try {
            int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
            int cantidad = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());

            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto == null) {
                throw new DatoNoEncontradoException("mensaje.producto_no_encontrado");
            }

            carritoParaAnadir.agregarProducto(producto, cantidad);

            cargarProductos();
            mostrarTotales();

        } catch (NumberFormatException e) {
            String msg = carritoAnadirView.getMensajeInternacionalizacion().get("mensaje.codigo_invalido");
            carritoAnadirView.mostrarMensaje(msg);
        } catch (DatoNoEncontradoException | ValidacionException e) {
            String msg = carritoAnadirView.getMensajeInternacionalizacion().get(e.getMessage());
            carritoAnadirView.mostrarMensaje(msg);
        }
    }

    /**
     * Elimina el ítem seleccionado de la tabla en la vista de añadir carrito.
     */
    private void eliminarItem() {
        try {
            int filaSeleccionada = carritoAnadirView.getTblProductos().getSelectedRow();
            if (filaSeleccionada == -1) {
                throw new ValidacionException("mensaje.seleccionar_item");
            }
            int codigo = (int) carritoAnadirView.getTblProductos().getValueAt(filaSeleccionada, 0);
            carritoParaAnadir.eliminarProducto(codigo);

            cargarProductos();
            mostrarTotales();
        } catch (ValidacionException | DatoNoEncontradoException e) {
            String msg = carritoAnadirView.getMensajeInternacionalizacion().get(e.getMessage());
            carritoAnadirView.mostrarMensaje(msg);
        }
    }

    /**
     * Actualiza la cantidad del ítem seleccionado en la tabla de la vista de añadir carrito.
     */
    private void actualizarCantidad() {
        int filaSeleccionada = carritoAnadirView.getTblProductos().getSelectedRow();
        if (filaSeleccionada == -1) {
            carritoAnadirView.mostrarMensaje(carritoAnadirView.getMensajeInternacionalizacion().get("mensaje.seleccionar_item"));
            return;
        }
        int codigo = (int) carritoAnadirView.getTblProductos().getValueAt(filaSeleccionada, 0);
        int nuevaCantidad = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());

        for (ItemCarrito item : carritoParaAnadir.obtenerItems()) {
            if (item.getProducto().getCodigo() == codigo) {
                try {
                    item.setCantidad(nuevaCantidad);
                    break;
                } catch (ValidacionException e) {
                    String msg = carritoAnadirView.getMensajeInternacionalizacion().get(e.getMessage());
                    carritoAnadirView.mostrarMensaje(msg);
                }
            }
        }
        cargarProductos();
        mostrarTotales();
    }

    /**
     * Busca un carrito por su código y lo muestra en la tabla de la vista de lista.
     * Valida si el usuario tiene permisos para ver el carrito.
     */
    private void buscarCarrito() {
        try {
            int codigo = Integer.parseInt(listaView.getTxtBuscar().getText());
            Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

            if (carritoEncontrado == null) {
                listaView.mostrarMensaje(listaView.getMensajeInternacionalizacion().get("mensaje.carrito_no_encontrado"));
                return;
            }

            if (!carritoEncontrado.perteneceA(usuario) && usuario.getRol() != Rol.ADMINISTRADOR) {
                listaView.mostrarMensaje(listaView.getMensajeInternacionalizacion().get("mensaje.permiso_denegado_ver"));
                return;
            }

            listaView.getModelo().setRowCount(0);
            Locale locale = listaView.getMensajeInternacionalizacion().getLocale();
            listaView.getModelo().addRow(new Object[]{
                    carritoEncontrado.getCodigo(),
                    carritoEncontrado.getUsuario().getUsername(),
                    FormateadorUtils.formatearFecha(carritoEncontrado.getFechaCreacion().getTime(), locale),
                    FormateadorUtils.formatearMoneda(carritoEncontrado.calcularTotal(), locale)
            });
        } catch (NumberFormatException e) {
            listaView.mostrarMensaje(listaView.getMensajeInternacionalizacion().get("mensaje.codigo_invalido"));
        }
    }

    /**
     * Obtiene y muestra la lista de carritos en la vista correspondiente.
     * Si el usuario es Administrador, lista todos los carritos.
     * Si es Usuario, lista solo sus propios carritos.
     */
    public void listarCarritos() {
        List<Carrito> carritos;
        if (usuario.getRol() == Rol.ADMINISTRADOR) {
            carritos = carritoDAO.listarTodos();
        } else {
            carritos = carritoDAO.listarPorUsuario(usuario.getUsername());
        }
        listaView.cargarDatos(carritos);
    }

    /**
     * Muestra la ventana de detalles para el carrito seleccionado en la tabla.
     * Valida permisos antes de mostrar los detalles.
     */
    private void verDetallesCarrito() {
        int filaSeleccionada = listaView.getTblCarritos().getSelectedRow();
        if (filaSeleccionada == -1) {
            listaView.mostrarMensaje(listaView.getMensajeInternacionalizacion().get("mensaje.seleccionar_carrito"));
            return;
        }

        int codigo = (int) listaView.getModelo().getValueAt(filaSeleccionada, 0);
        Carrito carritoSeleccionado = carritoDAO.buscarPorCodigo(codigo);

        if (carritoSeleccionado != null && !carritoSeleccionado.perteneceA(usuario) && usuario.getRol() != Rol.ADMINISTRADOR) {
            listaView.mostrarMensaje(listaView.getMensajeInternacionalizacion().get("mensaje.permiso_denegado_ver"));
            return;
        }

        JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, listaView);
        if (desktopPane != null && !detalleView.isVisible()) {
            desktopPane.add(detalleView);
        }
        detalleView.cargarDetalles(carritoSeleccionado);
        detalleView.setVisible(true);
    }

    /**
     * Busca un carrito por código para cargarlo en la vista de actualización.
     * Valida permisos y habilita los campos de edición si se encuentra.
     */
    private void buscarCarritoActualizar() {
        try {
            int codigo = Integer.parseInt(actualizarView.getTxtCodigoCarrito().getText());
            Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

            if (carritoEncontrado == null) {
                throw new DatoNoEncontradoException("error.carrito.no.encontrado");
            }
            if (!carritoEncontrado.perteneceA(usuario) && usuario.getRol() != Rol.ADMINISTRADOR) {
                throw new ValidacionException("mensaje.permiso.denegado");
            }

            this.carritoParaActualizar = carritoEncontrado;
            actualizarView.setCamposHabilitados(true);
            cargarProductosActualizar();
            mostrarTotalesActualizar();

        } catch (NumberFormatException e) {
            actualizarView.mostrarMensaje(actualizarView.getMensajeInternacionalizacion().get("mensaje.codigo_invalido"));
        } catch (DatoNoEncontradoException | ValidacionException e) {
            actualizarView.mostrarMensaje(actualizarView.getMensajeInternacionalizacion().get(e.getMessage()));
        }
    }

    /**
     * Busca un producto por código en la vista de actualización y muestra sus datos.
     */
    private void buscarProductoActualizar() {
        try {
            int codigo = Integer.parseInt(actualizarView.getTxtCodigoProducto().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                actualizarView.mostrarMensaje(actualizarView.getMensajeInternacionalizacion().get("mensaje.producto_no_encontrado"));
                return;
            }

            actualizarView.mostrarDatosProducto(producto.getNombre(), producto.getPrecio());
        } catch (NumberFormatException e) {
            actualizarView.mostrarMensaje(actualizarView.getMensajeInternacionalizacion().get("mensaje.codigo_invalido"));
        }
    }

    /**
     * Agrega un nuevo producto al carrito que se está actualizando.
     */
    private void agregarProductoActualizar() {
        try {
            if(carritoParaActualizar == null) throw new ValidacionException("error.carrito.no.cargado");

            int codigo = Integer.parseInt(actualizarView.getTxtCodigoProducto().getText());
            int cantidad = (int) actualizarView.getCbxCantidad().getSelectedItem();

            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if(producto == null) throw new DatoNoEncontradoException("mensaje.producto_no_encontrado");

            carritoParaActualizar.agregarProducto(producto, cantidad);

            cargarProductosActualizar();
            mostrarTotalesActualizar();
            actualizarView.limpiarCamposProducto();
        } catch (NumberFormatException e) {
            actualizarView.mostrarMensaje(actualizarView.getMensajeInternacionalizacion().get("mensaje.codigo_invalido"));
        } catch (DatoNoEncontradoException | ValidacionException e) {
            actualizarView.mostrarMensaje(actualizarView.getMensajeInternacionalizacion().get(e.getMessage()));
        }
    }

    /**
     * Elimina un producto del carrito que se está actualizando.
     */
    private void eliminarProductoActualizar() {
        try {
            if(carritoParaActualizar == null) throw new ValidacionException("error.carrito.no.cargado");

            int filaSeleccionada = actualizarView.getTblProductos().getSelectedRow();
            if (filaSeleccionada == -1) {
                throw new ValidacionException("mensaje.seleccionar_item");
            }
            int codigo = (int) actualizarView.getModeloTabla().getValueAt(filaSeleccionada, 0);

            carritoParaActualizar.eliminarProducto(codigo);

            cargarProductosActualizar();
            mostrarTotalesActualizar();
        } catch (ValidacionException | DatoNoEncontradoException e) {
            actualizarView.mostrarMensaje(actualizarView.getMensajeInternacionalizacion().get(e.getMessage()));
        }
    }

    /**
     * Actualiza la cantidad de un producto existente en el carrito que se está modificando.
     */
    private void actualizarCantidadActualizar() {
        if(carritoParaActualizar == null) {
            actualizarView.mostrarMensaje(actualizarView.getMensajeInternacionalizacion().get("error.carrito.no.cargado"));
            return;
        }

        int filaSeleccionada = actualizarView.getTblProductos().getSelectedRow();
        if (filaSeleccionada == -1) {
            actualizarView.mostrarMensaje(actualizarView.getMensajeInternacionalizacion().get("mensaje.seleccionar_item"));
            return;
        }
        int codigo = (int) actualizarView.getModeloTabla().getValueAt(filaSeleccionada, 0);
        int nuevaCantidad = (int) actualizarView.getCbxCantidad().getSelectedItem();

        for (ItemCarrito item : carritoParaActualizar.obtenerItems()) {
            if (item.getProducto().getCodigo() == codigo) {
                try {
                    item.setCantidad(nuevaCantidad);
                    break;
                } catch (ValidacionException e) {
                    String msg = actualizarView.getMensajeInternacionalizacion().get(e.getMessage());
                    actualizarView.mostrarMensaje(msg);
                }
            }
        }
        cargarProductosActualizar();
        mostrarTotalesActualizar();
    }

    /**
     * Guarda en la persistencia los cambios realizados al carrito en la vista de actualización.
     */
    private void guardarCarritoActualizar() {
        try {
            if (carritoParaActualizar == null) {
                throw new ValidacionException("error.carrito.no.cargado");
            }

            MensajeInternacionalizacionHandler handler = actualizarView.getMensajeInternacionalizacion();
            int opcion = JOptionPane.showConfirmDialog(actualizarView,
                    handler.get("confirmar.guardar_cambios_mensaje"),
                    handler.get("titulo.confirmar"),
                    JOptionPane.YES_NO_OPTION);

            if(opcion != JOptionPane.YES_OPTION) return;

            carritoDAO.actualizar(carritoParaActualizar);

            actualizarView.mostrarMensaje(handler.get("mensaje.carrito_actualizado"));
            actualizarView.limpiarTodo();

        } catch (ValidacionException | DatoNoEncontradoException e) {
            String msg = actualizarView.getMensajeInternacionalizacion().get(e.getMessage());
            actualizarView.mostrarMensaje(msg);
        }
    }

    /**
     * Elimina un carrito de la persistencia, previa confirmación del usuario.
     * Valida permisos antes de proceder.
     */
    private void eliminarCarrito() {
        try {
            MensajeInternacionalizacionHandler handler = eliminarView.getMensajeInternacionalizacion();
            String codigoStr = eliminarView.getTxtCodigoCarrito().getText();
            if (codigoStr.isEmpty()) {
                throw new ValidacionException("mensaje.ingresar_codigo_carrito");
            }
            int codigo = Integer.parseInt(codigoStr);

            Carrito carrito = carritoDAO.buscarPorCodigo(codigo);
            if (carrito == null) {
                throw new DatoNoEncontradoException("error.carrito.no.encontrado");
            }
            if (!carrito.perteneceA(this.usuario) && this.usuario.getRol() != Rol.ADMINISTRADOR) {
                throw new ValidacionException("mensaje.permiso.denegado");
            }

            String template = handler.get("confirmar.eliminar_carrito_mensaje");
            String mensajeConfirmacion = String.format(template, codigo);
            int confirmacion = eliminarView.mostrarConfirmacion(mensajeConfirmacion);

            if (confirmacion == JOptionPane.YES_OPTION) {
                carritoDAO.eliminar(codigo);
                eliminarView.mostrarMensaje(handler.get("mensaje.carrito_eliminado"));
                eliminarView.limpiarCampos();
                actualizarListaCarritos();
            }
        } catch (NumberFormatException e) {
            eliminarView.mostrarMensaje(eliminarView.getMensajeInternacionalizacion().get("mensaje.codigo_invalido"));
        } catch (ValidacionException | DatoNoEncontradoException e) {
            eliminarView.mostrarMensaje(eliminarView.getMensajeInternacionalizacion().get(e.getMessage()));
        }
    }

    /**
     * Actualiza la tabla de productos en la vista de añadir carrito.
     */
    private void cargarProductos() {
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setRowCount(0);
        Locale locale = carritoAnadirView.getMensajeInternacionalizacion().getLocale();
        if (carritoParaAnadir == null) return;
        for (ItemCarrito item : carritoParaAnadir.obtenerItems()) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(item.getSubtotal(), locale)
            });
        }
    }

    /**
     * Actualiza los campos de subtotal, IVA y total en la vista de añadir carrito.
     */
    public void mostrarTotales() {
        Locale locale = carritoAnadirView.getMensajeInternacionalizacion().getLocale();
        carritoAnadirView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carritoParaAnadir.calcularSubtotal(), locale));
        carritoAnadirView.getTxtIva().setText(FormateadorUtils.formatearMoneda(carritoParaAnadir.calcularIVA(), locale));
        carritoAnadirView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carritoParaAnadir.calcularTotal(), locale));
    }

    /**
     * Actualiza la tabla de productos en la vista de actualizar carrito.
     */
    private void cargarProductosActualizar() {
        DefaultTableModel modelo = actualizarView.getModeloTabla();
        modelo.setRowCount(0);
        Locale locale = actualizarView.getMensajeInternacionalizacion().getLocale();
        if (carritoParaActualizar == null) return;
        for (ItemCarrito item : carritoParaActualizar.obtenerItems()) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(item.getSubtotal(), locale)
            });
        }
    }

    /**
     * Actualiza los campos de subtotal, IVA y total en la vista de actualizar carrito.
     */
    private void mostrarTotalesActualizar() {
        actualizarView.actualizarTotales(
                carritoParaActualizar.calcularSubtotal(),
                carritoParaActualizar.calcularIVA(),
                carritoParaActualizar.calcularTotal()
        );
    }

    /**
     * Recarga la lista de carritos en la vista de eliminar carrito.
     */
    public void actualizarListaCarritos() {
        DefaultTableModel modelo = eliminarView.getModelo();
        modelo.setRowCount(0);
        List<Carrito> carritos;
        if (usuario.getRol() == Rol.ADMINISTRADOR) {
            carritos = carritoDAO.listarTodos();
        } else {
            carritos = carritoDAO.listarPorUsuario(usuario.getUsername());
        }
        Locale locale = eliminarView.getMensajeInternacionalizacion().getLocale();
        for (Carrito c : carritos) {
            modelo.addRow(new Object[]{
                    c.getCodigo(),
                    FormateadorUtils.formatearFecha(c.getFechaCreacion().getTime(), locale),
                    c.obtenerItems().size(),
                    FormateadorUtils.formatearMoneda(c.calcularSubtotal(), locale),
                    FormateadorUtils.formatearMoneda(c.calcularTotal(), locale)
            });
        }
    }
}