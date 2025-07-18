package ec.edu.ups.poo.controller.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase de utilidad para formatear datos comunes como fechas y monedas.
 * <p>
 * Proporciona métodos estáticos para convertir tipos de datos numéricos y de fecha
 * a cadenas de texto (String) localizadas, es decir, adaptadas a diferentes
 * idiomas y convenciones regionales.
 * </p>
 * Esta clase no está diseñada para ser instanciada.
 */
public final class FormateadorUtils {

    /**
     * Constructor privado para prevenir la instanciación de esta clase de utilidad.
     */
    private FormateadorUtils() {
    }

    /**
     * Formatea una cantidad numérica como una cadena de texto de moneda.
     * <p>
     * Utiliza la configuración regional (Locale) para determinar automáticamente
     * el símbolo monetario (ej. $, €), los separadores de miles y decimales,
     * y la posición del símbolo. Siempre formatea con dos decimales.
     * </p>
     *
     * @param cantidad El valor numérico (double) a formatear.
     * @param locale   La configuración regional que define el formato de la moneda.
     * @return Una cadena de texto con la cantidad formateada como moneda.
     */
    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        formatoMoneda.setMinimumFractionDigits(2);
        formatoMoneda.setMaximumFractionDigits(2);
        return formatoMoneda.format(cantidad);
    }

    /**
     * Formatea un objeto {@link Date} a una cadena de texto de fecha en formato mediano.
     * <p>
     * El formato de salida depende del {@link Locale} proporcionado. Por ejemplo,
     * para `en_US` podría ser "Jan 1, 2024", mientras que para `es_EC` sería "1 ene. 2024".
     * </p>
     *
     * @param fecha  El objeto {@code Date} a formatear.
     * @param locale La configuración regional que define el formato de la fecha.
     * @return Una cadena de texto con la fecha formateada.
     */
    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }
}