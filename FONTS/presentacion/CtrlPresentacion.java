package presentacion; /** @file CtrlPresentacion.java
 @brief Codigo del controlador de presentacion
 */
import clases.*;
import controladores.CtrlDominio;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

/** @class CtrlPresentacion
 * @brief La clase del controlador de presentacion interactua entre la presentacion y el controlador de dominio
 *
 * @author Carla Campàs Gené
 *
 * @date December 2021
 */
public class CtrlPresentacion {
    /**
     * @var CtrlDominio ctrlDominio
     * @brief instancia del controlador de dominio
     */
    private static CtrlDominio ctrlDominio;
    /**
     * @var vistaPrincipal vistaPrincipal
     * @brief instancia de la vista principal usada para lanzar errores
     */
    private static vistaPrincipal vistaPrincipal;
    /**
     * @var CtrlPresentacion cp
     * @brief instancia de la clase, usada para mantener la clase estatica
     */
    private static CtrlPresentacion cp = new CtrlPresentacion();

    /**
     * @var JFrame frame
     * @brief frame en el cual vamos a poner todas las vistas y controlar estas
     */
    private static JFrame frame;

    /**
     * @fn CtrlPresentacion()
     * @brief constructora por defecto, inicializa todas las variables globales
     * @pre no hay instancia de la clase
     * @post se ha creado una instancia de la clase con las variables globales inicializadas
     */
    public CtrlPresentacion() {
        ctrlDominio = new CtrlDominio();
        frame = new vistaPrincipal(cp);
    }

