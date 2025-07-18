package ec.edu.ups.poo;

import ec.edu.ups.poo.DAO.*;
import ec.edu.ups.poo.DAO.impl.*;
import ec.edu.ups.poo.controller.*;
import ec.edu.ups.poo.models.*;
import ec.edu.ups.poo.view.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * La clase Main es el punto de entrada de la aplicación y gestiona la inicialización del sistema,
 * la configuración de la persistencia de datos (en memoria o en archivos), el flujo de inicio de sesión de usuarios,
 * y la configuración de eventos para la interfaz de usuario principal y sus sub-ventanas.
 * Permite seleccionar el modo de almacenamiento al inicio y maneja la internacionalización de la interfaz.
 */
public class Main {
    private static ProductoDAO productoDAO;
    private static CarritoDAO carritoDAO;
    private static UsuarioDAO usuarioDAO;
    private static PreguntaSeguridadDAO preguntaSeguridadDAO;
    private static RespuestaSeguridadDAO respuestaSeguridadDAO;

    /**
     * Método principal que inicia la aplicación.
     * Muestra una ventana de selección para que el usuario elija el modo de persistencia (Archivo o Memoria)
     * y, si es el caso, la ruta del archivo.
     * Una vez que el usuario selecciona y acepta, se inicializa la aplicación con la configuración elegida.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // El programa ahora inicia mostrando la ventana de selección.
        SwingUtilities.invokeLater(() -> {
            Seleccionador seleccionadorView = new Seleccionador();

            // Se añade un listener al botón "Aceptar" de la ventana de selección.
            seleccionadorView.addAceptarListener(e -> {
                String persistencia = seleccionadorView.getOpcionSeleccionada();
                String ruta = seleccionadorView.getRutaSeleccionada();

                if (persistencia.equals("Archivo") && (ruta == null || ruta.trim().isEmpty())) {
                    seleccionadorView.mostrarError("error.ruta.vacia");
                    return;
                }
                seleccionadorView.dispose();
                iniciarAplicacion(persistencia, ruta);
            });

            seleccionadorView.setVisible(true);
        });
    }

    /**
     * Inicializa las implementaciones de los DAOs (Data Access Objects) basándose en el tipo de persistencia seleccionado.
     * Si el tipo es "Archivo", se utilizan implementaciones que guardan los datos en archivos binarios o de texto en la ruta especificada.
     * Si el tipo es "Memoria" (por defecto), se utilizan implementaciones que guardan los datos solo en memoria RAM.
     * Después de la inicialización de DAOs, se inicia el flujo de login.
     *
     * @param tipoPersistencia El tipo de persistencia a utilizar ("Archivo" o "Memoria").
     * @param ruta La ruta del directorio para la persistencia de archivos (ignorada si es "Memoria").
     */
    private static void iniciarAplicacion(String tipoPersistencia, String ruta) {
        // --- Factory de DAOs ---
        switch (tipoPersistencia) {
            case "Archivo":
                System.out.println("Modo de persistencia: Archivo. Ruta: " + ruta);

                productoDAO = new ProductoDAOBinario(ruta);
                preguntaSeguridadDAO = new PreguntaSeguridadDaoTexto(ruta);
                respuestaSeguridadDAO = new RespuestaSeguridadDaoBinario(ruta);

                UsuarioDaoTexto tempUserDAO = new UsuarioDaoTexto(ruta);
                CarritoDaoTexto tempCartDAO = new CarritoDaoTexto(productoDAO, ruta);

                tempUserDAO.setCarritoDAO(tempCartDAO);
                tempCartDAO.setUsuarioDAO(tempUserDAO);

                usuarioDAO = tempUserDAO;
                carritoDAO = tempCartDAO;
                break;

            case "Memoria":
            default:
                System.out.println("Modo de persistencia: Todo en Memoria");
                productoDAO = new ProductoDAOMemoria();
                preguntaSeguridadDAO = new PreguntaSeguridadDAOMemoria();
                respuestaSeguridadDAO = new RespuestaSeguridadDAOMemoria();
                carritoDAO = new CarritoDAOMemoria();
                usuarioDAO = new UsuarioDAOMemoria(carritoDAO);
                break;
        }

        // --- Se inicia el flujo de login por primera vez ---
        iniciarFlujoDeLogin();
    }

