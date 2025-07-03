package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.DAO.PreguntaSeguridadDAO;
import ec.edu.ups.poo.DAO.UsuarioDAO;
import ec.edu.ups.poo.models.*;
import ec.edu.ups.poo.view.LoginView;
import ec.edu.ups.poo.view.RecuperacionView;
import ec.edu.ups.poo.view.RegistroView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioController {
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final PreguntaSeguridadDAO preguntaSeguridadDAO;
    private final LoginView loginView;
    private RegistroView registroView;
    private RecuperacionView recuperacionView;

    public UsuarioController(UsuarioDAO usuarioDAO, PreguntaSeguridadDAO preguntaSeguridadDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.preguntaSeguridadDAO = preguntaSeguridadDAO;
        this.loginView = loginView;
        this.usuario = null;
        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
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
            recuperacionView.mostrarMensaje("Usuario no encontrado");
            return;
        }

        // Cargar las preguntas del usuario
        PreguntaSeguridad pregunta1 = preguntaSeguridadDAO.buscarPorId(usuario.getPregunta1Id());
        PreguntaSeguridad pregunta2 = preguntaSeguridadDAO.buscarPorId(usuario.getPregunta2Id());
        PreguntaSeguridad pregunta3 = preguntaSeguridadDAO.buscarPorId(usuario.getPregunta3Id());

        recuperacionView.cargarPreguntas(
                pregunta1.getTexto(),
                pregunta2.getTexto(),
                pregunta3.getTexto()
        );
    }

    private void verificarPreguntas() {
        String username = recuperacionView.getTxtUsername().getText();
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            recuperacionView.mostrarMensaje("Usuario no encontrado");
            return;
        }

        String respuesta1 = recuperacionView.getTxtRespuesta1().getText();
        String respuesta2 = recuperacionView.getTxtRespuesta2().getText();
        String respuesta3 = recuperacionView.getTxtRespuesta3().getText();

        if (respuesta1.equals(usuario.getRespuesta1()) &&
                respuesta2.equals(usuario.getRespuesta2()) &&
                respuesta3.equals(usuario.getRespuesta3())) {
            recuperacionView.mostrarMensaje("Respuestas correctas. Ahora puede cambiar su contraseña.");
        } else {
            recuperacionView.mostrarMensaje("Respuestas incorrectas");
        }
    }

    private void cambiarContrasenia() {
        String username = recuperacionView.getTxtUsername().getText();
        String nuevaContrasenia = new String(recuperacionView.getTxtNuevaContrasenia().getPassword());

        if (nuevaContrasenia.isEmpty()) {
            recuperacionView.mostrarMensaje("Ingrese una nueva contraseña");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario != null) {
            usuario.setContrasenia(nuevaContrasenia);
            usuarioDAO.actualizar(usuario);
            recuperacionView.mostrarMensaje("Contraseña cambiada exitosamente");
            recuperacionView.dispose();
        } else {
            recuperacionView.mostrarMensaje("Usuario no encontrado");
        }
    }

    private void registrarUsuario() {
        String username = registroView.getTxtUsername().getText();
        String contrasenia = new String(registroView.getTxtContrasenia().getPassword());
        String nombre = registroView.getTxtNombre().getText();
        String apellido = registroView.getTxtApellido().getText();
        String correo = registroView.getTxtCorreo().getText();
        String telefono = registroView.getTxtTelefono().getText();
        String fechaNacimiento = registroView.getTxtFechaNacimiento().getText();

        PreguntaSeguridad pregunta1 = (PreguntaSeguridad) registroView.getCmbPregunta1().getSelectedItem();
        String respuesta1 = registroView.getTxtRespuesta1().getText();
        PreguntaSeguridad pregunta2 = (PreguntaSeguridad) registroView.getCmbPregunta2().getSelectedItem();
        String respuesta2 = registroView.getTxtRespuesta2().getText();
        PreguntaSeguridad pregunta3 = (PreguntaSeguridad) registroView.getCmbPregunta3().getSelectedItem();
        String respuesta3 = registroView.getTxtRespuesta3().getText();

        if (username.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ||
                correo.isEmpty() || telefono.isEmpty() || fechaNacimiento.isEmpty() ||
                respuesta1.isEmpty() || respuesta2.isEmpty() || respuesta3.isEmpty()) {
            registroView.mostrarMensaje("Todos los campos son requeridos");
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            registroView.mostrarMensaje("El usuario ya existe");
            return;
        }
        if(pregunta1.getId() == pregunta2.getId() ||
                pregunta1.getId() == pregunta3.getId() ||
                pregunta2.getId() == pregunta3.getId()) {
            registroView.mostrarMensaje("Debe seleccionar preguntas diferentes");
            return;
        }

        Usuario nuevoUsuario = new Usuario(
                username, contrasenia, Rol.USUARIO,
                nombre, apellido, correo, telefono, fechaNacimiento,
                pregunta1.getId(), respuesta1,
                pregunta2.getId(), respuesta2,
                pregunta3.getId(), respuesta3
        );

        usuarioDAO.crear(nuevoUsuario);
        registroView.mostrarMensaje("Usuario registrado exitosamente");
        registroView.limpiarCampos();
        registroView.dispose();
    }





    private void autenticar() {
        String username = loginView.getTxtUsername().getText();
        String contrasenia = loginView.getTxtContrasenia().getText();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje("Usuario o contraseña incorrectos.");
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