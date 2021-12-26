package clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recomendaciones {
    /** @var Map<String, Recomendacion> recomendaciones
     * @brief mapa de recomendaciones con item id y evaluación predecida
     */
    Map<String, Recomendacion> recomendaciones;

    /** @fn Recomendaciones
     * @brief constructora por defecto
     * @pre -
     * @post la clase de recomendaciones con
     */
    public Recomendaciones() {
        recomendaciones = new HashMap<>();
    }

    /** @fn Recomendaciones
     * @brief constructora por defecto
     * @pre -
     * @post se ha creado un objeto con una lista de recomendaciones
     * @param recomendaciones lista de recomendaciones
     */
    public Recomendaciones(ArrayList<Recomendacion> recomendaciones) {
        this.recomendaciones = new HashMap<>();
        for (Recomendacion r : recomendaciones){
            this.recomendaciones.put(r.getName(), r);
        }
    }

    /** @fn getRecomendaciones
     * @brief coger las recomendaciones del sistema
     * @pre hay una instancia de la clase de recomendaciones
     * @post se ha devuelto la lista de recomendaciones
     * @return lista de instancias de la clase recomendacion
     */
    public ArrayList <Recomendacion> getRecomendaciones () {
        return new ArrayList<>(this.recomendaciones.values());
    }

    /** @fn getRecomendacion
     * @brief coger la instancia de la recomendacion con nombre name
     * @pre hay una instancia de la clase de recomendaciones
     * @post se ha devuelto la instancia que corresponde al nombre name
     * @param name el nombre de la recomendacion
     * @return recomendacion con el nombre name
     */
    public Recomendacion getRecomendacion (String name) {
        if (recomendaciones.containsKey(name)) return recomendaciones.get(name);
        return null;
    }

    /** @fn addRecomendacion
     * @brief añadir una recomendacion en el mapa
     * @pre existe una instancia de la clase
     * @post se ha añadido el objeto r, y en caso que ya existiese reemplazado el objeto existente
     * @param r objeto de la clase de recomendacion que queremos añadir a la clase
     */
    public void setRecomendacion (Recomendacion r) {
        this.recomendaciones.put(r.getName(), r);
    }

    /** @fn addRecomendacion
     * @brief añadir una recomendacion en el mapa
     * @pre existe una instancia de la clase
     * @post se ha añadido el objeto r, y en caso que ya existiese reemplazado el objeto existente
     * @param r objeto de la clase de recomendacion que queremos añadir a la clase
     */
    public void addRecomendacion (Recomendacion r) {
        recomendaciones.put(r.getName(), r);
    }

    public void removeRecomendacino (String name) { recomendaciones.remove(name); }

    /** @fn getHistorial()
     * @brief funcion para hallar los nombres de todas las recomendaciones del sistema
     * @pre hay una instancia de la clase recomendaciones
     * @post ha devuelto los nombres de todas las recomendaciones del sistema
     * @return devuelve los nombres de todas las recomendaciones del sistema
     */
    public ArrayList <String> getHistorial () {
        return new ArrayList<>(recomendaciones.keySet());
    }

    /** @fn toString()
     * @brief funcion usada para pasar clase a string
     * @pre hay una instancia de la clase recomendaciones
     * @post se ha devuelto los componentes de la clase en formato string
     * @return los componentes de la clase en formato string
     */
    @Override
    public String toString() {
        String valor = "";
        for (Recomendacion recom : recomendaciones.values())
            valor += recom.getName() + "," + recom.getType() + "," + recom.getDate();
        return valor;
    }
}
