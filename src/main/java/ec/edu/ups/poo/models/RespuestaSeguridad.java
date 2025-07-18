package ec.edu.ups.poo.models;

import java.io.Serializable;

/**
 * Representa la respuesta de un usuario a una {@link PreguntaSeguridad} específica.
 * <p>
 * Esta clase es serializable para permitir su persistencia en archivos binarios.
 * </p>
 */
public class RespuestaSeguridad implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int preguntaId;
    private String username;
    private String respuesta;

    /**
     * Constructor por defecto.
     */
    public RespuestaSeguridad() {
    }

    /**
     * Construye una nueva respuesta de seguridad.
     *
     * @param id         El identificador único de la respuesta.
     * @param preguntaId El ID de la pregunta a la que corresponde esta respuesta.
     * @param username   El username del usuario que da la respuesta.
     * @param respuesta  El texto de la respuesta.
     */
    public RespuestaSeguridad(int id, int preguntaId, String username, String respuesta) {
        this.id = id;
        this.preguntaId = preguntaId;
        this.username = username;
        this.respuesta = respuesta;
    }

    /**
     * Obtiene el ID de la respuesta.
     * @return el ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID de la respuesta.
     * @param id el nuevo ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el ID de la pregunta asociada.
     * @return el ID de la pregunta.
     */
    public int getPreguntaId() {
        return preguntaId;
    }

    /**
     * Establece el ID de la pregunta asociada.
     * @param preguntaId el nuevo ID de la pregunta.
     */
    public void setPreguntaId(int preguntaId) {
        this.preguntaId = preguntaId;
    }

    /**
     * Obtiene el username del usuario asociado.
     * @return el username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el username del usuario asociado.
     * @param username el nuevo username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el texto de la respuesta.
     * @return la respuesta.
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Establece el texto de la respuesta.
     * @param respuesta la nueva respuesta.
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}