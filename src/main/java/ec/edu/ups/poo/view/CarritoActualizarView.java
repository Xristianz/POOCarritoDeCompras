package ec.edu.ups.poo.view;

import javax.swing.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CarritoActualizarView extends JInternalFrame {
    private JTextField txtCodigoCarrito;
    private JButton btnBuscarCarrito;
    private JTable tblProductos;
    private JButton btnAgregarProducto;
    private JButton btnEliminarProducto;
    private JButton btnActualizarCantidad;
    private JComboBox<Integer> cbxCantidad;
    private JTextField txtCodigoProducto;
    private JTextField txtNombreProducto;
    private JTextField txtPrecioProducto;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JPanel panelPrincipal;
    private JButton btnBuscarProducto;

    private DefaultTableModel modeloTabla;

    public CarritoActualizarView() {
        super("Actualizar Carrito", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        // Configurar modelo de tabla
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable directamente
            }
        };
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Subtotal");
        tblProductos.setModel(modeloTabla);

        // Inicializar ComboBox de cantidades
        inicializarComboBoxCantidad();

        // Configurar campos de solo lectura
        txtNombreProducto.setEditable(false);
        txtPrecioProducto.setEditable(false);
        txtSubtotal.setEditable(false);
        txtIva.setEditable(false);
        txtTotal.setEditable(false);

        // Deshabilitar campos inicialmente
        setCamposHabilitados(false);
    }

    private void inicializarComboBoxCantidad() {
        DefaultComboBoxModel<Integer> modelo = new DefaultComboBoxModel<>();
        for (int i = 1; i <= 20; i++) {
            modelo.addElement(i);
        }
        cbxCantidad.setModel(modelo);
        cbxCantidad.setSelectedIndex(0);
    }

    public void setCamposHabilitados(boolean habilitado) {
        txtCodigoProducto.setEnabled(habilitado);
        btnBuscarProducto.setEnabled(habilitado);
        txtNombreProducto.setEnabled(habilitado);
        txtPrecioProducto.setEnabled(habilitado);
        btnAgregarProducto.setEnabled(habilitado);
        btnEliminarProducto.setEnabled(habilitado);
        btnActualizarCantidad.setEnabled(habilitado);
        cbxCantidad.setEnabled(habilitado);
        btnGuardar.setEnabled(habilitado);
        btnCancelar.setEnabled(habilitado);
    }

    // Getters para todos los componentes
    public JButton getBtnBuscarCarrito() { return btnBuscarCarrito; }
    public JButton getBtnAgregarProducto() { return btnAgregarProducto; }
    public JButton getBtnEliminarProducto() { return btnEliminarProducto; }
    public JButton getBtnActualizarCantidad() { return btnActualizarCantidad; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JTextField getTxtCodigoCarrito() { return txtCodigoCarrito; }
    public JTextField getTxtCodigoProducto() { return txtCodigoProducto; }
    public JTextField getTxtNombreProducto() { return txtNombreProducto; }
    public JTextField getTxtPrecioProducto() { return txtPrecioProducto; }
    public JComboBox<Integer> getCbxCantidad() { return cbxCantidad; }
    public JTextField getTxtSubtotal() { return txtSubtotal; }
    public JTextField getTxtIva() { return txtIva; }
    public JTextField getTxtTotal() { return txtTotal; }
    public JTable getTblProductos() { return tblProductos; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JButton getBtnBuscarProducto() { return btnBuscarProducto; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCamposProducto() {
        txtCodigoProducto.setText("");
        txtNombreProducto.setText("");
        txtPrecioProducto.setText("");
        cbxCantidad.setSelectedIndex(0);
    }

    public void limpiarTodo() {
        txtCodigoCarrito.setText("");
        modeloTabla.setRowCount(0);
        txtSubtotal.setText("");
        txtIva.setText("");
        txtTotal.setText("");
        limpiarCamposProducto();
        setCamposHabilitados(false);
    }

    public void cargarProductosEnTabla(Object[][] datos) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }

    public void actualizarTotales(double subtotal, double iva, double total) {
        txtSubtotal.setText(String.format("%.2f", subtotal));
        txtIva.setText(String.format("%.2f", iva));
        txtTotal.setText(String.format("%.2f", total));
    }

    public void mostrarDatosProducto(String nombre, double precio) {
        txtNombreProducto.setText(nombre);
        txtPrecioProducto.setText(String.format("%.2f", precio));
    }
}