package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.controller.util.FormateadorUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.Locale;

/**
 * Vista (JInternalFrame) para la actualización de un carrito de compras existente.
 * <p>
 * Permite buscar un carrito por código y, una vez cargado, modificar sus ítems
 * (agregar, eliminar, actualizar cantidad) y guardar los cambios.
 * </p>
 */
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

    private JLabel lblCodigoCarrito;
    private JLabel lblCodigoProducto;
    private JLabel lblNombreProducto;
    private JLabel lblPrecioProducto;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;

    /**
     * Construye la ventana de actualización de carritos.
     * Inicializa los componentes, el modelo de la tabla y los textos internacionalizados.
     */
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

        if (txtCodigoCarrito == null) txtCodigoCarrito = new JTextField();
        if (txtCodigoProducto == null) txtCodigoProducto = new JTextField();
        if (txtNombreProducto == null) txtNombreProducto = new JTextField();
        if (txtPrecioProducto == null) txtPrecioProducto = new JTextField();
        if (txtSubtotal == null) txtSubtotal = new JTextField();
        if (txtIva == null) txtIva = new JTextField();
        if (txtTotal == null) txtTotal = new JTextField();
        if (tblProductos == null) tblProductos = new JTable();

        this.mensajeInternacionalizacion = new MensajeInternacionalizacionHandler("es", "EC");

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
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

    /**
     * Actualiza todos los textos de la interfaz gráfica al idioma actual.
     */
    public void actualizarTextos() {
        setTitle(mensajeInternacionalizacion.get("titulo.actualizar_carrito"));

        lblCodigoCarrito.setText(mensajeInternacionalizacion.get("carrito.codigo_carrito"));
        lblCodigoProducto.setText(mensajeInternacionalizacion.get("carrito.codigo_producto"));
        lblNombreProducto.setText(mensajeInternacionalizacion.get("carrito.nombre"));
        lblPrecioProducto.setText(mensajeInternacionalizacion.get("carrito.precio"));
        lblCantidad.setText(mensajeInternacionalizacion.get("carrito.cantidad"));
        lblSubtotal.setText(mensajeInternacionalizacion.get("carrito.subtotal"));
        lblIva.setText(mensajeInternacionalizacion.get("carrito.iva"));
        lblTotal.setText(mensajeInternacionalizacion.get("carrito.total"));

        btnBuscarCarrito.setText(mensajeInternacionalizacion.get("carrito.buscar"));
        btnBuscarProducto.setText(mensajeInternacionalizacion.get("carrito.buscar"));
        btnAgregarProducto.setText(mensajeInternacionalizacion.get("carrito.agregar"));
        btnEliminarProducto.setText(mensajeInternacionalizacion.get("carrito.eliminar"));
        btnActualizarCantidad.setText(mensajeInternacionalizacion.get("carrito.actualizar_cantidad"));
        btnGuardar.setText(mensajeInternacionalizacion.get("carrito.guardar"));
        btnCancelar.setText(mensajeInternacionalizacion.get("carrito.cancelar"));

        modeloTabla.setColumnIdentifiers(new Object[]{
                mensajeInternacionalizacion.get("carrito.columna.codigo"),
                mensajeInternacionalizacion.get("carrito.columna.nombre"),
                mensajeInternacionalizacion.get("carrito.columna.precio"),
                mensajeInternacionalizacion.get("carrito.columna.cantidad"),
                mensajeInternacionalizacion.get("carrito.columna.subtotal")
        });
        configurarIconos();
    }

    /**
     * Carga y asigna los iconos a los botones de la interfaz.
     */
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

    /**
     * Carga un recurso de imagen y lo escala al tamaño deseado.
     * @param ruta La ruta del recurso de la imagen.
     * @param size La dimensión a la que se escalará el icono.
     * @return un {@code ImageIcon} escalado, o {@code null} si no se encuentra.
     */
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

    /**
     * Inicializa el JComboBox de cantidades con valores del 1 al 20.
     */
    private void inicializarComboBoxCantidad() {
        DefaultComboBoxModel<Integer> modelo = new DefaultComboBoxModel<>();
        for (int i = 1; i <= 20; i++) {
            modelo.addElement(i);
        }
        cbxCantidad.setModel(modelo);
        cbxCantidad.setSelectedIndex(0);
    }

    /**
     * Habilita o deshabilita los campos de edición de productos del carrito.
     * @param habilitado {@code true} para habilitar, {@code false} para deshabilitar.
     */
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

    /**
     * Muestra un mensaje de diálogo al usuario.
     * @param mensaje El texto a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Limpia los campos de texto relacionados con la búsqueda y datos de un producto.
     */
    public void limpiarCamposProducto() {
        txtCodigoProducto.setText("");
        txtNombreProducto.setText("");
        txtPrecioProducto.setText("");
        cbxCantidad.setSelectedIndex(0);
    }

    /**
     * Limpia todos los campos y la tabla de la vista, y deshabilita los controles de edición.
     */
    public void limpiarTodo() {
        txtCodigoCarrito.setText("");
        modeloTabla.setRowCount(0);
        txtSubtotal.setText("");
        txtIva.setText("");
        txtTotal.setText("");
        limpiarCamposProducto();
        setCamposHabilitados(false);
    }

    /**
     * Rellena la tabla de productos con los datos proporcionados.
     * @param datos Un arreglo de objetos bidimensional con los datos de los ítems.
     */
    public void cargarProductosEnTabla(Object[][] datos) {
        modeloTabla.setRowCount(0);
        Locale locale = mensajeInternacionalizacion.getLocale();
        for (Object[] fila : datos) {

            fila[2] = FormateadorUtils.formatearMoneda((Double) fila[2], locale);
            fila[4] = FormateadorUtils.formatearMoneda((Double) fila[4], locale);
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Actualiza los campos de texto de subtotal, IVA y total con valores formateados.
     * @param subtotal El valor del subtotal.
     * @param iva El valor del IVA.
     * @param total El valor del total.
     */
    public void actualizarTotales(double subtotal, double iva, double total) {
        Locale locale = mensajeInternacionalizacion.getLocale();
        txtSubtotal.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
        txtIva.setText(FormateadorUtils.formatearMoneda(iva, locale));
        txtTotal.setText(FormateadorUtils.formatearMoneda(total, locale));
    }

    /**
     * Muestra los datos de un producto encontrado en los campos de texto correspondientes.
     * @param nombre El nombre del producto.
     * @param precio El precio del producto.
     */
    public void mostrarDatosProducto(String nombre, double precio) {
        txtNombreProducto.setText(nombre);
        Locale locale = mensajeInternacionalizacion.getLocale();
        txtPrecioProducto.setText(FormateadorUtils.formatearMoneda(precio, locale));
    }

    /**
     * Devuelve el manejador de internacionalización de esta vista.
     * @return la instancia de {@code MensajeInternacionalizacionHandler}.
     */
    public MensajeInternacionalizacionHandler getMensajeInternacionalizacion() {
        return mensajeInternacionalizacion;
    }

    /**
     * A continuación se presentan los métodos de acceso para los componentes de la UI,
     * permitiendo que el controlador interactúe con ellos.
     */
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
    public JTable getTblProductos() { return tblProductos; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JButton getBtnBuscarProducto() { return btnBuscarProducto; }
}