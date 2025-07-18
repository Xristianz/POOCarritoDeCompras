package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.controller.util.FormateadorUtils;
import ec.edu.ups.poo.models.Carrito;
import ec.edu.ups.poo.models.ItemCarrito;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;

/**
 * La clase DetalleCarritoView es un JInternalFrame que muestra los detalles de un carrito de compras específico.
 * Presenta los ítems del carrito en una tabla, incluyendo información del producto, precio, cantidad y subtotal por ítem.
 * Además, calcula y muestra el subtotal general, el IVA y el total del carrito al final de la tabla.
 * Esta vista soporta internacionalización para sus etiquetas de texto.
 */
public class DetalleCarritoView extends JInternalFrame {
    private JTable tblDetalles;
    private JPanel panelPrincipal;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;
    private Carrito carritoActual;

    /**
     * Construye una nueva instancia de DetalleCarritoView.
     * Inicializa los componentes de la interfaz de usuario, configura el modelo de la tabla
     * y actualiza los textos para internacionalización.
     */
    public DetalleCarritoView() {
        super("", true, true, true, true);
        panelPrincipal.setBackground(Color.darkGray);
        panelPrincipal.setForeground(Color.WHITE);
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
            }
        }
        setContentPane(panelPrincipal);
        setSize(600, 400);

        if (tblDetalles == null) tblDetalles = new JTable();

        this.mensajeInternacionalizacion = new MensajeInternacionalizacionHandler("es", "EC");

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDetalles.setModel(modelo);

        actualizarTextos();
    }

    /**
     * Actualiza el título de la ventana y los encabezados de las columnas de la tabla
     * según la configuración de internacionalización actual.
     * Si hay un carrito actual, vuelve a cargar sus detalles para reflejar los cambios de idioma.
     */
    public void actualizarTextos() {
        setTitle(mensajeInternacionalizacion.get("titulo.detalle_carrito"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajeInternacionalizacion.get("carrito.columna.codigo"),
                mensajeInternacionalizacion.get("carrito.columna.producto"),
                mensajeInternacionalizacion.get("carrito.columna.precio"),
                mensajeInternacionalizacion.get("carrito.columna.cantidad"),
                mensajeInternacionalizacion.get("carrito.columna.subtotal"),
        });

        if (this.carritoActual != null) {
            cargarDetalles(this.carritoActual);
        }
    }

    /**
     * Carga los detalles de un objeto Carrito específico en la tabla.
     * Limpia la tabla existente y añade filas con información de cada ítem del carrito,
     * incluyendo el código del producto, nombre, precio, cantidad y subtotal del ítem.
     * Al final, añade filas con el subtotal, IVA y total del carrito.
     *
     * @param carrito El objeto Carrito cuyos detalles se van a mostrar.
     */
    public void cargarDetalles(Carrito carrito) {
        this.carritoActual = carrito;
        modelo.setNumRows(0);
        Locale locale = mensajeInternacionalizacion.getLocale();

        if (carrito != null) {
            for (ItemCarrito item : carrito.obtenerItems()) {
                double subtotal = item.getProducto().getPrecio() * item.getCantidad();

                modelo.addRow(new Object[]{
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                        item.getCantidad(),
                        FormateadorUtils.formatearMoneda(subtotal, locale)
                });
            }


            modelo.addRow(new Object[]{"", "", "", "", ""});
            modelo.addRow(new Object[]{
                    "", "", "",
                    mensajeInternacionalizacion.get("carrito.columna.subtotal") + ":",
                    FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale)
            });
            modelo.addRow(new Object[]{
                    "", "", "",
                    "IVA:",
                    FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale)
            });
            modelo.addRow(new Object[]{
                    "", "", "",
                    mensajeInternacionalizacion.get("carrito.columna.total") + ":",
                    FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale)
            });
        }
    }

    /**
     * Muestra un cuadro de diálogo de mensaje al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Devuelve la instancia de MensajeInternacionalizacionHandler utilizada para la internacionalización.
     *
     * @return La instancia de MensajeInternacionalizacionHandler.
     */
    public MensajeInternacionalizacionHandler getMensajeInternacionalizacion() {
        return mensajeInternacionalizacion;
    }
}