package ec.edu.ups.poo.view;

import javax.swing.*;

public class RecuperacionView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JLabel lblPregunta;
    private JTextField txtRespuesta;
    private JPasswordField txtNuevaContrasenia;
    private JButton btnVerificar;
    private JButton btnCambiarContrasenia;
    private JButton btnBuscar;

    private String[] preguntas;
    private String[] respuestasCorrectas;
    private int preguntaActualIndex;
    private int intentosRestantes;

    public RecuperacionView() {
        setContentPane(panelPrincipal);
        setTitle("Recuperación de Contraseña");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        // Inicialmente deshabilitamos los campos hasta que se busque el usuario
        lblPregunta.setText("Ingrese su nombre de usuario y presione Buscar");
        txtRespuesta.setEnabled(false);
        txtNuevaContrasenia.setEnabled(false);
        btnVerificar.setEnabled(false);
        btnCambiarContrasenia.setEnabled(false);
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
                mostrarMensaje("Respuesta incorrecta. Te quedan " + intentosRestantes + " intentos.");
            } else {
                // Reiniciar el ciclo
                preguntaActualIndex = 0;
                intentosRestantes = 3;
                mostrarPreguntaActual();
                mostrarMensaje("Has agotado tus intentos. El ciclo de preguntas comenzará de nuevo.");
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
}