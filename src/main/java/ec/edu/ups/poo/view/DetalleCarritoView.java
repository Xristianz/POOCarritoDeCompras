package ec.edu.ups.poo.view;

import ec.edu.ups.poo.models.Carrito;
import ec.edu.ups.poo.models.ItemCarrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DetalleCarritoView extends JInternalFrame {
    private JTable tblDetalles;
    private JPanel panelPrincipal;
    private DefaultTableModel modelo;

    public DetalleCarritoView() {
        super("Detalles del Carrito", true, true, true, true);
        setContentPane(panelPrincipal);
        setSize(600, 400);

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"Código", "Producto", "Precio", "Cantidad", "Subtotal", "Total con IVA"});
        tblDetalles.setModel(modelo);
    }

    public void cargarDetalles(Carrito carrito) {
        modelo.setNumRows(0);

        if (carrito != null) {
            for (ItemCarrito item : carrito.obtenerItems()) {
                double subtotal = item.getProducto().getPrecio() * item.getCantidad();
                double totalConIVA = subtotal * 1.12; // Asumiendo 12% de IVA

                modelo.addRow(new Object[]{
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        String.format("%.2f", item.getProducto().getPrecio()),
                        item.getCantidad(),
                        String.format("%.2f", subtotal),
                        String.format("%.2f", totalConIVA)
                });
            }

            // Agregar fila de totales
            modelo.addRow(new Object[]{
                    "", "", "", "",
                    String.format("%.2f", carrito.calcularSubtotal()),
                    String.format("%.2f", carrito.calcularTotal())
            });
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

}
