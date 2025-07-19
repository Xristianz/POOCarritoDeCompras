package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.PreguntaSeguridad;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioAnadir extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JLabel lblPreguntaActual;
    private JTextField txtRespuesta;
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
    private JLabel lblUsername;
    private JLabel lblFechaDeNacimiento;

    private List<PreguntaSeguridad> preguntas;
    private int preguntaActualIndex;
    private int preguntasRespondidas;
    private String[] respuestasGuardadas;
    private int[] idsPreguntasRespondidas;
    private MensajeInternacionalizacionHandler mensajeHandler;

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
    }

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

    public void cargarPreguntas(List<PreguntaSeguridad> preguntas) {
        this.preguntas = preguntas;
        this.preguntaActualIndex = 0;
        this.preguntasRespondidas = 0;
        mostrarSiguientePregunta();
    }

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

    private boolean yaRespondida(int preguntaId) {
        for (int i = 0; i < preguntasRespondidas; i++) {
            if (idsPreguntasRespondidas[i] == preguntaId) {
                return true;
            }
        }
        return false;
    }

    public String getNombre() {
        return txtNombre.getText();
    }

    public String getApellido() {
        return txtApellido.getText();
    }

    public String getCorreo() {
        return txtCorreo.getText();
    }

    public String getTelefono() {
        return txtTelefono.getText();
    }

    public String getFechaNacimiento() {
        return txtFechaNacimiento.getText();
    }

    public String getUsername() {
        return txtUsername.getText();
    }

    public String getContrasenia() {
        return new String(txtContrasenia.getPassword());
    }

    public String[] getRespuestasGuardadas() {
        return respuestasGuardadas;
    }

    public int[] getIdsPreguntasRespondidas() {
        return idsPreguntasRespondidas;
    }

    public int getPreguntasRespondidas() {
        return preguntasRespondidas;
    }

    public JButton getBtnAgregarUsuario() {
        return btnAgregarUsuario;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

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

    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}