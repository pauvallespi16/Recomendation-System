/** @file NearestNeighborsTest.java
 @brief Código de la clase de pruebas de la clase NearestNeighbors
 */

import clases.*;
import controladores.NearestNeighbors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;


import static org.junit.jupiter.api.Assertions.assertEquals;

/** @class NearestNeighborsTest
 * @brief Tiene las pruebas implementadas para comprobar que funcionan los métodos de la clase Nearest Neighbors.
 *
 * @author Andres Eduardo Bercowsky Rama
 *
 * @date November 2021
 */
public class NearestNeighborsTest {

    /** @fn testDistanceNums
     * @brief Test para comprobar las funcionalidades de testDistanceNums de la clase NearestNeighbors
     */
    @Test
    @DisplayName("Distancia entre números")
    void testDistanceNums() {
        NearestNeighbors n = new NearestNeighbors(3);
        Item a = new Item();
        Item b = new Item();

        String[] attNames = {"Budget", "Revenue", "Popularity", "Run time"};
        String[] attValuesA = {"0.3", "0.35", "0.17", "0.86"};
        String[] attValuesB = {"0.4", "0.2", "0.30", "0.80"};

        Map<String, Atributo> atributosA = new HashMap<>();
        Map<String, Atributo> atributosB = new HashMap<>();

        for (int i = 0; i < attNames.length; i++) {
            AtributoNumerico atributoA = new AtributoNumerico(attValuesA[i]);
            atributoA.setValorNormalizado(atributoA.getValor());
            atributosA.put(attNames[i], atributoA);

            AtributoNumerico atributoB = new AtributoNumerico(attValuesB[i]);
            atributoB.setValorNormalizado(atributoB.getValor());
            atributosB.put(attNames[i], atributoB);
        }
        a.setAtributos(atributosA);
        b.setAtributos(atributosB);

        for (int i = 0; i < a.getAtributos().size(); i++) {
            AtributoNumerico a1 = (AtributoNumerico) atributosA.get(attNames[i]);
            AtributoNumerico b1 = (AtributoNumerico) atributosB.get(attNames[i]);
            double distancia = n.distanceNums(attNames[i], a, b);
            double distanciaReal = Math.abs(a1.getValorNormalizado() - b1.getValorNormalizado());
            assertEquals(distanciaReal, distancia, "incorrect distance at: " + i);
        }
    }

