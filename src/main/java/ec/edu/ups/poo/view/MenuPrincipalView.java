package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;

import javax.swing.*;


public class MenuPrincipalView extends JFrame {
    private JMenuBar menuBar;

    private JMenu menuProducto;
    private JMenu menuCarrito;

    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;

    private JMenuItem menuItemCrearCarrito;

    private JDesktopPane jDesktopPane;
    private JMenuItem menuItemCerrarSesion;
    private JMenu menuIdioma;
    private JMenu menuSalir;
    private JMenuItem menuItemIdiomaEspanol;
    private JMenuItem menuItemIdiomaIngles;
    private JMenuItem menuItemIdiomaFrances;
    private JMenuItem menuItemSalir;
    private JMenu menuUsuario;
    private JMenuItem menuItemGestionUsuarios;
    private JMenuItem menuItemListarCarritos;
    private JMenuItem menuItemAgregarUsuario;
    private JMenuItem menuItemActualizarContrasenia;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private JMenuItem menuItemActualizarCarrito;
    private JMenuItem menuItemEliminarCarrito;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public MenuPrincipalView() {
        mensajeInternacionalizacionHandler = new MensajeInternacionalizacionHandler("es", "EC");
        initComponents();



    }

    public JMenuItem getMenuItemCrearProducto() {
        return menuItemCrearProducto;
    }

