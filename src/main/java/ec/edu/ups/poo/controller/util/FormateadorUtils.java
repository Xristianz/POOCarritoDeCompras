package ec.edu.ups.poo.controller.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class FormateadorUtils {

    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda;

        // Forzar euros para francés
        if (locale.getLanguage().equals("fr")) {
            formatoMoneda = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        }
        // Forzar dólares para español
        else if (locale.getLanguage().equals("es")) {
            formatoMoneda = NumberFormat.getCurrencyInstance(locale);
            DecimalFormatSymbols symbols = ((DecimalFormat)formatoMoneda).getDecimalFormatSymbols();
            symbols.setCurrencySymbol("$");
            ((DecimalFormat)formatoMoneda).setDecimalFormatSymbols(symbols);
        }
        // Formato por defecto para otros idiomas
        else {
            formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        }

        return formatoMoneda.format(cantidad);
    }

    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }
}