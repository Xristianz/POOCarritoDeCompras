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
 * La clase UsuarioAnadir es un JInternalFrame que proporciona una interfaz de usuario para añadir nuevos usuarios.
 * Permite a los usuarios ingresar su información personal como nombre, apellido, correo electrónico, teléfono,
 * fecha de nacimiento, y su cédula (que actúa como nombre de usuario) junto con una contraseña. Para completar el registro,
 * el usuario debe responder a tres preguntas de seguridad seleccionadas aleatoriamente.
 * La vista soporta internacionalización para sus etiquetas de texto y los nombres de los botones.
 */
public class UsuarioAnadir extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;
    private JTextField txtUsername;
    private JTextField txtRespuesta; // Ahora representa la Cédula
    private JPasswordField txtContrasenia;
    private JLabel lblPreguntaActual;
    private JButton btnGuardarRespuesta;
    private JButton btnAgregarUsuario;
    private JButton btnCancelar;
    private JLabel agregarUsuarios;
    private JLabel preguntasDeSeguridad;
    private JLabel lblContrasenia;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JLabel lblApelido;
    private JLabel lblNombre;
    private JLabel lblUsername; // Ahora representa la etiqueta de Cédula
    private JLabel lblFechaDeNacimiento;

    private List<PreguntaSeguridad> preguntas;
    private int preguntaActualIndex;
    private int preguntasRespondidas;
    private String[] respuestasGuardadas;
    private int[] idsPreguntasRespondidas;
    private MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Construye una nueva instancia de UsuarioAnadir.
     * Inicializa el manejador de mensajes para internacionalización en español (Ecuador),
     * configura la apariencia del panel principal, establece el título de la ventana,
     * configura la operación de cierre predeterminada y el tamaño.
     * Inicializa las variables para el manejo de preguntas de seguridad,
     * y luego configura la internacionalización, los eventos y los iconos.
     */
    public UsuarioAnadir() {
        super("Agregar Usuario", true, true, false, true);
        panelPrincipal.setBackground(Color.darkGray);
        panelPrincipal.setForeground(Color.WHITE);
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
            }
        }
        mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);

        respuestasGuardadas = new String[3];
        idsPreguntasRespondidas = new int[3];
        preguntasRespondidas = 0;

        internacionalizar();
        configurarEventos();
        configurarIconos();
    }

    /**
     * Configura los iconos para los botones en la vista.
     * Carga imágenes desde rutas predefinidas y las escala al tamaño deseado antes de asignarlas a los botones.
     * También ajusta la posición del texto en relación con el icono y establece tooltips.
     */
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);

        ImageIcon agregarIcon = cargarIcono("/imagenes/agregar-usuario.png", iconSize);
        ImageIcon cancelarIcon = cargarIcono("/imagenes/revolver.png", iconSize);
        ImageIcon guardarIcon = cargarIcono("/imagenes/agregar.png", iconSize);


        btnAgregarUsuario.setIcon(agregarIcon);
        btnCancelar.setIcon(cancelarIcon);
        btnGuardarRespuesta.setIcon(guardarIcon);


        btnAgregarUsuario.setIconTextGap(10);
        btnCancelar.setIconTextGap(10);
        btnGuardarRespuesta.setIconTextGap(10);


        btnAgregarUsuario.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnCancelar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnGuardarRespuesta.setHorizontalTextPosition(SwingConstants.RIGHT);

        btnAgregarUsuario.setToolTipText("Guardar nuevo usuario");
        btnCancelar.setToolTipText("Cancelar operación");
        btnGuardarRespuesta.setToolTipText("Guardar respuesta de seguridad");
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
     * Establece los textos de los labels y botones según el idioma configurado en el `mensajeHandler`.
     */
    public void internacionalizar() {
        setTitle(mensajeHandler.get("anadir.titulo"));
        agregarUsuarios.setText(mensajeHandler.get("anadir.titulo"));
        preguntasDeSeguridad.setText(mensajeHandler.get("anadir.preguntas"));
        btnAgregarUsuario.setText(mensajeHandler.get("anadir.boton.agregar"));
        btnCancelar.setText(mensajeHandler.get("anadir.boton.cancelar"));
        btnGuardarRespuesta.setText(mensajeHandler.get("anadir.boton.guardar"));
        lblUsername.setText(mensajeHandler.get("anadir.username"));
        lblNombre.setText(mensajeHandler.get("anadir.nombre"));
        lblApelido.setText(mensajeHandler.get("anadir.apellido"));
        lblCorreo.setText(mensajeHandler.get("anadir.correo"));
        lblTelefono.setText(mensajeHandler.get("anadir.telefono"));
        lblFechaDeNacimiento.setText(mensajeHandler.get("anadir.fecha_nacimiento"));
        lblContrasenia.setText(mensajeHandler.get("anadir.contrasenia"));


        if (preguntas != null && !preguntas.isEmpty() && preguntaActualIndex < preguntas.size() && preguntasRespondidas < 3) {
            lblPreguntaActual.setText(preguntas.get(preguntaActualIndex).getTexto(mensajeHandler));
        }
    }

    /**
     * Configura los ActionListeners para los botones de la vista.
     * El botón "Cancelar" cierra la ventana.
     * El botón "Guardar Respuesta" guarda la respuesta actual y avanza a la siguiente pregunta de seguridad
     * hasta que se hayan respondido tres preguntas.
     */
    private void configurarEventos() {
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
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
                        lblPreguntaActual.setText(mensajeHandler.get("anadir.mensaje.preguntas_completas"));
                        btnGuardarRespuesta.setEnabled(false);
                        txtRespuesta.setEnabled(false);
                    }
                }
            }
        });
    }

    /**
     * Carga la lista de preguntas de seguridad disponibles.
     * Reinicia el estado de las preguntas respondidas y muestra la primera pregunta.
     *
     * @param preguntas La lista de objetos PreguntaSeguridad a utilizar.
     */
    public void cargarPreguntas(List<PreguntaSeguridad> preguntas) {
        this.preguntas = preguntas;
        this.preguntaActualIndex = 0;
        this.preguntasRespondidas = 0;
        mostrarSiguientePregunta();
    }

    /**
     * Muestra la siguiente pregunta de seguridad disponible en el JLabel `lblPreguntaActual`.
     * Selecciona una pregunta al azar que no haya sido respondida previamente.
     * Si no hay preguntas disponibles o ya se han respondido 3, actualiza el JLabel y deshabilita los controles de respuesta.
     */
    private void mostrarSiguientePregunta() {
        if (preguntas == null || preguntas.isEmpty()) {
            lblPreguntaActual.setText(mensajeHandler.get("anadir.sin_preguntas"));
            return;
        }

        if (preguntasRespondidas >= 3) {
            lblPreguntaActual.setText(mensajeHandler.get("anadir.preguntas_completas"));
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
     * Devuelve el texto del campo de nombre.
     *
     * @return El String del nombre.
     */
    public String getNombre() {
        return txtNombre.getText();
    }

    /**
     * Devuelve el texto del campo de apellido.
     *
     * @return El String del apellido.
     */
    public String getApellido() {
        return txtApellido.getText();
    }

    /**
     * Devuelve el texto del campo de correo electrónico.
     *
     * @return El String del correo electrónico.
     */
    public String getCorreo() {
        return txtCorreo.getText();
    }

    /**
     * Devuelve el texto del campo de teléfono.
     *
     * @return El String del teléfono.
     */
    public String getTelefono() {
        return txtTelefono.getText();
    }

    /**
     * Devuelve el texto del campo de fecha de nacimiento.
     *
     * @return El String de la fecha de nacimiento.
     */
    public String getFechaNacimiento() {
        return txtFechaNacimiento.getText();
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
     * Devuelve el texto del campo de contraseña.
     *
     * @return El String de la contraseña.
     */
    public String getContrasenia() {
        return new String(txtContrasenia.getPassword());
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
     * Devuelve el componente del botón "Agregar Usuario".
     *
     * @return El JButton para añadir un nuevo usuario.
     */
    public JButton getBtnAgregarUsuario() {
        return btnAgregarUsuario;
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
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");
        txtUsername.setText("");
        txtContrasenia.setText("");
        txtRespuesta.setText("");

        preguntasRespondidas = 0;
        respuestasGuardadas = new String[3];
        idsPreguntasRespondidas = new int[3];

        btnGuardarRespuesta.setEnabled(true);
        txtRespuesta.setEnabled(true);

        if (preguntas != null && !preguntas.isEmpty()) {
            mostrarSiguientePregunta();
        }
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