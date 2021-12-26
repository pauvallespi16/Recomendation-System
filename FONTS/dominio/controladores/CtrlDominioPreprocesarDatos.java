/** @file CtrlDominioCargarDatos.java
 @brief Codigo auxiliar del controlador de dominio
 */
package controladores;

import clases.Parser;

import java.util.ArrayList;

/** @class CtrlDominioCargarDatos
 * @brief Clase utilizada como soporte para el controlador de la capa de dominio.
 *
 * Esta clase controla la relación con la capa de persistencia y se encarga de controlar el preproceso
 * de los archivos
 *
 * @author Pau Vallespí Monclús
 * @date Diciembre 2021
 */

public class CtrlDominioPreprocesarDatos {
    /** @fn preprocesarArchivos
     * @brief Función utilizada para preprocesar los archivos llamando al controlador de la capa de persistencia
     * @pre Path válido
     * @post Se han cargado los archivos
     * @param path Representa el path del cuál se quiere leer
     */
    public static void preprocesarArchivos (String path) throws Exception {
        CtrlDominioPreprocesarDatos.preprocesarTypes(path + "types.csv");
        CtrlDominioPreprocesarDatos.preprocesarItems(path + "items.csv");
        CtrlDominioPreprocesarDatos.preprocesarRatings(path + "ratings.db.csv");
        CtrlDominioPreprocesarDatos.preprocesarRatingsKnown(path + "ratings.test.known.csv");
        //CtrlDominioCargarDatos.preprocesarRatingsUnknown(path + "/ratings.test.unknown.csv");
    }

    /** @fn preprocesarTypes
     * @brief Función utilizada para llamar al preproceso del archivo types.csv llamando al controlador de la capa de persistencia
     * @pre Path válido
     * @post Se han cargado los archivos
     * @param path Representa el path del cuál se quiere leer
     */
    public static void preprocesarTypes (String path) throws Exception {
        ArrayList<String> datos = CtrlPersistencia.leerFichero(path);
        Parser.setTypes(datos);
    }

    /** @fn preprocesarItems
     * @brief Función utilizada para llamar al preproceso del archivo items.csv llamando al controlador de la capa de persistencia
     * @pre Path válido
     * @post Se han cargado los archivos
     * @param path Representa el path del cuál se quiere leer
     */
    public static void preprocesarItems (String path) throws Exception {
        ArrayList<String> datos = CtrlPersistencia.leerFichero(path);
        Parser.processItems(datos);
    }

    /** @fn preprocesarRatings
     * @brief Función utilizada para llamar al preproceso del archivo ratings.db.csv llamando al controlador de la capa de persistencia
     * @pre Path válido
     * @post Se han cargado los archivos
     * @param path Representa el path del cuál se quiere leer
     */
    public static void preprocesarRatings (String path) throws Exception {
        ArrayList<String> datos = CtrlPersistencia.leerFichero(path);
        CtrlDominio.setUsuariosRatings(Parser.processUsers(datos, Parser.Path.ratings));
    }

    /** @fn preprocesarRatingsKnown
     * @brief Función utilizada para llamar al preproceso del archivo ratings.test.known.csv llamando al controlador de la capa de persistencia
     * @pre Path válido
     * @post Se han cargado los archivos
     * @param path Representa el path del cuál se quiere leer
     */
    public static void preprocesarRatingsKnown (String path) throws Exception {
        ArrayList<String> datos = CtrlPersistencia.leerFichero(path);
        CtrlDominio.setUsuariosRatingsKnown(Parser.processUsers(datos, Parser.Path.ratingsKnown));
    }

    /** @fn preprocesarRatingsUknown
     * @brief Función utilizada para llamar al preproceso del archivo ratings.test.unknown.csv llamando al controlador de la capa de persistencia
     * @pre Path válido
     * @post Se han cargado los archivos
     * @param path Representa el path del cuál se quiere leer
     */
    public static void preprocesarRatingsUknown (String path) throws Exception {
        ArrayList<String> datos = CtrlPersistencia.leerFichero(path);
        CtrlDominio.setUsuariosRatingsUnknown(Parser.processUsers(datos, Parser.Path.ratingsUnknown));
    }

}
