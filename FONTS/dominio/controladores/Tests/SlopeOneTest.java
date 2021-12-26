/** @file SlopeOne.java
 @brief Código de la clase de pruebas de la clase SlopeOne
 */

import clases.*;
import controladores.SlopeOne;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** @class SlopeOneTest
 * @brief Tiene las pruebas implementadas para comprobar que funcionan los métodos de la clase Slope One.
 *
 * @author Carla Campàs Gené
 *
 * @date November 2021
 */
public class SlopeOneTest {
    /**@var Usuario u
     * @brief Variable utilizada para almacenar el usuario del cual parten las predicciones.
     */
    private static Usuario u;

    /**@var Usuarios us
     * @brief Variable utilizada para almacenar el conjunto de usuarios que vamos a utilizar para hacer las predicciones.
     */
    private static Usuarios us;

    private static SlopeOne slopeOne = new SlopeOne();

    /** @fn setUp
     * @brief funcion para implementar todas las variables/clases necesarias para hacer los tests del Slope One
     */
    @BeforeAll
    public static void setUp() {
        ArrayList<Item> items = new ArrayList<>();
        for (int i=0; i<=10; i++){
            items.add(new Item(i, new HashMap<>()));
        }
        Parser.setAllItems(new Items(items));

        ArrayList <Usuario> aU = new ArrayList<>();
        Map <Integer, Double> valoraciones = new HashMap<>();

        valoraciones.put(0, 9.2);
        valoraciones.put(1, 10.5);
        valoraciones.put(4, 5.5);
        valoraciones.put(6, 1.1);
        valoraciones.put(8, 3.9);
        u = new Usuario(0, valoraciones);

        valoraciones = new HashMap<>();
        valoraciones.put(0, 5.1);
        valoraciones.put(1, 2.6);
        valoraciones.put(2, 9.3);
        valoraciones.put(8, 3.6);
        valoraciones.put(3, 8.3);
        valoraciones.put(10, 6.2);
        aU.add(new Usuario (1, valoraciones));

        valoraciones = new HashMap<>();
        valoraciones.put(2, 7.0);
        aU.add(new Usuario (2, valoraciones));

        valoraciones = new HashMap<>();
        valoraciones.put(1, 6.5);
        valoraciones.put(3, 5.72);
        valoraciones.put(8, 1.23);
        valoraciones.put(7, 6.3);
        valoraciones.put(2, 3.4);
        aU.add(new Usuario (3, valoraciones));

        valoraciones = new HashMap<>();
        valoraciones.put(10, 3.0);
        valoraciones.put(4, 5.0);
        valoraciones.put(2, 5.0);
        valoraciones.put(9, 7.0);
        valoraciones.put(1, 1.0);
        aU.add(new Usuario (4, valoraciones));

        valoraciones.put(6, 3.0);
        valoraciones.put(2, 5.0);
        valoraciones.put(9, 5.0);
        valoraciones.put(1, 7.0);
        valoraciones.put(3, 1.0);
        aU.add(new Usuario (5, valoraciones));

        us = new Usuarios();
        us.setUsers(aU);

        Parser.setScoreLimits(0.0, 10.0);
        slopeOne.setUser(u);
        slopeOne.setUsuarios(us);
        //slopeOne.computarDesviacion();
        //slopeOne.computarPredicciones();
    }

    /** @fn testComputarDesviacion
     * @brief Test para comprobar las funcionalidades de computerDesviacion de la clase SlopeOne
     */
    @Test
    @DisplayName("Calcular mediana")
    void testCalcularMediana() {
        assertEquals(slopeOne.getMediana(), 6.040000000000001);
    }

