package ec.edu.ups.poo.view;

import javax.swing.*;


import ec.edu.ups.poo.models.PreguntaSeguridad;
import javax.swing.*;
import java.util.List;

public class UsuarioAnadir extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JComboBox<PreguntaSeguridad> cmbPregunta1; // Cambiado a PreguntaSeguridad
    private JTextField txtRespuesta1;
    private JComboBox<PreguntaSeguridad> cmbPregunta2; // Cambiado a PreguntaSeguridad
    private JTextField txtRespuesta2;
    private JComboBox<PreguntaSeguridad> cmbPregunta3; // Cambiado a PreguntaSeguridad
    private JTextField txtRespuesta3;
    private JButton btnAgregarUsuario;
    private JButton btnCancelar;

        public UsuarioAnadir() {
            super("Agregar Usuario", true, true, false, true);
            setContentPane(panelPrincipal);
            setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
            setSize(500, 500);
        }

    // Getters actualizados
    public JComboBox<PreguntaSeguridad> getCmbPregunta1() {
        return cmbPregunta1;
    }

    public JComboBox<PreguntaSeguridad> getCmbPregunta2() {
        return cmbPregunta2;
    }

    public JComboBox<PreguntaSeguridad> getCmbPregunta3() {
        return cmbPregunta3;
    }

    public JTextField getTxtRespuesta1() {
        return txtRespuesta1;
    }

    public JTextField getTxtRespuesta2() {
        return txtRespuesta2;
    }

    public JTextField getTxtRespuesta3() {
        return txtRespuesta3;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
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

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public JButton getBtnAgregarUsuario() {
        return btnAgregarUsuario;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
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
        txtRespuesta1.setText("");
        txtRespuesta2.setText("");
        txtRespuesta3.setText("");
        cmbPregunta1.setSelectedIndex(0);
        cmbPregunta2.setSelectedIndex(0);
        cmbPregunta3.setSelectedIndex(0);
    }
    public void cargarPreguntas(List<PreguntaSeguridad> preguntas) {
        DefaultComboBoxModel<PreguntaSeguridad> modeloPregunta1 = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<PreguntaSeguridad> modeloPregunta2 = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<PreguntaSeguridad> modeloPregunta3 = new DefaultComboBoxModel<>();

        for (PreguntaSeguridad pregunta : preguntas) {
            modeloPregunta1.addElement(pregunta);
            modeloPregunta2.addElement(pregunta);
            modeloPregunta3.addElement(pregunta);
        }

        cmbPregunta1.setModel(modeloPregunta1);
        cmbPregunta2.setModel(modeloPregunta2);
        cmbPregunta3.setModel(modeloPregunta3);
    }
}