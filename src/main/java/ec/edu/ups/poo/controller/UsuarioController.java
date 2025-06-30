package ec.edu.ups.poo.controller;

import ec.edu.ups.poo.DAO.UsuarioDAO;
import ec.edu.ups.poo.models.Rol;
import ec.edu.ups.poo.models.Usuario;
import ec.edu.ups.poo.view.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        configurarEventosEnVistas();
        configurarRegistro(); // Nueva línea
    }

    private void configurarEventosEnVistas(){
        loginView.getBtnIniciarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });
    }

    private void autenticar(){
        String username = loginView.getTxtUsername().getText();
        String contrasenia = loginView.getTxtContrasenia().getText();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if(usuario == null){
            loginView.mostrarMensaje("Usuario o contraseña incorrectos.");
        }else{
            loginView.dispose();
        }
    }

    public Usuario getUsuarioAutenticado(){
        return usuario;
    }
    private void configurarRegistro() {
        loginView.getBtnRegistrarse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        String username = loginView.getTxtUsername().getText();
        String contrasenia = new String(loginView.getTxtContrasenia().getPassword());

        if(username.isEmpty() || contrasenia.isEmpty()) {
            loginView.mostrarMensaje("Usuario y contraseña son requeridos");
            return;
        }

        if(usuarioDAO.buscarPorUsername(username) != null) {
            loginView.mostrarMensaje("El usuario ya existe");
            return;
        }

        if(loginView.confirmarRegistro()) {
            Usuario nuevoUsuario = new Usuario(username, contrasenia, Rol.USUARIO);
            usuarioDAO.crear(nuevoUsuario);
            loginView.mostrarMensaje("Usuario registrado exitosamente");
            loginView.limpiarCampos();
        }
    }
    public void cerrarSesion() {
        this.usuario = null;
    }
    public void salir(){

    }
}