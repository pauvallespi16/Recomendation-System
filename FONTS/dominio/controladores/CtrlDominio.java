/** @file CtrlDominio.java
 @brief Codigo del controlador de dominio
 */
package controladores;

import clases.*;
import presentacion.CtrlPresentacion;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

/** @class CtrlDominio
 * @brief La clase de controlador de dominio que interactua con presentacion y las clases de dominio
 *
 * @author Carla Campàs Gené
 *
 * @date December 2021
 */
public class CtrlDominio {
    /** @var
     *  @brief insntancia del controlador de dominio */
    private static CtrlDominio cd = new CtrlDominio();

    /** @var Usuario u
     *  @brief usuario desde el cual vamos a ejecutar las clases del dominio */
    private static Usuario u;

    /** @var Usuarios usuariosRatingsUnknown
     *  @brief informacion sobre los ratings que aún no sabemos (test) */
    private static Usuarios usuariosRatingsUnknown;

    /** @var Usuarios usuariosRatingsKnown
     *  @brief informacion sobre los ratings que ya sabemos (test) */
    private static Usuarios usuariosRatingsKnown;

    /** @var Usuarios usuariosRatings
     *  @brief informacion sobre los usuarios generales (training) */
    private static Usuarios usuariosRatings;

    /** @var si el usuario require evaluacion o no
     *  @brief si el usuario require evaluacion o no */
    static boolean wantEval;

    /** @var path del archivo
     *  @brief path del archivo */
    private static String path;

    /** @var recomendaciones realizadas al usuario
     *  @brief recomendaciones realizadas al usuario */
    private static Recomendaciones r;

    /** @var Path necesario para coger elementos de los csv
     *  @brief Path necesario para coger elementos de los csv */
    private static String pathNeeded;

    /** @fn CtrlDominio()
     * @brief constructora por defecto
     * @pre -
     * @post se ha creado una instancia vacia de la clase CtrlDominio
     */
    public CtrlDominio() {
        wantEval = false;
        r = new Recomendaciones();
    }

    /**@fn getInstance()
     * @brief retorna la instancia del controlador de dominio
     * @pre -
     * @post se ha devuelto la instancia de esta clase
     * @return se devuelve la instancia de la clase, una instancia nueva si aun no se ha inicializado
     */
    public static CtrlDominio getInstance() {
        if(cd == null) cd = new CtrlDominio();
        return cd;
    }

    /**@fn getPathNeeded()
     * @brief getter de pathNeeded
     * @pre se ha inicializado pathNeeded
     * @post se ha devuelto el valor de pathNeeded
     * @return se devuelve el valor de pathNeeded
     */
    public String getPathNeeded () {
        return this.pathNeeded;
    }

    /** @fn preprocesarArchivos
     * @brief Función utilizada para preprocesar los archivos llamando al controlador de la capa de persistencia
     * @pre path válido
     * @post Se han cargado los archivos
     * @param path representa el path del cuál se quiere leer
     */
    public static void preprocesarArchivos(String path) throws Exception { CtrlDominioPreprocesarDatos.preprocesarArchivos(path); }

    /** @fn guardarArchivos
     * @brief Función utilizada para guardar los archivos llamando al controlador de la capa de persistencia
     * @pre Objeto válido (items o usuarios) y path válido
     * @post Se han guardado los archivos
     * @param obj Objetos que queremos guardar
     * @param path Representa el path del cuál se quiere leer
     */
    public static void guardarArchivos(Object obj, String path) throws Exception {
        if (obj instanceof Items) {
            Parser.setAllItems((Items) obj);
            CtrlPersistencia.guardarFichero(Parser.getAllItems().toString(), path);
        }
        if (obj instanceof Usuarios) {
            ArrayList<String> aux = new ArrayList<>();
            Collections.addAll(aux, obj.toString().split("\n"));
            CtrlPersistencia.guardarFichero(Parser.loadUsers(aux, Parser.Path.ratingsKnown).toString(), path);
        }
    }

