package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * La clase ProductoEliminarView es un JInternalFrame que proporciona una interfaz de usuario para eliminar productos.
 * Permite al usuario buscar un producto por su código y, una vez encontrado, eliminarlo.
 * La vista soporta internacionalización para sus etiquetas de texto y los nombres de los botones.
 */
public class ProductoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;

    /**
     * Construye una nueva instancia de ProductoEliminarView.
     * Inicializa los componentes de la interfaz de usuario, configura el panel principal,
     * establece las propiedades de la ventana interna, inicializa el manejador de internacionalización,
     * actualiza los textos, configura los campos de nombre y precio como no editables y configura los iconos.
     */
    public ProductoEliminarView() {
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

        txtNombre.setEditable(false);
        txtPrecio.setEditable(false);
        configurarIconos();
    }

    /**
     * Configura los iconos para los botones en la vista.
     * Carga las imágenes desde las rutas especificadas y las escala a un tamaño consistente.
     */
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);
        btnBuscar.setIcon(cargarIcono("/imagenes/buscar-producto.png", iconSize));
        btnEliminar.setIcon(cargarIcono("/imagenes/eliminar-producto.png", iconSize));

        btnBuscar.setIconTextGap(10);
        btnEliminar.setIconTextGap(10);

        btnBuscar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnEliminar.setHorizontalTextPosition(SwingConstants.RIGHT);
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
        btnEliminar.setText(mensajeInternacionalizacion.get("producto.eliminar"));
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
     * Devuelve el componente del botón "Eliminar".
     *
     * @return El JButton para eliminar un producto.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
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
     * Muestra un cuadro de diálogo de confirmación para la eliminación de un producto.
     *
     * @return true si el usuario confirma la eliminación, false en caso contrario.
     */
    public boolean confirmarEliminacion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                mensajeInternacionalizacion.get("mensaje.confirmar_eliminacion"),
                mensajeInternacionalizacion.get("titulo.producto"),
                JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
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