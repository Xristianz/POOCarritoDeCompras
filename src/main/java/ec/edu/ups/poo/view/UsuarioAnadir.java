package ec.edu.ups.poo.view;

import ec.edu.ups.poo.models.PreguntaSeguridad;
import javax.swing.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioAnadir extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JLabel lblPreguntaActual;
    private JTextField txtRespuesta;
    private JButton btnGuardarRespuesta;
    private JButton btnAgregarUsuario;
    private JButton btnCancelar;

    private List<PreguntaSeguridad> preguntas;
    private int preguntaActualIndex;
    private int preguntasRespondidas;
    private String[] respuestasGuardadas;
    private int[] idsPreguntasRespondidas;

    public UsuarioAnadir() {
        super("Agregar Usuario", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);

        respuestasGuardadas = new String[3];
        idsPreguntasRespondidas = new int[3];
        preguntasRespondidas = 0;

        configurarEventos();
    }

    private void configurarEventos() {
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnGuardarRespuesta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        if (preguntas == null || preguntas.isEmpty()) {
            lblPreguntaActual.setText("No hay preguntas disponibles");
            return;
        }

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

    public String getNombre() {
        return txtNombre.getText();
    }

    public String getApellido() {
        return txtApellido.getText();
    }

    public String getCorreo() {
        return txtCorreo.getText();
    }

    public String getTelefono() {
        return txtTelefono.getText();
    }

    public String getFechaNacimiento() {
        return txtFechaNacimiento.getText();
    }

    public String getUsername() {
        return txtUsername.getText();
    }

    public String getContrasenia() {
        return new String(txtContrasenia.getPassword());
    }

    public String[] getRespuestasGuardadas() {
        return respuestasGuardadas;
    }

    public int[] getIdsPreguntasRespondidas() {
        return idsPreguntasRespondidas;
    }

    public int getPreguntasRespondidas() {
        return preguntasRespondidas;
    }

    public JButton getBtnAgregarUsuario() {
        return btnAgregarUsuario;
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
        txtRespuesta.setText("");

        preguntasRespondidas = 0;
        respuestasGuardadas = new String[3];
        idsPreguntasRespondidas = new int[3];

        btnGuardarRespuesta.setEnabled(true);
        txtRespuesta.setEnabled(true);

        if (preguntas != null && !preguntas.isEmpty()) {
            mostrarSiguientePregunta();
        }
    }
}