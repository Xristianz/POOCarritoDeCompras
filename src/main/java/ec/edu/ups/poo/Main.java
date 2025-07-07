package ec.edu.ups.poo;

import ec.edu.ups.poo.DAO.*;
import ec.edu.ups.poo.DAO.impl.*;
import ec.edu.ups.poo.controller.*;
import ec.edu.ups.poo.models.*;
import ec.edu.ups.poo.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    private static ProductoDAO productoDAO = new ProductoDAOMemoria();
    private static CarritoDAO carritoDAO = new CarritoDAOMemoria();
    private static UsuarioDAO usuarioDAO = new UsuarioDAOMemoria(carritoDAO);
    private static PreguntaSeguridadDAO preguntaSeguridadDAO = new PreguntaSeguridadDAOMemoria();
    private static RespuestaSeguridadDAO respuestaSeguridadDAO = new RespuestaSeguridadDAOMemoria();

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
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
                                    carritoListaView, carritoActualizarView, carritoEliminarView, usuarioAutenticado);

                            principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());
                            if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                                principalView.deshabilitarMenusAdministrador();
                            }

                            // Configurar eventos del menú principal
                            configurarEventosMenu(principalView, productoAnadirView, productoListaView,
                                    productoActualizarView, productoEliminarView, carritoAnadirView,
                                    usuarioView, carritoListaView, usuarioAnadirView, usuarioActualizarView,
                                    carritoActualizarView, carritoEliminarView, loginView, usuarioController);

                            // Configurar eventos de idioma
                            configurarEventosIdioma(principalView,
                                    carritoAnadirView,
                                    carritoListaView,
                                    carritoActualizarView,
                                    carritoEliminarView,
                                    detalleCarritoView,
                                    productoAnadirView,
                                    productoListaView,
                                    productoActualizarView,
                                    productoEliminarView);
                        }
                    }
                });
            }
        });
    }

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

        principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!productoAnadirView.isVisible()) {
                    productoAnadirView.setVisible(true);
                    principalView.getjDesktopPane().add(productoAnadirView);
                    try {
                        productoAnadirView.setSelected(true);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!productoListaView.isVisible()) {
                    productoListaView.setVisible(true);
                    principalView.getjDesktopPane().add(productoListaView);
                    try {
                        productoListaView.setSelected(true);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!productoActualizarView.isVisible()) {
                    productoActualizarView.setVisible(true);
                    principalView.getjDesktopPane().add(productoActualizarView);
                    try {
                        productoActualizarView.setSelected(true);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!productoEliminarView.isVisible()) {
                    productoEliminarView.setVisible(true);
                    principalView.getjDesktopPane().add(productoEliminarView);
                    try {
                        productoEliminarView.setSelected(true);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!carritoAnadirView.isVisible()) {
                    carritoAnadirView.setVisible(true);
                    principalView.getjDesktopPane().add(carritoAnadirView);
                    try {
                        carritoAnadirView.setSelected(true);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        principalView.getMenuItemListarCarritos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!carritoListaView.isVisible()) {
                    carritoListaView.setVisible(true);
                    principalView.getjDesktopPane().add(carritoListaView);
                    try {
                        carritoListaView.setSelected(true);
                    } catch (Exception ex) {
                    }
                }
            }
        });
        principalView.getMenuItemEliminarCarrito().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!carritoEliminarView.isVisible()) {
                    carritoEliminarView.setVisible(true);
                    principalView.getjDesktopPane().add(carritoEliminarView);
                    try {
                        carritoEliminarView.setSelected(true);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        principalView.getMenuItemActualizarCarrito().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!carritoActualizarView.isVisible()) {
                    carritoActualizarView.setVisible(true);
                    principalView.getjDesktopPane().add(carritoActualizarView);
                    try {
                        carritoActualizarView.setSelected(true);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        principalView.getMenuItemGestionUsuarios().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!usuarioView.isVisible()) {
                    usuarioView.setVisible(true);
                    principalView.getjDesktopPane().add(usuarioView);
                    usuarioView.cargarDatos(usuarioDAO.listarTodos());
                    usuarioController.configurarEventosUsuarioView(usuarioView);
                    try {
                        usuarioView.setSelected(true);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        principalView.getMenuItemAgregarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!usuarioAnadirView.isVisible()) {
                    usuarioAnadirView.setVisible(true);
                    principalView.getjDesktopPane().add(usuarioAnadirView);
                    usuarioController.configurarEventosUsuarioAnadir(usuarioAnadirView);
                    try {
                        usuarioAnadirView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        principalView.getMenuItemActualizarContrasenia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!usuarioActualizarView.isVisible()) {
                    usuarioActualizarView.setVisible(true);
                    principalView.getjDesktopPane().add(usuarioActualizarView);
                    usuarioController.configurarEventosUsuarioActualizar(usuarioActualizarView);
                    try {
                        usuarioActualizarView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        principalView.getMenuItemCerrarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(
                        principalView,
                        "¿Está seguro que desea cerrar sesión?",
                        "Confirmar cierre de sesión",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    principalView.dispose();
                    usuarioController.cerrarSesion();
                    loginView.setVisible(true);
                }
            }
        });

        principalView.getMenuItemSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(
                        principalView,
                        "¿Está seguro que desea salir del sistema?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    private static void configurarEventosIdioma(MenuPrincipalView principalView,
                                                CarritoAnadirView carritoAnadirView,
                                                CarritoListaView carritoListaView,
                                                CarritoActualizarView carritoActualizarView,
                                                CarritoEliminarView carritoEliminarView,
                                                DetalleCarritoView detalleCarritoView,
                                                ProductoAnadirView productoAnadirView,
                                                ProductoListaView productoListaView,
                                                ProductoActualizarView productoActualizarView,
                                                ProductoEliminarView productoEliminarView) {

        principalView.getMenuItemIdiomaEspanol().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                principalView.cambiarIdioma("es", "EC");

                // Carrito
                carritoAnadirView.getMensajeInternacionalizacion().setLenguaje("es", "EC");
                carritoListaView.getMensajeInternacionalizacion().setLenguaje("es", "EC");
                carritoActualizarView.getMensajeInternacionalizacion().setLenguaje("es", "EC");
                carritoEliminarView.getMensajeInternacionalizacion().setLenguaje("es", "EC");
                detalleCarritoView.getMensajeInternacionalizacion().setLenguaje("es", "EC");

                // Producto
                productoAnadirView.getMensajeInternacionalizacion().setLenguaje("es", "EC");
                productoListaView.getMensajeInternacionalizacion().setLenguaje("es", "EC");
                productoActualizarView.getMensajeInternacionalizacion().setLenguaje("es", "EC");
                productoEliminarView.getMensajeInternacionalizacion().setLenguaje("es", "EC");

                // Actualizar textos
                carritoAnadirView.actualizarTextos();
                carritoListaView.actualizarTextos();
                carritoActualizarView.actualizarTextos();
                carritoEliminarView.actualizarTextos();
                detalleCarritoView.actualizarTextos();
                productoAnadirView.actualizarTextos();
                productoListaView.actualizarTextos();
                productoActualizarView.actualizarTextos();
                productoEliminarView.actualizarTextos();
            }
        });

        principalView.getMenuItemIdiomaIngles().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                principalView.cambiarIdioma("en", "US");

                // Carrito
                carritoAnadirView.getMensajeInternacionalizacion().setLenguaje("en", "US");
                carritoListaView.getMensajeInternacionalizacion().setLenguaje("en", "US");
                carritoActualizarView.getMensajeInternacionalizacion().setLenguaje("en", "US");
                carritoEliminarView.getMensajeInternacionalizacion().setLenguaje("en", "US");
                detalleCarritoView.getMensajeInternacionalizacion().setLenguaje("en", "US");

                // Producto
                productoAnadirView.getMensajeInternacionalizacion().setLenguaje("en", "US");
                productoListaView.getMensajeInternacionalizacion().setLenguaje("en", "US");
                productoActualizarView.getMensajeInternacionalizacion().setLenguaje("en", "US");
                productoEliminarView.getMensajeInternacionalizacion().setLenguaje("en", "US");

                // Actualizar textos
                carritoAnadirView.actualizarTextos();
                carritoListaView.actualizarTextos();
                carritoActualizarView.actualizarTextos();
                carritoEliminarView.actualizarTextos();
                detalleCarritoView.actualizarTextos();
                productoAnadirView.actualizarTextos();
                productoListaView.actualizarTextos();
                productoActualizarView.actualizarTextos();
                productoEliminarView.actualizarTextos();
            }
        });

        principalView.getMenuItemIdiomaFrances().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                principalView.cambiarIdioma("fr", "FR");

                // Carrito
                carritoAnadirView.getMensajeInternacionalizacion().setLenguaje("fr", "FR");
                carritoListaView.getMensajeInternacionalizacion().setLenguaje("fr", "FR");
                carritoActualizarView.getMensajeInternacionalizacion().setLenguaje("fr", "FR");
                carritoEliminarView.getMensajeInternacionalizacion().setLenguaje("fr", "FR");
                detalleCarritoView.getMensajeInternacionalizacion().setLenguaje("fr", "FR");

                // Producto
                productoAnadirView.getMensajeInternacionalizacion().setLenguaje("fr", "FR");
                productoListaView.getMensajeInternacionalizacion().setLenguaje("fr", "FR");
                productoActualizarView.getMensajeInternacionalizacion().setLenguaje("fr", "FR");
                productoEliminarView.getMensajeInternacionalizacion().setLenguaje("fr", "FR");

                // Actualizar textos
                carritoAnadirView.actualizarTextos();
                carritoListaView.actualizarTextos();
                carritoActualizarView.actualizarTextos();
                carritoEliminarView.actualizarTextos();
                detalleCarritoView.actualizarTextos();
                productoAnadirView.actualizarTextos();
                productoListaView.actualizarTextos();
                productoActualizarView.actualizarTextos();
                productoEliminarView.actualizarTextos();
            }
        });
    }
}