package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ProductoActualizarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnActualizar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;

    public ProductoActualizarView() {
        super("", true, true, false, true);
        panelPrincipal.setBackground(Color.darkGray);
        panelPrincipal.setForeground(Color.WHITE);
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
            }
        }
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        this.mensajeInternacionalizacion = new MensajeInternacionalizacionHandler("es", "EC");
        actualizarTextos();
        configurarIconos();
    }
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);
        btnBuscar.setIcon(cargarIcono("/imagenes/buscar.png", iconSize));
        btnActualizar.setIcon(cargarIcono("/imagenes/agregar.png", iconSize));

        btnBuscar.setIconTextGap(10);
        btnActualizar.setIconTextGap(10);

        btnBuscar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnActualizar.setHorizontalTextPosition(SwingConstants.RIGHT);
    }

    private ImageIcon cargarIcono(String ruta, Dimension size) {
        try {
            URL imgURL = getClass().getResource(ruta);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public void actualizarTextos() {
        this.setTitle(mensajeInternacionalizacion.get("titulo.producto"));
        lblCodigo.setText(mensajeInternacionalizacion.get("producto.codigo"));
        lblNombre.setText(mensajeInternacionalizacion.get("producto.nombre"));
        lblPrecio.setText(mensajeInternacionalizacion.get("producto.precio"));
        btnBuscar.setText(mensajeInternacionalizacion.get("producto.buscar"));
        btnActualizar.setText(mensajeInternacionalizacion.get("producto.actualizar"));
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

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}