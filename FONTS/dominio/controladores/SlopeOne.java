package controladores; /** @file SlopeOne.java
 @brief Codigo de la clase Slope One
 */

import clases.Items;
import clases.Usuario;
import clases.Usuarios;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/** @class Slope One
 * @brief Implementación del algoritmo slope one, usado en Collaborative Filtering después de KMeans.
 *
 * Slope One tiene en cuenta la información de otros usuarios que han valorado el mismo item y los diferentes items que
 * han valorado los distintos usuarios para crear una predicción de la valoración que le dara nuestro usuario.
 *
 * Usaremos la siguiente versión de la fórmula de predicción para el Slope One:
 * P(u) = (1/|Rj|)*∑dev_(i,j)
 *
 * Donde dev_(i,j) sigue la siguiente fórmula:
 * dev_(i,j) = ∑((uj - ui)/card(S_(i,j)(x))
 *
 * Para más información: https://www.cs.upc.edu/prop/data/uploads/info-recomanadors.pdf
 *
 * @author Carla Campàs Gené
 * @date December 2021
 */
public class SlopeOne extends Algoritmos {
	/** @var Map <Integer, Map <Integer, Double> > diff
	 * @brief Mapa de desviaciones para todos los usuarios formato
	 * @format Map <ItemId, Map <ItemId, Score>>
	 */
	private Map <Integer, Map <Integer, Double> > diff = new HashMap<>();

	/** @var Map <Integer, Map <Integer, Integer> > freq
	 * @brief cuantos usuarios han valorado los items
	 *
	 * De esta manera contamos los cardinales de la fórmula de cálculo de predicciones.
	 * @format Map <ItemId, Map <ItemId, Frequencia>>
	 */
	private Map <Integer, Map <Integer, Integer> > freq = new HashMap<>();

	/** @var Map <Integer, Double> pred
	 * @brief Predicciones de la puntuación que le daria un usuario a un item
	 */
	private Map <Integer, Double> pred = new HashMap<>();

	/** @var double mediana
	 * @brief Mediana de predicción de las valoraciones del usuario u
	 */
	private double mediana = 0.0;

	/** @fn SlopeOne
	 * @brief Constructora por defecto
	 * Constructora para inicializar un objeto de la clase, como los atributos son static no sera necesario.
	 *
	 * @pre -
	 * @post se inicializa un objeto de la clase vacío
	 */
    public SlopeOne(){}

	public SlopeOne(Usuarios us, Usuario u, Items it){
		super (us, u, it);
	}

	/** @fn setUsuarios
	 * @brief Setter de cluster de usuarios
	 * @pre El conjunto de usuarios no esta inicializado o esta inicializado a un conjunto de usuarios distinto.
	 * @post El atributo us que contiene el conjunto de usuarios del cual se basa el algoritmo es el conjunto de usuarios users.
	 * @param users: conjunto de usuarios desde el cual vamos a predecir los ratings del usuario.
	 */
	public void setUsuarios (Usuarios users) {
		super.us = users;
		/*for (Usuario u : us.getUsers()){
			System.out.print (u.getUserId() + ": ");
			System.out.println (u.getValoraciones());
		}
		System.out.println();*/
	}

	/** @fn setUser
	 * @brief Setter del usuario desde el cual ejecutaremos el Slope One
	 * También inicializa la mediana.
	 *
	 * @pre No hay usuario identificado en el slope one o esta identificado un usuario distinto.
	 * @post El usuario de la clase Slope One es user i la variable mediana se ha inicializado con la mediana de las valoraciones del usuario.
	 * @param user: usuario del cual queremos sacar las predicciones.
	 * @see calcularMediana()
	 */
	public void setUser (Usuario user) {
		super.u = user;
		calcularMediana();
	}

	public double getMediana() { return mediana; }

	/** @fn getPredictionOnItem
	 * @brief Coger la predicción de un Item a partir de su id
	 * @pre -
	 * @post Se ha devuelto la predicción
	 * @param item: el id del item para el cual queremos una
	 * @return Si el usuario ya ha valorado el item se devolverá esta valoración, si no se devolver -1 si no se puede conseguir valoración de este item y la predicción si se puede.
	 */
	public double getPredictionOnItem (Integer item){
		if (u.tieneValoracion(item)) return u.getValoracio(item);

		if (!pred.containsKey(item)) return -1.0;
		return pred.get(item);
	}

