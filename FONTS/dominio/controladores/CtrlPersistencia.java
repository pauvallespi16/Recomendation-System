/** @file CtrlPersistencia.java
 * @brief Código de la clase Lectura
 */

package controladores;
import java.io.File;
import java.util.ArrayList;

import persistencia.Escritura;
import persistencia.Gestion;
import persistencia.Lectura;

/** @class CtrlPersistencia
 * @brief Clase utilizada como controlador de la capa de persistencia.
 *
 * Esta clase lee los datos que nos llegan en los ficheros .csv (items, ratings, ratingsKnown, ratingsUnkown, types).
 * Trata estos ficheros para poder usar los datos que nos llegan en los algoritmos de recomendación.
 *
 * @author Pau Vallespí Monclús
 * @date Diciembre 2021
 */

public class CtrlPersistencia {
    /** @fn leerFichero
     * @brief Función utilitzada para leer un fichero
     * @pre path válido
     * @post Se ha devuelto un array list que contiene los datos del fichero
     * @param path Path del cuál se lee
     * @return Devuelve los datos en formato ArrayList<String>
     */
    public static ArrayList<String> leerFichero(String path) throws Exception { return Lectura.leerFichero(path); }

    /** @fn guardarFichero
     * @brief Función utilitzada para guardar un fichero
     * @pre path válido
     * @post Se ha devuelto un array list que contiene los datos del fichero
     * @param datos Representa los datos que queremos guardar
     * @param path Representa el path donde queremos guardar los datos
     * @return Devuelve los datos en formato ArrayList<String>
     */
    public static void guardarFichero(String datos, String path) throws Exception { Gestion.guardarFichero(datos, path); }

    /** @fn borrarFichero
     * @brief Función utilitzada para borrar archivos
     * @pre path válido
     * @post Se ha borrado el archivo
     * @param path Path del archivo que queremos borrar
     */
    public static void borrarFichero(String path) throws Exception { Gestion.borrarFichero(path); }

    /** @fn borrarCarpeta
     * @brief Función utilitzada para borrar carpetas
     * @pre path válido
     * @post Se ha borrado los archivos de la carpeta
     * @param path Path del archivo que queremos borrar
     */
    public static void borrarCarpeta(String path) { Gestion.borrarCarpeta(path); }

    /** @fn mostrarDirectorios
     * @brief Función utilitzada para mostrar los directorios
     * @pre -
     * @post Se escriben por pantalla los directorios y sus subdirectorios
     */
    public static void mostrarDirectorios() throws Exception { Escritura.escribirDirectorios(); }

    public static void escribirPantalla(String valor) { Escritura.escribirPantalla(valor); }

    public static void comprobarCarpeta(String path) throws Exception { Gestion.crearDirectorio(path); };
}
