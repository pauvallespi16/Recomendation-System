package clases;
/** @file Preproceso.java
 @brief Código de la clase Preproceso
 */

import controladores.CtrlDominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/** @class Preproceso
 * @brief Clase utilizada para la lectura y preprocesamiento de los datos.
 *
 * Esta clase lee los datos que nos llegan en los ficheros .csv (items, ratings, ratingsKnown, ratingsUnkown).
 * Trata estos ficheros para poder usar los datos que nos llegan en los algoritmos de recomendación.
 *
 * Además de leer los datos de los diferentes archivos, también se preprocesa la información de estos archivos
 * para facilitar el trabajo a las diferentes clases que hagan uso de estos datos.
 *
 * @author Pau Vallespí Monclús
 * @date December 2021
 */
public class Parser {
    /**
     * @enum Path
     * @brief Utilizado para elegir el path del archivo que queremos leer.
     */
    public enum Path {
        ratings,
        /**
         * @brief < Enum valor: ratings
         */
        ratingsKnown,
        /**
         * @brief < Enum valor: ratingsKnown
         */
        ratingsUnknown    /** @brief < Enum valor: ratingsUnknown */
    }

    /**
     * @var Items items
     * @brief Variable utilizada para almacenar los items
     */
    private static Items items = new Items();

    /**
     * @var Map<Integer, Integer> itemsTable
     * @brief Diccionario utilizado para traducir los ids de los ítems
     */
    private static Map<Integer, Integer> itemsTable = new HashMap<>();

    /**
     * @var Map<Integer, Integer> usersTable
     * @brief Diccionario utilizado para traducir los ids de los usuarios
     */
    private static Map<Integer, Integer> usersTable = new HashMap<>();

    /**
     * @var Map<String, Integer> bitsTable
     * @brief Diccionario utilizado para traducir un género a una posición del bitset del atributo
     */
    private static Map<String, Integer> bitsTable = new HashMap<>();

    /**
     * @var Map<String, Map < String, Integer>> IDF
     * @brief Frecuencia inversa del documento, donde para cada palabra que haya aparecido alguna vez en este atributo
     * de todos los items, tendremos el número de veces que ha aparecido en este atributo de todos los items.
     */
    private static Map<String, Map<String, Integer>> IDF = new HashMap<>();

    /**
     * @var String id_bitset
     * @brief Variable utilizada para controlar la posición que usamos en bitsTable
     */
    private static int bitIndex = 0;

    /**
     * @var String[] types
     * @brief Array utilizado para almacenar los tipos de cada columna del archivo de ítems
     */
    private static String[] types;

    /**
     * @var String[] headersItems
     * @brief Array utilizado para almacenar la cabecera de los ítems
     */
    private static String[] headersItems;

    /**
     * @var String[]words
     * @brief Array utilizado para almacenar los datos de cada fila de los archivos
     */
    private static String[] words;

    /**
     * @var Double[][] auxNormalizeNumeros
     * @brief Array utilizado para almacenar los máximos y mínimos de cada dato númerico para
     * posteriormente normalizar esos datos.
     */
    private static Double[][] auxNormalizeNumeros;

    /**
     * @var Long[][] auxNormalizeFechas
     * @brief Array utilizado para almacenar los máximos y mínimos de cada dato con formato fecha para
     * posteriormente normalizar esos datos.
     */
    private static Long[][] auxNormalizeFechas;

    /**
     * @var double maxScore
     * @brief Variable utilizada para almacenar el máximo de los valores de las valoraciones
     */
    private static double maxScore = 0;

    /**
     * @var double minScore
     * @brief Variable utilizada para almacenar el mínimo de los valores de las valoraciones
     */
    private static double minScore = Double.MAX_VALUE;

    /**
     * @fn Constructora por defecto
     * Constructora para inicializar un objeto de la clase, como los atributos son static no será necesario.
     */
    Parser() {}

    /**
     * @return Items Representa el conjunto de items
     * @fn getAllItems
     * @brief Función utilizada para obtener los items
     * @pre -
     * @post Se ha obtenido Items
     */
    public static Items getAllItems() { return items; }

