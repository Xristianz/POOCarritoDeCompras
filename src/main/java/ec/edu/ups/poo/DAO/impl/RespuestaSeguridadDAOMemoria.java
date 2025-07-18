package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.RespuestaSeguridadDAO;
import ec.edu.ups.poo.models.RespuestaSeguridad;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación en memoria de la interfaz {@link RespuestaSeguridadDAO}.
 * <p>
 * Esta clase gestiona una colección de objetos {@link RespuestaSeguridad} utilizando
 * una {@link ArrayList}. Los datos son volátiles y se pierden al finalizar la aplicación.
 * Implementa un mecanismo de auto-incremento para el ID de cada nueva respuesta.
 * </p>
 */
public class RespuestaSeguridadDAOMemoria implements RespuestaSeguridadDAO {
    private List<RespuestaSeguridad> respuestas;
    private int nextId = 1;

    /**
     * Construye una nueva instancia del DAO e inicializa la lista de respuestas.
     */
    public RespuestaSeguridadDAOMemoria() {
        respuestas = new ArrayList<>();
    }

    /**
     * Asigna un ID único y secuencial a la respuesta y la agrega a la lista en memoria.
     *
     * @param respuesta El objeto {@code RespuestaSeguridad} a ser creado.
     */
    @Override
    public void crear(RespuestaSeguridad respuesta) {
        respuesta.setId(nextId++);
        respuestas.add(respuesta);
    }

    /**
     * Devuelve una lista con todas las respuestas de seguridad de un usuario específico.
     *
     * @param username El nombre de usuario para filtrar las respuestas.
     * @return una {@code List<RespuestaSeguridad>} con las respuestas encontradas.
     */
    @Override
    public List<RespuestaSeguridad> listarPorUsuario(String username) {
        List<RespuestaSeguridad> result = new ArrayList<>();
        for (RespuestaSeguridad r : respuestas) {
            if (r.getUsername().equals(username)) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Busca una respuesta específica asociada a un ID de pregunta y un usuario.
     *
     * @param preguntaId El ID de la pregunta de seguridad.
     * @param username   El nombre de usuario asociado a la respuesta.
     * @return El objeto {@code RespuestaSeguridad} encontrado, o {@code null} si no existe.
     */
    @Override
    public RespuestaSeguridad buscarPorPreguntaYUsuario(int preguntaId, String username) {
        for (RespuestaSeguridad r : respuestas) {
            if (r.getPreguntaId() == preguntaId && r.getUsername().equals(username)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Elimina una respuesta de la lista por su ID único.
     *
     * @param id El ID de la respuesta a eliminar.
     */
    @Override
    public void eliminar(int id) {
        respuestas.removeIf(r -> r.getId() == id);
    }

    /**
     * Elimina todas las respuestas de seguridad asociadas a un usuario.
     *
     * @param username El nombre de usuario cuyas respuestas serán eliminadas.
     */
    @Override
    public void eliminarPorUsuario(String username) {
        respuestas.removeIf(r -> r.getUsername().equals(username));
    }
}