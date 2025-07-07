package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.models.Carrito;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
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
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;

    private JLabel lblBuscar;

    public CarritoListaView() {
        super("", true, true, true, true);
        panelPrincipal.setBackground(Color.darkGray);
        panelPrincipal.setForeground(Color.WHITE);
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
            }
        }
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);


        this.mensajeInternacionalizacion = new MensajeInternacionalizacionHandler("es", "EC");

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"Código", "Usuario", "Fecha", "Total"});
        tblCarritos.setModel(modelo);


        actualizarTextos();
        configurarIconos();
    }
    private void configurarIconos() {
        Dimension iconSize = new Dimension(30, 30);

        btnBuscar.setIcon(cargarIcono("/imagenes/revolver.png", iconSize));
        btnListar.setIcon(cargarIcono("/imagenes/lista-de-verificacion.png", iconSize));
        btnVerDetalles.setIcon(cargarIcono("/imagenes/buscar.png", iconSize));

        btnBuscar.setIconTextGap(10);
        btnListar.setIconTextGap(10);
        btnVerDetalles.setIconTextGap(10);


        btnBuscar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnListar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnVerDetalles.setHorizontalTextPosition(SwingConstants.RIGHT);
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
        setTitle(mensajeInternacionalizacion.get("titulo.lista_carritos"));

        // Labels
        lblBuscar.setText(mensajeInternacionalizacion.get("carrito.buscar_por_codigo"));

        // Botones
        btnBuscar.setText(mensajeInternacionalizacion.get("carrito.buscar"));
        btnListar.setText(mensajeInternacionalizacion.get("carrito.listar_todos"));
        btnVerDetalles.setText(mensajeInternacionalizacion.get("carrito.ver_detalles"));

        // Encabezados de tabla
        modelo.setColumnIdentifiers(new Object[]{
                mensajeInternacionalizacion.get("carrito.columna.codigo"),
                mensajeInternacionalizacion.get("carrito.columna.usuario"),
                mensajeInternacionalizacion.get("carrito.columna.fecha"),
                mensajeInternacionalizacion.get("carrito.columna.total")
        });
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
    public MensajeInternacionalizacionHandler getMensajeInternacionalizacion() {
        return mensajeInternacionalizacion;
    }

    // Getters (mantener los mismos)
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