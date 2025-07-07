package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import javax.swing.*;

public class ProductoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;

    public ProductoEliminarView() {
        super("", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        this.mensajeInternacionalizacion = new MensajeInternacionalizacionHandler("es", "EC");
        actualizarTextos();

        txtNombre.setEditable(false);
        txtPrecio.setEditable(false);
    }

    public void actualizarTextos() {
        this.setTitle(mensajeInternacionalizacion.get("titulo.producto"));
        lblCodigo.setText(mensajeInternacionalizacion.get("producto.codigo"));
        lblNombre.setText(mensajeInternacionalizacion.get("producto.nombre"));
        lblPrecio.setText(mensajeInternacionalizacion.get("producto.precio"));
        btnBuscar.setText(mensajeInternacionalizacion.get("producto.buscar"));
        btnEliminar.setText(mensajeInternacionalizacion.get("producto.eliminar"));
    }

    public MensajeInternacionalizacionHandler getMensajeInternacionalizacion() {
        return mensajeInternacionalizacion;
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

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean confirmarEliminacion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                mensajeInternacionalizacion.get("mensaje.confirmar_eliminacion"),
                mensajeInternacionalizacion.get("titulo.producto"),
                JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}