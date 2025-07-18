package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import javax.swing.*;

/**
 * Vista (JFrame) principal de la aplicación que se muestra después del inicio de sesión.
 * <p>
 * Actúa como el contenedor principal, utilizando un {@link MiJDesktopPane} personalizado
 * para albergar todas las ventanas internas (JInternalFrames) de la aplicación.
 * Contiene la barra de menú con todas las opciones de navegación y funcionalidades.
 * </p>
 */
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

    /**
     * Construye la ventana del menú principal e inicializa sus componentes.
     */
    public MenuPrincipalView() {
        mensajeInternacionalizacionHandler = new MensajeInternacionalizacionHandler("es", "EC");
        initComponents();
    }

    /**
     * Inicializa y ensambla todos los componentes de la interfaz gráfica.
     * <p>
     * Este método construye la barra de menú completa, la asocia al JFrame y
     * establece el panel de escritorio personalizado como el contenedor principal.
     * </p>
     */
    private void initComponents() {
        jDesktopPane = new MiJDesktopPane();
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

    /**
     * Cambia el idioma de todos los textos del menú y el título de la ventana.
     *
     * @param lenguaje El código de lenguaje de dos letras (ej. "es").
     * @param pais     El código de país de dos letras (ej. "EC").
     */
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

    /**
     * Deshabilita las opciones del menú que son exclusivas para el rol de Administrador.
     * Este método se invoca cuando inicia sesión un usuario con rol {@code USUARIO}.
     */
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

    /**
     * Muestra un mensaje de diálogo simple al usuario.
     * @param mensaje El texto a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * A continuación se presentan los métodos de acceso (getters y setters) para
     * los componentes de la interfaz de usuario. Estos son utilizados por los
     * controladores para añadir listeners de eventos y manipular la vista.
     */
    public JMenuItem getMenuItemCrearProducto() { return menuItemCrearProducto; }
    public JMenuItem getMenuItemEliminarProducto() { return menuItemEliminarProducto; }
    public JMenuItem getMenuItemActualizarProducto() { return menuItemActualizarProducto; }
    public JMenuItem getMenuItemBuscarProducto() { return menuItemBuscarProducto; }
    public JMenuItem getMenuItemCrearCarrito() { return menuItemCrearCarrito; }
    public JDesktopPane getjDesktopPane() { return jDesktopPane; }
    public JMenuItem getMenuItemSalir() { return menuItemSalir; }
    public JMenuItem getMenuItemIdiomaEspanol() { return menuItemIdiomaEspanol; }
    public JMenuItem getMenuItemIdiomaIngles() { return menuItemIdiomaIngles; }
    public JMenuItem getMenuItemIdiomaFrances() { return menuItemIdiomaFrances; }
    public JMenuItem getMenuItemCerrarSesion() { return menuItemCerrarSesion; }
    public JMenuItem getMenuItemListarCarritos() { return menuItemListarCarritos; }
    public JMenuItem getMenuItemAgregarUsuario() { return menuItemAgregarUsuario; }
    public JMenuItem getMenuItemActualizarContrasenia() { return menuItemActualizarContrasenia; }
    public JMenuItem getMenuItemActualizarCarrito() { return menuItemActualizarCarrito; }
    public JMenuItem getMenuItemEliminarCarrito() { return menuItemEliminarCarrito; }
    public MensajeInternacionalizacionHandler getMensajeInternacionalizacionHandler() { return mensajeInternacionalizacionHandler; }
    public JMenuItem getMenuItemGestionUsuarios() { return menuItemGestionUsuarios; }
}