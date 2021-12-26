/** @file ParserTest.java
 @brief Código de la clase de pruebas de la clase Parser
 */

import clases.*;
import controladores.CtrlDominio;
import controladores.CtrlDominioPreprocesarDatos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** @class ParserTest
 * @brief Tiene las pruebas implementadas para comprobar que funcionan los métodos de la clase Preprocess.
 *
 * @author Pau Vallespí Monclús
 *
 * @date November 2021
 */
public class ParserTest {
    /** @fn testNormalizarItems
     * @brief Test para comprobar las funcionalidades de normalizarItems de la clase Preproceso
     */
    @Test
    @DisplayName("Normalizar ítems")
    void testNormalizarItems() throws Exception {
        Map<String, Atributo> atr0 = new HashMap<>();
        atr0.put("a0", new AtributoNumerico("4"));
        atr0.put("a1", new AtributoNumerico("7"));
        atr0.put("a2", new AtributoNumerico("-1"));
        atr0.put("a3", new AtributoFecha("2003-08-23"));

        Item i0 = new Item(0, atr0);

        Map<String, Atributo> atr1 = new HashMap<>();
        atr1.put("a0", new AtributoNumerico("-5"));
        atr1.put("a1", new AtributoNumerico("2"));
        atr1.put("a2", new AtributoNumerico("8"));
        atr1.put("a3", new AtributoFecha("2020-01-01"));

        Item i1 = new Item(1, atr1);

        Map<String, Atributo> atr2 = new HashMap<>();
        atr2.put("a0", new AtributoNumerico("-0.5"));
        atr2.put("a1", new AtributoNumerico("13"));
        atr2.put("a2", new AtributoNumerico("5"));
        atr2.put("a3", new AtributoFecha("1998-03-13"));

        Item i2 = new Item(2, atr2);

        ArrayList <Item> itemsAux = new ArrayList<>();
        itemsAux.add(i0);
        itemsAux.add(i1);
        itemsAux.add(i2);
        Parser.setAllItems(new Items (itemsAux));

        String[] headers = {"a0", "a1", "a2", "a3"};

        Double[][] aux = {{-5.0, 4.0}, {2.0, 13.0}, {-1.0, 8.0}, {}};
        Long[][] auxLong = {{}, {}, {}, {889743600000L, 1577833200000L}};

        Parser.setHeaders(headers);
        Parser.setNormalize(aux, auxLong);
        Parser.normalizarItems();

        AtributoNumerico a0_0 = (AtributoNumerico) Parser.getAllItems().getItem(0).getAtributo("a0");
        AtributoNumerico a0_1 = (AtributoNumerico) Parser.getAllItems().getItem(0).getAtributo("a1");
        AtributoNumerico a0_2 = (AtributoNumerico) Parser.getAllItems().getItem(0).getAtributo("a2");
        AtributoFecha a0_3 = (AtributoFecha) Parser.getAllItems().getItem(0).getAtributo("a3");

        AtributoNumerico a1_0 = (AtributoNumerico) Parser.getAllItems().getItem(1).getAtributo("a0");
        AtributoNumerico a1_1 = (AtributoNumerico) Parser.getAllItems().getItem(1).getAtributo("a1");
        AtributoNumerico a1_2 = (AtributoNumerico) Parser.getAllItems().getItem(1).getAtributo("a2");
        AtributoFecha a1_3 = (AtributoFecha) Parser.getAllItems().getItem(1).getAtributo("a3");

        AtributoNumerico a2_0 = (AtributoNumerico) Parser.getAllItems().getItem(2).getAtributo("a0");
        AtributoNumerico a2_1 = (AtributoNumerico) Parser.getAllItems().getItem(2).getAtributo("a1");
        AtributoNumerico a2_2 = (AtributoNumerico) Parser.getAllItems().getItem(2).getAtributo("a2");
        AtributoFecha a2_3 = (AtributoFecha) Parser.getAllItems().getItem(2).getAtributo("a3");

        assertEquals(1, a0_0.getValorNormalizado());
        assertEquals(0.45454545454545453, a0_1.getValorNormalizado());
        assertEquals(0, a0_2.getValorNormalizado());
        assertEquals(0.24974363803783695, a0_3.getValorNormalizado());

        assertEquals(0, a1_0.getValorNormalizado());
        assertEquals(0, a1_1.getValorNormalizado());
        assertEquals(1, a1_2.getValorNormalizado());
        assertEquals(1, a1_3.getValorNormalizado());

        assertEquals(0.5, a2_0.getValorNormalizado());
        assertEquals(1, a2_1.getValorNormalizado());
        assertEquals(0.6666666666666666, a2_2.getValorNormalizado());
        assertEquals(0, a2_3.getValorNormalizado());

        Parser.setAllItems(new Items (new ArrayList<>()));
    }

