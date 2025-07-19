package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.DAO.PreguntaSeguridadDAO;
import ec.edu.ups.poo.DAO.RespuestaSeguridadDAO;
import ec.edu.ups.poo.DAO.UsuarioDAO;
import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.*;
import ec.edu.ups.poo.view.LoginView;
import ec.edu.ups.poo.view.RecuperacionView;
import ec.edu.ups.poo.view.RegistroView;
import ec.edu.ups.poo.view.UsuarioAnadir;
import ec.edu.ups.poo.view.UsuarioActualizar;
import ec.edu.ups.poo.view.UsuarioView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UsuarioController {
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final PreguntaSeguridadDAO preguntaSeguridadDAO;
    private final RespuestaSeguridadDAO respuestaSeguridadDAO;
    private final LoginView loginView;
    private RegistroView registroView;
    private RecuperacionView recuperacionView;
    private UsuarioAnadir usuarioAnadirView;
    private UsuarioActualizar usuarioActualizarView;
    private UsuarioView usuarioView;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public UsuarioController(UsuarioDAO usuarioDAO, PreguntaSeguridadDAO preguntaSeguridadDAO,
                             RespuestaSeguridadDAO respuestaSeguridadDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.preguntaSeguridadDAO = preguntaSeguridadDAO;
        this.respuestaSeguridadDAO = respuestaSeguridadDAO;
        this.loginView = loginView;
        this.usuario = null;
        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        configurarEventosLogin();
    }

    private void configurarEventosLogin() {
        loginView.getBtnIniciarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });

        loginView.getBtnRegistrarse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaRegistro();
            }
        });

        loginView.getOlvidoSuContraseniaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaRecuperacion();
            }
        });
    }

    private void mostrarVentanaRegistro() {
        registroView = new RegistroView();
        registroView.setVisible(true);
        registroView.cargarPreguntas(preguntaSeguridadDAO.listarTodas());

        registroView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        String username = registroView.getTxtUsername().getText();
        String contrasenia = new String(registroView.getTxtContrasenia().getPassword());
        String nombre = registroView.getTxtNombre().getText();
        String apellido = registroView.getTxtApellido().getText();
        String correo = registroView.getTxtCorreo().getText();
        String telefono = registroView.getTxtTelefono().getText();
        String fechaNacimiento = registroView.getTxtFechaNacimiento().getText();

        if (username.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ||
                correo.isEmpty() || telefono.isEmpty() || fechaNacimiento.isEmpty()) {
            registroView.mostrarMensaje(registroView.getMensajeHandler().get("mensaje.campos_requeridos"));
            return;
        }

        if (registroView.getPreguntasRespondidas() < 3) {
            registroView.mostrarMensaje(registroView.getMensajeHandler().get("mensaje.preguntas_requeridas"));
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            registroView.mostrarMensaje(registroView.getMensajeHandler().get("mensaje.usuario_existe"));
            return;
        }

        Usuario nuevoUsuario = new Usuario(
                username, contrasenia, Rol.USUARIO,
                nombre, apellido, correo, telefono, fechaNacimiento
        );

        usuarioDAO.crear(nuevoUsuario);

        String[] respuestas = registroView.getRespuestasGuardadas();
        int[] idsPreguntas = registroView.getIdsPreguntasRespondidas();

        for (int i = 0; i < 3; i++) {
            RespuestaSeguridad respuesta = new RespuestaSeguridad(0, idsPreguntas[i], username, respuestas[i]);
            respuestaSeguridadDAO.crear(respuesta);
        }

        registroView.mostrarMensaje(registroView.getMensajeHandler().get("mensaje.registro_exitoso"));
        registroView.limpiarCampos();
        registroView.dispose();
    }

    private void mostrarVentanaRecuperacion() {
        recuperacionView = new RecuperacionView();
        recuperacionView.setVisible(true);

        recuperacionView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuarioParaRecuperacion();
            }
        });

        recuperacionView.getBtnVerificar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarPreguntas();
            }
        });

        recuperacionView.getBtnCambiarContrasenia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarContrasenia();
            }
        });
    }

    private void buscarUsuarioParaRecuperacion() {
        String username = recuperacionView.getTxtUsername().getText();
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            recuperacionView.mostrarMensaje(recuperacionView.getMensajeHandler().get("mensaje.usuario_no_encontrado"));
            return;
        }

        List<RespuestaSeguridad> respuestas = respuestaSeguridadDAO.listarPorUsuario(username);
        if (respuestas.size() < 3) {
            recuperacionView.mostrarMensaje(recuperacionView.getMensajeHandler().get("mensaje.preguntas_insuficientes"));
            return;
        }

        String[] textosPreguntas = new String[3];
        String[] textosRespuestas = new String[3];

        for (int i = 0; i < 3; i++) {
            RespuestaSeguridad respuesta = respuestas.get(i);
            PreguntaSeguridad pregunta = preguntaSeguridadDAO.buscarPorId(respuesta.getPreguntaId());
            textosPreguntas[i] = pregunta.getTexto(recuperacionView.getMensajeHandler());
            textosRespuestas[i] = respuesta.getRespuesta();
        }

        recuperacionView.cargarPreguntas(textosPreguntas, textosRespuestas);
    }

    private void verificarPreguntas() {
        String username = recuperacionView.getTxtUsername().getText();
        String respuesta = recuperacionView.getTxtRespuesta().getText();

        if (respuesta.isEmpty()) {
            recuperacionView.mostrarMensaje(recuperacionView.getMensajeHandler().get("mensaje.respuesta_requerida"));
            return;
        }

        if (recuperacionView.verificarRespuesta(respuesta)) {
            recuperacionView.mostrarMensaje(recuperacionView.getMensajeHandler().get("mensaje.respuesta_correcta"));
            recuperacionView.habilitarCambioContrasenia();
        }
    }

    private void cambiarContrasenia() {
        String username = recuperacionView.getTxtUsername().getText();
        String nuevaContrasenia = new String(recuperacionView.getTxtNuevaContrasenia().getPassword());

        if (nuevaContrasenia.isEmpty()) {
            recuperacionView.mostrarMensaje(recuperacionView.getMensajeHandler().get("mensaje.contrasenia_requerida"));
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario != null) {
            usuario.setContrasenia(nuevaContrasenia);
            usuarioDAO.actualizar(usuario);
            recuperacionView.mostrarMensaje(recuperacionView.getMensajeHandler().get("mensaje.contrasenia_cambiada"));
            recuperacionView.dispose();
        } else {
            recuperacionView.mostrarMensaje(recuperacionView.getMensajeHandler().get("mensaje.usuario_no_encontrado"));
        }
    }

    public void configurarEventosUsuarioAnadir(UsuarioAnadir view) {
        this.usuarioAnadirView = view;

        view.getBtnAgregarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarUsuarioAdmin();
            }
        });

        view.cargarPreguntas(preguntaSeguridadDAO.listarTodas());
    }

    private void agregarUsuarioAdmin() {
        String username = usuarioAnadirView.getUsername();
        String contrasenia = usuarioAnadirView.getContrasenia();
        String nombre = usuarioAnadirView.getNombre();
        String apellido = usuarioAnadirView.getApellido();
        String correo = usuarioAnadirView.getCorreo();
        String telefono = usuarioAnadirView.getTelefono();
        String fechaNacimiento = usuarioAnadirView.getFechaNacimiento();

        if (username.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ||
                correo.isEmpty() || telefono.isEmpty() || fechaNacimiento.isEmpty()) {
            usuarioAnadirView.mostrarMensaje(usuarioAnadirView.getMensajeHandler().get("mensaje.campos_requeridos"));
            return;
        }

        if (usuarioAnadirView.getPreguntasRespondidas() < 3) {
            usuarioAnadirView.mostrarMensaje(usuarioAnadirView.getMensajeHandler().get("mensaje.preguntas_requeridas"));
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            usuarioAnadirView.mostrarMensaje(usuarioAnadirView.getMensajeHandler().get("mensaje.usuario_existe"));
            return;
        }

        Usuario nuevoUsuario = new Usuario(
                username, contrasenia, Rol.USUARIO,
                nombre, apellido, correo, telefono, fechaNacimiento
        );

        usuarioDAO.crear(nuevoUsuario);

        String[] respuestas = usuarioAnadirView.getRespuestasGuardadas();
        int[] idsPreguntas = usuarioAnadirView.getIdsPreguntasRespondidas();

        for (int i = 0; i < 3; i++) {
            RespuestaSeguridad respuesta = new RespuestaSeguridad(0, idsPreguntas[i], username, respuestas[i]);
            respuestaSeguridadDAO.crear(respuesta);
        }

        usuarioAnadirView.mostrarMensaje(usuarioAnadirView.getMensajeHandler().get("mensaje.usuario_agregado"));
        usuarioAnadirView.limpiarCampos();
    }

    public void configurarEventosUsuarioActualizar(UsuarioActualizar view) {
        this.usuarioActualizarView = view;

        view.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuarioParaActualizar();
            }
        });

        view.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarUsuarioAdmin();
            }
        });

        view.cargarPreguntas(preguntaSeguridadDAO.listarTodas());
    }

    private void buscarUsuarioParaActualizar() {
        String username = usuarioActualizarView.getUsername();
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            usuarioActualizarView.mostrarMensaje(usuarioActualizarView.getMensajeHandler().get("mensaje.usuario_no_encontrado"));
            return;
        }

        usuarioActualizarView.cargarDatosUsuario(
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getFechaNacimiento()
        );
    }

    private void actualizarUsuarioAdmin() {
        String username = usuarioActualizarView.getUsername();
        String nuevaContrasenia = usuarioActualizarView.getNuevaContrasenia();

        if (usuarioActualizarView.getPreguntasRespondidas() < 3) {
            usuarioActualizarView.mostrarMensaje(usuarioActualizarView.getMensajeHandler().get("mensaje.preguntas_requeridas"));
            return;
        }

        if (nuevaContrasenia.isEmpty()) {
            usuarioActualizarView.mostrarMensaje(usuarioActualizarView.getMensajeHandler().get("mensaje.contrasenia_requerida"));
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario != null) {
            usuario.setContrasenia(nuevaContrasenia);
            usuarioDAO.actualizar(usuario);

            respuestaSeguridadDAO.eliminarPorUsuario(username);

            String[] respuestas = usuarioActualizarView.getRespuestasGuardadas();
            int[] idsPreguntas = usuarioActualizarView.getIdsPreguntasRespondidas();

            for (int i = 0; i < 3; i++) {
                RespuestaSeguridad respuesta = new RespuestaSeguridad(0, idsPreguntas[i], username, respuestas[i]);
                respuestaSeguridadDAO.crear(respuesta);
            }

            usuarioActualizarView.mostrarMensaje(usuarioActualizarView.getMensajeHandler().get("mensaje.usuario_actualizado"));
            usuarioActualizarView.limpiarCampos();
        } else {
            usuarioActualizarView.mostrarMensaje(usuarioActualizarView.getMensajeHandler().get("mensaje.usuario_no_encontrado"));
        }
    }

    public void configurarEventosUsuarioView(UsuarioView view) {
        this.usuarioView = view;

        view.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarUsuarios();
            }
        });

        view.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        usuarioView.cargarDatos(usuarios);
    }

    private void eliminarUsuario() {
        String username = usuarioView.getTxtUsername().getText();

        if (username.isEmpty()) {
            usuarioView.mostrarMensaje(usuarioView.getMensajeHandler().get("mensaje.username_requerido"));
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario == null) {
            usuarioView.mostrarMensaje(usuarioView.getMensajeHandler().get("mensaje.usuario_no_encontrado"));
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                usuarioView,
                usuarioView.getMensajeHandler().get("mensaje.confirmar_eliminar") + " " + username + "?",
                usuarioView.getMensajeHandler().get("titulo.confirmar"),
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                usuarioDAO.eliminar(username);
                respuestaSeguridadDAO.eliminarPorUsuario(username);

                usuarioView.mostrarMensaje(usuarioView.getMensajeHandler().get("mensaje.usuario_eliminado"));
                usuarioView.getTxtUsername().setText("");
                listarUsuarios();
            } catch (Exception e) {
                usuarioView.mostrarMensaje(usuarioView.getMensajeHandler().get("mensaje.error_eliminar") + ": " + e.getMessage());
            }
        }
    }

    private void autenticar() {
        String username = loginView.getTxtUsername().getText();
        String contrasenia = loginView.getTxtContrasenia().getText();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje(loginView.getMensajeHandler().get("mensaje.credenciales_incorrectas"));
        } else {
            loginView.dispose();
        }
    }

    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    public void cerrarSesion() {
        this.usuario = null;
    }

}