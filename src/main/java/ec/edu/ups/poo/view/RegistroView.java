package ec.edu.ups.poo.view;

import ec.edu.ups.poo.models.PreguntaSeguridad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RegistroView extends JFrame {
    private JPanel panelPrincipal;
    private JButton cancelarButton;
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JButton btnRegistrar;
    private JComboBox<PreguntaSeguridad> cmbPregunta1;
    private JTextField txtRespuesta1;
    private JComboBox<PreguntaSeguridad> cmbPregunta2;
    private JTextField txtRespuesta2;
    private JComboBox<PreguntaSeguridad> cmbPregunta3;
    private JTextField txtRespuesta3;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;

    public RegistroView() {
        setContentPane(panelPrincipal);
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    // Getters para todos los campos
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JComboBox<PreguntaSeguridad> getCmbPregunta1() {
        return cmbPregunta1;
    }

    public JTextField getTxtRespuesta1() {
        return txtRespuesta1;
    }

    public JComboBox<PreguntaSeguridad> getCmbPregunta2() {
        return cmbPregunta2;
    }

    public JTextField getTxtRespuesta2() {
        return txtRespuesta2;
    }

    public JComboBox<PreguntaSeguridad> getCmbPregunta3() {
        return cmbPregunta3;
    }

    public JTextField getTxtRespuesta3() {
        return txtRespuesta3;
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
        txtRespuesta1.setText("");
        txtRespuesta2.setText("");
        txtRespuesta3.setText("");
        cmbPregunta1.setSelectedIndex(0);
        cmbPregunta2.setSelectedIndex(0);
        cmbPregunta3.setSelectedIndex(0);
    }

    public void cargarPreguntas(List<PreguntaSeguridad> preguntas) {
        // Crear modelos independientes para cada ComboBox
        DefaultComboBoxModel<PreguntaSeguridad> modelo1 = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<PreguntaSeguridad> modelo2 = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<PreguntaSeguridad> modelo3 = new DefaultComboBoxModel<>();

        for (PreguntaSeguridad pregunta : preguntas) {
            // Crear nuevas instancias para cada modelo
            modelo1.addElement(new PreguntaSeguridad(pregunta.getId(), pregunta.getTexto()));
            modelo2.addElement(new PreguntaSeguridad(pregunta.getId(), pregunta.getTexto()));
            modelo3.addElement(new PreguntaSeguridad(pregunta.getId(), pregunta.getTexto()));
        }

        cmbPregunta1.setModel(modelo1);
        cmbPregunta2.setModel(modelo2);
        cmbPregunta3.setModel(modelo3);
    }
}