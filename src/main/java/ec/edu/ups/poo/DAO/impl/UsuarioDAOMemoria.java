package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.CarritoDAO;
import ec.edu.ups.poo.DAO.UsuarioDAO;
import ec.edu.ups.poo.models.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {
    private List<Usuario> usuarios;
    private CarritoDAO carritoDAO;

    public UsuarioDAOMemoria(CarritoDAO carritoDAO) {
        this.carritoDAO = carritoDAO;
        usuarios = new ArrayList<Usuario>();

        // Admin inicial
        crear(new Usuario(
                "admin",
                "12345",
                Rol.ADMINISTRADOR,
                "Administrador",
                "Sistema",
                "admin@system.com",
                "0999999999",
                "01/01/2000"
        ));

        // Usuario inicial
        crear(new Usuario(
                "user",
                "12345",
                Rol.USUARIO,
                "Usuario",
                "Prueba",
                "user@test.com",
                "0988888888",
                "15/05/1995"
        ));
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if(usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contrasenia)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        if (buscarPorUsername(usuario.getUsername()) == null) {
            usuarios.add(usuario);
        } else {
            throw new IllegalArgumentException("El usuario ya existe");
        }
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                // Primero eliminamos todos los carritos del usuario
                List<Carrito> carritosUsuario = carritoDAO.listarPorUsuario(username);
                for (Carrito carrito : carritosUsuario) {
                    carritoDAO.eliminar(carrito.getCodigo());
                }

                // Luego eliminamos el usuario
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioAux = usuarios.get(i);
            if (usuarioAux.getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios); // Retorna copia para evitar modificaciones externas
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals(rol)) {
                usuariosEncontrados.add(usuario);
            }
        }
        return usuariosEncontrados;
    }

}