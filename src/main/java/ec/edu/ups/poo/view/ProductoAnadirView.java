package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.Producto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

/**
 * La clase ProductoAnadirView es un JInternalFrame que proporciona una interfaz de usuario para añadir nuevos productos.
 * Permite al usuario ingresar el código, nombre y precio de un producto, y luego guardarlo.
 * La vista incluye botones para aceptar la entrada y limpiar los campos, y es compatible con la internacionalización
 * para sus etiquetas de texto y nombres de botones.
 */
public class ProductoAnadirView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtPrecio;
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;

    /**
     * Construye una nueva instancia de ProductoAnadirView.
     * Inicializa los componentes de la interfaz de usuario, configura el panel principal,
     * establece las propiedades de la ventana interna, inicializa el manejador de internacionalización,
     * actualiza los textos, configura un ActionListener para el botón limpiar y configura los iconos.
     */
    public ProductoAnadirView() {
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

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        configurarIconos();
    }

    /**
     * Configura los iconos para los botones en la vista.
     * Carga las imágenes desde las rutas especificadas y las escala a un tamaño consistente.
     */
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);
        btnAceptar.setIcon(cargarIcono("/imagenes/agregar.png", iconSize));
        btnLimpiar.setIcon(cargarIcono("/imagenes/pagina-web.png", iconSize));

        btnAceptar.setIconTextGap(10);
        btnLimpiar.setIconTextGap(10);

        btnAceptar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnLimpiar.setHorizontalTextPosition(SwingConstants.RIGHT);
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
        btnAceptar.setText(mensajeInternacionalizacion.get("producto.aceptar"));
        btnLimpiar.setText(mensajeInternacionalizacion.get("producto.limpiar"));
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
     * Devuelve el panel principal de la vista.
     *
     * @return El JPanel que representa el área de contenido principal.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
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
     * Devuelve el campo de texto para el nombre del producto.
     *
     * @return El JTextField para el nombre del producto.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
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
     * Devuelve el componente del botón "Aceptar".
     *
     * @return El JButton para aceptar la adición del producto.
     */
    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    /**
     * Devuelve el componente del botón "Limpiar".
     *
     * @return El JButton para limpiar los campos de entrada.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
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

    /**
     * Muestra los productos en la consola (utilizado para depuración o listado simple).
     *
     * @param productos Una lista de objetos Producto a mostrar.
     */
    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }
}