    /**
     * @param it Representa el conjunto de items
     * @fn setAllItems
     * @brief Función utilizada para establecer los items
     * @pre -
     * @post Se ha establecido Items
     */
    public static void setAllItems(Items it) { items = it; }

    /**
     * @param types contiene los tipos que quieren incluirse
     * @fn setTypes
     * @brief Función utilizada para establecer los valores que contiene el array
     * que almacena los tipos
     * @pre -
     * @post types contiene los tipos de los items
     */
    public static void setTypes(ArrayList<String> types) {
        Parser.types = types.get(0).split(",");
    }

    /**
     * @return String[] Representa un array que contiene los tipos
     * @fn getTypes
     * @brief Función utilizada para obtener el array types
     * que almacena los tipos de los ítems
     * @pre -
     * @post Se ha obtenido headersItems
     */
    public static String[] getTypes() {
        return types;
    }

    /**
     * @param headers contiene los headers que quieren incluirse
     * @fn setHeaders
     * @brief Función utilizada para establecer los valores que contiene el array
     * que almacena la cabecera de los ítems
     * @pre -
     * @post headersItems contiene los valores de headers
     */
    public static void setHeaders(String[] headers) {
        headersItems = headers;
    }

    /**
     * @return String[] Representa un array que contiene las cabeceras
     * @fn getHeaders
     * @brief Función utilizada para obtener el array headers
     * que almacena la cabecera de los ítems
     * @pre -
     * @post Se ha obtenido headersItems
     */
    public static String[] getHeaders() {
        return headersItems;
    }

    /**
     * @fn setScoreLimits
     * @brief Función utilizada para establecer el máximo y el mínimo de las valoraciones
     * @pre max > min
     * @post Se ha establecido el máximo y el mínimo de las valoraciones
     * @param max Representa el máximo
     * @param min Representa el mínimo
     */
    public static void setScoreLimits(double max, double min) {
        maxScore = max;
        minScore = min;
    }

    /**
     * @return Double maxScore
     * @fn getMaxScore
     * @brief Función utilizada para adquirir la variable maxScore.
     * @pre -
     * @post Se ha devuelto maxScore
     */
    public static double getMaxScore() {
        return maxScore;
    }

    /**
     * @return Double minScore
     * @fn getMinScore
     * @brief Función utilizada para adquirir la variable minScore.
     * @pre -
     * @post Se ha devuelto minScore
     */
    public static double getMinScore() {
        return minScore;
    }

    /**
     * @fn setIDF
     * @brief Función utilitzada para establecer el map que representa el IDF
     * @pre Map válido
     * @post Se ha establecido IDF
     * @param idf Map que queremos establecer como IDF
     */
    public static void setIDF(Map<String, Map<String, Integer>> idf) {
        IDF = new HashMap<>(idf);
    }

    /**
     * @fn getIDF
     * @brief Función utilitzada para obtener el map que representa el IDF
     * @pre -
     * @post Se ha obtenido IDF
     */
    public static Map<String, Map<String, Integer>> getIDF() {
        return IDF;
    }

    /**
     * @fn getBitsTable
     * @brief Función utilitzada para obtener bitsTable
     * @pre -
     * @post Se ha obtenido bitsTable
     * @return El map bitsTable
     */
    public static Map<String, Integer> getBitsTable() { return bitsTable; }

    /**
     * @param normNums   Array de arrays que contiene los valores normalizados de los atributos numéricos
     * @param normFechas Array de arrays que contiene los valores normalizados de los atributos de fecha
     * @fn setNormalize
     * @brief Función utilizada en los test para establecer los valores que contienen los arrays
     * utilizados para la posterior normalización de los datos
     * @pre -
     * @post headersItems contiene los valores de headers
     */
    public static void setNormalize(Double[][] normNums, Long[][] normFechas) {
        auxNormalizeNumeros = normNums;
        auxNormalizeFechas = normFechas;
    }

    /**
     * @fn auxNormalizeNumeros
     * @brief Función utilitzada para obtener auxNormalizeNumeros
     * @pre -
     * @post Se ha obtenido auxNormalizeNumeros
     * @return La tabla auxNormalizeNumeros
     */
    public static Double[][] getAuxNormalizeNumeros() { return auxNormalizeNumeros; }

