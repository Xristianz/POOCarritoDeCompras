package ec.edu.ups.poo.DAO;

import ec.edu.ups.poo.models.Producto;

import java.util.List;

/**
 * Interfaz que define el Contrato de Acceso a Datos (DAO) para la entidad Producto.
 * <p>
 * Abstrae las operaciones de persistencia (CRUD) para los objetos {@link Producto},
 * permitiendo que diferentes implementaciones (en memoria, en archivo, etc.)
 * sean utilizadas de manera intercambiable por el resto de la aplicación.
 * </p>
 */
public interface ProductoDAO {

    /**
     * Persiste un nuevo objeto Producto en el sistema de almacenamiento.
     *
     * @param producto El objeto {@code Producto} a ser creado.
     */
    void crear(Producto producto);

    /**
     * Busca y devuelve un Producto por su código único.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto {@code Producto} encontrado, o {@code null} si no existe.
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca y devuelve una lista de Productos cuyo nombre coincida con el término de búsqueda.
     *
     * @param nombre El nombre a buscar. La implementación puede manejar búsquedas exactas o parciales.
     * @return una {@code List<Producto>} con los productos que coinciden.
     */
    List<Producto> buscarPorNombre(String nombre);

    /**
     * Actualiza los datos de un Producto existente en el sistema de almacenamiento.
     *
     * @param producto El objeto {@code Producto} con la información actualizada.
     */
    void actualizar(Producto producto);

    /**
     * Elimina un Producto del sistema de almacenamiento por su código.
     *
     * @param codigo El código del producto a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Devuelve una lista con todos los Productos almacenados en el sistema.
     *
     * @return una {@code List<Producto>} con todos los productos.
     */
    List<Producto> listarTodos();

}