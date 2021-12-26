package clases; /** @file Atributo.java
 @brief Código de la clase Atributo
 */


/** @Class Atributo
 * @brief La clase abstracta Atributo crea una clase base para inicializar los tipos de los atirbutos del item.
 *
 * Sabemos que los atributos del item pueden ser de diferentes tipos.
 * Para hacer una instancia de esta clase, tenemos que pasar del tipo que va a ser la instancia.
 * Esta clase es abstracta porque todos los atributos los vamos a instanciar mediante las subclasses que hemos creado de esta.
 * Nos sirve para poder instanciar diferentes tipos cuando hablamos de los atributos (en item).
 *
 * @author Pau Vallespí Monclús
 *
 * @date December 2021
 */
public abstract class Atributo<T> {
    /** @fn getValor
     * @brief Getter del atributo sin formato (con tipo de variable T)
     * En las subclases instnaciamos el tipo T y hacemos override de esta función para devolver el item.
     * @pre La clase Atributo tiene instancia en una subclase y devuelve el atributo de esta instancia.
     * @post Se ha devuelto la variable global de la subclase instanciada.
     * @return la variable global de la subclase instnaciada de tipo T.
     */
    public abstract T getValor();
}
