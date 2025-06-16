package ec.edu.ups.poo;
import ec.edu.ups.poo.Service.CarritoServiceImpl;
import ec.edu.ups.poo.controller.ProductoController;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.view.ProductoListaView;
import ec.edu.ups.poo.view.ProductoView;


public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProductoView productoView = new ProductoView();
                ProductoListaView productoListaView = new ProductoListaView();
                ProductoDAO productoDAO = new ProductoDAOMemoria();
                CarritoServiceImpl carritoService = new CarritoServiceImpl();
                new ProductoController(
                        productoDAO,
                        productoView,
                        productoListaView,
                        carritoService
                );

                productoView.setVisible(true);
                productoListaView.setVisible(true);
            }
        });
    }
}