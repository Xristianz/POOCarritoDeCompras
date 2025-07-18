package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.PreguntaSeguridad;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * La clase RecuperacionView es un JFrame que proporciona una interfaz de usuario para la recuperación de contraseñas.
 * Permite a los usuarios ingresar su nombre de usuario, responder preguntas de seguridad y establecer una nueva contraseña.
 * La vista soporta internacionalización y tiene un sistema de intentos para las preguntas de seguridad.
 */
public class RecuperacionView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JLabel lblPregunta;
    private JTextField txtRespuesta;
    private JPasswordField txtNuevaContrasenia;
    private JButton btnVerificar;
    private JButton btnCambiarContrasenia;
    private JButton btnBuscar;
    private JLabel nuevaContrasenia;
    private JLabel preguntaDeSeguridad;
    private JLabel recuperacionDeContrasenia;
    private JLabel lblUsername;
    private JComboBox<String> cmbIdioma;
    private MensajeInternacionalizacionHandler mensajeHandler;

    private List<PreguntaSeguridad> preguntas;
    private String[] respuestasCorrectas;
    private int preguntaActualIndex;
    private int intentosRestantes;

    /**
     * Construye una nueva instancia de RecuperacionView.
     * Inicializa el manejador de mensajes para internacionalización en español (Ecuador),
     * configura la apariencia del panel principal, establece el título de la ventana,
     * configura la operación de cierre predeterminada, el tamaño y la ubicación.
     * También configura el combo box de idiomas y desactiva los campos y botones relacionados
     * con la respuesta y cambio de contraseña hasta que se busque un usuario.
     * Finalmente, configura los iconos y realiza la internacionalización inicial.
     */
    public RecuperacionView() {
        mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        panelPrincipal.setBackground(Color.darkGray);
        panelPrincipal.setForeground(Color.WHITE);
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
            }
        }
        setContentPane(panelPrincipal);
        setTitle(mensajeHandler.get("recuperacion.titulo"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        cmbIdioma.addItem("Español");
        cmbIdioma.addItem("English");
        cmbIdioma.addItem("Français");
        cmbIdioma.addActionListener(e -> cambiarIdioma());

        txtRespuesta.setEnabled(false);
        txtNuevaContrasenia.setEnabled(false);
        btnVerificar.setEnabled(false);
        btnCambiarContrasenia.setEnabled(false);

        configurarIconos();
        internacionalizar();
    }

    /**
     * Configura los iconos para los botones de la vista.
     * Carga imágenes desde rutas predefinidas y las escala al tamaño deseado antes de asignarlas a los botones.
     * También ajusta la posición del texto en relación con el icono.
     */
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);
        btnBuscar.setIcon(cargarIcono("/imagenes/reticulo.png", iconSize));
        btnVerificar.setIcon(cargarIcono("/imagenes/agregar.png", iconSize));
        btnCambiarContrasenia.setIcon(cargarIcono("/imagenes/restablecer-la-contrasena.png", iconSize));

        btnBuscar.setIconTextGap(10);
        btnVerificar.setIconTextGap(10);
        btnCambiarContrasenia.setIconTextGap(10);

        btnBuscar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnVerificar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnCambiarContrasenia.setHorizontalTextPosition(SwingConstants.RIGHT);
    }

    /**
     * Carga un ImageIcon desde la ruta de recurso especificada y lo escala al tamaño dado.
     *
     * @param ruta La ruta del recurso de la imagen (por ejemplo, "/imagenes/reticulo.png").
     * @param size La dimensión deseada para el icono.
     * @return Un ImageIcon escalado, o null si el recurso no se encuentra o hay un error.
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
     * Cambia el idioma de la interfaz de usuario de la ventana de recuperación de contraseña.
     * Actualiza el manejador de mensajes según la selección del JComboBox de idiomas y luego
     * llama al método de internacionalización para refrescar los textos.
     * También actualiza el texto de la pregunta de seguridad actual.
     */
    private void cambiarIdioma() {
        String idiomaSeleccionado = cmbIdioma.getSelectedItem().toString();
        if (idiomaSeleccionado.equals("English")) {
            mensajeHandler.setLenguaje("en", "US");
        } else if (idiomaSeleccionado.equals("Français")) {
            mensajeHandler.setLenguaje("fr", "FR");
        } else {
            mensajeHandler.setLenguaje("es", "EC");
        }
        internacionalizar();


        if (preguntas != null && !preguntas.isEmpty() && preguntaActualIndex < preguntas.size()) {
            lblPregunta.setText(preguntas.get(preguntaActualIndex).getTexto(mensajeHandler));
        } else {
            lblPregunta.setText(mensajeHandler.get("recuperacion.mensaje.ingresar_usuario_buscar"));
        }
    }

    /**
     * Internacionaliza los textos de todos los componentes de la interfaz de usuario.
     * Establece los textos de los labels y botones según el idioma configurado en el `mensajeHandler`.
     */
    private void internacionalizar() {
        setTitle(mensajeHandler.get("recuperacion.titulo"));
        recuperacionDeContrasenia.setText(mensajeHandler.get("recuperacion.titulo"));
        preguntaDeSeguridad.setText(mensajeHandler.get("recuperacion.pregunta"));
        nuevaContrasenia.setText(mensajeHandler.get("recuperacion.nueva_contrasenia"));
        btnBuscar.setText(mensajeHandler.get("recuperacion.boton.buscar"));
        btnVerificar.setText(mensajeHandler.get("recuperacion.boton.verificar"));
        btnCambiarContrasenia.setText(mensajeHandler.get("recuperacion.boton.cambiar"));
        lblUsername.setText(mensajeHandler.get("recuperacion.cedula"));
    }

    /**
     * Carga las preguntas de seguridad y sus respuestas correctas.
     * Inicializa el índice de la pregunta actual y el número de intentos restantes.
     * Luego, muestra la primera pregunta de seguridad.
     *
     * @param preguntas Una lista de objetos PreguntaSeguridad.
     * @param respuestas Un array de Strings con las respuestas correctas correspondientes.
     */
    public void cargarPreguntas(List<PreguntaSeguridad> preguntas, String[] respuestas) {
        this.preguntas = preguntas;
        this.respuestasCorrectas = respuestas;
        this.preguntaActualIndex = 0;
        this.intentosRestantes = 3;
        mostrarPreguntaActual();
    }

    /**
     * Muestra la pregunta de seguridad actual en el JLabel `lblPregunta`.
     * Habilita el campo de respuesta y el botón de verificación.
     */
    private void mostrarPreguntaActual() {
        if (preguntas != null && preguntaActualIndex < preguntas.size()) {
            lblPregunta.setText(preguntas.get(preguntaActualIndex).getTexto(mensajeHandler));
            txtRespuesta.setEnabled(true);
            btnVerificar.setEnabled(true);
        }
    }

    /**
     * Verifica si la respuesta proporcionada es correcta para la pregunta actual.
     * Si es incorrecta, decrementa los intentos restantes y avanza a la siguiente pregunta si hay intentos.
     * Si se agotan los intentos, reinicia el proceso de preguntas.
     *
     * @param respuesta La respuesta del usuario al campo de texto.
     * @return true si la respuesta es correcta, false en caso contrario.
     */
    public boolean verificarRespuesta(String respuesta) {
        if (respuestasCorrectas == null || preguntaActualIndex >= respuestasCorrectas.length) {
            return false;
        }

        boolean correcta = respuesta.equalsIgnoreCase(respuestasCorrectas[preguntaActualIndex]);
        if (!correcta) {
            intentosRestantes--;
            if (intentosRestantes > 0) {
                preguntaActualIndex = (preguntaActualIndex + 1) % preguntas.size();
                mostrarPreguntaActual();
                mostrarMensaje(mensajeHandler.get("recuperacion.respuesta_incorrecta") + " " + intentosRestantes + " " + mensajeHandler.get("recuperacion.intentos_restantes"));
            } else {
                preguntaActualIndex = 0;
                intentosRestantes = 3;
                mostrarPreguntaActual();
                mostrarMensaje(mensajeHandler.get("recuperacion.intentos_agotados"));
            }
        }
        return correcta;
    }

    /**
     * Devuelve el campo de texto para el nombre de usuario.
     *
     * @return El JTextField del nombre de usuario.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    /**
     * Devuelve el campo de texto para la respuesta de seguridad.
     *
     * @return El JTextField de la respuesta.
     */
    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    /**
     * Devuelve el campo de contraseña para la nueva contraseña.
     *
     * @return El JPasswordField de la nueva contraseña.
     */
    public JPasswordField getTxtNuevaContrasenia() {
        return txtNuevaContrasenia;
    }

    /**
     * Devuelve el botón de "Verificar".
     *
     * @return El JButton para verificar la respuesta de seguridad.
     */
    public JButton getBtnVerificar() {
        return btnVerificar;
    }

    /**
     * Devuelve el botón de "Cambiar Contraseña".
     *
     * @return El JButton para cambiar la contraseña.
     */
    public JButton getBtnCambiarContrasenia() {
        return btnCambiarContrasenia;
    }

    /**
     * Devuelve el botón de "Buscar".
     *
     * @return El JButton para buscar un usuario.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
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
     * Habilita los campos para cambiar la contraseña y el botón correspondiente,
     * y deshabilita los campos y botones relacionados con la verificación de preguntas.
     */
    public void habilitarCambioContrasenia() {
        txtNuevaContrasenia.setEnabled(true);
        btnCambiarContrasenia.setEnabled(true);
        txtRespuesta.setEnabled(false);
        btnVerificar.setEnabled(false);
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