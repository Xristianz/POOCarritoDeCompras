package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.controller.util.Excepciones;
import ec.edu.ups.poo.models.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz {@link ProductoDAO} para persistencia en archivos binarios.
 * <p>
 * Esta clase gestiona las operaciones CRUD para objetos {@link Producto} mediante
 * la serialización de una lista completa de productos en un archivo ".bin".
 * Cada operación de escritura (crear, actualizar, eliminar) reescribe todo el archivo.
 * </p>
 */
public class ProductoDAOBinario implements ProductoDAO {

    private final String RUTA_ARCHIVO;

    /**
     * Construye una nueva instancia del DAO y establece la ruta del archivo de datos.
     *
     * @param basePath La ruta del directorio base donde se creará el archivo "productos.bin".
     */
    public ProductoDAOBinario(String basePath) {
        this.RUTA_ARCHIVO = basePath + File.separator + "productos.bin";
    }

    /**
     * Agrega un nuevo producto a la colección persistida.
     * <p>
     * Lee la lista actual de productos, agrega el nuevo y reescribe el archivo.
     * </p>
     * @param producto El objeto {@code Producto} a ser creado.
     * @throws Excepciones.DatoExistenteException si ya existe un producto con el mismo código.
     */
    @Override
    public void crear(Producto producto) {
        List<Producto> productos = listarTodos();
        if (productos.stream().anyMatch(p -> p.getCodigo() == producto.getCodigo())) {
            throw new Excepciones.DatoExistenteException("error.producto.existente");
        }
        productos.add(producto);
        guardarTodos(productos);
    }

    /**
     * Busca un producto en el archivo por su código único.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto {@code Producto} encontrado, o {@code null} si no existe.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        return listarTodos().stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    /**
     * Actualiza un producto existente en el archivo.
     * <p>
     * Lee la lista actual, reemplaza el producto correspondiente y reescribe el archivo.
     * </p>
     * @param producto El objeto {@code Producto} con los datos actualizados.
     * @throws Excepciones.DatoNoEncontradoException si no se encuentra un producto con el código especificado.
     */
    @Override
    public void actualizar(Producto producto) {
        List<Producto> productos = listarTodos();
        boolean encontrado = false;
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new Excepciones.DatoNoEncontradoException("error.producto.no.encontrado");
        }
        guardarTodos(productos);
    }

    /**
     * Elimina un producto del archivo por su código.
     * <p>
     * Lee la lista actual, elimina el producto y reescribe el archivo.
     * </p>
     * @param codigo El código del producto a eliminar.
     * @throws Excepciones.DatoNoEncontradoException si no se encuentra un producto con el código especificado.
     */
    @Override
    public void eliminar(int codigo) {
        List<Producto> productos = listarTodos();
        boolean eliminado = productos.removeIf(p -> p.getCodigo() == codigo);
        if (!eliminado) {
            throw new Excepciones.DatoNoEncontradoException("error.producto.no.encontrado");
        }
        guardarTodos(productos);
    }

    /**
     * Lee y deserializa la lista completa de productos desde el archivo binario.
     * <p>
     * Si el archivo no se encuentra o está vacío (lo cual es normal en el primer uso),
     * devuelve una lista vacía.
     * </p>
     * @return una {@code List<Producto>} con todos los productos almacenados.
     * @throws RuntimeException si ocurre un error de I/O o de casteo de clase irrecuperable.
     */
    @Override
    public List<Producto> listarTodos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_ARCHIVO))) {
            return (List<Producto>) ois.readObject();
        } catch (FileNotFoundException | EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("error.persistencia.io", e);
        }
    }

    /**
     * Método de ayuda privado para serializar y escribir la lista de productos al archivo.
     * <p>
     * Esta operación sobrescribe completamente el contenido anterior del archivo.
     * </p>
     * @param productos La lista de productos a guardar.
     */
    private void guardarTodos(List<Producto> productos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO, false))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error.persistencia.io", e);
        }
    }

    /**
     * Busca productos en la colección cuyo nombre coincida (ignorando mayúsculas/minúsculas).
     *
     * @param nombre El nombre del producto a buscar.
     * @return una {@code List<Producto>} con los productos encontrados.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return listarTodos().stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
    }
}