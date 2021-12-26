package clases; /** @file AtributoTexto.java
 @brief Código de la clase Atributo de Texto subclase de Atributo
 */

import java.util.HashMap;
import java.util.Map;

/** @Class AtributoTexto
 * @brief La clase AtributoTexto es herencia de la clase atributo con tipo HashMap<String, Integer>.
 *
 * @author Pau Vallespí Monclús
 *
 * @date December 2021
 */
public class AtributoTexto extends Atributo<Map<String, Integer>>{
    /** @var Map<String, Integer> valorTexto
     *  @brief Map en donde las key son las palabras y el value es las veces que aparece en el texto*/
    private Map<String, Integer> valorTexto;

    /** @var int nPalabras
     *  @brief Numero de palabras que aparecen en el atributo
     */
    private int nPalabras;

    /** @fn AtributoTexto
     * @brief Constructora por defecto de la clase AtributoTexto
     *
     * @pre No hay objeto de AtributoTexto
     * @post Se ha creado el objeto de la clase AtributoTexto con la variable valorTexto inicializado al parametro.
     * @param valorTexto: atributo de la clase
     */
    public AtributoTexto(String valorTexto) {
        String [] valores = valorTexto.split("[^a-zA-Z0-9']+|'");
        this.valorTexto = new HashMap<>();
        for (String s : valores) {
            String aux = s.toLowerCase();
            nPalabras++;
            if (this.valorTexto.containsKey(aux)) {
                int n = this.valorTexto.get(aux);
                this.valorTexto.put(aux, n+1);
            }
            else if (!aux.equals("")) { this.valorTexto.put(aux, 1); }
        }
    }

    /** @fn AtributoTexto
     * @brief Constructora alternativa de la clase AtributoTexto
     *
     * @pre No hay objeto de AtributoTexto
     * @post Se ha creado el objeto de la clase AtributoTexto
     * @param valor: Atributo de la clase
     * @param nPalabras: Atributo que representa el numero de palabras
     */
    public AtributoTexto(Map<String, Integer> valor, int nPalabras) {
        this.valorTexto = new HashMap<>(valor);
        this.nPalabras = nPalabras;
    }

    /** @fn getValor
     * @brief Getter del atributo en formato HashMap<String, Integer>.
     * Hace override de la clase base con la instancia correcta de tipo y variable.
     * @pre La clase AtributoTexto tiene instancia, y la variable global valorTexto esta inicializada.
     * @post Se ha devuelto el Map<String, Integer> de la clase.
     * @return Devuelve el Map<String, Integer> que tiene la clase como variable global.
     */
    public Map<String, Integer> getValor() { return this.valorTexto; }

    /** @fn getnPalabras
     * @brief Getter del numero de palabras.
     * @pre La clase AtributoTexto tiene instancia, y la variable global nPalabras esta inicializada.
     * @post Se ha devuelto la variable nPalabras
     * @return Devuelve un int que representa el numero de palabras.
     */
    public int getnPalabras() { return this.nPalabras; }
}
