package ec.edu.ups.poo.DAO;

import ec.edu.ups.poo.models.PreguntaSeguridad;
import java.util.List;



public interface PreguntaSeguridadDAO {

    List<PreguntaSeguridad> listarTodas();
    PreguntaSeguridad buscarPorId(int id);

}