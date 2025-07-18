package ec.edu.ups.poo.models;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import java.io.Serializable;

/**
 * Representa una pregunta de seguridad utilizada para la recuperación de contraseñas.
 */
public class PreguntaSeguridad implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String textoKey;

    /**
     * Constructor por defecto.
     */
    public PreguntaSeguridad() {
    }

    /**
     * Construye una nueva pregunta de seguridad.
     *
     * @param id       El identificador único de la pregunta.
     * @param textoKey La clave de internacionalización para el texto de la pregunta.
     */
    public PreguntaSeguridad(int id, String textoKey) {
        this.id = id;
        this.textoKey = textoKey;
    }

    /**
     * Obtiene el ID de la pregunta.
     * @return el ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID de la pregunta.
     * @param id el nuevo ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la clave de internacionalización para el texto de la pregunta.
     * @return la clave del texto.
     */
    public String getTextoKey() {
        return textoKey;
    }

    /**
     * Establece la clave de internacionalización.
     * @param textoKey la nueva clave de texto.
     */
    public void setTextoKey(String textoKey) {
        this.textoKey = textoKey;
    }

    /**
     * Obtiene el texto de la pregunta traducido al idioma actual.
     *
     * @param handler El manejador de internacionalización.
     * @return El texto de la pregunta traducido.
     */
    public String getTexto(MensajeInternacionalizacionHandler handler) {
        return handler.get(textoKey);
    }

    @Override
    public String toString() {
        return textoKey;
    }
}