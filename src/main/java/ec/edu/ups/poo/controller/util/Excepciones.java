package ec.edu.ups.poo.controller.util;

/**
 * Clase contenedora para las excepciones personalizadas de la aplicación.
 * <p>
 * Este enfoque utiliza clases internas estáticas para agrupar todas las excepciones
 * de negocio en un único archivo, facilitando su gestión y mantenimiento.
 * Todas las excepciones heredan de {@link RuntimeException} para no forzar su
 * manejo explícito en cada método.
 * </p>
 */
public class Excepciones {

    /**
     * Excepción lanzada cuando una regla de validación de datos no se cumple.
     * <p>
     * Se utiliza para errores como campos vacíos, formatos incorrectos (cédula, email),
     * o valores fuera de los rangos permitidos (ej. precio negativo).
     * </p>
     */
    public static class ValidacionException extends RuntimeException {
        /**
         * Construye una nueva ValidacionException.
         *
         * @param message La clave del mensaje de error para internacionalización.
         */
        public ValidacionException(String message) {
            super(message);
        }
    }

    /**
     * Excepción lanzada al intentar crear un dato que ya existe.
     * <p>
     * Típicamente se usa para violaciones de claves únicas, como intentar registrar
     * un producto con un código ya existente o un usuario con un username duplicado.
     * </p>
     */
    public static class DatoExistenteException extends RuntimeException {
        /**
         * Construye una nueva DatoExistenteException.
         *
         * @param message La clave del mensaje de error para internacionalización.
         */
        public DatoExistenteException(String message) {
            super(message);
        }
    }

    /**
     * Excepción lanzada cuando una operación (búsqueda, actualización, eliminación)
     * no encuentra el dato especificado.
     */
    public static class DatoNoEncontradoException extends RuntimeException {
        /**
         * Construye una nueva DatoNoEncontradoException.
         *
         * @param message La clave del mensaje de error para internacionalización.
         */
        public DatoNoEncontradoException(String message) {
            super(message);
        }
    }

    /**
     * Excepción lanzada específicamente cuando las credenciales de un usuario
     * (username o contraseña) son incorrectas durante el proceso de autenticación.
     */
    public static class CredencialesInvalidasException extends RuntimeException {
        /**
         * Construye una nueva CredencialesInvalidasException.
         *
         * @param message La clave del mensaje de error para internacionalización.
         */
        public CredencialesInvalidasException(String message) {
            super(message);
        }
    }
}