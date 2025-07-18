package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * La clase Seleccionador es un JFrame que actúa como una ventana de configuración inicial para la aplicación.
 * Permite al usuario seleccionar el modo de persistencia de datos (en memoria o en archivo) y,
 * si elige el modo de archivo, especificar la ruta de un directorio.
 * La vista también incluye un menú para cambiar el idioma de la interfaz (español, inglés, francés)
 * y soporta la internacionalización de todos sus textos y mensajes.
 */
public class Seleccionador extends JFrame {

    // --- Componentes del Menú ---
    private JMenu menuIdioma;
    private JMenuItem menuItemEspanol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;

    // --- Componentes de la UI y Clases Auxiliares ---
    private JPanel panel1;
    private JPanel panelMenu;
    private JComboBox<String> cbxSelecionar;
    private JButton btnAceptar;
    private JFileChooser fileChooser;
    private JLabel lblInstruccion;
    private JLabel lblRuta;
    private JTextField txtRuta;
    private JButton btnSeleccionarRuta;
    private MensajeInternacionalizacionHandler i18n;
    private Map<String, String> opcionMap;

    /**
     * Construye una nueva instancia de Seleccionador.
     * Inicializa el manejador de mensajes para internacionalización en español (Ecuador),
     * inicializa el mapa de opciones, los componentes de la UI, configura el menú,
     * los eventos y actualiza los textos.
     * También establece las propiedades de la ventana como la operación de cierre, tamaño, ubicación y si es redimensionable.
     */
    public Seleccionador() {
        this.i18n = new MensajeInternacionalizacionHandler("es", "EC");
        this.opcionMap = new HashMap<>();

        initComponents();
        configurarMenuEnPanel();
        configurarEventos();
        actualizarTextos();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 220);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Inicializa y organiza los componentes visuales de la ventana.
     * Configura el layout principal, añade paneles y distribuye etiquetas, campos de texto,
     * combo box y botones utilizando GridBagLayout.
     * Establece la visibilidad inicial de los componentes de ruta como invisibles y configura el JFileChooser.
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        panelMenu = new JPanel();
        add(panelMenu, BorderLayout.NORTH);

