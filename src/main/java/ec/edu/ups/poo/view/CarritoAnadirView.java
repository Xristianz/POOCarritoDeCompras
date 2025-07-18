package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.CarritoController;
import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

/**
 * La clase CarritoAnadirView es un JInternalFrame que proporciona una interfaz de usuario para añadir productos a un carrito de compras.
 * Incluye campos para el código, nombre y precio del producto, y la cantidad, junto con botones para buscar, añadir, guardar,
 * limpiar, eliminar y actualizar elementos en el carrito. La vista muestra una tabla de productos seleccionados y calcula
 * el subtotal, el IVA y el total. También es compatible con la internacionalización de las etiquetas de texto y los nombres de los botones.
 */
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
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;

    /**
     * Construye una nueva instancia de CarritoAnadirView.
     * Inicializa los componentes de la interfaz de usuario, configura el modelo de la tabla, carga los datos iniciales,
     * actualiza las etiquetas de texto para la internacionalización y configura los iconos de los botones.
     */
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


        this.mensajeInternacionalizacion = new MensajeInternacionalizacionHandler("es", "EC");


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


        cargarDatos();


        actualizarTextos();
        configurarIconos();
    }

    /**
     * Configura los iconos para los botones de la vista.
     * Carga las imágenes desde las rutas especificadas y las escala a un tamaño consistente.
     */
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

    /**
     * Carga un icono desde la ruta dada y lo escala al tamaño especificado.
     *
     * @param ruta La ruta al recurso de la imagen.
     * @param size El tamaño deseado para el icono.
     * @return Un objeto ImageIcon, o null si la imagen no se puede cargar.
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
     * Actualiza todas las etiquetas de texto, textos de botones y encabezados de columnas de la tabla
     * según la configuración de internacionalización actual.
     */
    public void actualizarTextos() {

        this.setTitle(mensajeInternacionalizacion.get("titulo.carrito"));


        lblCodigo.setText(mensajeInternacionalizacion.get("carrito.codigo"));
        lblNombre.setText(mensajeInternacionalizacion.get("carrito.nombre"));
        lblPrecio.setText(mensajeInternacionalizacion.get("carrito.precio"));
        lblCantidad.setText(mensajeInternacionalizacion.get("carrito.cantidad"));
        lblSubtotal.setText(mensajeInternacionalizacion.get("carrito.subtotal"));
        lblIva.setText(mensajeInternacionalizacion.get("carrito.iva"));
        lblTotal.setText(mensajeInternacionalizacion.get("carrito.total"));


        btnBuscar.setText(mensajeInternacionalizacion.get("carrito.buscar"));
        btnAnadir.setText(mensajeInternacionalizacion.get("carrito.anadir"));
        btnGuardar.setText(mensajeInternacionalizacion.get("carrito.guardar"));
        btnLimpiar.setText(mensajeInternacionalizacion.get("carrito.limpiar"));
        btnEliminarItem.setText(mensajeInternacionalizacion.get("carrito.eliminar"));
        btnActualizarCantidad.setText(mensajeInternacionalizacion.get("carrito.actualizar"));


        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
        modelo.setColumnIdentifiers(new Object[]{
                mensajeInternacionalizacion.get("carrito.columna.codigo"),
                mensajeInternacionalizacion.get("carrito.columna.nombre"),
                mensajeInternacionalizacion.get("carrito.columna.precio"),
                mensajeInternacionalizacion.get("carrito.columna.cantidad"),
                mensajeInternacionalizacion.get("carrito.columna.subtotal")
        });


        if (carritoController != null) {
            carritoController.mostrarTotales();
        }
    }

    /**
     * Establece el CarritoController para esta vista.
     *
     * @param carritoController La instancia de CarritoController a utilizar.
     */
    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
    }

    /**
     * Carga los datos iniciales en los componentes de la vista, específicamente poblando el combo box de cantidad.
     */
    private void cargarDatos() {
        cbxCantidad.removeAllItems();
        for(int i = 0; i < 20; i++) {
            cbxCantidad.addItem(String.valueOf(i + 1));
        }
    }

    /**
     * Devuelve la instancia de MensajeInternacionalizacionHandler utilizada para la internacionalización.
     *
     * @return La instancia de MensajeInternacionalizacionHandler.
     */
    public MensajeInternacionalizacionHandler getMensajeInternacionalizacion() {
        return mensajeInternacionalizacion;
    }

    /**
     * Devuelve el componente del botón "Buscar".
     *
     * @return El JButton para buscar productos.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Devuelve el campo de texto para el código del producto.
     *
     * @return El JTextField para la entrada del código del producto.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Devuelve el campo de texto para el nombre del producto.
     *
     * @return El JTextField para la visualización del nombre del producto.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Devuelve el campo de texto para el precio del producto.
     *
     * @return El JTextField para la visualización del precio del producto.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Devuelve el componente del botón "Añadir".
     *
     * @return El JButton para añadir productos al carrito.
     */
    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    /**
     * Devuelve el JTable que muestra los productos en el carrito.
     *
     * @return El JTable para la visualización de productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Devuelve el campo de texto para la visualización del subtotal.
     *
     * @return El JTextField para la visualización del subtotal.
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Devuelve el campo de texto para la visualización del IVA.
     *
     * @return El JTextField para la visualización del IVA.
     */
    public JTextField getTxtIva() {
        return txtIva;
    }

    /**
     * Devuelve el campo de texto para la visualización del total.
     *
     * @return El JTextField para la visualización del total.
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Devuelve el componente del botón "Guardar".
     *
     * @return El JButton para guardar el carrito.
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * Devuelve el componente del botón "Limpiar".
     *
     * @return El JButton para limpiar el carrito.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Devuelve el combo box para seleccionar la cantidad del producto.
     *
     * @return El JComboBox para la selección de cantidad.
     */
    public JComboBox<String> getCbxCantidad() {
        return cbxCantidad;
    }

    /**
     * Devuelve el panel principal de la vista.
     *
     * @return El JPanel que representa el área de contenido principal.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    /**
     * Devuelve el componente del botón "Eliminar Item".
     *
     * @return El JButton para eliminar un elemento del carrito.
     */
    public JButton getBtnEliminarItem() {
        return btnEliminarItem;
    }

    /**
     * Devuelve el componente del botón "Actualizar Cantidad".
     *
     * @return El JButton para actualizar la cantidad de un elemento en el carrito.
     */
    public JButton getBtnActualizarCantidad() {
        return btnActualizarCantidad;
    }

    /**
     * Muestra un cuadro de diálogo de mensaje al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Este método es llamado por el diseñador de la interfaz de usuario para crear componentes personalizados.
     * Está vacío ya que no se manejan creaciones de componentes personalizados aquí.
     */
    private void createUIComponents() {
    }
}