    /** @fn testPrediccionValoracionItem
     * @brief Test para comprobar las funcionalidades de prediccionValoracionItem de la clase NearestNeighbors
     */
    @Test
    @DisplayName("Predicción valoración de un ítem")
    void testPrediccionValoracionItem() {
        NearestNeighbors n = new NearestNeighbors(3);

        String[] attNames = {"Budget", "Director", "Genre", "Adult", "Sinopsis"};

        AtributoNumerico numerico1 = new AtributoNumerico("0.3");
        numerico1.setValorNormalizado(0.3);
        AtributoCategorico categorico1 = new AtributoCategorico("Charles");
        AtributoCategoricoMultiple categoricoMultiple1 = new AtributoCategoricoMultiple("Action;Horror;Comedy;Science");
        AtributoBoolean booleano1 = new AtributoBoolean("true");
        categoricoMultiple1.setBitSetValue(0);
        categoricoMultiple1.setBitSetValue(1);
        categoricoMultiple1.setBitSetValue(2);
        categoricoMultiple1.setBitSetValue(3);
        AtributoTexto texto1 = new AtributoTexto("El planeta Tierra");

        AtributoNumerico numerico2 = new AtributoNumerico("0.4");
        numerico2.setValorNormalizado(0.4);
        AtributoCategorico categorico2 = new AtributoCategorico("Pau");
        AtributoCategoricoMultiple categoricoMultiple2 = new AtributoCategoricoMultiple("Science;Comedy;HAHA;jdjd");
        AtributoBoolean booleano2 = new AtributoBoolean("true");
        categoricoMultiple2.setBitSetValue(1);
        categoricoMultiple2.setBitSetValue(3);
        categoricoMultiple2.setBitSetValue(4);
        categoricoMultiple2.setBitSetValue(5);
        AtributoTexto texto2 = new AtributoTexto("Sabemos que el planeta tierra es grande");
        //1,1,1
        //1.584962501,...,1,1,1,...,...
        // numerador = 3
        // denominadorA = sqrt(3)
        // denominadorB = 

        Map<String, Atributo> atributos1 = new HashMap<>();
        Map<String, Atributo> atributos2 = new HashMap<>();

        atributos1.put(attNames[0], numerico1);
        atributos1.put(attNames[1], categorico1);
        atributos1.put(attNames[2], categoricoMultiple1);
        atributos1.put(attNames[3], booleano1);
        atributos1.put(attNames[4], texto1);

        atributos2.put(attNames[0], numerico2);
        atributos2.put(attNames[1], categorico2);
        atributos2.put(attNames[2], categoricoMultiple2);
        atributos2.put(attNames[3], booleano2);
        atributos2.put(attNames[4], texto2);

        Map<String, Integer> aux = new HashMap<>();
        Map<String, Map<String, Integer>> IDF = new HashMap<>();
        IDF.put(attNames[4], aux);
        for (Map.Entry<String, Integer> words : texto1.getValor().entrySet()) {
            if (IDF.get(attNames[4]).containsKey(words.getKey())) {
                int num = IDF.get(attNames[4]).get(words.getKey());
                IDF.get(attNames[4]).put(words.getKey(), num+1);
            }
            else IDF.get(attNames[4]).put(words.getKey(), 1);
        }
        for (Map.Entry<String, Integer> words : texto2.getValor().entrySet()) {
            if (IDF.get(attNames[4]).containsKey(words.getKey())) {
                int num = IDF.get(attNames[4]).get(words.getKey());
                IDF.get(attNames[4]).put(words.getKey(), num+1);
            }
            else IDF.get(attNames[4]).put(words.getKey(), 1);
        }
        Parser.setIDF(IDF);

        Item a = new Item(0, atributos1);
        Item b = new Item(1, atributos2);

        ArrayList<Item> its = new ArrayList<>();
        its.add(a);
        its.add(b);

        Items items = new Items(its);
        Parser.setAllItems(items);

        AtributoNumerico a1 = (AtributoNumerico) atributos1.get(attNames[0]);
        AtributoNumerico b1 = (AtributoNumerico) atributos2.get(attNames[0]);
        double distancia = n.distanceNums(attNames[0], a, b);
        double distanceReal = Math.abs(a1.getValorNormalizado() - b1.getValorNormalizado());
        assertEquals(distanceReal, distancia, "incorrect distance at: 0");


        distancia = n.distanceCategorico(attNames[1], a, b);
        distanceReal = 1;
        assertEquals(distanceReal, distancia, "incorrect distance at: 1");


        distancia = n.distanceCategoricoMultiple(attNames[2], a, b);
        distanceReal = 0.5;
        assertEquals(distanceReal, distancia, "incorrect distance at: 2");


        distancia = n.distanceBooleans(attNames[3], a, b);
        distanceReal = 0;
        assertEquals(distanceReal, distancia, "incorrect distance at: 3");

        distancia = n.distanceTexto(attNames[4], a, b);
        distanceReal = 0.5205077538497194;
        assertEquals(distanceReal, distancia, "incorrect distance at: 4");
    }

