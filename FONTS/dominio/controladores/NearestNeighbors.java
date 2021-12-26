package controladores; /** @file NearestNeighbors.java
 @brief Código de la clase Nearest Neighbors
 */

import clases.*;

import java.util.*;
/** @class NearestNeighbors
 * @brief Implementación del algoritmo nearest neighbors, usando content based filtering.
 *
 * El algoritmo Nearest Neighbors tiene en cuenta la distancia de cada item que ha valorado el usuario con el resto de
 * items que existen, para así recomendarle los items mas similares. Ademas, también se tiene en cuenta la valoración
 * que le ha dado el usuario a sus items.
 *
 * @author Andrés Eduardo Bercowsky Rama
 *
 * @date December 2021
 */
public class NearestNeighbors extends Algoritmos {
    /** @var int nNearestNeighbors
     * @brief Esta variable nos indica el número de vecinos mas cercanos que tenemos que devolver.
     */
	private static int k = 0;

    /** @fn NearestNeighbors
     * @brief Constructora por defecto.
     *
     * @pre -
     * @post Se inicializa un objeto de la clase vacío.
     */
    public NearestNeighbors() {}

    /** @fn NearestNeighbors
     * @brief Constructora por defecto.
     *
     * @pre El parámetro k es un entero positivo.
     * @post Se inicializa un objeto de la clase con el atributo k inicializado.
     * @param k: indica el número de vecinos mas cercanos que tenemos que devolver.
     */
    public NearestNeighbors(int k) {
        NearestNeighbors.k = k;
    }

    /** @fn NearestNeighbors
     * @brief Constructora por defecto.
     *
     * @pre Los parametros us, u e it estan inicializados.
     * @post Se inicializa un objeto de la clase con los atributos de la super clase inicializados.
     * @param us: indica los usuarios que existen.
     * @param u: indica el usuario del cual queremos hacer una recomendacion.
     * @param it: items de los que queremos recomendar alguno.
     */
    public NearestNeighbors (Usuarios us, Usuario u, Items it){
        super(us, u, it);
    }

    /** @fn setNeighbors
     * @brief Setter de la variable k.
     *
     * @pre El parámetro que se le pasa a la función ha de ser un entero positivo.
     * @post Se ha cambiado el valor de la variable k al parámetro k.
     * @param k: indica el número de vecinos mas cercanos que tenemos que devolver.
     */
    public void setNeighbors(int k) { NearestNeighbors.k = k; }

    /** @fn getMaxWordInAtributoTexto
     * @brief Calcula la palabra con máxima frecuencia en un atributoTexto.
     *
     * @pre El atributoTexto está inicializado.
     * @post Se ha devuelto la frecuencia máxima de la palabra que más aparece en el atributo.
     * @param a: atributoTexto del cual se quiere saber la frecuencia de la palabra que mas aparece en el texto.
     * @return Devuelve la frecuencia máxima de la palabra que más aparece en el atributo.
     */
    private int getMaxWordInAtributoTexto(AtributoTexto a) {
        int max = 0;
        for (Map.Entry<String, Integer> word : a.getValor().entrySet()) {
            if (word.getValue() > max) max = word.getValue();
        }
        return max;
    }

