package ec.edu.ups.poo.DAO;

import ec.edu.ups.poo.models.RespuestaSeguridad;
import java.util.List;

/**
 * Interfaz que define el Contrato de Acceso a Datos (DAO) para la entidad RespuestaSeguridad.
 * <p>
 * Abstrae las operaciones de persistencia para los objetos {@link RespuestaSeguridad},
 * permitiendo que diferentes implementaciones (en memoria, en archivo, etc.)
 * sean utilizadas de manera intercambiable por el resto de la aplicación.
 * </p>
 */
public interface RespuestaSeguridadDAO {

    /**
     * Persiste un nuevo objeto RespuestaSeguridad en el sistema de almacenamiento.
     *
     * @param respuesta El objeto {@code RespuestaSeguridad} a ser creado.
     */
    void crear(RespuestaSeguridad respuesta);

    /**
     * Devuelve una lista con todas las respuestas de seguridad de un usuario específico.
     *
     * @param username El nombre de usuario para filtrar las respuestas.
     * @return una {@code List<RespuestaSeguridad>} con las respuestas del usuario.
     */
    List<RespuestaSeguridad> listarPorUsuario(String username);

    /**
     * Busca y devuelve una respuesta específica asociada a un ID de pregunta y un usuario.
     *
     * @param preguntaId El ID de la pregunta de seguridad.
     * @param username   El nombre de usuario asociado a la respuesta.
     * @return El objeto {@code RespuestaSeguridad} encontrado, o {@code null} si no existe.
     */
    RespuestaSeguridad buscarPorPreguntaYUsuario(int preguntaId, String username);

    /**
     * Elimina una respuesta del sistema de almacenamiento por su ID único.
     *
     * @param id El ID de la respuesta a eliminar.
     */
    void eliminar(int id);

    /**
     * Elimina todas las respuestas de seguridad asociadas a un usuario.
     * Este método es útil para operaciones en cascada al eliminar un usuario.
     *
     * @param username El nombre de usuario cuyas respuestas serán eliminadas.
     */
    void eliminarPorUsuario(String username);
}