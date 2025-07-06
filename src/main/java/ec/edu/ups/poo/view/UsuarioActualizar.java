package ec.edu.ups.poo.view;

import ec.edu.ups.poo.models.PreguntaSeguridad;
import javax.swing.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioActualizar extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;
    private JPasswordField txtNuevaContrasenia;
    private JLabel lblPreguntaActual;
    private JTextField txtRespuesta;
    private JButton btnGuardarRespuesta;
    private JButton btnActualizar;

    private List<PreguntaSeguridad> preguntas;
    private int preguntaActualIndex;
    private int preguntasRespondidas;
    private String[] respuestasGuardadas;
    private int[] idsPreguntasRespondidas;

    public UsuarioActualizar() {
        super("Actualizar Usuario", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);

        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtCorreo.setEditable(false);
        txtTelefono.setEditable(false);
        txtFechaNacimiento.setEditable(false);

        respuestasGuardadas = new String[3];
        idsPreguntasRespondidas = new int[3];
        preguntasRespondidas = 0;

        configurarEventos();
    }

    private void configurarEventos() {
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementar búsqueda de usuario
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

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementar actualización de usuario
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

    public void cargarDatosUsuario(String nombre, String apellido, String correo, String telefono, String fechaNacimiento) {
        txtNombre.setText(nombre);
        txtApellido.setText(apellido);
        txtCorreo.setText(correo);
        txtTelefono.setText(telefono);
        txtFechaNacimiento.setText(fechaNacimiento);
    }

    public String getUsername() {
        return txtUsername.getText();
    }

    public String getNuevaContrasenia() {
        return new String(txtNuevaContrasenia.getPassword());
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

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtUsername.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");
        txtNuevaContrasenia.setText("");
        txtRespuesta.setText("");

        preguntasRespondidas = 0;
        respuestasGuardadas = new String[3];
        idsPreguntasRespondidas = new int[3];

        btnGuardarRespuesta.setEnabled(true);
        txtRespuesta.setEnabled(true);
    }
}