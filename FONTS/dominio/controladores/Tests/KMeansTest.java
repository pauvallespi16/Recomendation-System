/** @file KMeansTest.java
 @brief Código de la clase de pruebas de la clase KMeans
 */

import clases.*;
import controladores.CtrlDominioPreprocesarDatos;
import controladores.KMeans;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/** @class KMeansTest
 * @brief Tiene las pruebas implementadas para comprobar que funcionan los métodos de la clase KMeans.
 *
 * @author Beatriz Gomes Da Costa
 *
 * @date November 2021
 */
class KMeansTest {
    KMeans kmeans = new KMeans();
    @AfterEach
    void defaultSettings(){ kmeans.setDefaults(); }

    /** @fn testComputeEuclideanDistance
     * @brief Test para comprobar el cálculo de la distancia euclidea entre usuarios

    @Test
    @DisplayName("Calcular distancia euclídea")
    void testComputeEuclideanDistance() {
        kmeans.setK(1);

        ArrayList<Centroide> c = new ArrayList<>();
        Map<Integer, Double> cData = new HashMap<>();

        cData.put(0, 4.5); cData.put(1, 2.2); cData.put(2, 0.0); cData.put(3, 1.0);
        c.add(0, new Centroide(0, cData));
        kmeans.setCentroides(c);


        Map<Integer, Double> uData1 = new HashMap<>();
        Map<Integer, Double> uData2 = new HashMap<>();
        Map<Integer, Double> uData3 = new HashMap<>();

        uData1.put(0, 3.0); uData1.put(1, 4.5); uData1.put(2, 4.0); uData1.put(3, 1.0);
        uData2.put(0, 2.5); uData2.put(1, 5.0);

        assertEquals(4.851803788283282,     kmeans.computeEuclideanDistance(uData1, 0), "Distance is not computed correctly");
        assertEquals(3.4409301068170506,    kmeans.computeEuclideanDistance(uData2, 0), "Distance for different sizes is not computed correctly");
        assertEquals(0.0,                   kmeans.computeEuclideanDistance(uData3, 0), "Distance for  is not computed correctly");
    }

    /** @fn testNearestCentroids
     * @brief Test para determinar cuál es el cluster más cercano a un usuario

    @Test
    @DisplayName("Determinar el cluster más cercano")
    void testNearestCentroids() {
        kmeans.setK(3);

        ArrayList<Centroide> centroides = new ArrayList<>();
        Map<Integer, Double> cData1 = new HashMap<>();
        Map<Integer, Double> cData2 = new HashMap<>();
        Map<Integer, Double> cData3 = new HashMap<>();

        cData1.put(0, 5.0); cData2.put(0, 2.5); cData3.put(0, 0.0);
        cData1.put(1, 5.0); cData2.put(1, 2.5); cData3.put(0, 0.0);
        cData1.put(2, 5.0); cData2.put(2, 2.5); cData3.put(0, 0.0);
        cData1.put(3, 5.0); cData2.put(3, 2.5); cData3.put(0, 0.0);

        Centroide c1 = new Centroide(0, cData1); centroides.add(c1);
        Centroide c2 = new Centroide(1, cData2); centroides.add(c2);
        Centroide c3 = new Centroide(2, cData3); centroides.add(c3);

        kmeans.setCentroides(centroides);


        Map<Integer, Double> uData1 = new HashMap<>();
        Map<Integer, Double> uData2 = new HashMap<>();
        Map<Integer, Double> uData3 = new HashMap<>();

        uData1.put(0, 4.5); uData2.put(0, 1.0); uData3.put(0, 2.0);
        uData1.put(1, 4.5); uData2.put(1, 1.0); uData3.put(1, 2.0);
        uData1.put(2, 4.5); uData2.put(2, 1.0); uData3.put(2, 2.0);
        uData1.put(3, 4.5); uData2.put(3, 1.0); uData3.put(3, 2.0);

        assertEquals(0, kmeans.nearestCentroid(uData1));
        assertEquals(2, kmeans.nearestCentroid(uData2));
        assertEquals(1, kmeans.nearestCentroid(uData3));
    }


    /** @fn testCentroidRelocation
     * @brief Test para comprobar que los centroides se relocalizan correctamente

    @Test
    @DisplayName("Relocalización de clusters")
    void testCentroidRelocation() {
        kmeans.setK(2);

        ArrayList<Centroide> centroides = new ArrayList<>();
        Map<Integer, Double> cData1 = new HashMap<>();
        Map<Integer, Double> cData2 = new HashMap<>();

        cData1.put(0, 4.5); cData1.put(1, 4.5); cData1.put(2, 4.5); cData1.put(3, 4.5);
        cData2.put(0, 1.0); cData2.put(1, 2.0); cData2.put(2, 1.5); cData2.put(3, 0.5);

        Centroide c1 = new Centroide(0, cData1); centroides.add(c1);
        Centroide c2 = new Centroide(1, cData2); centroides.add(c2);

        kmeans.setCentroides(centroides);

        ArrayList<Usuario> cluster1 = new ArrayList<>();
        Map<Integer, Double> uData1 = new HashMap<>();
        Map<Integer, Double> uData2 = new HashMap<>();
        Map<Integer, Double> uData3 = new HashMap<>();

        uData1.put(0, 4.5); uData2.put(0, 5.0); uData3.put(0, 4.0);
        uData1.put(1, 4.5); uData2.put(1, 4.0); uData3.put(1, 4.5);
        uData1.put(2, 4.5); uData2.put(2, 5.0); uData3.put(2, 3.0);
        uData1.put(3, 4.5); uData2.put(3, 4.5); uData3.put(3, 5.0);

        assertEquals(0, kmeans.nearestCentroid(uData1));
        assertEquals(0, kmeans.nearestCentroid(uData2));
        assertEquals(0, kmeans.nearestCentroid(uData3));

        Usuario f1 = new Usuario(1, uData1); cluster1.add(f1);
        Usuario f2 = new Usuario(2, uData2); cluster1.add(f2);
        Usuario f3 = new Usuario(3, uData3); cluster1.add(f3);

        kmeans.centroidRelocation(0, c1.getImaginaryUsers(), cluster1);

        centroides = kmeans.getCentroides();

        assertEquals(4.5,               centroides.get(0).getImaginaryUsers().get(0));
        assertEquals(4.333333333333333, centroides.get(0).getImaginaryUsers().get(1));
        assertEquals(4.166666666666667, centroides.get(0).getImaginaryUsers().get(2));
        assertEquals(4.666666666666667, centroides.get(0).getImaginaryUsers().get(3));

        kmeans.setDefaults();

        //Test for users that have not scored all items from centroid
        kmeans.setK(2);

        ArrayList<Centroide> centroides1 = new ArrayList<>();
        Map<Integer, Double> cData3 = new HashMap<>();
        Map<Integer, Double> cData4 = new HashMap<>();

        cData3.put(0, 4.5); cData3.put(1, 4.5); cData3.put(2, 4.5); cData3.put(3, 4.5);
        cData4.put(0, 1.0); cData4.put(1, 2.0); cData4.put(2, 1.5); cData4.put(3, 0.5);

        Centroide c3 = new Centroide(3, cData3); centroides1.add(c3);
        Centroide c4 = new Centroide(4, cData4); centroides1.add(c4);

        kmeans.setCentroides(centroides1);

        ArrayList<Usuario> cluster2 = new ArrayList<>();
        Map<Integer, Double> uData4 = new HashMap<>();
        Map<Integer, Double> uData5 = new HashMap<>();
        Map<Integer, Double> uData6 = new HashMap<>();

        uData4.put(0, 1.0); uData4.put(1, 2.0); uData4.put(2, 1.5);
        uData5.put(1, 2.5); uData5.put(3, 1.0);
        uData6.put(2, 2.0);

        assertEquals(4, kmeans.nearestCentroid(uData4));
        assertEquals(4, kmeans.nearestCentroid(uData5));
        assertEquals(4, kmeans.nearestCentroid(uData6));

        Usuario f4 = new Usuario(4, uData4); cluster2.add(f4);
        Usuario f5 = new Usuario(5, uData5); cluster2.add(f5);
        Usuario f6 = new Usuario(6, uData6); cluster2.add(f6);

        kmeans.centroidRelocation(1, c4.getImaginaryUsers(), cluster2);
        centroides = kmeans.getCentroides();

        //assertEquals(1.0,   centroides.get(1).getImaginaryUsers().get(0));
        assertEquals(2.25,  centroides.get(1).getImaginaryUsers().get(1));
        assertEquals(1.75,  centroides.get(1).getImaginaryUsers().get(2));
        assertEquals(1.0,   centroides.get(1).getImaginaryUsers().get(3));

    }


    /** @fn testRandomCentroids
     * @brief Test para comprobar que los centroides aleatorios generados al principio del algoritmo se crean correctamente

    @Test
    @DisplayName("Creación aleatoria de centroides")
    void testRandomCentroids(){
        KMeans kmeans = new KMeans();
        kmeans.setK(2);
        double maxsc = kmeans.getMaxSc(), minsc = kmeans.getMinSc();
        kmeans.randomCentroids();
        kmeans.furthestCentroids();

        ArrayList<Centroide> c = new ArrayList<>(kmeans.getCentroides());

        assertEquals(kmeans.getK(), c.size(), "Error: expected " + kmeans.getK() + " centroids, " + c.size() + " were created instead.");

        for (int i = 0; i < kmeans.getK(); i++){
            c.get(i).getImaginaryUsers().forEach((key, v) -> {
                assertTrue(v <= maxsc, "Error: Item score exceeds max score value.");
                assertTrue(v >= minsc, "Error: Item score exceeds max score value.");
            });
        }
    }


    /** @fn testAddToCluster
     * @brief Test para comprobar que la adición de un usuario a un cluster se realiza correctamente

    @Test
    @DisplayName("Añadir usuario a un cluster")
    void testAddToCluster(){
        KMeans kmeans = new KMeans();
        kmeans.setK(1);
        kmeans.randomCentroids();
        kmeans.furthestCentroids();

        Map<Integer, Double> uData1 = new HashMap<>();

        uData1.put(0, 4.5); uData1.put(0, 4.5); uData1.put(0, 4.5); uData1.put(0, 4.5);
        Usuario f = new Usuario(1, uData1);

        kmeans.addToCluster(f, 0);

        Map<Integer, ArrayList<Usuario>> clusters = new HashMap<>(kmeans.getClusterMap());
        assertEquals(1, clusters.get(0).size());

        kmeans.setDefaults();
    }


    /** @fn testShouldTerminate
     * @brief Test para comprobar que cuando dos iteraciones de kmeans seguidas computan el mismo clusterMap, se termina el algoritmo

    @Test
    @DisplayName("Terminación temprana")
    void testShouldTerminate(){
        KMeans kmeans = new KMeans();
        kmeans.setK(20);
        int n = 10;

        Usuario u;
        Map<Integer, ArrayList<Usuario>> lastState = new HashMap<>();
        Map<Integer, ArrayList<Usuario>> current = new HashMap<>();

        kmeans.randomCentroids();
        kmeans.furthestCentroids();
        Centroide c1 = kmeans.getCentroides().get(0);

        ArrayList<Usuario> u1 = new ArrayList<>();
        ArrayList<Usuario> u2 = new ArrayList<>();
        Random r = new Random();

        for(int i = 0; i < 10; i++){
            Map<Integer, Double> v1 = new HashMap<>();
            Map<Integer, Double> v2 = new HashMap<>();

            for (int j = 0; j < n; ++j){
                v1.put(j, r.nextDouble());
                v2.put(j, r.nextDouble());
            }

            u = new Usuario(i+1,v1); u1.add(i, u);
            u = new Usuario(i+1+10,v2); u2.add(i, u);
        }

        lastState.put(c1.getCentroID(), u1);
        current.put(c1.getCentroID(), u2);
        kmeans.setClusterMap(lastState);

        assertEquals(false, kmeans.shouldTerminate(current));

        lastState = new HashMap<>();
        current = new HashMap<>();
        kmeans.setClusterMap(lastState);

        lastState.put(c1.getCentroID(), u1);
        current.put(c1.getCentroID(), u1);
        assertTrue(kmeans.shouldTerminate(current));
    }


    /** @fn furthestCentroidsTest
     * @brief Test para comprobar que los clusters más cercanos son eliminados

    @Test
    @DisplayName("Eliminación de los clusters más cercanos")
    void furthestCentroidsTest() {
        KMeans kmeans = new KMeans();
        kmeans.setK(3);
        ArrayList<Centroide> centroides = new ArrayList<>();
        Map<Integer, Double> cData1 = new HashMap<>();
        Map<Integer, Double> cData2 = new HashMap<>();
        Map<Integer, Double> cData3 = new HashMap<>();
        Map<Integer, Double> cData4 = new HashMap<>();
        Map<Integer, Double> cData5 = new HashMap<>();
        Map<Integer, Double> cData6 = new HashMap<>();

        cData1.put(0, 4.5); cData1.put(1, 4.5); cData1.put(2, 4.5); cData1.put(3, 4.5);
        cData2.put(0, 1.0); cData2.put(1, 2.0); cData2.put(2, 1.5); cData2.put(3, 0.5);
        cData3.put(0, 4.5); cData3.put(1, 4.0); cData3.put(2, 5.0); cData3.put(3, 4.5);
        cData4.put(0, 1.2); cData4.put(1, 1.7); cData4.put(2, 1.6); cData4.put(3, 0.3);
        cData5.put(0, 3.0); cData5.put(1, 2.0); cData5.put(2, 2.5); cData5.put(3, 3.0);
        cData6.put(0, 2.7); cData6.put(1, 2.2); cData6.put(2, 2.4); cData6.put(3, 2.9);

        Centroide c1 = new Centroide(0, cData1); centroides.add(c1);
        Centroide c2 = new Centroide(1, cData2); centroides.add(c2);
        Centroide c3 = new Centroide(2, cData3); centroides.add(c3);
        Centroide c4 = new Centroide(3, cData4); centroides.add(c4);
        Centroide c5 = new Centroide(4, cData5); centroides.add(c5);
        Centroide c6 = new Centroide(5, cData6); centroides.add(c6);

        kmeans.setCentroides(centroides);
        kmeans.furthestCentroids();
        centroides = kmeans.getCentroides();
        assertEquals(4.5, centroides.get(0).getImaginaryUsers().get(0));
        assertEquals(1.0, centroides.get(1).getImaginaryUsers().get(0));
        assertEquals(3.0, centroides.get(2).getImaginaryUsers().get(0));
    }


    /** @fn clusterFormationTest
     * @brief Test para comprobar que la formación de clusters funciona correctamente. Es en esencia, comprobar que la totalidad del algoritmo funciona.

    @Test
    @DisplayName("Formación de clusters")
    void clusterFormationTest(){
        KMeans kmeans = new KMeans();
        kmeans.setK(20);
        kmeans.setCota(100);
        int n = 750, us = 2000;

        double minsc = kmeans.getMinSc(), maxsc = kmeans.getMaxSc();
        Usuarios v = new Usuarios();

        Random r = new Random();
        for(int userId = 0; userId < us; userId++){
            Map<Integer, Double> uData = new HashMap<>();
            for (int itemId = 0; itemId < n; itemId++){
                int odds = r.nextInt() * (5-1)+1;
                if (odds == 1) uData.put(itemId, r.nextDouble() * (maxsc - minsc) + minsc);
            }
            v.addUser(new Usuario(userId, uData));
        }

        //KMeans.clusterFormation();

        Map<Integer, ArrayList<Usuario>> res = kmeans.getClusterMap();
    }

    @Test
    @DisplayName("Computación del WSS")
    void computeWSSTest() {
        KMeans kmeans = new KMeans();
        kmeans.setK(2);

        ArrayList<Centroide> centroides = new ArrayList<>();
        Map<Integer, Double> cData1 = new HashMap<>();
        Map<Integer, Double> cData2 = new HashMap<>();

        cData1.put(0, 4.5); cData1.put(1, 4.3333333333); cData1.put(2, 4.1666666667); cData1.put(3, 4.6666666667);
        cData2.put(0, 1.0); cData2.put(1, 2.25); cData2.put(2, 1.75); cData2.put(3, 1.0);

        Centroide c1 = new Centroide(0, cData1); centroides.add(c1);
        Centroide c2 = new Centroide(1, cData2); centroides.add(c2);

        kmeans.setCentroides(centroides);
        ArrayList<Usuario> cluster1 = new ArrayList<>();
        Map<Integer, Double> uData1 = new HashMap<>();
        Map<Integer, Double> uData2 = new HashMap<>();
        Map<Integer, Double> uData3 = new HashMap<>();

        ArrayList<Usuario> cluster2 = new ArrayList<>();
        Map<Integer, Double> uData4 = new HashMap<>();
        Map<Integer, Double> uData5 = new HashMap<>();
        Map<Integer, Double> uData6 = new HashMap<>();

        uData1.put(0, 4.5); uData2.put(0, 5.0); uData3.put(0, 4.0);
        uData1.put(1, 4.5); uData2.put(1, 4.0);  uData3.put(1, 4.5);
        uData1.put(2, 4.5); uData2.put(2, 5.0); uData3.put(2, 3.0);
        uData1.put(3, 4.5); uData2.put(3, 4.5);  uData3.put(3, 5.0);

        uData4.put(0, 1.0); /* missing data  */ /* missing data  */
        /* uData4.put(1, 2.0); uData5.put(1, 2.5); /* missing data  */
        /* uData4.put(2, 1.5); /* missing data  */ /* uData6.put(2, 2.0);
        /* missing data  */ /* uData5.put(3, 1.0); /* missing data

        Usuario f1 = new Usuario(1, uData1); cluster1.add(f1);
        Usuario f2 = new Usuario(2, uData2); cluster1.add(f2);
        Usuario f3 = new Usuario(3, uData3); cluster1.add(f3);
        Usuario f4 = new Usuario(4, uData4); cluster2.add(f4);
        Usuario f5 = new Usuario(5, uData5); cluster2.add(f5);
        Usuario f6 = new Usuario(6, uData6); cluster2.add(f6);

        Map<Integer, ArrayList<Usuario>> cM = new HashMap<>();
        cM.put(0, cluster1);
        cM.put(1, cluster2);

        kmeans.setClusterMap(cM);

        double expected = 0.5416666666666667;

        assertEquals(expected, kmeans.computeAverageWSS());

    }*/

