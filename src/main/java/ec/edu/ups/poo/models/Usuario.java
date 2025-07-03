package ec.edu.ups.poo.models;
public class Usuario {
    private String username;
    private String contrasenia;
    private Rol rol;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String fechaNacimiento;
    private int pregunta1Id;
    private String respuesta1;
    private int pregunta2Id;
    private String respuesta2;
    private int pregunta3Id;
    private String respuesta3;

    public Usuario() {
    }

    public Usuario(String username, String contrasenia, Rol rol, String nombre, String apellido,
                   String correo, String telefono, String fechaNacimiento,
                   int pregunta1Id, String respuesta1,
                   int pregunta2Id, String respuesta2,
                   int pregunta3Id, String respuesta3) {
        this.username = username;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.pregunta1Id = pregunta1Id;
        this.respuesta1 = respuesta1;
        this.pregunta2Id = pregunta2Id;
        this.respuesta2 = respuesta2;
        this.pregunta3Id = pregunta3Id;
        this.respuesta3 = respuesta3;
    }

    // Getters y Setters para todos los campos
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getPregunta1Id() {
        return pregunta1Id;
    }

    public void setPregunta1Id(int pregunta1Id) {
        this.pregunta1Id = pregunta1Id;
    }

    public String getRespuesta1() {
        return respuesta1;
    }

    public void setRespuesta1(String respuesta1) {
        this.respuesta1 = respuesta1;
    }

    public int getPregunta2Id() {
        return pregunta2Id;
    }

    public void setPregunta2Id(int pregunta2Id) {
        this.pregunta2Id = pregunta2Id;
    }

    public String getRespuesta2() {
        return respuesta2;
    }

    public void setRespuesta2(String respuesta2) {
        this.respuesta2 = respuesta2;
    }

    public int getPregunta3Id() {
        return pregunta3Id;
    }

    public void setPregunta3Id(int pregunta3Id) {
        this.pregunta3Id = pregunta3Id;
    }

    public String getRespuesta3() {
        return respuesta3;
    }

    public void setRespuesta3(String respuesta3) {
        this.respuesta3 = respuesta3;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                '}';
    }
}