    /** @fn cargarRecomendacion
     * @brief Función utilizada para cargar una recomendacion
     * @pre Nombre válido
     * @post Se ha cargado la recomendacion
     * @param recomName representa el nombre de la recomendacion se quiere leer
     * @return Recomendacion que se ha pedido
     */
    public static Recomendacion cargarRecomendacion(String recomName) throws Exception {
        ArrayList<String> aux = CtrlPersistencia.leerFichero("DATA/procesado/" + pathNeeded + '/' + u.getOriginalUserId() + "/" + recomName + ".txt");
        return Parser.loadRecoms(u, usuariosRatings, aux);
    }

    /** @fn guardarRecomendacion
     * @brief Función utilizada para guardar los archivos llamando al controlador de la capa de persistencia
     * @pre Objeto válido
     * @post Se han guardado los archivos
     * @param recom Recomendacion a guardar
     * @param path Representa el path del cuál se quiere leer
     */
    public static void guardarRecomendacion (Recomendacion recom, String path) throws Exception {
        CtrlPersistencia.guardarFichero(recom.toString(), path);
    }

    /** @fn cargarHistorial
     * @brief Función utilizada para cargar un conjunto de recomendaciones
     * @pre -
     * @post Se ha cargado el conjunto de recomendaciones
     * @return Conjunto de recomendaciones que se ha pedido
     */
    public static Recomendaciones cargarHistorial() throws Exception {
        ArrayList<String> aux = CtrlPersistencia.leerFichero("DATA/procesado/" + pathNeeded + '/' + u.getOriginalUserId() + "/historial.txt");
        return Parser.loadHistorial(aux);
    }

    /** @fn guardarHistorial
     * @brief Función utilizada para guardar los archivos llamando al controlador de la capa de persistencia
     * @pre Objeto válido
     * @post Se han guardado el historial
     * @param path Representa el path del cuál se quiere leer
     */
    public static void guardarHistorial (String path) throws Exception {
        CtrlPersistencia.comprobarCarpeta(path);
        for (Recomendacion recom : r.getRecomendaciones())
            guardarRecomendacion(recom, path + "/" + recom.getName() + ".txt");
        CtrlPersistencia.guardarFichero(r.toString(), path + "/historial.txt");
    }

    /** @fn setUsuariosRatings
     * @brief Función utilizada para establecer los usuarios del archivo ratings
     * @pre -
     * @post Los usuarios de ratings se han establecido
     * @param us Usuarios que se van a establecer como los de la clase
     */
    public static void setUsuariosRatings(Usuarios us) { usuariosRatings = us; }

    /** @fn setUsuariosRatingsKnown
     * @brief Función utilizada para establecer los usuarios del archivo ratings known
     * @pre -
     * @post Los usuarios de ratings se han establecido
     * @param us Usuarios que se van a establecer como los de la clase
     */
    public static void setUsuariosRatingsKnown(Usuarios us) { usuariosRatingsKnown = us; }

    /** @fn setUsuariosRatingsUnknown
     * @brief Función utilizada para establecer los usuarios del archivo ratings unknown
     * @pre -
     * @post Los usuarios de ratings se han establecido
     * @param us Usuarios que se van a establecer como los de la clase
     */
    public static void setUsuariosRatingsUnknown(Usuarios us) { usuariosRatingsUnknown = us; }

    /** @fn getUsuariosRatings
     * @brief
     * @pre La clase tiene instancia de usuariosRatings
     * @post Se ha devuelto usuariosRatings
     * @return Devuelve la estructura de datos que contiene los usuarios
     */
    public static Usuarios getUsuariosRatings() { return usuariosRatings; }

    /** @fn getUsuariosRatingsKnown
     * @brief
     * @pre La clase tiene instancia de usuariosRatingsKnown
     * @post Se ha devuelto usuariosRatingsKnown
     * @return Devuelve la estructura de datos que contiene los usuarios
     */
    public static Usuarios getUsuariosRatingsKnown() { return usuariosRatingsKnown; }

    /** @fn getUsuariosRatingsUnknown
     * @brief
     * @pre La clase tiene instancia de usuariosRatingsUnknown
     * @post Se ha devuelto usuariosRatingsUnknown
     * @return Devuelve la estructura de datos que contiene los usuarios
     */
    public static Usuarios getUsuariosRatingsUnknown() { return usuariosRatingsUnknown; }

