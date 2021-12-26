package clases; /** @file AtributoNumerico.java
 @brief Código de la clase Atributo Numerico subclase de Atributo
 */


/** @Class AtributoNumerico
 * @brief La clase AtributoNumerico es herencia de la clase atributo con tipo Double.
 *
 * @author Pau Vallespí Monclús
 *
 * @date December 2021
 */
public class AtributoNumerico extends Atributo<Double> {
    /** @var long valorNumerico
     *  @brief El valor del atributo en formato Double*/
    private Double valorNumerico;

    /** @var long valorNormalizado
     *  @brief El valor del double normalizado para el uso de Content Based Filtering (de 0 a 1)
     *  El valorNormalizado ira de 0 a 1 basado en el rango de doubles que nos entren de los diferentes items.
     */
    private Double valorNormalizado;

    /** @fn AtributoNumerico
     * @brief Constructora por defecto de la clase AtributoNumerico
     *
     * @pre No hay objeto de AtributoNumerico
     * @post Se ha creado el objeto de la clase AtributoNumerico con la variable valorNumerico inicializado al parametro.
     * @param valorNumerico: atributo de la clase
     */
    public AtributoNumerico(String valorNumerico) {
        if (!valorNumerico.equals(""))
            this.valorNumerico = Double.parseDouble(valorNumerico);
    }

    /** @fn getValor
     * @brief Getter del atributo en formato double.
     * Hace override de la clase base con la instancia correcta de tipo y variable.
     * @pre La clase AtributoNumerico tiene instancia, y la variable global valorBoolean esta inicializada.
     * @post Se ha devuelto el booleano de la clase.
     * @return Devuelve el numerico que tiene la clase como variable global.
     */
    public Double getValor() { return valorNumerico; }

    /**@fn setValorNormalizado
     * @brief Setter del valor normalizado del double
     * @pre La variable global valorNormalizado no se ha inicializado o tiene un valor distinto al parametro
     * @post La variable global valorNormalizado del double
     * @param valorNormalizado: valor del double normalizado
     */
    public void setValorNormalizado(Double valorNormalizado) { this.valorNormalizado = valorNormalizado; }

    /** @fn getValorNormalizado
     * @brief Getter del valor normalizado del double (0 a 1).
     * @pre -
     * @post Se ha devuelto el valor de la variable valorNormalizado.
     * @return Devuelve el valor del double normalizado.
     */
    public Double getValorNormalizado() { return valorNormalizado; }
}