    /** @fn testComputeAllSimilarities
     * @brief Test para comprobar las funcionalidades de computeAllSimilarities de la clase NearestNeighbors
     */
    @Test
    @DisplayName("Computar similitudes")
    void testComputeAllSimilarities() {
        Parser.setAllItems(new Items (new ArrayList<>()));

        String[] attNames = {"Budget", "Director", "Genre", "Adult"};

        AtributoNumerico numericoA = new AtributoNumerico("0.3");
        numericoA.setValorNormalizado(0.3);
        AtributoCategorico categoricoA = new AtributoCategorico("Charles");
        AtributoCategoricoMultiple categoricoMultipleA = new AtributoCategoricoMultiple("Action;Horror;Comedy;Science");
        AtributoBoolean booleanoA = new AtributoBoolean("true");

        AtributoNumerico numericoB = new AtributoNumerico("0.4");
        numericoB.setValorNormalizado(0.4);
        AtributoCategorico categoricoB = new AtributoCategorico("Pau");
        AtributoCategoricoMultiple categoricoMultipleB = new AtributoCategoricoMultiple("Science");
        AtributoBoolean booleanoB = new AtributoBoolean("true");

        AtributoNumerico numericoC = new AtributoNumerico("0.5");
        numericoC.setValorNormalizado(0.5);
        AtributoCategorico categoricoC = new AtributoCategorico("Pau");
        AtributoCategoricoMultiple categoricoMultipleC = new AtributoCategoricoMultiple("Science");
        AtributoBoolean booleanoC = new AtributoBoolean("false");

        AtributoNumerico numericoD = new AtributoNumerico("0.6");
        numericoD.setValorNormalizado(0.6);
        AtributoCategorico categoricoD = new AtributoCategorico("Pau");
        AtributoCategoricoMultiple categoricoMultipleD = new AtributoCategoricoMultiple("Science");
        AtributoBoolean booleanoD = new AtributoBoolean("true");

        AtributoNumerico numericoE = new AtributoNumerico("0.7");
        numericoE.setValorNormalizado(0.7);
        AtributoCategorico categoricoE = new AtributoCategorico("Pau");
        AtributoCategoricoMultiple categoricoMultipleE = new AtributoCategoricoMultiple("Science");
        AtributoBoolean booleanoE = new AtributoBoolean("true");

        AtributoNumerico numericoF = new AtributoNumerico("0.7");
        numericoF.setValorNormalizado(0.7);
        AtributoCategorico categoricoF = new AtributoCategorico("Sam");
        AtributoCategoricoMultiple categoricoMultipleF = new AtributoCategoricoMultiple("Action;Horror;Comedy");
        AtributoBoolean booleanoF = new AtributoBoolean("true");

        Map<String, Atributo> atributosA = new HashMap<>();
        Map<String, Atributo> atributosB = new HashMap<>();
        Map<String, Atributo> atributosC = new HashMap<>();
        Map<String, Atributo> atributosD = new HashMap<>();
        Map<String, Atributo> atributosE = new HashMap<>();
        Map<String, Atributo> atributosF = new HashMap<>();

        atributosA.put(attNames[0], numericoA);
        atributosA.put(attNames[1], categoricoA);
        atributosA.put(attNames[2], categoricoMultipleA);
        atributosA.put(attNames[3], booleanoA);

        atributosB.put(attNames[0], numericoB);
        atributosB.put(attNames[1], categoricoB);
        atributosB.put(attNames[2], categoricoMultipleB);
        atributosB.put(attNames[3], booleanoB);

        atributosC.put(attNames[0], numericoC);
        atributosC.put(attNames[1], categoricoC);
        atributosC.put(attNames[2], categoricoMultipleC);
        atributosC.put(attNames[3], booleanoC);

        atributosD.put(attNames[0], numericoD);
        atributosD.put(attNames[1], categoricoD);
        atributosD.put(attNames[2], categoricoMultipleD);
        atributosD.put(attNames[3], booleanoD);

        atributosE.put(attNames[0], numericoE);
        atributosE.put(attNames[1], categoricoE);
        atributosE.put(attNames[2], categoricoMultipleE);
        atributosE.put(attNames[3], booleanoE);

        atributosF.put(attNames[0], numericoF);
        atributosF.put(attNames[1], categoricoF);
        atributosF.put(attNames[2], categoricoMultipleF);
        atributosF.put(attNames[3], booleanoF);


        Item a = new Item(0, atributosA);
        a.setOriginalId(0);
        Item b = new Item(1, atributosB);
        b.setOriginalId(1);
        Item c = new Item(2, atributosC);
        c.setOriginalId(2);
        Item d = new Item(3, atributosD);
        d.setOriginalId(3);
        Item e = new Item(4, atributosE);
        e.setOriginalId(4);
        Item f = new Item(5, atributosF);
        f.setOriginalId(5);

        Map <Integer, Double> valoraciones = new HashMap<>();
        valoraciones.put(b.getId(), 5.0);

        Usuario usuario = new Usuario(0, valoraciones);

        ArrayList<Item> itemsAux = new ArrayList<>();
        itemsAux.add(a);
        itemsAux.add(b);
        itemsAux.add(c);
        itemsAux.add(d);
        itemsAux.add(e);
        itemsAux.add(f);
        Parser.setAllItems(new Items (itemsAux));

        NearestNeighbors n = new NearestNeighbors(new Usuarios(), usuario, new Items(itemsAux));
        n.setNeighbors(2);
        n.setNeighbors(itemsAux.size());

        Map<Integer, Double> knn = n.computeAllPredicciones();

        List<Item> list = Arrays.asList(d, e);
        ArrayList<Item> correctRes = new ArrayList<>();
        correctRes.addAll(list);

        ArrayList<Item> knnAux = new ArrayList<>();

        for (Integer i : knn.keySet()) knnAux.add(Parser.getAllItems().getItem(i));
        ArrayList<Item> knnAux2 = new ArrayList<>();
        knn.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> knnAux2.add(Parser.getAllItems().getItem(x.getKey())));

        ArrayList<Item> knn2 = new ArrayList<Item>( knnAux2.subList(0, 2) );

        assertEquals(correctRes, knn2, "incorrect items");
    }
}
