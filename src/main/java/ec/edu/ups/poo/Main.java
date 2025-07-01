package ec.edu.ups.poo;

import ec.edu.ups.poo.DAO.CarritoDAO;
import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.DAO.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.DAO.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.DAO.UsuarioDAO;
import ec.edu.ups.poo.DAO.impl.UsuarioDAOMemoria;
import ec.edu.ups.poo.controller.CarritoController;
import ec.edu.ups.poo.controller.ProductoController;
import ec.edu.ups.poo.controller.UsuarioController;
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
    private static UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                //Iniciar sesión

                LoginView loginView = new LoginView();
                loginView.setVisible(true);

                UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

                loginView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {

                        Usuario usuarioAuntenticado = usuarioController.getUsuarioAutenticado();
                        if (usuarioAuntenticado != null) {


                            //instancio Vistas
                            MenuPrincipalView principalView = new MenuPrincipalView();
                            ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                            ProductoListaView productoListaView = new ProductoListaView();
                            ProductoActualizarView productoActualizarView = new ProductoActualizarView();
                            ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                            CarritoAnadirView carritoAnadirView = new CarritoAnadirView();
                            UsuarioView usuarioView = new UsuarioView();
                            CarritoListaView carritoListaView = new CarritoListaView();
                            UsuarioAnadir usuarioAnadirView = new UsuarioAnadir();
                            UsuarioActualizar usuarioActualizarView = new UsuarioActualizar();
                            CarritoActualizarView carritoActualizarView = new CarritoActualizarView();
                            CarritoEliminarView carritoEliminarView = new CarritoEliminarView();




                            //instanciamos Controladores
                            ProductoController productoController = new ProductoController(productoDAO,
                                    productoAnadirView,
                                    productoListaView,
                                    carritoAnadirView,
                                    productoActualizarView,
                                    productoEliminarView);
                            CarritoController carritoController = new CarritoController(
                                    carritoDAO,
                                    productoDAO,
                                    carritoAnadirView,
                                    carritoListaView,
                                    carritoActualizarView,
                                    usuarioAuntenticado);

                            principalView.mostrarMensaje("Bienvenido: " + usuarioAuntenticado.getUsername());
                            if (usuarioAuntenticado.getRol().equals(Rol.USUARIO)) {
                                principalView.deshabilitarMenusAdministrador();
                            }

                            principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoAnadirView.isVisible()){
                                        productoAnadirView.setVisible(true);
                                        principalView.getjDesktopPane().add(productoAnadirView);
                                    }
                                }
                            });

                            principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoListaView.isVisible()){
                                        productoListaView.setVisible(true);
                                        principalView.getjDesktopPane().add(productoListaView);
                                    }
                                }
                            });
                            principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoActualizarView.isVisible()) {
                                        productoActualizarView.setVisible(true);
                                        principalView.getjDesktopPane().add(productoActualizarView);
                                    }
                                }
                            });
                            principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoEliminarView.isVisible()) {
                                        productoEliminarView.setVisible(true);
                                        principalView.getjDesktopPane().add(productoEliminarView);
                                    }
                                }
                            });

                            principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoAnadirView.isVisible()){
                                        carritoAnadirView.setVisible(true);
                                        principalView.getjDesktopPane().add(carritoAnadirView);
                                    }
                                }
                            });
                            principalView.getMenuItemCerrarSesion().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    principalView.dispose();
                                    usuarioController.cerrarSesion();
                                    loginView.setVisible(true);
                                }
                            });
                            principalView.getMenuItemIdiomaEspanol().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    principalView.cambiarIdioma("es", "EC");
                                }
                            });

                            principalView.getMenuItemIdiomaIngles().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    principalView.cambiarIdioma("en", "US");
                                }
                            });

                            principalView.getMenuItemIdiomaFrances().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    principalView.cambiarIdioma("fr", "FR");
                                }
                            });
                            principalView.getMenuItemSalir().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.exit(0);
                                }
                            });
                            principalView.getMenuItemGestionUsuarios().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!usuarioView.isVisible()){
                                        usuarioView.setVisible(true);
                                        principalView.getjDesktopPane().add(usuarioView);
                                    }
                                }
                            });

                            // Configurar eventos en UsuarioView
                            usuarioView.getBtnListar().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    usuarioView.cargarDatos(usuarioDAO.listarTodos());
                                }
                            });

                            usuarioView.getBtnEliminar().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String username = usuarioView.getTxtUsername().getText();
                                    if(username.isEmpty()) {
                                        usuarioView.mostrarMensaje("Ingrese un nombre de usuario");
                                        return;
                                    }

                                    Usuario usuario = usuarioDAO.buscarPorUsername(username);
                                    if(usuario == null) {
                                        usuarioView.mostrarMensaje("Usuario no encontrado");
                                        return;
                                    }

                                    if(usuario.getRol() == Rol.ADMINISTRADOR) {
                                        usuarioView.mostrarMensaje("No se puede eliminar un administrador");
                                        return;
                                    }

                                    usuarioDAO.eliminar(username);
                                    usuarioView.mostrarMensaje("Usuario eliminado correctamente");
                                    usuarioView.cargarDatos(usuarioDAO.listarTodos());
                                    usuarioView.getTxtUsername().setText("");
                                }
                            });

                            principalView.getMenuItemAgregarUsuario().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!usuarioAnadirView.isVisible()) {
                                        usuarioAnadirView.setVisible(true);
                                        principalView.getjDesktopPane().add(usuarioAnadirView);
                                    }
                                }
                            });

                            principalView.getMenuItemActualizarContrasenia().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!usuarioActualizarView.isVisible()) {
                                        usuarioActualizarView.setVisible(true);
                                        principalView.getjDesktopPane().add(usuarioActualizarView);
                                    }
                                }
                            });
                            usuarioAnadirView.getBtnAgregarUsuario().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String username = usuarioAnadirView.getTxtUsername().getText();
                                    String contrasenia = new String(usuarioAnadirView.getTxtContrasenia().getPassword());
                                    String pregunta1 = usuarioAnadirView.getTxtPregunta1().getText();
                                    String pregunta2 = usuarioAnadirView.getTxtPregunta2().getText();
                                    String pregunta3 = usuarioAnadirView.getTxtPregunta3().getText();

                                    if(username.isEmpty() || contrasenia.isEmpty() || pregunta1.isEmpty() || pregunta2.isEmpty() || pregunta3.isEmpty()) {
                                        usuarioAnadirView.mostrarMensaje("Todos los campos son requeridos");
                                        return;
                                    }

                                    if(usuarioDAO.buscarPorUsername(username) != null) {
                                        usuarioAnadirView.mostrarMensaje("El usuario ya existe");
                                        return;
                                    }

                                    Usuario nuevoUsuario = new Usuario(username, contrasenia, Rol.USUARIO, pregunta1, pregunta2, pregunta3);
                                    usuarioDAO.crear(nuevoUsuario);
                                    usuarioAnadirView.mostrarMensaje("Usuario creado exitosamente");
                                    usuarioAnadirView.limpiarCampos();
                                }
                            });

                            usuarioActualizarView.getBtnBuscar().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String username = usuarioActualizarView.getTxtUsername().getText();
                                    if(username.isEmpty()) {
                                        usuarioActualizarView.mostrarMensaje("Ingrese un nombre de usuario");
                                        return;
                                    }

                                    Usuario usuario = usuarioDAO.buscarPorUsername(username);
                                    if(usuario == null) {
                                        usuarioActualizarView.mostrarMensaje("Usuario no encontrado");
                                        return;
                                    }

                                    usuarioActualizarView.cargarDatosUsuario(usuario);
                                }
                            });
                            usuarioActualizarView.getBtnActualizar().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String username = usuarioActualizarView.getTxtUsername().getText();
                                    String nuevaContrasenia = new String(usuarioActualizarView.getTxtNuevaContrasenia().getPassword());
                                    String pregunta1 = usuarioActualizarView.getTxtPregunta1().getText();
                                    String pregunta2 = usuarioActualizarView.getTxtPregunta2().getText();
                                    String pregunta3 = usuarioActualizarView.getTxtPregunta3().getText();

                                    if(username.isEmpty()) {
                                        usuarioActualizarView.mostrarMensaje("Ingrese un nombre de usuario");
                                        return;
                                    }

                                    Usuario usuario = usuarioDAO.buscarPorUsername(username);
                                    if(usuario == null) {
                                        usuarioActualizarView.mostrarMensaje("Usuario no encontrado");
                                        return;
                                    }

                                    if(!nuevaContrasenia.isEmpty()) {
                                        usuario.setContrasenia(nuevaContrasenia);
                                    }

                                    if(!pregunta1.isEmpty()) {
                                        usuario.setPregunta1(pregunta1);
                                    }

                                    if(!pregunta2.isEmpty()) {
                                        usuario.setPregunta2(pregunta2);
                                    }

                                    if(!pregunta3.isEmpty()) {
                                        usuario.setPregunta3(pregunta3);
                                    }

                                    usuarioDAO.actualizar(usuario);
                                    usuarioActualizarView.mostrarMensaje("Usuario actualizado exitosamente");
                                    usuarioView.cargarDatos(usuarioDAO.listarTodos()); // Actualizar la tabla
                                }
                            });
                            principalView.getMenuItemActualizarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoActualizarView.isVisible()) {
                                        carritoActualizarView.setVisible(true);
                                        principalView.getjDesktopPane().add(carritoActualizarView);
                                    }
                                }
                            });

                            principalView.getMenuItemEliminarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoEliminarView.isVisible()) {
                                        carritoEliminarView.setVisible(true);
                                        principalView.getjDesktopPane().add(carritoEliminarView);

                                        carritoEliminarView.getModelo().setNumRows(0);
                                        for (Carrito carrito : carritoDAO.listarTodos()) {
                                            Object[] fila = {
                                                    carrito.getCodigo(),
                                                    carrito.getFechaCreacion().getTime(),
                                                    carrito.obtenerItems().size(),
                                                    carrito.calcularSubtotal(),
                                                    carrito.calcularTotal()
                                            };
                                            carritoEliminarView.getModelo().addRow(fila);
                                        }
                                    }
                                }
                            });

                            principalView.getMenuItemListarCarritos().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoListaView.isVisible()) {
                                        carritoListaView.setVisible(true);
                                        principalView.getjDesktopPane().add(carritoListaView);
                                        // Solo cargar los carritos del usuario actual
                                        carritoListaView.cargarDatos(carritoDAO.listarPorUsuario(usuarioAuntenticado.getUsername()));
                                    }
                                }
                            });

                            // Configurar eventos en CarritoEliminarView
                            carritoEliminarView.getBtnActualizarLista().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    carritoEliminarView.getModelo().setNumRows(0);
                                    for (Carrito carrito : carritoDAO.listarTodos()) {
                                        Object[] fila = {
                                                carrito.getCodigo(),
                                                carrito.getFechaCreacion().getTime(),
                                                carrito.obtenerItems().size(),
                                                carrito.calcularSubtotal(),
                                                carrito.calcularTotal()
                                        };
                                        carritoEliminarView.getModelo().addRow(fila);
                                    }
                                }
                            });

                            carritoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String codigoStr = carritoEliminarView.getTxtCodigoCarrito().getText();
                                    if (codigoStr.isEmpty()) {
                                        carritoEliminarView.mostrarMensaje("Ingrese un código de carrito");
                                        return;
                                    }

                                    try {
                                        int codigo = Integer.parseInt(codigoStr);
                                        int confirmacion = carritoEliminarView.mostrarConfirmacion("¿Está seguro de eliminar este carrito?");

                                        if (confirmacion == JOptionPane.YES_OPTION) {
                                            carritoDAO.eliminar(codigo);
                                            carritoEliminarView.mostrarMensaje("Carrito eliminado correctamente");
                                            carritoEliminarView.getTxtCodigoCarrito().setText("");

                                            carritoEliminarView.getModelo().setNumRows(0);
                                            for (Carrito carrito : carritoDAO.listarTodos()) {
                                                Object[] fila = {
                                                        carrito.getCodigo(),
                                                        carrito.getFechaCreacion().getTime(),
                                                        carrito.obtenerItems().size(),
                                                        carrito.calcularSubtotal(),
                                                        carrito.calcularTotal()
                                                };
                                                carritoEliminarView.getModelo().addRow(fila);
                                            }
                                        }
                                    } catch (NumberFormatException ex) {
                                        carritoEliminarView.mostrarMensaje("El código debe ser numérico");
                                    }
                                }
                            });

                        }
                    }
                });
            }
        });

    }

}