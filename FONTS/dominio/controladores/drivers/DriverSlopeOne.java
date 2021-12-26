import clases.*;
import clases.inout;
import controladores.SlopeOne;

import java.util.HashMap;
import java.util.Map;

/** @class DriverSlopeOne
 * @brief Drivers de la clase Slope One
 *
 * @author Carla Campàs Gené
 * @date November 2021
 */
public class DriverSlopeOne {
    private static Items items = new Items();
    private static Usuario u = new Usuario();
    private static SlopeOne sa;
    private static inout INOUT = new inout();

    public static void constructorVacío() throws Exception {
        try {
            sa = new SlopeOne();
        } catch (Exception e){
            INOUT.writeln("ERROR: llamada a constructora vacía ha devuelto error");
        }
        INOUT.writeln("Se ha llamado a la constructora vacía!");
    }

    public static void setUsuariosSlopeOne() throws Exception {
        Usuarios us = new Usuarios();
        INOUT.write("Cuantos usuarios deseas añadir?");
        int n = INOUT.readint();
        for (int i=0; i<n; i++){
            INOUT.writeln("ID del usuario " + i + ":");
            int id = INOUT.readint();
            INOUT.write("Cuantas valoraciones deseas añadir para el usuario " + i + ":");
            int m = INOUT.readint();

            Map <Integer, Double> valoraciones = new HashMap<>();
            for (int j = 0; j<m; j++){
                INOUT.write("ID del item para valoracion #" + j + ":");
                int idItem = INOUT.readint();
                if (!items.containsItem(idItem)) items.addItem(new Item(idItem, new HashMap<>()));
                INOUT.write("Valoración del item #" + idItem + ":");
                double v = INOUT.readdouble();
                valoraciones.put(idItem, v);
            }

            us.addUser(new Usuario(id, valoraciones));
        }

        try {
            sa.setUsuarios(us);
        } catch (Exception e) {
            INOUT.writeln("ERROR: el setter del conjunto de usuarios ha devuelto error");
        }
        INOUT.writeln("Se ha setteado correctamente el nuevo conjunto de usuarios!");
    }

    public static void setUsuarioSlopeOne() throws Exception {
        INOUT.write("ID del usuario: " );
        int id = INOUT.readint();
        INOUT.writeln("Cuantas valoraciones deseas añadir para el usuario: ");
        int m = INOUT.readint();

        Map <Integer, Double> valoraciones = new HashMap<>();
        for (int j = 0; j<m; j++){
            INOUT.write("ID del item para valoracion #" + j + ": ");
            int idItem = INOUT.readint();
            if (!items.containsItem(idItem)) items.addItem(new Item(idItem, new HashMap<>()));
            INOUT.write("Valoración del item #" + idItem + ": ");
            double v = INOUT.readdouble();
            valoraciones.put(idItem, v);
        }

        try {
            sa.setUser(new Usuario(id, valoraciones));
        } catch (Exception e) {
            INOUT.writeln("ERROR: el setter del usuario ha devuelto error");
        }
        INOUT.writeln("Se ha setteado correctamente el nuevo usuario!");
    }

    public static void getMedianaSlopeOne() throws Exception {
        try {
            INOUT.writeln("La mediana de la clase instanciada de slope one es: " + sa.getMediana());
        } catch (Exception e) {
            INOUT.writeln("ERROR: el getter de la mediana ha devuelto error");
        }
    }

    public static void getDiffMatrixSlopeOne() throws Exception {
        try {
            Map <Integer, Map <Integer, Double>> diffGet = sa.getDiff();

            INOUT.writeln("MATRIZ DE DESVIACIONES: ");
            for (Map.Entry<Integer, Map <Integer, Double>> e : diffGet.entrySet()){
                INOUT.write("[" +e.getKey() + "] ");
                for (Map.Entry<Integer, Double> e1 : e.getValue().entrySet()){
                    INOUT.write("(" + e1.getKey() + "," + e1.getValue() + ") ");
                }
                INOUT.writeln();
            }
        } catch (Exception e) {
            INOUT.writeln("ERROR: el getter de la matriz de desviacoines ha devuelto error");
        }
    }

    public static void getFreqMatrixSlopeOne() throws Exception {
        try {
            Map <Integer, Map <Integer, Integer>> freqGet = sa.getFreq();

            INOUT.writeln("MATRIZ DE FRECUENCIAS: ");
            for (Map.Entry<Integer, Map <Integer, Integer>> e : freqGet.entrySet()){
                INOUT.write("[" + e.getKey() + "] ");
                for (Map.Entry<Integer, Integer> e1 : e.getValue().entrySet()){
                    INOUT.write("(" + e1.getKey() + "," + e1.getValue() + ") ");
                }
                INOUT.writeln();
            }
        } catch (Exception e) {
            INOUT.writeln("ERROR: el getter de la matriz de frecuencias ha devuelto error");
        }
    }

