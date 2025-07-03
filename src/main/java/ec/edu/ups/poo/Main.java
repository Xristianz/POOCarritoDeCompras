package ec.edu.ups.poo;

import ec.edu.ups.poo.DAO.CarritoDAO;
import ec.edu.ups.poo.DAO.PreguntaSeguridadDAO;
import ec.edu.ups.poo.DAO.ProductoDAO;
import ec.edu.ups.poo.DAO.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.DAO.impl.PreguntaSeguridadDAOMemoria;
import ec.edu.ups.poo.DAO.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.DAO.UsuarioDAO;
import ec.edu.ups.poo.DAO.impl.UsuarioDAOMemoria;
import ec.edu.ups.poo.controller.CarritoController;
import ec.edu.ups.poo.controller.ProductoController;
import ec.edu.ups.poo.controller.UsuarioController;
import ec.edu.ups.poo.models.*;
import ec.edu.ups.poo.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class Main {
    private static ProductoDAO productoDAO = new ProductoDAOMemoria();
    private static CarritoDAO carritoDAO = new CarritoDAOMemoria();
    private static UsuarioDAO usuarioDAO = new UsuarioDAOMemoria(carritoDAO);
    private static PreguntaSeguridadDAO preguntaSeguridadDAO = new PreguntaSeguridadDAOMemoria();

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginView loginView = new LoginView();
                loginView.setVisible(true);

                UsuarioController usuarioController = new UsuarioController(usuarioDAO, preguntaSeguridadDAO, loginView);

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
                            UsuarioView usuarioView = new UsuarioView();
                            CarritoListaView carritoListaView = new CarritoListaView();
                            UsuarioAnadir usuarioAnadirView = new UsuarioAnadir();
                            UsuarioActualizar usuarioActualizarView = new UsuarioActualizar();
                            CarritoActualizarView carritoActualizarView = new CarritoActualizarView();
                            CarritoEliminarView carritoEliminarView = new CarritoEliminarView();

                            // Instanciar controladores
                            ProductoController productoController = new ProductoController(productoDAO,
                                    productoAnadirView, productoListaView, carritoAnadirView,
                                    productoActualizarView, productoEliminarView);

                            CarritoController carritoController = new CarritoController(
                                    carritoDAO, productoDAO, carritoAnadirView,
                                    carritoListaView, carritoActualizarView, usuarioAutenticado);

                            principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());
                            if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                                principalView.deshabilitarMenusAdministrador();
                            }

                            // Configurar eventos del menú principal
                            configurarEventosMenu(principalView, productoAnadirView, productoListaView,
                                    productoActualizarView, productoEliminarView, carritoAnadirView,
                                    usuarioView, carritoListaView, usuarioAnadirView, usuarioActualizarView,
                                    carritoActualizarView, carritoEliminarView, loginView, usuarioController,
                                    usuarioDAO, usuarioAutenticado, productoController);

                            // Configurar eventos específicos de usuarios
                            configurarEventosUsuario(usuarioView, usuarioAnadirView, usuarioActualizarView,
                                    usuarioDAO, usuarioAutenticado, preguntaSeguridadDAO);

                            // Configurar eventos de carrito
                            configurarEventosCarrito(carritoEliminarView, carritoDAO, usuarioAutenticado,
                                    carritoListaView, carritoController);
                        }
                    }
                });
            }
        });
    }

    private static void configurarEventosMenu(MenuPrincipalView principalView,
                                              ProductoAnadirView productoAnadirView, ProductoListaView productoListaView,
                                              ProductoActualizarView productoActualizarView, ProductoEliminarView productoEliminarView,
                                              CarritoAnadirView carritoAnadirView, UsuarioView usuarioView,
                                              CarritoListaView carritoListaView, UsuarioAnadir usuarioAnadirView,
                                              UsuarioActualizar usuarioActualizarView, CarritoActualizarView carritoActualizarView,
                                              CarritoEliminarView carritoEliminarView, LoginView loginView,
                                              UsuarioController usuarioController, UsuarioDAO usuarioDAO,
                                              Usuario usuarioAutenticado, ProductoController productoController) {

        // Eventos para Productos
        principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(!productoAnadirView.isVisible()) {
                    productoAnadirView.setVisible(true);
                    principalView.getjDesktopPane().add(productoAnadirView);
                    try {
                        productoAnadirView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(!productoListaView.isVisible()) {
                    productoListaView.setVisible(true);
                    principalView.getjDesktopPane().add(productoListaView);
                    try {
                        productoListaView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(!productoActualizarView.isVisible()) {
                    productoActualizarView.setVisible(true);
                    principalView.getjDesktopPane().add(productoActualizarView);
                    try {
                        productoActualizarView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(!productoEliminarView.isVisible()) {
                    productoEliminarView.setVisible(true);
                    principalView.getjDesktopPane().add(productoEliminarView);
                    try {
                        productoEliminarView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        // Eventos para Carritos
        principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(!carritoAnadirView.isVisible()) {
                    carritoAnadirView.setVisible(true);
                    principalView.getjDesktopPane().add(carritoAnadirView);
                    try {
                        carritoAnadirView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        principalView.getMenuItemListarCarritos().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(!carritoListaView.isVisible()) {
                    carritoListaView.setVisible(true);
                    principalView.getjDesktopPane().add(carritoListaView);
                    try {
                        carritoListaView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        principalView.getMenuItemActualizarCarrito().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(!carritoActualizarView.isVisible()) {
                    carritoActualizarView.setVisible(true);
                    principalView.getjDesktopPane().add(carritoActualizarView);
                    try {
                        carritoActualizarView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        principalView.getMenuItemEliminarCarrito().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(!carritoEliminarView.isVisible()) {
                    carritoEliminarView.setVisible(true);
                    principalView.getjDesktopPane().add(carritoEliminarView);
                    try {
                        carritoEliminarView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        // Eventos para Usuarios
        principalView.getMenuItemGestionUsuarios().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(!usuarioView.isVisible()) {
                    usuarioView.setVisible(true);
                    principalView.getjDesktopPane().add(usuarioView);
                    usuarioView.cargarDatos(usuarioDAO.listarTodos());
                    try {
                        usuarioView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        principalView.getMenuItemAgregarUsuario().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(!usuarioAnadirView.isVisible()) {
                    usuarioAnadirView.setVisible(true);
                    principalView.getjDesktopPane().add(usuarioAnadirView);

                    // Solución definitiva para los ComboBox independientes
                    List<PreguntaSeguridad> preguntas = preguntaSeguridadDAO.listarTodas();

                    // Crear modelos independientes para cada ComboBox
                    DefaultComboBoxModel<PreguntaSeguridad> modeloPregunta1 = new DefaultComboBoxModel<>();
                    DefaultComboBoxModel<PreguntaSeguridad> modeloPregunta2 = new DefaultComboBoxModel<>();
                    DefaultComboBoxModel<PreguntaSeguridad> modeloPregunta3 = new DefaultComboBoxModel<>();

                    for (PreguntaSeguridad pregunta : preguntas) {
                        modeloPregunta1.addElement(new PreguntaSeguridad(pregunta.getId(), pregunta.getTexto()));
                        modeloPregunta2.addElement(new PreguntaSeguridad(pregunta.getId(), pregunta.getTexto()));
                        modeloPregunta3.addElement(new PreguntaSeguridad(pregunta.getId(), pregunta.getTexto()));
                    }

                    usuarioAnadirView.getCmbPregunta1().setModel(modeloPregunta1);
                    usuarioAnadirView.getCmbPregunta2().setModel(modeloPregunta2);
                    usuarioAnadirView.getCmbPregunta3().setModel(modeloPregunta3);

                    try {
                        usuarioAnadirView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        principalView.getMenuItemActualizarContrasenia().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(!usuarioActualizarView.isVisible()) {
                    usuarioActualizarView.setVisible(true);
                    principalView.getjDesktopPane().add(usuarioActualizarView);
                    try {
                        usuarioActualizarView.setSelected(true);
                    } catch (Exception ex) {}
                }
            }
        });

        // Configurar eventos de búsqueda de productos
        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigoStr = productoListaView.getTxtBuscar().getText();
                if (codigoStr.isEmpty()) {
                    productoListaView.mostrarMensaje("Ingrese un código de producto");
                    return;
                }

                try {
                    int codigo = Integer.parseInt(codigoStr);
                    Producto producto = productoDAO.buscarPorCodigo(codigo);

                    DefaultTableModel modelo = (DefaultTableModel) productoListaView.getTblProductos().getModel();
                    modelo.setRowCount(0); // Limpiar tabla

                    if (producto != null) {
                        modelo.addRow(new Object[]{
                                producto.getCodigo(),
                                producto.getNombre(),
                                producto.getPrecio()
                        });
                    } else {
                        productoListaView.mostrarMensaje("Producto no encontrado");
                    }
                } catch (NumberFormatException ex) {
                    productoListaView.mostrarMensaje("El código debe ser numérico");
                }
            }
        });

        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoController.listarProductos();
            }
        });

        // Eventos para Idiomas
        principalView.getMenuItemIdiomaEspanol().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                principalView.cambiarIdioma("es", "EC");
            }
        });

        principalView.getMenuItemIdiomaIngles().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                principalView.cambiarIdioma("en", "US");
            }
        });

        principalView.getMenuItemIdiomaFrances().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                principalView.cambiarIdioma("fr", "FR");
            }
        });

        // Eventos para Salir/Cerrar Sesión
        principalView.getMenuItemCerrarSesion().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
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
            @Override public void actionPerformed(ActionEvent e) {
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

    private static void configurarEventosUsuario(UsuarioView usuarioView, UsuarioAnadir usuarioAnadirView,
                                                 UsuarioActualizar usuarioActualizarView, UsuarioDAO usuarioDAO,
                                                 Usuario usuarioAutenticado, PreguntaSeguridadDAO preguntaSeguridadDAO) {

        usuarioView.getBtnListar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                usuarioView.cargarDatos(usuarioDAO.listarTodos());
            }
        });

        usuarioView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
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

        usuarioAnadirView.getBtnAgregarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = usuarioAnadirView.getTxtNombre().getText();
                String apellido = usuarioAnadirView.getTxtApellido().getText();
                String correo = usuarioAnadirView.getTxtCorreo().getText();
                String telefono = usuarioAnadirView.getTxtTelefono().getText();
                String fechaNacimiento = usuarioAnadirView.getTxtFechaNacimiento().getText();
                String username = usuarioAnadirView.getTxtUsername().getText();
                String contrasenia = new String(usuarioAnadirView.getTxtContrasenia().getPassword());

                PreguntaSeguridad pregunta1 = (PreguntaSeguridad) usuarioAnadirView.getCmbPregunta1().getSelectedItem();
                PreguntaSeguridad pregunta2 = (PreguntaSeguridad) usuarioAnadirView.getCmbPregunta2().getSelectedItem();
                PreguntaSeguridad pregunta3 = (PreguntaSeguridad) usuarioAnadirView.getCmbPregunta3().getSelectedItem();

                String respuesta1 = usuarioAnadirView.getTxtRespuesta1().getText();
                String respuesta2 = usuarioAnadirView.getTxtRespuesta2().getText();
                String respuesta3 = usuarioAnadirView.getTxtRespuesta3().getText();

                if(nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() ||
                        telefono.isEmpty() || fechaNacimiento.isEmpty() || username.isEmpty() ||
                        contrasenia.isEmpty() || respuesta1.isEmpty() || respuesta2.isEmpty() ||
                        respuesta3.isEmpty() || pregunta1 == null || pregunta2 == null || pregunta3 == null) {
                    usuarioAnadirView.mostrarMensaje("Todos los campos son requeridos");
                    return;
                }

                if(pregunta1.getId() == pregunta2.getId() ||
                        pregunta1.getId() == pregunta3.getId() ||
                        pregunta2.getId() == pregunta3.getId()) {
                    usuarioAnadirView.mostrarMensaje("Debe seleccionar preguntas diferentes");
                    return;
                }

                if(usuarioDAO.buscarPorUsername(username) != null) {
                    usuarioAnadirView.mostrarMensaje("El usuario ya existe");
                    return;
                }

                Usuario nuevoUsuario = new Usuario(
                        username, contrasenia, Rol.USUARIO,
                        nombre, apellido, correo, telefono, fechaNacimiento,
                        pregunta1.getId(), respuesta1,
                        pregunta2.getId(), respuesta2,
                        pregunta3.getId(), respuesta3
                );

                usuarioDAO.crear(nuevoUsuario);
                usuarioAnadirView.mostrarMensaje("Usuario creado exitosamente");
                usuarioAnadirView.limpiarCampos();
            }
        });

        usuarioActualizarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
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

                PreguntaSeguridadDAO preguntaDAO = new PreguntaSeguridadDAOMemoria();
                usuarioActualizarView.getLblPregunta1().setText(preguntaDAO.buscarPorId(usuario.getPregunta1Id()).getTexto());
                usuarioActualizarView.getLblPregunta2().setText(preguntaDAO.buscarPorId(usuario.getPregunta2Id()).getTexto());
                usuarioActualizarView.getLblPregunta3().setText(preguntaDAO.buscarPorId(usuario.getPregunta3Id()).getTexto());

                usuarioActualizarView.cargarDatosUsuario(usuario);
            }
        });

        usuarioActualizarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String username = usuarioActualizarView.getTxtUsername().getText();
                if (username.isEmpty()) {
                    usuarioActualizarView.mostrarMensaje("Ingrese un nombre de usuario");
                    return;
                }

                Usuario usuario = usuarioDAO.buscarPorUsername(username);
                if (usuario == null) {
                    usuarioActualizarView.mostrarMensaje("Usuario no encontrado");
                    return;
                }

                // Obtener solo los campos editables
                String nuevaContrasenia = new String(usuarioActualizarView.getTxtNuevaContrasenia().getPassword());
                String respuesta1 = usuarioActualizarView.getTxtRespuesta1().getText();
                String respuesta2 = usuarioActualizarView.getTxtRespuesta2().getText();
                String respuesta3 = usuarioActualizarView.getTxtRespuesta3().getText();

                // Validar que al menos haya un cambio
                if (nuevaContrasenia.isEmpty() && respuesta1.isEmpty() &&
                        respuesta2.isEmpty() && respuesta3.isEmpty()) {
                    usuarioActualizarView.mostrarMensaje("No hay cambios para actualizar");
                    return;
                }

                // Validar respuestas (si se están cambiando)
                if ((!respuesta1.isEmpty() || !respuesta2.isEmpty() || !respuesta3.isEmpty()) &&
                        (respuesta1.isEmpty() || respuesta2.isEmpty() || respuesta3.isEmpty())) {
                    usuarioActualizarView.mostrarMensaje("Debe completar todas las respuestas que desea cambiar");
                    return;
                }

                // Actualizar contraseña si se proporcionó
                if (!nuevaContrasenia.isEmpty()) {
                    usuario.setContrasenia(nuevaContrasenia);
                }

                // Actualizar respuestas si se proporcionaron
                if (!respuesta1.isEmpty()) {
                    usuario.setRespuesta1(respuesta1);
                }
                if (!respuesta2.isEmpty()) {
                    usuario.setRespuesta2(respuesta2);
                }
                if (!respuesta3.isEmpty()) {
                    usuario.setRespuesta3(respuesta3);
                }

                // Guardar cambios
                usuarioDAO.actualizar(usuario);
                usuarioActualizarView.mostrarMensaje("Datos actualizados correctamente");

                // Limpiar campos de contraseña
                usuarioActualizarView.getTxtNuevaContrasenia().setText("");
            }
        });
    }

    private static void configurarEventosCarrito(CarritoEliminarView carritoEliminarView,
                                                 CarritoDAO carritoDAO, Usuario usuarioAutenticado,
                                                 CarritoListaView carritoListaView, CarritoController carritoController) {

        carritoEliminarView.getBtnActualizarLista().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                List<Carrito> carritos;
                if (usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
                    carritos = carritoDAO.listarTodos();
                } else {
                    carritos = carritoDAO.listarPorUsuario(usuarioAutenticado.getUsername());
                }

                DefaultTableModel modelo = carritoEliminarView.getModelo();
                modelo.setRowCount(0);

                for (Carrito carrito : carritos) {
                    modelo.addRow(new Object[]{
                            carrito.getCodigo(),
                            carrito.getFechaCreacion().getTime(),
                            carrito.obtenerItems().size(),
                            carrito.calcularSubtotal(),
                            carrito.calcularTotal()
                    });
                }
            }
        });

        carritoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String codigoStr = carritoEliminarView.getTxtCodigoCarrito().getText();
                if (codigoStr.isEmpty()) {
                    carritoEliminarView.mostrarMensaje("Ingrese un código de carrito");
                    return;
                }

                try {
                    int codigo = Integer.parseInt(codigoStr);
                    Carrito carritoAEliminar = carritoDAO.buscarPorCodigo(codigo);

                    if (carritoAEliminar == null) {
                        carritoEliminarView.mostrarMensaje("Carrito no encontrado");
                        return;
                    }

                    if (usuarioAutenticado.getRol() != Rol.ADMINISTRADOR &&
                            !carritoAEliminar.getUsuario().getUsername().equals(usuarioAutenticado.getUsername())) {
                        carritoEliminarView.mostrarMensaje("No tiene permiso para eliminar este carrito");
                        return;
                    }

                    int confirmacion = JOptionPane.showConfirmDialog(carritoEliminarView,
                            "¿Está seguro de eliminar este carrito?", "Confirmar",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        carritoDAO.eliminar(codigo);
                        carritoEliminarView.mostrarMensaje("Carrito eliminado correctamente");
                        carritoEliminarView.getTxtCodigoCarrito().setText("");

                        // Actualizar lista
                        carritoController.listarCarritos();
                    }
                } catch (NumberFormatException ex) {
                    carritoEliminarView.mostrarMensaje("El código debe ser numérico");
                }
            }
        });
    }
}