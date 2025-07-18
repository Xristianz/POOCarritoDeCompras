package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.CarritoDAO;
import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.DAO.UsuarioDAO;
import ec.edu.ups.poo.controller.util.Excepciones.DatoNoEncontradoException;
import ec.edu.ups.poo.controller.util.Excepciones.ValidacionException;
import ec.edu.ups.poo.models.Carrito;
import ec.edu.ups.poo.models.ItemCarrito;
import ec.edu.ups.poo.models.Producto;
import ec.edu.ups.poo.models.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz CarritoDAO para persistencia en archivos de texto.
 * <p>
 * Gestiona las operaciones CRUD para objetos {@link Carrito} guardando los datos
 * en un archivo de texto plano. Utiliza un formato separado por comas para los
 * atributos del carrito y un subformato para los ítems.
 * </p>
 */
public class CarritoDaoTexto implements CarritoDAO {

    private final String RUTA_ARCHIVO;
    private final ProductoDAO productoDAO;
    private UsuarioDAO usuarioDAO;

    /**
     * Construye una nueva instancia del DAO.
     * <p>
     * Este constructor no recibe el {@code UsuarioDAO} para evitar un bloqueo de
     * dependencia circular durante la inicialización. El {@code UsuarioDAO} debe
     * ser inyectado posteriormente a través de {@link #setUsuarioDAO(UsuarioDAO)}.
     * </p>
     *
     * @param productoDAO El DAO de productos, necesario para reconstruir los carritos.
     * @param basePath    La ruta base del directorio donde se guardará el archivo "carritos.txt".
     */
    public CarritoDaoTexto(ProductoDAO productoDAO, String basePath) {
        this.productoDAO = productoDAO;
        this.RUTA_ARCHIVO = basePath + File.separator + "carritos.txt";

        File file = new File(RUTA_ARCHIVO);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Inyecta la dependencia del DAO de usuarios.
     * <p>
     * Este método es crucial para resolver la dependencia circular entre
     * {@code CarritoDaoTexto} y {@code UsuarioDaoTexto}.
     * </p>
     *
     * @param usuarioDAO La instancia del DAO de usuarios.
     */
    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Guarda un nuevo carrito en el archivo de texto.
     *
     * @param carrito El objeto {@code Carrito} a ser creado.
     * @throws ValidacionException si el carrito está vacío.
     */
    @Override
    public void crear(Carrito carrito) {
        if (carrito.estaVacio()) {
            throw new ValidacionException("error.carrito.vacio");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            writer.println(carritoToString(carrito));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un carrito en el archivo por su código único.
     *
     * @param codigo El código del carrito a buscar.
     * @return El objeto {@code Carrito} encontrado, o {@code null} si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        return listarTodos().stream()
                .filter(c -> c.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    /**
     * Actualiza un carrito existente en el archivo.
     *
     * @param carritoActualizado El objeto {@code Carrito} con los datos actualizados.
     * @throws DatoNoEncontradoException si no se encuentra el carrito.
     */
    @Override
    public void actualizar(Carrito carritoActualizado) {
        List<Carrito> carritos = listarTodos();
        boolean encontrado = false;
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carritoActualizado.getCodigo()) {
                carritos.set(i, carritoActualizado);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new DatoNoEncontradoException("error.carrito.no.encontrado");
        }
        guardarTodos(carritos);
    }

    /**
     * Elimina un carrito del archivo por su código.
     *
     * @param codigo El código del carrito a eliminar.
     * @throws DatoNoEncontradoException si no se encuentra el carrito.
     */
    @Override
    public void eliminar(int codigo) {
        List<Carrito> carritos = listarTodos();
        boolean eliminado = carritos.removeIf(c -> c.getCodigo() == codigo);
        if (!eliminado) {
            throw new DatoNoEncontradoException("error.carrito.no.encontrado");
        }
        guardarTodos(carritos);
    }

    /**
     * Lee el archivo completo y devuelve una lista de todos los carritos.
     *
     * @return Una {@code List<Carrito>} con todos los carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> carritos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Carrito carrito = stringToCarrito(linea);
                if (carrito != null) {
                    carritos.add(carrito);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carritos;
    }

    /**
     * Devuelve una lista de carritos que pertenecen a un usuario específico.
     *
     * @param username El nombre de usuario para filtrar los carritos.
     * @return Una {@code List<Carrito>} con los carritos del usuario.
     */
    @Override
    public List<Carrito> listarPorUsuario(String username) {
        return listarTodos().stream()
                .filter(c -> c.getUsuario().getUsername().equals(username))
                .collect(Collectors.toList());
    }

    /**
     * Escribe una lista completa de carritos al archivo, sobrescribiendo su contenido.
     *
     * @param carritos La lista de carritos a guardar.
     */
    private void guardarTodos(List<Carrito> carritos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RUTA_ARCHIVO, false))) {
            for (Carrito carrito : carritos) {
                writer.println(carritoToString(carrito));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Convierte un objeto {@code Carrito} a una cadena de texto en formato CSV.
     *
     * @param carrito El carrito a convertir.
     * @return Una cadena de texto formateada para ser guardada en el archivo.
     */
    private String carritoToString(Carrito carrito) {
        StringBuilder sb = new StringBuilder();
        sb.append(carrito.getCodigo()).append(",");
        sb.append(carrito.getFechaCreacion().getTimeInMillis()).append(",");
        sb.append(carrito.getUsuario().getUsername()).append(",");

        String itemsString = carrito.obtenerItems().stream()
                .map(item -> item.getProducto().getCodigo() + ":" + item.getCantidad())
                .collect(Collectors.joining(";"));
        sb.append(itemsString);

        return sb.toString();
    }

    /**
     * Convierte una cadena de texto del archivo a un objeto {@code Carrito}.
     *
     * @param linea La línea de texto leída del archivo.
     * @return Un objeto {@code Carrito} reconstruido.
     * @throws IllegalStateException si el {@code UsuarioDAO} no ha sido inyectado.
     */
    private Carrito stringToCarrito(String linea) {
        if (usuarioDAO == null) {
            throw new IllegalStateException("UsuarioDAO no ha sido inyectado en CarritoDaoTexto.");
        }
        String[] partes = linea.split(",", 4);
        if (partes.length < 3) return null;

        int codigo = Integer.parseInt(partes[0]);
        long fechaMillis = Long.parseLong(partes[1]);
        String username = partes[2];

        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario == null) {
            return null;
        }

        Carrito carrito = new Carrito(usuario);
        carrito.setCodigo(codigo);
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.setTimeInMillis(fechaMillis);
        carrito.setFechaCreacion(fecha);

        if (partes.length > 3 && !partes[3].isEmpty()) {
            String[] itemsArray = partes[3].split(";");
            for (String itemStr : itemsArray) {
                String[] itemPartes = itemStr.split(":");
                int productoCodigo = Integer.parseInt(itemPartes[0]);
                int cantidad = Integer.parseInt(itemPartes[1]);

                Producto producto = productoDAO.buscarPorCodigo(productoCodigo);
                if (producto != null) {
                    try {
                        carrito.agregarProducto(producto, cantidad);
                    } catch (ValidacionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return carrito;
    }
}