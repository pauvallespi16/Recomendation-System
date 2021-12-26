package clases; /** @file Usuarios.java
 @brief Código de la clase Usuarios
 */

import java.util.*;

/** @class Usuarios
 * @brief La clase de usuario contiene la implementación de un conjunto de usuarios.
 *
 * @author Carla Campàs Gené
 *
 * @date December 2021
 */
public class Usuarios {
    /** @var ArrayList <Usuario> users
     *  @brief Conjunto de objetos de la clase de usuario que componen el conjunto de usuarios. */
    private ArrayList<Usuario> users;

    /** @fn Usuarios
     * @brief Constructora por defecto, inicializa un conjunto de usuarios vacío.
     * @pre No hay objeto usuarios.
     * @post Se ha creado un objeto de usuarios vacío.
     */
    public Usuarios(){ this.users = new ArrayList<>(); }

    /** @fn Usuarios
     * @brief Constructora por defecto, inicializa un conjunto de usuarios con un ArrayList de usuario.
     * @pre No hay objeto usuarios.
     * @post Se ha creado un objeto de usuarios con la variable users de la clase inicializada.
     * @param users: ArrayList de usuarios que componen el conjunto.
     */
    public Usuarios(ArrayList<Usuario> users) { this.users = new ArrayList<>(users); }

    /** @fn getUsers
     * @brief Getter del ArrayList de usuarios
     * @pre -
     * @post Se ha devuelto el ArrayList de usuarios.
     * @return Devuelve el ArrayList de usuarios.
     */
    public ArrayList<Usuario> getUsers() { return users; }

    /** @fn getUser
     * @brief Getter de un usuario con id "id"
     * @pre El ArrayList de usuarios tiene el id "id"
     * @post Ha devuelto el usuario con id "id"
     * @param id: id del usuario del que queremos conseguir el objeto.
     * @return devuelve el objeto de usuario con id "id"
     */
    public Usuario getUser(int id) { return users.get(id); }

    /** @fn setUsers
     * @brief Setter del ArrayList de usuarios
     * @pre El ArrayList de usuarios no esta inicializado o esta inicializado a un ArrayList distinto.
     * @post El atributo users de la instancia de la clase es una copia del parametro users.
     * @param users: ArrayList de los usuarios del conjunto
     */
    public void setUsers(ArrayList<Usuario> users) { this.users = new ArrayList<>(users); }

    /** @fn setUser
     * @brief Cambiar el objeto de un usuario en el ArrayList
     * @pre El ArrayList de users tiene un usuario en la posición id.
     * @post El ArrayList de usuarios tiene un entry {user id, user}
     * @param id: id de la posición del ArrayList que queremos cambiar.
     * @param user: usuario que queremos cambiar en el ArrayList
     */
    public void setUser(Integer id, Usuario user) { users.set(id, user); }

    /** @fn addUser
     * @brief Añadir el objeto de usuario en el ArrayList
     * @pre El id del usuario que pasamos como parámetro ya esta instanciado en el objeto.
     * @post El ArrayList de usuarios tiene un nuevo entry del usuario user
     * @param user: usuario que queremos meter en el ArrayList
     */
    public void addUser(Usuario user) { users.add(user); }

    /** @fn containsUser
     * @brief Comprobar si el ArrayList users tiene el usuario con id users
     * @pre -
     * @post Ha devuelto true si el objeto tiene un usuario con id "id", falso si no.
     * @param id: id del usario del que queremos comprobar si esta en el conjunto de usuarios
     * @return true si el conjunto tiene al usuario con "id", falso si no.
     */
    public boolean containsUser(int id) {
        try { users.get(id); return true; }
        catch(Exception IndexOutOfBoundsException) { return false; }
    }

    /** @fn toString
     * @brief Hace override de la funcion toString
     * @pre -
     * @post Los usuarios se han traducido a String para poder imprimirse
     * @return Devuelve el item traducido a String
     */
    public String toString() {
        String valor = "";
        for (Usuario u : users)
            valor += u + "|\n";
        return valor;
    }
}
