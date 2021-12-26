/** @file Recomendacion.java
 @brief Código de la clase Recomendacion
 */

package clases;

import controladores.KMeans;
import controladores.NearestNeighbors;
import controladores.SlopeOne;

import java.util.*;

/** @class Recomendacion
 * @brief La clase de recomendacion controla las llamadas a los algoritmos
 *
 * @author Carla Campàs Gené
 *
 * @date December 2021
 */
public class Recomendacion {
    /** @var Map <Integer, Double> recomItems
     * @brief array list de items recomendados en orden
     */
    private Map <Integer, Double> recomItems;

    /** @var String type
     * @brief tipo de algoritmo que se ha usado para la prediccion de las recomendaciones
     */
    private String type;

    /** @var String nombre
     * @brief identificador que le damos a la recomendacion
     */
    private String nombre;

    /** @var String fecha
     * @brief fecha en la que se produce la recomendacion
     */
    private String fecha;

    /** @var Items it
     * @brief Items que se usaran para hacer recomendaciones al usuario
     */
    private Items it;

    /** @var Usuarios us
     * @brief Usuarios que se usaran para clusterizar en el caso del collaborative filtering
     */
    private Usuarios us;

    /** @var Usuario u
     * @brief Usuario que pide las recomendaciones al sistema
     */
    private Usuario u;

    int size;

    /** @fn Recomendacion
     * @brief Constructora por defecto, objeto vacío.
     * Constructora para inicializar un objeto de la clase.
     * @pre No hay objeto de la clase recomendacion
     * @post Existe el objeto vacío de la clase recomendacion
     */
    public Recomendacion(){}

    /** @fn Recomendacion
     * @brief Constructora por defecto, inicializa la clase con el conjunto de usuarios, el conjunto de items y el usuario.
     * @pre No hay objeto de la clase recomendacion
     * @post Existe el objeto de la clase recomendacion con variables globales u e it inicializadas
     * Solo podremos usar esto en el caso de llamar a los algoritmos de content based filtering.
     * @param u:  Usuario al que le queremos recomendar items
     */
    public Recomendacion(Usuario u) {
        this.u = u;
        this.us = new Usuarios();
        procesarItems();
    }

    /** @fn Recomendacion
     * @brief Constructora por defecto, inicializa la clase con el conjunto de usuarios, el conjunto de items y el usuario.
     * @pre No hay objeto de la clase recomendacion
     * @post Existe el objeto de la clase recomendacion con variables globales us, u e it inicializadas
     * @param us: Conjunto de usuarios que usaremos para los algoritmos de recomendación
     * @param u:  Usuario al que le queremos recomendar items
     */
    public Recomendacion(Usuarios us, Usuario u) {
        this.us = us;
        this.u = u;
        procesarItems();
    }

    /** @fn Recomendacion
     * @brief Constructora por defecto, inicializa la clase con el conjunto de usuarios, el conjunto de items y el usuario.
     * @pre No hay objeto de la clase recomendacion
     * @post Existe el objeto de la clase recomendacion con variables globales us, u e it inicializadas
     * @param us: Conjunto de usuarios que usaremos para los algoritmos de recomendación
     * @param u:  Usuario al que le queremos recomendar items
     * @param it: items a recomendar
     */
    public Recomendacion(Usuarios us, Usuario u, Items it) {
        System.out.println ("here");
        this.us = us;
        this.u = u;
        this.it = it;
        size = it.getItems().size();
        for (Item i : this.it.getItems().values()){
            System.out.println (i.getId());
        }
    }

    /** @fn Recomendacion
     * @brief Constructora para inicializar un objeto de la clase.
     * @pre No hay objeto de la clase recomendacion
     * @post Existe el objeto vacío de la clase recomendacion
     * @param u Usuario al que le recomendamos
     * @param us Usuarios totales
     * @param recoms Recomendaciones del usuario
     */
    public Recomendacion(Usuario u, Usuarios us, Map<Integer, Double> recoms) {
        this.u = u;
        this.us = us;
        this.recomItems = recoms;
        procesarItems();
    }

    public ArrayList <Integer> getRecomItems () { return new ArrayList<>(this.recomItems.keySet()); }

    public Map <Integer, Double> getRecomendaciones() { return this.recomItems; }

    public void setDate (String fecha) { this.fecha = fecha; }

    public String getDate () { return this.fecha; }

    public void setName (String nombre) { this.nombre = nombre; }

    public String getName () { return this.nombre; }

    public void setType(String type) { this.type = type; }

    public String getType () { return this.type; }

    public void setSize(int size) { this.size = size; }

    public int getSize () { return this.size; }

    public void removeValoracion (int itemID) {
        this.recomItems.remove(itemID);
    }

    /** @fn procesarItems
     * @brief Procesado de los items para que esten acorde con lo que necesitamso en las predicciones
     * Los items que el usuario ya ha valorado no queremos que sean predecidos, los eliminamos del conjunto general de items.
     * Los items restantes los vamos a querer valorar.
     * @pre El usuario ya ha sido inicializado
     * @post El conjunto de items que van a ser valorados estan en la variable global it.
     */
    public void procesarItems () {
        Items aux = new Items (Parser.getAllItems().getItems());
        for (Integer i : u.getValoraciones().keySet()){
            aux.removeItem(i);
        }
        it = aux;
    }

