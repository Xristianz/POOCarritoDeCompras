package ec.edu.ups.poo.view;


import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.PreguntaSeguridad;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class RegistroView extends JFrame {
    private JPanel panelPrincipal;
    private JButton cancelarButton;
    private JTextField txtUsername;
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
    private JLabel lblIngreseSuUsuario;
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

    private void internacionalizar() {
        setTitle(mensajeHandler.get("registro.titulo"));
        lblCrearCuenta.setText(mensajeHandler.get("registro.titulo"));
        preguntasDeSeguridad.setText(mensajeHandler.get("registro.preguntas"));
        btnRegistrar.setText(mensajeHandler.get("registro.boton.registrar"));
        btnGuardarRespuesta.setText(mensajeHandler.get("registro.boton.guardar"));
        cancelarButton.setText(mensajeHandler.get("registro.boton.cancelar"));
        lblNombre.setText(mensajeHandler.get("registro.nombre"));
        lblApellido.setText(mensajeHandler.get("registro.apellido"));
        lblIngreseSuUsuario.setText(mensajeHandler.get("registro.usuario"));
        lblIngreseSuContrasenia.setText(mensajeHandler.get("registro.contrasenia"));
        lblCorreo.setText(mensajeHandler.get("registro.correo"));
        lblNumeroTelefonico.setText(mensajeHandler.get("registro.telefono"));
        lblFechaDeNacimiento.setText(mensajeHandler.get("registro.fecha_nacimiento"));
    }
    private void configurarComboBoxIdioma() {
        cmbIdioma.addItem("Español");
        cmbIdioma.addItem("English");
        cmbIdioma.addItem("Français");

        cmbIdioma.addActionListener(e -> {
            String idioma = cmbIdioma.getSelectedItem().toString();
            if (idioma.equals("English")) {
                mensajeHandler = new MensajeInternacionalizacionHandler("en", "US");
            } else if (idioma.equals("Français")) {
                mensajeHandler = new MensajeInternacionalizacionHandler("fr", "FR");
            } else {
                mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
            }
            internacionalizar();

        });
    }

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

    public void cargarPreguntas(List<PreguntaSeguridad> preguntas) {
        this.preguntas = preguntas;
        this.preguntaActualIndex = 0;
        this.preguntasRespondidas = 0;
        this.respuestasGuardadas = new String[3];
        this.idsPreguntasRespondidas = new int[3];

        // Reiniciar estado de los componentes
        txtRespuesta.setEnabled(true);
        btnGuardarRespuesta.setEnabled(true);
        txtRespuesta.setText("");

        mostrarSiguientePregunta();
    }

    private void mostrarSiguientePregunta() {
        if (preguntas == null || preguntas.isEmpty()) {
            lblPreguntaActual.setText(mensajeHandler.get("registro.sin_preguntas"));
            return;
        }

        // Verificar si ya respondió todas las preguntas
        if (preguntasRespondidas >= 3) {
            lblPreguntaActual.setText(mensajeHandler.get("registro.preguntas_completas"));
            return;
        }

        // Seleccionar pregunta aleatoria no respondida
        int index;
        do {
            index = (int) (Math.random() * preguntas.size());
        } while (yaRespondida(preguntas.get(index).getId()));

        preguntaActualIndex = index;
        // Actualizar el label con la pregunta traducida
        lblPreguntaActual.setText(preguntas.get(preguntaActualIndex).getTexto(mensajeHandler));

        // Habilitar campos necesarios
        txtRespuesta.setEnabled(true);
        btnGuardarRespuesta.setEnabled(true);
    }

    private boolean yaRespondida(int preguntaId) {
        for (int i = 0; i < preguntasRespondidas; i++) {
            if (idsPreguntasRespondidas[i] == preguntaId) {
                return true;
            }
        }
        return false;
    }

    // Getters para los campos...
    public String[] getRespuestasGuardadas() {
        return respuestasGuardadas;
    }

    public int[] getIdsPreguntasRespondidas() {
        return idsPreguntasRespondidas;
    }

    public int getPreguntasRespondidas() {
        return preguntasRespondidas;
    }

    // Resto de getters...
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtApellido() {
        return txtApellido;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtFechaNacimiento() {
        return txtFechaNacimiento;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

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
    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}