import clases.*;
import clases.inout;
import controladores.NearestNeighbors;

import java.util.HashMap;
import java.util.Map;



/** @class DriverNearestNeighbors
 * @brief Drivers de la clase Nearest Neighbors
 *
 * @author Andrés Eduardo Bercowsky Rama
 * @date November 2021
 */
public class DriverNearestNeighbors {
    private static Items items = new Items();
    private static int k;
    //private static ArrayList<Item> items = new ArrayList<>();
    private static NearestNeighbors nn;
    private static inout INOUT = new inout();
    private static Usuario usuario;
    private static double maxValoración;


    private static void initializeK() throws Exception {
        INOUT.write("Introduzca el valor de la k: ");
        k = INOUT.readint();
        INOUT.readline();
    }

    private static void initializeUsuario() throws  Exception {
        INOUT.write("ID del usuario: ");
        int id = INOUT.readint();

        Map <Integer, Double> valoraciones = new HashMap<>();
        usuario = new Usuario(id, valoraciones);
    }

    private static Map<String, Atributo> fillAtributos(Map<String, Atributo> atributos, int j) throws Exception {
        INOUT.write("Introduce el nombre de un atributo del item "+j+": ");
        String name = INOUT.readline();
        INOUT.writeln("Introduce el tipo del atributo #" + j + " de entre los siguientes:");
        INOUT.writeln("numerico, booleano, categorico, categorico multiple");
        String tipo = INOUT.readline();
        while (!tipo.equals("numerico") && !tipo.equals("booleano") && !tipo.equals("categorico") && !tipo.equals("categorico multiple")) {
            INOUT.writeln("Dato incorrecto, por favor introduzca uno de los tipos de datos dados.");
            tipo = INOUT.readline();
        }
        switch (tipo) {
            case "numerico":
                INOUT.write("Introduce un double entre 0 y 1: ");
                Double d = INOUT.readdouble();
                while (d < 0 || d > 1) {
                    INOUT.writeln("Input incorrecto, por favor introduzca un valor entre 0 y 1");
                    d = INOUT.readdouble();
                }
                INOUT.readline();
                AtributoNumerico atributoNumerico = new AtributoNumerico(d.toString());
                atributoNumerico.setValorNormalizado(d);
                atributos.put(name, atributoNumerico);
                break;

            case "booleano":
                INOUT.write("Introduce un booleano (true/false): ");
                String b = INOUT.readline();
                AtributoBoolean atributoBooleano = new AtributoBoolean(b);
                atributos.put(name, atributoBooleano);
                break;

            case "categorico":
                INOUT.write("Introduce un string: ");
                String s = INOUT.readline();
                AtributoCategorico atributoCategorico = new AtributoCategorico(s);
                atributos.put(name, atributoCategorico);
                break;

            case "categorico multiple":
                INOUT.write("Introduce varias palabras sin espacios y separadas por punto y coma (;): ");
                String sm = INOUT.readline();
                AtributoCategoricoMultiple atributoCategoricoMultiple = new AtributoCategoricoMultiple(sm.toString());
                atributos.put(name, atributoCategoricoMultiple);
                break;
        }
        return atributos;
    }

    private static void generarItems() throws Exception {
        INOUT.write("¿Cuantos items deseas añadir? ");
        int n = INOUT.readint();
        INOUT.readline();
        INOUT.write("Cuantos atributos deseas añadir: ");
        int m = INOUT.readint();
        INOUT.readline();
        INOUT.writeln("Para cada Item, introduce el mismo número de atributos, " +
                "con el mismo orden y que el nombre y el tipo concuerde para cada posición!");
        for (int i=0; i<n; i++){

            Map<String, Atributo> atributos = new HashMap<>();
            for (int j = 0; j < m; j++) {
                atributos = fillAtributos(atributos, i);
            }

            Item item = new Item(i, atributos);
            //items.add(item);
            items.addItem(item);
        }
        INOUT.write("Introduce el número de items que el usuario ha valorado: ");
        int nValorados = INOUT.readint();
        while (k+nValorados > n) {
            INOUT.write("Introduce un valor más pequeño: ");
            nValorados = INOUT.readint();
        }

        Map <Integer, Double> valoraciones = new HashMap<>();
        for (int j = 0; j < nValorados; j++) {
            INOUT.write("Introduce el id de un item, un número del 0 al "+(n-1)+" que no haya introducido antes: ");
            int id = INOUT.readint();
            while (id < 0 || id >= nValorados || valoraciones.containsKey(id)) {
                INOUT.write("El id es incorrecto. Por favor introduzca uno nuevo: ");
                id = INOUT.readint();
            }
            INOUT.write("Introduce una valoración double del 0.0 al "+maxValoración+": ");
            double score = INOUT.readdouble();
            INOUT.readline();
            while (score < 0 || score > maxValoración) {
                INOUT.write("El score es incorrecto. Por favor introduzca uno nuevo: ");
                score = INOUT.readdouble();
            }
            valoraciones.put(id, score);
        }
        usuario.setValoraciones(valoraciones);
    }

