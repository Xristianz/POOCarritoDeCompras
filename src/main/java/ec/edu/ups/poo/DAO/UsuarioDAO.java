package ec.edu.ups.poo.DAO;

import ec.edu.ups.poo.models.Rol;
import ec.edu.ups.poo.models.Usuario;

import java.util.List;

/**
 * Interfaz que define el Contrato de Acceso a Datos (DAO) para la entidad Usuario.
 * <p>
 * Abstrae las operaciones de persistencia (CRUD) y autenticación para los objetos {@link Usuario},
 * permitiendo que diferentes implementaciones (en memoria, en archivo, etc.)
 * puedan ser utilizadas de manera intercambiable por el resto de la aplicación.
 * </p>
 */
public interface UsuarioDAO {

    /**
     * Autentica a un usuario verificando su nombre de usuario y contraseña.
     *
     * @param username    El nombre de usuario a verificar.
     * @param contrasenia La contraseña a verificar.
     * @return El objeto {@code Usuario} si la autenticación es exitosa.
     * @throws ec.edu.ups.poo.controller.util.Excepciones.CredencialesInvalidasException si las credenciales no coinciden.
     */
    Usuario autenticar(String username, String contrasenia);

    /**
     * Persiste un nuevo objeto Usuario en el sistema de almacenamiento.
     *
     * @param usuario El objeto {@code Usuario} a ser creado.
     * @throws ec.edu.ups.poo.controller.util.Excepciones.DatoExistenteException si ya existe un usuario con el mismo username.
     */
    void crear(Usuario usuario);

    /**
     * Busca y devuelve un Usuario por su nombre de usuario (username), que es único.
     *
     * @param username El nombre de usuario a buscar.
     * @return El objeto {@code Usuario} encontrado, o {@code null} si no existe.
     */
    Usuario buscarPorUsername(String username);

    /**
     * Elimina un Usuario del sistema de almacenamiento por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario a eliminar.
     * @throws ec.edu.ups.poo.controller.util.Excepciones.DatoNoEncontradoException si el usuario no se encuentra.
     */
    void eliminar(String username);

    /**
     * Actualiza los datos de un Usuario existente en el sistema de almacenamiento.
     *
     * @param usuario El objeto {@code Usuario} con la información actualizada.
     * @throws ec.edu.ups.poo.controller.util.Excepciones.DatoNoEncontradoException si el usuario no se encuentra.
     */
    void actualizar(Usuario usuario);

    /**
     * Devuelve una lista con todos los Usuarios almacenados en el sistema.
     *
     * @return una {@code List<Usuario>} con todos los usuarios.
     */
    List<Usuario> listarTodos();

    /**
     * Devuelve una lista de todos los Usuarios que pertenecen a un rol específico.
     *
     * @param rol El {@code Rol} para filtrar los usuarios (ADMINISTRADOR o USUARIO).
     * @return una {@code List<Usuario>} con los usuarios del rol especificado.
     */
    List<Usuario> listarPorRol(Rol rol);

}