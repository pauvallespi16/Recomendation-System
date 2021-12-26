/** @file Lectura.java
 * @brief Código de la clase Lectura
 */

package persistencia;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/** @class Guardado
 * @brief Clase utilizada para guardar ficheros.
 *
 * @author Pau Vallespí Monclús
 * @date Diciembre 2021
 */

public class Lectura {
    /** @fn leerFichero
     * @brief Función utilitzada para leer un fichero
     * @pre path válido
     * @post Se ha devuelto un array list que contiene los datos del fichero
     * @param path Path del cuál se lee
     * @return Devuelve los datos en formato ArrayList<String>
     */
    public static ArrayList<String> leerFichero(String path) throws Exception {
        ArrayList<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                data.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
