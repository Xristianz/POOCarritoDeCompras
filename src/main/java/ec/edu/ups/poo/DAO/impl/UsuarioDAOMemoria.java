package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.CarritoDAO;
import ec.edu.ups.poo.DAO.UsuarioDAO;
import ec.edu.ups.poo.controller.util.Excepciones.CredencialesInvalidasException;
import ec.edu.ups.poo.controller.util.Excepciones.DatoExistenteException;
import ec.edu.ups.poo.controller.util.Excepciones.DatoNoEncontradoException;
import ec.edu.ups.poo.models.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria de la interfaz {@link UsuarioDAO}.
 * <p>
 * Esta clase gestiona una colección de objetos {@link Usuario} utilizando una
 * {@link ArrayList}. Los datos son volátiles y se pierden al finalizar la aplicación.
 * Incluye una dependencia con {@link CarritoDAO} para realizar eliminaciones en cascada.
 * </p>
 */
public class UsuarioDAOMemoria implements UsuarioDAO {
    private List<Usuario> usuarios;
    private CarritoDAO carritoDAO;

    /**
     * Construye una nueva instancia del DAO.
     * <p>
     * Inicializa la lista de usuarios y la puebla con dos usuarios por defecto
     * (un administrador y un usuario estándar).
     * </p>
     * @param carritoDAO La instancia de CarritoDAO necesaria para la eliminación
     * en cascada de los carritos de un usuario.
     */
    public UsuarioDAOMemoria(CarritoDAO carritoDAO) {
        this.carritoDAO = carritoDAO;
        usuarios = new ArrayList<>();

        usuarios.add(new Usuario("admin","12345",Rol.ADMINISTRADOR,"Administrador","Sistema","admin@system.com","0999999999","01/01/2000"));
        usuarios.add(new Usuario("user","12345",Rol.USUARIO,"Usuario","Prueba","user@test.com","0988888888","15/05/1995"));
    }

    /**
     * Autentica a un usuario comparando su nombre de usuario y contraseña.
     *
     * @param username    El nombre de usuario a verificar.
     * @param contrasenia La contraseña a verificar.
     * @return El objeto {@code Usuario} si la autenticación es exitosa.
     * @throws CredencialesInvalidasException si el nombre de usuario o la contraseña no coinciden.
     */
    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contrasenia)) {
                return usuario;
            }
        }
        throw new CredencialesInvalidasException("mensaje.credenciales_incorrectas");
    }

    /**
     * Agrega un nuevo usuario a la lista en memoria.
     *
     * @param usuario El objeto {@code Usuario} a ser creado.
     * @throws DatoExistenteException si ya existe un usuario with el mismo username.
     */
    @Override
    public void crear(Usuario usuario) {
        if (buscarPorUsername(usuario.getUsername()) != null) {
            throw new DatoExistenteException("mensaje.usuario_existe");
        }
        usuarios.add(usuario);
    }

    /**
     * Busca un usuario en la lista por su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return El objeto {@code Usuario} encontrado, o {@code null} si no existe.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Elimina un usuario de la lista y, en cascada, todos sus carritos asociados.
     *
     * @param username El nombre de usuario del usuario a eliminar.
     * @throws DatoNoEncontradoException si no se encuentra un usuario con el username especificado.
     */
    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                List<Carrito> carritosUsuario = carritoDAO.listarPorUsuario(username);
                for (Carrito carrito : carritosUsuario) {
                    carritoDAO.eliminar(carrito.getCodigo());
                }
                iterator.remove();
                return;
            }
        }
        throw new DatoNoEncontradoException("mensaje.usuario_no_encontrado");
    }

    /**
     * Actualiza un usuario existente en la lista.
     *
     * @param usuario El objeto {@code Usuario} con los datos actualizados.
     * @throws DatoNoEncontradoException si no se encuentra un usuario con el mismo username.
     */
    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                return;
            }
        }
        throw new DatoNoEncontradoException("mensaje.usuario_no_encontrado");
    }

    /**
     * Devuelve una copia de la lista de todos los usuarios almacenados.
     *
     * @return Una nueva {@code List<Usuario>} con todos los usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    /**
     * Devuelve una lista de todos los usuarios que pertenecen a un rol específico.
     *
     * @param rol El {@code Rol} para filtrar los usuarios.
     * @return Una {@code List<Usuario>} con los usuarios del rol especificado.
     */
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