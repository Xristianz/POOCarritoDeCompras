package ec.edu.ups.poo.view;

import ec.edu.ups.poo.models.Producto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtDescuento;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnCalcularTotal;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblDescuento;

    public ProductoView() {
        setContentPane(panelPrincipal);
        setTitle("Gestión de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }


    public JButton getBtnAgregar() {
        return btnAgregar;
    }
    public JButton getBtnModificar() {
        return btnModificar;
    }
    public JButton getBtnEliminar() {
        return btnEliminar;
    }
    public JButton getBtnCalcularTotal() {
        return btnCalcularTotal;
    }
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }
    public JTextField getTxtNombre() {
        return txtNombre;
    }
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }
    public JTextField getTxtDescuento() {
        return txtDescuento;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtDescuento.setText("");
    }
}