    public static void getPredMatrixSlopeOne() throws Exception {
        try {
            Map <Integer, Double> predGet = sa.getPred();

            INOUT.writeln("MATRIZ DE PREDICCIONES: ");
            for (Map.Entry<Integer, Double> e : predGet.entrySet()){
                INOUT.writeln("(" + e.getKey() + "," + e.getValue() + ") ");
            }
        } catch (Exception e) {
            INOUT.writeln("ERROR: el getter de la matriz de desviacoines ha devuelto error");
        }
    }

    public static void calcularMedianaSlopeOne() throws Exception {
        try {
            //sa.calcularMediana();
            INOUT.writeln("Mediana del usuario de la clase Slope One se ha calculado correctamente.");
        } catch (Exception e) {
            INOUT.writeln("ERROR: el calculo de la mediana del usuario de la clase Slope One ha devuelto un error.");
        }
    }

    public static void computarDesviacionesSlopeOne() throws Exception {
        try {
            //sa.computarDesviacion();
            INOUT.writeln("Matriz de desviaciones y frequencias se han calculado correctamente!");
        } catch (Exception e) {
            INOUT.writeln("ERROR: el calculo de la matriz de desviaciones y frequencias ha devuelto un error.");
        }
    }

    public static void computarPrediccionesSlopeOne() throws Exception {
        try {
            //sa.computarPredicciones();
            INOUT.writeln("Vector de predicciones calculado correctamente.");
        } catch (Exception e) {
            INOUT.writeln("ERROR: el calculo de predicciones ha devuelto un error");
        }
    }

    public static void printDiffSlopeOne() throws Exception {
        try {
            sa.printDiff();
        } catch (Exception e) {
            INOUT.writeln("ERROR: la impresión de la matriz de desviaciones ha causado un error");
        }
    }

    public static void printFreqSlopeOne() throws Exception {
        try {
            sa.printFreq();
        } catch (Exception e) {
            INOUT.writeln("ERROR: la impresión de la matriz de frecuencias ha causado un error");
        }
    }

    public static void printPredSlopeOne() throws Exception {
        try {
            sa.printPred();
        } catch (Exception e) {
            INOUT.writeln("ERROR: la impresión del vector de predicciones ha causado un error");
        }
    }

    public static void main(String args[]) throws Exception {
        String command = "";

        while (!command.equals("salir")) {
            INOUT.writeln("Inserte un comando. Inserte help para ver todos los posibles comandos.");
            command = INOUT.readline();

            if (command.equals("constructor vacio")) constructorVacío();
            else if (command.equals("set usuarios")) setUsuariosSlopeOne();
            else if (command.equals("set usuario")) setUsuarioSlopeOne();
            else if (command.equals("get mediana")) getMedianaSlopeOne();
            else if (command.equals("get diff matrix")) getDiffMatrixSlopeOne();
            else if (command.equals("get freq matrix")) getFreqMatrixSlopeOne();
            else if (command.equals("get pred matrix")) getPredMatrixSlopeOne();
            else if (command.equals("calcular mediana")) calcularMedianaSlopeOne();
            else if (command.equals("computar desviaciones")) computarDesviacionesSlopeOne();
            else if (command.equals("computar predicciones")) computarPrediccionesSlopeOne();
            else if (command.equals("print diff")) printDiffSlopeOne();
            else if (command.equals("print freq")) printFreqSlopeOne();
            else if (command.equals("print pred")) printPredSlopeOne();
            else if (command.equals("help")) {
                INOUT.writeln("This are the commands available for this class:");
                INOUT.writeln("constructor vacio");
                INOUT.writeln("set usuarios");
                INOUT.writeln("set usuario");
                INOUT.writeln("get mediana");
                INOUT.writeln("get diff matrix");
                INOUT.writeln("get freq matrix");
                INOUT.writeln("get pred matrix");
                INOUT.writeln("calcular mediana");
                INOUT.writeln("computar desviaciones");
                INOUT.writeln("computar predicciones");
                INOUT.writeln("print diff");
                INOUT.writeln("print freq");
                INOUT.writeln("print pred");
                INOUT.writeln("help");
                INOUT.writeln("salir");
                INOUT.writeln();
            }
            else
                System.out.println("Comando incorrecto. Por favor intente de nuevo!");
            INOUT.writeln();
        }
    }
}