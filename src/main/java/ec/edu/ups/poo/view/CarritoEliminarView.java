package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.controller.util.FormateadorUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.Locale;

/**
 * La clase CarritoEliminarView es un JInternalFrame que proporciona una interfaz de usuario para eliminar carritos de compras.
 * Permite al usuario ingresar el código de un carrito para eliminarlo y también actualizar la lista de carritos existentes
 * mostrada en una tabla. La vista es compatible con la internacionalización para sus etiquetas y textos de botones.
 */
public class CarritoEliminarView extends JInternalFrame {
    private JTextField txtCodigoCarrito;
    private JButton btnEliminar;
    private JButton btnActualizarLista;
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;

    private JLabel lblCodigoCarrito;

    /**
     * Construye una nueva instancia de CarritoEliminarView.
     * Inicializa los componentes de la interfaz de usuario, configura el modelo de la tabla,
     * actualiza los textos para internacionalización y configura los iconos de los botones.
     */
    public CarritoEliminarView() {
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


        if (txtCodigoCarrito == null) txtCodigoCarrito = new JTextField();
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
        btnEliminar.setIcon(cargarIcono("/imagenes/carro-vacio.png", iconSize));
        btnActualizarLista.setIcon(cargarIcono("/imagenes/lista-de-verificacion.png", iconSize));

        btnEliminar.setIconTextGap(10);
        btnActualizarLista.setIconTextGap(10);

        btnEliminar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnActualizarLista.setHorizontalTextPosition(SwingConstants.RIGHT);
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

        setTitle(mensajeInternacionalizacion.get("titulo.eliminar_carrito"));


        lblCodigoCarrito.setText(mensajeInternacionalizacion.get("carrito.codigo_carrito"));


        btnEliminar.setText(mensajeInternacionalizacion.get("carrito.eliminar"));
        btnActualizarLista.setText(mensajeInternacionalizacion.get("carrito.actualizar_lista"));


        modelo.setColumnIdentifiers(new Object[]{
                mensajeInternacionalizacion.get("carrito.columna.codigo"),
                mensajeInternacionalizacion.get("carrito.columna.fecha"),
                mensajeInternacionalizacion.get("carrito.columna.total_items"),
                mensajeInternacionalizacion.get("carrito.columna.subtotal"),
                mensajeInternacionalizacion.get("carrito.columna.total")
        });


    }

    /**
     * Devuelve el componente del botón "Eliminar".
     *
     * @return El JButton para eliminar carritos.
     */
    public JButton getBtnEliminar() { return btnEliminar; }

    /**
     * Devuelve el componente del botón "Actualizar Lista".
     *
     * @return El JButton para actualizar la lista de carritos.
     */
    public JButton getBtnActualizarLista() { return btnActualizarLista; }

    /**
     * Devuelve el campo de texto para el código del carrito.
     *
     * @return El JTextField para la entrada del código del carrito.
     */
    public JTextField getTxtCodigoCarrito() { return txtCodigoCarrito; }

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

    /**
     * Muestra un cuadro de diálogo de confirmación al usuario y devuelve la opción seleccionada.
     *
     * @param mensaje El mensaje de confirmación a mostrar.
     * @return Un entero que representa la opción seleccionada por el usuario (e.g., JOptionPane.YES_OPTION, JOptionPane.NO_OPTION).
     */
    public int mostrarConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(
                this,
                mensaje,
                mensajeInternacionalizacion.get("titulo.confirmar_eliminacion"),
                JOptionPane.YES_NO_OPTION
        );
    }

    /**
     * Limpia los campos de entrada de la vista.
     */
    public void limpiarCampos() {
        txtCodigoCarrito.setText("");
    }

    /**
     * Devuelve la instancia de MensajeInternacionalizacionHandler utilizada para la internacionalización.
     *
     * @return La instancia de MensajeInternacionalizacionHandler.
     */
    public MensajeInternacionalizacionHandler getMensajeInternacionalizacion() {
        return mensajeInternacionalizacion;
    }
}