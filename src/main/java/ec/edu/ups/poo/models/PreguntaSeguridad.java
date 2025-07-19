package ec.edu.ups.poo.models;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;

public class PreguntaSeguridad {
    private int id;
    private String textoKey;

    public PreguntaSeguridad() {
    }

    public PreguntaSeguridad(int id, String textoKey) {
        this.id = id;
        this.textoKey = textoKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextoKey() {
        return textoKey;
    }

    public void setTextoKey(String textoKey) {
        this.textoKey = textoKey;
    }

    // Método para obtener el texto traducido
    public String getTexto(MensajeInternacionalizacionHandler handler) {
        return handler.get(textoKey);
    }

    @Override
    public String toString() {
        return textoKey;
    }
}