    /** @fn collaborativeFiltering
     * @brief Llamada a creación de recomendaciones usando los métodos de collaborative filtering (K-Means + Slope One).
     * @pre La clase de Recomendacion ha sido inicializada
     * @post Se ha llamado a las subclases K-Means y SlopeOne para crear las recomendaciones para un usuario u.
     */
    public void collaborativeFiltering () {
        KMeans kMeans = new KMeans(us, u, it);
        this.us = kMeans.getUsersCluster();

        SlopeOne so = new SlopeOne(us, u, it);
        so.calcularRecomendaciones(size);
        so.printPred();
        recomItems = so.getPredicciones(size);
    }

    /** @fn contentBasedFiltering
     * @brief Llamada a creación de recomendaciones usando los metodos de content based filtering.
     * @pre La clase de Recomendacion ha sido inicializada
     * @post Se han ejecutado los algoritmos de content based filtering
     */
    public void contentBasedFiltering () {
        NearestNeighbors nn = new NearestNeighbors(us, u, it);
        nn.calcularRecomendaciones(size);
        recomItems = nn.getPredicciones(size);
    }

    /** @fn hybrid
     * @brief Llamada a la creación de recomendaciones utilizando un metodo hibrido (content based y collaborative)
     * @pre La clase de Recomendacion ha sido inicializada
     * @post Se han ejecutado los algoritmos de content based y collaborative filtering para sacar unas recomendaciones hibridas
     */
    public void hybrid () {
        KMeans kMeans = new KMeans(us, u, it);
        this.us = kMeans.getUsersCluster();

        SlopeOne so = new SlopeOne(us, u, it);
        so.calcularRecomendaciones(size);
        Map <Integer, Double> aux = so.getPredicciones(size);

        NearestNeighbors nn = new NearestNeighbors();
        Map <Integer, Double> auxNN = nn.getPredicciones(size);

        //media
        for (Map.Entry<Integer, Double> e : auxNN.entrySet()){
            Double predCF = aux.get(e.getKey());
            if (predCF == -1.0) {
                recomItems.put(e.getKey(), e.getValue());
                continue;
            }
            recomItems.put(e.getKey(), (e.getValue() + predCF)/2);
        }
    }

    /**
     * @fn evaluarIDCG
     * @brief Computa el ideal discounted cumulative value para una recomendación determinada
     * @param lt Map<Integer, Integer> en formato Map<itemID, posiciónI> donde posiciónI es la posición que ocupa en la permutación ideal, cada elemento en lr
     * @return devuelve el ideal discounted cumulative gain
     */
    public double evaluarIDCG(List<Map.Entry<Integer, Double>> lt) {
        int pos = 1;
        double idcg = 0.0;
        for (Map.Entry<Integer, Double> k : lt){
            Double val = k.getValue();
            double num = (Math.pow(2,val))-1;
            double denom = Math.log(pos + 1.0) / Math.log(2.0);
            idcg += (num / denom);
            pos++;
        }
        return idcg;
    }

    public ArrayList<ArrayList<Integer>> generateArrayRecom () {
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        ArrayList<Integer> aux = new ArrayList<>();
        double last = -1.0;
        for (Map.Entry<Integer, Double> e : recomItems.entrySet()) {
            if (e.getValue() == last) {
                aux.add(e.getKey());
            } else {
                if (aux.size() > 0) {
                    ret.add(aux);
                    aux = new ArrayList<>();
                }
                aux.add(e.getKey());
                last = e.getValue();
            }
        }
        return ret;
    }

    /**
     * @fn evaluarRecomendacion
     * @brief Computa el discounted cumulative value para una recomendación determinada
     * @param lt Map<Integer, Integer> en formato Map<itemID, posiciónI> donde posiciónI es la posición que ocupa en la permutación ideal, cada elemento en lr
     * @return devuelve el Discounted Cumulative Gain para la recomendación hecha al usuario u.
     */
    public double evaluarRecomendacion(Map<Integer, Double> lt) {
        ArrayList<ArrayList<Integer>> lr = generateArrayRecom ();
        System.out.println (lr);
        System.out.println (lt);
        double dcg = 0.0;
        int pos = 1;
        for (ArrayList<Integer> items : lr) {
            for (int idItem : items) {
                double reli = 0.0;
                if (lt.containsKey(idItem)) {
                    reli = lt.get(idItem);
                }
                dcg += ((Math.pow(2, reli) - 1) / (Math.log(pos + 1)/Math.log(2)));
            }
            pos++;
        }
        return dcg;
    }

    /** @fn toString
     * @brief Hace override de la funcion toString
     * @pre -
     * @post La recomendacion se ha traducido a String para poder imprimirse
     * @return Devuelve la recomendacion traducido a String
     */
    @Override
    public String toString() {
        String valor = "";
        for (Map.Entry<Integer, Double> recom : recomItems.entrySet())
            valor += recom.getKey() + "," + recom.getValue() + "\n";
        return valor;
    }
}
