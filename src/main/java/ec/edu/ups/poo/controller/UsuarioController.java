package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.DAO.UsuarioDAO;
import ec.edu.ups.poo.models.Rol;
import ec.edu.ups.poo.models.Usuario;
import ec.edu.ups.poo.view.LoginView;
import ec.edu.ups.poo.view.RecuperacionView;
import ec.edu.ups.poo.view.RegistroView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioController {
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private RegistroView registroView;
    private RecuperacionView recuperacionView;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
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

        loginView.getOlvidoSuContraeñaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaRecuperacion();
            }
        });
    }

    private void mostrarVentanaRegistro() {
        registroView = new RegistroView();
        registroView.setVisible(true);

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

    private void registrarUsuario() {
        String username = registroView.getTxtUsername().getText();
        String contrasenia = new String(registroView.getTxtContrasenia().getPassword());
        String pregunta1 = registroView.getTxtPregunta1().getText();
        String pregunta2 = registroView.getTxtPregunta2().getText();
        String pregunta3 = registroView.getTxtPregunta3().getText();

        if (username.isEmpty() || contrasenia.isEmpty() || pregunta1.isEmpty() || pregunta2.isEmpty() || pregunta3.isEmpty()) {
            registroView.mostrarMensaje("Todos los campos son requeridos");
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            registroView.mostrarMensaje("El usuario ya existe");
            return;
        }

        Usuario nuevoUsuario = new Usuario(username, contrasenia, Rol.USUARIO, pregunta1, pregunta2, pregunta3);
        usuarioDAO.crear(nuevoUsuario);
        registroView.mostrarMensaje("Usuario registrado exitosamente");
        registroView.limpiarCampos();
        registroView.dispose();
    }

    private void verificarPreguntas() {
        String username = recuperacionView.getTxtUsername().getText();
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            recuperacionView.mostrarMensaje("Usuario no encontrado");
            return;
        }

        String pregunta1 = recuperacionView.getTxtPregunta1().getText();
        String pregunta2 = recuperacionView.getTxtPregunta2().getText();
        String pregunta3 = recuperacionView.getTxtPregunta3().getText();

        if (pregunta1.equals(usuario.getPregunta1()) &&
                pregunta2.equals(usuario.getPregunta2()) &&
                pregunta3.equals(usuario.getPregunta3())) {
            recuperacionView.mostrarMensaje("Preguntas verificadas correctamente. Ahora puede cambiar su contraseña.");
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