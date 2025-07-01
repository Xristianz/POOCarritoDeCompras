package ec.edu.ups.poo.view;

import javax.swing.*;

public class RegistroView extends JFrame {
    private JPanel panelPrincipal;
    private JButton cancelarButton;
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JButton btnRegistrar;
    private JTextField txtPregunta1;
    private JTextField txtPregunta2;
    private JTextField txtPregunta3;

    public RegistroView() {
        setContentPane(panelPrincipal);
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
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

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtUsername.setText("");
        txtContrasenia.setText("");
        txtPregunta1.setText("");
        txtPregunta2.setText("");
        txtPregunta3.setText("");
    }
}