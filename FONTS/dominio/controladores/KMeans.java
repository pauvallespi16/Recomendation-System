package controladores; /** @file KMeans.java
 @brief Código de la clase KMeans
 */

import clases.*;

import java.util.*;

/** @class  KMeans
 * @brief Implementación del algoritmo KMeans, usando collaborative filtering. Extiende la clase Algotimos.
 *
 * El algoritmo KMeans recibe un conjunto de usuarios, y los clusteriza, en función de como de similares sean estos usuarios.
 * Consideramos similares dos usuarios que han valorado de manera parecida una serie de items. Nos ayudamos de k centroides que hacen
 * las funciones de un "usuario imaginario" que ha valorado todos los items. Por lo que compara cada usuario a los centroides, y los agrupa
 * dependiendo de cuál sea el más cercano.
 *
 * @author Beatriz Gomes da Costa
 *
 * @date December 2021
 */
public class KMeans extends Algoritmos {

    /**
     * @var double maxsc
     * @brief Valor máximo permitido para cada valoración
     */
    static double maxsc = Parser.getMaxScore();

    /**
     * @var double minsc
     * @brief Valor mínimo permitido para cada valoración
     */
    static double minsc =  Parser.getMinScore();

    /**
     * @val int k
     * @brief Número de clusters que se forman para el algoritmo
     */
    private int k = 10;

    /**
     * @val int cota
     * @brief Máximo número de iteraciones permitidas
     */
    private int cota = 30;

    /**
     * @var Random random
     * @brief Objeto random para la creación de valoraciones aleatorias de items para cada centroides
     */
    private final Random random = new Random();

    /**
     * @var Map<Integer, ArrayList<Usuario>> clusterMap
     * @brief Map donde almacenamos cómo quedan los k clusters de usuarios formados
     * @format Map<CentroID, ArrayList<Usuario>>
     */
    private Map<Integer, ArrayList<Usuario>> clusterMap = new HashMap<>(k);

    /**
     * @var ArrayList<Centroide> centroides
     * @brief ArrayList donde almacenamos los k centroides creados en el algoritmo
     * @format ArrayList<Centroide>
     */
    private ArrayList<Centroide> centroides = new ArrayList<>();

    /**
     * @fn KMeans
     * @brief Constructora por defecto de la clase KMeans
     * @pre no hay objeto KMeans
     * @post se ha creado un objeto KMeans
     */
    public KMeans() {}

    public KMeans(Usuarios us, Usuario u, Items it) {
        super (us, u, it);
    }

    /**
     * @fn getMaxSc
     * @brief getter de la variable maxsc
     * @pre
     * @post se ha devuelto el valor de maxsc
     * @return valor máximo permitido para una valoración
     */
    public double getMaxSc(){ return maxsc; }

    /**
     * @fn getMinSc
     * @brief getter de la variable minsc
     * @pre
     * @post se ha devuelto el valor de minsc
     * @return valor mínimo permitido para una valoración.
     */
    public double getMinSc(){ return minsc; }

    /**
     * @fn getCentroides
     * @brief getter de la ArrayList de los k centroides creados
     * @pre
     * @post se ha devuelto el Arraylist de centroides
     * @return ArrayList con los k centroides creados.
     */
    public ArrayList<Centroide> getCentroides() { return centroides; }

    /**
     * @fn getK
     * @brief getter del valor k usado para el algoritmo
     * @pre
     * @post se ha devuelto el valor de la k (número de clusters)
     * @return valor de k, número de centroides.
     */
    public int getK(){ return k; }

    /**
     * @fn getCota
     * @brief getter de la cota usada para el algoritmo
     * @pre
     * @post se ha devuelto el valor de la cota (número máximo de iteraciones)
     * @return valor de cota, número máximo de iteraciones.
     */
    public int getCota(){ return cota; }

    /**
     * @fn getClusterMap
     * @brief getter del map clusterMap donde se almacenan los clusters
     * @pre
     * @post se ha devuelto el mapa con con los k clusters y sus respectivos centroides identificados por un identificador único
     * @return estructura clusterMap donde guardamos los k clusters, mapeados por su respectivo centroide
     */
    public Map<Integer, ArrayList<Usuario>> getClusterMap(){ return clusterMap; }