	/** @fn getAllPredictions
	 * @brief Coger la predicción de todos los items
	 * @pre -
	 * @post se ha devuelto el mapa de predicciones
	 * @return se devuelve el mapa de predicciones, un mapa vacío si este no esta inicializado.
	 */
	public Map <Integer, Double> getAllPredictions() {
		return pred;
	}

	/** @fn getDiff
	 * @brief Getter de el mapa de desviaciones
	 * @pre -
	 * @post Se ha devuelto el mapa de desviaciones.
	 * @return Devuelve el mapa de desviaciones.
	 */
	public Map <Integer, Map <Integer, Double> > getDiff() {
		return diff;
	}

	/** @fn getFreq
	 * @brief Getter de el mapa de frecuencias
	 * @pre -
	 * @post Se ha devuelto el mapa de frecuencias.
	 * @return Devuelve el mapa de frecuencias.
	 */
	public Map <Integer, Map <Integer, Integer> > getFreq() {
		return freq;
	}

	/** @fn getPred
	 * @brief Getter del mapa de predicciones
	 * @pre -
	 * @post Se ha devuelto el mapa de predicciones.
	 * @return Devuelve el mapa de predicciones.
	 */
	public Map <Integer, Double> getPred () {
		return pred;
	}

	/** @fn calcularMediana
	 * @brief Calcular mediana del usuario, se calcula automáticamente cuando se introduce un usuario
	 * @pre Se ha introducido un usuario como usuario base para las predicciones.
	 * @post Se ha inicializado el valor de la variable de clase mediana a l
	 */
	private void calcularMediana () {
		for (Double e : u.getValoraciones().values()){
			mediana += e.doubleValue();
		}
		mediana /= u.getValoraciones().size();
		//System.out.println("MEDIANA" + mediana);
	}

	/** @fn computarDesviacion
	 * @brief Crear el mapa de desviaciones
	 *
	 * Cogemos la diferencia entre cada usuario que tiene valoración para dos items i, j.
	 * Añadimos la desviación al mapa de diff y añadimos a la frecuencia de usuarios que han valorado i y j.
	 *
	 * PSEUDOCÓDIGO:
	 * for every item i
	 * 				for every other item j
	 * 					for every user u expressing preference for both i and j
	 * 						add the difference in u’s preference for i and j to an average
	 *
	 * @pre Se han inicializado las variables u y us a un usuario y un conjunto de usuarios respectivamente.
	 * @post Se han calculado las desviaciones entre todos los items que un mismo usuario haya valorado.
	 */
	private void computarDesviacion() {
		diff = new HashMap<>();
		freq = new HashMap<>();

		for (Usuario u1 : us.getUsers()){
			for (Entry <Integer, Double> e : u1.getValoraciones().entrySet()){
				if (!diff.containsKey(e.getKey())){
					diff.put(e.getKey(), new HashMap<>());
					freq.put(e.getKey(), new HashMap<>());
				}
				for (Entry <Integer, Double> e2 : u1.getValoraciones().entrySet()){
					int oldCount = 0;
					if (freq.get(e.getKey()).containsKey(e2.getKey())){
						oldCount = freq.get(e.getKey()).get(e2.getKey()).intValue();
					}
					double oldDiff = 0.0;
					if (diff.get(e.getKey()).containsKey(e2.getKey())){
						oldDiff = diff.get(e.getKey()).get(e2.getKey()).doubleValue();
					}
					double obsDiff = e.getValue() - e2.getValue();
					freq.get(e.getKey()).put(e2.getKey(), oldCount + 1);
					diff.get(e.getKey()).put(e2.getKey(), oldDiff + obsDiff);
				}
			}
		}

		for (Integer j : diff.keySet()){
			for (Integer i : diff.get(j).keySet()){
				double oldValue = diff.get(j).get(i).doubleValue();
				int count = freq.get(j).get(i).intValue();
				diff.get(j).put(i, oldValue/count);
			}
		}
	}

