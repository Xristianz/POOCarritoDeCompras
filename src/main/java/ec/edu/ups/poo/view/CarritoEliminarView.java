package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class CarritoEliminarView extends JInternalFrame {
    private JTextField txtCodigoCarrito;
    private JButton btnEliminar;
    private JButton btnActualizarLista;
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;


    private JLabel lblCodigoCarrito;


    public CarritoEliminarView() {
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
        setSize(600, 400);


        this.mensajeInternacionalizacion = new MensajeInternacionalizacionHandler("es", "EC");


        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("Código");
        modelo.addColumn("Fecha Creación");
        modelo.addColumn("Total Items");
        modelo.addColumn("Subtotal");
        modelo.addColumn("Total");
        tblCarritos.setModel(modelo);


        actualizarTextos();
        configurarIconos();
    }
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);
        btnEliminar.setIcon(cargarIcono("/imagenes/carro-vacio.png", iconSize));
        btnActualizarLista.setIcon(cargarIcono("/imagenes/lista-de-verificacion.png", iconSize));

        btnEliminar.setIconTextGap(10);
        btnActualizarLista.setIconTextGap(10);

        btnEliminar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnActualizarLista.setHorizontalTextPosition(SwingConstants.RIGHT);
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
        // Título
        setTitle(mensajeInternacionalizacion.get("titulo.eliminar_carrito"));

        // Labels
        lblCodigoCarrito.setText(mensajeInternacionalizacion.get("carrito.codigo_carrito"));

        // Botones
        btnEliminar.setText(mensajeInternacionalizacion.get("carrito.eliminar"));
        btnActualizarLista.setText(mensajeInternacionalizacion.get("carrito.actualizar_lista"));

        // Encabezados de tabla
        modelo.setColumnIdentifiers(new Object[]{
                mensajeInternacionalizacion.get("carrito.columna.codigo"),
                mensajeInternacionalizacion.get("carrito.columna.fecha"),
                mensajeInternacionalizacion.get("carrito.columna.total_items"),
                mensajeInternacionalizacion.get("carrito.columna.subtotal"),
                mensajeInternacionalizacion.get("carrito.columna.total")
        });
    }

    // Getters (mantener los mismos)
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnActualizarLista() { return btnActualizarLista; }
    public JTextField getTxtCodigoCarrito() { return txtCodigoCarrito; }
    public DefaultTableModel getModelo() { return modelo; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public int mostrarConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(
                this,
                mensaje,
                mensajeInternacionalizacion.get("titulo.confirmar_eliminacion"),
                JOptionPane.YES_NO_OPTION
        );
    }

    public void limpiarCampos() {
        txtCodigoCarrito.setText("");
    }
    public MensajeInternacionalizacionHandler getMensajeInternacionalizacion() {
        return mensajeInternacionalizacion;
    }
}