    /**
     * @fn getUsersCluster
     * @brief Encuentra a qué cluster pertenece un nuevo usuario u, para el cual estamos tratando de encontrar los usuarios más parecidos
     * @pre el usuario u no pertenece a ninguno de los clusters almacenados en clusterMap
     * @post se ha encontrado cuál es el centroide más cercano al usuario u y por lo tanto a qué cluster pertenece.
     * @return se devuelve el cluster al cual pertenece u (por encontrarse más cerca de su centroide)
     */
    public Usuarios getUsersCluster () {
        clusterFormation(this.us);
        int cluster = nearestCentroid(super.u.getValoraciones());
        return new Usuarios(clusterMap.get(cluster));
    }

    public void setMaxsc(double maxsc){ this.maxsc = maxsc; }

    public void setMinsc(double minsc) { this.minsc = minsc; }
    /**
     * @fn setCentroides
     * @brief setter del atributo centroides
     * @pre el atributo centroides no está incializado o contiene una Array de Centroides
     * @post se ha asignado al atributo centroides la ArrayList pasada por parámetro
     * @param centroides ArrayList de Centroides. Su identificador indica también su posición en la Array
     */
    public void setCentroides(ArrayList<Centroide> centroides) { this.centroides = centroides; }

    /**
     * @fn setClusterMap
     * @brief setter del atributo clusterMap
     * @pre el atributo clusterMap es un mapa vacío de tamaño k o bien contiene un mapa de clusters mapeados según el identificador del centroide al cual pertenece cada cluster
     * @param clusterMap mapa donde la llave es el identificador del centroide y el ArrayList de usuarios mapeados es el conjunto de usuarios que forman un cluster alrededor de dicho centtroide
     */
    public void setClusterMap(Map<Integer, ArrayList<Usuario>> clusterMap) { this.clusterMap = clusterMap; }

    /**
     * @fn setCota
     * @brief setter del atributo cota que determina el número total de iteraciones
     * @pre el atributo cota tiene o bien su valor inicial o uno que se le haya asignado previamente, pero nunca está sin inicializar
     * @post se le ha asignado al atributo cota el valor pasado por parámetro
     * @param cota nuevo valor que pasa a tener el atributo cota. Determinará el número máximo de iteraciones permitidas
     */
    public void setCota(int cota) { this.cota = cota; }

    /**
     * @fn setK
     * @brief setter del atributo k, que determina el número de clusters
     * @pre el atributo k tiene o bien su valor inicial o uno que se le haya asignado previamente, pero nunca está sin inicializar
     * @param k nuevo valor que pasa a tener el atributo k. Determinará el número de clusters que se formen
     */
    public void setK(int k) { this.k = k; }

    /**
     * @fn setDefaults
     * @brief devuelve a su estado inicial todos los atributos de la clase
     * @pre -
     * @post los atributos k, centroides, cota y clusterMap pasan a tener sus valores iniciales.
     */
    public void setDefaults(){
        this.k = 10;
        this.centroides = new ArrayList<>();
        this.cota = 30;
        this.clusterMap = new HashMap<>(k);
    }

    /**
     * @fn clusterFormation
     * @brief Función principal del algoritmo kMeans. Después de como máximo cota iteraciones, ha separado los usuarios de entrada en k clusters
     * @pre Los identificadores de cada usuario en valoraciones, así como los identificadores d elos items que han sido valorados por éstos están normalizados.
     * @post Se han formado k clusters formados por subconjuntos disjuntos de valoraciones.
     *
     * @see //clusterFormationTest()
     */
    public void clusterFormation(Usuarios usrs) {
        //setOptimalK();

        double t1 = System.currentTimeMillis();
        Map<Integer, ArrayList<Usuario>> lastState = new HashMap<>();           //Estructura para guardar el ultimo estado de los clusters
        int actualCota = 0;
        boolean shouldTerminate = false;

        randomCentroids();                                                     //Creamos k centroides arbitrarios
        furthestCentroids();
        // iterate for a pre-defined number of times
        for (int i = 0; i < cota && !shouldTerminate; i++) {
            boolean isLastIteration = i == cota - 1;

            // in each iteration we should find the nearest centroid for each user
            for (Usuario f : usrs.getUsers()) {
                int p = nearestCentroid(f.getValoraciones());
                addToCluster(f, p);
            }

            // if the assignments do not change, then the algorithm terminates
            shouldTerminate = isLastIteration || shouldTerminate(lastState);

            if (!shouldTerminate) {
                // at the end of each iteration we relocate the centroids
                lastState.clear();
                for (Integer c : clusterMap.keySet()) {
                    ArrayList<Usuario> aux = clusterMap.get(c);
                    centroidRelocation(c, centroides.get(c).getImaginaryUsers(), aux);
                    lastState.put(centroides.get(c).getCentroID(), clusterMap.get(c));
                }

                clusterMap = new HashMap<>();
            }

            else actualCota = i+1;
        }
        clusterMap = new HashMap<>(lastState);

        //This part is for applying the elbow method and determining the best number of clusters

        t1 = System.currentTimeMillis() - t1;
        //double wss = computeAverageWSS();
    }

