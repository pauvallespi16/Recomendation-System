package clases; /** @file AtributoFecha.java
 @brief Código de la clase Atributo Fecha subclase de Atributo
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;

/** @Class AtributoFecha
 * @brief La clase AtributoFecha es herencia de la clase atributo con tipo long.
 *
 * Esta clase implementa un atributo con tipo long para hacer el formato de la fecha.
 * Esta se guarda en long que corresponde al número de segundos que han pasado desde segundos desde 1970.
 * Esto es el standard de la librería SimpleDateFormat.
 *
 * @author Pau Vallespí Monclús
 *
 * @date December 2021
 */
public class AtributoFecha extends Atributo<Long> {
    /** @var long valorFecha
     *  @brief El valor de la fecha en formato de long (número de segundos desde el 1970)*/
    private long valorFecha;

    /** @var long valorNormalizado
     *  @brief El valor de la fecha normalizado para el uso de Content Based Filtering (de 0 a 1)
     *  El valorNormalizado ira de 0 a 1 basado en el rango de fechas que nos entren de los diferentes items.
     */
    double valorNormalizado;

    /** @fn AtributoFecha
     * @brief Constructora por defecto de la clase AtributoFecha
     *
     * Pasa el valor de la fecha estandard (yyyy-mm-dd) a segundos desde 1970.
     * Ejemplo de fecha: 1995-12-22
     *
     * @pre No hay objeto de AtributoFecha
     * @post Se ha creado el objeto de la clase AtributoFecha con la variable valor fecha isntanciada
     * @param valorFecha: Se pasa el valor de la fecha en formato string
     * @throws ParseException
     */
    public AtributoFecha(String valorFecha) throws ParseException {
        if (!valorFecha.equals(""))
            this.valorFecha = new SimpleDateFormat("yyyy-MM-dd").parse(valorFecha).getTime();
    }

    /** @fn AtributoFecha
     * @brief Constructora alternativa de la clase AtributoFecha
     *
     * @pre No hay objeto de AtributoFecha
     * @post Se ha creado el objeto de la clase AtributoFecha con la variable valor fecha isntanciada
     * @param value: Se pasa el valor de la fecha en formato long
     * @throws ParseException
     */
    public AtributoFecha(Long value) throws ParseException {
        this.valorFecha = value;
    }

    /** @fn getValor
     * @brief Getter de la fecha, valor sin normalizar.
     * Hace override de la clase base con la instancia correcta de tipo y variable.
     * @pre La clase AtributoFecha tiene instancia, y la variable global valorFecha esta inicializada.
     * @post Se ha devuelto el valor en segundos de la fecha del atributo.
     * @return Devuelve el valor en segundos de la fecha del atributo.
     */
    public Long getValor() { return valorFecha; }

    /**@fn setValorNormalizado
     * @brief Setter del valor normalizado de la fecha
     * @pre La variable global valorNormalizado no se ha inicializado o tiene un valor distinto al parametro
     * @post La variable global valorNormalizado de la fecha
     * @param valorNormalizado: valor de la fecha normalizado
     */
    public void setValorNormalizado(Double valorNormalizado) { this.valorNormalizado = valorNormalizado; }

    /** @fn getValorNormalizado
     * @brief Getter del valor normalizado de la fecha (0 a 1).
     * @pre -
     * @post Se ha devuelto el valor de la variable valorNormalizado.
     * @return Devuelve el valor de la fecha normalizado.
     */
    public Double getValorNormalizado() { return valorNormalizado; }
}
