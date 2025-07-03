package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.PreguntaSeguridadDAO;
import ec.edu.ups.poo.models.PreguntaSeguridad;
import java.util.ArrayList;
import java.util.List;

public class PreguntaSeguridadDAOMemoria implements PreguntaSeguridadDAO {
    private List<PreguntaSeguridad> preguntas;

    public PreguntaSeguridadDAOMemoria() {
        preguntas = new ArrayList<>();
        // Agregamos 10 preguntas de seguridad
        preguntas.add(new PreguntaSeguridad(1, "¿Cuál es el nombre de tu primera mascota?"));
        preguntas.add(new PreguntaSeguridad(2, "¿Cuál es tu ciudad de nacimiento?"));
        preguntas.add(new PreguntaSeguridad(3, "¿Cuál es tu color favorito?"));
        preguntas.add(new PreguntaSeguridad(4, "¿Cuál es el nombre de tu mejor amigo de la infancia?"));
        preguntas.add(new PreguntaSeguridad(5, "¿Cuál es tu comida favorita?"));
        preguntas.add(new PreguntaSeguridad(6, "¿Cuál es el nombre de tu profesor favorito?"));
        preguntas.add(new PreguntaSeguridad(7, "¿Cuál es tu película favorita?"));
        preguntas.add(new PreguntaSeguridad(8, "¿Cuál es el nombre de tu primer colegio?"));
        preguntas.add(new PreguntaSeguridad(9, "¿Cuál es tu deporte favorito?"));
        preguntas.add(new PreguntaSeguridad(10, "¿Cuál es el nombre de tu abuela materna?"));
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
