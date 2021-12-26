import clases.Items;
import clases.inout;
import controladores.CtrlDominio;
import controladores.CtrlDominioPreprocesarDatos;
import controladores.CtrlPersistencia;

import java.util.Vector;

/** @class DriverRecomendaciones
 * @brief Drivers de la clase Recomendaciones
 *
 * @author Pau Vallespí Monclús
 * @date December 2021
 */

public class DriverRecomendaciones {
    private static Items items = new Items();

    public static void main(String args[]) throws Exception {
        String eval;
        String recomAlg;
        inout INOUT = new inout();

        String command = "";
        while (!command.equals("NO")) {
            INOUT.write("Introduce el id del usuario: ");
            int userId = INOUT.readint();

            INOUT.write("Introduce el número de ítems para recomendar: ");
            int size = INOUT.readint();

            INOUT.writeln("De qué fuente de datos quieres leer los archivos? ");
            INOUT.writeln("Opciones:");
            CtrlPersistencia.mostrarDirectorios();

            INOUT.write("Introduce tu selección completa (por ejemplo: data.peliculas/250) ");
            String path = INOUT.readword();
            path = path.toLowerCase() + "/";
            INOUT.writeln();

            CtrlDominio.cargarDatos("DATA/csv/" + path);

            CtrlDominio.enterNewUser(userId, false);

            INOUT.writeln("Algoritmos: ");
            INOUT.writeln(" - CF: Collaborative Filtering [K-Means + Slope One]");
            INOUT.writeln(" - CB: Content Based Filtering [K Nearest Neighbors]");
            INOUT.writeln(" - HYBRID: Collaborative & Content Based Filtering");
            do {
                INOUT.writeln();
                INOUT.write ("Escoge algoritmo de recomendación: ");
                recomAlg = INOUT.readword();
                recomAlg = recomAlg.trim().toUpperCase();
            } while (!recomAlg.equals("CF") && !recomAlg.equals("CB"));

            do {
                INOUT.write ("Quiere evaluar la recomendación? (SI/NO): ");
                eval = INOUT.readword();
                eval = eval.trim().toUpperCase();
            } while (!eval.equals("SI") && !eval.equals("NO"));

            INOUT.writeln();

            if (eval.equals("SI")) CtrlDominioPreprocesarDatos.preprocesarRatingsUknown("DATA/csv/" + path + "/ratings.test.unknown.csv");
            Vector v = CtrlDominio.getPredicciones(recomAlg.toLowerCase(), "", size);
            for (int i = 0; i < v.size(); i++) INOUT.writeln((i+1) + "- " + ((Vector) v.get(i)).get(0).toString());

            INOUT.write("¿Desea obtener una nueva recomendación? (SI/NO) ");
            command = INOUT.readword();
            INOUT.writeln();
        }
    }
}
