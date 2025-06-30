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
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Nombre", "Rol"};
        modelo.setColumnIdentifiers(columnas);
        tblUsuarios.setModel(modelo);


    }

    public void cargarDatos(List<Usuario> usuarios) {
        modelo.setRowCount(0);
        for (Usuario u : usuarios) {
            Object[] fila = {
                    u.getUsername(),
                    u.getRol().name()
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

