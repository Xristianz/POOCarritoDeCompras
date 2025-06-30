package ec.edu.ups.poo.view;

import javax.swing.*;


public class UsuarioAnadir extends JInternalFrame {
    private JTextField txtUsername;
    private JTextField txtContrasenia;
    private JButton btnAgregarUsuario;
    private JPanel panelPrincipal;

    public UsuarioAnadir() {
        super("Agregar Usuario", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
    }

    public JButton getBtnAgregarUsuario() {
        return btnAgregarUsuario;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JTextField getTxtContrasenia() {
        return txtContrasenia;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
