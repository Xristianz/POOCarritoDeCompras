package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.CarritoDAO;
import ec.edu.ups.poo.controller.util.Excepciones.DatoNoEncontradoException;
import ec.edu.ups.poo.controller.util.Excepciones.ValidacionException;
import ec.edu.ups.poo.models.Carrito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria de la interfaz CarritoDAO.
 * <p>
 * Esta clase gestiona una colección de objetos {@link Carrito} utilizando una
 * {@link ArrayList}. Los datos son volátiles y se pierden al finalizar la aplicación.
 * </p>
 */
public class CarritoDAOMemoria implements CarritoDAO {

    private List<Carrito> carritos;

    /**
     * Construye una nueva instancia del DAO e inicializa la lista de carritos.
     */
    public CarritoDAOMemoria() {
        this.carritos = new ArrayList<>();
    }

    /**
     * Agrega un nuevo carrito a la lista en memoria.
     *
     * @param carrito El objeto {@code Carrito} a ser creado.
     * @throws ValidacionException si el carrito está vacío (no tiene ítems).
     */
    @Override
    public void crear(Carrito carrito) {
        if (carrito.estaVacio()) {
            throw new ValidacionException("error.carrito.vacio");
        }
        carritos.add(carrito);
    }

    /**
     * Busca un carrito en la lista por su código único.
     *
     * @param codigo El código del carrito a buscar.
     * @return El objeto {@code Carrito} encontrado, o {@code null} si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Actualiza un carrito existente en la lista.
     *
     * @param carrito El objeto {@code Carrito} con los datos actualizados. Su código
     * se utiliza para encontrar el carrito original.
     * @throws DatoNoEncontradoException si no se encuentra ningún carrito con el
     * código especificado.
     */
    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                return;
            }
        }
        throw new DatoNoEncontradoException("error.carrito.no.encontrado");
    }

    /**
     * Elimina un carrito de la lista por su código.
     *
     * @param codigo El código del carrito a eliminar.
     * @throws DatoNoEncontradoException si no se encuentra ningún carrito con el
     * código especificado.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getCodigo() == codigo) {
                iterator.remove();
                return;
            }
        }
        throw new DatoNoEncontradoException("error.carrito.no.encontrado");
    }

    /**
     * Devuelve una copia de la lista de todos los carritos almacenados.
     *
     * @return Una nueva {@code List<Carrito>} con todos los carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

    /**
     * Devuelve una lista de todos los carritos que pertenecen a un usuario específico.
     *
     * @param username El nombre de usuario para filtrar los carritos.
     * @return Una {@code List<Carrito>} con los carritos del usuario especificado.
     */
    @Override
    public List<Carrito> listarPorUsuario(String username) {
        List<Carrito> carritosUsuario = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario().getUsername().equals(username)) {
                carritosUsuario.add(carrito);
            }
        }
        return carritosUsuario;
    }
}