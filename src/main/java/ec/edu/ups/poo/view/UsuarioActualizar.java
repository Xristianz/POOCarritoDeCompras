package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.PreguntaSeguridad;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La clase UsuarioActualizar es un JInternalFrame que permite a los usuarios actualizar su información personal
 * y, opcionalmente, cambiar su contraseña. La vista soporta internacionalización y requiere que el usuario
 * responda a tres preguntas de seguridad para proceder con la actualización o el cambio de contraseña.
 */
public class UsuarioActualizar extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;
    private JPasswordField txtNuevaContrasenia;
    private JLabel lblPreguntaActual;
    private JTextField txtRespuesta;
    private JButton btnGuardarRespuesta;
    private JButton btnActualizar;
    private JLabel actualizarUsuarios;
    private JLabel preguntasDeSeguridad;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JLabel lblApellido;
    private JLabel lblNombre;
    private JLabel lblUsername;
    private JLabel lblFechaDeNacimiento;
    private JLabel lblContraseniaNueva;
    private MensajeInternacionalizacionHandler mensajeHandler;

    private List<PreguntaSeguridad> preguntas;
    private int preguntaActualIndex;
    private int preguntasRespondidas;
    private String[] respuestasGuardadas;
    private int[] idsPreguntasRespondidas;

    /**
     * Construye una nueva instancia de UsuarioActualizar.
     * Inicializa el manejador de mensajes para internacionalización en español (Ecuador),
     * configura la apariencia del panel principal, establece el título de la ventana,
     * configura la operación de cierre predeterminada y el tamaño.
     * Deshabilita la edición de campos de datos personales, inicializa las variables para
     * el manejo de preguntas de seguridad, configura los eventos, la internacionalización
     * y los iconos.
     */
    public UsuarioActualizar() {
        super("Actualizar Usuario", true, true, false, true);
        mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        panelPrincipal.setBackground(Color.darkGray);
        panelPrincipal.setForeground(Color.WHITE);
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
            }
        }
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);

        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtCorreo.setEditable(false);
        txtTelefono.setEditable(false);
        txtFechaNacimiento.setEditable(false);

        respuestasGuardadas = new String[3];
        idsPreguntasRespondidas = new int[3];
        preguntasRespondidas = 0;

        configurarEventos();
        internacionalizar();
        configurarIconos();
    }

    /**
     * Configura los iconos para los botones en la vista.
     * Carga imágenes desde rutas predefinidas y las escala al tamaño deseado antes de asignarlas a los botones.
     * También ajusta la posición del texto en relación con el icono.
     */
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);
        btnBuscar.setIcon(cargarIcono("/imagenes/reticulo.png", iconSize));
        btnActualizar.setIcon(cargarIcono("/imagenes/nueva-cuenta.png", iconSize));
        btnGuardarRespuesta.setIcon(cargarIcono("/imagenes/agregar.png", iconSize));

        btnBuscar.setIconTextGap(10);
        btnActualizar.setIconTextGap(10);
        btnGuardarRespuesta.setIconTextGap(10);

        btnBuscar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnActualizar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnGuardarRespuesta.setHorizontalTextPosition(SwingConstants.RIGHT);
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
     * Internacionaliza los textos de todos los componentes de la interfaz de usuario.
     * Establece los textos de los labels y botones según el idioma configurado en el `mensajeHandler`.
     */
    public void internacionalizar() {
        actualizarUsuarios.setText(mensajeHandler.get("actualizar.titulo"));
        preguntasDeSeguridad.setText(mensajeHandler.get("actualizar.preguntas"));
        btnBuscar.setText(mensajeHandler.get("actualizar.boton.buscar"));
        btnActualizar.setText(mensajeHandler.get("actualizar.boton.actualizar"));
        btnGuardarRespuesta.setText(mensajeHandler.get("actualizar.boton.guardar"));
        lblUsername.setText(mensajeHandler.get("actualizar.username"));
        lblNombre.setText(mensajeHandler.get("actualizar.nombre"));
        lblApellido.setText(mensajeHandler.get("actualizar.apellido"));
        lblCorreo.setText(mensajeHandler.get("actualizar.correo"));
        lblTelefono.setText(mensajeHandler.get("actualizar.telefono"));
        lblFechaDeNacimiento.setText(mensajeHandler.get("actualizar.fecha_nacimiento"));
        lblContraseniaNueva.setText(mensajeHandler.get("actualizar.contrasenia_nueva"));


        if (preguntas != null && !preguntas.isEmpty() && preguntaActualIndex < preguntas.size() && preguntasRespondidas < 3) {
            lblPreguntaActual.setText(preguntas.get(preguntaActualIndex).getTexto(mensajeHandler));
        }
    }

    /**
     * Configura los ActionListeners para los botones de la vista.
     * Define las acciones para los botones "Buscar", "Guardar Respuesta" y "Actualizar".
     * El botón "Guardar Respuesta" maneja la lógica de responder preguntas de seguridad,
     * avanzando a la siguiente pregunta o deshabilitando los campos una vez que se han respondido 3 preguntas.
     */
    private void configurarEventos() {
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica del botón Buscar (implementada en el controlador)
            }
        });

        btnGuardarRespuesta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtRespuesta.getText().isEmpty()) {
                    mostrarMensaje(mensajeHandler.get("anadir.mensaje.respuesta_requerida"));
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
                        lblPreguntaActual.setText(mensajeHandler.get("actualizar.preguntas_completas"));
                        btnGuardarRespuesta.setEnabled(false);
                        txtRespuesta.setEnabled(false);
                    }
                }
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica del botón Actualizar (implementada en el controlador)
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
     * Si no hay preguntas disponibles o ya se han respondido 3, actualiza el JLabel y deshabilita los controles de respuesta.
     */
    private void mostrarSiguientePregunta() {
        if (preguntas == null || preguntas.isEmpty()) {
            lblPreguntaActual.setText(mensajeHandler.get("actualizar.sin_preguntas"));
            return;
        }

        if (preguntasRespondidas >= 3) {
            lblPreguntaActual.setText(mensajeHandler.get("actualizar.preguntas_completas"));
            btnGuardarRespuesta.setEnabled(false);
            txtRespuesta.setEnabled(false);
            return;
        }

        int index;
        do {
            index = (int) (Math.random() * preguntas.size());
        } while (yaRespondida(preguntas.get(index).getId()));

        preguntaActualIndex = index;
        lblPreguntaActual.setText(preguntas.get(preguntaActualIndex).getTexto(mensajeHandler));
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
     * Carga los datos de un usuario en los campos de texto correspondientes.
     *
     * @param nombre El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @param correo El correo electrónico del usuario.
     * @param telefono El número de teléfono del usuario.
     * @param fechaNacimiento La fecha de nacimiento del usuario.
     */
    public void cargarDatosUsuario(String nombre, String apellido, String correo, String telefono, String fechaNacimiento) {
        txtNombre.setText(nombre);
        txtApellido.setText(apellido);
        txtCorreo.setText(correo);
        txtTelefono.setText(telefono);
        txtFechaNacimiento.setText(fechaNacimiento);
    }

    /**
     * Devuelve el texto del campo de usuario (cédula).
     *
     * @return El String del nombre de usuario (cédula).
     */
    public String getUsername() {
        return txtUsername.getText();
    }

    /**
     * Devuelve el texto del campo de la nueva contraseña.
     *
     * @return El String de la nueva contraseña.
     */
    public String getNuevaContrasenia() {
        return new String(txtNuevaContrasenia.getPassword());
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
     * Devuelve el componente del botón "Buscar".
     *
     * @return El JButton para buscar un usuario.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Devuelve el componente del botón "Actualizar".
     *
     * @return El JButton para actualizar la información del usuario.
     */
    public JButton getBtnActualizar() {
        return btnActualizar;
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
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");
        txtNuevaContrasenia.setText("");
        txtRespuesta.setText("");

        preguntasRespondidas = 0;
        respuestasGuardadas = new String[3];
        idsPreguntasRespondidas = new int[3];

        btnGuardarRespuesta.setEnabled(true);
        txtRespuesta.setEnabled(true);
    }

    /**
     * Devuelve el campo de texto para el nombre de usuario (cédula).
     *
     * @return El JTextField para el nombre de usuario.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
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