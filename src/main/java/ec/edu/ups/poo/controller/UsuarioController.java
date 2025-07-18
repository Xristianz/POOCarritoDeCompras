package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.DAO.PreguntaSeguridadDAO;
import ec.edu.ups.poo.DAO.RespuestaSeguridadDAO;
import ec.edu.ups.poo.DAO.UsuarioDAO;
import ec.edu.ups.poo.controller.util.Excepciones.*;
import ec.edu.ups.poo.models.*;
import ec.edu.ups.poo.view.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Controlador principal para todas las operaciones relacionadas con los usuarios.
 * <p>
 * Gestiona la lógica de negocio para la autenticación, registro de nuevos usuarios,
 * recuperación de contraseñas y las operaciones de administración de usuarios (CRUD).
 * Actúa como intermediario entre las vistas de usuario y la capa de acceso a datos.
 * </p>
 */
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

    /**
     * Construye el controlador de usuario.
     *
     * @param usuarioDAO            DAO para la persistencia de datos de Usuario.
     * @param preguntaSeguridadDAO  DAO para acceder a las preguntas de seguridad.
     * @param respuestaSeguridadDAO DAO para la persistencia de las respuestas de seguridad.
     * @param loginView             La vista principal de inicio de sesión que este controlador gestiona.
     */
    public UsuarioController(UsuarioDAO usuarioDAO, PreguntaSeguridadDAO preguntaSeguridadDAO,
                             RespuestaSeguridadDAO respuestaSeguridadDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.preguntaSeguridadDAO = preguntaSeguridadDAO;
        this.respuestaSeguridadDAO = respuestaSeguridadDAO;
        this.loginView = loginView;
        this.usuario = null;
        configurarEventosLogin();
    }

    /**
     * Configura los listeners de eventos para los botones de la vista de Login.
     */
    private void configurarEventosLogin() {
        loginView.getBtnIniciarSesion().addActionListener(e -> autenticar());
        loginView.getBtnRegistrarse().addActionListener(e -> mostrarVentanaRegistro());
        loginView.getOlvidoSuContraseniaButton().addActionListener(e -> mostrarVentanaRecuperacion());
    }

    /**
     * Procesa el intento de inicio de sesión del usuario.
     * Autentica las credenciales contra el DAO y actualiza el estado del usuario en sesión.
     */
    private void autenticar() {
        try {
            String username = loginView.getTxtUsername().getText();
            String contrasenia = new String(loginView.getTxtContrasenia().getPassword());
            this.usuario = usuarioDAO.autenticar(username, contrasenia);
            loginView.dispose();
        } catch (CredencialesInvalidasException e) {
            String msg = loginView.getMensajeHandler().get(e.getMessage());
            loginView.mostrarMensaje(msg);
        }
    }

    /**
     * Muestra la ventana de registro para un nuevo usuario.
     */
    private void mostrarVentanaRegistro() {
        registroView = new RegistroView();
        registroView.setVisible(true);
        registroView.cargarPreguntas(preguntaSeguridadDAO.listarTodas());
        registroView.getBtnRegistrar().addActionListener(e -> registrarUsuario());
    }

    /**
     * Valida los datos ingresados en la vista de registro, y si son correctos,
     * crea un nuevo usuario y sus respuestas de seguridad.
     */
    private void registrarUsuario() {
        try {
            String username = registroView.getTxtUsername().getText().trim();
            String contrasenia = new String(registroView.getTxtContrasenia().getPassword());
            String nombre = registroView.getTxtNombre().getText().trim();
            String apellido = registroView.getTxtApellido().getText().trim();
            String correo = registroView.getTxtCorreo().getText().trim();
            String telefono = registroView.getTxtTelefono().getText().trim();
            String fechaNacimiento = registroView.getTxtFechaNacimiento().getText().trim();

            if (nombre.isEmpty()) throw new ValidacionException("mensaje.nombre_vacio");
            if (apellido.isEmpty()) throw new ValidacionException("mensaje.apellido_vacio");
            if (correo.isEmpty()) throw new ValidacionException("mensaje.correo_vacio");
            if (telefono.isEmpty()) throw new ValidacionException("mensaje.telefono_vacio");
            if (fechaNacimiento.isEmpty()) throw new ValidacionException("mensaje.fecha_vacia");
            if (!isValidCedula(username)) throw new ValidacionException("mensaje.cedula_invalida");
            if (!isValidPassword(contrasenia)) throw new ValidacionException("mensaje.contrasenia_invalida");
            if (!isEmailValid(correo)) throw new ValidacionException("mensaje.correo_invalido");
            if (!isPhoneValid(telefono)) throw new ValidacionException("mensaje.telefono_invalido");
            if (!isDateValid(fechaNacimiento)) throw new ValidacionException("mensaje.fecha_invalida");
            if (registroView.getPreguntasRespondidas() < 3) {
                throw new ValidacionException("mensaje.preguntas_requeridas");
            }

            Usuario nuevoUsuario = new Usuario(username, contrasenia, Rol.USUARIO, nombre, apellido, correo, telefono, fechaNacimiento);
            usuarioDAO.crear(nuevoUsuario);

            String[] respuestas = registroView.getRespuestasGuardadas();
            int[] idsPreguntas = registroView.getIdsPreguntasRespondidas();
            for (int i = 0; i < 3; i++) {
                respuestaSeguridadDAO.crear(new RespuestaSeguridad(0, idsPreguntas[i], username, respuestas[i]));
            }

            registroView.mostrarMensaje(registroView.getMensajeHandler().get("mensaje.registro_exitoso"));
            registroView.limpiarCampos();
            registroView.dispose();

        } catch (ValidacionException | DatoExistenteException e) {
            String msg = registroView.getMensajeHandler().get(e.getMessage());
            registroView.mostrarMensaje(msg);
        }
    }

    /**
     * Muestra la ventana de recuperación de contraseña.
     */
    private void mostrarVentanaRecuperacion() {
        recuperacionView = new RecuperacionView();
        recuperacionView.setVisible(true);
        recuperacionView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaRecuperacion());
        recuperacionView.getBtnVerificar().addActionListener(e -> verificarPreguntas());
        recuperacionView.getBtnCambiarContrasenia().addActionListener(e -> cambiarContrasenia());
    }

    /**
     * Busca un usuario por su username y carga sus preguntas de seguridad en la vista de recuperación.
     */
    private void buscarUsuarioParaRecuperacion() {
        try {
            String username = recuperacionView.getTxtUsername().getText();
            Usuario usuario = usuarioDAO.buscarPorUsername(username);

            if (usuario == null) {
                throw new DatoNoEncontradoException("mensaje.usuario_no_encontrado");
            }

            List<RespuestaSeguridad> respuestas = respuestaSeguridadDAO.listarPorUsuario(username);
            if (respuestas.size() < 3) {
                throw new ValidacionException("mensaje.preguntas_insuficientes");
            }

            List<PreguntaSeguridad> preguntasDelUsuario = new ArrayList<>();
            String[] textosRespuestas = new String[respuestas.size()];

            for (int i = 0; i < respuestas.size(); i++) {
                RespuestaSeguridad respuesta = respuestas.get(i);
                PreguntaSeguridad pregunta = preguntaSeguridadDAO.buscarPorId(respuesta.getPreguntaId());
                if (pregunta != null) {
                    preguntasDelUsuario.add(pregunta);
                    textosRespuestas[i] = respuesta.getRespuesta();
                }
            }
            recuperacionView.cargarPreguntas(preguntasDelUsuario, textosRespuestas);

        } catch(DatoNoEncontradoException | ValidacionException e) {
            recuperacionView.mostrarMensaje(recuperacionView.getMensajeHandler().get(e.getMessage()));
        }
    }

    /**
     * Verifica si la respuesta de seguridad ingresada por el usuario es correcta.
     */
    private void verificarPreguntas() {
        String respuestaUsuario = recuperacionView.getTxtRespuesta().getText();

        if (respuestaUsuario.isEmpty()) {
            recuperacionView.mostrarMensaje(recuperacionView.getMensajeHandler().get("mensaje.respuesta_requerida"));
            return;
        }

        if (recuperacionView.verificarRespuesta(respuestaUsuario)) {
            recuperacionView.mostrarMensaje(recuperacionView.getMensajeHandler().get("mensaje.respuesta_correcta"));
            recuperacionView.habilitarCambioContrasenia();
        }
    }

    /**
     * Cambia la contraseña del usuario después de una verificación exitosa de las preguntas de seguridad.
     */
    private void cambiarContrasenia() {
        try {
            String username = recuperacionView.getTxtUsername().getText();
            String nuevaContrasenia = new String(recuperacionView.getTxtNuevaContrasenia().getPassword());

            if (!isValidPassword(nuevaContrasenia)) {
                throw new ValidacionException("mensaje.contrasenia_invalida");
            }

            Usuario usuarioParaActualizar = usuarioDAO.buscarPorUsername(username);
            if(usuarioParaActualizar == null) {
                throw new DatoNoEncontradoException("mensaje.usuario_no_encontrado");
            }

            usuarioParaActualizar.setContrasenia(nuevaContrasenia);
            usuarioDAO.actualizar(usuarioParaActualizar);

            recuperacionView.mostrarMensaje(recuperacionView.getMensajeHandler().get("mensaje.contrasenia_cambiada"));
            recuperacionView.dispose();

        } catch (ValidacionException | DatoNoEncontradoException e) {
            recuperacionView.mostrarMensaje(recuperacionView.getMensajeHandler().get(e.getMessage()));
        }
    }

    /**
     * Configura los eventos para la vista de añadir usuario (panel de administrador).
     * @param view La instancia de {@code UsuarioAnadirView}.
     */
    public void configurarEventosUsuarioAnadir(UsuarioAnadir view) {
        this.usuarioAnadirView = view;
        view.getBtnAgregarUsuario().addActionListener(e -> agregarUsuarioAdmin());
        view.cargarPreguntas(preguntaSeguridadDAO.listarTodas());
    }

    /**
     * Valida y agrega un nuevo usuario desde el panel de administrador.
     */
    private void agregarUsuarioAdmin() {
        try {
            String username = usuarioAnadirView.getUsername().trim();
            String contrasenia = usuarioAnadirView.getContrasenia();
            String nombre = usuarioAnadirView.getNombre().trim();
            String apellido = usuarioAnadirView.getApellido().trim();
            String correo = usuarioAnadirView.getCorreo().trim();
            String telefono = usuarioAnadirView.getTelefono().trim();
            String fechaNacimiento = usuarioAnadirView.getFechaNacimiento().trim();

            if (nombre.isEmpty()) throw new ValidacionException("mensaje.nombre_vacio");
            if (apellido.isEmpty()) throw new ValidacionException("mensaje.apellido_vacio");
            if (correo.isEmpty()) throw new ValidacionException("mensaje.correo_vacio");
            if (telefono.isEmpty()) throw new ValidacionException("mensaje.telefono_vacio");
            if (fechaNacimiento.isEmpty()) throw new ValidacionException("mensaje.fecha_vacia");
            if (!isValidCedula(username)) throw new ValidacionException("mensaje.cedula_invalida");
            if (!isValidPassword(contrasenia)) throw new ValidacionException("mensaje.contrasenia_invalida");
            if (!isEmailValid(correo)) throw new ValidacionException("mensaje.correo_invalido");
            if (!isPhoneValid(telefono)) throw new ValidacionException("mensaje.telefono_invalido");
            if (!isDateValid(fechaNacimiento)) throw new ValidacionException("mensaje.fecha_invalida");
            if (usuarioAnadirView.getPreguntasRespondidas() < 3) {
                throw new ValidacionException("mensaje.preguntas_requeridas");
            }

            Usuario nuevoUsuario = new Usuario(username, contrasenia, Rol.USUARIO, nombre, apellido, correo, telefono, fechaNacimiento);
            usuarioDAO.crear(nuevoUsuario);

            String[] respuestas = usuarioAnadirView.getRespuestasGuardadas();
            int[] idsPreguntas = usuarioAnadirView.getIdsPreguntasRespondidas();

            for (int i = 0; i < 3; i++) {
                respuestaSeguridadDAO.crear(new RespuestaSeguridad(0, idsPreguntas[i], username, respuestas[i]));
            }

            usuarioAnadirView.mostrarMensaje(usuarioAnadirView.getMensajeHandler().get("mensaje.usuario_agregado"));
            usuarioAnadirView.limpiarCampos();

        } catch (ValidacionException | DatoExistenteException e) {
            usuarioAnadirView.mostrarMensaje(usuarioAnadirView.getMensajeHandler().get(e.getMessage()));
        }
    }

    /**
     * Configura los eventos para la vista de actualizar usuario (panel de administrador).
     * @param view La instancia de {@code UsuarioActualizarView}.
     */
    public void configurarEventosUsuarioActualizar(UsuarioActualizar view) {
        this.usuarioActualizarView = view;
        view.getBtnBuscar().addActionListener(e -> buscarUsuarioParaActualizar());
        view.getBtnActualizar().addActionListener(e -> actualizarUsuarioAdmin());
        view.cargarPreguntas(preguntaSeguridadDAO.listarTodas());
    }

    /**
     * Busca un usuario y carga sus datos en la vista de actualización del administrador.
     */
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

    /**
     * Actualiza la contraseña y las preguntas de seguridad de un usuario desde el panel de administrador.
     */
    private void actualizarUsuarioAdmin() {
        try {
            String username = usuarioActualizarView.getUsername();
            String nuevaContrasenia = usuarioActualizarView.getNuevaContrasenia();

            if (usuarioDAO.buscarPorUsername(username) == null) {
                throw new DatoNoEncontradoException("mensaje.usuario_no_encontrado");
            }
            if (usuarioActualizarView.getPreguntasRespondidas() < 3) {
                throw new ValidacionException("mensaje.preguntas_requeridas");
            }
            if (!isValidPassword(nuevaContrasenia)) {
                throw new ValidacionException("mensaje.contrasenia_invalida");
            }

            Usuario usuario = usuarioDAO.buscarPorUsername(username);
            usuario.setContrasenia(nuevaContrasenia);
            usuarioDAO.actualizar(usuario);

            respuestaSeguridadDAO.eliminarPorUsuario(username);
            String[] respuestas = usuarioActualizarView.getRespuestasGuardadas();
            int[] idsPreguntas = usuarioActualizarView.getIdsPreguntasRespondidas();
            for (int i = 0; i < 3; i++) {
                respuestaSeguridadDAO.crear(new RespuestaSeguridad(0, idsPreguntas[i], username, respuestas[i]));
            }

            usuarioActualizarView.mostrarMensaje(usuarioActualizarView.getMensajeHandler().get("mensaje.usuario_actualizado"));
            usuarioActualizarView.limpiarCampos();

        } catch(DatoNoEncontradoException | ValidacionException e) {
            usuarioActualizarView.mostrarMensaje(usuarioActualizarView.getMensajeHandler().get(e.getMessage()));
        }
    }

    /**
     * Configura los eventos para la vista de gestión de usuarios (listar/eliminar).
     * @param view La instancia de {@code UsuarioView}.
     */
    public void configurarEventosUsuarioView(UsuarioView view) {
        this.usuarioView = view;
        view.getBtnListar().addActionListener(e -> listarUsuarios());
        view.getBtnEliminar().addActionListener(e -> eliminarUsuario());
    }

    /**
     * Obtiene y muestra todos los usuarios en la vista de gestión.
     */
    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        usuarioView.cargarDatos(usuarios);
    }

    /**
     * Elimina un usuario seleccionado desde la vista de gestión, previa confirmación.
     */
    private void eliminarUsuario() {
        try {
            String username = usuarioView.getTxtUsername().getText();
            if (username.isEmpty()) {
                throw new ValidacionException("mensaje.username_requerido");
            }

            int confirmacion = JOptionPane.showConfirmDialog(usuarioView,
                    usuarioView.getMensajeHandler().get("mensaje.confirmar_eliminar") + " " + username + "?",
                    usuarioView.getMensajeHandler().get("titulo.confirmar"),
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                usuarioDAO.eliminar(username);
                respuestaSeguridadDAO.eliminarPorUsuario(username);
                usuarioView.mostrarMensaje(usuarioView.getMensajeHandler().get("mensaje.usuario_eliminado"));
                usuarioView.getTxtUsername().setText("");
                listarUsuarios();
            }
        } catch (DatoNoEncontradoException | ValidacionException e) {
            usuarioView.mostrarMensaje(usuarioView.getMensajeHandler().get(e.getMessage()));
        }
    }

    /**
     * Obtiene el usuario que ha iniciado sesión.
     * @return El objeto {@code Usuario} autenticado, o {@code null} si no hay sesión activa.
     */
    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    /**
     * Cierra la sesión del usuario actual, estableciendo el usuario en sesión a {@code null}.
     */
    public void cerrarSesion() {
        this.usuario = null;
    }

    /**
     * Valida una cédula ecuatoriana.
     * @param cedula El número de cédula de 10 dígitos.
     * @return {@code true} si la cédula es válida, {@code false} en caso contrario.
     */
    private boolean isValidCedula(String cedula) {
        if (cedula.length() != 10 || !cedula.matches("\\d+")) return false;
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) return false;
        int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
        if (tercerDigito >= 6) return false;
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Integer.parseInt(cedula.substring(i, i + 1));
            int producto = digito * coeficientes[i];
            if (producto > 9) producto -= 9;
            suma += producto;
        }
        int digitoVerificador = Integer.parseInt(cedula.substring(9, 10));
        int decenaSuperior = ((suma / 10) + (suma % 10 != 0 ? 1 : 0)) * 10;
        int digitoCalculado = decenaSuperior - suma;
        if (digitoCalculado == 10) digitoCalculado = 0;
        return digitoCalculado == digitoVerificador;
    }

    /**
     * Valida la complejidad de una contraseña.
     * @param password La contraseña a validar.
     * @return {@code true} si la contraseña es válida, {@code false} en caso contrario.
     */
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[a-z].*")) return false;
        if (!password.matches(".*[@_\\-.].*")) return false;
        return true;
    }

    /**
     * Valida el formato de un correo electrónico.
     * @param email El correo a validar.
     * @return {@code true} si el formato es válido, {@code false} en caso contrario.
     */
    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) return false;
        return pat.matcher(email).matches();
    }

    /**
     * Valida el formato de un número de teléfono ecuatoriano (10 dígitos, empieza con 09).
     * @param phone El teléfono a validar.
     * @return {@code true} si el formato es válido, {@code false} en caso contrario.
     */
    private boolean isPhoneValid(String phone) {
        return phone != null && phone.matches("^09\\d{8}$");
    }

    /**
     * Valida un formato simple de fecha (AAAA-MM-DD o DD/MM/AAAA).
     * @param date La fecha a validar.
     * @return {@code true} si el formato es válido, {@code false} en caso contrario.
     */
    private boolean isDateValid(String date) {
        return date != null && date.matches("^(\\d{4}-\\d{2}-\\d{2}|\\d{2}/\\d{2}/\\d{4})$");
    }
}