    /**
     * @fn auxNormalizeFechas
     * @brief Función utilitzada para obtener auxNormalizeFechas
     * @pre -
     * @post Se ha obtenido auxNormalizeFechas
     * @return La tabla auxNormalizeFechas
     */
    public static Long[][] getAuxNormalizeFechas() { return auxNormalizeFechas; }

    /**
     * @return String que usaremos en el split
     * @fn validRegex
     * @brief Función auxiliar utilizada para dividir cada línea. Información obtenida de la página web:
     * https://www.it-swarm-es.com/es/java/java-dividir-una-cadena-separada-por-comas-pero-ignorar-comas-entre-comillas/969032997/
     * @pre -
     * @post Se ha devuelto un string
     */
    private static String validRegex() {
        String otherThanQuote = " [^\"] ";
        String quotedString = String.format(" \" %s* \" ", otherThanQuote);
        return String.format("(?x) " + // enable comments, ignore white spaces
                        ",                         " + // match a comma
                        "(?=                       " + // start positive look ahead
                        "  (?:                     " + //   start non-capturing group 1
                        "    %s*                   " + //     match 'otherThanQuote' zero or more times
                        "    %s                    " + //     match 'quotedString'
                        "  )*                      " + //   end group 1 and repeat it zero or more times
                        "  %s*                     " + //   match 'otherThanQuote'
                        "  $                       " + // match the end of the string
                        ")                         ", // stop positive look ahead
                otherThanQuote, quotedString, otherThanQuote);
    }

    /**
     * @param originalId id original del ítem
     * @return Identificador del ítem con "id" = originalId en el archivo original
     * @fn translateItem
     * @brief Función utilizada para obtener el identificador original del ítem
     * @pre -
     * @post Se ha devuelto la traducción del id
     */
    public static int translateItem(int originalId) {
        if (!itemsTable.containsKey(originalId)) return -1;
        return itemsTable.get(originalId);
    }

    /**
     * @param originalId id original del usuario
     * @return Identificador del usuario con userId = originalId en el archivo original
     * @fn translateUser
     * @brief Función utilizada para obtener el identificador original del usuario
     * @pre -
     * @post Se ha devuelto la traducción del id
     */
    public static int translateUser(int originalId) {
        if (!usersTable.containsKey(originalId)) return -1;
        return usersTable.get(originalId);
    }

    /**
     * @fn updateContents
     * @brief Función utilizada para actualizar las funciones auxiliares al introducir un item
     * @pre Item válido
     * @post Estructuras actualizadas
     * @param it Item nuevo
     */
    public static void updateContents(Item it) {
        items.addItem(it);
        itemsTable.put(it.getOriginalId(), it.getId());
        for (int i = 0; i < headersItems.length; i++) {
            String header = headersItems[i];
            Atributo at = it.getAtributos().get(header);

            // separamos por casos según el tipo
            if (at instanceof AtributoNumerico) {
                auxNormalizeNumeros[i][0] = Math.min(auxNormalizeNumeros[i][0], (Double) at.getValor());
                auxNormalizeNumeros[i][1] = Math.max(auxNormalizeNumeros[i][1], (Double) at.getValor());
            } else if (at instanceof AtributoFecha) {
                auxNormalizeFechas[i][0] = Math.min(auxNormalizeFechas[i][0], (Long) at.getValor());
                auxNormalizeFechas[i][1] = Math.max(auxNormalizeFechas[i][1], (Long) at.getValor());
            } else if (at instanceof AtributoCategoricoMultiple) {
                Set<String> set = ((AtributoCategoricoMultiple) at).getValor();
                for (String s : set) { if (!bitsTable.containsKey(s)) { bitsTable.put(s, bitIndex); bitIndex++; } }
                ((AtributoCategoricoMultiple) at).setBitSetSize(bitsTable.size());
                for (String s : set) ((AtributoCategoricoMultiple) at).setBitSetValue(bitsTable.get(s));
            } else if (at instanceof AtributoTexto) {
                Map<String, Integer> freq = ((AtributoTexto) at).getValor();
                Map<String, Integer> idfMap = IDF.get(header);
                for (Map.Entry<String, Integer> word : freq.entrySet()) {
                    if (idfMap.containsKey(word.getKey())) {
                        int n = idfMap.get(word.getKey());
                        idfMap.put(word.getKey(), n + 1);
                    } else idfMap.put(word.getKey(), 1);
                }
                IDF.put(headersItems[i], idfMap);
            }
        }
        normalizarItems();
    }

