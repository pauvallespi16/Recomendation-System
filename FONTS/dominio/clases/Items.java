package clases; /** @file Items.java
 @brief Código de la clase Items
 */

import java.util.*;

/** @class Items
 * @brief La clase de items contiene la implementación de un conjunto de items.
 *
 * @author Andres Eduardo Bercowsky Rama
 *
 * @date December 2021
 */
public class Items {
	/** @var Map<Integer, Item> items
	 *  @brief Conjunto de objetos de la clase de item que componen el conjunto de items. */
	private Map<Integer, Item> items;

	/** @fn Items
	 * @brief Constructora por defecto, inicializa un conjunto de items vacío.
	 * @pre No hay objeto items.
	 * @post Se ha creado un objeto de items vacío.
	 */
	public Items() {
		items = new HashMap<>();
	}

	/** @fn Items
	 * @brief Constructora por defecto, inicializa un conjunto de items con un ArrayList de item.
	 * @pre No hay objeto items.
	 * @post Se ha creado un objeto de items con la variable items de la clase inicializada.
	 * @param items: Mapa de items que componen el conjunto.
	 */
	public Items(Map<Integer, Item> items) {
		this.items = new HashMap<>(items);
	}

	/** @fn Items
	 * @brief Constructora por defecto, inicializa un conjunto de items con un ArrayList de item.
	 * @pre No hay objeto items.
	 * @post Se ha creado un objeto de items con la variable items de la clase inicializada.
	 * @param items: ArrayList de items que componen el conjunto.
	 */
	public Items(ArrayList<Item> items) {
		Map <Integer, Item> aux = new HashMap<>();
		for (Item i : items) aux.put(i.getId(), i);
		this.items = aux;
	}

	/** @fn getItems
	 * @brief Getter del ArrayList de items
	 * @pre -
	 * @post Se ha devuelto el ArrayList de items.
	 * @return Devuelve el ArrayList de items.
	 */
	public Map<Integer, Item> getItems() { return this.items; }

	/** @fn getSize
	 * @brief Getter del tamaño del ArrayList de items
	 * @pre -
	 * @post Se ha devuelto el tamaño del ArrayList de items
	 * @return El tamaño del ArrayList de items (cuantos items hay en el conjunto)
	 * */
	public int getSize() { return items.size(); }

	/** @fn getItem
	 * @brief Getter de un item con id "id"
	 * @pre El ArrayList de item tiene el id "id"
	 * @post Ha devuelto el item con id "id"
	 * @param id: id del item del que queremos conseguir el objeto.
	 * @return devuelve el objeto de item con id "id"
	 */
	public Item getItem (int id) { return items.get(id); }

	public int findMaxId () {
		int maxID = -1;
		for (Integer i : this.items.keySet()){
			maxID = Math.max(maxID, i);
		}
		return maxID;
	}
	/** @fn setItems
	 * @brief Setter del ArrayList de items
	 * @pre El ArrayList de items no esta inicializado o esta inicializado a un ArrayList distinto.
	 * @post El atributo items de la instancia de la clase es una copia del parametro items.
	 * @param items: Map de los items del conjunto
	 */
	public void setItems(Map<Integer, Item> items) {
		this.items = new HashMap<>(items);
		findMaxId();
	}
	/** @fn setItems
	 * @brief Setter del ArrayList de items
	 * @pre El ArrayList de items no esta inicializado o esta inicializado a un ArrayList distinto.
	 * @post El atributo items de la instancia de la clase es una copia del parametro items.
	 * @param items: ArrayList de los items del conjunto
	 */
	public void setItems(ArrayList<Item> items) {
		Map <Integer, Item> aux = new HashMap<>();
		for (Item i : items) aux.put(i.getId(), i);
		this.items = aux;
	}

	/** @fn setItem
	 * @brief Cambiar el objeto de un item en el ArrayList
	 * @pre El ArrayList de items tiene un item en la posición id.
	 * @post El ArrayList de item tiene un entry {item id, item}
	 * @param id: id de la posición del ArrayList que queremos cambiar, equivalente al id preprocesado del ítem.
	 * @param item: item al que queremos cambiar en el ArrayList
	 */
	public void setItem(int id, Item item) {
		items.put(id, item);
		findMaxId();
	}

	/** @fn addItem
	 * @brief Añadir el objeto de item en el ArrayList
	 * @pre El id del item que pasamos como parámetro ya esta instanciado en el objeto.
	 * @post El ArrayList de items tiene un nuevo entry del item item
	 * @param item: item que queremos meter en el ArrayList
	 */
	public void addItem(Item item) { items.put(findMaxId()+1, item); }

	public void addItem(Item item, int id) { items.put(id, item); }

	/** @fn removeItem
	 * @brief Eliminar item en el ArrayList
	 * @pre El item que pasamos como parámetro ya esta instanciado en el objeto.
	 * @post El ArrayList ya no tiene el item pasado
	 * @param item: item que queremos meter en el ArrayList
	 */
	public void removeItem(Item item) { items.remove(item.getId()); }

	/** @fn removeItem
	 * @brief Eliminar item en el ArrayList
	 * @pre El id del item que pasamos como parámetro ya esta instanciado en el objeto.
	 * @post El ArrayList ya no tiene el item con id equivalente al id pasado
	 * @param item: id del item que queremos meter en el ArrayList
	 */
	public void removeItem(Integer item) { items.remove(item); }

	/** @fn containsItem
	 * @brief Comprobar si el ArrayList items tiene el item con el id dado como parámetro
	 * @pre -
	 * @post Ha devuelto true si el objeto tiene un item con id "id", falso si no.
	 * @param id: id del ítem del que queremos comprobar si está en el conjunto de items
	 * @return true si el conjunto tiene al item con "id", falso si no.
	 */
	public boolean containsItem(int id) {
		try { items.get(id); return true; }
		catch(Exception IndexOutOfBoundsException) { return false; }
	}

	/** @fn toString
	 * @brief Hace override de la funcion toString
	 * @pre -
	 * @post Los items se ha traducido a String para poder imprimirse
	 * @return Devuelve los items traducido a String
	 */
	@Override
	public String toString() {
		String valor = "";
		Map<String, Integer> bitsTable = Parser.getBitsTable();
		valor += "BITSTABLE\n";
		for (Map.Entry<String, Integer> entry : bitsTable.entrySet())
			valor += entry.getKey() +";"+entry.getValue() + "\n";
		valor += "NORMITEMS\n";
		Double[][] normItems = Parser.getAuxNormalizeNumeros();
		for (int i = 0; i < normItems.length; i++)
			valor += normItems[i][0] + ";" + normItems[i][1] + "\n";
		valor += "NORMFECHAS\n";
		Long[][] normFechas = Parser.getAuxNormalizeFechas();
		for (int i = 0; i < normFechas.length; i++)
			valor += normFechas[i][0] + ";" + normFechas[i][1] + "\n";
		valor += "ITEMS\n";
		for (Map.Entry<Integer, Item> entry : items.entrySet())
			valor += entry.getValue().toString() + "#\n" ;

		return valor;
	}

}
