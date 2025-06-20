package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.models.Producto;
import ec.edu.ups.poo.view.CarritoAnadirView;
import javax.swing.table.DefaultTableModel;

public class CarritoController {

    private final CarritoAnadirView carritoView;
    private final ProductoDAO productoDAO;
    private final DefaultTableModel modeloTabla;

    public CarritoController(CarritoAnadirView carritoView, ProductoDAO productoDAO) {
        this.carritoView = carritoView;
        this.productoDAO = productoDAO;


        modeloTabla = new DefaultTableModel();
        modeloTabla.setColumnIdentifiers(new String[]{"Código", "Nombre", "Precio", "Cantidad"});
        carritoView.getTblProductos().setModel(modeloTabla);

        iniciarEventos();
    }

    private void iniciarEventos() {
        carritoView.getBtnBuscar().addActionListener(e -> buscarProducto());
        carritoView.getBtnAnadir().addActionListener(e -> anadirProductoAlCarrito());
        carritoView.getBtnLimpiar().addActionListener(e -> limpiarCampos());
    }

    private void buscarProducto() {
        try {
            int codigo = Integer.parseInt(carritoView.getTxtCodigo().getText().trim());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto != null) {
                carritoView.getTxtNombre().setText(producto.getNombre());
                carritoView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                carritoView.mostrarMensaje("Producto no encontrado.");
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            carritoView.mostrarMensaje("Código inválido. Debe ser un número.");
        }
    }

    private void anadirProductoAlCarrito() {
        try {
            int codigo = Integer.parseInt(carritoView.getTxtCodigo().getText().trim());
            String nombre = carritoView.getTxtNombre().getText().trim();
            double precio = Double.parseDouble(carritoView.getTxtPrecio().getText().trim());
            int cantidad = Integer.parseInt(carritoView.getCbxCantidad().getSelectedItem().toString());

            if (nombre.isEmpty()) {
                carritoView.mostrarMensaje("Debe buscar un producto válido.");
                return;
            }

            modeloTabla.addRow(new Object[]{codigo, nombre, precio, cantidad});
            calcularTotales();
            limpiarCampos();

        } catch (NumberFormatException e) {
            carritoView.mostrarMensaje("Datos inválidos. Revise los campos.");
        }
    }

    private void calcularTotales() {
        double subtotal = 0;

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            double precio = (double) modeloTabla.getValueAt(i, 2);
            int cantidad = Integer.parseInt(modeloTabla.getValueAt(i, 3).toString());
            subtotal += precio * cantidad;
        }

        double iva = subtotal * 0.12;
        double total = subtotal + iva;

        carritoView.getTxtSubtotal().setText(String.format("%.2f", subtotal));
        carritoView.getTxtIva().setText(String.format("%.2f", iva));
        carritoView.getTxtTotal().setText(String.format("%.2f", total));
    }

    private void limpiarCampos() {
        carritoView.getTxtCodigo().setText("");
        carritoView.getTxtNombre().setText("");
        carritoView.getTxtPrecio().setText("");
        carritoView.getCbxCantidad().setSelectedIndex(0);
    }
}