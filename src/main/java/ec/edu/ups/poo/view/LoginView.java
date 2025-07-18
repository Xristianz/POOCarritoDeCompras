package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * La clase LoginView es un JFrame que representa la interfaz de usuario para el inicio de sesión de la aplicación.
 * Permite a los usuarios ingresar su nombre de usuario y contraseña, con opciones para iniciar sesión, registrarse o recuperar la contraseña.
 * La vista también incluye una funcionalidad de cambio de idioma para internacionalización, permitiendo al usuario seleccionar entre español, inglés y francés.
 */
public class LoginView extends JFrame {
    private JPanel panelSecundario;
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JButton olvidoSuContraseniaButton;
    private JLabel lblUsuario;
    private JLabel lblContrasenia;
    private JComboBox<String> cmbIdioma;
    private JLabel lblIniciarSesion;

    private MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Construye una nueva instancia de LoginView.
     * Inicializa el manejador de mensajes para internacionalización en español (Ecuador),
     * configura la apariencia del panel principal, establece el título de la ventana,
     * configura la operación de cierre predeterminada, el tamaño y la ubicación,
     * y luego inicializa la configuración de idiomas y los iconos.
     */
    public LoginView() {
        mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setForeground(Color.BLACK);

        setTitle(mensajeHandler.get("login.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        configurarIdiomas();
        configurarIconos();
    }

    /**
     * Configura los iconos para los botones de la vista.
     * Carga imágenes desde rutas predefinidas y las escala al tamaño deseado antes de asignarlas a los botones.
     * También ajusta la posición del texto en relación con el icono.
     */
    private void configurarIconos() {
        Dimension iconSize = new Dimension(25, 25);

        ImageIcon loginIcon = cargarIcono("/imagenes/iniciar-sesion.png", iconSize);
        ImageIcon registerIcon = cargarIcono("/imagenes/agregar-usuario.png", iconSize);
        ImageIcon forgotIcon = cargarIcono("/imagenes/restablecer-la-contrasena.png", iconSize);


        btnIniciarSesion.setIcon(loginIcon);
        btnRegistrarse.setIcon(registerIcon);
        olvidoSuContraseniaButton.setIcon(forgotIcon);


        btnIniciarSesion.setIconTextGap(10);
        btnRegistrarse.setIconTextGap(10);
        olvidoSuContraseniaButton.setIconTextGap(10);

        btnIniciarSesion.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnRegistrarse.setHorizontalTextPosition(SwingConstants.RIGHT);
        olvidoSuContraseniaButton.setHorizontalTextPosition(SwingConstants.RIGHT);
    }

    /**
     * Carga un ImageIcon desde la ruta de recurso especificada y lo escala al tamaño dado.
     *
     * @param ruta La ruta del recurso de la imagen (por ejemplo, "/imagenes/iniciar-sesion.png").
     * @param size La dimensión deseada para el icono.
     * @return Un ImageIcon escalado, o null si el recurso no se encuentra o hay un error.
     */
    private ImageIcon cargarIcono(String ruta, Dimension size) {
        try {
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
     * Configura el JComboBox de selección de idioma con las opciones "Español", "English" y "Français".
     * Añade un ActionListener que cambia el idioma de la interfaz cuando se selecciona una opción diferente.
     */
    private void configurarIdiomas() {
        cmbIdioma.setModel(new DefaultComboBoxModel<>(new String[]{"Español", "English", "Français"}));
        cmbIdioma.addActionListener(e -> {
            String idioma = (String) cmbIdioma.getSelectedItem();
            switch (idioma) {
                case "Español" -> cambiarIdioma("es", "EC");
                case "English" -> cambiarIdioma("en", "US");
                case "Français" -> cambiarIdioma("fr", "FR");
            }
        });
    }

    /**
     * Cambia el idioma de la interfaz de usuario de la ventana de inicio de sesión.
     * Actualiza el título de la ventana, las etiquetas y los textos de los botones
     * utilizando el MensajeInternacionalizacionHandler.
     *
     * @param lenguaje El código de lenguaje (por ejemplo, "es", "en", "fr").
     * @param pais El código de país (por ejemplo, "EC", "US", "FR").
     */
    private void cambiarIdioma(String lenguaje, String pais) {
        mensajeHandler.setLenguaje(lenguaje, pais);
        setTitle(mensajeHandler.get("login.titulo"));

        lblUsuario.setText(mensajeHandler.get("login.usuario"));
        lblContrasenia.setText(mensajeHandler.get("login.contrasenia"));
        btnIniciarSesion.setText(mensajeHandler.get("login.boton.iniciar"));
        btnRegistrarse.setText(mensajeHandler.get("login.boton.registrar"));
        olvidoSuContraseniaButton.setText(mensajeHandler.get("login.boton.recuperar"));
    }

    /**
     * Devuelve el panel principal de la vista de inicio de sesión.
     * @return El JPanel principal.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    /**
     * Establece el panel principal de la vista de inicio de sesión.
     * @param panelPrincipal El JPanel a establecer como panel principal.
     */
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    /**
     * Devuelve el campo de texto para el nombre de usuario.
     * @return El JTextField del nombre de usuario.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    /**
     * Establece el campo de texto para el nombre de usuario.
     * @param txtUsername El JTextField a establecer para el nombre de usuario.
     */
    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    /**
     * Devuelve el campo de contraseña.
     * @return El JPasswordField de la contraseña.
     */
    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    /**
     * Establece el campo de contraseña.
     * @param txtContrasenia El JPasswordField a establecer para la contraseña.
     */
    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    /**
     * Devuelve el botón de "Iniciar Sesión".
     * @return El JButton de inicio de sesión.
     */
    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    /**
     * Establece el botón de "Iniciar Sesión".
     * @param btnIniciarSesion El JButton a establecer para iniciar sesión.
     */
    public void setBtnIniciarSesion(JButton btnIniciarSesion) {
        this.btnIniciarSesion = btnIniciarSesion;
    }

    /**
     * Devuelve el botón de "Registrarse".
     * @return El JButton de registro.
     */
    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    /**
     * Establece el botón de "Registrarse".
     * @param btnRegistrarse El JButton a establecer para registrarse.
     */
    public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.btnRegistrarse = btnRegistrarse;
    }

    /**
     * Limpia los campos de texto del nombre de usuario y la contraseña.
     */
    public void limpiarCampos() {
        txtUsername.setText("");
        txtContrasenia.setText("");
    }

    /**
     * Muestra un cuadro de diálogo de confirmación para el registro de un nuevo usuario.
     * @return true si el usuario confirma el registro, false en caso contrario.
     */
    public boolean confirmarRegistro() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Registrar nuevo usuario con estos datos?",
                "Confirmar registro",
                JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

    /**
     * Devuelve el botón de "Olvidó su contraseña".
     * @return El JButton para recuperar la contraseña.
     */
    public JButton getOlvidoSuContraseniaButton() {
        return olvidoSuContraseniaButton;
    }

    /**
     * Establece el botón de "Olvidó su contraseña".
     * @param olvidoSuContraseniaButton El JButton a establecer para recuperar la contraseña.
     */
    public void setOlvidoSuContraseniaButton(JButton olvidoSuContraseniaButton) {
        this.olvidoSuContraseniaButton = olvidoSuContraseniaButton;
    }

    /**
     * Muestra un cuadro de diálogo de mensaje al usuario.
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Devuelve la instancia de MensajeInternacionalizacionHandler utilizada para la internacionalización.
     * @return La instancia de MensajeInternacionalizacionHandler.
     */
    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}