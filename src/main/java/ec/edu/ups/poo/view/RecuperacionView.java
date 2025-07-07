package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

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

    private String[] preguntas;
    private String[] respuestasCorrectas;
    private int preguntaActualIndex;
    private int intentosRestantes;

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

        // Inicialmente deshabilitamos los campos hasta que se busque el usuario
        lblPregunta.setText("Ingrese su nombre de usuario y presione Buscar");
        txtRespuesta.setEnabled(false);
        txtNuevaContrasenia.setEnabled(false);
        btnVerificar.setEnabled(false);
        btnCambiarContrasenia.setEnabled(false);

        configurarIconos();
        internacionalizar();
    }
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
    private void cambiarIdioma() {
        String idioma = cmbIdioma.getSelectedItem().toString();
        if (idioma.equals("English")) {
            mensajeHandler = new MensajeInternacionalizacionHandler("en", "US");
        } else if (idioma.equals("Français")) {
            mensajeHandler = new MensajeInternacionalizacionHandler("fr", "FR");
        } else {
            mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        }
        internacionalizar();
    }
    private void internacionalizar() {
        setTitle(mensajeHandler.get("recuperacion.titulo"));
        recuperacionDeContrasenia.setText(mensajeHandler.get("recuperacion.titulo"));
        recuperacionDeContrasenia.setText(mensajeHandler.get("recuperacion.titulo"));
        preguntaDeSeguridad.setText(mensajeHandler.get("recuperacion.pregunta"));
        nuevaContrasenia.setText(mensajeHandler.get("recuperacion.nueva_contrasenia"));
        btnBuscar.setText(mensajeHandler.get("recuperacion.boton.buscar"));
        btnVerificar.setText(mensajeHandler.get("recuperacion.boton.verificar"));
        btnCambiarContrasenia.setText(mensajeHandler.get("recuperacion.boton.cambiar"));
    }


    public void cargarPreguntas(String[] preguntas, String[] respuestas) {
        this.preguntas = preguntas;
        this.respuestasCorrectas = respuestas;
        this.preguntaActualIndex = 0;
        this.intentosRestantes = 3;
        mostrarPreguntaActual();
    }

    private void mostrarPreguntaActual() {
        if (preguntas != null && preguntaActualIndex < preguntas.length) {
            lblPregunta.setText(preguntas[preguntaActualIndex]);
            txtRespuesta.setEnabled(true);
            btnVerificar.setEnabled(true);
        }
    }

    public boolean verificarRespuesta(String respuesta) {
        if (respuestasCorrectas == null || preguntaActualIndex >= respuestasCorrectas.length) {
            return false;
        }

        boolean correcta = respuesta.equals(respuestasCorrectas[preguntaActualIndex]);
        if (!correcta) {
            intentosRestantes--;
            if (intentosRestantes > 0) {
                // Rotar a la siguiente pregunta
                preguntaActualIndex = (preguntaActualIndex + 1) % preguntas.length;
                mostrarPreguntaActual();
                mostrarMensaje(mensajeHandler.get("recuperacion.respuesta_incorrecta") + " " + intentosRestantes + " " + mensajeHandler.get("recuperacion.intentos_restantes"));
            } else {
                // Reiniciar el ciclo
                preguntaActualIndex = 0;
                intentosRestantes = 3;
                mostrarPreguntaActual();
                mostrarMensaje(mensajeHandler.get("recuperacion.intentos_agotados"));
            }
        }
        return correcta;
    }
    // Getters para todos los campos
    public JTextField getTxtUsername() {
        return txtUsername;
    }


    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    public JPasswordField getTxtNuevaContrasenia() {
        return txtNuevaContrasenia;
    }

    public JButton getBtnVerificar() {
        return btnVerificar;
    }

    public JButton getBtnCambiarContrasenia() {
        return btnCambiarContrasenia;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }


    public void habilitarCambioContrasenia() {
        txtNuevaContrasenia.setEnabled(true);
        btnCambiarContrasenia.setEnabled(true);
        txtRespuesta.setEnabled(false);
        btnVerificar.setEnabled(false);
    }
    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}