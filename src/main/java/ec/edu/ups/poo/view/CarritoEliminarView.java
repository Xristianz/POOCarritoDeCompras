package ec.edu.ups.poo.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CarritoEliminarView extends JInternalFrame {
    private JTextField txtCodigoCarrito;
    private JButton btnEliminar;
    private JButton btnActualizarLista;
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private DefaultTableModel modelo;

    public CarritoEliminarView() {
        super("Eliminar Carrito", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);

        // Configurar modelo de tabla
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("Código");
        modelo.addColumn("Fecha Creación");
        modelo.addColumn("Total Items");
        modelo.addColumn("Subtotal");
        modelo.addColumn("Total");
        tblCarritos.setModel(modelo);
    }

    // Getters
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnActualizarLista() {
        return btnActualizarLista;
    }

    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public int mostrarConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(
                this,
                mensaje,
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );
    }

    public void limpiarCampos() {
        txtCodigoCarrito.setText("");
    }
}
