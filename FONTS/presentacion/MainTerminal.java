package presentacion; /** @file MainTerminal.java
 @brief Código de la clase Main por terminal
 */

import clases.*;
import clases.inout;
import controladores.*;

import java.util.*;


/**
 * @brief Sistema de recomendaciones a un usuario basado en valoraciones previamente hechas a otros items
 *
 * @author Carla Campàs Gené
 * @author Pau Vallespí Monclús
 * @author Andres Eduardo Bercowsky Rama
 * @author Beatriz Gomes da Costa
 *
 * @date November 2021
 */


public class MainTerminal {
	/*
		------------------ PROJECTE DE PROGRAMACIÓ ------------------
		GRUP: 2.1

		PROFESSOR:
		  · Borja Vallès

		MEMBRES:
		  · Andrés Eduardo Bercowsky Rama
		  · Carla Campàs Gené
		  · Beatriz Gomes da Costa
		  · Pau Vallespí Monclús
		-------------------------------------------------------------
	*/

    public static void main(String[] args) throws Exception {
        inout INOUT = new inout();
        INOUT.writeln();

        bienvenida();

        INOUT.write("Introduce el id del usuario: ");
        int userId = INOUT.readint();
        INOUT.writeln();

        INOUT.write("Introduce el número de ítems para recomendar: ");
        int size = INOUT.readint();
        INOUT.writeln();

        INOUT.writeln("De qué fuente de datos quieres leer los archivos? ");
        INOUT.writeln("Opciones:");
        CtrlPersistencia.mostrarDirectorios();

        INOUT.write("Introduce tu selección completa (por ejemplo: data.peliculas/250) ");
        String path = INOUT.readword();
        path = path.toLowerCase() + "/";
        INOUT.writeln();

        CtrlDominio.cargarDatos("DATA/csv/" + path);

        CtrlDominio.enterNewUser(userId, false);
        mostrarOpciones();

        INOUT.write("Introduce tu opción: ");
        String decision = INOUT.readword();
        while (!decision.equals("salir")) {
            switch (decision) {
                case "añadir":
                    INOUT.write("Introduzca el id del item: "); int   id = INOUT.readint();
                    INOUT.write("Introduzca la valoracion: ");  double d = INOUT.readdouble();
                    CtrlDominio.createNewValoracion(id, d);
                    INOUT.writeln("Hecho!");
                    break;
                case "borrar":
                    INOUT.write("Introduzca el id del item: "); id = INOUT.readint();
                    CtrlDominio.quitarValoracion(id);
                    INOUT.writeln("Hecho!");
                    break;
                case "delete":
                    INOUT.write("Introduzca el id del item: "); id = INOUT.readint();
                    CtrlDominio.deleteItem(id);
                    INOUT.writeln("Hecho!");
                    break;
                case "items":
                    CtrlDominio.mostrarItems();
                    INOUT.writeln("Hecho!");
                    break;
                case "valoraciones":
                    CtrlDominio.mostrarValoraciones();
                    INOUT.writeln("Hecho!");
                    break;
                case "historial":
                    CtrlDominio.mostrarHistorial();
                    INOUT.writeln("Hecho!");
                    break;
                case "obtener":
                    String eval;
                    String recomAlg;
                    String nombre;

                    INOUT.writeln("Algoritmos: ");
                    INOUT.writeln(" - CF: Collaborative Filtering [K-Means + Slope One]");
                    INOUT.writeln(" - CB: Content Based Filtering [K Nearest Neighbors]");
                    INOUT.writeln(" - HYBRID: Collaborative & Content Based Filtering");
                    do {
                        INOUT.writeln();
                        INOUT.write ("Escoge algoritmo de recomendación: ");
                        recomAlg = INOUT.readword();
                        recomAlg = recomAlg.trim().toUpperCase();
                    } while (!recomAlg.equals("CF") && !recomAlg.equals("CB") && !recomAlg.equals("HYBRID"));

                    do {
                        INOUT.write ("Quiere evaluar la recomendación? (SI/NO): ");
                        eval = INOUT.readword();
                        eval = eval.trim().toUpperCase();
                    } while (!eval.equals("SI") && !eval.equals("NO"));

                    INOUT.writeln();
                    INOUT.write ("Escoge nombre de recomendación: ");
                    nombre = INOUT.readword();

                    if (eval.equals("SI")) CtrlDominioPreprocesarDatos.preprocesarRatingsUknown("DATA/csv/" + path + "/ratings.test.unknown.csv");
                    Vector v = CtrlDominio.getPredicciones(recomAlg.toLowerCase(), nombre, size);
                    for (int i = 0; i < v.size(); i++) INOUT.writeln((i+1) + "- " + ((Vector) v.get(i)).get(0).toString());
                    INOUT.writeln("Hecho!");
                    break;
            }
            INOUT.writeln();
            INOUT.write("Introduce tu opción: "); decision = INOUT.readword();
        }
    }

    private static void bienvenida() throws Exception {
        inout INOUT = new inout();
        INOUT.writeln("########### SISTEMA DE RECOMENDACIÓN ###########");
        INOUT.writeln("¡Bienvenido a nuestro sistema de recomendación!");
        INOUT.writeln("A continuación se le harán unas preguntas... \n");
    }

    private static void mostrarOpciones() throws Exception {
        inout INOUT = new inout();
        INOUT.writeln("Opciones disponibles");
        INOUT.writeln(" - Añadir valoracion. Escriba 'añadir'");
        INOUT.writeln(" - Borrar valoracion. Escriba 'borrar'");
        INOUT.writeln(" - Borrar item. Escriba 'delete'");
        INOUT.writeln(" - Ver items. Escriba 'items'");
        INOUT.writeln(" - Ver valoraciones. Escriba 'valoraciones'");
        INOUT.writeln(" - Ver historial de recomendaciones. Escriba 'historial'");
        INOUT.writeln(" - Obtener recomendaciones. Escriba 'obtener'");
        INOUT.writeln(" - Salir del programa. Escriba 'salir'");
    }
}
