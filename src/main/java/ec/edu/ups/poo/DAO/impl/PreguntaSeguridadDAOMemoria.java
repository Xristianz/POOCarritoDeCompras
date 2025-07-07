package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.PreguntaSeguridadDAO;
import ec.edu.ups.poo.models.PreguntaSeguridad;
import java.util.ArrayList;
import java.util.List;

public class PreguntaSeguridadDAOMemoria implements PreguntaSeguridadDAO {
    private List<PreguntaSeguridad> preguntas;

    public PreguntaSeguridadDAOMemoria() {
        preguntas = new ArrayList<>();
        // Agregamos 10 preguntas de seguridad con claves de internacionalización
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

    @Override
    public List<PreguntaSeguridad> listarTodas() {
        return preguntas;
    }

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