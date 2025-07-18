package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * La clase ProductoActualizarView es un JInternalFrame que proporciona una interfaz de usuario para actualizar
 * la información de un producto. Permite al usuario buscar un producto por su código y luego modificar su nombre y precio.
 * La vista soporta internacionalización para sus etiquetas de texto y los nombres de los botones.
 */
public class ProductoActualizarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnActualizar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;

    /**
     * Construye una nueva instancia de ProductoActualizarView.
     * Inicializa los componentes de la interfaz de usuario, configura el panel principal,
     * establece las propiedades de la ventana interna, inicializa el manejador de internacionalización,
     * actualiza los textos y configura los iconos.
     */
    public ProductoActualizarView() {
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
        setSize(500, 300);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

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
        btnBuscar.setIcon(cargarIcono("/imagenes/buscar.png", iconSize));
        btnActualizar.setIcon(cargarIcono("/imagenes/agregar.png", iconSize));

        btnBuscar.setIconTextGap(10);
        btnActualizar.setIconTextGap(10);

        btnBuscar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnActualizar.setHorizontalTextPosition(SwingConstants.RIGHT);
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
     * Actualiza el título de la ventana, las etiquetas y los textos de los botones
     * según la configuración de internacionalización actual.
     */
    public void actualizarTextos() {
        this.setTitle(mensajeInternacionalizacion.get("titulo.producto"));
        lblCodigo.setText(mensajeInternacionalizacion.get("producto.codigo"));
        lblNombre.setText(mensajeInternacionalizacion.get("producto.nombre"));
        lblPrecio.setText(mensajeInternacionalizacion.get("producto.precio"));
        btnBuscar.setText(mensajeInternacionalizacion.get("producto.buscar"));
        btnActualizar.setText(mensajeInternacionalizacion.get("producto.actualizar"));
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
     * Devuelve el campo de texto para el código del producto.
     *
     * @return El JTextField para el código del producto.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Devuelve el campo de texto para el nombre del producto.
     *
     * @return El JTextField para el nombre del producto.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Devuelve el campo de texto para el precio del producto.
     *
     * @return El JTextField para el precio del producto.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Devuelve el componente del botón "Buscar".
     *
     * @return El JButton para buscar un producto.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Devuelve el componente del botón "Actualizar".
     *
     * @return El JButton para actualizar la información del producto.
     */
    public JButton getBtnActualizar() {
        return btnActualizar;
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
     * Muestra un cuadro de diálogo de mensaje al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Limpia los campos de entrada de texto en la vista.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}