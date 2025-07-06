package ec.edu.ups.poo.view;


import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.PreguntaSeguridad;

import javax.swing.*;
import java.util.List;

public class RegistroView extends JFrame {
    private JPanel panelPrincipal;
    private JButton cancelarButton;
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JButton btnRegistrar;
    private JTextField txtRespuesta;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;
    private JComboBox<String> cmbIdioma;
    private JLabel lblPreguntaActual;
    private JButton btnGuardarRespuesta;
    private MensajeInternacionalizacionHandler mensajeHandler;

    private List<PreguntaSeguridad> preguntas;
    private int preguntaActualIndex;
    private int preguntasRespondidas;
    private String[] respuestasGuardadas;
    private int[] idsPreguntasRespondidas;

    public RegistroView() {
        mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        setContentPane(panelPrincipal);
        setTitle(mensajeHandler.get("registro.titulo"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        respuestasGuardadas = new String[3];
        idsPreguntasRespondidas = new int[3];
        preguntasRespondidas = 0;

        configurarEventos();
        configurarIdiomas();
    }

    private void configurarIdiomas() {

    }

    private void configurarEventos() {
        cancelarButton.addActionListener(e -> dispose());

        btnGuardarRespuesta.addActionListener(e -> {
            if (txtRespuesta.getText().isEmpty()) {
                mostrarMensaje("Debe ingresar una respuesta");
                return;
            }

            if (preguntasRespondidas < 3) {
                respuestasGuardadas[preguntasRespondidas] = txtRespuesta.getText();
                idsPreguntasRespondidas[preguntasRespondidas] = preguntas.get(preguntaActualIndex).getId();
                preguntasRespondidas++;
                txtRespuesta.setText("");

                if (preguntasRespondidas < 3) {
                    mostrarSiguientePregunta();
                } else {
                    lblPreguntaActual.setText("Has respondido las 3 preguntas requeridas");
                    btnGuardarRespuesta.setEnabled(false);
                    txtRespuesta.setEnabled(false);
                }
            }
        });
    }

    public void cargarPreguntas(List<PreguntaSeguridad> preguntas) {
        this.preguntas = preguntas;
        this.preguntaActualIndex = 0;
        this.preguntasRespondidas = 0;
        mostrarSiguientePregunta();
    }

    private void mostrarSiguientePregunta() {
        if (preguntas == null || preguntas.isEmpty()) return;

        // Seleccionar una pregunta aleatoria que no haya sido respondida aún
        int index;
        do {
            index = (int) (Math.random() * preguntas.size());
        } while (yaRespondida(preguntas.get(index).getId()));

        preguntaActualIndex = index;
        lblPreguntaActual.setText(preguntas.get(preguntaActualIndex).getTexto());
    }

    private boolean yaRespondida(int preguntaId) {
        for (int i = 0; i < preguntasRespondidas; i++) {
            if (idsPreguntasRespondidas[i] == preguntaId) {
                return true;
            }
        }
        return false;
    }

    // Getters para los campos...
    public String[] getRespuestasGuardadas() {
        return respuestasGuardadas;
    }

    public int[] getIdsPreguntasRespondidas() {
        return idsPreguntasRespondidas;
    }

    public int getPreguntasRespondidas() {
        return preguntasRespondidas;
    }

    // Resto de getters...
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
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
        txtRespuesta.setText("");
        preguntasRespondidas = 0;
        respuestasGuardadas = new String[3];
        idsPreguntasRespondidas = new int[3];
    }
}