    public static void constructorVacío() throws Exception {
        try {
            nn = new NearestNeighbors();
        } catch (Exception e){
            INOUT.writeln("ERROR: llamada a constructora vacía ha devuelto error");
        }
        INOUT.writeln("Se ha llamado a la constructora vacía!");
    }

    public static void constructorNoVacío() throws Exception {
        try {
            nn = new NearestNeighbors(k);
        } catch (Exception e){
            INOUT.writeln("ERROR: llamada a constructora con parametros ha devuelto error");
        }
        INOUT.writeln("Se ha llamado a la constructora con parametros con k = "+k);
    }

    public static void setNeighborsKNN() throws Exception {
        try {
            INOUT.write("Introduzca el valor de la k: ");
            k = INOUT.readint();
            nn.setNeighbors(k);
        } catch (Exception e){
            INOUT.writeln("ERROR: llamada a la función setNeighbors ha devuelto error");
        }
        INOUT.writeln("Se ha llamado a la funcion setNeighbors!");
    }

    public static void distanceNumsKNN() throws Exception {
        double dist = -1;
        try {
            INOUT.write("Introduzca el nombre del atributo: ");
            String attName = INOUT.readline();
            INOUT.write("Introduzca el id de dos items: ");
            int id1 = INOUT.readint();
            while (id1 < 0 || id1 >= items.getSize()) {
                INOUT.write("Id incorrecto, por favor introduzca uno nuevo: ");
                id1 = INOUT.readint();
            }
            int id2 = INOUT.readint();
            while (id2 < 0 || id2 >= items.getSize()) {
                INOUT.write("Id incorrecto, por favor introduzca uno nuevo: ");
                id2 = INOUT.readint();
            }
            dist = nn.distanceNums(attName, items.getItem(id1), items.getItem(id2));
        } catch (Exception e){
            INOUT.writeln("ERROR: llamada a la función distanceNums ha devuelto error"+ e);
        }
        INOUT.writeln("Se ha llamado a la funcion distanceNums: "+dist);
    }

    public static void distanceBooleansKNN() throws Exception {
        double dist = -1;
        try {
            INOUT.write("Introduzca el nombre del atributo: ");
            String attName = INOUT.readline();
            INOUT.write("Introduzca el id de dos items: ");
            int id1 = INOUT.readint();
            while (id1 < 0 || id1 >= items.getSize()) {
                INOUT.write("Id incorrecto, por favor introduzca uno nuevo: ");
                id1 = INOUT.readint();
            }
            int id2 = INOUT.readint();
            while (id2 < 0 || id2 >= items.getSize()) {
                INOUT.write("Id incorrecto, por favor introduzca uno nuevo: ");
                id2 = INOUT.readint();
            }
            dist = nn.distanceBooleans(attName, items.getItem(id1), items.getItem(id2));
        } catch (Exception e){
            INOUT.writeln("ERROR: llamada a la función distanceBooleans ha devuelto error");
        }
        INOUT.writeln("Se ha llamado a la funcion distanceBooleans: "+dist);
    }

