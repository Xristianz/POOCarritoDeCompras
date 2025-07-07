package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoginView extends JFrame {
    private JPanel panelSecundario;
    private JPanel panelPrincipal; //
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JButton olvidoSuContraseniaButton;
    private JLabel lblUsuario;
    private JLabel lblContrasenia;


    private JComboBox<String> cmbIdioma;
    private JLabel lblIniciarSesion;

    private MensajeInternacionalizacionHandler mensajeHandler;

    public LoginView() {
        mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setForeground(Color.BLACK);

        setTitle(mensajeHandler.get("login.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        configurarIdiomas();
        configurarIconos();
    }
    private void configurarIconos() {
        Dimension iconSize = new Dimension(25, 25);

        ImageIcon loginIcon = cargarIcono("/imagenes/iniciar-sesion.png", iconSize);
        ImageIcon registerIcon = cargarIcono("/imagenes/agregar-usuario.png", iconSize);
        ImageIcon forgotIcon = cargarIcono("/imagenes/restablecer-la-contrasena.png", iconSize);


        btnIniciarSesion.setIcon(loginIcon);
        btnRegistrarse.setIcon(registerIcon);
        olvidoSuContraseniaButton.setIcon(forgotIcon);


        btnIniciarSesion.setIconTextGap(10);
        btnRegistrarse.setIconTextGap(10);
        olvidoSuContraseniaButton.setIconTextGap(10);

        btnIniciarSesion.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnRegistrarse.setHorizontalTextPosition(SwingConstants.RIGHT);
        olvidoSuContraseniaButton.setHorizontalTextPosition(SwingConstants.RIGHT);
    }
    private ImageIcon cargarIcono(String ruta, Dimension size) {
        try {
            URL imgURL = getClass().getResource(ruta);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(
                        size.width, size.height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } else {
                System.err.println("No se encontró el archivo de icono: " + ruta);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el icono: " + e.getMessage());
            return null;
        }
    }


    private void configurarIdiomas() {
        cmbIdioma.setModel(new DefaultComboBoxModel<>(new String[]{"Español", "English", "Français"}));
        cmbIdioma.addActionListener(e -> {
            String idioma = (String) cmbIdioma.getSelectedItem();
            switch (idioma) {
                case "Español" -> cambiarIdioma("es", "EC");
                case "English" -> cambiarIdioma("en", "US");
                case "Français" -> cambiarIdioma("fr", "FR");
            }
        });
    }

    private void cambiarIdioma(String lenguaje, String pais) {
        mensajeHandler.setLenguaje(lenguaje, pais);
        setTitle(mensajeHandler.get("login.titulo"));

        // Actualizar textos (los componentes ya existen por el GUI Designer)
        lblUsuario.setText(mensajeHandler.get("login.usuario"));
        lblContrasenia.setText(mensajeHandler.get("login.contrasenia"));
        btnIniciarSesion.setText(mensajeHandler.get("login.boton.iniciar"));
        btnRegistrarse.setText(mensajeHandler.get("login.boton.registrar"));
        olvidoSuContraseniaButton.setText(mensajeHandler.get("login.boton.recuperar"));
    }


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }



    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public void setBtnIniciarSesion(JButton btnIniciarSesion) {
        this.btnIniciarSesion = btnIniciarSesion;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.btnRegistrarse = btnRegistrarse;
    }

    public void limpiarCampos() {
        txtUsername.setText("");
        txtContrasenia.setText("");
    }

    public boolean confirmarRegistro() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Registrar nuevo usuario con estos datos?",
                "Confirmar registro",
                JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

    public JButton getOlvidoSuContraseniaButton() {
        return olvidoSuContraseniaButton;
    }

    public void setOlvidoSuContraseniaButton(JButton olvidoSuContraseniaButton) {
        this.olvidoSuContraseniaButton = olvidoSuContraseniaButton;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}