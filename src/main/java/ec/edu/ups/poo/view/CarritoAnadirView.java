package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.CarritoController;
import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class CarritoAnadirView extends JInternalFrame {
    private JButton btnBuscar;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAnadir;
    private JTable tblProductos;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JComboBox<String> cbxCantidad;
    private JPanel panelPrincipal;
    private JButton btnEliminarItem;
    private JButton btnActualizarCantidad;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;
    private CarritoController carritoController;

    // Componentes para las etiquetas (labels)
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;

    public CarritoAnadirView() {
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
        setSize(600, 500);

        // Configuración inicial de campos editables
        txtNombre.setEditable(false);
        txtPrecio.setEditable(false);
        txtSubtotal.setEditable(false);
        txtIva.setEditable(false);
        txtTotal.setEditable(false);

        // Inicializar el manejador de internacionalización
        this.mensajeInternacionalizacion = new MensajeInternacionalizacionHandler("es", "EC");

        // Configurar la tabla
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Subtotal");
        tblProductos.setModel(modelo);

        // Cargar datos iniciales
        cargarDatos();

        // Actualizar textos con el idioma inicial
        actualizarTextos();
        configurarIconos();
    }

    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);

        btnBuscar.setIcon(cargarIcono("/imagenes/buscar.png", iconSize));
        btnAnadir.setIcon(cargarIcono("/imagenes/agregar.png", iconSize));
        btnGuardar.setIcon(cargarIcono("/imagenes/compras.png", iconSize));
        btnLimpiar.setIcon(cargarIcono("/imagenes/carro-vacio.png", iconSize));
        btnEliminarItem.setIcon(cargarIcono("/imagenes/eliminar-producto.png", iconSize));
        btnActualizarCantidad.setIcon(cargarIcono("/imagenes/lista-de-verificacion.png", iconSize));

        btnBuscar.setIconTextGap(10);
        btnAnadir.setIconTextGap(10);
        btnGuardar.setIconTextGap(10);
        btnLimpiar.setIconTextGap(10);
        btnEliminarItem.setIconTextGap(10);
        btnActualizarCantidad.setIconTextGap(10);

        btnBuscar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnAnadir.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnGuardar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnLimpiar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnEliminarItem.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnActualizarCantidad.setHorizontalTextPosition(SwingConstants.RIGHT);
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

    public void actualizarTextos() {
        // Actualizar título de la ventana
        this.setTitle(mensajeInternacionalizacion.get("titulo.carrito"));

        // Actualizar etiquetas
        lblCodigo.setText(mensajeInternacionalizacion.get("carrito.codigo"));
        lblNombre.setText(mensajeInternacionalizacion.get("carrito.nombre"));
        lblPrecio.setText(mensajeInternacionalizacion.get("carrito.precio"));
        lblCantidad.setText(mensajeInternacionalizacion.get("carrito.cantidad"));
        lblSubtotal.setText(mensajeInternacionalizacion.get("carrito.subtotal"));
        lblIva.setText(mensajeInternacionalizacion.get("carrito.iva"));
        lblTotal.setText(mensajeInternacionalizacion.get("carrito.total"));

        // Actualizar botones
        btnBuscar.setText(mensajeInternacionalizacion.get("carrito.buscar"));
        btnAnadir.setText(mensajeInternacionalizacion.get("carrito.anadir"));
        btnGuardar.setText(mensajeInternacionalizacion.get("carrito.guardar"));
        btnLimpiar.setText(mensajeInternacionalizacion.get("carrito.limpiar"));
        btnEliminarItem.setText(mensajeInternacionalizacion.get("carrito.eliminar"));
        btnActualizarCantidad.setText(mensajeInternacionalizacion.get("carrito.actualizar"));

        // Actualizar encabezados de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
        modelo.setColumnIdentifiers(new Object[]{
                mensajeInternacionalizacion.get("carrito.columna.codigo"),
                mensajeInternacionalizacion.get("carrito.columna.nombre"),
                mensajeInternacionalizacion.get("carrito.columna.precio"),
                mensajeInternacionalizacion.get("carrito.columna.cantidad"),
                mensajeInternacionalizacion.get("carrito.columna.subtotal")
        });

        // Refrescar los totales con el nuevo formato
        if (carritoController != null) {
            carritoController.mostrarTotales();
        }
    }
    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
    }

    private void cargarDatos() {
        cbxCantidad.removeAllItems();
        for(int i = 0; i < 20; i++) {
            cbxCantidad.addItem(String.valueOf(i + 1));
        }
    }

    public MensajeInternacionalizacionHandler getMensajeInternacionalizacion() {
        return mensajeInternacionalizacion;
    }

    // Getters para los componentes
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public JTextField getTxtIva() {
        return txtIva;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JComboBox<String> getCbxCantidad() {
        return cbxCantidad;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JButton getBtnEliminarItem() {
        return btnEliminarItem;
    }

    public JButton getBtnActualizarCantidad() {
        return btnActualizarCantidad;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Método para crear los componentes UI (usado por el diseñador de formularios)
    private void createUIComponents() {
        // Inicialización de componentes personalizados si es necesario
    }
}