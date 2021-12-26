package clases; /** @file Centroide.java
 @brief Código de la clase Centroide
 */


import java.util.*;

/**
 * @Class Centroide
 * @brief La clase de centroide contiene la implementación de un centroide con su identificador único y sus valoraciones a todos los items en el sistema.
 *
 * @author Beatriz Gomes da Costa
 *
 * @date December 2021
 */

public class Centroide {
    /**
     * @brief Entero positivo identificador del centroide
     * @var identificador del Centroide
     */
    private int centroID;

    /**
     * @brief Mapa de valoraciones a todos los items en el sistema
     * @var mapa de valoraciones por item del centroide
     * @format Map<itemId, score>
     */
    private Map<Integer, Double> imaginaryUsers;
    //centroide (usuario imaginario respecto al cual determinamos los usuarios reales más cercanos a este (KMeans))

    /**
     * @fn Centroide
     * @brief Constructora vacía de la clase centroide
     * @pre El centroide no existe
     * @pos Se ha creado un centroide sin identificador y sin las valoraciones inicializadas
     */
    public Centroide(){}

    /**
     * @fn Centroide
     * @brief Constructora de la clase centroide
     * @pre El centroide no existe
     * @post Se ha creado un centroide con sus atributos inicializados a los valores pasados por parámetro
     * @param centroID Identificador del centroide. Número natural positivo
     * @param imaginaryUsers mapa de valoraciones por item del centroide
     */
    public Centroide(int centroID, Map<Integer, Double> imaginaryUsers){
        this.centroID = centroID;
        this.imaginaryUsers = imaginaryUsers;
    }

    /**
     * @fn getCentroID
     * @brief getter del identificador del centroide
     * @pre
     * @post se ha devuelto el identificador de este centroide
     * @return Devuelve el identificador de este centroide
     */
    public int getCentroID() {return centroID;}

    /**
     * @fn setCentroID
     * @brief setter del atributo centroID de la clase Centroide
     * @pre el objeto tenia o bien un identificador, o no se le había asignado uno, y el centroID deberá seguir siendo un valor único
     * @post se ha cambiado o asignado el identificador pasado por parámetro a este Centroide
     * @param centroID nuevo identificador que pasará a representar este Centroide
     */
    public void setCentroID(int centroID){ this.centroID = centroID;}
    /**
     * @fn getImaginaryUsers
     * @brief getter del mapa de valoraciones del centroide
     * @pre
     * @post se ha devuelto el mapa de valoraciones a todos los items en el sistema
     * @return Devuelve el mapa de valoraciones a los items de este centroide
     */
    public Map<Integer, Double> getImaginaryUsers() { return imaginaryUsers; }
}
