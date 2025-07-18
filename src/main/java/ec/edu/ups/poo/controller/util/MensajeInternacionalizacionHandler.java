package ec.edu.ups.poo.controller.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Gestiona la internacionalización (i18n) de la aplicación.
 * <p>
 * Esta clase encapsula la lógica para cargar y acceder a los paquetes de recursos
 * ({@link ResourceBundle}) basados en una configuración regional ({@link Locale}).
 * Permite obtener cadenas de texto traducidas a partir de una clave y cambiar
 * el idioma de la aplicación dinámicamente.
 * </p>
 */
public class MensajeInternacionalizacionHandler {

    /**
     * Almacena los mensajes del idioma actual, cargados desde los archivos .properties.
     */
    private ResourceBundle bundle;
    /**
     * Representa la configuración regional actual (idioma y país).
     */
    private Locale locale;

    /**
     * Construye un nuevo manejador de internacionalización con un idioma y país iniciales.
     *
     * @param lenguaje El código de idioma de dos letras según ISO 639 (ej. "es", "en").
     * @param pais     El código de país de dos letras según ISO 3166 (ej. "EC", "US").
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        // Carga el archivo de propiedades correspondiente (ej. mensajes_es_EC.properties)
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el mensaje traducido correspondiente a una clave.
     *
     * @param key La clave del mensaje definida en el archivo de propiedades.
     * @return El mensaje ({@code String}) en el idioma configurado.
     */
    public String get(String key) {
        return bundle.getString(key);
    }

    /**
     * Cambia el idioma y el país de la aplicación en tiempo de ejecución.
     * <p>
     * Este método recarga el paquete de recursos para reflejar el nuevo idioma seleccionado.
     * </p>
     *
     * @param lenguaje El nuevo código de idioma (ej. "fr").
     * @param pais     El nuevo código de país (ej. "FR").
     */
    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Devuelve la configuración regional (Locale) actualmente en uso.
     *
     * @return El objeto {@link Locale} actual.
     */
    public Locale getLocale() {
        return locale;
    }
}