    public static void distanceCategoricoKNN() throws Exception {
        double dist = -1;
        try {
            INOUT.write("Introduzca el nombre del atributo: ");
            String attName = INOUT.readline();
            INOUT.write("Introduzca el id de dos items: ");
            int id1 = INOUT.readint();
            while (id1 < 0 || id1 >= items.getSize()) {
                INOUT.write("Id incorrecto, por favor introduzca uno nuevo: ");
                id1 = INOUT.readint();
            }
            int id2 = INOUT.readint();
            while (id2 < 0 || id2 >= items.getSize()) {
                INOUT.write("Id incorrecto, por favor introduzca uno nuevo: ");
                id2 = INOUT.readint();
            }
            dist = nn.distanceCategorico(attName, items.getItem(id1), items.getItem(id2));
        } catch (Exception e){
            INOUT.writeln("ERROR: llamada a la función distanceCategorico ha devuelto error");
        }
        INOUT.writeln("Se ha llamado a la funcion distanceCategorico con resultado: "+dist);
    }

    public static void distanceCategoricoMultipleKNN() throws Exception {
        double dist = -1;
        try {
            INOUT.write("Introduzca el nombre del atributo: ");
            String attName = INOUT.readline();
            INOUT.write("Introduzca el id de dos items: ");
            int id1 = INOUT.readint();
            while (id1 < 0 || id1 >= items.getSize()) {
                INOUT.write("Id incorrecto, por favor introduzca uno nuevo: ");
                id1 = INOUT.readint();
            }
            int id2 = INOUT.readint();
            while (id2 < 0 || id2 >= items.getSize()) {
                INOUT.write("Id incorrecto, por favor introduzca uno nuevo: ");
                id2 = INOUT.readint();
            }
            dist = nn.distanceCategoricoMultiple(attName, items.getItem(id1), items.getItem(id1));
        } catch (Exception e){
            INOUT.writeln("ERROR: llamada a la función distanceCategoricoMultiple ha devuelto error");
        }
        INOUT.writeln("Se ha llamado a la funcion distanceCategoricoMultiple con resultado: "+dist);
    }

    public static void computeAllPrediccionesKNN() throws Exception {
        try {
            Map<Integer, Double> result = nn.computeAllPredicciones();
            INOUT.writeln("Los "+k+" items en orden de preferencia para el usuario son: ");
            for (Integer i : result.keySet()) INOUT.writeln(Parser.getAllItems().getItem(i.intValue()).toString());
        } catch (Exception e){
            INOUT.writeln("ERROR: llamada a la función computeAllPredicciones ha devuelto error"+e);
        }
        INOUT.writeln("Se ha llamado a la funcion computeAllPredicciones!");
    }


    public static void main(String args[]) throws Exception {
        maxValoración = 5;
        initializeK();
        initializeUsuario();
        generarItems();

        INOUT.writeln("ANTES DE LLAMAR A CUALQUIER FUNCIÓN, LLAMA A LA CONSTRUCTORA NO VACIA!");
        String command = "";
        while (!command.equals("salir")) {
            INOUT.writeln("Inserte un comando. Inserte help para ver todos los posibles comandos.");
            command = INOUT.readline();

            if (command.equals("constructor vacio")) constructorVacío();
            else if (command.equals("constructor no vacio")) constructorNoVacío();
            else if (command.equals("set neighbors")) setNeighborsKNN();
            else if (command.equals("distanceNums")) distanceNumsKNN();
            else if (command.equals("distanceBooleans")) distanceBooleansKNN();
            else if (command.equals("distanceCategorico")) distanceCategoricoKNN();
            else if (command.equals("distanceCategoricoMultiple")) distanceCategoricoMultipleKNN();
            else if (command.equals("computeAllPredicciones")) computeAllPrediccionesKNN();
            else if (command.equals("help")) {
                INOUT.writeln("This are the commands available for this class:");
                INOUT.writeln("constructor vacio");
                INOUT.writeln("constructor no vacio");
                INOUT.writeln("set neighbors");
                INOUT.writeln("distanceNums");
                INOUT.writeln("distanceBooleans");
                INOUT.writeln("distanceCategorico");
                INOUT.writeln("distanceCategoricoMultiple");
                INOUT.writeln("computeAllPredicciones");
                INOUT.writeln("help");
                INOUT.writeln("salir");
                INOUT.writeln();
            }
            INOUT.writeln();
        }
    }
}
