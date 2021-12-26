import clases.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/** @class DriverKMeans
 * @brief Drivers de la clase KMeans
 *
 * @author Beatriz Gomes Da Costa
 * @date November 2021
 */
public class DriverKMeans {
   /* public static void main(String args[]) throws Exception {
        KMeans kmeans = new KMeans();
        String command = "";
        inout INOUT = new inout();
        command = INOUT.readline();
        INOUT.writeln("Inserte un comando. Inserte help para ver todos los posibles comandos.");

        Map<Integer, Double> valoraciones = new HashMap<>();
        ArrayList<Centroide> centroides = new ArrayList<>();
        Map<Integer, ArrayList<Usuario>> clusterMap = new HashMap<>();
        Usuarios usuarios = new Usuarios();

        while (!command.equals("salir")) {
            switch (command) {
                case "constructor vacio":
                    KMeans km = new KMeans();
                    INOUT.writeln("Hecho");
                    command = INOUT.readline();
                    break;

                case "getmaxsc":
                    INOUT.write("El maxsc es determinado durante el periodo de preproceso: ");
                    INOUT.writeln(Parser.getMaxScore());
                    command = INOUT.readline();
                    break;

                case "getminsc":
                    INOUT.write("El minsc es determinado durante el periodo de preproceso: ");
                    INOUT.writeln(Parser.getMinScore());
                    command = INOUT.readline();
                    break;

                case "get centroides":
                    centroides = new ArrayList<>();

                    INOUT.writeln("Por favor introduzca el número de centroides deseado.");
                    int nc = INOUT.readint();
                    INOUT.writeln("A continuación introduzca los identificadores de los " + nc + " centroides. Deben ser únicos.");
                    for (int i = 0; i < nc; ++i){
                        int c = INOUT.readint();
                        INOUT.writeln("Centroide " + c + " leído.");
                        centroides.add(new Centroide(c, valoraciones));
                    }
                    kmeans.setCentroides(centroides);
                    centroides = kmeans.getCentroides();
                    for (Centroide c : centroides)INOUT.writeln("Centroide: " + c.getCentroID());
                    valoraciones.clear();
                    command = INOUT.readline();
                    break;

                case "get k":
                    INOUT.writeln("Por favor introduzca el valor de k deseado.");
                    int k = INOUT.readint();
                    kmeans.setK(k);
                    INOUT.writeln("Nuevo valor de k: " + kmeans.getK());
                    command = INOUT.readline();
                    break;

                case "get cota":
                    INOUT.writeln("Por favor introduzca el valor de cota deseado.");
                    int cota = INOUT.readint();
                    kmeans.setCota(cota);
                    INOUT.writeln("Nuevo valor de cota: " + kmeans.getCota());
                    command = INOUT.readline();
                    break;

                case "get cM":
                    clusterMap = new HashMap<>();
                    ArrayList<Usuario> users = new ArrayList<>();
                    INOUT.writeln("Introduce el número de clusters");
                    int cl = INOUT.readint();
                    for(int i = 0; i < cl; ++i){
                        INOUT.writeln("Introduce el número de usuarios que desee incluir en el cluster " + i);
                        int us = INOUT.readint();
                        INOUT.writeln("A continuación introduzca los identificadores de los " + us + " usuarios. Deben ser únicos.");
                        for (int ii = 0; ii < us; ++ii){
                            int uID = INOUT.readint();
                            INOUT.writeln("Usuario " + uID + " leído.");
                            INOUT.writeln("Introduzca el número de valoraciones para el usuario.");
                            int nV = INOUT.readint();
                            for (int iii = 0; iii < nV; ++iii){
                                INOUT.writeln("Introduzca el id del item valorado por el usuario.");
                                int itID = INOUT.readint();
                                INOUT.writeln("Introduzca el score del item valorado por el usuario.");
                                double val = INOUT.readdouble();
                                valoraciones.put(itID, val);
                                INOUT.writeln("Valoración " + iii +" = " + val + " para el usuario " + uID + " leída.");
                            }
                            users.add(new Usuario(uID, valoraciones));
                            valoraciones.clear();
                        }
                        clusterMap.put(i, users);
                        INOUT.writeln("Cluster " + "leído.");
                        users = new ArrayList<>();
                    }

                    kmeans.setClusterMap(clusterMap);
                    clusterMap =  kmeans.getClusterMap();
                    INOUT.writeln("Estado cluster Map");
                    for (Integer key : clusterMap.keySet()) {

                        INOUT.writeln("Tamaño del centroide " + key + ": " + clusterMap.get(key).size());
                    };
                    command = INOUT.readline();
                    break;

                case "get users cluster":
                    users = new ArrayList<>();
                    INOUT.writeln("Introduce el número de clusters deseado (valor de k)");
                    k = INOUT.readint();
                    kmeans.setK(k);
                    INOUT.writeln("Introduce el número de usuarios");
                    int us = INOUT.readint();
                    INOUT.writeln("A continuación introduzca los identificadores de los " + us + " usuarios. Deben ser únicos.");
                    for (int ii = 0; ii < us; ++ii){
                        int uID = INOUT.readint();
                        INOUT.writeln("Usuario " + uID + " leído.");
                        INOUT.writeln("Introduzca el número de valoraciones para el usuario.");
                        int nV = INOUT.readint();
                        for (int iii = 0; iii < nV; ++iii){
                            INOUT.writeln("Introduzca el id del item valorado por el usuario.");
                            int itID = INOUT.readint();
                            INOUT.writeln("Introduzca el score del item valorado por el usuario.");
                            double val = INOUT.readdouble();
                            valoraciones.put(itID, val);
                            INOUT.writeln("Valoración " + iii +" = " + val + " para el usuario " + uID + " leída.");
                        }
                        users.add(new Usuario(uID, valoraciones));
                        valoraciones.clear();
                    }
                    usuarios = new Usuarios(users);
                    INOUT.writeln("Ejecutando el algoritmo...");
                    //KMeans.clusterFormation();
                    INOUT.writeln("Hecho. Por favor introduzca ahora la información sobre el usuario cuyo cluster desea determinar.");
                    int uID = INOUT.readint();

                    INOUT.writeln("Introduzca el número de valoraciones para el usuario.");
                    int nV = INOUT.readint();
                    for (int i = 0; i < nV; ++i){
                        INOUT.writeln("Introduzca el id del item valorado por el usuario.");
                        int itID = INOUT.readint();
                        INOUT.writeln("Introduzca el score del item valorado por el usuario.");
                        double val = INOUT.readdouble();
                        valoraciones.put(itID, val);
                        INOUT.writeln("Valoración " + i + " = " + val + " para el usuario " + uID + " leída.");
                    }
                    Usuario usuario = new Usuario(uID, valoraciones);
                    //Usuarios clusterDeU = kmeans.getUsersCluster(usuario);
                    INOUT.writeln("El usuario " + usuario.getUserId() + " pertenece al mismo cluster que los usuarios siguientes:" );
                   // for (Usuario x: clusterDeU.getUsers()) INOUT.writeln(x.getUserId());
                    command = INOUT.readline();
                    break;

                case "set centroides":
                    centroides = new ArrayList<>();

                    INOUT.writeln("Por favor introduzca el número de centroides deseado.");
                    nc = INOUT.readint();
                    INOUT.writeln("A continuación introduzca los identificadores de los " + nc + " centroides. Deben ser únicos.");

                    for (int i = 0; i < nc; ++i){
                        int c = INOUT.readint();
                        INOUT.writeln("Centroide " + c + " leído.");
                        INOUT.writeln("Introduzca el número de valoraciones para el centroide " + nc +".");
                        nV = INOUT.readint();
                        for (int j = 0; j < nV; ++j){
                            INOUT.writeln("Introduzca el id del item valorado por el centroide.");
                            int itID = INOUT.readint();
                            INOUT.writeln("Introduzca el score del item valorado por el centroide.");
                            double val = INOUT.readdouble();
                            valoraciones.put(itID, val);
                            INOUT.writeln("Valoración " + j + " = " + val + " para el centroide " + nc + " leída.");
                        }
                        centroides.add(new Centroide(c, valoraciones));
                        valoraciones.clear();
                    }

                    INOUT.writeln("Valor de centroides antes de llamar al setter:");
                    ArrayList<Centroide> antes = kmeans.getCentroides();
                    for (Centroide c : antes) INOUT.writeln(c.getCentroID());

                    kmeans.setCentroides(centroides);

                    INOUT.writeln("Valor de centroides después de llamar al setter:");
                    ArrayList<Centroide> dp = kmeans.getCentroides();
                    for (Centroide c : dp) INOUT.writeln(c.getCentroID());

                    command = INOUT.readline();
                    break;

                case "set cM":
                    clusterMap = new HashMap<>();
                    users = new ArrayList<>();
                    INOUT.writeln("Introduce el número de clusters");
                    cl = INOUT.readint();
                    for(int i = 0; i < cl; ++i){
                        INOUT.writeln("Introduce el número de usuarios que desee incluir en el cluster " + i);
                        us = INOUT.readint();
                        INOUT.writeln("A continuación introduzca los identificadores de los " + us + " usuarios. Deben ser únicos.");
                        for (int ii = 0; ii < us; ++ii){
                            uID = INOUT.readint();
                            INOUT.writeln("Usuario " + uID + " leído.");
                            INOUT.writeln("Introduzca el número de valoraciones para el usuario.");
                            nV = INOUT.readint();
                            for (int iii = 0; iii < nV; ++iii){
                                INOUT.writeln("Introduzca el id del item valorado por el usuario.");
                                int itID = INOUT.readint();
                                INOUT.writeln("Introduzca el score del item valorado por el usuario.");
                                double val = INOUT.readdouble();
                                valoraciones.put(itID, val);
                                INOUT.writeln("Valoración " + iii +" = " + val + " para el usuario " + uID + " leída.");
                            }
                            users.add(new Usuario(uID, valoraciones));
                            valoraciones.clear();
                        }
                        clusterMap.put(i, users);
                        INOUT.writeln("Cluster " + "leído.");
                        users = new ArrayList<>();
                    }

                    INOUT.writeln("Estado del cluster Map antes de llamar al setter");
                    for (Integer key : kmeans.getClusterMap().keySet()) {
                        INOUT.writeln("Tamaño del centroide " + key + ": " + kmeans.getClusterMap().get(key).size());
                    };

                    kmeans.setClusterMap(clusterMap);
                    clusterMap =  kmeans.getClusterMap();
                    INOUT.writeln("Estado del cluster Map ahora");
                    for (Integer key : clusterMap.keySet()) {

                        INOUT.writeln("Tamaño del centroide " + key + ": " + clusterMap.get(key).size());
                    };
                    command = INOUT.readline();
                    break;

                case "set cota":
                    INOUT.writeln("Por favor introduzca el valor de cota deseado.");
                    cota = INOUT.readint();
                    INOUT.writeln("Antiguo valor de cota: " + kmeans.getCota());
                    kmeans.setCota(cota);
                    INOUT.writeln("Nuevo valor de cota: " + kmeans.getCota());
                    command = INOUT.readline();
                    break;

                case "set k":
                    INOUT.writeln("Por favor introduzca el valor de k deseado.");
                    k = INOUT.readint();
                    INOUT.writeln("Antiguo valor de k: " + kmeans.getK());
                    kmeans.setK(k);
                    INOUT.writeln("Nuevo valor de k: " + kmeans.getK());
                    command = INOUT.readline();
                    break;

                case "restore":
                    INOUT.writeln("Por favor introduzca un valor de k para asegurar que al menos un valor sea distinto a los predeterminados.");
                    k = INOUT.readint();
                    kmeans.setK(k);
                    INOUT.writeln("Valores antes del reinicio:");
                    INOUT.writeln("valor de k: " + kmeans.getK());
                    INOUT.writeln("valor de cota: " + kmeans.getCota());
                    for (Centroide c : kmeans.getCentroides()) INOUT.writeln("Centroide: " + c.getCentroID());
                    for (Integer cID: kmeans.getClusterMap().keySet()){
                        for (Usuario usID : kmeans.getClusterMap().get(cID))
                        INOUT.writeln("  usuario: " + usID.getUserId() + " valoraciones: " + usID.getValoraciones().toString());
                    }

                    kmeans.setDefaults();

                    INOUT.writeln("Valores después del reinicio:");
                    INOUT.writeln("valor de k: " + kmeans.getK());
                    INOUT.writeln("valor de cota: " + kmeans.getCota());
                    for (Centroide c : kmeans.getCentroides()) INOUT.writeln("Centroide: " + c.getCentroID());
                    for (Integer cID: kmeans.getClusterMap().keySet()){
                        for (Usuario usID : kmeans.getClusterMap().get(cID))
                            INOUT.writeln("  usuario: " + usID.getUserId() + " valoraciones: " + usID.getValoraciones().toString());
                    }

                    command = INOUT.readline();
                    break;

                case "cluster formation":
                    users = new ArrayList<>();
                    clusterMap = new HashMap<>();
                    INOUT.writeln("Introduce el número de clusters deseado (valor de k)");
                    k = INOUT.readint();
                    kmeans.setK(k);
                    INOUT.writeln("Introduce el número de usuarios");
                    us = INOUT.readint();
                    INOUT.writeln("A continuación introduzca los identificadores de los " + us + " usuarios. Deben ser únicos.");
                    for (int ii = 0; ii < us; ++ii){
                        uID = INOUT.readint();
                        INOUT.writeln("Usuario " + uID + " leído.");
                        INOUT.writeln("Introduzca el número de valoraciones para el usuario.");
                        nV = INOUT.readint();
                        for (int iii = 0; iii < nV; ++iii){
                            INOUT.writeln("Introduzca el id del item valorado por el usuario.");
                            int itID = INOUT.readint();
                            INOUT.writeln("Introduzca el score del item valorado por el usuario.");
                            double val = INOUT.readdouble();
                            valoraciones.put(itID, val);
                            INOUT.writeln("Valoración " + iii +" = " + val + " para el usuario " + uID + " leída.");
                        }
                        users.add(new Usuario(uID, valoraciones));
                        valoraciones.clear();
                    }
                    usuarios = new Usuarios(users);
                    INOUT.writeln("Ejecutando el algoritmo...");
                   // KMeans.clusterFormation(usuarios);
                    INOUT.writeln("Hecho. Mostrando resultados:");
                    clusterMap = kmeans.getClusterMap();
                    for (Integer key : clusterMap.keySet()){
                        INOUT.writeln("Centroide " + key + " size: " + kmeans.getClusterMap().get(key).size());
                        INOUT.writeln("usuarios:");
                        for(Usuario x : kmeans.getClusterMap().get(key)){
                            INOUT.writeln(x.getUserId() + ":");
                            for (Integer v : x.getValoraciones().keySet()) INOUT.write(x.getValoraciones() + " ");
                        }
                    }
                    command = INOUT.readline();
                    break;
                case "dist euclidea":
                    centroides = new ArrayList<>();
                    INOUT.writeln("A continuación escribirá la información de un usuario, seguida de la de un centroide");
                    INOUT.writeln("Primero, introduzca el id del usuario.");
                    uID = INOUT.readint();
                    INOUT.writeln("Usuario " + uID + " leído.");
                    INOUT.writeln("Introduzca el número de valoraciones para el usuario.");
                    nV = INOUT.readint();
                    for (int j = 0; j < nV; ++j){
                        INOUT.writeln("Introduzca el id del item valorado por el usuario.");
                        int itID = INOUT.readint();
                        INOUT.writeln("Introduzca el score del item valorado por el usuario.");
                        double val = INOUT.readdouble();
                        valoraciones.put(itID, val);
                        INOUT.writeln("Valoración " + j +" = " + val + " para el usuario " + uID + " leída.");
                    }
                    INOUT.writeln("A continuación introduzca los id de centroide.");
                    int c = INOUT.readint();
                    INOUT.writeln("Centroide " + c + " leído.");
                    INOUT.writeln("Introduzca el número de valoraciones para el centroide. Recuerde que el centroide valora todos los items existentes");
                    nV = INOUT.readint();
                    for (int j = 0; j < nV; ++j){
                        INOUT.writeln("Introduzca el id del item valorado por el centroide.");
                        int itID = INOUT.readint();
                        INOUT.writeln("Introduzca el score del item valorado por el centroide.");
                        double val = INOUT.readdouble();
                        valoraciones.put(itID, val);
                    }
                    centroides.add(new Centroide(c, valoraciones));
                    kmeans.setCentroides(centroides);
                    INOUT.writeln("Distancia: " + kmeans.computeEuclideanDistance(valoraciones, 0));
                    command = INOUT.readline();
                    break;

                case "random centroids":
                    INOUT.writeln("Por favor introduzca el número de centroides aleatorios que desee crear:");
                    k = INOUT.readint();
                    kmeans.setK(k);
                    INOUT.writeln("Se han creado el doble de centroides aleatorios");
                    kmeans.randomCentroids();

                    centroides = kmeans.getCentroides();

                    for (Centroide ce : centroides) {
                        INOUT.writeln("Centroide " + ce.getCentroID() + " valoraciones: ");
                        for (Integer itID : ce.getImaginaryUsers().keySet()) INOUT.writeln(itID + " = " +   ce.getImaginaryUsers().get(itID));
                    }
                    command = INOUT.readline();
                    break;

                case "furthest centroids":
                    centroides = new ArrayList<>();
                    INOUT.writeln("Por favor introduzca el número de centroides deseado.");
                    nc = INOUT.readint();
                    INOUT.writeln("A continuación introduzca los identificadores de los " + nc + " centroides. Deben ser únicos.");

                    for (int i = 0; i < nc; ++i){
                        c = INOUT.readint();
                        INOUT.writeln("Centroide " + c + " leído.");
                        INOUT.writeln("Introduzca el número de valoraciones para el centroide " + nc +".");
                        nV = INOUT.readint();
                        for (int j = 0; j < nV; ++j){
                            INOUT.writeln("Introduzca el id del item valorado por el centroide.");
                            int itID = INOUT.readint();
                            INOUT.writeln("Introduzca el score del item valorado por el centroide.");
                            double val = INOUT.readdouble();
                            valoraciones.put(itID, val);
                            INOUT.writeln("Valoración " + j + " = " + val + " para el centroide " + nc + " leída.");
                        }
                        centroides.add(new Centroide(c, valoraciones));
                        valoraciones.clear();
                    }
                    kmeans.setCentroides(centroides);
                    INOUT.writeln("Centroides antes de la llamada a furthestCentroids():");
                    centroides = kmeans.getCentroides();
                    for (Centroide ce : centroides) {
                        INOUT.writeln("Centroide " + ce.getCentroID() + " valoraciones: ");
                        for (Integer itID : ce.getImaginaryUsers().keySet()) INOUT.writeln(itID + " = " +   ce.getImaginaryUsers().get(itID));
                    }
                    INOUT.writeln("Eliminando los " + nc + " más cercanos.");
                    kmeans.furthestCentroids();
                    INOUT.writeln("Centroides antes de la llamada a furthestCentroids():");
                    centroides = kmeans.getCentroides();
                    for (Centroide ce : centroides) {
                        INOUT.writeln("Centroide " + ce.getCentroID() + " valoraciones: ");
                        for (Integer itID : ce.getImaginaryUsers().keySet()) INOUT.writeln(itID + " = " +   ce.getImaginaryUsers().get(itID));
                    }
                    command = INOUT.readline();
                    break;

                case "nearest centroid":
                    centroides = new ArrayList<>();
                    INOUT.writeln("Por favor introduzca el número de centroides deseado.");
                    nc = INOUT.readint();
                    INOUT.writeln("A continuación introduzca los identificadores de los " + nc + " centroides. Deben ser únicos.");

                    for (int i = 0; i < nc; ++i){
                        c = INOUT.readint();
                        INOUT.writeln("Centroide " + c + " leído.");
                        INOUT.writeln("Introduzca el número de valoraciones para el centroide " + nc +".");
                        nV = INOUT.readint();
                        for (int j = 0; j < nV; ++j){
                            INOUT.writeln("Introduzca el id del item valorado por el centroide.");
                            int itID = INOUT.readint();
                            INOUT.writeln("Introduzca el score del item valorado por el centroide.");
                            double val = INOUT.readdouble();
                            valoraciones.put(itID, val);
                            INOUT.writeln("Valoración " + j + " = " + val + " para el centroide " + nc + " leída.");
                        }
                        centroides.add(new Centroide(c, valoraciones));
                        valoraciones.clear();
                    }
                    kmeans.setCentroides(centroides);
                    INOUT.writeln("A continuación escribirá la información de un usuario");
                    INOUT.writeln("Primero, introduzca el id del usuario.");
                    uID = INOUT.readint();
                    INOUT.writeln("Usuario " + uID + " leído.");
                    INOUT.writeln("Introduzca el número de valoraciones para el usuario.");
                    nV = INOUT.readint();
                    for (int j = 0; j < nV; ++j){
                        INOUT.writeln("Introduzca el id del item valorado por el usuario.");
                        int itID = INOUT.readint();
                        INOUT.writeln("Introduzca el score del item valorado por el usuario.");
                        double val = INOUT.readdouble();
                        valoraciones.put(itID, val);
                        INOUT.writeln("Valoración " + j +" = " + val + " para el usuario " + uID + " leída.");
                    }

                    INOUT.writeln("El centroide más cercano al usuario introducido es el centroide: " + centroides.get(kmeans.nearestCentroid(valoraciones)).getCentroID());
                    command = INOUT.readline();
                    break;

                case "add to centroid":
                    INOUT.writeln("introduzca el id del usuario que desea añadir");
                    uID = INOUT.readint();
                    INOUT.writeln("Usuario " + uID + " leído.");
                    INOUT.writeln("Introduzca el número de valoraciones para el usuario.");
                    nV = INOUT.readint();
                    for (int i = 0; i < nV; ++i){
                        INOUT.writeln("Introduzca el id del item valorado por el usuario.");
                        int itID = INOUT.readint();
                        INOUT.writeln("Introduzca el score del item valorado por el usuario.");
                        double val = INOUT.readdouble();
                        valoraciones.put(itID, val);
                        INOUT.writeln("Valoración " + i +" = " + val + " para el usuario " + uID + " leída.");
                    }
                    usuario = new Usuario(uID, valoraciones);
                    INOUT.writeln("Añadiendo usuario " + uID + " al cluster del centroide 0");

                    clusterMap = kmeans.getClusterMap();

                    INOUT.writeln("Estado del cluster Map antes de la adición");
                    for (Integer key : clusterMap.keySet()) {

                        INOUT.writeln("Tamaño del centroide " + key + ": " + clusterMap.get(key).size());
                    };

                    kmeans.addToCluster(usuario, 0);
                    clusterMap = kmeans.getClusterMap();

                    INOUT.writeln("Estado del cluster Map después de la adición");
                    for (Integer key : clusterMap.keySet()) {

                        INOUT.writeln("Tamaño del centroide " + key + ": " + clusterMap.get(key).size());
                    };
                    command = INOUT.readline();
                    break;

                case "centroid relocation":
                    centroides = new ArrayList<>();
                    users = new ArrayList<>();

                    INOUT.writeln("Introduce el número de usuarios");
                    us = INOUT.readint();
                    INOUT.writeln("A continuación introduzca los identificadores de los " + us + " usuarios. Deben ser únicos.");
                    for (int i = 0; i < us; ++i){
                        uID = INOUT.readint();
                        INOUT.writeln("Usuario " + uID + " leído.");
                        INOUT.writeln("Introduzca el número de valoraciones para el usuario.");
                        nV = INOUT.readint();
                        for (int j = 0; j < nV; ++j){
                            INOUT.writeln("Introduzca el id del item valorado por el usuario.");
                            int itID = INOUT.readint();
                            INOUT.writeln("Introduzca el score del item valorado por el usuario.");
                            double val = INOUT.readdouble();
                            valoraciones.put(itID, val);
                            INOUT.writeln("Valoración " + j +" = " + val + " para el usuario " + uID + " leída.");
                        }
                        users.add(new Usuario(uID, valoraciones));
                        valoraciones.clear();
                    }

                    INOUT.writeln("Introduzca el identificador del centroide a relocalizar");
                    c = INOUT.readint();
                    INOUT.writeln("Centroide " + c + " leído.");
                    INOUT.writeln("Introduzca el número de valoraciones para el centroide " + c +".");
                    nV = INOUT.readint();
                    for (int j = 0; j < nV; ++j){
                        INOUT.writeln("Introduzca el id del item valorado por el centroide.");
                        int itID = INOUT.readint();
                        INOUT.writeln("Introduzca el score del item valorado por el centroide.");
                        double val = INOUT.readdouble();
                        valoraciones.put(itID, val);
                        INOUT.writeln("Valoración " + j + " = " + val + " para el centroide " + c + " leída.");
                    }
                    centroides.add(new Centroide(c, valoraciones));
                    kmeans.setCentroides(centroides);

                    INOUT.writeln("centroide antes de la relocalización");
                    for (Integer itID : centroides.get(0).getImaginaryUsers().keySet()){
                        INOUT.writeln("Valoración al item " + itID + ": " + centroides.get(0).getImaginaryUsers().get(itID));
                    }
                    kmeans.centroidRelocation(c, valoraciones, users);
                    INOUT.writeln("centroide después de la relocalización");
                    for (Integer itID : centroides.get(0).getImaginaryUsers().keySet()){
                        INOUT.writeln("Valoración al item " + itID + ": " + centroides.get(0).getImaginaryUsers().get(itID));
                    }
                    command = INOUT.readline();
                    break;

                case "terminacion temprana":
                    INOUT.writeln("Especifique el estado inicial de clusterMap");
                    clusterMap = new HashMap<>();
                    users = new ArrayList<>();
                    INOUT.writeln("Introduce el número de clusters");
                    cl = INOUT.readint();
                    for(int i = 0; i < cl; ++i){
                        INOUT.writeln("Introduce el número de usuarios que desee incluir en el cluster " + i);
                        us = INOUT.readint();
                        INOUT.writeln("A continuación introduzca los identificadores de los " + us + " usuarios. Deben ser únicos.");
                        for (int ii = 0; ii < us; ++ii){
                            uID = INOUT.readint();
                            INOUT.writeln("Usuario " + uID + " leído.");
                            INOUT.writeln("Introduzca el número de valoraciones para el usuario.");
                            nV = INOUT.readint();
                            for (int iii = 0; iii < nV; ++iii){
                                INOUT.writeln("Introduzca el id del item valorado por el usuario.");
                                int itID = INOUT.readint();
                                INOUT.writeln("Introduzca el score del item valorado por el usuario.");
                                double val = INOUT.readdouble();
                                valoraciones.put(itID, val);
                                INOUT.writeln("Valoración " + iii +" = " + val + " para el usuario " + uID + " leída.");
                            }
                            users.add(new Usuario(uID, valoraciones));
                            valoraciones.clear();
                        }
                        clusterMap.put(i, users);
                        INOUT.writeln("Cluster " + "leído.");
                        users = new ArrayList<>();
                    }
                    kmeans.setClusterMap(clusterMap);
                    INOUT.writeln("Especifique ahora el estado con el que quiere comparar clusterMap");
                    clusterMap = new HashMap<>();
                    users = new ArrayList<>();
                    INOUT.writeln("Introduce el número de clusters");
                    cl = INOUT.readint();
                    for(int i = 0; i < cl; ++i){
                        INOUT.writeln("Introduce el número de usuarios que desee incluir en el cluster " + i);
                        us = INOUT.readint();
                        INOUT.writeln("A continuación introduzca los identificadores de los " + us + " usuarios. Deben ser únicos.");
                        for (int ii = 0; ii < us; ++ii){
                            uID = INOUT.readint();
                            INOUT.writeln("Usuario " + uID + " leído.");
                            INOUT.writeln("Introduzca el número de valoraciones para el usuario.");
                            nV = INOUT.readint();
                            for (int iii = 0; iii < nV; ++iii){
                                INOUT.writeln("Introduzca el id del item valorado por el usuario.");
                                int itID = INOUT.readint();
                                INOUT.writeln("Introduzca el score del item valorado por el usuario.");
                                double val = INOUT.readdouble();
                                valoraciones.put(itID, val);
                                INOUT.writeln("Valoración " + iii +" = " + val + " para el usuario " + uID + " leída.");
                            }
                            users.add(new Usuario(uID, valoraciones));
                            valoraciones.clear();
                        }
                        clusterMap.put(i, users);
                        INOUT.writeln("Cluster " + "leído.");
                        users = new ArrayList<>();
                    }
                    boolean b = kmeans.shouldTerminate(clusterMap);
                    if (b) INOUT.writeln("El estado no ha variado. El algoritmo terminará.");
                    else INOUT.writeln("Distintos estados. El algoritmo continuará su ejecución.");
                    command = INOUT.readline();
                    break;


                case "help":
                    INOUT.writeln("This are the commands available for this class:");
                    INOUT.writeln("constructor vacio");
                    INOUT.writeln("getmaxsc");
                    INOUT.writeln("getminsc");
                    INOUT.writeln("get centroides");
                    INOUT.writeln("get k");
                    INOUT.writeln("get cota");
                    INOUT.writeln("get cM");
                    INOUT.writeln("get users cluster");
                    INOUT.writeln("set centroides");
                    INOUT.writeln("set cM");
                    INOUT.writeln("set k");
                    INOUT.writeln("set cota");
                    INOUT.writeln("restore");
                    INOUT.writeln("cluster formation");
                    INOUT.writeln("dist euclidea");
                    INOUT.writeln("random centroids");
                    INOUT.writeln("furthest centroids");
                    INOUT.writeln("nearest centroid");
                    INOUT.writeln("add to centroid");
                    INOUT.writeln("centroid relocation");
                    INOUT.writeln("terminacion temprana");
                    INOUT.writeln();
                    break;
                default:
                    INOUT.writeln("Comando incorrecto.");
                    command = INOUT.readline();
            }
            INOUT.writeln();
            INOUT.writeln("Escribe tu comando: ");
            command = INOUT.readline();
        }
    }*/
}
