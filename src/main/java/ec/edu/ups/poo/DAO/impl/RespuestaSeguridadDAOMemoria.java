package ec.edu.ups.poo.DAO.impl;


import ec.edu.ups.poo.DAO.RespuestaSeguridadDAO;
import ec.edu.ups.poo.models.RespuestaSeguridad;
import java.util.ArrayList;
import java.util.List;

public class RespuestaSeguridadDAOMemoria implements RespuestaSeguridadDAO {
    private List<RespuestaSeguridad> respuestas;
    private int nextId = 1;

    public RespuestaSeguridadDAOMemoria() {
        respuestas = new ArrayList<>();
    }

    @Override
    public void crear(RespuestaSeguridad respuesta) {
        respuesta.setId(nextId++);
        respuestas.add(respuesta);
    }

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

    @Override
    public RespuestaSeguridad buscarPorPreguntaYUsuario(int preguntaId, String username) {
        for (RespuestaSeguridad r : respuestas) {
            if (r.getPreguntaId() == preguntaId && r.getUsername().equals(username)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void eliminar(int id) {
        respuestas.removeIf(r -> r.getId() == id);
    }

    @Override
    public void eliminarPorUsuario(String username) {
        respuestas.removeIf(r -> r.getUsername().equals(username));
    }
}