    /**
     * Este nuevo método encapsula la lógica para mostrar la ventana de Login y configurar sus controladores.
     * Permite reutilizar el flujo de inicio de sesión sin tener que reiniciar toda la aplicación.
     */
    private static void iniciarFlujoDeLogin() {
        LoginView loginView = new LoginView();
        loginView.setVisible(true);

        UsuarioController usuarioController = new UsuarioController(
                usuarioDAO, preguntaSeguridadDAO, respuestaSeguridadDAO, loginView);

        loginView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();
                if (usuarioAutenticado != null) {
                    // Instanciar vistas
                    MenuPrincipalView principalView = new MenuPrincipalView();
                    ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                    ProductoListaView productoListaView = new ProductoListaView();
                    ProductoActualizarView productoActualizarView = new ProductoActualizarView();
                    ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                    CarritoAnadirView carritoAnadirView = new CarritoAnadirView();
                    CarritoListaView carritoListaView = new CarritoListaView();
                    CarritoEliminarView carritoEliminarView = new CarritoEliminarView();
                    CarritoActualizarView carritoActualizarView = new CarritoActualizarView();
                    UsuarioView usuarioView = new UsuarioView();
                    UsuarioAnadir usuarioAnadirView = new UsuarioAnadir();
                    UsuarioActualizar usuarioActualizarView = new UsuarioActualizar();
                    DetalleCarritoView detalleCarritoView = new DetalleCarritoView();

                    // Instanciar controladores
                    ProductoController productoController = new ProductoController(
                            productoDAO, productoAnadirView, productoListaView,
                            carritoAnadirView, productoActualizarView, productoEliminarView);

                    CarritoController carritoController = new CarritoController(
                            carritoDAO, productoDAO, carritoAnadirView,
                            carritoListaView, carritoActualizarView, carritoEliminarView,
                            detalleCarritoView,
                            usuarioAutenticado);

                    principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());
                    if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                        principalView.deshabilitarMenusAdministrador();
                    }

                    configurarEventosMenu(principalView, productoAnadirView, productoListaView,
                            productoActualizarView, productoEliminarView, carritoAnadirView,
                            usuarioView, carritoListaView, usuarioAnadirView, usuarioActualizarView,
                            carritoActualizarView, carritoEliminarView, loginView, usuarioController);