    public static void mostrarItems() {
        Items items = Parser.getAllItems();
        String valor = "";
        for (Item it : items.getItems().values()) {
            valor += "ID: " + it.getOriginalId() + ", ATRIBUTOS: \n";
            for (Map.Entry<String, Atributo> at : it.getAtributos().entrySet())
                valor += " " + at.getKey() + ": " + at.getValue().getValor() + "\n";
        }
        CtrlPersistencia.escribirPantalla(valor);
    }

    public static void mostrarValoraciones() {
        Map<Integer, Double> recoms = u.getValoraciones();
        String valor = "";
        for (Map.Entry<Integer, Double> entry : recoms.entrySet()) valor += "ITEM ID: " + entry.getKey() + ", VALORACION: " + entry.getValue() +"\n";
        CtrlPersistencia.escribirPantalla(valor);
    }

    /** @fn mostrarHistorial
     * @brief Muestra el historial en la aplicacion.
     * @pre El parametro r esta inicializado.
     * @post Se ha mostrado el historial en la aplicacion
     */
    public static void mostrarHistorial() {
        if (r.getRecomendaciones().size() != 0) CtrlPersistencia.escribirPantalla(r.toString());
    }


    /** @fn cargarDatos
     * @brief Carga los datos de los archivos dados
     * @pre El string path esta inicializado y pertenece a un path correcto
     * @post Se han cargado los datos
     */
    public static void cargarDatos (String path) throws Exception {
        path += '/';
        cd.path = path;
        CtrlDominioPreprocesarDatos.preprocesarTypes(path + "types.csv");
        CtrlDominioPreprocesarDatos.preprocesarItems(path + "items.csv");
        CtrlDominioPreprocesarDatos.preprocesarRatings(path + "ratings.db.csv");
        CtrlDominioPreprocesarDatos.preprocesarRatingsKnown(path + "ratings.test.known.csv");

        String[] auxPath = path.split("/");
        pathNeeded = auxPath[auxPath.length - 2] + "/" + auxPath[auxPath.length - 1];
    }

    /** @fn enterNewUser
     * @brief entra un usuario nuevo a la aplicacion de unos ficheros selecionados
     * @pre existe un objeto de la clase CtrlPresentacion
     * @post se ha entrado el nuevo usuario y cargado sus datos, o lanzado un error
     * @param userID usuario que interactuara con la aplicación
     * @param eval si el usuario quiere una evaluacion de las predicciones que haga el sistema
     */
    public static void enterNewUser (int userID, boolean eval) throws Exception {
        wantEval = eval;
        u = usuariosRatingsKnown.getUser(Parser.translateUser(userID));
        if (wantEval) CtrlDominioPreprocesarDatos.preprocesarRatingsUknown(path + "ratings.test.unknown.csv");

        r = cargarHistorial();
    }

    /** @fn getValoracionesDeUsuario()
     * @brief coger el mapa de valoraciones del usuario
     * @pre existe un objeto de la clase, con el usuario inicializado correctamente
     * @post se ha devuelto las valoraciones que pertenecen a este usuario
     * @return se devuelven las valoraciones que pertenecen a este usuario
     */
    public static Map<Integer, Double> getValoracionesDeUsuario() {
        return u.getValoraciones();
    }

    /** @fn createNewValoracion()
     * @brief añadir una valoración para el usuario activo en la sesión activa
     * @pre existe un objeto de la clase, con el usuario inicializado correctamente
     * @post se ha añadido la valoracion al mapa de valoraicones del usuario
     * @param itemID: id del item que el usuario desea valorar
     * @param valoracion: valoración asociada a este item
     */
    public static void createNewValoracion (int itemID, Double valoracion) {
        u.addValoracion(itemID, valoracion);
        usuariosRatingsKnown.setUser(u.getUserId(), u);
    }

    /** @fn quitarValoracion()
     * @brief quitar una valoración del conjunto de valoraciones del usuario
     * @pre existe un objeto de la clase, con el usuario inicializado correctamente
     * @post se ha quitado la valoración del mapa de valoraciones del usuario
     * @param itemID: id del item del cual queremos quitar la valoración
     */
    public static void quitarValoracion (int itemID) {
        u.borrarValoracion(itemID);
        usuariosRatingsKnown.setUser(u.getUserId(), u);
    }