    /**
     * @fn normalizarItems
     * @brief Función utilizada para normalizar los atributos númericos y de fecha de los ítems
     * @pre Items no vacío
     * @post Para cada ítem se han normalizado sus atributos numéricos y fechas
     */
    public static void normalizarItems() {
        ArrayList<Item> arr = new ArrayList(items.getItems().values());

        for (int i = 0; i < arr.size(); i++) {
            Item it = arr.get(i);
            Map<String, Atributo> atts = it.getAtributos();

            // iteramos sobre todos los elementos de la cabecera
            for (int j = 0; j < headersItems.length; j++) {
                Atributo atr = atts.get(headersItems[j]);

                // si el elemento es texto
                if (atr instanceof AtributoTexto) {
                    Map<String, Integer> idfMap = IDF.get(headersItems[j]);
                    Map<String, Integer> freq = ((AtributoTexto) atr).getValor();
                    for (Map.Entry<String, Integer> word : freq.entrySet()) {
                        if (idfMap.containsKey(word.getKey())) {
                            int n = idfMap.get(word.getKey());
                            idfMap.put(word.getKey(), n + 1);
                        } else idfMap.put(word.getKey(), 1);
                    }
                    IDF.put(headersItems[j], idfMap);
                }

                // si el elemento es categórico múltiple
                if (atr instanceof AtributoCategoricoMultiple) {
                    ((AtributoCategoricoMultiple) atr).setBitSetSize(bitsTable.size());
                    // ponemos a 1 los bits que representen los strings del atributo
                    for (String s : ((AtributoCategoricoMultiple) atr).getValor()) {
                        ((AtributoCategoricoMultiple) atr).setBitSetValue(bitsTable.get(s));
                    }
                }

                // si el elemento es númerico o de fecha
                if (atr instanceof AtributoNumerico || atr instanceof AtributoFecha) {
                    // norm = (num - min) / (max - min)
                    double valorNormalizado;

                    if (atr instanceof AtributoNumerico) {
                        // el caso de las series en el que hay un valor erróneo
                        if (((AtributoNumerico) atr).getValor() == Double.MAX_VALUE) {
                            ((AtributoNumerico) atr).setValorNormalizado(-1.0);
                        } else {
                            double minNum = auxNormalizeNumeros[j][0];
                            double maxNum = auxNormalizeNumeros[j][1];
                            valorNormalizado = (((AtributoNumerico) atr).getValor() - minNum) / (maxNum - minNum);
                            ((AtributoNumerico) atr).setValorNormalizado(valorNormalizado);
                        }
                    }

                    if (atr instanceof AtributoFecha) {
                        long minLong = auxNormalizeFechas[j][0];
                        long maxLong = auxNormalizeFechas[j][1];
                        valorNormalizado = (double) (((AtributoFecha) atr).getValor() - minLong) / (maxLong - minLong);
                        ((AtributoFecha) atr).setValorNormalizado(valorNormalizado);
                    }

                    // actualizamos el ítem
                    it.setAtributo(headersItems[j], atr);
                }
            }

            // actualizamos el array
            arr.set(i, it);
        }
        items.setItems(arr);
    }