    /**
     * @fn computeEuclideanDistance
     * @brief Calcula la distancia euclídea entre un centroide y un usuario, a partir del conjunto de valoraciones que cada uno de estos ha dado a los distintos items.
     * @pre Los items valorados por el usuario son un subconjunto del total de items.
     * @post Se ha computado la distancia entre el centroide y el usuario al cual pertenezcan dichas valoraciones.
     *
     * @param vals contiene las valoraciones que ha dado un usuario a una serie de items. La llave contiene el identificador del item, y el valor es la valoración dada.
     * @param centroid contiene las valoraciones arbitrarias de un centroide a cada objeto del conjunto de items. La llave contiene el identificador del item y el valor es una valoración aleatoria entre maxsc y minsc.
     * @return Distancia euclidea entre el usuario representado por vals y el centroide
     * //@see computeEuclideanDistanceTest()
     *
     */
    public double computeEuclideanDistance(Map<Integer, Double> vals, Map<Integer, Double> valsP, Integer centroid) {
        double sum = 0.0;
        if (valsP == null){
            for (Integer key : centroides.get(centroid).getImaginaryUsers().keySet()){
                double c = centroides.get(centroid).getImaginaryUsers().get(key);
                if (vals.containsKey(key)){
                    double v = vals.get(key);
                    sum += Math.pow((v - c), 2);
                }
            }
        }

        else {
            for (Integer key : centroides.get(centroid).getImaginaryUsers().keySet()){
                if (vals.containsKey(key) && valsP.containsKey(key)) {
                    double v1 = vals.get(key);
                    double v2 = valsP.get(key);
                    sum += Math.pow((v1 - v2), 2);
                }
                else if (vals.containsKey(key)) sum += Math.pow(vals.get(key), 2);
                else if (valsP.containsKey(key)) sum += Math.pow(valsP.get(key), 2);
            }
        }
        return Math.sqrt(sum);
    }


    /**
     * @pfn randomCentroids
     * @brief Forma 2k centroides para el algoritmo. Un centroide es el resultado de asignar, para cada uno de los items disponibles, una valoración aleatoria entre minsc y maxsc
     * @pre k es un entero positivo y tenemos al menos un item que valorar
     * @post centroides contiene k objetos de tipo Centroide, cada uno con sus respectivas valoraciones arbitrarias para todos los items.
     */
    private void randomCentroids() {
        for (int i = 0; i < 2*k; ++i) {
            Map<Integer, Double> coords = new HashMap<>();

            for (int ids = 0; ids < it.getSize(); ++ids) {
                coords.put(ids, random.nextDouble() * (maxsc - minsc) + minsc);
            }
            centroides.add(i, new Centroide(i, coords));
        }
    }

    /**
     * @fn furthestCentroids
     * @brief elimina los clusters más parecidos entre ellos, dejando sólo los k más alejados
     * @pre centroides contiene 2k centroides
     * @post los k centroides más cercanos entre sí han sido eliminados
     */
    private void furthestCentroids(){
        ArrayList<Centroide> copy = new ArrayList<>(centroides);
        int id = 0;

        for (Centroide c : copy){
            if (centroides.size() > k && centroides.contains(c)){
                double min = Double.MAX_VALUE;
                Centroide p = centroides.get(0);
                for (Centroide c1 : centroides){
                    if (c1.getCentroID() != c.getCentroID()){
                        double dist = computeEuclideanDistance(c.getImaginaryUsers(), null, centroides.indexOf(c1));
                        if (dist < min){
                            min = dist;
                            p = c1;
                        }
                    }
                }

                centroides.remove(p);
            }
        }
        for (Centroide c : centroides){
            c.setCentroID(id);
            id++;
        }
    }