    /**
     * @fn addNewItem
     * @brief Añade un nuevo item
     * @pre los paramtros op y atributos estan inicializados.
     * @post Se ha añadido el item.
     * @param og: id original del item.
     * @param attributes: atributos del item que se quiere.
     */
    public void addNewItem(String og, Map<String, Atributo> attributes) {
        int id = Parser.getAllItems().findMaxId() + 1;
        Item it = new Item(id, attributes);
        it.setOriginalId(Integer.parseInt(og));
        Parser.updateContents(it);
    }

    /** @fn getAllItemAtrbutes()
     * @brief coger atributos de todos los items
     * @pre existe un objeto de la clase
     * @post
     * @return vector con los atributos de los items de las cabeceras que queremos
     */
    public static Vector getAllItemAtrbutes() {
        Map <String, String> m = new HashMap<>();
        m.put("id", "");
        m.put ("title", "");
        m.put ("genre", "");
        m.put ("popularity", "");
        String[] headers = Parser.getHeaders();
        for (String h : headers){
            for (Map.Entry<String, String> e : m.entrySet()) {
                if (e.getKey().equals(e.getValue())) continue;
                if (h.contains(e.getKey())) m.put(e.getKey(), h);
            }
        }

        Vector v = new Vector();
        for (Item i : Parser.getAllItems().getItems().values()){
            Map <String, Atributo> atributos = i.getAtributos();
            Vector vAux = new Vector();
            for (String e : m.values()){
                if (!e.equals("")) {
                    vAux.add(atributos.get(e).getValor());
                }
                else {vAux.add("");}
            }
            v.add(vAux);
        }
        return v;
    }

    /** @fn getPredicciones()
     * @brief coger predicciones de los algoritmos de recomendación
     * @pre existe un objeto de la clase, con el usuario inicializado correctamente
     * @post se ha ejecutado la recomendación que quiere el usuario y se ha devuelto la predicción
     * @return las recomendaciones para el usuario
     * @param type: tipo de algoritmo de recomendación que queremos ejecutar
     */
    public static Vector getPredicciones(String type, String name, int size) throws Exception {
        Recomendacion rx;
        if (!wantEval) rx = new Recomendacion(usuariosRatings, u);
        else {
            Map <Integer, Double> v = usuariosRatingsUnknown.getUser(u.getUserId()).getValoraciones();
            Items its = new Items();
            for (Integer e : v.keySet()) {
                its.addItem(Parser.getAllItems().getItem(e));
                System.out.println (e);
            }
            rx = new Recomendacion(usuariosRatings, u, its);
            System.out.println ("here recoms");
        }
        rx.setDate(Date.from(Instant.now()).toString());

        rx.setType(type);
        rx.setName(name);
        rx.setSize(size);
        
        if (type.equals("cb")) {
            rx.contentBasedFiltering();
        }
        if (type.equals("cf")) {
            rx.collaborativeFiltering();
        }
        if (type.equals("hybrid")) {
            rx.hybrid();
        }
        r.addRecomendacion(rx);

        Map <Integer, Double> aux = rx.getRecomendaciones();
        Vector v = new Vector();
        for (Map.Entry<Integer, Double> e : aux.entrySet()){
            Vector vaux = new Vector();
            vaux.add(Parser.getAllItems().getItem(e.getKey()).getOriginalId());
            vaux.add(e.getValue());
            v.add(vaux);
        }
        return v;
    }

    /** @fn deleteItem()
     * @brief Borra un item del conjunto de items.
     * @pre Tiene que existir un item con id original igual al parametro id
     * @post Se ha eliminado el item del conjunto de items
     * @param id: id original del item que se desea eliminar
     */
    public static void deleteItem(int id) {
        Parser.getAllItems().removeItem(Parser.translateItem(id));
    }

    /** @fn getRecomsVector()
     * @brief Obtiene el vector de recomendaciones.
     * @pre La recomendacion raux tiene que existir.
     * @post Se ha obtenido el vector de recomendaciones con nombre raux.
     * @param raux: nombre de la recomendacion.
     */
    public Vector getRecomsVector(String raux){
        Vector data = new Vector();
        r.getRecomendacion(raux).getRecomendaciones().forEach((k, v) ->{
            Vector row = new Vector();
            row.add(k);
            row.add(v);
            data.add(row);
        });
        return data;
    }