    /**
     * @param i Índice para el array "types" para obtener el tipo del elemento
     * @return Atributo del tipo que especifiquemos
     * @fn transformarAtributo
     * @brief Función utilizada para transformar el Atributo en el tipo que realmente sea
     * @pre Entero válido que represente una posición en el array words
     * @post Se ha devuelto un atributo del tipo que sea requerido
     */
    private static Atributo transformarAtributo(int i) throws ParseException {
        switch (types[i]) {
            case "bool":
                return new AtributoBoolean(words[i]);
            case "string":
                return new AtributoCategorico(words[i]);
            case "double":
                Atributo atrNum = new AtributoNumerico(words[i]);
                if (!words[i].equals("") && !words[i].equals("-9223372036854775808")) {
                    auxNormalizeNumeros[i][0] = Math.min(auxNormalizeNumeros[i][0], (Double) atrNum.getValor());
                    auxNormalizeNumeros[i][1] = Math.max(auxNormalizeNumeros[i][1], (Double) atrNum.getValor());
                } else atrNum = new AtributoNumerico(String.valueOf(Double.MAX_VALUE));
                return atrNum;
            case "set":
                String[] list = words[i].split(";");
                for (String s : list) {
                    if (!bitsTable.containsKey(s)) {
                        bitsTable.put(s, bitIndex);
                        bitIndex++;
                    }
                }
                return new AtributoCategoricoMultiple(words[i]);
            case "fecha":
                Atributo atrFecha = new AtributoFecha(words[i]);
                auxNormalizeFechas[i][0] = Math.min(auxNormalizeFechas[i][0], (Long) atrFecha.getValor());
                auxNormalizeFechas[i][1] = Math.max(auxNormalizeFechas[i][1], (Long) atrFecha.getValor());
                return atrFecha;
            case "texto":
                return new AtributoTexto(words[i]);
            default:
                return null;
        }
    }

    /**
     * @fn processItems
     * @brief Función utilizada para preprocesar los ítems
     * @pre -
     * @post Se han guardado los datos sobre los ítems en la estructura
     * de datos correspondiente
     * @param data Datos sobre los items
     */
    public static void processItems(ArrayList<String> data) throws Exception {
        // obtenemos la cabecera y los tipos
        headersItems = data.get(0).split(",");

        auxNormalizeNumeros = new Double[headersItems.length][2];
        auxNormalizeFechas = new Long[headersItems.length][2];

        // inicializamos la estructura de datos al máximo y mínimo valor respectivamente
        for (int i = 0; i < headersItems.length; i++) {
            auxNormalizeNumeros[i][0] = Double.MAX_VALUE;
            auxNormalizeNumeros[i][1] = Double.MIN_VALUE;
            auxNormalizeFechas[i][0] = Long.MAX_VALUE;
            auxNormalizeFechas[i][1] = Long.MIN_VALUE;
        }

        int itemId = 0;
        for (int line = 1; line < data.size(); line++) {
            words = data.get(line).split(validRegex(), -1);

            // if para tratar los saltos de linea
            if (words.length != headersItems.length) {
                String actualLine = data.get(line) + data.get(line + 1);
                words = actualLine.split(validRegex(), -1);
                data.set(line + 1, actualLine);
            } else {
                // iteramos sobre todos elementos de la cabecera y lo asignamos con su tipo
                // correspondiente
                Item item = new Item(itemId, new HashMap<>());
                for (int i = 0; i < headersItems.length; i++) {
                    if (!types[i].equals("other")) {
                        Atributo atr = transformarAtributo(i);
                        IDF.put(headersItems[i], new HashMap<>());

                        // guardamos el "id" original para posteriormente traducir
                        if (headersItems[i].equals("id") && !words[i].equals("")) {
                            itemsTable.put(Integer.parseInt(words[i]), itemId);
                            item.setOriginalId(Integer.parseInt(words[i]));
                        }
                        item.setAtributo(headersItems[i], atr);
                    }
                }
                itemId++;

                // añadimos el ítem
                items.addItem(item);
            }
        }
        normalizarItems();
    }

