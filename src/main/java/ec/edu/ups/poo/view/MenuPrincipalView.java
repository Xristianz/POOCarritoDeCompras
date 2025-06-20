package ec.edu.ups.poo.view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends JFrame {
    private JMenuBar menuBar;

    private JMenu menuProducto;
    private JMenu menuCarrito;

    private JMenuItem menuItemCrear;
    private JMenuItem menuItemEliminar;
    private JMenuItem menuItemActualizar;
    private JMenuItem menuItemBuscar;

    private JMenuItem menuItemCrearCarrito;

    private JDesktopPane jDesktopPane;

    public MenuPrincipalView() {
        jDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();

        menuProducto = new JMenu("Producto");
        menuCarrito = new JMenu("Carrito");

        menuItemCrear = new JMenuItem("Crear Producto");
        menuItemBuscar = new JMenuItem("Buscar Producto");
        menuItemEliminar = new JMenuItem("Eliminar Producto");
        menuItemActualizar = new JMenuItem("Modificar Producto");

        menuItemCrearCarrito = new JMenuItem("Crear Carrito");

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);

        menuProducto.add(menuItemCrear);
        menuProducto.add(menuItemBuscar);
        menuProducto.add(menuItemEliminar);
        menuProducto.add(menuItemActualizar);

        menuCarrito.add(menuItemCrearCarrito);

        setJMenuBar(menuBar);
        setContentPane(jDesktopPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema De Carrito de Compras");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public JMenu getMenuProducto() {
        return menuProducto;
    }

    public void setMenuProducto(JMenu menuProducto) {
        this.menuProducto = menuProducto;
    }

    public JMenu getMenuCarrito() {
        return menuCarrito;
    }

    public void setMenuCarrito(JMenu menuCarrito) {
        this.menuCarrito = menuCarrito;
    }

    public JMenuItem getMenuItemCrear() {
        return menuItemCrear;
    }

    public void setMenuItemCrear(JMenuItem menuItemCrear) {
        this.menuItemCrear = menuItemCrear;
    }

    public JMenuItem getMenuItemEliminar() {
        return menuItemEliminar;
    }

    public void setMenuItemEliminar(JMenuItem menuItemEliminar) {
        this.menuItemEliminar = menuItemEliminar;
    }

    public JMenuItem getMenuItemActualizar() {
        return menuItemActualizar;
    }

    public void setMenuItemActualizar(JMenuItem menuItemActualizar) {
        this.menuItemActualizar = menuItemActualizar;
    }

    public JMenuItem getMenuItemBuscar() {
        return menuItemBuscar;
    }

    public void setMenuItemBuscar(JMenuItem menuItemBuscar) {
        this.menuItemBuscar = menuItemBuscar;
    }

    public JMenuItem getMenuItemCrearCarrito() {
        return menuItemCrearCarrito;
    }

    public void setMenuItemCrearCarrito(JMenuItem menuItemCrearCarrito) {
        this.menuItemCrearCarrito = menuItemCrearCarrito;
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    public void setjDesktopPane(JDesktopPane jDesktopPane) {
        this.jDesktopPane = jDesktopPane;
    }
}