package clases; /** @file AtributoBoolean.java
 @brief Código de la clase Atributo Booleano subclase de Atributo
 */

/** @Class AtributoBoolean
 * @brief La clase AtributoBoolean es herencia de la clase atributo con tipo Booleano.
 *
 * @author Pau Vallespí Monclús
 *
 * @date December 2021
 */
public class AtributoBoolean extends Atributo<Boolean> {
    /** @var boolean valorBoolean
     *  @brief El valor del atributo en formato boolean*/
    private boolean valorBoolean;

    /** @fn AtributoBoolean
     * @brief Constructora por defecto de la clase AtributoBoolean
     *
     * @pre No hay objeto de AtributoBoolean
     * @post Se ha creado el objeto de la clase AtributoBoolean con la variable valorBoolean inicializado al parametro.
     * @param valorBoolean: atributo de la clase
     */
    public AtributoBoolean(String valorBoolean) { if (!valorBoolean.equals("")) this.valorBoolean = Boolean.parseBoolean(valorBoolean); }

    /** @fn getValor
     * @brief Getter del atributo en formato booleano.
     * Hace override de la clase base con la instancia correcta de tipo y variable.
     * @pre La clase AtributoCategorico tiene instancia, y la variable global valorBoolean esta inicializada.
     * @post Se ha devuelto el booleano de la clase.
     * @return Devuelve el booleano que tiene la clase como variable global.
     */
    public Boolean getValor() { return this.valorBoolean; }
}