    @Test
    @DisplayName("Computar desviación")
    void testComputarDesviacion() {
        Map <Integer, Map <Integer, Double>> expectedDiff = new HashMap<>();
        /**************************************
         Diff:
         Item 0: (0,0.0)(1,2.4999999999999996)(2,-4.200000000000001)(3,-3.200000000000001)(8,1.4999999999999996)(10,-1.1000000000000005)
         Item 1: (0,-2.4999999999999996)(1,0.0)(2,-1.4000000000000004)(3,0.3599999999999997)(4,-1.0)(6,4.0)(7,0.20000000000000018)(8,2.135)(9,-2.0)(10,-0.5333333333333332)
         Item 2: (0,4.200000000000001)(1,1.4000000000000004)(2,0.0)(3,0.8933333333333334)(4,0.0)(6,2.0)(7,-2.9)(8,3.9350000000000005)(9,-1.0)(10,2.3666666666666667)
         Item 3: (0,3.200000000000001)(1,-0.3599999999999997)(2,-0.8933333333333334)(3,0.0)(4,-4.0)(6,-2.0)(7,-0.5800000000000001)(8,4.595000000000001)(9,-4.0)(10,0.050000000000000266)
         Item 4: (1,1.0)(2,0.0)(3,4.0)(4,0.0)(6,2.0)(9,-1.0)(10,2.0)
         Item 6: (1,-4.0)(2,-2.0)(3,2.0)(4,-2.0)(6,0.0)(9,-2.0)(10,0.0)
         Item 7: (1,-0.20000000000000018)(2,2.9)(3,0.5800000000000001)(7,0.0)(8,5.07)
         Item 8: (0,-1.4999999999999996)(1,-2.135)(2,-3.9350000000000005)(3,-4.595000000000001)(7,-5.07)(8,0.0)(10,-2.6)
         Item 9: (1,2.0)(2,1.0)(3,4.0)(4,1.0)(6,2.0)(9,0.0)(10,3.0)
         Item 10: (0,1.1000000000000005)(1,0.5333333333333332)(2,-2.3666666666666667)(3,-0.050000000000000266)(4,-2.0)(6,0.0)(8,2.6)(9,-3.0)(10,0.0)
         **************************************/

        Map <Integer, Double> auxD = new HashMap<>();
        auxD.put(0,0.0);
        auxD.put(1,2.4999999999999996);
        auxD.put(2,-4.200000000000001);
        auxD.put(3,-3.200000000000001);
        auxD.put(8,1.4999999999999996);
        auxD.put(10,-1.1000000000000005);
        expectedDiff.put(0, auxD);

        auxD = new HashMap<>();
        auxD.put(0,-2.4999999999999996);
        auxD.put(1,0.0);
        auxD.put(2,-1.4000000000000004);
        auxD.put(3,0.3599999999999997);
        auxD.put(4,-1.0);
        auxD.put(6,4.0);
        auxD.put(7,0.20000000000000018);
        auxD.put(8,2.135);
        auxD.put(9,-2.0);
        auxD.put(10,-0.5333333333333332);
        expectedDiff.put(1, auxD);

        auxD = new HashMap<>();
        auxD.put(0,4.200000000000001);
        auxD.put(1,1.4000000000000004);
        auxD.put(2,0.0);
        auxD.put(3,0.8933333333333334);
        auxD.put(4,0.0);
        auxD.put(6,2.0);
        auxD.put(7,-2.9);
        auxD.put(8,3.9350000000000005);
        auxD.put(9,-1.0);
        auxD.put(10,2.3666666666666667);
        expectedDiff.put(2, auxD);

        auxD = new HashMap<>();
        auxD.put(0,3.200000000000001);
        auxD.put(1,-0.3599999999999997);
        auxD.put(2,-0.8933333333333334);
        auxD.put(3,0.0);
        auxD.put(4,-4.0);
        auxD.put(6,-2.0);
        auxD.put(7,-0.5800000000000001);
        auxD.put(8,4.595000000000001);
        auxD.put(9,-4.0);
        auxD.put(10,0.050000000000000266);
        expectedDiff.put(3, auxD);

        auxD = new HashMap<>();
        auxD.put(1,1.0);
        auxD.put(2,0.0);
        auxD.put(3,4.0);
        auxD.put(4,0.0);
        auxD.put(6,2.0);
        auxD.put(9,-1.0);
        auxD.put(10,2.0);
        expectedDiff.put(4, auxD);

        auxD = new HashMap<>();
        auxD.put(1,-4.0);
        auxD.put(2,-2.0);
        auxD.put(3,2.0);
        auxD.put(4,-2.0);
        auxD.put(6,0.0);
        auxD.put(9,-2.0);
        auxD.put(10,0.0);
        expectedDiff.put(6, auxD);

        auxD = new HashMap<>();
        auxD.put(1,-0.20000000000000018);
        auxD.put(2,2.9);
        auxD.put(3,0.5800000000000001);
        auxD.put(7,0.0);
        auxD.put(8,5.07);
        expectedDiff.put(7, auxD);

        auxD = new HashMap<>();
        auxD.put (0,-1.4999999999999996);
        auxD.put(1,-2.135);
        auxD.put(2,-3.9350000000000005);
        auxD.put(3,-4.595000000000001);
        auxD.put(7,-5.07);
        auxD.put(8,0.0);
        auxD.put(10,-2.6);
        expectedDiff.put(8, auxD);

        auxD = new HashMap<>();
        auxD.put(1,2.0);
        auxD.put(2,1.0);
        auxD.put(3,4.0);
        auxD.put(4,1.0);
        auxD.put(6,2.0);
        auxD.put(9,0.0);
        auxD.put(10,3.0);
        expectedDiff.put(9, auxD);

        auxD = new HashMap<>();
        auxD.put(0,1.1000000000000005);
        auxD.put(1,0.5333333333333332);
        auxD.put(2,-2.3666666666666667);
        auxD.put(3,-0.050000000000000266);
        auxD.put(4,-2.0);
        auxD.put(6,0.0);
        auxD.put(8,2.6);
        auxD.put(9,-3.0);
        auxD.put(10,0.0);
        expectedDiff.put(10, auxD);

        Map <Integer, Map <Integer, Double>> diff = slopeOne.getDiff();
        for (Map.Entry<Integer, Map <Integer, Double>> d : diff.entrySet()){
            assert(expectedDiff.containsKey(d.getKey()));
            for (Map.Entry<Integer, Double> d1 : d.getValue().entrySet()){
                assert(expectedDiff.get(d.getKey()).containsKey(d1.getKey()));
                assertEquals(expectedDiff.get(d.getKey()).get(d1.getKey()), d1.getValue());
            }
        }

        Map <Integer, Map <Integer, Integer>> expectedFreq = new HashMap<>();;
        /**************************************
         Freq:
         Item 0: (0,1)(1,1)(2,1)(3,1)(8,1)(10,1)
         Item 1: (0,1)(1,4)(2,4)(3,3)(4,2)(6,1)(7,1)(8,2)(9,2)(10,3)
         Item 2: (0,1)(1,4)(2,5)(3,3)(4,2)(6,1)(7,1)(8,2)(9,2)(10,3)
         Item 3: (0,1)(1,3)(2,3)(3,3)(4,1)(6,1)(7,1)(8,2)(9,1)(10,2)
         Item 4: (1,2)(2,2)(3,1)(4,2)(6,1)(9,2)(10,2)
         Item 6: (1,1)(2,1)(3,1)(4,1)(6,1)(9,1)(10,1)
         Item 7: (1,1)(2,1)(3,1)(7,1)(8,1)
         Item 8: (0,1)(1,2)(2,2)(3,2)(7,1)(8,2)(10,1)
         Item 9: (1,2)(2,2)(3,1)(4,2)(6,1)(9,2)(10,2)
         Item 10: (0,1)(1,3)(2,3)(3,2)(4,2)(6,1)(8,1)(9,2)(10,3)
         **************************************/
        Map <Integer, Integer> auxF = new HashMap<>();
        auxF.put(0,1);
        auxF.put(1,1);
        auxF.put(2,1);
        auxF.put(3,1);
        auxF.put(8,1);
        auxF.put(10,1);
        expectedFreq.put(0, auxF);

        auxF = new HashMap<>();
        auxF.put(0,1);
        auxF.put(1,4);
        auxF.put(2,4);
        auxF.put(3,3);
        auxF.put(4,2);
        auxF.put(6,1);
        auxF.put(7,1);
        auxF.put(8,2);
        auxF.put(9,2);
        auxF.put(10,3);
        expectedFreq.put(1, auxF);

        auxF = new HashMap<>();
        auxF.put(0,1);
        auxF.put(1,4);
        auxF.put(2,5);
        auxF.put(3,3);
        auxF.put(4,2);
        auxF.put(6,1);
        auxF.put(7,1);
        auxF.put(8,2);
        auxF.put(9,2);
        auxF.put(10,3);
        expectedFreq.put(2, auxF);

        auxF = new HashMap<>();
        auxF.put(0,1);
        auxF.put(1,3);
        auxF.put(2,3);
        auxF.put(3,3);
        auxF.put(4,1);
        auxF.put(6,1);
        auxF.put(7,1);
        auxF.put(8,2);
        auxF.put(9,1);
        auxF.put(10,2);
        expectedFreq.put(3, auxF);

        auxF = new HashMap<>();
        auxF.put(1,2);
        auxF.put(2,2);
        auxF.put(3,1);
        auxF.put(4,2);
        auxF.put(6,1);
        auxF.put(9,2);
        auxF.put(10,2);
        expectedFreq.put(4, auxF);

        auxF = new HashMap<>();
        auxF.put(1,1);
        auxF.put(2,1);
        auxF.put(3,1);
        auxF.put(4,1);
        auxF.put(6,1);
        auxF.put(9,1);
        auxF.put(10,1);
        expectedFreq.put(6, auxF);

        auxF = new HashMap<>();
        auxF.put(1,1);
        auxF.put(2,1);
        auxF.put(3,1);
        auxF.put(7,1);
        auxF.put(8,1);
        expectedFreq.put(7, auxF);

        auxF = new HashMap<>();
        auxF.put(0,1);
        auxF.put(1,2);
        auxF.put(2,2);
        auxF.put(3,2);
        auxF.put(7,1);
        auxF.put(8,2);
        auxF.put(10,1);
        expectedFreq.put(8, auxF);

        auxF = new HashMap<>();
        auxF.put(1,2);
        auxF.put(2,2);
        auxF.put(3,1);
        auxF.put(4,2);
        auxF.put(6,1);
        auxF.put(9,2);
        auxF.put(10,2);
        expectedFreq.put(9, auxF);

        auxF = new HashMap<>();
        auxF.put(0,1);
        auxF.put(1,3);
        auxF.put(2,3);
        auxF.put(3,2);
        auxF.put(4,2);
        auxF.put(6,1);
        auxF.put(8,1);
        auxF.put(9,2);
        auxF.put(10,3);
        expectedFreq.put(10, auxF);

        Map <Integer, Map <Integer, Integer>> freq = slopeOne.getFreq();
        for (Map.Entry<Integer, Map <Integer, Integer>> f : freq.entrySet()){
            assert(expectedDiff.containsKey(f.getKey()));
            for (Map.Entry<Integer, Integer> f1 : f.getValue().entrySet()){
                assert(expectedDiff.get(f.getKey()).containsKey(f1.getKey()));
                assertEquals(expectedFreq.get(f.getKey()).get(f1.getKey()), f1.getValue());
            }
        }
    }

