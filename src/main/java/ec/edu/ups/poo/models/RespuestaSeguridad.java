package ec.edu.ups.poo.models;

public class RespuestaSeguridad {
    private int id;
    private int preguntaId;
    private String username;
    private String respuesta;

    public RespuestaSeguridad() {
    }

    public RespuestaSeguridad(int id, int preguntaId, String username, String respuesta) {
        this.id = id;
        this.preguntaId = preguntaId;
        this.username = username;
        this.respuesta = respuesta;
    }

    // Getters y setters...
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(int preguntaId) {
        this.preguntaId = preguntaId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}