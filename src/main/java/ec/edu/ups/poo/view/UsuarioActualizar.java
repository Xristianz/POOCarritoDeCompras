package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.PreguntaSeguridad;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
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
    private JLabel actualizarUsuarios;
    private JLabel preguntasDeSeguridad;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JLabel lblApellido;
    private JLabel lblNombre;
    private JLabel lblUsername;
    private JLabel lblFechaDeNacimiento;
    private JLabel lblContraseniaNueva;
    private MensajeInternacionalizacionHandler mensajeHandler;

    private List<PreguntaSeguridad> preguntas;
    private int preguntaActualIndex;
    private int preguntasRespondidas;
    private String[] respuestasGuardadas;
    private int[] idsPreguntasRespondidas;

    public UsuarioActualizar() {
        super("Actualizar Usuario", true, true, false, true);
        mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        panelPrincipal.setBackground(Color.darkGray);
        panelPrincipal.setForeground(Color.WHITE);
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
            }
        }
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
        internacionalizar();
        configurarIconos();
    }
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);
        btnBuscar.setIcon(cargarIcono("/imagenes/reticulo.png", iconSize));
        btnActualizar.setIcon(cargarIcono("/imagenes/nueva-cuenta.png", iconSize));
        btnGuardarRespuesta.setIcon(cargarIcono("/imagenes/agregar.png", iconSize));

        btnBuscar.setIconTextGap(10);
        btnActualizar.setIconTextGap(10);
        btnGuardarRespuesta.setIconTextGap(10);

        btnBuscar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnActualizar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnGuardarRespuesta.setHorizontalTextPosition(SwingConstants.RIGHT);
    }

    private ImageIcon cargarIcono(String ruta, Dimension size) {
        try {
            URL imgURL = getClass().getResource(ruta);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    public void internacionalizar() {
        actualizarUsuarios.setText(mensajeHandler.get("actualizar.titulo"));
        preguntasDeSeguridad.setText(mensajeHandler.get("actualizar.preguntas"));
        btnBuscar.setText(mensajeHandler.get("actualizar.boton.buscar"));
        btnActualizar.setText(mensajeHandler.get("actualizar.boton.actualizar"));
        btnGuardarRespuesta.setText(mensajeHandler.get("actualizar.boton.guardar"));
        lblUsername.setText(mensajeHandler.get("actualizar.username"));
        lblNombre.setText(mensajeHandler.get("actualizar.nombre"));
        lblApellido.setText(mensajeHandler.get("actualizar.apellido"));
        lblCorreo.setText(mensajeHandler.get("actualizar.correo"));
        lblTelefono.setText(mensajeHandler.get("actualizar.telefono"));
        lblFechaDeNacimiento.setText(mensajeHandler.get("actualizar.fecha_nacimiento"));
        lblContraseniaNueva.setText(mensajeHandler.get("actualizar.contrasenia_nueva"));
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
        this.respuestasGuardadas = new String[3];
        this.idsPreguntasRespondidas = new int[3];

        // Reiniciar estado de los componentes
        txtRespuesta.setEnabled(true);
        btnGuardarRespuesta.setEnabled(true);
        txtRespuesta.setText("");

        mostrarSiguientePregunta();
    }

    private void mostrarSiguientePregunta() {
        if (preguntas == null || preguntas.isEmpty()) {
            lblPreguntaActual.setText(mensajeHandler.get("actualizar.sin_preguntas"));
            return;
        }

        if (preguntasRespondidas >= 3) {
            lblPreguntaActual.setText(mensajeHandler.get("actualizar.preguntas_completas"));
            btnGuardarRespuesta.setEnabled(false);
            txtRespuesta.setEnabled(false);
            return;
        }

        int index;
        do {
            index = (int) (Math.random() * preguntas.size());
        } while (yaRespondida(preguntas.get(index).getId()));

        preguntaActualIndex = index;
        lblPreguntaActual.setText(preguntas.get(preguntaActualIndex).getTexto(mensajeHandler));
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
    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}