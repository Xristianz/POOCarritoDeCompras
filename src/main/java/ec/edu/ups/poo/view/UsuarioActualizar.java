package ec.edu.ups.poo.view;

import ec.edu.ups.poo.models.Usuario;

import javax.swing.*;

public class UsuarioActualizar extends JInternalFrame {
    private JTextField txtUsername;
    private JPasswordField txtNuevaContrasenia;
    private JButton btnActualizar;
    private JPanel panelPrincipal;
    private JTextField txtPregunta1;
    private JTextField txtPregunta2;
    private JTextField txtPregunta3;
    private JButton btnBuscar;

    public UsuarioActualizar() {
        super("Actualizar Usuario", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtNuevaContrasenia() {
        return txtNuevaContrasenia;
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

    public void cargarDatosUsuario(Usuario usuario) {
        txtPregunta1.setText(usuario.getPregunta1());
        txtPregunta2.setText(usuario.getPregunta2());
        txtPregunta3.setText(usuario.getPregunta3());
    }
}
