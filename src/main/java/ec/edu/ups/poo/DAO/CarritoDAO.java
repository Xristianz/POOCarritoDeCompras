package ec.edu.ups.poo.DAO;

import ec.edu.ups.poo.models.Carrito;

import java.util.List;

public interface CarritoDAO {

    void crear(Carrito carrito);

    Carrito buscarPorCodigo(int codigo);

    void actualizar(Carrito carrito);

    void eliminar(int codigo);

    List<Carrito> listarTodos();
    List<Carrito> listarPorUsuario(String username);

}