	/** @fn computarPredicciones
	 * @brief Calcular las predicciones para el usuario u
	 *
	 * A partir del mapa de desviación y frequencia podemos calcular las valoraciones que hara el usuario.
	 * Cogeremos las diferencias de todo j e i donde i ha sido valorado por nuestro usuario, y la añadimos a la mediana
	 * de valoraciones de nuestro usuario la podemos predecir el valor que le dara nuestro usuario al item j.
	 *
	 * PSEUDOCÓDIGO:
	 * for every item i the user u expresses no preference for
	 * 		for every item j that user u expresses a preference for
	 * 			find the average preference difference between j and i
	 * 				add this diff to u’s preference value for j
	 * 	return the top items, ranked by these averages
	 *
	 * @pre Se han inicializado las variable u, us, diff y freq de tal manera que podamos computar las predicciones. Para inicializar a diff y freq se tiene que llamar a computarPredicciones.
	 * @post Se ha inicializado el mapa de predicciones de manera que este contiene todas las predicciones que podemos hacer de un usuario base.
	 * @see computarPredicciones()
	 */

	private void computarPredicciones (){
		Map<Integer, Double> pAux = new HashMap<>();
		Map<Integer, Integer> fAux = new HashMap<>();

		for (Integer i : diff.keySet()){
			fAux.put(i, 0);
			pAux.put(i, 0.0);
		}

		//for (Usuario u1 : us.getUsers()){
		for (Integer j : u.getValoraciones().keySet()){
			for (Integer i : diff.keySet()){
				try {
					double predVal = diff.get(i).get(j).doubleValue() + u.getValoracio(j);
					double finVal = predVal * freq.get(i).get(j).intValue();
					pAux.put(i, pAux.get(i) + finVal);
					fAux.put(i, fAux.get(i) + freq.get(i).get(j).intValue());
				}
				catch (NullPointerException e){}
			}
		}
		for (Integer j : pAux.keySet()) {
			if (super.it.containsItem(j) && fAux.get(j) > 0) {
				pred.put(j, pAux.get(j).doubleValue() / fAux.get(j).intValue());
			}
		}
	}

	/** @fn printDiff
	 * @brief Imprimir mapa de desviaciones
	 * @pre -
	 * @post El mapa de desviaciones ha sido imprimido por el output estandard
	 * @format "ITEM: " + i + "(" + j + "," + desviación en j + ")" + ... (para toda i, j)
	 */
	public void printDiff () {
		for (Map.Entry<Integer, Map <Integer, Double>> e : diff.entrySet()) {
			System.out.print("Item " + e.getKey() + ": ");
			for (Map.Entry<Integer, Double> e1 : e.getValue().entrySet()){
				System.out.print("(" + e1.getKey() + "," + e1.getValue() + ")");
			}
			System.out.println();
		}
	}

	/** @fn printFreq
	 * @brief Imprimir mapa de frecuencias
	 * @pre -
	 * @post El mapa de frequencias ha sido imprimido por el output estandard
	 * @format "ITEM: " + i + "(" + j + "," + frecuencias en j + ")" + ... (para toda i, j)
	 */
	public void printFreq () {
		for (Map.Entry<Integer, Map <Integer, Integer>> e : freq.entrySet()) {
			System.out.print("Item " + e.getKey() + ": ");
			for (Map.Entry<Integer, Integer> e1 : e.getValue().entrySet()){
				System.out.print("(" + e1.getKey() + "," + e1.getValue() + ")");
			}
			System.out.println();
		}
	}

	/** @fn printPred
	 * @brief Imprimir mapa de predicciones
	 * @pre -
	 * @post El mapa de predicciones ha sido imprimido por el output estandard
	 * @format "PREDICTION ITEM " + itemID + " IS " + predictedValue
	 */
	public void printPred () {
		for (Map.Entry<Integer, Double> p : pred.entrySet()){
			System.out.println ("PREDICTION ITEM " + p.getKey() + " IS " + p.getValue());
		}System.out.println();
	}

	/** @fn calcularRecomendaciones
	 * @brief para inicializar todo lo que debe calcular para sus recomendaciones
	 * @pre existe clase de slope one
	 * @post se ha ejecutado la clase de nearest neighbors y puesto el vector de predicciones a las predicciones
	 */
	public void calcularRecomendaciones (int size) {
		computarDesviacion();
		computarPredicciones();
		Map <Integer, Double> orderedItems = new HashMap<>();

		pred.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> orderedItems.put(x.getKey(), x.getValue()));

		super.predictions = orderedItems;
	}
}

/**
 * control persistencia
 * control dimino
 */
