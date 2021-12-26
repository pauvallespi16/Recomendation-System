package clases; /** @file AtributoCategorico.java
 @brief Código de la clase Atributo Categorico subclase de Atributo
 */

/** @Class AtributoCategorico
 * @brief La clase AtributoCategorico es herencia de la clase atributo con tipo String.
 *
 * @author Pau Vallespí Monclús
 *
 * @date December 2021
 */
public class AtributoCategorico extends Atributo<String> {
    /** @var String valorString
     *  @brief El valor del atributo en formato string*/
    private String valorString;

    /** @fn AtributoCategorico
     * @brief Constructora por defecto de la clase AtributoCategorico
     *
     * @pre No hay objeto de AtributoCategorico
     * @post Se ha creado el objeto de la clase AtributoCategorico con la variable valorString inicializado al parametro.
     * @param valorString: atributo de la clase
     */
    public AtributoCategorico(String valorString) { this.valorString = valorString; }

    /** @fn getValor
     * @brief Getter del atributo en formato string.
     * Hace override de la clase base con la instancia correcta de tipo y variable.
     * @pre La clase AtributoCategorico tiene instancia, y la variable global valorString esta inicializada.
     * @post Se ha devuelto el string de la clase.
     * @return Devuelve el string que tiene la clase como variable global.
     */
    public String getValor() { return this.valorString; }


}
