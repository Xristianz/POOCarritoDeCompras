package ec.edu.ups.poo.view;

import ec.edu.ups.poo.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;


public class UsuarioView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblUsuarios;
    private JTextField txtUsername;
    private JButton btnListar;
    private JButton btnEliminar;
    private DefaultTableModel modelo;

    public UsuarioView() {
        super("Gestión de Usuarios", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 400); // Aumentamos el tamaño para acomodar más columnas

        modelo = new DefaultTableModel();
        // Orden modificado según lo solicitado
        Object[] columnas = {"Username", "Nombre", "Apellido", "Correo", "Teléfono", "Fecha Nacimiento", "Rol"};
        modelo.setColumnIdentifiers(columnas);
        tblUsuarios.setModel(modelo);
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
}

