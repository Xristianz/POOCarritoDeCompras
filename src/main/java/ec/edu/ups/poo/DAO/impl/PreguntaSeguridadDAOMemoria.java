package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.PreguntaSeguridadDAO;
import ec.edu.ups.poo.models.PreguntaSeguridad;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación en memoria de la interfaz {@link PreguntaSeguridadDAO}.
 * <p>
 * Esta clase proporciona una lista fija y predefinida de preguntas de seguridad.
 * Los datos se cargan al momento de la instanciación y son volátiles, es decir,
 * existen únicamente mientras la aplicación está en ejecución.
 * </p>
 */
public class PreguntaSeguridadDAOMemoria implements PreguntaSeguridadDAO {
    private List<PreguntaSeguridad> preguntas;

    /**
     * Construye una nueva instancia del DAO e inicializa la lista de preguntas de seguridad.
     * <p>
     * Se añaden 10 preguntas por defecto, donde el texto de cada pregunta se almacena como
     * una clave de internacionalización (ej. "pregunta.mascota").
     * </p>
     */
    public PreguntaSeguridadDAOMemoria() {
        preguntas = new ArrayList<>();
        preguntas.add(new PreguntaSeguridad(1, "pregunta.mascota"));
        preguntas.add(new PreguntaSeguridad(2, "pregunta.ciudad"));
        preguntas.add(new PreguntaSeguridad(3, "pregunta.color"));
        preguntas.add(new PreguntaSeguridad(4, "pregunta.amigo"));
        preguntas.add(new PreguntaSeguridad(5, "pregunta.comida"));
        preguntas.add(new PreguntaSeguridad(6, "pregunta.profesor"));
        preguntas.add(new PreguntaSeguridad(7, "pregunta.pelicula"));
        preguntas.add(new PreguntaSeguridad(8, "pregunta.colegio"));
        preguntas.add(new PreguntaSeguridad(9, "pregunta.deporte"));
        preguntas.add(new PreguntaSeguridad(10, "pregunta.abuela"));
    }

    /**
     * Devuelve la lista completa y estática de todas las preguntas de seguridad disponibles.
     *
     * @return una {@code List<PreguntaSeguridad>} con todas las preguntas.
     */
    @Override
    public List<PreguntaSeguridad> listarTodas() {
        return preguntas;
    }

    /**
     * Busca una pregunta de seguridad específica por su ID único.
     *
     * @param id El ID de la pregunta a buscar.
     * @return el objeto {@code PreguntaSeguridad} si se encuentra, o {@code null} si no.
     */
    @Override
    public PreguntaSeguridad buscarPorId(int id) {
        for (PreguntaSeguridad pregunta : preguntas) {
            if (pregunta.getId() == id) {
                return pregunta;
            }
        }
        return null;
    }
}