    /**
     * @fn startFrame()
     * @brief abrir el frame para que lo pueda ver el usuario
     * @pre el frame se ha inicializado con una de las vistas que puede tener el usuario
     * @post se ha inicializado el frame con el panel asociaddo
     */
    public static void startFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setSize(850, 500);
                frame.setVisible(true);
            }
        });
    }

    /**
     * @fn iniPresentacion()
     * @brief inicializar la presentacion para el usuario
     * @pre Existe una instancia de la clase CtrlPersistencia con las variables globales incializadas
     * @post se muestra la vista principal al usuario
     */
    public static void iniPresentacion() {
        frame = new cargarDatos(cp);
        startFrame();
    }

    /**
     * @param view: a que vista quiere cambiar
     * @fn changeFrame()
     * @brief cambiar el panel que mostramos al usuario
     * @pre el usuario está interactuando con la aplicación y desea cambiar de vista
     * @post se enseña la nueva vista que desea ver el usuario
     */
    public static void changeFrame(String view) throws Exception {
        switch (view) {
            case "cargarDatos":
                guardarSession();
                frame.setVisible(false);
                frame = new cargarDatos(cp);
                startFrame();
                break;
            case "vistaPrincipal":
                if (!(frame instanceof cargarDatos)) guardarSession();
                frame.setVisible(false);
                frame = new vistaPrincipal(cp);
                startFrame();
                break;
            case "usuarioMain":
                frame.setVisible(false);
                frame = new usuarioMain(cp);
                startFrame();
                break;
            case "vistaItems":
                frame.setVisible(false);
                frame = new vistaItems(cp);
                startFrame();
                break;
            case "vistaRecomendaciones":
                frame.setVisible(false);
                frame = new vistaRecomendaciones(cp);
                startFrame();
                break;
            case "añadirItem":
                frame.setVisible(false);
                frame = new añadirItem(cp);
                startFrame();
                break;
            case "historialRecomendaciones":
                frame.setVisible(false);
                frame = new historialRecomendaciones(cp);
                startFrame();
                break;
        }
    }

    /** @fn guardarSession()
     * @brief Guarda los archivos procesados.
     * @pre Existen sesiones.
     * @post Se han guardados los archivos procesados.
     */
    public static void guardarSession() throws Exception {
        ctrlDominio.guardarSession();
    }

    /** @fn enterNewUser
     * @brief entra un usuario nuevo a la aplicacion de unos ficheros selecionados
     *
     * @pre existe un objeto de la clase CtrlPresentacion
     * @post se ha entrado el nuevo usuario y cargado sus datos, o lanzado un error
     * @param userID usuario que interactuara con la aplicación
     * @param eval si el usuario quiere una evaluacion de las predicciones que haga el sistema
     */
    public void enterNewUser (int userID, boolean eval) {
        try {
            ctrlDominio.enterNewUser (userID, eval);
        } catch (Exception e){
            vistaPrincipal.hayError(e.toString());
        }
    }
    /** @fn getValoracionesDeUsuario
     * @return se devuelven las valoraciones que pertenecen a este usuario
     * @brief coger el mapa de valoraciones del usuario
     * @pre existe un objeto de la clase, con el usuario inicializado correctamente
     * @post se ha devuelto las valoraciones que pertenecen a este usuario
     */
    public Map<Integer, Double> getValoracionesDeUsuario() {
        return CtrlDominio.getValoracionesDeUsuario();
    }

    /** @fn createNewValoracion()
     * @brief añadir una valoración para el usuario activo en la sesión activa
     *
     * @pre existe un objeto de la clase, con el usuario inicializado correctamente
     * @post se ha añadido la valoracion al mapa de valoraicones del usuario
     * @param itemID:     id del item que el usuario desea valorar
     * @param valoracion: valoración asociada a este item
     */
    public void createNewValoracion(int itemID, Double valoracion) {
        ctrlDominio.createNewValoracion(itemID, valoracion);
    }

    /**
     * @param itemID: id del item del cual queremos quitar la valoración
     * @fn quitarValoracion()
     * @brief quitar una valoración del conjunto de valoraciones del usuario
     * @pre existe un objeto de la clase, con el usuario inicializado correctamente
     * @post se ha quitado la valoración del mapa de valoraciones del usuario
     */
    public void quitarValoracion(int itemID) {
        ctrlDominio.quitarValoracion(itemID);
    }

    public void addNewItem(String id, Map<String, Atributo> attributes) {
        ctrlDominio.addNewItem(id, attributes);
    }

    public void modifyItem(String id, Map<String, Atributo> newAttributes) {
        ctrlDominio.modifyItem(id, newAttributes);
    }

    /**
     * @return vector con los atributos de los items de las cabeceras que queremos
     * @fn getAllItemAtrbutes()
     * @brief coger atributos de todos los items
     * @pre existe un objeto de la clase
     * @post
     */
    public Vector getAllItemAtrbutes() {
        return ctrlDominio.getAllItemAtrbutes();
    }

    /**
     * @param type: tipo de algoritmo de recomendación que queremos ejecutar
     * @return las recomendaciones para el usuario
     * @fn getPredicciones()
     * @brief coger predicciones de los algoritmos de recomendación
     * @pre existe un objeto de la clase, con el usuario inicializado correctamente
     * @post se ha ejecutado la recomendación que quiere el usuario y se ha devuelto la predicción
     */
    public Vector getPredicciones(String type, String name, int size) throws Exception {
        return ctrlDominio.getPredicciones(type, name, size);
    }

    /** @fn getuId()
     * @brief getter del id original del usuario.
     * @pre -
     * @post Se ha obtenido el id original del usuario
     */
    public Integer getuId() {
        return ctrlDominio.getuId();
    }

    /** @fn getHeaders()
     * @brief getter de los headers.
     * @pre -
     * @post Se han obtenido los headers de los atributos de los items.
     */
    public String[] getHeaders() {
        return ctrlDominio.getHeaders();
    }

    /** @fn getTypes()
     * @brief getter de los tipos de los atributos de los items.
     * @pre -
     * @post Se han obtenido los tipos de los atributos de los items.
     */
    public String[] getTypes() {
        return ctrlDominio.getTypes();
    }

    /** @fn getItems()
     * @brief getter de los items.
     * @pre -
     * @post Se han obtenido los items.
     */
    public Items getItems() {
        return ctrlDominio.getItems();
    }

    /** @fn añadirUsuario()
     * @brief Añade un usuario.
     * @pre el path es correcto y el usuario con id userID no existe.
     * @post Se ha añadido un nuevo usuario con id userID
     * @param path: path donde añadir el usuario.
     * @param userID: id del usuario que crearemos.
     */
    public void añadirUsuario (String path, int userID) throws Exception {
      ctrlDominio.añadirUsuario(path, userID);
    }

    /** @fn existsId()
     * @brief Traduce el id original al asignado por el programa.
     * @pre El item con el id pasado por parametro existe.
     * @post Se ha devuelto el id asignado por el programa para ese item.
     * @param id: id original del item.
     */
    public boolean existsId(Integer id) {
        return ctrlDominio.containsItemId(id) != -1;
    }

    /** @fn getRecomendaciones
     * @brief Función utilizada para conseguir un conjunto de recomendaciones
     * @pre -
     * @post Se ha cargado el conjunto de recomendaciones
     * @return Conjunto de recomendaciones que se ha pedido
     */
    public ArrayList<Recomendacion> getRecomendaciones() throws Exception {
        return ctrlDominio.cargarHistorial().getRecomendaciones();
    }

    /** @fn getRecomsVector()
     * @brief Obtiene el vector de recomendaciones.
     * @pre La recomendacion raux tiene que existir.
     * @post Se ha obtenido el vector de recomendaciones con nombre raux.
     * @param r: nombre de la recomendacion.
     */
    public Vector getRecomsVector(String r) {
        return ctrlDominio.getRecomsVector(r);
    }

    /** @fn eliminarRecomItem()
     * @brief elimina items de recomendaciones.
     * @pre Existe el item con id itemId en la recomendacion llamada recom.
     * @post Se ha eliminado el item de la recomendacion.
     * @param r: nombre de la recomendacion.
     * @param itemId: id del item.
     */
    public void eliminarRecomItem(String r, Integer itemId) throws Exception {
        ctrlDominio.eliminarRecomItem(r, itemId);
    }

    /** @fn getHistorialRecomendaciones()
     * @brief Obtiene el historial de las recomendaciones.
     * @pre la variable r esta inicializada.
     * @post Se ha devuelto el historial de recomendaciones.
     * @return Retorna un ArrayList de strings que contiene el historial de recomendaciones
     */
    public ArrayList<String> getHistorialRecomendaciones() {
        return ctrlDominio.getHistorialRecomendaciones();
    }

    /** @fn cargarDatos
     * @brief Carga los datos de los archivos dados
     * @pre El string path esta inicializado y pertenece a un path correcto
     * @post Se han cargado los datos
     */
    public void cargarDatos (String path) throws Exception {
        ctrlDominio.cargarDatos(path);
    }

    public ArrayList<Integer> getItemsRecomendacion (String recom) {
        return ctrlDominio.getItemsRecomendacion(recom);
    }

    /** @fn cargarDatosProcesados()
     * @brief Carga los datos preprocesados.
     * @pre El path es correcto.
     * @post Se han cargado los datos preprocesados.
     * @param path: path de los datos procesados.
     * @param eval: indica si se quiere evaluar.
     */
    public void cargarDatosProcesados (String path, boolean eval) throws Exception {
        ctrlDominio.cargarDatosProcesados(path, eval);
    }

    /** @fn sendError()
     * @brief envia un error.
     * @pre la variable err esta inicializada.
     * @post Se ha enviado el error err.
     * @param s: variable que contiene el error.
     */
    public static void sendError (String s) {
        vistaPrincipal.hayError(s);
    }

    /** @fn evaluarRecomendaciones()
     * @brief Evalua la calidad de las recomendaciones.
     * @pre name esta inicializado.
     * @post Se ha devuelto un double con la calidad de la solucion.
     * @return Devuelve un double con la calidad de la solucion.
     * @param name: nombre de la recomendacion.
     */
    public static double evaluarRecomendaciones(String name) {
        return ctrlDominio.evaluarRecomendaciones(name);
    }

    /** borrarRecomendacionHistorial
     * @brief borrar una recomendacion del historial
     * @pre existe objeto
     * @post se ha borrado si existia la recomendacion con nombre name
     * @param name nombre de la recomendacion
     */
    public static void borrarRecomendacionHistorial (String name) { ctrlDominio.borrarRecomendacionHistorial(name); }
}
