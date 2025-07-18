package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.controller.util.Excepciones.DatoExistenteException;
import ec.edu.ups.poo.controller.util.Excepciones.DatoNoEncontradoException;
import ec.edu.ups.poo.models.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria de la interfaz {@link ProductoDAO}.
 * <p>
 * Esta clase gestiona una colección de objetos {@link Producto} utilizando una
 * {@link ArrayList}. Los datos son volátiles y se pierden al finalizar la aplicación.
 * </p>
 */
public class ProductoDAOMemoria implements ProductoDAO {

    private List<Producto> productos;

    /**
     * Construye una nueva instancia del DAO e inicializa la lista para almacenar productos.
     */
    public ProductoDAOMemoria() {
        productos = new ArrayList<Producto>();
    }

    /**
     * Agrega un nuevo producto a la lista en memoria.
     *
     * @param producto El objeto {@code Producto} a ser creado.
     * @throws DatoExistenteException si ya existe un producto con el mismo código.
     */
    @Override
    public void crear(Producto producto) {
        if (buscarPorCodigo(producto.getCodigo()) != null) {
            throw new DatoExistenteException("error.producto.existente");
        }
        productos.add(producto);
    }

    /**
     * Busca un producto en la lista por su código único.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto {@code Producto} encontrado, o {@code null} si no existe.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    /**
     * Busca productos en la lista cuyo nombre coincida, ignorando mayúsculas y minúsculas.
     *
     * @param nombre El nombre o parte del nombre a buscar.
     * @return una {@code List<Producto>} con los productos que coinciden.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    /**
     * Actualiza un producto existente en la lista.
     *
     * @param producto El objeto {@code Producto} con los datos actualizados.
     * @throws DatoNoEncontradoException si no se encuentra un producto con el código especificado.
     */
    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                return;
            }
        }
        throw new DatoNoEncontradoException("error.producto.no.encontrado");
    }

    /**
     * Elimina un producto de la lista basado en su código.
     *
     * @param codigo El código del producto a eliminar.
     * @throws DatoNoEncontradoException si no se encuentra un producto con el código especificado.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
                return;
            }
        }
        throw new DatoNoEncontradoException("error.producto.no.encontrado");
    }

    /**
     * Devuelve una copia de la lista de todos los productos almacenados.
     *
     * @return Una nueva {@code List<Producto>} con todos los productos.
     */
    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}