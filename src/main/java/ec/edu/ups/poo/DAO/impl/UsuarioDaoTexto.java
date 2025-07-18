package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.CarritoDAO;
import ec.edu.ups.poo.DAO.UsuarioDAO;
import ec.edu.ups.poo.controller.util.Excepciones.*;
import ec.edu.ups.poo.models.Carrito;
import ec.edu.ups.poo.models.Rol;
import ec.edu.ups.poo.models.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz {@link UsuarioDAO} para persistencia en archivos de texto.
 * <p>
 * Gestiona las operaciones CRUD para objetos {@link Usuario} guardando los datos
 * en un archivo de texto plano ("usuarios.txt") en formato CSV.
 * Incluye una dependencia con {@link CarritoDAO} para realizar eliminaciones en cascada.
 * </p>
 */
public class UsuarioDaoTexto implements UsuarioDAO {

    private final String RUTA_ARCHIVO;
    private CarritoDAO carritoDAO;

    /**
     * Construye una nueva instancia del DAO.
     * <p>
     * Establece la ruta del archivo de datos y llama a un método para inicializarlo
     * con usuarios por defecto si el archivo no existe.
     * </p>
     * @param basePath La ruta del directorio base donde se creará o leerá el archivo.
     */
    public UsuarioDaoTexto(String basePath) {
        this.RUTA_ARCHIVO = basePath + File.separator + "usuarios.txt";
        crearArchivoConDatosPorDefectoSiNoExiste();
    }

    /**
     * Inyecta la dependencia del DAO de carritos.
     * <p>
     * Este método es crucial para resolver la dependencia circular entre
     * {@code UsuarioDaoTexto} y las implementaciones de {@code CarritoDAO},
     * permitiendo realizar eliminaciones en cascada.
     * </p>
     * @param carritoDAO La instancia del DAO de carritos.
     */
    public void setCarritoDAO(CarritoDAO carritoDAO) {
        this.carritoDAO = carritoDAO;
    }

    /**
     * Guarda un nuevo usuario en el archivo de texto.
     *
     * @param usuario El objeto {@code Usuario} a ser creado.
     * @throws DatoExistenteException si ya existe un usuario con el mismo username.
     */
    @Override
    public void crear(Usuario usuario) {
        if (buscarPorUsername(usuario.getUsername()) != null) {
            throw new DatoExistenteException("mensaje.usuario_existe");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            writer.println(usuarioToString(usuario));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un usuario del archivo y, en cascada, todos sus carritos asociados.
     *
     * @param username El nombre de usuario del usuario a eliminar.
     * @throws DatoNoEncontradoException si no se encuentra el usuario.
     * @throws IllegalStateException si el {@code CarritoDAO} no ha sido inyectado.
     */
    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = listarTodos();
        boolean fueEliminado = usuarios.removeIf(u -> u.getUsername().equalsIgnoreCase(username));

        if (fueEliminado) {
            if (carritoDAO == null) {
                throw new IllegalStateException("CarritoDAO no fue inyectado. No se pueden eliminar los carritos asociados.");
            }
            List<Carrito> carritosUsuario = carritoDAO.listarPorUsuario(username);
            for (Carrito carrito : carritosUsuario) {
                carritoDAO.eliminar(carrito.getCodigo());
            }
            guardarTodos(usuarios);
        } else {
            throw new DatoNoEncontradoException("mensaje.usuario_no_encontrado");
        }
    }

    /**
     * Autentica a un usuario comparando su nombre de usuario y contraseña.
     *
     * @param username    El nombre de usuario a verificar.
     * @param contrasenia La contraseña a verificar.
     * @return El objeto {@code Usuario} si la autenticación es exitosa.
     * @throws CredencialesInvalidasException si las credenciales no coinciden.
     */
    @Override
    public Usuario autenticar(String username, String contrasenia) {
        Usuario usuario = buscarPorUsername(username);
        if (usuario != null && usuario.getContrasenia().equals(contrasenia)) {
            return usuario;
        }
        throw new CredencialesInvalidasException("mensaje.credenciales_incorrectas");
    }

    /**
     * Busca un usuario en el archivo por su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return El objeto {@code Usuario} encontrado, o {@code null} si no existe.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.split(",")[0].equalsIgnoreCase(username)) {
                    return stringToUsuario(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actualiza un usuario existente en el archivo.
     *
     * @param usuario El objeto {@code Usuario} con los datos actualizados.
     * @throws DatoNoEncontradoException si no se encuentra el usuario.
     */
    @Override
    public void actualizar(Usuario usuario) {
        List<Usuario> usuarios = listarTodos();
        boolean encontrado = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equalsIgnoreCase(usuario.getUsername())) {
                usuarios.set(i, usuario);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new DatoNoEncontradoException("mensaje.usuario_no_encontrado");
        }
        guardarTodos(usuarios);
    }

    /**
     * Lee el archivo completo y devuelve una lista de todos los usuarios.
     *
     * @return Una {@code List<Usuario>} con todos los usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    usuarios.add(stringToUsuario(linea));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    /**
     * Devuelve una lista de usuarios que pertenecen a un rol específico.
     *
     * @param rol El {@code Rol} para filtrar los usuarios.
     * @return Una {@code List<Usuario>} con los usuarios del rol especificado.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return listarTodos().stream()
                .filter(u -> u.getRol() == rol)
                .collect(Collectors.toList());
    }

    /**
     * Escribe una lista completa de usuarios al archivo, sobrescribiendo su contenido.
     *
     * @param usuarios La lista de usuarios a guardar.
     */
    private void guardarTodos(List<Usuario> usuarios) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RUTA_ARCHIVO, false))) {
            for (Usuario usuario : usuarios) {
                writer.println(usuarioToString(usuario));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Convierte un objeto {@code Usuario} a una cadena de texto en formato CSV.
     *
     * @param u El usuario a convertir.
     * @return Una cadena de texto formateada para ser guardada.
     */
    private String usuarioToString(Usuario u) {
        return String.join(",",
                u.getUsername(), u.getContrasenia(), u.getRol().name(), u.getNombre(),
                u.getApellido(), u.getCorreo(), u.getTelefono(), u.getFechaNacimiento());
    }

    /**
     * Convierte una cadena de texto en formato CSV a un objeto {@code Usuario}.
     *
     * @param linea La línea de texto leída del archivo.
     * @return Un objeto {@code Usuario} reconstruido.
     */
    private Usuario stringToUsuario(String linea) {
        String[] p = linea.split(",", 8);
        return new Usuario(p[0], p[1], Rol.valueOf(p[2]), p[3], p[4], p[5], p[6], p[7]);
    }

    /**
     * Método de ayuda que verifica si el archivo de usuarios existe.
     * Si no, lo crea y lo puebla con un administrador y un usuario por defecto.
     */
    private void crearArchivoConDatosPorDefectoSiNoExiste() {
        File file = new File(RUTA_ARCHIVO);
        if (!file.exists()) {
            try {
                file.createNewFile();
                List<Usuario> usuariosPorDefecto = new ArrayList<>();
                usuariosPorDefecto.add(new Usuario("admin","12345",Rol.ADMINISTRADOR,"Administrador","Del Sistema","admin@mail.com","0999999999","2000-01-01"));
                usuariosPorDefecto.add(new Usuario("user","12345",Rol.USUARIO,"Usuario","De Prueba","user@mail.com","0988888888","1995-05-15"));
                guardarTodos(usuariosPorDefecto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}