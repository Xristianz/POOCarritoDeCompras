package ec.edu.ups.poo.view;

import ec.edu.ups.poo.models.Carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class CarritoListaView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblCarritos;
    private JPanel panelPrincipal;
    private JButton btnListar;
    private JButton btnVerDetalles;
    private DefaultTableModel modelo;

    public CarritoListaView() {
        super("Listado de Carritos", true, true, true, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"Código", "Usuario", "Fecha", "Total"});
        tblCarritos.setModel(modelo);
    }

    public void cargarDatos(List<Carrito> listaCarritos) {
        modelo.setNumRows(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (Carrito carrito : listaCarritos) {
            modelo.addRow(new Object[]{
                    carrito.getCodigo(),
                    carrito.getUsuario().getUsername(),
                    sdf.format(carrito.getFechaCreacion().getTime()),
                    String.format("%.2f", carrito.calcularTotal())
            });
        }
    }

    // Getters para los componentes
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnListar() { return btnListar; }
    public JButton getBtnVerDetalles() { return btnVerDetalles; }
    public JTextField getTxtBuscar() { return txtBuscar; }
    public JTable getTblCarritos() { return tblCarritos; }
    public DefaultTableModel getModelo() { return modelo; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}


