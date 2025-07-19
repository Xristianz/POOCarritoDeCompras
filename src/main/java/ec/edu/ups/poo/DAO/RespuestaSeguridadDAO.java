package ec.edu.ups.poo.DAO;

import ec.edu.ups.poo.models.RespuestaSeguridad;
import java.util.List;

public interface RespuestaSeguridadDAO {
    void crear(RespuestaSeguridad respuesta);
    List<RespuestaSeguridad> listarPorUsuario(String username);
    RespuestaSeguridad buscarPorPreguntaYUsuario(int preguntaId, String username);
    void eliminar(int id);
    void eliminarPorUsuario(String username);
}
