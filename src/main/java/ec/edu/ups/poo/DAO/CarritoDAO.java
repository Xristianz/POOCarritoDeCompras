package ec.edu.ups.poo.DAO;

import ec.edu.ups.poo.models.Carrito;
import java.util.List;

/**
 * Interfaz que define el Contrato de Acceso a Datos (DAO) para la entidad Carrito.
 * <p>
 * Abstrae las operaciones de persistencia (CRUD) para los objetos {@link Carrito},
 * permitiendo que diferentes implementaciones (en memoria, en archivo, en base de datos)
 * puedan ser utilizadas de manera intercambiable por el resto de la aplicación.
 * </p>
 */
public interface CarritoDAO {

    /**
     * Persiste un nuevo objeto Carrito en el sistema de almacenamiento.
     *
     * @param carrito El objeto {@code Carrito} a ser creado.
     */
    void crear(Carrito carrito);

    /**
     * Busca y devuelve un Carrito por su código único.
     *
     * @param codigo El código del carrito a buscar.
     * @return El objeto {@code Carrito} encontrado, o {@code null} si no existe.
     */
    Carrito buscarPorCodigo(int codigo);

    /**
     * Actualiza los datos de un Carrito existente en el sistema de almacenamiento.
     *
     * @param carrito El objeto {@code Carrito} con la información actualizada.
     */
    void actualizar(Carrito carrito);

    /**
     * Elimina un Carrito del sistema de almacenamiento por su código.
     *
     * @param codigo El código del carrito a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Devuelve una lista con todos los Carritos almacenados en el sistema.
     *
     * @return una {@code List<Carrito>} con todos los carritos.
     */
    List<Carrito> listarTodos();

    /**
     * Devuelve una lista de todos los Carritos que pertenecen a un usuario específico.
     *
     * @param username El nombre de usuario para filtrar los carritos.
     * @return una {@code List<Carrito>} con los carritos del usuario especificado.
     */
    List<Carrito> listarPorUsuario(String username);

}