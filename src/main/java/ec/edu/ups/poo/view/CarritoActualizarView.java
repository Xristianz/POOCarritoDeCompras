package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

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
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;

    // Labels
    private JLabel lblCodigoCarrito;
    private JLabel lblCodigoProducto;
    private JLabel lblNombreProducto;
    private JLabel lblPrecioProducto;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;

    public CarritoActualizarView() {
        super("", true, true, false, true);
        panelPrincipal.setBackground(Color.darkGray);
        panelPrincipal.setForeground(Color.WHITE);
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
            }
        }
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);


        this.mensajeInternacionalizacion = new MensajeInternacionalizacionHandler("es", "EC");


        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Subtotal");
        tblProductos.setModel(modeloTabla);


        inicializarComboBoxCantidad();


        txtNombreProducto.setEditable(false);
        txtPrecioProducto.setEditable(false);
        txtSubtotal.setEditable(false);
        txtIva.setEditable(false);
        txtTotal.setEditable(false);


        setCamposHabilitados(false);


        actualizarTextos();
    }

    public void actualizarTextos() {
        // Título
        setTitle(mensajeInternacionalizacion.get("titulo.actualizar_carrito"));

        // Labels
        lblCodigoCarrito.setText(mensajeInternacionalizacion.get("carrito.codigo_carrito"));
        lblCodigoProducto.setText(mensajeInternacionalizacion.get("carrito.codigo_producto"));
        lblNombreProducto.setText(mensajeInternacionalizacion.get("carrito.nombre"));
        lblPrecioProducto.setText(mensajeInternacionalizacion.get("carrito.precio"));
        lblCantidad.setText(mensajeInternacionalizacion.get("carrito.cantidad"));
        lblSubtotal.setText(mensajeInternacionalizacion.get("carrito.subtotal"));
        lblIva.setText(mensajeInternacionalizacion.get("carrito.iva"));
        lblTotal.setText(mensajeInternacionalizacion.get("carrito.total"));

        // Botones
        btnBuscarCarrito.setText(mensajeInternacionalizacion.get("carrito.buscar"));
        btnBuscarProducto.setText(mensajeInternacionalizacion.get("carrito.buscar"));
        btnAgregarProducto.setText(mensajeInternacionalizacion.get("carrito.agregar"));
        btnEliminarProducto.setText(mensajeInternacionalizacion.get("carrito.eliminar"));
        btnActualizarCantidad.setText(mensajeInternacionalizacion.get("carrito.actualizar_cantidad"));
        btnGuardar.setText(mensajeInternacionalizacion.get("carrito.guardar"));
        btnCancelar.setText(mensajeInternacionalizacion.get("carrito.cancelar"));

        // Encabezados de tabla
        modeloTabla.setColumnIdentifiers(new Object[]{
                mensajeInternacionalizacion.get("carrito.columna.codigo"),
                mensajeInternacionalizacion.get("carrito.columna.nombre"),
                mensajeInternacionalizacion.get("carrito.columna.precio"),
                mensajeInternacionalizacion.get("carrito.columna.cantidad"),
                mensajeInternacionalizacion.get("carrito.columna.subtotal")
        });
        configurarIconos();
    }
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);

        btnBuscarCarrito.setIcon(cargarIcono("/imagenes/buscar.png", iconSize));
        btnBuscarProducto.setIcon(cargarIcono("/imagenes/reticulo.png", iconSize));
        btnAgregarProducto.setIcon(cargarIcono("/imagenes/agregar.png", iconSize));
        btnEliminarProducto.setIcon(cargarIcono("/imagenes/carro-vacio.png", iconSize));
        btnActualizarCantidad.setIcon(cargarIcono("/imagenes/actualizar.png", iconSize));
        btnGuardar.setIcon(cargarIcono("/imagenes/compras.png", iconSize));
        btnCancelar.setIcon(cargarIcono("/imagenes/revolver.png", iconSize));

        btnBuscarCarrito.setIconTextGap(10);
        btnBuscarProducto.setIconTextGap(10);
        btnAgregarProducto.setIconTextGap(10);
        btnEliminarProducto.setIconTextGap(10);
        btnActualizarCantidad.setIconTextGap(10);
        btnGuardar.setIconTextGap(10);
        btnCancelar.setIconTextGap(10);

        btnBuscarCarrito.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnBuscarProducto.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnAgregarProducto.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnEliminarProducto.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnActualizarCantidad.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnGuardar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnCancelar.setHorizontalTextPosition(SwingConstants.RIGHT);
    }

    private ImageIcon cargarIcono(String ruta, Dimension size) {
        try {
            URL imgURL = getClass().getResource(ruta);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
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

    // Getters (mantener los mismos)
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
    public MensajeInternacionalizacionHandler getMensajeInternacionalizacion() {
        return mensajeInternacionalizacion;
    }
}