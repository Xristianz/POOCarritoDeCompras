package ec.edu.ups.poo;

import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.DAO.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.controller.CarritoController;
import ec.edu.ups.poo.controller.ProductoController;
import ec.edu.ups.poo.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // DAO
                ProductoDAO productoDAO = new ProductoDAOMemoria();

                // Vistas
                MenuPrincipalView principalView = new MenuPrincipalView();
                ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                ProductoListaView productoListaView = new ProductoListaView();
                ProductoActualizarView productoModificarView = new ProductoActualizarView();
                ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                CarritoAnadirView carritoAnadirView = new CarritoAnadirView();

                // Controladores
                ProductoController productoController = new ProductoController(
                        productoDAO,
                        productoAnadirView,
                        productoListaView,
                        productoEliminarView,
                        productoModificarView,
                        carritoAnadirView
                );

                CarritoController carritoController = new CarritoController(
                        carritoAnadirView,
                        productoDAO
                );

                // Menú Producto - Crear
                principalView.getMenuItemCrear().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!productoAnadirView.isVisible()) {
                            principalView.getjDesktopPane().add(productoAnadirView);
                            productoAnadirView.setVisible(true);
                            productoAnadirView.toFront();
                        }
                    }
                });

                // Menú Producto - Buscar
                principalView.getMenuItemBuscar().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!productoListaView.isVisible()) {
                            principalView.getjDesktopPane().add(productoListaView);
                            productoListaView.setVisible(true);
                            productoListaView.toFront();
                        }
                    }
                });

                // Menú Producto - Eliminar
                principalView.getMenuItemEliminar().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!productoEliminarView.isVisible()) {
                            principalView.getjDesktopPane().add(productoEliminarView);
                            productoEliminarView.setVisible(true);
                            productoEliminarView.toFront();
                        }
                    }
                });

                // Menú Producto - Actualizar
                principalView.getMenuItemActualizar().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!productoModificarView.isVisible()) {
                            principalView.getjDesktopPane().add(productoModificarView);
                            productoModificarView.setVisible(true);
                            productoModificarView.toFront();
                        }
                    }
                });

                // Menú Carrito - Crear
                principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!carritoAnadirView.isVisible()) {
                            principalView.getjDesktopPane().add(carritoAnadirView);
                            carritoAnadirView.setVisible(true);
                            carritoAnadirView.toFront();
                        }
                    }
                });

                // Mostrar ventana principal
                principalView.setVisible(true);
            }
        });
    }
}