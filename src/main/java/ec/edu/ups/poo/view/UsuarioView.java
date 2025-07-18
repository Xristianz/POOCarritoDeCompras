package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * La clase UsuarioView es un JInternalFrame que proporciona una interfaz para la gestión de usuarios.
 * Muestra una lista de usuarios en una tabla, y permite al administrador listar todos los usuarios
 * y eliminar usuarios seleccionados. La vista soporta internacionalización para sus etiquetas de texto,
 * nombres de botones y encabezados de tabla.
 */
public class UsuarioView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblUsuarios;
    private JTextField txtUsername;
    private JButton btnListar;
    private JButton btnEliminar;
    private JLabel listaDeUsuarios;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Construye una nueva instancia de UsuarioView.
     * Inicializa el manejador de mensajes para internacionalización en español (Ecuador),
     * configura el panel principal, establece el título de la ventana y su tamaño.
     * Inicializa el modelo de la tabla con columnas predefinidas,
     * y luego configura los iconos y realiza la internacionalización inicial.
     */
    public UsuarioView() {
        super("Gestión de Usuarios", true, true, false, true);
        mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 400);

        modelo = new DefaultTableModel();

        Object[] columnas = {"Username", "Nombre", "Apellido", "Correo", "Teléfono", "Fecha Nacimiento", "Rol"};
        modelo.setColumnIdentifiers(columnas);
        tblUsuarios.setModel(modelo);

        configurarIconos();
        internacionalizar();
    }

    /**
     * Configura los iconos para los botones de la vista.
     * Carga imágenes desde rutas predefinidas y las escala al tamaño deseado antes de asignarlas a los botones.
     * También ajusta la posición del texto en relación con el icono y establece tooltips.
     */
    private void configurarIconos() {
        Dimension iconSize = new Dimension(25, 25);

        ImageIcon listarIcon = cargarIcono("/imagenes/revision-positiva.png", iconSize);
        ImageIcon eliminarIcon = cargarIcono("/imagenes/reticulo.png", iconSize);


        btnListar.setIcon(listarIcon);
        btnEliminar.setIcon(eliminarIcon);


        btnListar.setIconTextGap(10);
        btnEliminar.setIconTextGap(10);


        btnListar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnEliminar.setHorizontalTextPosition(SwingConstants.RIGHT);


        btnListar.setToolTipText("Listar todos los usuarios");
        btnEliminar.setToolTipText("Eliminar usuario seleccionado");
    }

    /**
     * Carga un ImageIcon desde la ruta de recurso especificada y lo escala al tamaño dado.
     *
     * @param ruta La ruta del recurso de la imagen (por ejemplo, "/imagenes/revision-positiva.png").
     * @param size La dimensión deseada para el icono.
     * @return Un ImageIcon escalado, o null si el recurso no se encuentra o hay un error.
     */
    private ImageIcon cargarIcono(String ruta, Dimension size) {
        try {
            // Cargar la imagen
            URL imgURL = getClass().getResource(ruta);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);

                Image scaledImage = originalIcon.getImage().getScaledInstance(
                        size.width, size.height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } else {
                System.err.println("No se encontró el archivo de icono: " + ruta);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el icono: " + e.getMessage());
            return null;
        }
    }

    /**
     * Internacionaliza los textos de todos los componentes de la interfaz de usuario.
     * Establece los textos de la etiqueta de título, botones y los encabezados de las columnas de la tabla
     * según el idioma configurado en el `mensajeHandler`.
     * También ajusta el color de fondo y de primer plano del panel principal y sus etiquetas.
     */
    public void internacionalizar() {
        listaDeUsuarios.setText(mensajeHandler.get("usuario.lista"));
        btnListar.setText(mensajeHandler.get("usuario.boton.listar"));
        btnEliminar.setText(mensajeHandler.get("usuario.boton.eliminar"));
        panelPrincipal.setBackground(Color.darkGray);
        panelPrincipal.setForeground(Color.WHITE);
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
            }
        }

        Object[] columnas = {
                mensajeHandler.get("usuario.columna.username"),
                mensajeHandler.get("usuario.columna.nombre"),
                mensajeHandler.get("usuario.columna.apellido"),
                mensajeHandler.get("usuario.columna.correo"),
                mensajeHandler.get("usuario.columna.telefono"),
                mensajeHandler.get("usuario.columna.fecha_nacimiento"),
                mensajeHandler.get("usuario.columna.rol")
        };
        modelo.setColumnIdentifiers(columnas);
    }

    /**
     * Carga los datos de una lista de usuarios en la tabla.
     * La tabla se vacía antes de añadir los nuevos datos.
     *
     * @param usuarios La lista de objetos Usuario a mostrar en la tabla.
     */
    public void cargarDatos(List<Usuario> usuarios) {
        modelo.setRowCount(0);
        for (Usuario u : usuarios) {
            Object[] fila = {
                    u.getUsername(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getCorreo(),
                    u.getTelefono(),
                    u.getFechaNacimiento(),
                    u.getRol().toString()
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Devuelve la JTable que muestra los usuarios.
     *
     * @return La JTable para la visualización de usuarios.
     */
    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    /**
     * Devuelve el componente del botón "Listar".
     *
     * @return El JButton para listar usuarios.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Devuelve el componente del botón "Eliminar".
     *
     * @return El JButton para eliminar usuarios.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Devuelve el modelo de tabla predeterminado utilizado por la JTable de usuarios.
     *
     * @return El DefaultTableModel de la tabla de usuarios.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Devuelve el campo de texto para el nombre de usuario (utilizado posiblemente para filtrar o buscar).
     *
     * @return El JTextField para el nombre de usuario.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
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
     * Devuelve la instancia de MensajeInternacionalizacionHandler utilizada para la internacionalización.
     *
     * @return La instancia de MensajeInternacionalizacionHandler.
     */
    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}