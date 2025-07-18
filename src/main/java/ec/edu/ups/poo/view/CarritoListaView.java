package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.controller.util.FormateadorUtils;
import ec.edu.ups.poo.models.Carrito;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * La clase CarritoListaView es un JInternalFrame que permite visualizar una lista de carritos de compras.
 * Los usuarios pueden buscar carritos por código, listar todos los carritos y ver los detalles de un carrito seleccionado.
 * La vista es compatible con la internacionalización para sus etiquetas y textos de botones,
 * y muestra los carritos en una tabla con información formateada.
 */
public class CarritoListaView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblCarritos;
    private JPanel panelPrincipal;
    private JButton btnListar;
    private JButton btnVerDetalles;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;

    private JLabel lblBuscar;

    /**
     * Construye una nueva instancia de CarritoListaView.
     * Inicializa los componentes de la interfaz de usuario, configura el modelo de la tabla,
     * actualiza los textos para internacionalización y configura los iconos de los botones.
     */
    public CarritoListaView() {
        super("", true, true, true, true);
        panelPrincipal.setBackground(Color.darkGray);
        panelPrincipal.setForeground(Color.WHITE);
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
            }
        }
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);


        if (txtBuscar == null) txtBuscar = new JTextField();
        if (tblCarritos == null) tblCarritos = new JTable();


        this.mensajeInternacionalizacion = new MensajeInternacionalizacionHandler("es", "EC");

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblCarritos.setModel(modelo);


        actualizarTextos();
        configurarIconos();
    }

    /**
     * Configura los iconos para los botones en la vista.
     * Carga las imágenes desde las rutas especificadas y las escala a un tamaño consistente.
     */
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);

        btnBuscar.setIcon(cargarIcono("/imagenes/revolver.png", iconSize));
        btnListar.setIcon(cargarIcono("/imagenes/lista-de-verificacion.png", iconSize));
        btnVerDetalles.setIcon(cargarIcono("/imagenes/buscar.png", iconSize));

        btnBuscar.setIconTextGap(10);
        btnListar.setIconTextGap(10);
        btnVerDetalles.setIconTextGap(10);


        btnBuscar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnListar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnVerDetalles.setHorizontalTextPosition(SwingConstants.RIGHT);
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
        setTitle(mensajeInternacionalizacion.get("titulo.lista_carritos"));
        lblBuscar.setText(mensajeInternacionalizacion.get("carrito.buscar_por_codigo"));
        btnBuscar.setText(mensajeInternacionalizacion.get("carrito.buscar"));
        btnListar.setText(mensajeInternacionalizacion.get("carrito.listar_todos"));
        btnVerDetalles.setText(mensajeInternacionalizacion.get("carrito.ver_detalles"));
        modelo.setColumnIdentifiers(new Object[]{
                mensajeInternacionalizacion.get("carrito.columna.codigo"),
                mensajeInternacionalizacion.get("carrito.columna.usuario"),
                mensajeInternacionalizacion.get("carrito.columna.fecha"),
                mensajeInternacionalizacion.get("carrito.columna.total")
        });
    }

    /**
     * Carga los datos de una lista de carritos en la tabla.
     * La tabla se vacía antes de añadir los nuevos datos.
     *
     * @param listaCarritos La lista de objetos Carrito a mostrar en la tabla.
     */
    public void cargarDatos(List<Carrito> listaCarritos) {
        modelo.setNumRows(0);
        Locale locale = mensajeInternacionalizacion.getLocale();

        for (Carrito carrito : listaCarritos) {
            modelo.addRow(new Object[]{
                    carrito.getCodigo(),
                    carrito.getUsuario().getUsername(),
                    FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), locale),
                    FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale)
            });
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
     * @return El JButton para buscar carritos.
     */
    public JButton getBtnBuscar() { return btnBuscar; }

    /**
     * Devuelve el componente del botón "Listar".
     *
     * @return El JButton para listar todos los carritos.
     */
    public JButton getBtnListar() { return btnListar; }

    /**
     * Devuelve el componente del botón "Ver Detalles".
     *
     * @return El JButton para ver los detalles de un carrito.
     */
    public JButton getBtnVerDetalles() { return btnVerDetalles; }

    /**
     * Devuelve el campo de texto para la búsqueda.
     *
     * @return El JTextField para la entrada de búsqueda.
     */
    public JTextField getTxtBuscar() { return txtBuscar; }

    /**
     * Devuelve el JTable que muestra los carritos.
     *
     * @return El JTable para la visualización de carritos.
     */
    public JTable getTblCarritos() { return tblCarritos; }

    /**
     * Devuelve el modelo de tabla predeterminado utilizado por la JTable de carritos.
     *
     * @return El DefaultTableModel de la tabla de carritos.
     */
    public DefaultTableModel getModelo() { return modelo; }

    /**
     * Muestra un cuadro de diálogo de mensaje al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}