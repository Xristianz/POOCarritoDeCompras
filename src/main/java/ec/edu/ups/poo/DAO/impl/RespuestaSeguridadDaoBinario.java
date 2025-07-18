package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.RespuestaSeguridadDAO;
import ec.edu.ups.poo.models.RespuestaSeguridad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz {@link RespuestaSeguridadDAO} para persistencia en archivos binarios.
 * <p>
 * Esta clase gestiona las operaciones CRUD para objetos {@link RespuestaSeguridad} mediante
 * la serialización de una lista completa en un archivo ".bin".
 * Implementa un mecanismo de auto-incremento para el ID de cada nueva respuesta.
 * </p>
 */
public class RespuestaSeguridadDaoBinario implements RespuestaSeguridadDAO {

    private final String RUTA_ARCHIVO;
    private int nextId = 1;

    /**
     * Construye una nueva instancia del DAO.
     * <p>
     * Establece la ruta del archivo de datos e inicializa la secuencia de IDs
     * basándose en el ID máximo encontrado en el archivo, si este ya existe.
     * </p>
     * @param basePath La ruta del directorio base donde se creará el archivo "respuestas.bin".
     */
    public RespuestaSeguridadDaoBinario(String basePath) {
        this.RUTA_ARCHIVO = basePath + File.separator + "respuestas.bin";
        List<RespuestaSeguridad> respuestas = listarTodosInternal();
        if (!respuestas.isEmpty()) {
            nextId = respuestas.stream().mapToInt(RespuestaSeguridad::getId).max().orElse(0) + 1;
        }
    }

    /**
     * Asigna un ID único a la respuesta y la guarda en el archivo.
     *
     * @param respuesta El objeto {@code RespuestaSeguridad} a ser creado.
     */
    @Override
    public void crear(RespuestaSeguridad respuesta) {
        List<RespuestaSeguridad> respuestas = listarTodosInternal();
        respuesta.setId(nextId++);
        respuestas.add(respuesta);
        guardarTodos(respuestas);
    }

    /**
     * Devuelve una lista con todas las respuestas de seguridad de un usuario específico.
     *
     * @param username El nombre de usuario para filtrar las respuestas.
     * @return una {@code List<RespuestaSeguridad>} con las respuestas del usuario.
     */
    @Override
    public List<RespuestaSeguridad> listarPorUsuario(String username) {
        return listarTodosInternal().stream()
                .filter(r -> r.getUsername().equalsIgnoreCase(username))
                .collect(Collectors.toList());
    }

    /**
     * Busca una respuesta específica asociada a un ID de pregunta y un usuario.
     *
     * @param preguntaId El ID de la pregunta de seguridad.
     * @param username El nombre de usuario.
     * @return El objeto {@code RespuestaSeguridad} encontrado, o {@code null} si no existe.
     */
    @Override
    public RespuestaSeguridad buscarPorPreguntaYUsuario(int preguntaId, String username) {
        return listarTodosInternal().stream()
                .filter(r -> r.getPreguntaId() == preguntaId && r.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Elimina una respuesta del archivo por su ID único.
     *
     * @param id El ID de la respuesta a eliminar.
     */
    @Override
    public void eliminar(int id) {
        List<RespuestaSeguridad> respuestas = listarTodosInternal();
        respuestas.removeIf(r -> r.getId() == id);
        guardarTodos(respuestas);
    }

    /**
     * Elimina todas las respuestas de seguridad asociadas a un usuario.
     *
     * @param username El nombre de usuario cuyas respuestas serán eliminadas.
     */
    @Override
    public void eliminarPorUsuario(String username) {
        List<RespuestaSeguridad> respuestas = listarTodosInternal();
        respuestas.removeIf(r -> r.getUsername().equalsIgnoreCase(username));
        guardarTodos(respuestas);
    }

    /**
     * Método de ayuda privado para leer la lista completa de objetos desde el archivo binario.
     *
     * @return La lista de respuestas de seguridad. Devuelve una lista vacía si el archivo
     * no existe o está vacío.
     */
    private List<RespuestaSeguridad> listarTodosInternal() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_ARCHIVO))) {
            return (List<RespuestaSeguridad>) ois.readObject();
        } catch (FileNotFoundException | EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer el archivo binario de respuestas.", e);
        }
    }

    /**
     * Método de ayuda privado para guardar la lista completa de respuestas en el archivo binario,
     * sobrescribiendo cualquier contenido anterior.
     *
     * @param respuestas La lista de respuestas a guardar.
     */
    private void guardarTodos(List<RespuestaSeguridad> respuestas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO, false))) {
            oos.writeObject(respuestas);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al escribir en el archivo binario de respuestas.", e);
        }
    }
}