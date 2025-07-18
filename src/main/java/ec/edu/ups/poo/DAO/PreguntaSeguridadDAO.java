package ec.edu.ups.poo.DAO;

import ec.edu.ups.poo.models.PreguntaSeguridad;
import java.util.List;

/**
 * Interfaz que define el Contrato de Acceso a Datos (DAO) para la entidad PreguntaSeguridad.
 * <p>
 * Proporciona métodos de solo lectura para acceder a la colección de preguntas de seguridad
 * del sistema, abstrayendo la fuente de datos subyacente (memoria, archivo, etc.).
 * </p>
 */
public interface PreguntaSeguridadDAO {

    /**
     * Devuelve una lista con todas las preguntas de seguridad disponibles.
     *
     * @return una {@code List<PreguntaSeguridad>} con todas las preguntas.
     */
    List<PreguntaSeguridad> listarTodas();

    /**
     * Busca y devuelve una PreguntaSeguridad por su ID único.
     *
     * @param id El ID de la pregunta a buscar.
     * @return El objeto {@code PreguntaSeguridad} encontrado, o {@code null} si no existe.
     */
    PreguntaSeguridad buscarPorId(int id);

}