    /** @fn setUp
     * @brief Test para comprobar las funcionalidades de computarPredicciones de la clase SlopeOne
     */
    @Test
    @DisplayName("Test computar predicciones")
    void testComputarPredicciones () {
        /*******************************
         PREDICTION ITEM 0 IS 9.2
         PREDICTION ITEM 1 IS 7.486999999999999
         PREDICTION ITEM 2 IS 9.077
         PREDICTION ITEM 3 IS 7.5512500000000005
         PREDICTION ITEM 4 IS 7.42
         PREDICTION ITEM 6 IS 3.6999999999999997
         PREDICTION ITEM 7 IS 9.635000000000002
         PREDICTION ITEM 8 IS 6.446000000000001
         PREDICTION ITEM 9 IS 8.22
         PREDICTION ITEM 10 IS 7.250000000000001
         *********************************/
        //Testeado previamente en computar desviaciones con el mismo set de datos

        Map<Integer, Double> sol = new HashMap<>();
        sol.put(0, 9.2);
        sol.put(1, 7.486999999999999);
        sol.put(2, 9.077);
        sol.put(3, 7.5512500000000005);
        sol.put(4, 7.42);
        sol.put(6, 3.6999999999999997);
        sol.put(7, 9.635000000000002);
        sol.put(8, 6.446000000000001);
        sol.put(9, 8.22);
        sol.put(10, 7.250000000000001);

        Map<Integer, Double> pred = slopeOne.getPred();
        for (Map.Entry<Integer, Double> p : pred.entrySet()) {
            assert (sol.containsKey(p.getKey()));
            assertEquals (sol.get(p.getKey()), p.getValue());
        }
    }
}
