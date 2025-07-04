package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
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
    private JComboBox<String> cmbIdioma;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblFechaNacimiento;
    private JLabel lblCorreo;
    private JLabel lblTelefono;
    private JLabel lblUsuario;
    private JLabel lblContrasenia;
    private JLabel lblPreguntasSeguridad;
    private JLabel lblPregunta1;
    private JLabel lblPregunta2;
    private JLabel lblPregunta3;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public RegistroView() {
        // Configuración inicial
        mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        setContentPane(panelPrincipal);
        setTitle(mensajeHandler.get("registro.titulo")); // Usar clave de properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Configurar eventos
        configurarEventos();
        configurarIdiomas();
    }

    private void configurarEventos() {
        cancelarButton.addActionListener(e -> dispose());
    }

    private void configurarIdiomas() {
        cmbIdioma.setModel(new DefaultComboBoxModel<>(new String[]{"Español", "English", "Français"}));

        cmbIdioma.addActionListener(e -> {
            String idioma = (String) cmbIdioma.getSelectedItem();
            switch (idioma) {
                case "Español" -> cambiarIdioma("es", "EC");
                case "English" -> cambiarIdioma("en", "US");
                case "Français" -> cambiarIdioma("fr", "FR");
            }
        });
    }

    private void cambiarIdioma(String lenguaje, String pais) {
        mensajeHandler.setLenguaje(lenguaje, pais);
        setTitle(mensajeHandler.get("registro.titulo"));

        // Actualizar todos los textos
        cancelarButton.setText(mensajeHandler.get("registro.boton.cancelar"));
        btnRegistrar.setText(mensajeHandler.get("registro.boton.registrar"));
        lblNombre.setText(mensajeHandler.get("registro.label.nombre"));
        lblApellido.setText(mensajeHandler.get("registro.label.apellido"));
        lblFechaNacimiento.setText(mensajeHandler.get("registro.label.fecha_nacimiento"));
        lblCorreo.setText(mensajeHandler.get("registro.label.correo"));
        lblTelefono.setText(mensajeHandler.get("registro.label.telefono"));
        lblUsuario.setText(mensajeHandler.get("registro.label.usuario"));
        lblContrasenia.setText(mensajeHandler.get("registro.label.contrasenia"));
        lblPreguntasSeguridad.setText(mensajeHandler.get("registro.label.preguntas_seguridad"));
        lblPregunta1.setText(mensajeHandler.get("registro.label.pregunta1"));
        lblPregunta2.setText(mensajeHandler.get("registro.label.pregunta2"));
        lblPregunta3.setText(mensajeHandler.get("registro.label.pregunta3"));

        // Actualizar textos de botones
        btnRegistrar.setText(mensajeHandler.get("registro.boton.registrar"));
        cancelarButton.setText(mensajeHandler.get("registro.boton.cancelar"));

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