    /** @fn cosineSimilarityBitSet
     * @brief Calcula el "cosine similarity" entre dos BitSets, que es una estructura de datos muy eficiente
     * para hacer cálculos entre vectores booleanos como ANDS, y devuelve un valor
     * entre 0 y 1, donde 1 es que son muy diferentes y 0 muy similares.
     * @pre Los BitSets pasados por parametro estan inicializados.
     * @post Se ha devuelto el "cosine similarity" entre los BitSets.
     * @param bsA: BitSet en que para cada posicion, nos dice si tiene o no el elemento del atributo.
     * @param bsB: BitSet en que para cada posicion, nos dice si tiene o no el elemento del atributo.
     * @return Devuelve el "cosine similarity" entre ambos BitSets.
     */
    public double cosineSimilarityBitSet(BitSet bsA, BitSet bsB) {
        BitSet combination = (BitSet) bsA.clone();
        combination.and(bsB);

        double dotProduct = combination.cardinality();
        double normA = bsA.cardinality();
        double normB = bsB.cardinality();

        if (normA == 0 || normB == 0) return 1;
        return  1 - dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    /** @fn cosineSimilarity
     * @brief Calcula el "cosine similarity" entre dos maps (string, valor) y devuelve un valor
     * entre 0 y 1, donde 1 es que son muy diferentes y 0 muy similares.
     * @pre Los maps pasados por parametro estan inicializados.
     * @post Se ha devuelto el "cosine similarity" entre los maps.
     * @param mapA: map que contiene las palabras y valores de un atributo
     * @param mapB: map que contiene las palabras y valores de un atributo
     * @return Devuelve el "cosine similarity" entre ambos maps.
     */
    public double cosineSimilarity(Map<String, Double> mapA, Map<String, Double> mapB) {
        double numerador = 0;
        double denominadorA = 0;
        double denominadorB = 0;
        for (Map.Entry<String, Double> words : mapA.entrySet()) {
            if (mapB.containsKey(words.getKey())) {
                numerador += words.getValue() * mapB.get(words.getKey()).doubleValue();
            }
            denominadorA += Math.pow(words.getValue(), 2);
        }
        for (Map.Entry<String, Double> words : mapB.entrySet()) {
            denominadorB += Math.pow(words.getValue(), 2);
        }
        if (denominadorA == 0 || denominadorB == 0) return 1;
        return 1 - numerador / (Math.sqrt(denominadorA)*Math.sqrt(denominadorB));
    }

    /** @fn distanceNums
     * @brief Calcula la distancia entre dos atributos numéricos.
     * @pre Existe un atributo att.
     * @post Se ha retornado la distancia entre el atributo att de los items a y b.
     * @param att: Nombre del atributo numérico.
     * @param a: Item del usuario.
     * @param b: Item del cual se quiere saber la distancia.
     * @return Devuelve la distancia entre el atributo numérico de los dos items.
     */
    public double distanceNums(String att, Item a, Item b) {
        AtributoNumerico a1 = (AtributoNumerico) a.getAtributo(att);
        AtributoNumerico b1 = (AtributoNumerico) b.getAtributo(att);
        if (a1.getValorNormalizado() < 0 || b1.getValorNormalizado() < 0) return 1.0;
        return Math.abs(a1.getValorNormalizado() - b1.getValorNormalizado());
    }

    /** @fn distanceFechas
     * @brief Calcula la distancia entre dos atributos fecha.
     * @pre Existe un atributo att.
     * @post Se ha retornado la distancia entre el atributo att de los items a y b.
     * @param att: Nombre del atributo numérico.
     * @param a: Item del usuario.
     * @param b: Item del cual se quiere saber la distancia.
     * @return Devuelve la distancia entre el atributo numérico de los dos items.
     */
    public double distanceFechas(String att, Item a, Item b) {
        AtributoFecha a1 = (AtributoFecha) a.getAtributo(att);
        AtributoFecha b1 = (AtributoFecha) b.getAtributo(att);
        if (a1.getValorNormalizado() < 0 || b1.getValorNormalizado() < 0) return 1.0;
        return Math.abs(a1.getValorNormalizado() - b1.getValorNormalizado());
    }

    /** @fn distanceBooleans
     * @brief Calcula la distancia entre dos atributos booleanos.
     * @pre Existe un atributo att.
     * @post Se ha retornado la distancia entre el atributo att de los items a y b.
     * @param att: Nombre del atributo booleano.
     * @param a: Item del usuario.
     * @param b: Item del cual se quiere saber la distancia.
     * @return Devuelve la distancia entre el atributo booleano de los dos items.
     */
    public double distanceBooleans(String att, Item a, Item b) {
        AtributoBoolean a1 = (AtributoBoolean) a.getAtributo(att);
        AtributoBoolean b1 = (AtributoBoolean) b.getAtributo(att);
        if (a1.getValor() == b1.getValor()) return 0.0;
        return 1.0;
    }

    /** @fn distanceCategorico
     * @brief Calcula la distancia entre dos atributos categóricos.
     * @pre Existe un atributo att.
     * @post Se ha retornado la distancia entre el atributo att de los items a y b.
     * @param att: Nombre del atributo categórico.
     * @param a: Item del usuario.
     * @param b: Item del cual se quiere saber la distancia.
     * @return Devuelve la distancia entre el atributo categórico de los dos items.
     */
    public double distanceCategorico(String att, Item a, Item b) {
        AtributoCategorico a1 = (AtributoCategorico) a.getAtributo(att);
        AtributoCategorico b1 = (AtributoCategorico) b.getAtributo(att);
        if ((a1.getValor()).equals(b1.getValor())) return 0.0;
        return 1.0;
    }

    /** @fn distanceCategoricoMultiple
     * @brief Calcula la distancia entre dos atributos categóricos múltiples.
     * @pre Existe un atributo att.
     * @post Se ha retornado la distancia entre el atributo att de los items a y b.
     * @param att: Nombre del atributo categórico múltiples.
     * @param a: Item del usuario.
     * @param b: Item del cual se quiere saber la distancia.
     * @return Devuelve la distancia entre el atributo categórico múltiples de los dos items.
     */
    public double distanceCategoricoMultiple(String att, Item a, Item b) {
        AtributoCategoricoMultiple a1 = (AtributoCategoricoMultiple)a.getAtributo(att);
        AtributoCategoricoMultiple b1 = (AtributoCategoricoMultiple)b.getAtributo(att);

        BitSet bs1 = a1.getBitSet();
        BitSet bs2 = b1.getBitSet();

        return cosineSimilarityBitSet(bs1, bs2);
    }

    /** @fn distanceTexto
     * @brief Calcula la distancia entre dos atributos de tipo texto.
     * @pre Existe un atributo att.
     * @post Se ha retornado la distancia entre el atributo att de los items a y b.
     * @param att: Nombre del atributo categórico múltiples.
     * @param a: Item del usuario.
     * @param b: Item del cual se quiere saber la distancia.
     */
    public double distanceTexto(String att, Item a, Item b) {
        AtributoTexto a1 = (AtributoTexto) a.getAtributo(att);
        AtributoTexto b1 = (AtributoTexto) b.getAtributo(att);

        Map<String, Integer> tfA = a1.getValor();
        Map<String, Integer> tfB = b1.getValor();

        Map<String, Integer> IDF = Parser.getIDF().get(att);

        Map<String, Double> mapA = new HashMap<>();
        Map<String, Double> mapB = new HashMap<>();

        int nDocuments = Parser.getAllItems().getSize();

        double maxRepetitions = getMaxWordInAtributoTexto(a1);
        for (Map.Entry<String, Integer> word : tfA.entrySet()) {
            mapA.put(word.getKey(), (word.getValue()/maxRepetitions) * Math.log(1 + (nDocuments / IDF.get(word.getKey()).doubleValue())));
        }

        maxRepetitions = getMaxWordInAtributoTexto(b1);
        for (Map.Entry<String, Integer> word : tfB.entrySet()) {
            mapB.put(word.getKey(), (word.getValue()/maxRepetitions) * Math.log(1 + (nDocuments / IDF.get(word.getKey()).doubleValue())));
        }

        return cosineSimilarity(mapA, mapB);
    }

    /** @fn prediccionValoracionItem
     * @brief Calcula las predicciones de valoraciones para un item que el usuario no ha valorado
     *
     * FORMULA:
     * recom(u,i) = ∑j(recom(u,j) * (1-dist (i,j)))
     * Donde u es el usuario, i es el item del cual queremos predecir la valoración y j es cada uno de los items
     * que ha valorado el usuario u.
     *
     * @pre Se ha inicializado la variable usuario a un usuario y la variable item a un item.
     * @post Se ha devuelto la predicción de la valoración para el item dado.
     * @param item: Item del usuario.
     * @return Devuelve la predicción de la valoración para el item dado.
     */
    public Double prediccionValoracionItem(Item item) {
        double predValoracion = 0;
        Map <Integer, Double> valoraciones = super.u.getValoraciones();
        for (Map.Entry<Integer, Double> valoracionUsuario : valoraciones.entrySet()) {
            Item knownItem = Parser.getAllItems().getItem(valoracionUsuario.getKey());
            Map<String, Atributo> attributes = knownItem.getAtributos();
            double distance = 0;
            for (Map.Entry<String, Atributo> att : attributes.entrySet()) {
                if (att.getValue() instanceof AtributoBoolean) distance += distanceBooleans(att.getKey(), knownItem, item);
                else if (att.getValue() instanceof AtributoNumerico) distance += distanceNums(att.getKey(), knownItem, item);
                else if (att.getValue() instanceof AtributoCategorico) distance += distanceCategorico(att.getKey(), knownItem, item);
                else if (att.getValue() instanceof AtributoCategoricoMultiple) distance += distanceCategoricoMultiple(att.getKey(), knownItem, item);
                else if (att.getValue() instanceof AtributoTexto) distance += distanceTexto(att.getKey(), knownItem, item);
                else if (att.getValue() instanceof AtributoFecha) distance += distanceFechas(att.getKey(), knownItem, item);
            }
            distance /= attributes.size();
            predValoracion += ((1-distance) * valoracionUsuario.getValue());
        }
        return predValoracion;
    }

    /** @fn computeAllSimilarities
     * @brief Calcula las distancias entre cada item que el usuario ha valorado y el resto de items.
     *
     * PSEUDOCÓDIGO:
     * for every item reviewed by usuario:
     *     compute the similarities with that item and the rest of the items, storing the result in an array
     *
     * @pre Existe el usuario
     * @post Se ha devuelto un array con los k items más cercanos.
     * @return Devuelve un array con los k items más cercanos.
     */
    public Map<Integer, Double> computeAllPredicciones() {
        //Key = idItem, value = distance
        Map<Integer, Double> similarities = new HashMap<>();
        Map <Integer, Double> valoraciones = super.u.getValoraciones();
        Map<Integer, Double> predicciones = new HashMap<>();
        for (int i = 0; i < super.it.getSize(); i++) similarities.put(i, 0.0);

        for (int i = 0; i < super.it.getSize(); i++) {
            if (!valoraciones.containsKey(i)) {
                predicciones.put(i, prediccionValoracionItem(super.it.getItem(i)));
            }
            else predicciones.put(i, -1.0);
        }
        //LinkedHashMap preserve the ordering of elements in which they are inserted
        Map <Integer, Double> orderedItems = new HashMap<>();

        //Use Comparator.reverseOrder() for reverse ordering
        predicciones.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> orderedItems.put(x.getKey(), x.getValue()));

        //ArrayList<Item> knn = new ArrayList<Item>( orderedItems.subList(0, k) );

        return orderedItems;
    }

    /** @fn calcularRecomendaciones
     * @brief para inicializar todo lo que debe calcular para sus recomendaciones
     * @pre existe clase de nearest neighbors
     * @post se ha ejecutado la clase de nearest neighbors y puesto el vector de predicciones a las predicciones
     */
    public void calcularRecomendaciones (int k) {
        setNeighbors(k);
        super.predictions = computeAllPredicciones();
    }
}
