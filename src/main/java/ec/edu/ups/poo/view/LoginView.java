package ec.edu.ups.poo.view;

import ec.edu.ups.poo.controller.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

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

    private MensajeInternacionalizacionHandler mensajeHandler;

    public LoginView() {
        // 1. Configuración básica del JFrame
        mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        setTitle(mensajeHandler.get("login.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // 2. Cargar componentes del GUI Designer (automático)
        setContentPane(panelPrincipal);

        // 3. Configurar el ComboBox (ya agregado en el .form)
        configurarIdiomas();
    }

    private void configurarIdiomas() {
        // Asegúrate de que cmbIdioma fue agregado en el GUI Designer
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
}