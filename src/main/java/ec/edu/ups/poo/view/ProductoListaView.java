package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.FormateadorUtils;
import ec.edu.ups.poo.models.Producto;
import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * La clase ProductoListaView es un JInternalFrame que permite visualizar una lista de productos.
 * Los usuarios pueden buscar productos por código o listar todos los productos disponibles.
 * La vista muestra los productos en una tabla no editable con columnas para código, nombre y precio.
 * Soporta internacionalización para sus etiquetas de texto y los nombres de los botones.
 */
public class ProductoListaView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JPanel panelPrincipal;
    private JButton btnListar;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;

    /**
     * Construye una nueva instancia de ProductoListaView.
     * Inicializa los componentes de la interfaz de usuario, configura el panel principal,
     * establece las propiedades de la ventana interna, configura el modelo de la tabla como no editable,
     * inicializa el manejador de internacionalización, actualiza los textos y configura los iconos.
     */
    public ProductoListaView() {
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
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);


        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProductos.setModel(modelo);
        this.mensajeInternacionalizacion = new MensajeInternacionalizacionHandler("es", "EC");
        actualizarTextos();
        configurarIconos();
    }

    /**
     * Configura los iconos para los botones en la vista.
     * Carga las imágenes desde las rutas especificadas y las escala a un tamaño consistente.
     */
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);
        btnBuscar.setIcon(cargarIcono("/imagenes/reticulo.png", iconSize));
        btnListar.setIcon(cargarIcono("/imagenes/lista-de-verificacion.png", iconSize));

        btnBuscar.setIconTextGap(10);
        btnListar.setIconTextGap(10);

        btnBuscar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnListar.setHorizontalTextPosition(SwingConstants.RIGHT);
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
     * Actualiza el título de la ventana, los textos de los botones y los encabezados de las columnas de la tabla
     * según la configuración de internacionalización actual.
     */
    public void actualizarTextos() {
        this.setTitle(mensajeInternacionalizacion.get("titulo.producto"));
        btnBuscar.setText(mensajeInternacionalizacion.get("producto.buscar"));
        btnListar.setText(mensajeInternacionalizacion.get("producto.listar"));

        Object[] columnas = {
                mensajeInternacionalizacion.get("producto.columna.codigo"),
                mensajeInternacionalizacion.get("producto.columna.nombre"),
                mensajeInternacionalizacion.get("producto.columna.precio")
        };
        modelo.setColumnIdentifiers(columnas);
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
     * Devuelve el campo de texto para la búsqueda.
     *
     * @return El JTextField para la entrada de búsqueda.
     */
    public JTextField getTxtBuscar() {
        return txtBuscar;
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
     * Devuelve el JTable que muestra los productos.
     *
     * @return El JTable para la visualización de productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
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
     * Devuelve el componente del botón "Listar".
     *
     * @return El JButton para listar todos los productos.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Devuelve el modelo de tabla predeterminado utilizado por la JTable de productos.
     *
     * @return El DefaultTableModel de la tabla de productos.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Carga los datos de una lista de productos en la tabla.
     * La tabla se vacía antes de añadir los nuevos datos y el precio se formatea como moneda.
     *
     * @param listaProductos La lista de objetos Producto a mostrar en la tabla.
     */
    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setRowCount(0);
        Locale locale = mensajeInternacionalizacion.getLocale();

        for (Producto producto : listaProductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    FormateadorUtils.formatearMoneda(producto.getPrecio(), locale)
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Muestra un cuadro de diálogo de mensaje al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}