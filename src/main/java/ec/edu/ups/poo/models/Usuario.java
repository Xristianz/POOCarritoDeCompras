package ec.edu.ups.poo.models;
public class Usuario {
    private String username;
    private String contrasenia;
    private Rol rol;
    private String pregunta1;
    private String pregunta2;
    private String pregunta3;

    public Usuario() {
    }

    public Usuario(String username, String contrasenia, Rol rol, String pregunta1, String pregunta2, String pregunta3) {
        this.username = username;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.pregunta1 = pregunta1;
        this.pregunta2 = pregunta2;
        this.pregunta3 = pregunta3;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(String pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public String getPregunta2() {
        return pregunta2;
    }

    public void setPregunta2(String pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public String getPregunta3() {
        return pregunta3;
    }

    public void setPregunta3(String pregunta3) {
        this.pregunta3 = pregunta3;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                ", pregunta1='" + pregunta1 + '\'' +
                ", pregunta2='" + pregunta2 + '\'' +
                ", pregunta3='" + pregunta3 + '\'' +
                '}';
    }

}