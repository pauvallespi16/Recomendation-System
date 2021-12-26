package controladores; /** @file Algoritmos.java
 @brief Código de la clase Algoritmos
 */

import clases.Item;
import clases.Items;
import clases.Usuario;
import clases.Usuarios;

import java.util.*;

/** @Class Algoritmos
 * @brief Clase de control de algoritmos, usa esta clase para controlar el uso de los diferentes algoritmos del sistema de recomendaciones.
 *
 * @author Carla Campàs Gené
 *
 * @date December 2021
 */
public class Algoritmos {
    /**
     * @var Usuarios us
     * @brief Conjunto de usuarios que vamos a usar para inicializar nuestro algoritmo de collaborative filtering.
     */
    protected Usuarios us;

    /**
     * @var Usuario u
     * @brief Usuario desde el cual vamos a hacer las predicciones de los items.
     */
    protected Usuario u;

    /** @var Map <Integer, Double> predictions
     * @brief Mapa de predicciones de los algoritmos
     */
    protected Map <Integer, Double> predictions;

    /** @var Items it
     * @brief Conjunto de items que se usaran para las valoraciones
     */
    protected Items it;

    /** @fn Algoritmos
     * @brief Constructora por defecto, objeto vacío.
     * Constructora para inicializar un objeto de la clase, pone la variable contentBased a true, por lo tanto se haran las predicciones mediante los algoritmos de collaborative filtering.
     * @pre No hay objeto de la clase algoritmos
     * @post Existe el objeto vacío de la clase algoritmos
     */
    public Algoritmos() {
        predictions = new HashMap<>();
    }

    /** @fn Algoritmos
     * @brief Constructora por defecto, inicializa la clase con un
     * @pre No hay objeto de la clase Algoritmos
     * @post Existe el objeto de la clase Algoritmos con variables globales us y u inicializadas
     * @param us: Conjunto de usuarios que usaremos para los algoritmos de recomendación
     * @param u:  Usuario al que le queremos recomendar items
     * @param it: Items de los que queremos obtener valoraciones
     */
    public Algoritmos(Usuarios us, Usuario u, Items it) {
        this();
        this.us = new Usuarios(us.getUsers());
        this.u = new Usuario(u.getUserId(), u.getValoraciones());
        this.it = it;
        for (Item i : it.getItems().values()) {
            System.out.println ("slopeone");
            System.out.println (i.getId());
        }
    }

    /** @fn calcularRecomendaciones
     * @brief para inicializar todo lo que debe calcular para sus recomendaciones
     */
    public void calcularRecomendaciones (int size) {}

    /** @fn getPredicciones()
     * @brief coger el mapa de predicciones generado por la llama a los algoritmos
     * @return devuelve las predicciones hechas por el algoritmo de predicciones
     */
    public Map <Integer, Double> getPredicciones(int k) {
        Map <Integer, Double> aux = new HashMap<>();
        for (Map.Entry <Integer, Double> e : predictions.entrySet()){
            if (k == 0) break;
            aux.put(e.getKey(), e.getValue());
            k--;
        }
        return aux;
    }
}