    /**
     * @fn nearestCentroid
     * @brief Determina cuál es el centroide más cercano a un usuario, calculando su distancia euclidea a cada centroide en centroides
     * @pre
     * @post se ha devuelto en pos la posición en la ArrayList centroides ocupada por el centroide más cercano a u.
     * @param u Usuario cuyo centroide a distancia mínima queremos conocer.
     * @return pos contiene la posición del centroide que se encuentra más cercano al usuario u.
     */
    private int nearestCentroid(Map<Integer, Double> u) {
        double minDist = Double.MAX_VALUE;
        int pos = 0;
        for (Centroide i : centroides){
            double currentDistance = computeEuclideanDistance(u, null, centroides.indexOf(i));
            if (currentDistance < minDist) {
                minDist = currentDistance;
                pos = i.getCentroID();
            }
        }
        return pos;
    }

    /**
     * @fn addToCluster
     * @brief Añade el usuario f al cluster perteneciente al centroide c
     * @pre f no pertenece a ningún cluster
     * @post el usuario f forma parte ahora del cluster c
     * @param f usuario que deseamos añadir a un cluster
     * @param c Centroide que del cluster al que queremos añadir al usuario f.
     */
    private void addToCluster( Usuario f, Integer c) {
        clusterMap.compute(c, (k, al) -> {
            if (al == null) {
                al = new ArrayList<>();
            }
            al.add(f);
            return al;
        });
    }

    /**
     * @fn centroidRelocation
     * @brief Recalcula las valoraciones de cada item para el centroide c, de manera que éste quede ahora en el centro de su cluster
     * @pre cluster es el conjunto de usuarios del Centroide c, y puede ser un conjunto vacío
     * @post si el conjunto era vacía (centroide sin usuarios) nada ha cambiado, y si no lo era, las valoraciones del centroide
     *      han sido modificadas para posicionarlo en el centro del cluster de usuarios.
     * @param id identificador del centroide al cual pertenece el cluster c
     * @param c valoraciones del centroide a todos los items
     * @param cluster conjunto de usuarios del Centroide c.
     */
    private void centroidRelocation(Integer id, Map<Integer, Double> c, ArrayList<Usuario> cluster) {
        if (!cluster.isEmpty()) {
            c.forEach((k, v) -> c.put(k, 0.0));

            for (Integer itemID : c.keySet()){
                int count = 0;
                for (Usuario u : cluster){
                    if (u.getValoraciones().containsKey(itemID)){
                        ++count;
                        double val = c.get(itemID);
                        c.put(itemID, val + u.getValoraciones().get(itemID));
                    }
                }
                double v = c.get(itemID);
                c.put(itemID, v/count);
            }

            centroides.set(id, new Centroide(id, c));
        }
    }

