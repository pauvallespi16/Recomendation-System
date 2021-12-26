package clases; /** @file Item.java
 @brief Código de la clase Item
 */

import java.util.*;

/** @class Item
 * @brief Implementación de los Items que leemos de los data sets
 *
 * @author Pau Vallespí Monclús
 *
 * @date December 2021
 */
public class Item {
	/** @var int id
	 *  @brief El id del item instanciado en la clase. */
	private int id;

	/** @var int originalId
	 *  @brief El id del item instanciado en los archivos. */
	private int originalId;

	/** @var Map<String, Atributo> atributos
	 * @brief Variable atributos contiene todos los atributos de un item
	 * @format Map <header, inforamción>
	 */
	private Map<String, Atributo> atributos;

	/** @fn Item
	 * @brief Constructora por defecto, inicializa un item vacío.
	 * @pre No hay objeto item.
	 * @post Se ha creado un objeto de item vacío.
	 */
	public Item(){}

	/** @fn Item
	 * @brief Constructora por defecto, inicializa un item con id y atributos.
	 * @pre No hay objeto item.
	 * @post Se ha creado un objeto de item con id y atributos instanciados.
	 * @param id: id del item generado por preproceso
	 * @param atributos: atributos del item que han sido leidas sobre el item durante el preproceso.
	 */
	public Item (int id, Map<String, Atributo> atributos) {
		this.id = id;
		this.atributos = new HashMap<>(atributos);
	}

	/** @fn equals
	 * @brief Comprueba la igualdad entre los dos objetos de la clase Item.
	 * @pre Se ha instanciado la clase item tanto el item que lo llama y el item que pasa como parámetro.
	 * @post Se ha devuelto true si los dos items tienen el mismo id y si no false.
	 * @param o: Otra instancia de la clase items que queremos comprobar que sea igual que el ítem que llama la función.
	 * @return true si los dos objetos de item tienen el mismo id, false de otra manera.
	 */
	@Override
	public boolean equals(Object o) {
		Item i = (Item) o;
		return this.id == i.id;
	}

	/** @fn getId
	 * @brief Getter del id del item.
	 * @pre -
	 * @post Se ha devuelto el id del objeto de item.
	 * @return Devuelve el id del objeto del item.
	 */
	public Integer getId() { return id; }

	/** @fn getOriginalId
	 * @brief Getter del id original del item.
	 * @pre -
	 * @post Se ha devuelto el id original del objeto de item.
	 * @return Devuelve el id original del objeto del item.
	 */
	public Integer getOriginalId () { return originalId; }

	/** @fn getAtributos
	 * @brief Getter del mapa de atributos
	 * @pre -
	 * @post Se ha devuelto el mapa de atributos.
	 * @return Devuelve el mapa de atributos.
	 */
	public Map<String, Atributo> getAtributos() { return atributos; }

	/** @fn getAtributo
	 * @brief Coger el atributo del item con header "atr"
	 * @pre El mapa de atributo tiene una atributo con el header "atr"
	 * @post Ha devuelto el valor del atributo del item para el atributo con header "atr"
	 * @param atr: nombre del header del atributo al que queremos acceder
	 * @return devuelve el valor del atributo del item para el atributo con header "atr"
	 */
	public Atributo getAtributo(String atr) { return atributos.get(atr); }

	/** @fn setOriginalId
	 * @brief Setter del originalId
	 * @pre OriginalId no vacío
	 * @post El originalId de ítem pasa a ser igual al parámetro que le pasamos al método.
	 * @param originalId: id del ítem original
	 */
	public void setOriginalId(int originalId) { this.originalId = originalId; }

	/** @fn setAtributos
	 * @brief Setter del mapa de atributos
	 * @pre El mapa de atributos no esta inicializado o esta inicializado a un mapa distinto.
	 * @post La variable global atributos que contiene el conjunto de atributos que ha hecho el usuario es el conjunto de atributos "atributos".
	 * @param atributos: mapa de atributos del item
	 */
	public void setAtributos(Map<String, Atributo> atributos) { this.atributos = new HashMap<>(atributos); }

	/** @fn setAtributo
	 * @brief Añadir un atributo al mapa de atributos del usuario.
	 * @pre -
	 * @post El mapa de atributos pasa a tener el entry {str, atr}
	 * @param str: el header del atributo que queremos añadir
	 * @param atr: valor de ese atributo para añadir al mapa de atributos
	 */
	public void setAtributo(String str, Atributo atr) { this.atributos.put(str, atr); }

	/** @fn toString
	 * @brief Hace override de la funcion toString
	 * @pre -
	 * @post El item se ha traducido a String para poder imprimirse
	 * @return Devuelve el item traducido a String
	 */
	@Override
	public String toString() {
		String valor = "";
		for (Map.Entry<String, Atributo> entry : atributos.entrySet()) {
			valor += entry.getKey() + "|" + entry.getValue().getClass().getSimpleName() + "|" +  entry.getValue().getValor();

			if (entry.getValue() instanceof AtributoNumerico) valor += "|" + ((AtributoNumerico) entry.getValue()).getValorNormalizado();
			if (entry.getValue() instanceof AtributoTexto) valor += "|" + ((AtributoTexto) entry.getValue()).getnPalabras();
			if (entry.getValue() instanceof AtributoFecha) valor += "|" + ((AtributoFecha) entry.getValue()).getValorNormalizado();
			if (entry.getValue() instanceof AtributoCategoricoMultiple) valor += "|" + ((AtributoCategoricoMultiple )entry.getValue()).getBitSet();

			valor += "\n";
		}
		return valor;
	}
}