                    configurarEventosIdioma(principalView,
                            carritoAnadirView, carritoListaView, carritoActualizarView,
                            carritoEliminarView, detalleCarritoView, productoAnadirView,
                            productoListaView, productoActualizarView, productoEliminarView,
                            usuarioView, usuarioAnadirView, usuarioActualizarView,
                            carritoController);
                }
            }
        });
    }

    /**
     * Configura los ActionListeners para los elementos del menú principal.
     * Cada elemento del menú abre una JInternalFrame correspondiente si no está ya visible.
     * También maneja la lógica para cerrar sesión y salir de la aplicación, con diálogos de confirmación.
     *
     * @param principalView La vista principal (MenuPrincipalView).
     * @param productoAnadirView La vista para añadir productos.
     * @param productoListaView La vista para listar productos.
     * @param productoActualizarView La vista para actualizar productos.
     * @param productoEliminarView La vista para eliminar productos.
     * @param carritoAnadirView La vista para añadir ítems al carrito.
     * @param usuarioView La vista para gestionar usuarios.
     * @param carritoListaView La vista para listar carritos.
     * @param usuarioAnadirView La vista para añadir usuarios.
     * @param usuarioActualizarView La vista para actualizar usuarios.
     * @param carritoActualizarView La vista para actualizar carritos.
     * @param carritoEliminarView La vista para eliminar carritos.
     * @param loginView La vista de login (utilizada para reiniciar el flujo).
     * @param usuarioController El controlador de usuarios.
     */
    private static void configurarEventosMenu(MenuPrincipalView principalView,
                                              ProductoAnadirView productoAnadirView,
                                              ProductoListaView productoListaView,
                                              ProductoActualizarView productoActualizarView,
                                              ProductoEliminarView productoEliminarView,
                                              CarritoAnadirView carritoAnadirView,
                                              UsuarioView usuarioView,
                                              CarritoListaView carritoListaView,
                                              UsuarioAnadir usuarioAnadirView,
                                              UsuarioActualizar usuarioActualizarView,
                                              CarritoActualizarView carritoActualizarView,
                                              CarritoEliminarView carritoEliminarView,
                                              LoginView loginView,
                                              UsuarioController usuarioController) {

        principalView.getMenuItemCrearProducto().addActionListener(e -> {
            if (!productoAnadirView.isVisible()) {
                productoAnadirView.setVisible(true);
                principalView.getjDesktopPane().add(productoAnadirView);
                try {
                    productoAnadirView.setSelected(true);
                } catch (Exception ex) {}
            }
        });

        principalView.getMenuItemBuscarProducto().addActionListener(e -> {
            if (!productoListaView.isVisible()) {
                productoListaView.setVisible(true);
                principalView.getjDesktopPane().add(productoListaView);
                try {
                    productoListaView.setSelected(true);
                } catch (Exception ex) {}
            }
        });

        principalView.getMenuItemActualizarProducto().addActionListener(e -> {
            if (!productoActualizarView.isVisible()) {
                productoActualizarView.setVisible(true);
                principalView.getjDesktopPane().add(productoActualizarView);
                try {
                    productoActualizarView.setSelected(true);
                } catch (Exception ex) {}
            }
        });

        principalView.getMenuItemEliminarProducto().addActionListener(e -> {
            if (!productoEliminarView.isVisible()) {
                productoEliminarView.setVisible(true);
                principalView.getjDesktopPane().add(productoEliminarView);
                try {
                    productoEliminarView.setSelected(true);
                } catch (Exception ex) {}
            }
        });

        principalView.getMenuItemCrearCarrito().addActionListener(e -> {
            if (!carritoAnadirView.isVisible()) {
                carritoAnadirView.setVisible(true);
                principalView.getjDesktopPane().add(carritoAnadirView);
                try {
                    carritoAnadirView.setSelected(true);
                } catch (Exception ex) {}
            }
        });

        principalView.getMenuItemListarCarritos().addActionListener(e -> {
            if (!carritoListaView.isVisible()) {
                carritoListaView.setVisible(true);
                principalView.getjDesktopPane().add(carritoListaView);
                try {
                    carritoListaView.setSelected(true);
                } catch (Exception ex) {}
            }
        });

        principalView.getMenuItemActualizarCarrito().addActionListener(e -> {
            if (!carritoActualizarView.isVisible()) {
                carritoActualizarView.setVisible(true);
                principalView.getjDesktopPane().add(carritoActualizarView);
                try {
                    carritoActualizarView.setSelected(true);
                } catch (Exception ex) {}
            }
        });

        principalView.getMenuItemEliminarCarrito().addActionListener(e -> {
            if (!carritoEliminarView.isVisible()) {
                carritoEliminarView.setVisible(true);
                principalView.getjDesktopPane().add(carritoEliminarView);
                try {
                    carritoEliminarView.setSelected(true);
                } catch (Exception ex) {}
            }
        });

        principalView.getMenuItemGestionUsuarios().addActionListener(e -> {
            if (!usuarioView.isVisible()) {
                usuarioView.setVisible(true);
                principalView.getjDesktopPane().add(usuarioView);
                usuarioView.cargarDatos(usuarioDAO.listarTodos());
                usuarioController.configurarEventosUsuarioView(usuarioView);
                try {
                    usuarioView.setSelected(true);
                } catch (Exception ex) {}
            }
        });

        principalView.getMenuItemAgregarUsuario().addActionListener(e -> {
            if (!usuarioAnadirView.isVisible()) {
                usuarioAnadirView.setVisible(true);
                principalView.getjDesktopPane().add(usuarioAnadirView);
                usuarioController.configurarEventosUsuarioAnadir(usuarioAnadirView);
                try {
                    usuarioAnadirView.setSelected(true);
                } catch (Exception ex) {}
            }
        });

        principalView.getMenuItemActualizarContrasenia().addActionListener(e -> {
            if (!usuarioActualizarView.isVisible()) {
                usuarioActualizarView.setVisible(true);
                principalView.getjDesktopPane().add(usuarioActualizarView);
                usuarioController.configurarEventosUsuarioActualizar(usuarioActualizarView);
                try {
                    usuarioActualizarView.setSelected(true);
                } catch (Exception ex) {}
            }
        });

        principalView.getMenuItemCerrarSesion().addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                    principalView,
                    principalView.getMensajeInternacionalizacionHandler().get("mensaje.confirmar_cierre_sesion"),
                    principalView.getMensajeInternacionalizacionHandler().get("titulo.confirmar_cierre_sesion"),
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                principalView.dispose();

                iniciarFlujoDeLogin();
            }
        });

        principalView.getMenuItemSalir().addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                    principalView,
                    principalView.getMensajeInternacionalizacionHandler().get("mensaje.confirmar_salida"),
                    principalView.getMensajeInternacionalizacionHandler().get("titulo.confirmar_salida"),
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    /**
     * Configura los ActionListeners para los elementos del menú de idioma en la vista principal.
     * Cuando se selecciona un idioma, se actualiza el `MensajeInternacionalizacionHandler` de la vista principal
     * y de todas las sub-vistas internas, y luego se llama al método `actualizarTextos()` en cada vista
     * para reflejar el cambio de idioma.
     * También refresca las listas de carritos si las vistas correspondientes están visibles.
     *
     * @param principalView La vista principal (MenuPrincipalView).
     * @param carritoAnadirView La vista para añadir ítems al carrito.
     * @param carritoListaView La vista para listar carritos.
     * @param carritoActualizarView La vista para actualizar carritos.
     * @param carritoEliminarView La vista para eliminar carritos.
     * @param detalleCarritoView La vista para detalles de carrito.
     * @param productoAnadirView La vista para añadir productos.
     * @param productoListaView La vista para listar productos.
     * @param productoActualizarView La vista para actualizar productos.
     * @param productoEliminarView La vista para eliminar productos.
     * @param usuarioView La vista para gestionar usuarios.
     * @param usuarioAnadirView La vista para añadir usuarios.
     * @param usuarioActualizarView La vista para actualizar usuarios.
     * @param carritoController El controlador de carritos (utilizado para refrescar listas).
     */
    private static void configurarEventosIdioma(MenuPrincipalView principalView,
                                                CarritoAnadirView carritoAnadirView,
                                                CarritoListaView carritoListaView,
                                                CarritoActualizarView carritoActualizarView,
                                                CarritoEliminarView carritoEliminarView,
                                                DetalleCarritoView detalleCarritoView,
                                                ProductoAnadirView productoAnadirView,
                                                ProductoListaView productoListaView,
                                                ProductoActualizarView productoActualizarView,
                                                ProductoEliminarView productoEliminarView,
                                                UsuarioView usuarioView,
                                                UsuarioAnadir usuarioAnadirView,
                                                UsuarioActualizar usuarioActualizarView,
                                                CarritoController carritoController) {

        ActionListener cambiarIdiomaListener = e -> {
            String language = "es";
            String country = "EC";
            if (e.getSource() == principalView.getMenuItemIdiomaIngles()) {
                language = "en";
                country = "US";
            } else if (e.getSource() == principalView.getMenuItemIdiomaFrances()) {
                language = "fr";
                country = "FR";
            }

            principalView.cambiarIdioma(language, country);
            carritoAnadirView.getMensajeInternacionalizacion().setLenguaje(language, country);
            carritoListaView.getMensajeInternacionalizacion().setLenguaje(language, country);
            carritoActualizarView.getMensajeInternacionalizacion().setLenguaje(language, country);
            carritoEliminarView.getMensajeInternacionalizacion().setLenguaje(language, country);
            detalleCarritoView.getMensajeInternacionalizacion().setLenguaje(language, country);
            productoAnadirView.getMensajeInternacionalizacion().setLenguaje(language, country);
            productoListaView.getMensajeInternacionalizacion().setLenguaje(language, country);
            productoActualizarView.getMensajeInternacionalizacion().setLenguaje(language, country);
            productoEliminarView.getMensajeInternacionalizacion().setLenguaje(language, country);
            usuarioView.getMensajeHandler().setLenguaje(language, country);
            usuarioAnadirView.getMensajeHandler().setLenguaje(language, country);
            usuarioActualizarView.getMensajeHandler().setLenguaje(language, country);

            carritoAnadirView.actualizarTextos();
            carritoListaView.actualizarTextos();
            carritoActualizarView.actualizarTextos();
            carritoEliminarView.actualizarTextos();
            detalleCarritoView.actualizarTextos();
            productoAnadirView.actualizarTextos();
            productoListaView.actualizarTextos();
            productoActualizarView.actualizarTextos();
            productoEliminarView.actualizarTextos();
            usuarioView.internacionalizar();
            usuarioAnadirView.internacionalizar();
            usuarioActualizarView.internacionalizar();

            if (carritoListaView.isVisible()) {
                carritoController.listarCarritos();
            }
            if (carritoEliminarView.isVisible()) {
                carritoController.actualizarListaCarritos();
            }
        };

        principalView.getMenuItemIdiomaEspanol().addActionListener(cambiarIdiomaListener);
        principalView.getMenuItemIdiomaIngles().addActionListener(cambiarIdiomaListener);
        principalView.getMenuItemIdiomaFrances().addActionListener(cambiarIdiomaListener);
    }
}