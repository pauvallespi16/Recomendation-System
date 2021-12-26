package persistencia;
/** @file Gestion.java
 * @brief Código de la clase Gestion
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/** @class Gestion
 * @brief Clase utilizada para la gestion de ficheros (guardar, comprobar ...).
 *
 * @author Pau Vallespí Monclús
 * @date Diciembre 2021
 */

public class Gestion {
    /** @fn guardarFichero
     * @brief Función utilitzada para guardar un fichero
     * @pre path válido
     * @post Se ha devuelto un array list que contiene los datos del fichero
     * @param datos Representa los datos que queremos guardar
     * @param path Representa el path donde queremos guardar los datos
     */
    public static void guardarFichero(String datos, String path) throws Exception {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path))) {
            br.write(datos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** @fn borrarFichero
     * @brief Función utilitzada para borrar archivos
     * @pre path válido
     * @post Se ha borrado el archivo
     * @param path Path del archivo que queremos borrar
     */
    public static void borrarFichero(String path) {
        File myObj = new File(path);
        if (myObj.exists()) myObj.delete();
    }

    /** @fn borrarCarpeta
     * @brief Función utilitzada para borrar carpetas
     * @pre path válido
     * @post Se ha borrado los archivos de la carpeta
     * @param path Path del archivo que queremos borrar
     */
    public static void borrarCarpeta(String path) {
        File index = new File(path);
        String[] entries = index.list();
        assert entries != null;
        for (String s: entries) borrarFichero(path + "/" + s);
    }

    /** @fn crearDirectorio
     * @brief Función utilitzada para crear una carpeta
     * @pre path válido
     * @post Se ha creado la carpeta
     * @param path Representa el path donde queremos guardar los datos
     */
    public static void crearDirectorio(String path) throws IOException {
        File myObj = new File(path);
        if (!myObj.exists()) myObj.mkdirs();
    }
}
