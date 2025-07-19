package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;


public class UsuarioView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblUsuarios;
    private JTextField txtUsername;
    private JButton btnListar;
    private JButton btnEliminar;
    private JLabel listaDeUsuarios;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajeHandler;


    public UsuarioView() {
        super("Gestión de Usuarios", true, true, false, true);
        mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 400);

        modelo = new DefaultTableModel();

        Object[] columnas = {"Username", "Nombre", "Apellido", "Correo", "Teléfono", "Fecha Nacimiento", "Rol"};
        modelo.setColumnIdentifiers(columnas);
        tblUsuarios.setModel(modelo);

        configurarIconos();
        internacionalizar();
    }

    private void configurarIconos() {
        // Tamaño preferido para los iconos
        Dimension iconSize = new Dimension(25, 25);

        // Cargar iconos desde resources
        ImageIcon listarIcon = cargarIcono("/imagenes/revision-positiva.png", iconSize);
        ImageIcon eliminarIcon = cargarIcono("/imagenes/reticulo.png", iconSize);


        btnListar.setIcon(listarIcon);
        btnEliminar.setIcon(eliminarIcon);


        btnListar.setIconTextGap(10);
        btnEliminar.setIconTextGap(10);


        btnListar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnEliminar.setHorizontalTextPosition(SwingConstants.RIGHT);


        btnListar.setToolTipText("Listar todos los usuarios");
        btnEliminar.setToolTipText("Eliminar usuario seleccionado");
    }
    private ImageIcon cargarIcono(String ruta, Dimension size) {
        try {
            // Cargar la imagen
            URL imgURL = getClass().getResource(ruta);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);

                Image scaledImage = originalIcon.getImage().getScaledInstance(
                        size.width, size.height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } else {
                System.err.println("No se encontró el archivo de icono: " + ruta);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el icono: " + e.getMessage());
            return null;
        }
    }
    public void internacionalizar() {
        listaDeUsuarios.setText(mensajeHandler.get("usuario.lista"));
        btnListar.setText(mensajeHandler.get("usuario.boton.listar"));
        btnEliminar.setText(mensajeHandler.get("usuario.boton.eliminar"));
        panelPrincipal.setBackground(Color.darkGray);
        panelPrincipal.setForeground(Color.WHITE);
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
            }
        }

        Object[] columnas = {
                mensajeHandler.get("usuario.columna.username"),
                mensajeHandler.get("usuario.columna.nombre"),
                mensajeHandler.get("usuario.columna.apellido"),
                mensajeHandler.get("usuario.columna.correo"),
                mensajeHandler.get("usuario.columna.telefono"),
                mensajeHandler.get("usuario.columna.fecha_nacimiento"),
                mensajeHandler.get("usuario.columna.rol")
        };
        modelo.setColumnIdentifiers(columnas);
    }


    public void cargarDatos(List<Usuario> usuarios) {
        modelo.setRowCount(0);
        for (Usuario u : usuarios) {
            Object[] fila = {
                    u.getUsername(), // Primero el username
                    u.getNombre(),
                    u.getApellido(),
                    u.getCorreo(),
                    u.getTelefono(),
                    u.getFechaNacimiento(),
                    u.getRol().toString()
            };
            modelo.addRow(fila);
        }
    }


    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}