    /**
     * @fn processUsers
     * @brief Función utilizada para procesar los usuarios y sus valoraciones
     * @pre Parámetro correcto que represente uno de los paths disponibles
     * @post Se han guardado los datos sobre los usuarios en la estructura
     * de datos correspondiente
     * @param data Datos sobre los usuarios
     * @param op Enum referenciando el path del archivo que queremos leer
     * @return Usuarios
     */
    public static Usuarios processUsers(ArrayList<String> data, Path op) throws Exception {
        Usuarios users = new Usuarios();

        // generamos su id manualmente
        int userId = 0;

        String[] headersUsers = data.get(0).split(",");

        for (int line = 1; line < data.size(); line++) {
            words = data.get(line).split(validRegex(), -1);

            int originalId = 0;
            int originalItemId = 0;
            double score = 0;

            for (int i = 0; i < headersUsers.length; i++) {
                // enlazamos cada elemento de la cabecera
                if (headersUsers[i].contains("user")) originalId = Integer.parseInt(words[i]);
                if (headersUsers[i].contains("item")) originalItemId = Integer.parseInt(words[i]);
                if (headersUsers[i].equals("rating")) score = Double.parseDouble(words[i]);
            }

            // obtenemos el "id" del ítem generado manualmente mediante itemsTable
            int itemId = itemsTable.get(originalItemId);
            Item it = items.getItem(itemId);

            boolean inList = true; // variable usada para comprobar que un usuario esté en el array
            Map<Integer, Double> valoraciones = new HashMap<>();

            if (usersTable.containsKey(originalId)) {
                if (users.containsUser(usersTable.get(originalId)))
                    valoraciones = users.getUser(usersTable.get(originalId)).getValoraciones();
                else inList = false;
            } else {
                usersTable.put(originalId, userId);
                inList = false;
            }

            // añadimos la valoración
            valoraciones.put(itemId, score);

            if (maxScore < score) maxScore = score;
            if (minScore > score) minScore = score;

            // actualizamos el ítem
            items.setItem(itemId, it);

            // creamos un nuevo usuario con los ítems
            Usuario user = new Usuario(usersTable.get(originalId), valoraciones);
            user.setOriginalUserId(originalId);

            // añadimos el usuario si no estaba, sino simplemente lo modificamos
            if (inList) users.setUser(usersTable.get(originalId), user);
            else {
                users.addUser(user);
                userId++;
            }
        }

        return users;
    }

