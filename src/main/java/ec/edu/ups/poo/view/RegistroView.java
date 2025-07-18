package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.PreguntaSeguridad;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * La clase RegistroView es un JFrame que proporciona una interfaz de usuario para el registro de nuevos usuarios.
 * Permite a los usuarios ingresar su cédula (tratada como nombre de usuario), contraseña, nombre, apellido,
 * correo electrónico, teléfono y fecha de nacimiento. Además, el proceso de registro incluye responder a tres
 * preguntas de seguridad seleccionadas aleatoriamente. La vista soporta internacionalización y ofrece opciones
 * para registrar y cancelar el proceso.
 */
public class RegistroView extends JFrame {

    private JPanel panelPrincipal;
    private JButton cancelarButton;
    private JTextField txtUsername; // Ahora representa la Cédula
    private JPasswordField txtContrasenia;
    private JButton btnRegistrar;
    private JTextField txtRespuesta;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;
    private JComboBox<String> cmbIdioma;
    private JLabel lblPreguntaActual;
    private JButton btnGuardarRespuesta;
    private JLabel preguntasDeSeguridad;
    private JLabel lblNumeroTelefonico;
    private JLabel lblIngreseSuUsuario; // Ahora representa la etiqueta de Cédula
    private JLabel lblIngreseSuContrasenia;
    private JLabel lblCorreo;
    private JLabel lblFechaDeNacimiento;
    private JLabel lblApellido;
    private JLabel lblNombre;
    private JLabel lblCrearCuenta;
    private MensajeInternacionalizacionHandler mensajeHandler;

    private List<PreguntaSeguridad> preguntas;
    private int preguntaActualIndex;
    private int preguntasRespondidas;
    private String[] respuestasGuardadas;
    private int[] idsPreguntasRespondidas;