    /** @fn getuId()
     * @brief getter del id original del usuario.
     * @pre -
     * @post Se ha obtenido el id original del usuario
     */
    public Integer getuId() {return u.getOriginalUserId();}

    /** @fn getHeaders()
     * @brief getter de los headers.
     * @pre -
     * @post Se han obtenido los headers de los atributos de los items.
     */
    public String[] getHeaders() {
        return Parser.getHeaders();
    }

    /** @fn getTypes()
     * @brief getter de los tipos de los atributos de los items.
     * @pre -
     * @post Se han obtenido los tipos de los atributos de los items.
     */
    public String[] getTypes() {
        return Parser.getTypes();
    }

    /** @fn getItems()
     * @brief getter de los items.
     * @pre -
     * @post Se han obtenido los items.
     */
    public Items getItems(){
        return Parser.getAllItems();
    }

    /** @fn añadirUsuario()
     * @brief Añade un usuario.
     * @pre el path es correcto y el usuario con id userID no existe.
     * @post Se ha añadido un nuevo usuario con id userID
     * @param path: path donde añadir el usuario.
     * @param userID: id del usuario que crearemos.
     */
  public void añadirUsuario (String path, int userID) throws Exception {
      wantEval = false;
      cargarDatos(path);
      u = new Usuario(userID, new HashMap<>());
      u.setOriginalUserId(userID);
      System.out.println ("here");
      usuariosRatingsKnown.addUser(u);
      r = cargarHistorial();
    }

    /** @fn containsItemId()
     * @brief Traduce el id original al asignado por el programa.
     * @pre El item con el id pasado por parametro existe.
     * @post Se ha devuelto el id asignado por el programa para ese item.
     * @param id: id original del item.
     */
    public Integer containsItemId(Integer id) {
        return Parser.translateItem(id);
    }

    /** @fn modifyItem()
     * @brief Modifica un item.
     * @pre Existe un item con el id pasado por parametro y newAttributos esta inicializado correctamente.
     * @post Se ha modificado el item con el id pasado.
     * @param id: id del item.
     * @param newAttributes: atributos con los que se creara el item nuevo.
     */
    public void modifyItem(String id, Map<String, Atributo> newAttributes) {
        int trlId = Parser.translateItem(Integer.parseInt(id));
        Map<String, Atributo> oldAttributes = new HashMap<>(Parser.getAllItems().getItem(trlId).getAtributos());

        newAttributes.forEach((k, v) -> {
            oldAttributes.put(k, v);
        });

        Parser.getAllItems().setItem(trlId, new Item(trlId, oldAttributes));
    }

    /** @fn eliminarRecomItem()
     * @brief elimina items de recomendaciones.
     * @pre Existe el item con id itemId en la recomendacion llamada recom.
     * @post Se ha eliminado el item de la recomendacion.
     * @param recom: nombre de la recomendacion.
     * @param itemId: id del item.
     */
    public void eliminarRecomItem(String recom, Integer itemId) throws Exception {
        Recomendacion rx = r.getRecomendacion (recom);
        rx.removeValoracion(itemId);
        r.setRecomendacion (rx);
        //String data = r.toString();
        //CtrlPersistencia.guardarFichero(data.substring(0, data.length()-2), "DATA/" + u.getOriginalUserId() + "/" + r.getName() + ".txt");
    }

    /** @fn getHistorialRecomendaciones()
     * @brief Obtiene el historial de las recomendaciones.
     * @pre la variable r esta inicializada.
     * @post Se ha devuelto el historial de recomendaciones.
     * @return Retorna un ArrayList de strings que contiene el historial de recomendaciones
     */
    public ArrayList<String> getHistorialRecomendaciones() {
        return r.getHistorial();
    }

    /** @fn getItemsRecomendacion()
     * @brief Obtiene las recomendaciones .
     * @pre la variable r esta inicializada.
     * @post Se ha devuelto el historial de recomendaciones.
     * @return Retorna un ArrayList de strings que contiene el historial de recomendaciones
     */
    public ArrayList<Integer> getItemsRecomendacion (String recom) {
        return r.getRecomendacion(recom).getRecomItems();
    }