    /**
     * @fn loadItems
     * @brief Función utilizada para cargar los ítems
     * @pre -
     * @post Se han guardado los datos sobre los ítems en la estructura
     * de datos correspondiente
     * @param data Datos sobre los items
     */
    public static void loadItems(ArrayList<String> data) throws Exception {
        items = new Items();

        // variables que iremos reutilizando
        int itemId = 0, originalId = 0, size = 0;
        Map<String, Atributo> atrs = new HashMap<>();

        ArrayList<String> headers = new ArrayList<>();
        ArrayList<String> tipos = new ArrayList<>();

        ArrayList<ArrayList<Double>> tablaNormNums = new ArrayList<>();
        ArrayList<ArrayList<Long>> tablaNormFechas = new ArrayList<>();

        boolean finishedHeaders = false;
        String typeOfData = "BITSTABLE";
        for (String datum : data) {
            String[] dataLine = datum.split("\\|");

            // cambiamos el modo de lectura segun el titulo
            if (datum.equals("ITEMS") || datum.equals("NORMITEMS") || datum.equals("NORMFECHAS")) {
                typeOfData = datum;
                continue;
            }

            // segun el tipo
            if (datum.equals("#")) {
                finishedHeaders = true;

                // creamos el objeto
                Item it = new Item(itemId, atrs);
                it.setOriginalId(originalId);
                items.addItem(it);

                itemId++;
                atrs = new HashMap<>();
            } else if (typeOfData.equals("BITSTABLE")) {
                String[] splitedData = datum.split(";");
                if (splitedData.length > 1) bitsTable.put(splitedData[0], Integer.parseInt(splitedData[1]));
            } else if (typeOfData.equals("NORMITEMS")) {
                String[] splitedData = datum.split(";");
                ArrayList<Double> vals = new ArrayList<>();
                vals.add(Double.parseDouble(splitedData[0]));
                vals.add(Double.parseDouble(splitedData[1]));
                tablaNormNums.add(vals);
            } else if (typeOfData.equals("NORMFECHAS")){
                String[] splitedData = datum.split(";");
                ArrayList<Long> vals = new ArrayList<>();
                vals.add(Long.parseLong(splitedData[0]));
                vals.add(Long.parseLong(splitedData[1]));
                tablaNormFechas.add(vals);
            } else if (typeOfData.equals("ITEMS")) {
                if (size == 0) size = bitsTable.size();
                if (!finishedHeaders) {
                    headers.add(dataLine[0]);
                }
                String tipoAtr = dataLine[1];

                // tenemos en cuenta el id original
                if (dataLine[0].equals("id")) {
                    originalId = Integer.parseInt(dataLine[2]);
                    itemsTable.put(originalId, itemId);
                }

                // dividimos y instanciamos los atributos segun el archivo
                switch (tipoAtr) {
                    case "AtributoNumerico":
                        AtributoNumerico atrNum = new AtributoNumerico(dataLine[2]);
                        double valueNorm = Double.parseDouble(dataLine[3]);
                        atrNum.setValorNormalizado(valueNorm);
                        atrs.put(dataLine[0], atrNum);
                        if (!finishedHeaders) { tipos.add("double"); }
                        break;
                    case "AtributoBoolean":
                        AtributoBoolean atrBool = new AtributoBoolean(dataLine[2]);
                        atrs.put(dataLine[0], atrBool);
                        if (!finishedHeaders) { tipos.add("bool"); }
                        break;
                    case "AtributoFecha":
                        AtributoFecha atrFecha = new AtributoFecha(Long.parseLong(dataLine[2]));
                        valueNorm = Double.parseDouble(dataLine[3]);
                        atrFecha.setValorNormalizado(valueNorm);
                        atrs.put(dataLine[0], atrFecha);
                        if (!finishedHeaders) { tipos.add("fecha"); }
                        break;
                    case "AtributoCategorico":
                        AtributoCategorico atrCat = new AtributoCategorico("");
                        if (dataLine.length > 2)
                            atrCat = new AtributoCategorico(dataLine[2]);
                        atrs.put(dataLine[0], atrCat);
                        if (!finishedHeaders) { tipos.add("string"); }
                        break;
                    case "AtributoCategoricoMultiple":
                        AtributoCategoricoMultiple atrCatMult = new AtributoCategoricoMultiple("");
                        // primero convertimos a set
                        if (dataLine.length > 2) {
                            String dataLineFixed = dataLine[2].replace("[", "").replace("]", "").replace(", ", ";");
                            atrCatMult = new AtributoCategoricoMultiple(dataLineFixed);

                            //ahora convertimos el bitset
                            if (dataLine.length > 3) {
                                dataLineFixed = dataLine[3].replace("{", "").replace("}", "").replace(", ", ";");
                                String[] valores = dataLineFixed.split(";");
                                atrCatMult.setBitSetSize(size);
                                for (String val : valores) {
                                    int n = Integer.parseInt(val);
                                    atrCatMult.setBitSetValue(n);
                                }
                                int idx = 0;
                            }
                        }
                        atrs.put(dataLine[0], atrCatMult);
                        if (!finishedHeaders) { tipos.add("set"); }
                        break;
                    case "AtributoTexto":
                        String dataLineFixed = (dataLine[2].replace("{", "")).replace("}", "");
                        Map<String, Integer> valoresMap = new HashMap<>();
                        String[] valores = dataLineFixed.split(",");

                        // convertimos el map
                        for (String line : valores) {
                            String[] vals = line.split("=");
                            if (vals.length > 1) valoresMap.put(vals[0].trim(), Integer.parseInt(vals[1]));
                        }
                        AtributoTexto atrText = new AtributoTexto(valoresMap, Integer.parseInt(dataLine[3]));
                        atrs.put(dataLine[0], atrText);
                        if (!finishedHeaders) { tipos.add("texto"); }
                        break;
                }
            }
        }

        // convertimso arrayList a arrays
        headersItems = headers.toArray(new String[0]);
        types = tipos.toArray(new String[0]);

        // convertimos los arrayList a las matrices
        auxNormalizeNumeros = new Double[tablaNormNums.size()][2];
        auxNormalizeFechas = new Long[tablaNormFechas.size()][2];
        for (int i = 0; i < tablaNormNums.size(); i++)  {
            auxNormalizeNumeros[i][0] = tablaNormNums.get(i).get(0);
            auxNormalizeNumeros[i][1] = tablaNormNums.get(i).get(1);
            auxNormalizeFechas[i][0] = tablaNormFechas.get(i).get(0);
            auxNormalizeFechas[i][1] = tablaNormFechas.get(i).get(1);
        }
    }

