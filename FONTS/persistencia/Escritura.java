/** @file Escritura.java
 * @brief Código de la clase Escritura
 */

package persistencia;
import java.io.File;

/** @class Guardado
 * @brief Clase utilizada para el control de ficheros (guardar, comprobar ...).
 *
 * @author Pau Vallespí Monclús
 * @date Diciembre 2021
 */

public class Escritura {
    /** @fn escribirDirectorios
     * @brief Función utilitzada para mostrar los directorios
     * @pre -
     * @post Se escriben por pantalla los directorios y sus subdirectorios
     */
    public static void escribirDirectorios() throws Exception {
        File directoryPath = new File("DATA/csv");
        String[] contents = directoryPath.list();
        assert contents != null;
        for (String word : contents) {
            if (!(word.charAt(0) == '.')) {
                System.out.println(" - " + word);
                File file = new File("DATA/csv/" + word);
                String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
                assert directories != null;
                if (directories.length != 0) {
                    for (String s : directories)
                        System.out.println("   - " + s);
                }
            }
        }
    }

    /** @fn escribirPantalla
     * @brief Función utilitzada para escribir por pantalla
     * @pre -
     * @post Se escribe por pantalla
     * @param valor Lo que queremos escribir
     */
    public static void escribirPantalla(String valor) { System.out.println(valor); }
}