    /**
     * @fn shouldTerminate
     * @brief Determina si el algoritmo de kMeans debería o no terminar antes de tiempo. Esto se debe dar solo en el caso de que nada haya cambiado en el estado de los clusters entre una iteración y la siguiente
     * @pre a o b pueden ser vacíos, pero si no lo son siempre tienen las mismas llaves que corresponden a los identificadores de los k centroides creados
     * @post si a o b eran vacíos o bien las valores varían para una o varias llaves determinadas, devuelve falso. Si a y b son idénticos devuelve cierto.
     * @param b Mapa que represeta el estado de los clusters en la ultima iteración de K-Means
     * @return  falso si los objetos son distintos, cierto si nada ha cambiado del estado en la última iteración al actual.
     */
    private boolean shouldTerminate(Map<Integer, ArrayList<Usuario>> b){
        if (b.isEmpty()) {
            return false;
        }

        for (Integer c : clusterMap.keySet()){
            boolean f = false;
            for (Integer d: b.keySet()) {
                if (!f && c.equals(d)){
                    f = true;
                    Integer szA = clusterMap.get(c).size(), szB = b.get(d).size();
                    if (!szA.equals(szB)){
                        return false;

                    }
                    else {
                        for (int i = 0; i < clusterMap.get(c).size(); i++) {
                            Integer aID = clusterMap.get(c).get(i).getUserId();
                            Integer bID = b.get(d).get(i).getUserId();

                            if (!aID.equals(bID)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * @fn computeAverageWSS
     * @brief Calcula el wss para todos los clusters de clusterMap y devuelve la media de estos
     * @pre clusterMap es el mapa de clusters, mapeados por su correspondiente centroide
     * @post se ha calculado la distancia media usuario-centroide para cada cluster, y devuelto la media
     * @return media de wss (within-cluster sum) de los clusters en clusterMap
     */
    private Double computeAverageWSS(){
        double wss = 0;
        for (Integer centroID : clusterMap.keySet()) {
            double d = 0;
            for (Usuario u : clusterMap.get(centroID)) {
                d += Math.pow(computeEuclideanDistance(u.getValoraciones(), null, centroID), 2);
            }
            d /= clusterMap.get(centroID).size();
            wss += d;
        }
        wss /= clusterMap.size();
        return wss;
    }

    /**
     * @fn similarity
     * @brief Computa la similaridad para un usuario; es decir, cómo de cercano es este usuario, de media, al resto de usuarios en el cluster.
     * @pre cluster es el cluster de u
     * @post se ha devuelto la distancia media de u a todos los clusters vecinos
     * @param u usuario al cual vamos a calcular la similaridad con todos los demás
     * @param cluster cluster al cual pertenece el usuario u
     * @return media de similaridad entre u y el resto de usuarios en el clsuter
     */
    private Double similarity(Usuario u, Integer cluster){
        double sum = 0.0;
        for (Usuario us : clusterMap.get(cluster)){
            if (!Objects.equals(u.getUserId(), us.getUserId())){
                sum += computeEuclideanDistance(u.getValoraciones(), us.getValoraciones(), cluster);
            }
        }
        return sum/(clusterMap.get(cluster).size()-1);
    }

    /**
     * @fn dissimilarity
     * @brief computa la diferencia entre un usuario y el resto de usuarios que no pertenecen al cluster de u
     * @pre uC es el identificador cluster del usuario u
     * @post se ha devuelto la distancia media entre u y todos los usuarios que no pertenecen al cluster de u
     * @param u usuario para el que queremos calcular la disimilaridad
     * @param uC identificador del cluster al que pertenece u
     * @return
     */
    private Double dissimilarity(Usuario u, Integer uC){

        double sum = 0.0, minAvg = -1.0;

        for (Integer k : clusterMap.keySet()){
            if (!Objects.equals(uC, k)){
                for (Usuario usr : clusterMap.get(k)){
                    sum += computeEuclideanDistance(u.getValoraciones(), usr.getValoraciones(), k);
                }
                sum /= clusterMap.get(k).size();
                if (minAvg == -1.0 || sum < minAvg){
                    minAvg = sum;
                }
            }
        }

        return minAvg;
    }

    /**
     * @fn sillhouetteCoef
     * @brief computa el coeficiente de la Silueta del usuario u
     * @pre el usuario u pertenece al cluster indicado por el identificador cluster
     * @post se ha devuelto el coeficiente de la silueta del usuario u
     * @param u usuario para el cual vamos a calcular el coeficiente de la silueta
     * @param cluster identificador del cluster de u
     * @return devuelve el coeficiente de la silueta del usuario u
     */
    private Double sillhouetteCoef(Usuario u, Integer cluster){
        if (clusterMap.get(cluster).size() == 1) return 0.0;

        double sim = similarity(u, cluster);
        double dis = dissimilarity(u, cluster);
        return (dis - sim / Math.max(dis, sim));
    }

    /**
     * @fn setOptimalK
     * @brief Encuentra la k optimal para ejecutar el algoritmo de kMeans.
     * @pre
     * @post se ha encontrado y establecido la k óptima para ejecutar el algoritmo
     * @param usuarios conjunto de usuarios que se quiere clusterizar.
     */
    public void setOptimalK(Usuarios usuarios){
        double maxAvg = -1.0;
        int optimalK = -1;
        for (int i = 2; i <= 10; i++){
            centroides = new ArrayList<>();
            clusterMap = new HashMap<>();

            double silAvg = 0.0;
            this.k = i;
            clusterFormation(usuarios);

            for (Integer k : clusterMap.keySet()){
                for (Usuario u : clusterMap.get(k)){
                    silAvg += sillhouetteCoef(u, k);
                }
                silAvg /= clusterMap.get(k).size();
            }
            
            if (maxAvg == -1.0 || silAvg > maxAvg){
                maxAvg = silAvg;
                optimalK = i;
            }
        }
        this.k = optimalK;
    }
}
