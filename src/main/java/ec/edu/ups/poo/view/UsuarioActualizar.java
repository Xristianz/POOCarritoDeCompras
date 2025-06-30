package ec.edu.ups.poo.view;

import javax.swing.*;

import javax.swing.*;

public class UsuarioActualizar extends JInternalFrame {
    private JTextField txtUsername;
    private JTextField txtNuevaContrasenia;
    private JButton btnActualizarContrasenia;
    private JPanel panelPrincipal;

    public UsuarioActualizar() {
        super("Actualizar Contraseña", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
    }

    public JButton getBtnActualizarContrasenia() {
        return btnActualizarContrasenia;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JTextField getTxtNuevaContrasenia() {
        return txtNuevaContrasenia;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
