package clases; /** @file Usuario.java
 @brief Código de la clase Usuario
 */

import java.util.*;

/** @Class Usuario
 * @brief La clase de usuario contiene la implementación de un usuario con su id y sus valoraciones.
 *
 * @author Beatriz Gomes da Costa
 *
 * @date December 2021
 */

public class Usuario {
	/** @var int userId
	 *  @brief El id del usuario instanciado en la clase. */
	private int userId;

	/** @var int originalUserId
	 *  @brief El id del usuario instanciado en los archivos. */
	private int originalUserId;

	/** @var Map <Integer, Double> valoraciones
	 * @brief Variable valoraciones contiene todas las valoraciones que ha hecho el item
	 * @format Map <ItemId, Score>
	 */
	private Map <Integer, Double> valoraciones;

	/** @fn Usuario
	 * @brief Constructora por defecto, inicializa un usuario vacío.
	 * @pre No hay objeto usuario.
	 * @post Se ha creado un objeto de usuario vacío.
	 */
	public Usuario(){}

	/** @fn Usuario
	 * @brief Constructora por defecto, inicializa un usuario con id y valoraciones.
	 * @pre No hay objeto usuario.
	 * @post Se ha creado un objeto de usuario con userId y valoraciones instanciadas.
	 * @param userId: id del usuario generado por preproceso
	 * @param valoraciones: valoraciones que ya ha hecho el usuario antes de empezar esta session.
	 */
	public Usuario (Integer userId, Map <Integer, Double> valoraciones){
		this.userId = userId;
		this.valoraciones = new HashMap<>(valoraciones);
	}

	/** @fn equals
	 * @brief Comprueba la igualdad entre los dos objetos de la clase Usuario.
	 * @pre Se ha instanciado la clase usuario tanto el usuario que lo llama y el usuario que pasa como parámetro.
	 * @post Se ha devuelto true si los dos usuarios tienen el mismo id y si no false.
	 * @param o: Objeto de usuario con el que queremos comprobar igualdad
	 * @return true si los dos objetos de usuario tienen el mismo id, false de otra manera.
	 */
	@Override
	public boolean equals(Object o) {
		Usuario us = (Usuario) o;
		return this.userId == us.userId;
	}

	/** @fn getValoraciones
	 * @brief Getter del mapa de valoraciones
	 * @pre -
	 * @post Se ha devuelto el mapa de valoraciones.
	 * @return Devuelve el mapa de valoraciones.
	 */
	public Map <Integer, Double> getValoraciones() { return this.valoraciones; }

	/** @fn getUserId
	 * @brief Getter del id del usuario.
	 * @pre -
	 * @post Se ha devuelto el id del objeto de usuario.
	 * @return Devuelve el id del objeto del usuario.
	 */
	public Integer getUserId() { return this.userId; }

	/** @fn getUserId
	 * @brief Getter del id original del usuario.
	 * @pre -
	 * @post Se ha devuelto el id del objeto de usuario.
	 * @return Devuelve el id original del objeto del usuario.
	 */
	public Integer getOriginalUserId() { return this.originalUserId; }

	/** @fn setValoraciones
	 * @brief Setter del mapa de valoraciones
	 * @pre El mapa de valoraciones no esta inicializado o esta inicializado a un mapa distinto.
	 * @post El atributo v que contiene el conjunto de valoraciones que ha hecho el usuario es el conjunto de valoraciones v.
	 * @param v: mapa de valoraciones del usuario
	 */
	public void setValoraciones(Map <Integer, Double> v) { this.valoraciones = new HashMap<>(v); }

	/** @fn setOriginalUserId
	 * @brief Setter del originalUserId
	 * @pre originalUserId no vacío
	 * @post Usuario contiene originalUserId
	 * @param originalUserId: id del usuario original
	 */
	public void setOriginalUserId(int originalUserId) { this.originalUserId = originalUserId; }

	/** @fn addValoracion
	 * @brief Añadir una valoración al mapa de valoraciones del usuario.
	 * @pre -
	 * @post El mapa de valoraciones pasa a tener el entry {id, v}
	 * @param id: id del item que el usuario ha valorado
	 * @param v: score de la
	 */
	public void addValoracion(Integer id, Double v) { valoraciones.put(id, v); }

	/** @fn setValoraciones
	 * @brief Comprobar si el mapa de las valoraciones tiene el id "idItem"
	 * @pre -
	 * @post Ha devuelto true si el usuario ha valorado el item y si no false
	 * @param idItem: id del item del que queremos comprobar si el usuario ha hecho una valoración
	 * @return true si el usuario tiene una valoración para el item idItem
	 */
	public boolean tieneValoracion (Integer idItem) { return valoraciones.containsKey(idItem); }

	/** @fn getValoracio
	 * @brief Coger la valoración del item con id "item"
	 * @pre El mapa de valoraciones tiene una valoración con el item id "item"
	 * @post Ha devuelto la valoración del item para el usuario instanciado
	 * @param item: id del item del que queremos conseguir la valoración.
	 * @return devuelve la valoración del item
	 */
	public double getValoracio (Integer item) {  return valoraciones.get(item).doubleValue(); }

	/** @fn borrarValoracion
	 * @brief quitar una valoracion hecha por el usuario
	 * @pre La clase usuario ha sido inicializada
	 * @post Si existe, se borra la valoración
	 * @param itemID: id del item que queremos borrar
	 */
	public void borrarValoracion (Integer itemID) {
		if (getValoraciones().containsKey(itemID)){
			valoraciones.remove(itemID);
		}
	}

	/** @fn toString
	 * @brief Hace override de la funcion toString
	 * @pre -
	 * @post El item se ha traducido a String para poder imprimirse
	 * @return Devuelve el item traducido a String
	 */
	@Override
	public String toString() {
		String valor = "";
		valor += originalUserId + "\n";
		for (Map.Entry<Integer, Double> entry : valoraciones.entrySet())
			valor += entry.getKey() + " " + entry.getValue() + "\n";
		return valor;
	}
}