    /**
     * Construye una nueva instancia de RegistroView.
     * Inicializa el manejador de mensajes para internacionalización en español (Ecuador),
     * configura la apariencia del panel principal, establece el título de la ventana,
     * configura la operación de cierre predeterminada, el tamaño y la ubicación.
     * Inicializa las variables para el manejo de preguntas de seguridad,
     * configura los iconos, los eventos, la internacionalización inicial y el combo box de idiomas.
     */
    public RegistroView() {
        mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        panelPrincipal.setBackground(Color.darkGray);
        panelPrincipal.setForeground(Color.WHITE);
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
            }
        }
        setContentPane(panelPrincipal);
        setTitle(mensajeHandler.get("registro.titulo"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        respuestasGuardadas = new String[3];
        idsPreguntasRespondidas = new int[3];
        preguntasRespondidas = 0;

        configurarIconos();
        configurarEventos();
        internacionalizar();
        configurarComboBoxIdioma();
    }

    /**
     * Configura los iconos para los botones de la vista.
     * Carga imágenes desde rutas predefinidas y las escala al tamaño deseado antes de asignarlas a los botones.
     * También ajusta la posición del texto en relación con el icono.
     */
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);
        btnRegistrar.setIcon(cargarIcono("/imagenes/agregar-usuario.png", iconSize));
        cancelarButton.setIcon(cargarIcono("/imagenes/revolver.png", iconSize));
        btnGuardarRespuesta.setIcon(cargarIcono("/imagenes/agregar.png", iconSize));

        btnRegistrar.setIconTextGap(10);
        cancelarButton.setIconTextGap(10);
        btnGuardarRespuesta.setIconTextGap(10);

        btnRegistrar.setHorizontalTextPosition(SwingConstants.RIGHT);
        cancelarButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnGuardarRespuesta.setHorizontalTextPosition(SwingConstants.RIGHT);
    }

    /**
     * Carga un ImageIcon desde la ruta de recurso especificada y lo escala al tamaño dado.
     *
     * @param ruta La ruta del recurso de la imagen (por ejemplo, "/imagenes/agregar-usuario.png").
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
     * Configura el JComboBox de selección de idioma con las opciones "Español", "English" y "Français".
     * Añade un ActionListener que cambia el idioma de la interfaz cuando se selecciona una opción diferente,
     * y actualiza el texto de la pregunta de seguridad actual.
     */
    private void configurarComboBoxIdioma() {
        cmbIdioma.addItem("Español");
        cmbIdioma.addItem("English");
        cmbIdioma.addItem("Français");

        cmbIdioma.addActionListener(e -> {
            String idiomaSeleccionado = cmbIdioma.getSelectedItem().toString();
            if (idiomaSeleccionado.equals("English")) {
                mensajeHandler.setLenguaje("en", "US");
            } else if (idiomaSeleccionado.equals("Français")) {
                mensajeHandler.setLenguaje("fr", "FR");
            } else {
                mensajeHandler.setLenguaje("es", "EC");
            }
            internacionalizar();


            if (preguntas != null && !preguntas.isEmpty() && preguntaActualIndex < preguntas.size() && preguntasRespondidas < 3) {
                lblPreguntaActual.setText(preguntas.get(preguntaActualIndex).getTexto(mensajeHandler));
            }
        });
    }

    /**
     * Internacionaliza los textos de todos los componentes de la interfaz de usuario.
     * Establece los textos de los labels y botones según el idioma configurado en el `mensajeHandler`.
     */
    private void internacionalizar() {
        setTitle(mensajeHandler.get("registro.titulo"));
        lblCrearCuenta.setText(mensajeHandler.get("registro.titulo"));
        preguntasDeSeguridad.setText(mensajeHandler.get("registro.preguntas"));
        btnRegistrar.setText(mensajeHandler.get("registro.boton.registrar"));
        btnGuardarRespuesta.setText(mensajeHandler.get("registro.boton.guardar"));
        cancelarButton.setText(mensajeHandler.get("registro.boton.cancelar"));
        lblNombre.setText(mensajeHandler.get("registro.nombre"));
        lblApellido.setText(mensajeHandler.get("registro.apellido"));
        lblIngreseSuUsuario.setText(mensajeHandler.get("registro.cedula")); // Se cambió a Cédula
        lblIngreseSuContrasenia.setText(mensajeHandler.get("registro.contrasenia"));
        lblCorreo.setText(mensajeHandler.get("registro.correo"));
        lblNumeroTelefonico.setText(mensajeHandler.get("registro.telefono"));
        lblFechaDeNacimiento.setText(mensajeHandler.get("registro.fecha_nacimiento"));
    }

    /**
     * Configura los ActionListeners para los botones de la vista.
     * El botón "Cancelar" cierra la ventana.
     * El botón "Guardar Respuesta" guarda la respuesta actual y avanza a la siguiente pregunta de seguridad
     * hasta que se hayan respondido tres preguntas.
     */
    private void configurarEventos() {
        cancelarButton.addActionListener(e -> dispose());

        btnGuardarRespuesta.addActionListener(e -> {
            if (txtRespuesta.getText().isEmpty()) {
                mostrarMensaje("Debe ingresar una respuesta");
                return;
            }

            if (preguntasRespondidas < 3) {
                respuestasGuardadas[preguntasRespondidas] = txtRespuesta.getText();
                idsPreguntasRespondidas[preguntasRespondidas] = preguntas.get(preguntaActualIndex).getId();
                preguntasRespondidas++;
                txtRespuesta.setText("");

                if (preguntasRespondidas < 3) {
                    mostrarSiguientePregunta();
                } else {
                    lblPreguntaActual.setText("Has respondido las 3 preguntas requeridas");
                    btnGuardarRespuesta.setEnabled(false);
                    txtRespuesta.setEnabled(false);
                }
            }
        });
    }

    /**
     * Carga la lista de preguntas de seguridad disponibles.
     * Reinicia el estado de las respuestas guardadas y las preguntas respondidas.
     * Habilita el campo de respuesta y el botón "Guardar Respuesta", y muestra la primera pregunta.
     *
     * @param preguntas La lista de objetos PreguntaSeguridad a utilizar.
     */
    public void cargarPreguntas(List<PreguntaSeguridad> preguntas) {
        this.preguntas = preguntas;
        this.preguntaActualIndex = 0;
        this.preguntasRespondidas = 0;
        this.respuestasGuardadas = new String[3];
        this.idsPreguntasRespondidas = new int[3];


        txtRespuesta.setEnabled(true);
        btnGuardarRespuesta.setEnabled(true);
        txtRespuesta.setText("");

        mostrarSiguientePregunta();
    }

    /**
     * Muestra la siguiente pregunta de seguridad disponible en el JLabel `lblPreguntaActual`.
     * Selecciona una pregunta al azar que no haya sido respondida previamente.
     * Habilita el campo de respuesta y el botón "Guardar Respuesta".
     * Si no hay preguntas disponibles o ya se han respondido 3, actualiza el JLabel.
     */
    private void mostrarSiguientePregunta() {
        if (preguntas == null || preguntas.isEmpty()) {
            lblPreguntaActual.setText(mensajeHandler.get("registro.sin_preguntas"));
            return;
        }


        if (preguntasRespondidas >= 3) {
            lblPreguntaActual.setText(mensajeHandler.get("registro.preguntas_completas"));
            return;
        }


        int index;
        do {
            index = (int) (Math.random() * preguntas.size());
        } while (yaRespondida(preguntas.get(index).getId()));

        preguntaActualIndex = index;

        lblPreguntaActual.setText(preguntas.get(preguntaActualIndex).getTexto(mensajeHandler));


        txtRespuesta.setEnabled(true);
        btnGuardarRespuesta.setEnabled(true);
    }

    /**
     * Verifica si una pregunta de seguridad con el ID dado ya ha sido respondida.
     *
     * @param preguntaId El ID de la pregunta a verificar.
     * @return true si la pregunta ya ha sido respondida, false en caso contrario.
     */
    private boolean yaRespondida(int preguntaId) {
        for (int i = 0; i < preguntasRespondidas; i++) {
            if (idsPreguntasRespondidas[i] == preguntaId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve el array de respuestas guardadas a las preguntas de seguridad.
     *
     * @return Un array de String con las respuestas.
     */
    public String[] getRespuestasGuardadas() {
        return respuestasGuardadas;
    }

    /**
     * Devuelve el array de IDs de las preguntas de seguridad respondidas.
     *
     * @return Un array de int con los IDs de las preguntas.
     */
    public int[] getIdsPreguntasRespondidas() {
        return idsPreguntasRespondidas;
    }

    /**
     * Devuelve el número de preguntas de seguridad que han sido respondidas.
     *
     * @return El número de preguntas respondidas.
     */
    public int getPreguntasRespondidas() {
        return preguntasRespondidas;
    }

    /**
     * Devuelve el campo de texto que representa la Cédula (anteriormente Username).
     *
     * @return El JTextField para la cédula.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    /**
     * Devuelve el campo de contraseña.
     *
     * @return El JPasswordField de la contraseña.
     */
    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    /**
     * Devuelve el botón de "Registrar".
     *
     * @return El JButton de registro.
     */
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    /**
     * Devuelve el campo de texto para el nombre.
     *
     * @return El JTextField para el nombre.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Devuelve el campo de texto para el apellido.
     *
     * @return El JTextField para el apellido.
     */
    public JTextField getTxtApellido() {
        return txtApellido;
    }

    /**
     * Devuelve el campo de texto para el correo electrónico.
     *
     * @return El JTextField para el correo electrónico.
     */
    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    /**
     * Devuelve el campo de texto para el número de teléfono.
     *
     * @return El JTextField para el teléfono.
     */
    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    /**
     * Devuelve el campo de texto para la fecha de nacimiento.
     *
     * @return El JTextField para la fecha de nacimiento.
     */
    public JTextField getTxtFechaNacimiento() {
        return txtFechaNacimiento;
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
     * Limpia todos los campos de entrada de texto y reinicia el estado de las preguntas de seguridad.
     */
    public void limpiarCampos() {
        txtUsername.setText("");
        txtContrasenia.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");
        txtRespuesta.setText("");
        preguntasRespondidas = 0;
        respuestasGuardadas = new String[3];
        idsPreguntasRespondidas = new int[3];
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