    @Test
    void testOptimalK() throws Exception {
        int n = 250, us = 400;

        double minsc = 0, maxsc = 5;
        Usuarios v = new Usuarios();
        Usuario u = new Usuario();

        Random r = new Random();

        System.out.println("generating numbers beween " + minsc + " and " + maxsc);

        for(int userId = 0; userId <= us; userId++){
            Map<Integer, Double> uData = new HashMap<>();
            for (int itemId = 0; itemId < n; itemId++){
               uData.put(itemId, r.nextDouble() * (maxsc - minsc) + minsc);
            }
            if (userId < us) v.addUser(new Usuario(userId, uData));
            else u = new Usuario(userId, uData);
        }

        Items its = new Items();
        Map<String, Atributo> ats = new HashMap<>();

        for (int i = 0; i < n; i++){
           Item it = new Item(i, ats);
           its.addItem(it);
        }

        KMeans kmeans = new KMeans(v, u, its);
        kmeans.setCota(100);
        kmeans.setMaxsc(5.0);
        kmeans.setMinsc(0.0);
        kmeans.setK(3);

        System.out.println("k before setting optimal: " + kmeans.getK() );
        kmeans.setOptimalK(v);
        System.out.println("optimal k: " + kmeans.getK() );
        kmeans.clusterFormation(v);
    }
}
