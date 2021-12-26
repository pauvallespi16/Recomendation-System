package clases; /** @file AtributoBoolean.java
 @brief Código de la clase Atributo Categórico Multiple subclase de Atributo
 */


import java.util.Set;
import java.util.TreeSet;
import java.util.BitSet;

/** @Class AtributoCategoricoMultiple
 * @brief La clase AtributoCategoricoMultiple es herencia de la clase atributo con tipo Set<String>.
 *
 * Implementa la clase atributo con un set de strings, de esta forma podemos guardar atributos como serian géneros.
 *
 * @author Pau Vallespí Monclús
 *
 * @date December 2021
 */
public class AtributoCategoricoMultiple extends Atributo<Set<String>> {
    /** @var Set<String> valorArrayString
     *  @brief El atributo en formato de set ordenado de strings (evita repeticiones).*/
    private Set<String> valorArrayString;

    /** @var BitSet bitSet
     *  @brief El atributo en formato de bitset.*/
    private BitSet bitSet;

    /** @fn AtributoCategoricoMultiple
     * @brief Constructora por defecto de la clase AtributoCategoricoMultiple
     *
     * Pasa el valor del set como un string dividido por el carácter ";".
     *
     * @pre No hay objeto de AtributoCategoricoMultiple
     * @post Se ha creado el objeto de la clase AtributoCategoricoMultiple con la variable valorArrayString inicializada al set del string que nos pasan.
     * @param valorArrayString: Se pasa los valores del set en formato de string separando los atributos con ";"
     */
    public AtributoCategoricoMultiple(String valorArrayString) {
        this.valorArrayString = new TreeSet<>(Set.of(valorArrayString.split(";")));
        this.bitSet = new BitSet();
    }

    /** @fn getValor
     * @brief Getter del set.
     * Hace override de la clase base con la instancia correcta de tipo y variable.
     * @pre La clase AtributoCategoricoMultiple tiene instancia, y la variable global valorArrayString esta inicializada.
     * @post Se ha devuelto el set de strings de la clase.
     * @return Devuelve el set de strings que tiene la clase como variable global.
     */
    public Set<String> getValor() { return valorArrayString; }

    /** @fn getBitSet
     * @brief Getter del BitSet.
     * @pre La clase AtributoCategoricoMultiple tiene instancia, y la variable global bitSet esta inicializada.
     * @post Se ha devuelto el bitSet de la clase.
     * @return Devuelve el bitSet que tiene la clase como variable global.
     */
    public BitSet getBitSet() { return this.bitSet; }

    /** @fn setBitSetValue
     * @brief Modifica a cierto el bit con id bitIndex del BitSet.
     * @pre La clase AtributoCategoricoMultiple tiene instancia, y la variable global bitSet esta inicializada.
     * @post Se ha modificado a cierto el bit en la posición bitIndex del bitSet de la clase.
     * @param bitIndex: Índice que queremos poner a cierto
     */
    public void setBitSetValue(int bitIndex) { this.bitSet.set(bitIndex); }

    /** @fn setBitSetSize
     * @brief Modifica el tamaño del bitSet
     * @pre -
     * @post Se ha modificado el tamaño del bitSet.
     * @param size: Tamaño del bitSet
     */
    public void setBitSetSize(int size) { this.bitSet = new BitSet(size); }
}
