package ec.edu.ups.poo.view;

import ec.edu.ups.poo.DAO.PreguntaSeguridadDAO;
import ec.edu.ups.poo.DAO.impl.PreguntaSeguridadDAOMemoria;
import ec.edu.ups.poo.models.PreguntaSeguridad;
import ec.edu.ups.poo.models.Usuario;

import javax.swing.*;

import javax.swing.*;
import java.util.List;

public class UsuarioActualizar extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;
    private JPasswordField txtNuevaContrasenia;
    private JLabel lblPregunta1;
    private JTextField txtRespuesta1;
    private JLabel lblPregunta2;
    private JTextField txtRespuesta2;
    private JLabel lblPregunta3;
    private JTextField txtRespuesta3;
    private JButton btnActualizar;

    // NUEVOS JComboBox agregados desde el GUI Designer
    private JComboBox<PreguntaSeguridad> cmbPregunta1;
    private JComboBox<PreguntaSeguridad> cmbPregunta2;
    private JComboBox<PreguntaSeguridad> cmbPregunta3;

    public UsuarioActualizar() {
        super("Actualizar Usuario", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtCorreo.setEditable(false);
        txtTelefono.setEditable(false);
        txtFechaNacimiento.setEditable(false);
    }

    public void cargarDatosUsuario(Usuario usuario) {
        // Mostrar información (solo lectura)
        txtNombre.setText(usuario.getNombre());
        txtNombre.setEditable(false); // Hacer campo no editable

        txtApellido.setText(usuario.getApellido());
        txtApellido.setEditable(false);

        txtCorreo.setText(usuario.getCorreo());
        txtCorreo.setEditable(false);

        txtTelefono.setText(usuario.getTelefono());
        txtTelefono.setEditable(false);

        txtFechaNacimiento.setText(usuario.getFechaNacimiento());
        txtFechaNacimiento.setEditable(false);

        // Mostrar preguntas de seguridad (no editables)
        PreguntaSeguridadDAO preguntaDAO = new PreguntaSeguridadDAOMemoria();
        lblPregunta1.setText(preguntaDAO.buscarPorId(usuario.getPregunta1Id()).getTexto());
        lblPregunta2.setText(preguntaDAO.buscarPorId(usuario.getPregunta2Id()).getTexto());
        lblPregunta3.setText(preguntaDAO.buscarPorId(usuario.getPregunta3Id()).getTexto());

        // Limpiar campos editables
        txtNuevaContrasenia.setText("");
        txtRespuesta1.setText("");
        txtRespuesta2.setText("");
        txtRespuesta3.setText("");
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // ===== Getters y setters =====

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

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public JTextField getTxtApellido() {
        return txtApellido;
    }

    public void setTxtApellido(JTextField txtApellido) {
        this.txtApellido = txtApellido;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    public JTextField getTxtFechaNacimiento() {
        return txtFechaNacimiento;
    }

    public void setTxtFechaNacimiento(JTextField txtFechaNacimiento) {
        this.txtFechaNacimiento = txtFechaNacimiento;
    }

    public JPasswordField getTxtNuevaContrasenia() {
        return txtNuevaContrasenia;
    }

    public void setTxtNuevaContrasenia(JPasswordField txtNuevaContrasenia) {
        this.txtNuevaContrasenia = txtNuevaContrasenia;
    }

    public JLabel getLblPregunta1() {
        return lblPregunta1;
    }

    public void setLblPregunta1(JLabel lblPregunta1) {
        this.lblPregunta1 = lblPregunta1;
    }

    public JLabel getLblPregunta2() {
        return lblPregunta2;
    }

    public void setLblPregunta2(JLabel lblPregunta2) {
        this.lblPregunta2 = lblPregunta2;
    }

    public JTextField getTxtRespuesta1() {
        return txtRespuesta1;
    }

    public void setTxtRespuesta1(JTextField txtRespuesta1) {
        this.txtRespuesta1 = txtRespuesta1;
    }

    public JTextField getTxtRespuesta2() {
        return txtRespuesta2;
    }

    public void setTxtRespuesta2(JTextField txtRespuesta2) {
        this.txtRespuesta2 = txtRespuesta2;
    }

    public JLabel getLblPregunta3() {
        return lblPregunta3;
    }

    public void setLblPregunta3(JLabel lblPregunta3) {
        this.lblPregunta3 = lblPregunta3;
    }

    public JTextField getTxtRespuesta3() {
        return txtRespuesta3;
    }

    public void setTxtRespuesta3(JTextField txtRespuesta3) {
        this.txtRespuesta3 = txtRespuesta3;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }
}