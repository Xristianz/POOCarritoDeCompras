package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.FormateadorUtils;
import ec.edu.ups.poo.models.Producto;
import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;

public class ProductoListaView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JPanel panelPrincipal;
    private JButton btnListar;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;

    public ProductoListaView() {
        super("", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);



        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProductos.setModel(modelo);
        this.mensajeInternacionalizacion = new MensajeInternacionalizacionHandler("es", "EC");
        actualizarTextos();
    }

    public void actualizarTextos() {
        this.setTitle(mensajeInternacionalizacion.get("titulo.producto"));
        btnBuscar.setText(mensajeInternacionalizacion.get("producto.buscar"));
        btnListar.setText(mensajeInternacionalizacion.get("producto.listar"));

        Object[] columnas = {
                mensajeInternacionalizacion.get("producto.columna.codigo"),
                mensajeInternacionalizacion.get("producto.columna.nombre"),
                mensajeInternacionalizacion.get("producto.columna.precio")
        };
        modelo.setColumnIdentifiers(columnas);
    }

    public MensajeInternacionalizacionHandler getMensajeInternacionalizacion() {
        return mensajeInternacionalizacion;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setRowCount(0);
        Locale locale = mensajeInternacionalizacion.getLocale();

        for (Producto producto : listaProductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    FormateadorUtils.formatearMoneda(producto.getPrecio(), locale)
            };
            modelo.addRow(fila);
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}