    /** @fn guardarSession()
     * @brief Guarda los archivos procesados.
     * @pre Existen sesiones.
     * @post Se han guardados los archivos procesados.
     */
    public void guardarSession() throws Exception {
        CtrlPersistencia.borrarCarpeta("DATA/procesado/" + pathNeeded + "/" + u.getOriginalUserId());
        String path = "DATA/procesado/" + pathNeeded + "/" + u.getOriginalUserId() + "/";
        CtrlPersistencia.comprobarCarpeta(path);
        //DATA/procesado/data.peliculas/250/2378

        //guardar usuarios
        CtrlPersistencia.guardarFichero(usuariosRatings.toString(), path +  "ratings.txt");
        CtrlPersistencia.guardarFichero(usuariosRatingsKnown.toString(), path +  "ratingKnown.txt");
        if (wantEval)
            CtrlPersistencia.guardarFichero(usuariosRatingsUnknown.toString(), path +  "ratingUnknown.txt");
        //guardar items
        CtrlPersistencia.guardarFichero(Parser.getAllItems().toString(), path + "items.txt");
        //guardar recomendaciones
        CtrlPersistencia.guardarFichero(r.toString(), path + "historial.txt");

        for (Recomendacion raux : r.getRecomendaciones()){
            CtrlPersistencia.guardarFichero(raux.toString(), path + raux.getName() + ".txt");
        }
    }

    /** @fn cargarDatosProcesados()
     * @brief Carga los datos preprocesados.
     * @pre El path es correcto.
     * @post Se han cargado los datos preprocesados.
     * @param path: path de los datos procesados.
     * @param eval: indica si se quiere evaluar.
     */
    public static void cargarDatosProcesados (String path, boolean eval) throws Exception {
        wantEval = eval;

        String[] auxP = path.split("/");
        int userID = Integer.parseInt(auxP[auxP.length-1]);
        pathNeeded = auxP[auxP.length-3] + "/" + auxP[auxP.length-2];

        if (eval) {
            path += '/';
            CtrlDominioCargarDatos.cargarArchivos(path);
            auxP = path.split("/");
            String newP = "";
            for (int i = 0; i < auxP.length - 1; i++) {
                if (i == auxP.length - 4) newP += "csv/";
                else newP += (auxP[i] + "/");
            }
            CtrlDominioPreprocesarDatos.preprocesarRatingsUknown(newP + "/ratings.test.unknown.csv");
        }

        u = usuariosRatingsKnown.getUser(Parser.translateUser(userID));

        r = cargarHistorial();
    }

    /** @fn sendError()
     * @brief envia un error.
     * @pre la variable err esta inicializada.
     * @post Se ha enviado el error err.
     * @param err: variable que contiene el error.
     */
    public static void sendError (String err) {
        CtrlPresentacion.sendError(err);
    }

    /** @fn evaluarRecomendaciones()
     * @brief Evalua la calidad de las recomendaciones.
     * @pre name esta inicializado.
     * @post Se ha devuelto un double con la calidad de la solucion.
     * @return Devuelve un double con la calidad de la solucion.
     * @param name: nombre de la recomendacion.
     */
    public static double evaluarRecomendaciones(String name) {
        if (!wantEval) return -1.0;
        Recomendacion raux = r.getRecomendacion(name);

        Map <Integer, Double> lt = new HashMap<>();
        Map <Integer, Double> v = usuariosRatingsUnknown.getUser(u.getUserId()).getValoraciones();
        System.out.println (v);
        System.out.println (raux.getRecomendaciones());
        for (Map.Entry <Integer, Double> e : raux.getRecomendaciones().entrySet()) {
            if (v.containsKey(e.getKey())) lt.put (e.getKey(), v.get(e.getKey()));
            else lt.put (e.getKey(), -1.0);
        }
        return raux.evaluarRecomendacion(lt);
    }

    /** borrarRecomendacionHistorial
     * @brief borrar una recomendacion del historial
     * @pre existe objeto
     * @post se ha borrado si existia la recomendacion con nombre name
     * @param name nombre de la recomendacion
     */
    public static void borrarRecomendacionHistorial (String name) {
        r.removeRecomendacino(name);
    }
}


