/** @file CtrlDominioCargarDatos.java
 @brief Codigo auxiliar del controlador de dominio
 */
package controladores;

import clases.Parser;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/** @class CtrlDominioCargarDatos
 * @brief Clase utilizada como soporte para el controlador de la capa de dominio.
 *
 * Esta clase se encarga de controlar la carga de los datos que ya han sido preprocesados,
 * para evitar preprocesarlos de nuevo
 *
 * @author Pau Vallespí Monclús
 * @date Diciembre 2021
 */
public class CtrlDominioCargarDatos {
    /** @fn cargarArchivos
     * @brief Función utilizada para preprocesar los archivos llamando al controlador de la capa de persistencia
     * @pre Path válido
     * @post Se han cargado los archivos
     * @param path Representa el path del cuál se quiere leer
     */
    public static void cargarArchivos (String path) throws Exception {
        CtrlDominioCargarDatos.cargarItems(path + "items.txt");
        CtrlDominioCargarDatos.cargarRatings(path + "ratings.txt");
        CtrlDominioCargarDatos.cargarRatingsKnown(path + "ratingKnown.txt");

        //if (eval) CtrlDominioCargarDatos.cargarRatingsUnknown(path + "ratingsUnknown.txt");
    }

    /** @fn preprocesarItems
     * @brief Función utilizada para llamar al preproceso del archivo items.csv llamando al controlador de la capa de persistencia
     * @pre Path válido
     * @post Se han cargado los archivos
     * @param path Representa el path del cuál se quiere leer
     */
    public static void cargarItems (String path) throws Exception {
        ArrayList<String> datos = CtrlPersistencia.leerFichero(path);
        Parser.loadItems(datos);
    }

    /** @fn preprocesarRatings
     * @brief Función utilizada para llamar al preproceso del archivo ratings.db.csv llamando al controlador de la capa de persistencia
     * @pre Path válido
     * @post Se han cargado los archivos
     * @param path Representa el path del cuál se quiere leer
     */
    public static void cargarRatings (String path) throws Exception {
        ArrayList<String> datos = CtrlPersistencia.leerFichero(path);
        CtrlDominio.setUsuariosRatings(Parser.loadUsers(datos, Parser.Path.ratings));
    }

    /** @fn preprocesarRatingsKnown
     * @brief Función utilizada para llamar al preproceso del archivo ratings.test.known.csv llamando al controlador de la capa de persistencia
     * @pre Path válido
     * @post Se han cargado los archivos
     * @param path Representa el path del cuál se quiere leer
     */
    public static void cargarRatingsKnown (String path) throws Exception {
        ArrayList<String> datos = CtrlPersistencia.leerFichero(path);
        CtrlDominio.setUsuariosRatingsKnown(Parser.loadUsers(datos, Parser.Path.ratingsKnown));
    }

    /** @fn preprocesarRatingsUknown
     * @brief Función utilizada para llamar al preproceso del archivo ratings.test.unknown.csv llamando al controlador de la capa de persistencia
     * @pre Path válido
     * @post Se han cargado los archivos
     * @param path Representa el path del cuál se quiere leer
     */
    public static void cargarRatingsUnknown (String path) throws Exception {
        ArrayList<String> datos = CtrlPersistencia.leerFichero(path);
        CtrlDominio.setUsuariosRatingsUnknown(Parser.loadUsers(datos, Parser.Path.ratingsUnknown));
    }

}
