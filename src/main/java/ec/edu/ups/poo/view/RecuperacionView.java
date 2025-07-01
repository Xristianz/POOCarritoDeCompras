package ec.edu.ups.poo.view;

import javax.swing.*;

public class RecuperacionView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JTextField txtPregunta1;
    private JTextField txtPregunta2;
    private JTextField txtPregunta3;
    private JPasswordField txtNuevaContrasenia;
    private JButton btnVerificar;
    private JButton btnCambiarContrasenia;

    public RecuperacionView() {
        setContentPane(panelPrincipal);
        setTitle("Recuperación de Contraseña");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JTextField getTxtPregunta1() {
        return txtPregunta1;
    }

    public JTextField getTxtPregunta2() {
        return txtPregunta2;
    }

    public JTextField getTxtPregunta3() {
        return txtPregunta3;
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

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