        panel1 = new JPanel(new GridBagLayout());
        add(panel1, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblInstruccion = new JLabel();
        cbxSelecionar = new JComboBox<>();
        lblRuta = new JLabel();
        txtRuta = new JTextField(25);
        txtRuta.setEditable(false);
        btnSeleccionarRuta = new JButton();
        btnAceptar = new JButton();

        // Se establece que los componentes de la ruta sean invisibles por defecto para evitar bugs visuales.
        lblRuta.setVisible(false);
        txtRuta.setVisible(false);
        btnSeleccionarRuta.setVisible(false);

        // Fila 1: Instrucción y ComboBox
        gbc.gridx = 0; gbc.gridy = 0;
        panel1.add(lblInstruccion, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        panel1.add(cbxSelecionar, gbc);

        // Fila 2: Ruta y botón de selección
        gbc.gridwidth = 1;

        // Columna de la etiqueta (no se estira)
        gbc.weightx = 0.0;
        gbc.gridx = 0; gbc.gridy = 1;
        panel1.add(lblRuta, gbc);

        // Columna del campo de texto (se estira para ocupar el espacio)
        gbc.weightx = 1.0;
        gbc.gridx = 1; gbc.gridy = 1;
        panel1.add(txtRuta, gbc);

        // Columna del botón (no se estira)
        gbc.weightx = 0.0;
        gbc.gridx = 2; gbc.gridy = 1;
        panel1.add(btnSeleccionarRuta, gbc);

        // Fila 3: Botón de Aceptar
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel1.add(btnAceptar, gbc);

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    /**
     * Configura el panel del menú para que contenga el menú desplegable de idiomas.
     * Establece un FlowLayout para la alineación a la izquierda.
     */
    private void configurarMenuEnPanel() {
        panelMenu.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelMenu.add(crearMenu());
    }

    /**
     * Crea y devuelve una JMenuBar que contiene un menú para la selección de idioma.
     * Configura los JMenuItems para Español, Inglés y Francés, y les añade un ActionListener
     * para cambiar el idioma de la interfaz.
     *
     * @return Una JMenuBar con el menú de selección de idioma.
     */
    private JMenuBar crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuIdioma = new JMenu();
        menuItemEspanol = new JMenuItem();
        menuItemIngles = new JMenuItem();
        menuItemFrances = new JMenuItem();

        menuIdioma.add(menuItemEspanol);
        menuIdioma.add(menuItemIngles);
        menuIdioma.add(menuItemFrances);
        menuBar.add(menuIdioma);

        ActionListener cambiarIdiomaListener = e -> {
            if (e.getSource() == menuItemIngles) i18n.setLenguaje("en", "US");
            else if (e.getSource() == menuItemFrances) i18n.setLenguaje("fr", "FR");
            else i18n.setLenguaje("es", "EC");
            actualizarTextos();
        };

        menuItemEspanol.addActionListener(cambiarIdiomaListener);
        menuItemIngles.addActionListener(cambiarIdiomaListener);
        menuItemFrances.addActionListener(cambiarIdiomaListener);

        return menuBar;
    }

    /**
     * Configura los eventos para los componentes de la interfaz.
     * Añade un ActionListener al JComboBox para actualizar la visibilidad de los campos de ruta
     * cuando cambia la selección.
     * Añade un ActionListener al botón "Seleccionar Ruta" para abrir un JFileChooser.
     */
    private void configurarEventos() {
        cbxSelecionar.addActionListener(e -> actualizarVisibilidadRuta());
        btnSeleccionarRuta.addActionListener(e -> {
            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                txtRuta.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario (título, etiquetas, botones, elementos del menú)
     * según el idioma configurado en el `MensajeInternacionalizacionHandler`.
     * También recarga las opciones del JComboBox de selección y mantiene la opción previamente seleccionada.
     */
    private void actualizarTextos() {
        String seleccionActual = getOpcionSeleccionada();
        setTitle(i18n.get("selector.titulo"));
        lblInstruccion.setText(i18n.get("selector.label"));
        lblRuta.setText(i18n.get("selector.label.ruta"));
        btnSeleccionarRuta.setText(i18n.get("selector.boton.seleccionar"));
        btnAceptar.setText(i18n.get("selector.boton.aceptar"));
        fileChooser.setDialogTitle(i18n.get("selector.filechooser.titulo"));

        // Actualiza el texto del propio menú de idiomas
        menuIdioma.setText(i18n.get("menu.idioma"));
        menuItemEspanol.setText(i18n.get("menu.idioma.es"));
        menuItemIngles.setText(i18n.get("menu.idioma.en"));
        menuItemFrances.setText(i18n.get("menu.idioma.fr"));

        cbxSelecionar.removeAllItems();
        opcionMap.clear();

        String opcionMemoria = i18n.get("selector.opcion.memoria");
        String opcionArchivo = i18n.get("selector.opcion.archivo");
        opcionMap.put(opcionMemoria, "Memoria");
        opcionMap.put(opcionArchivo, "Archivo");

        cbxSelecionar.addItem(opcionMemoria);
        cbxSelecionar.addItem(opcionArchivo);

        for (String key : opcionMap.keySet()) {
            if (opcionMap.get(key).equals(seleccionActual)) {
                cbxSelecionar.setSelectedItem(key);
                break;
            }
        }
        actualizarVisibilidadRuta();
    }

    /**
     * Actualiza la visibilidad de los componentes relacionados con la ruta (etiqueta, campo de texto, botón)
     * basándose en si la opción seleccionada en el JComboBox es "Archivo".
     */
    private void actualizarVisibilidadRuta() {
        boolean esModoArchivo = getOpcionSeleccionada().equals("Archivo");
        lblRuta.setVisible(esModoArchivo);
        txtRuta.setVisible(esModoArchivo);
        btnSeleccionarRuta.setVisible(esModoArchivo);
    }

    /**
     * Devuelve la opción de persistencia seleccionada actualmente en el JComboBox.
     *
     * @return Un String que representa la opción seleccionada ("Memoria" o "Archivo").
     */
    public String getOpcionSeleccionada() {
        String seleccionDisplay = (String) cbxSelecionar.getSelectedItem();
        return opcionMap.getOrDefault(seleccionDisplay, "Memoria");
    }

    /**
     * Devuelve la ruta del directorio seleccionada en el campo de texto.
     *
     * @return Un String con la ruta del directorio.
     */
    public String getRutaSeleccionada() {
        return txtRuta.getText();
    }

    /**
     * Añade un ActionListener al botón "Aceptar".
     *
     * @param listener El ActionListener a añadir.
     */
    public void addAceptarListener(ActionListener listener) {
        btnAceptar.addActionListener(listener);
    }

    /**
     * Muestra un cuadro de diálogo de error al usuario.
     * El título y el mensaje se obtienen del manejador de internacionalización.
     *
     * @param mensajeKey La clave para el mensaje de error en el archivo de recursos de internacionalización.
     */
    public void mostrarError(String mensajeKey) {
        String titulo = i18n.get("titulo.error");
        String mensajeTraducido = i18n.get(mensajeKey);
        JOptionPane.showMessageDialog(this, mensajeTraducido, titulo, JOptionPane.ERROR_MESSAGE);
    }
}