    /** @fn testReadItems
     * @brief Test para comprobar las funcionalidades de readItems de la clase Preproceso
     */
    @Test
    @DisplayName("Leer ítems")
    void testReadItems() throws Exception {
        CtrlDominioPreprocesarDatos.preprocesarTypes("DATA/csv/data.peliculas/250/types.csv");
        CtrlDominioPreprocesarDatos.preprocesarItems("DATA/csv/data.peliculas/250/items.csv");
        Map<String, Atributo> atr0 = new HashMap<>();
        atr0.put("adult", new AtributoBoolean("false"));
        atr0.put("belongs_to_collection", new AtributoCategorico(""));
        atr0.put("budget", new AtributoNumerico("98000000"));
        atr0.put("genres", new AtributoCategoricoMultiple("Action;Adventure"));

        Map<String, Atributo> atr1 = new HashMap<>();
        atr1.put("adult", new AtributoBoolean("false"));
        atr1.put("belongs_to_collection", new AtributoCategorico(""));
        atr1.put("budget", new AtributoNumerico("4000000"));
        atr1.put("genres", new AtributoCategoricoMultiple("Crime;Comedy"));

        Map<String, Atributo> atr2 = new HashMap<>();
        atr2.put("adult", new AtributoBoolean("false"));
        atr2.put("belongs_to_collection", new AtributoCategorico(""));
        atr2.put("budget", new AtributoNumerico("0"));
        atr2.put("genres", new AtributoCategoricoMultiple("Music;Drama;Family"));

        Item i0 = new Item (0, atr0);
        Item i1 = new Item (1, atr1);
        Item i2 = new Item (2, atr2);

        Item i0_Test = Parser.getAllItems().getItem(0);
        Item i1_Test = Parser.getAllItems().getItem(1);
        Item i2_Test = Parser.getAllItems().getItem(2);

        Map<String, Atributo> atr0_Test = i0_Test.getAtributos();
        Map<String, Atributo> atr1_Test = i1_Test.getAtributos();
        Map<String, Atributo> atr2_Test = i2_Test.getAtributos();

        // item 0
        assertEquals(false, atr0_Test.get("adult").getValor(), "failed item 0");
        assertEquals("", atr0_Test.get("belongs_to_collection").getValor(), "failed item 0");
        assertEquals(Double.parseDouble("98000000"), atr0_Test.get("budget").getValor(), "failed item 0");
        AtributoCategoricoMultiple at = (AtributoCategoricoMultiple) atr0_Test.get("genres"); int i = 0;
        Set<String> set = at.getValor();
        for (String s : set) {
            if (i == 0) assertEquals("Action", s, "failed item 0");
            if (i == 1) assertEquals("Adventure", s, "failed item 0");
            i++;
        }

        // item 1
        assertEquals(false, atr1_Test.get("adult").getValor(), "failed item 1");
        assertEquals("", atr1_Test.get("belongs_to_collection").getValor(), "failed item 1");
        assertEquals(Double.parseDouble("4000000"), atr1_Test.get("budget").getValor(), "failed item 1");
        at = (AtributoCategoricoMultiple) atr1_Test.get("genres"); i = 0;
        set = at.getValor();
        for (String s : set) {
            if (i == 0) assertEquals("Comedy", s, "failed item 1");
            if (i == 1) assertEquals("Crime", s, "failed item 1");
            i++;
        }

        // item 2
        assertEquals(false, atr2_Test.get("adult").getValor(), "failed item 2");
        assertEquals("", atr2_Test.get("belongs_to_collection").getValor(), "failed item 2");
        assertEquals(Double.parseDouble("0"), atr2_Test.get("budget").getValor(), "failed item 2");
        at = (AtributoCategoricoMultiple) atr2_Test.get("genres"); i = 0;
        set = at.getValor();
        for (String s : set) {
            if (i == 0) assertEquals("Drama", s, "failed item 2");
            if (i == 1) assertEquals("Family", s, "failed item 2");
            if (i == 2) assertEquals("Music", s, "failed item 2");
            i++;
        }
    }

    /** @fn testReadUsers
     * @brief Test para comprobar las funcionalidades de readUsers de la clase Preproceso
     */
    @Test
    @DisplayName("Leer usuarios")
    void testReadUsers() throws Exception {
        CtrlDominioPreprocesarDatos.preprocesarArchivos("DATA/csv/data.peliculas/250/");
        Usuarios users = CtrlDominio.getUsuariosRatings();

        Map<Integer, Double> valoracionUser0 = new HashMap<>();
        valoracionUser0.put(Parser.translateItem(296), 3.0);
        valoracionUser0.put(Parser.translateItem(318), 4.0);
        valoracionUser0.put(Parser.translateItem(480), 3.0);
        valoracionUser0.put(Parser.translateItem(527), 4.0);
        valoracionUser0.put(Parser.translateItem(780), 2.5);
        valoracionUser0.put(Parser.translateItem(858), 4.5);
        valoracionUser0.put(Parser.translateItem(1213), 4.0);
        valoracionUser0.put(Parser.translateItem(2959), 3.5);

        Map<Integer, Double> valoracionUser1 = new HashMap<>();
        valoracionUser1.put(Parser.translateItem(231), 5.0);
        valoracionUser1.put(Parser.translateItem(1580), 4.0);
        valoracionUser1.put(Parser.translateItem(6373), 5.0);
        valoracionUser1.put(Parser.translateItem(8874), 5.0);
        valoracionUser1.put(Parser.translateItem(68954), 4.0);

        Map<Integer, Double> valoracionUser2 = new HashMap<>();
        valoracionUser2.put(Parser.translateItem(910), 4.0);
        valoracionUser2.put(Parser.translateItem(2699), 4.5);

        Usuario user0 = new Usuario(Parser.translateUser(50), valoracionUser0);
        Usuario user1 = new Usuario(Parser.translateUser(420), valoracionUser1);
        Usuario user2 = new Usuario(Parser.translateUser(841), valoracionUser2);

        // valoracion user0
        assertEquals(user0.getValoraciones(), users.getUser(Parser.translateUser(50)).getValoraciones());

        // valoracion user1
        assertEquals(user1.getValoraciones(), users.getUser(Parser.translateUser(420)).getValoraciones());

        // valoracion user2
        assertEquals(user2.getValoraciones(), users.getUser(Parser.translateUser(841)).getValoraciones());
    }
}