    public void setMenuItemCrearProducto(JMenuItem menuItemCrearProducto) {
        this.menuItemCrearProducto = menuItemCrearProducto;
    }

    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) {
        this.menuItemEliminarProducto = menuItemEliminarProducto;
    }

    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    public void setMenuItemActualizarProducto(JMenuItem menuItemActualizarProducto) {
        this.menuItemActualizarProducto = menuItemActualizarProducto;
    }

    public JMenuItem getMenuItemBuscarProducto() {
        return menuItemBuscarProducto;
    }

    public void setMenuItemBuscarProducto(JMenuItem menuItemBuscarProducto) {
        this.menuItemBuscarProducto = menuItemBuscarProducto;
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

    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    public void setMenuItemSalir(JMenuItem menuItemSalir) {
        this.menuItemSalir = menuItemSalir;
    }

    public JMenuItem getMenuItemIdiomaEspanol() {
        return menuItemIdiomaEspanol;
    }

    public void setMenuItemIdiomaEspanol(JMenuItem menuItemIdiomaEspanol) {
        this.menuItemIdiomaEspanol = menuItemIdiomaEspanol;
    }

    public JMenuItem getMenuItemIdiomaIngles() {
        return menuItemIdiomaIngles;
    }

    public void setMenuItemIdiomaIngles(JMenuItem menuItemIdiomaIngles) {
        this.menuItemIdiomaIngles = menuItemIdiomaIngles;
    }

    public JMenuItem getMenuItemIdiomaFrances() {
        return menuItemIdiomaFrances;
    }

    public void setMenuItemIdiomaFrances(JMenuItem menuItemIdiomaFrances) {
        this.menuItemIdiomaFrances = menuItemIdiomaFrances;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public JMenuItem getMenuItemCerrarSesion() {
        return menuItemCerrarSesion;
    }

    public JMenuItem getMenuItemListarCarritos() {
        return menuItemListarCarritos;
    }

    public void setMenuItemListarCarritos(JMenuItem menuItemListarCarritos) {
        this.menuItemListarCarritos = menuItemListarCarritos;
    }
    public JMenuItem getMenuItemAgregarUsuario() {
        return menuItemAgregarUsuario;
    }

    public JMenuItem getMenuItemActualizarContrasenia() {
        return menuItemActualizarContrasenia;
    }
    public JMenuItem getMenuItemActualizarCarrito() {
        return menuItemActualizarCarrito;
    }

    public JMenuItem getMenuItemEliminarCarrito() {
        return menuItemEliminarCarrito;
    }


    public void deshabilitarMenusAdministrador() {
        getMenuItemCrearProducto().setEnabled(false);
        getMenuItemBuscarProducto().setEnabled(false);
        getMenuItemActualizarProducto().setEnabled(false);
        getMenuItemEliminarProducto().setEnabled(false);
        getMenuItemGestionUsuarios().setEnabled(false);
        getMenuItemListarCarritos().setEnabled(true);
        getMenuItemAgregarUsuario().setEnabled(false);
        getMenuItemActualizarContrasenia().setEnabled(false);
        getMenuItemActualizarCarrito().setEnabled(true);
        getMenuItemEliminarCarrito().setEnabled(true);
    }
    public JMenuItem getMenuItemGestionUsuarios() {
        return menuItemGestionUsuarios;
    }
    private void initComponents() {
        jDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();

        menuProducto = new JMenu(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuCarrito = new JMenu(mensajeInternacionalizacionHandler.get("menu.carrito"));
        menuIdioma = new JMenu(mensajeInternacionalizacionHandler.get("menu.idiomas"));
        menuSalir = new JMenu(mensajeInternacionalizacionHandler.get("menu.salir"));
        menuUsuario = new JMenu(mensajeInternacionalizacionHandler.get("menu.usuario"));
        menuItemListarCarritos = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.carrito.listar"));

        menuItemCrearProducto = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.producto.crear"));
        menuItemEliminarProducto = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.producto.eliminar"));
        menuItemActualizarProducto = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.producto.actualizar"));
        menuItemBuscarProducto = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.producto.buscar"));

        menuItemCrearCarrito = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.carrito.crear"));

        menuItemIdiomaEspanol = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.idioma.es"));
        menuItemIdiomaIngles = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.idioma.en"));
        menuItemIdiomaFrances = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.idioma.fr"));

        menuItemSalir = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.salir.salir"));
        menuItemCerrarSesion = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.salir.cerrar"));
        menuUsuario = new JMenu(mensajeInternacionalizacionHandler.get("menu.usuario"));
        menuItemGestionUsuarios = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.usuario.gestion"));
        menuItemAgregarUsuario = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.usuario.agregar"));
        menuItemActualizarContrasenia = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.usuario.actualizar"));
        menuItemActualizarCarrito = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.carrito.actualizar"));
        menuItemEliminarCarrito = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.carrito.eliminar"));


        menuBar.add(menuUsuario);
        menuUsuario.add(menuItemGestionUsuarios);

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuUsuario);
        menuBar.add(menuIdioma);
        menuBar.add(menuSalir);

        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemListarCarritos);
        menuCarrito.add(menuItemActualizarCarrito);
        menuCarrito.add(menuItemEliminarCarrito);

        menuUsuario.add(menuItemGestionUsuarios);
        menuUsuario.add(menuItemAgregarUsuario);
        menuUsuario.add(menuItemActualizarContrasenia);

        menuIdioma.add(menuItemIdiomaEspanol);
        menuIdioma.add(menuItemIdiomaIngles);
        menuIdioma.add(menuItemIdiomaFrances);

        menuSalir.add(menuItemSalir);
        menuSalir.add(menuItemCerrarSesion);

        setJMenuBar(menuBar);
        setContentPane(jDesktopPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(mensajeInternacionalizacionHandler.get("app.titulo"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
    public void cambiarIdioma(String lenguaje, String pais) {
        mensajeInternacionalizacionHandler.setLenguaje(lenguaje, pais);

        setTitle(mensajeInternacionalizacionHandler.get("app.titulo"));

        menuProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito"));
        menuIdioma.setText(mensajeInternacionalizacionHandler.get("menu.idiomas"));
        menuSalir.setText(mensajeInternacionalizacionHandler.get("menu.salir"));
        menuUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usuario"));

        menuItemCrearProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.crear"));
        menuItemEliminarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.eliminar"));
        menuItemActualizarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.actualizar"));
        menuItemBuscarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.buscar"));
        menuItemGestionUsuarios.setText(mensajeInternacionalizacionHandler.get("menu.usuario.gestion"));

        menuItemCrearCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito.crear"));
        menuItemListarCarritos.setText(mensajeInternacionalizacionHandler.get("menu.carrito.listar"));

        menuItemIdiomaEspanol.setText(mensajeInternacionalizacionHandler.get("menu.idioma.es"));
        menuItemIdiomaIngles.setText(mensajeInternacionalizacionHandler.get("menu.idioma.en"));
        menuItemIdiomaFrances.setText(mensajeInternacionalizacionHandler.get("menu.idioma.fr"));

        menuItemSalir.setText(mensajeInternacionalizacionHandler.get("menu.salir.salir"));
        menuItemCerrarSesion.setText(mensajeInternacionalizacionHandler.get("menu.salir.cerrar"));
        menuItemAgregarUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usuario.agregar"));
        menuItemActualizarContrasenia.setText(mensajeInternacionalizacionHandler.get("menu.usuario.actualizar"));
        menuItemActualizarCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito.actualizar"));
        menuItemEliminarCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito.eliminar"));


    }


}
