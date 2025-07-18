package ec.edu.ups.poo.DAO.impl;

import ec.edu.ups.poo.DAO.PreguntaSeguridadDAO;
import ec.edu.ups.poo.models.PreguntaSeguridad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz {@link PreguntaSeguridadDAO} para persistencia en archivos de texto.
 * <p>
 * Gestiona la lectura de preguntas de seguridad desde un archivo de texto plano (preguntas.txt).
 * Si el archivo no existe en la ruta especificada, lo crea automáticamente con un
 * conjunto de preguntas por defecto.
 * </p>
 */
public class PreguntaSeguridadDaoTexto implements PreguntaSeguridadDAO {

    private final String RUTA_ARCHIVO;

    /**
     * Construye una nueva instancia del DAO y define la ruta del archivo de datos.
     *
     * @param basePath La ruta del directorio base donde se creará o leerá el archivo "preguntas.txt".
     */
    public PreguntaSeguridadDaoTexto(String basePath) {
        this.RUTA_ARCHIVO = basePath + File.separator + "preguntas.txt";
        crearArchivoSiNoExiste();
    }

    /**
     * Lee todas las líneas del archivo de texto y las convierte en una lista de objetos {@link PreguntaSeguridad}.
     * <p>
     * Maneja internamente errores de formato numérico o de lectura de archivo.
     * </p>
     *
     * @return una {@code List<PreguntaSeguridad>} con todas las preguntas de seguridad encontradas.
     */
    @Override
    public List<PreguntaSeguridad> listarTodas() {
        List<PreguntaSeguridad> preguntas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",", 2);
                if (partes.length == 2) {
                    int id = Integer.parseInt(partes[0]);
                    String textoKey = partes[1];
                    preguntas.add(new PreguntaSeguridad(id, textoKey));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer el archivo de preguntas: " + e.getMessage());
            e.printStackTrace();
        }
        return preguntas;
    }

    /**
     * Busca una pregunta de seguridad específica por su ID único.
     *
     * @param id El ID de la pregunta a buscar.
     * @return el objeto {@code PreguntaSeguridad} si se encuentra, o {@code null} si no.
     */
    @Override
    public PreguntaSeguridad buscarPorId(int id) {
        return listarTodas().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Método de ayuda privado que verifica si el archivo de preguntas existe.
     * Si no existe, lo crea y lo puebla con un conjunto de 10 preguntas por defecto.
     * El texto de cada pregunta se guarda como una clave de internacionalización.
     */
    private void crearArchivoSiNoExiste() {
        File file = new File(RUTA_ARCHIVO);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("1,pregunta.mascota");
                writer.println("2,pregunta.ciudad");
                writer.println("3,pregunta.color");
                writer.println("4,pregunta.amigo");
                writer.println("5,pregunta.comida");
                writer.println("6,pregunta.profesor");
                writer.println("7,pregunta.pelicula");
                writer.println("8,pregunta.colegio");
                writer.println("9,pregunta.deporte");
                writer.println("10,pregunta.abuela");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}