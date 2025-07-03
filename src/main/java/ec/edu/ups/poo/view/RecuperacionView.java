package ec.edu.ups.poo.view;

import javax.swing.*;

public class RecuperacionView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JLabel lblPregunta1;
    private JTextField txtRespuesta1;
    private JLabel lblPregunta2;
    private JTextField txtRespuesta2;
    private JLabel lblPregunta3;
    private JTextField txtRespuesta3;
    private JPasswordField txtNuevaContrasenia;
    private JButton btnVerificar;
    private JButton btnCambiarContrasenia;
    private JButton btnBuscar;

    public RecuperacionView() {
        setContentPane(panelPrincipal);
        setTitle("Recuperación de Contraseña");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Inicialmente deshabilitamos los campos hasta que se busque el usuario
        lblPregunta1.setText("Pregunta 1");
        lblPregunta2.setText("Pregunta 2");
        lblPregunta3.setText("Pregunta 3");
        txtRespuesta1.setEnabled(false);
        txtRespuesta2.setEnabled(false);
        txtRespuesta3.setEnabled(false);
        txtNuevaContrasenia.setEnabled(false);
        btnVerificar.setEnabled(false);
        btnCambiarContrasenia.setEnabled(false);
    }

    public void cargarPreguntas(String pregunta1, String pregunta2, String pregunta3) {
        lblPregunta1.setText(pregunta1);
        lblPregunta2.setText(pregunta2);
        lblPregunta3.setText(pregunta3);

        // Habilitar campos después de cargar las preguntas
        txtRespuesta1.setEnabled(true);
        txtRespuesta2.setEnabled(true);
        txtRespuesta3.setEnabled(true);
        txtNuevaContrasenia.setEnabled(true);
        btnVerificar.setEnabled(true);
        btnCambiarContrasenia.setEnabled(true);
    }

    public void limpiarCampos() {
        txtRespuesta1.setText("");
        txtRespuesta2.setText("");
        txtRespuesta3.setText("");
        txtNuevaContrasenia.setText("");
    }

    // Getters para todos los campos
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JLabel getLblPregunta1() {
        return lblPregunta1;
    }

    public JTextField getTxtRespuesta1() {
        return txtRespuesta1;
    }

    public JLabel getLblPregunta2() {
        return lblPregunta2;
    }

    public JTextField getTxtRespuesta2() {
        return txtRespuesta2;
    }

    public JLabel getLblPregunta3() {
        return lblPregunta3;
    }

    public JTextField getTxtRespuesta3() {
        return txtRespuesta3;
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
}