    /**
     * @fn loadUsers
     * @brief Función utilizada para cargar los usuarios y sus valoraciones
     * @pre Parámetro correcto que represente uno de los paths disponibles
     * @post Se han guardado los datos sobre los usuarios en la estructura
     * de datos correspondiente
     * @param data Datos sobre los usuarios
     * @param op Enum referenciando el path del archivo que queremos leer
     * @return Usuarios
     */
    public static Usuarios loadUsers(ArrayList<String> data, Path op) throws Exception {
        Usuarios users = new Usuarios();

        // variables que iremos reutilizando
        int userId = 0, originalId = 0;
        Map<Integer, Double> valoraciones = new HashMap<>();

        for (String datum : data) {
            String[] dataLine = datum.split(" ");
            if (dataLine.length == 1 && !datum.equals("|")) {
                // caso en el que es un id
                originalId = Integer.parseInt(dataLine[0]);
                usersTable.put(originalId, userId);
            } else if (dataLine.length == 2) {
                // caso en el que hay valoraciones
                int itemId = Integer.parseInt(dataLine[0]);
                double score = Double.parseDouble(dataLine[1]);
                valoraciones.put(itemId, score);
            } else if (datum.equals("|")) {
                // caso en el que hay que cambiar de usuario
                boolean inList = true;
                if (usersTable.containsKey(originalId)) {
                    if (users.containsUser(usersTable.get(originalId)))
                        valoraciones = users.getUser(usersTable.get(originalId)).getValoraciones();
                    else inList = false;
                } else { usersTable.put(originalId, userId); inList = false; }

                // creamos un nuevo usuario con los ítems
                Usuario us = new Usuario(usersTable.get(originalId), valoraciones);
                us.setOriginalUserId(originalId);

                // añadimos el usuario si no estaba, sino simplemente lo modificamos
                if (inList) users.setUser(usersTable.get(originalId), us);
                else { users.addUser(us); userId++; }

                valoraciones = new HashMap<>();
            }
        }
        return users;
    }

    /**
     * @fn loadRecoms
     * @brief Función utilizada para cargar una recomendacion
     * @pre -
     * @post Se han guardado los datos sobre la recomendacion en la estructura
     * de datos correspondiente
     * @param u Usuario del que queremos cargar la informacion
     * @param us Conjunto de usuarios
     * @param data Datos sobre el conjunto de recomendaciones
     * @return Recomendacion
     */
    public static Recomendacion loadRecoms(Usuario u, Usuarios us, ArrayList<String> data) throws Exception {
        Map<Integer, Double> mapRecom = new HashMap<>();
        for (String datum : data) {
            String[] datos = datum.split(",");
            mapRecom.put(Integer.parseInt(datos[0]), Double.parseDouble(datos[1]));
        }
        return new Recomendacion(u, us, mapRecom);
    }

    /**
     * @fn loadHistorial
     * @brief Función utilizada para cargar un conjunto de recomendaciones
     * @pre -
     * @post Se han guardado los datos sobre el conjunto de recomendaciones en la estructura
     * de datos correspondiente
     * @param data Datos sobre el conjunto de recomendaciones
     * @return Recomendaciones que se han pedido
     */
    public static Recomendaciones loadHistorial(ArrayList<String> data) throws Exception {
        ArrayList<Recomendacion> recomendaciones = new ArrayList<>();
        for (String datum : data) {
            String[] aux = datum.split(",");
            Recomendacion recom =  CtrlDominio.cargarRecomendacion(aux[0]);
            recom.setName(aux[0]);
            recom.setType(aux[1]);
            recom.setDate(aux[2]);
            recomendaciones.add(recom);
        }
        return new Recomendaciones(recomendaciones);
    }
}