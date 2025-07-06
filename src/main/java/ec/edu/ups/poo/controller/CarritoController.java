package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.DAO.CarritoDAO;
import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.models.*;
import ec.edu.ups.poo.view.CarritoActualizarView;
import ec.edu.ups.poo.view.CarritoAnadirView;
import ec.edu.ups.poo.view.CarritoListaView;
import ec.edu.ups.poo.view.DetalleCarritoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class CarritoController {
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private final CarritoListaView listaView;
    private Carrito carrito;
    private final CarritoActualizarView actualizarView;

    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView,
                             CarritoListaView listaView,
                             CarritoActualizarView actualizarView,
                             Usuario usuario) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.listaView = listaView;
        this.actualizarView = actualizarView;
        this.carrito = new Carrito(usuario);
        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        carritoAnadirView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirProducto();
            }
        });

        carritoAnadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarrito();
            }
        });

        carritoAnadirView.getBtnEliminarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarItem();
            }
        });

        carritoAnadirView.getBtnActualizarCantidad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCantidad();
            }
        });

        listaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarrito();
            }
        });

        listaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarCarritos();
            }
        });

        listaView.getBtnVerDetalles().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verDetallesCarrito();
            }
        });

        actualizarView.getBtnBuscarCarrito().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoActualizar();
            }
        });

        actualizarView.getBtnBuscarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoActualizar();
            }
        });

        actualizarView.getBtnAgregarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProductoActualizar();
            }
        });

        actualizarView.getBtnEliminarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProductoActualizar();
            }
        });

        actualizarView.getBtnActualizarCantidad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCantidadActualizar();
            }
        });

        actualizarView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarritoActualizar();
            }
        });
    }

    private void guardarCarrito() {
        int opcion = JOptionPane.showConfirmDialog(
                carritoAnadirView,
                "¿Está seguro de guardar el carrito?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            carritoDAO.crear(carrito);
            carritoAnadirView.mostrarMensaje("Carrito guardado correctamente");
            carrito = new Carrito(carrito.getUsuario());
            cargarProductos();
            mostrarTotales();
        }
    }

    private void anadirProducto() {
        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());
        carrito.agregarProducto(producto, cantidad);

        cargarProductos();
        mostrarTotales();
    }

    private void cargarProductos() {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setNumRows(0);
        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad()
            });
        }
    }

    private void mostrarTotales() {
        String subtotal = String.valueOf(carrito.calcularSubtotal());
        String iva = String.valueOf(carrito.calcularIVA());
        String total = String.valueOf(carrito.calcularTotal());

        carritoAnadirView.getTxtSubtotal().setText(subtotal);
        carritoAnadirView.getTxtIva().setText(iva);
        carritoAnadirView.getTxtTotal().setText(total);
    }

    private void eliminarItem() {
        int filaSeleccionada = carritoAnadirView.getTblProductos().getSelectedRow();
        if (filaSeleccionada == -1) {
            carritoAnadirView.mostrarMensaje("Seleccione un producto");
            return;
        }

        int codigo = (int) carritoAnadirView.getTblProductos().getValueAt(filaSeleccionada, 0);
        carrito.eliminarProducto(codigo);
        cargarProductos();
        mostrarTotales();
    }

    private void actualizarCantidad() {
        int filaSeleccionada = carritoAnadirView.getTblProductos().getSelectedRow();
        if (filaSeleccionada == -1) {
            carritoAnadirView.mostrarMensaje("Seleccione un producto");
            return;
        }

        int codigo = (int) carritoAnadirView.getTblProductos().getValueAt(filaSeleccionada, 0);
        int nuevaCantidad = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());

        for (ItemCarrito item : carrito.obtenerItems()) {
            if (item.getProducto().getCodigo() == codigo) {
                item.setCantidad(nuevaCantidad);
                break;
            }
        }

        cargarProductos();
        mostrarTotales();
    }

    private void buscarCarrito() {
        String codigoStr = listaView.getTxtBuscar().getText();
        if (codigoStr.isEmpty()) {
            listaView.mostrarMensaje("Ingrese un código de carrito");
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            Carrito carrito = carritoDAO.buscarPorCodigo(codigo);

            if (carrito == null) {
                listaView.mostrarMensaje("No se encontró un carrito con ese código");
                return;
            }

            if (!carrito.getUsuario().getUsername().equals(this.carrito.getUsuario().getUsername())) {
                listaView.mostrarMensaje("No tiene permiso para acceder a este carrito");
                return;
            }

            listaView.getModelo().setNumRows(0);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            listaView.getModelo().addRow(new Object[]{
                    carrito.getCodigo(),
                    carrito.getUsuario().getUsername(),
                    sdf.format(carrito.getFechaCreacion().getTime()),
                    String.format("%.2f", carrito.calcularTotal())
            });

        } catch (NumberFormatException e) {
            listaView.mostrarMensaje("El código debe ser un número válido");
        }
    }

    public void listarCarritos() {
        if (carrito.getUsuario().getRol() == Rol.ADMINISTRADOR) {
            listaView.cargarDatos(carritoDAO.listarTodos());
        } else {
            listaView.cargarDatos(carritoDAO.listarPorUsuario(carrito.getUsuario().getUsername()));
        }
    }

    private void verDetallesCarrito() {
        int filaSeleccionada = listaView.getTblCarritos().getSelectedRow();
        if (filaSeleccionada == -1) {
            listaView.mostrarMensaje("Seleccione un carrito de la tabla");
            return;
        }

        int codigo = (int) listaView.getModelo().getValueAt(filaSeleccionada, 0);
        Carrito carritoSeleccionado = carritoDAO.buscarPorCodigo(codigo);

        if (carrito.getUsuario().getRol() != Rol.ADMINISTRADOR &&
                !carritoSeleccionado.getUsuario().getUsername().equals(carrito.getUsuario().getUsername())) {
            listaView.mostrarMensaje("No tiene permiso para ver este carrito");
            return;
        }

        DetalleCarritoView detalleView = new DetalleCarritoView();
        detalleView.cargarDetalles(carritoSeleccionado);
        detalleView.setVisible(true);

        JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(
                JDesktopPane.class, listaView);
        desktopPane.add(detalleView);
    }

    private void buscarCarritoActualizar() {
        try {
            int codigo = Integer.parseInt(actualizarView.getTxtCodigoCarrito().getText());
            Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

            if (carritoEncontrado == null) {
                actualizarView.mostrarMensaje("Carrito no encontrado");
                return;
            }

            if (carrito.getUsuario().getRol() != Rol.ADMINISTRADOR &&
                    !carritoEncontrado.getUsuario().getUsername().equals(carrito.getUsuario().getUsername())) {
                actualizarView.mostrarMensaje("No tiene permiso para editar este carrito");
                return;
            }

            this.carrito = carritoEncontrado;
            actualizarView.setCamposHabilitados(true);
            cargarProductosActualizar();
            mostrarTotalesActualizar();
        } catch (NumberFormatException e) {
            actualizarView.mostrarMensaje("Ingrese un código válido");
        }
    }

    private void buscarProductoActualizar() {
        try {
            int codigo = Integer.parseInt(actualizarView.getTxtCodigoProducto().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                actualizarView.mostrarMensaje("Producto no encontrado");
                return;
            }

            actualizarView.mostrarDatosProducto(producto.getNombre(), producto.getPrecio());
        } catch (NumberFormatException e) {
            actualizarView.mostrarMensaje("Código inválido");
        }
    }

    private void agregarProductoActualizar() {
        try {
            int codigo = Integer.parseInt(actualizarView.getTxtCodigoProducto().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                actualizarView.mostrarMensaje("Producto no encontrado");
                return;
            }

            int cantidad = (int) actualizarView.getCbxCantidad().getSelectedItem();
            carrito.agregarProducto(producto, cantidad);

            cargarProductosActualizar();
            mostrarTotalesActualizar();
            actualizarView.limpiarCamposProducto();
        } catch (NumberFormatException e) {
            actualizarView.mostrarMensaje("Datos inválidos");
        }
    }

    private void eliminarProductoActualizar() {
        int filaSeleccionada = actualizarView.getTblProductos().getSelectedRow();

        if (filaSeleccionada == -1) {
            actualizarView.mostrarMensaje("Seleccione un producto");
            return;
        }

        int codigo = (int) actualizarView.getModeloTabla().getValueAt(filaSeleccionada, 0);
        carrito.eliminarProducto(codigo);

        cargarProductosActualizar();
        mostrarTotalesActualizar();
    }

    private void actualizarCantidadActualizar() {
        int filaSeleccionada = actualizarView.getTblProductos().getSelectedRow();

        if (filaSeleccionada == -1) {
            actualizarView.mostrarMensaje("Seleccione un producto");
            return;
        }

        int codigo = (int) actualizarView.getModeloTabla().getValueAt(filaSeleccionada, 0);
        int nuevaCantidad = (int) actualizarView.getCbxCantidad().getSelectedItem();

        for (ItemCarrito item : carrito.obtenerItems()) {
            if (item.getProducto().getCodigo() == codigo) {
                item.setCantidad(nuevaCantidad);
                break;
            }
        }

        cargarProductosActualizar();
        mostrarTotalesActualizar();
    }

    private void cargarProductosActualizar() {
        DefaultTableModel modelo = actualizarView.getModeloTabla();
        modelo.setRowCount(0);

        for (ItemCarrito item : carrito.obtenerItems()) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad()
            });
        }
    }

    private void mostrarTotalesActualizar() {
        actualizarView.actualizarTotales(
                carrito.calcularSubtotal(),
                carrito.calcularIVA(),
                carrito.calcularTotal()
        );
    }

    private void guardarCarritoActualizar() {
        int opcion = JOptionPane.showConfirmDialog(
                actualizarView,
                "¿Está seguro de guardar los cambios?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            carritoDAO.actualizar(carrito);
            actualizarView.mostrarMensaje("Carrito actualizado correctamente");
            actualizarView.limpiarTodo();
        }
    }
}