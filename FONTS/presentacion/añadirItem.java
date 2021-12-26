package presentacion;
/** @file añadirItem.java
 @brief Codigo de la añadir items
 */
import clases.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import presentacion.CtrlPresentacion;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.*;
import java.util.*;

/**
 * @author Carla Campàs Gené
 * @class vistaRecomendaciones
 * @brief La clase de la vista para añadir items
 * @date December 2021
 */
public class añadirItem extends JFrame {
    CtrlPresentacion pCtrlPresentacion;
    private JPanel panel1;
    private JScrollPane scrollContentPanel;
    private JPanel contentsPanel;
    private JButton añadirItemButton;
    private JButton modificarItemButton;
    private JButton volverButton;
    private JTable table1;
    private JScrollPane scrollPane1;
    private String[] headers;
    private String[] types;
    private ArrayList<JPanel> dynPanels = new ArrayList<>();
    private ArrayList<JComponent> dataComponents = new ArrayList<>();
    private Map<String, Atributo> attributes = new HashMap<>();
    private String id;

    Map<Integer, Double> atributos;
    private JLabel[] labels;
    private JMenuBar menubar;
    private JMenu actividad;
    private JMenuItem[] itemsMenu;

    private void setAttributes() throws ParseException {
        int p = 0;
        for (String t : types) {
            Atributo at = null;
            JComponent jc = dataComponents.get(p);
            switch (t) {
                case "bool":
                    JCheckBox cbComp = (JCheckBox) jc;
                    if (cbComp.isSelected()) at = new AtributoBoolean("true");
                    else at = new AtributoBoolean("false");
                    break;

                case "string":
                    JTextField tfStrComp = (JTextField) jc;
                    String valueStr = tfStrComp.getText();
                    if (headers[dataComponents.indexOf(jc)].equals("id")) {
                        id = valueStr;
                    }
                    at = new AtributoCategorico(valueStr);
                    break;

                case "set":
                    JTextField tfSetComp = (JTextField) jc;
                    String valueSet = tfSetComp.getText();
                    at = new AtributoCategoricoMultiple(valueSet);
                    break;
                case "fecha":
                    JTextField ftfFechaComp = (JFormattedTextField) jc;
                    String valueFecha = ftfFechaComp.getText();
                    at = new AtributoFecha(valueFecha);
                    break;

                case "double":
                    JTextField ftfDbComp = (JFormattedTextField) jc;
                    String valueDouble = ftfDbComp.getText();
                    at = new AtributoFecha(valueDouble);
                    break;

                case "texto":
                    JTextArea textAComp = (JTextArea) jc;
                    String valueTxt = textAComp.getText();
                    at = new AtributoTexto(valueTxt);
                    break;
            }
            attributes.put(headers[dataComponents.indexOf(jc)], at);
            p++;
        }

    }

    /**
     * @fn initMenu()
     * @brief generar codigo para el menu
     */
    public void initMenu() {
        menubar = new JMenuBar();
        actividad = new JMenu("actividad");

        itemsMenu = new JMenuItem[7];
        itemsMenu[0] = new JMenuItem("cargar archivo nuevo");
        itemsMenu[0].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pCtrlPresentacion.changeFrame("cargarDatos");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemsMenu[1] = new JMenuItem("ver recomendaciones");
        itemsMenu[1].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pCtrlPresentacion.changeFrame("vistaRecomendaciones");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemsMenu[2] = new JMenuItem("elegir usuario/cambiar datos");
        itemsMenu[2].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pCtrlPresentacion.changeFrame("vistaItems");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemsMenu[3] = new JMenuItem("ver items");
        itemsMenu[3].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pCtrlPresentacion.changeFrame("vistaItems");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemsMenu[4] = new JMenuItem("ver/añadir valoraciones");
        itemsMenu[4].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pCtrlPresentacion.changeFrame("usuarioMain");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemsMenu[5] = new JMenuItem("historial recomendaciones");
        itemsMenu[5].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pCtrlPresentacion.changeFrame("historialRecomendaciones");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemsMenu[6] = new JMenuItem("salir");
        itemsMenu[6].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        menubar.add(actividad);
        for (JMenuItem it : itemsMenu) {
            actividad.add(it);
        }
    }

    public void setContentsPanel() {
        headers = pCtrlPresentacion.getHeaders();
        types = pCtrlPresentacion.getTypes();

        for (String h : headers) {
            int index = Arrays.asList(headers).indexOf(h);

            switch (types[index]) {
                case "bool":
                    JPanel boolQ = new JPanel();
                    boolQ.setBackground(new Color(-1));
                    boolQ.setLayout(new BorderLayout(0, 0));

                    JCheckBox cbBoolQ = new JCheckBox();
                    cbBoolQ.setText("<html>¿" + h + "?</html>");
                    cbBoolQ.setBackground(new Color((-1)));
                    dataComponents.add(cbBoolQ);

                    boolQ.add(cbBoolQ, BorderLayout.CENTER);
                    dynPanels.add(boolQ);
                    break;

                case "string":
                    JPanel stringQ = new JPanel();
                    stringQ.setBackground(new Color(-1));
                    stringQ.setLayout(new BorderLayout(0, 0));

                    JTextField tfStringQ = new JTextField();
                    tfStringQ.setBackground(new Color((-1)));
                    dataComponents.add(tfStringQ);

                    JLabel lStringQ = new JLabel();
                    lStringQ.setText("<html>" + h + "</html>");
                    lStringQ.setBackground(new Color((-1)));

                    stringQ.add(lStringQ, BorderLayout.NORTH);
                    stringQ.add(tfStringQ, BorderLayout.CENTER);

                    dynPanels.add(stringQ);
                    break;

                case "double":
                    JPanel doubleQ = new JPanel();
                    doubleQ.setBackground(new Color(-1));
                    doubleQ.setLayout(new BorderLayout(0, 0));

                    JLabel lDoubleQ = new JLabel();
                    lDoubleQ.setText("<html>" + h + "</html>");
                    lDoubleQ.setBackground(new Color((-1)));

                    NumberFormat doubleFormat = DecimalFormat.getInstance();
                    JFormattedTextField ftfDoubleQ = new JFormattedTextField(doubleFormat);
                    dataComponents.add(ftfDoubleQ);

                    doubleQ.add(lDoubleQ, BorderLayout.NORTH);
                    doubleQ.add(ftfDoubleQ, BorderLayout.CENTER);

                    dynPanels.add(doubleQ);
                    break;

                case "set":
                    JPanel setQ = new JPanel();
                    setQ.setBackground(new Color(-1));
                    setQ.setLayout(new BorderLayout(0, 0));

                    JTextField tfSetQ = new JTextField();
                    tfSetQ.setBackground(new Color((-1)));
                    dataComponents.add(tfSetQ);

                    JLabel lSetQ = new JLabel();
                    lSetQ.setText("<html>" + h + ". Por favor, introduzca los elementos separados por comas. </html>");
                    lSetQ.setBackground(new Color((-1)));

                    setQ.add(lSetQ, BorderLayout.NORTH);
                    setQ.add(tfSetQ, BorderLayout.CENTER);

                    dynPanels.add(setQ);
                    break;

                case "fecha":
                    JPanel dateQ = new JPanel();
                    dateQ.setBackground(new Color(-1));
                    dateQ.setLayout(new BorderLayout(0, 0));

                    JLabel lDateQ = new JLabel();
                    lDateQ.setText("<html>" + h + "</html>");
                    lDateQ.setBackground(new Color((-1)));

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    JFormattedTextField ftfDateQ = new JFormattedTextField(dateFormat);
                    dataComponents.add(ftfDateQ);

                    dateQ.add(lDateQ, BorderLayout.NORTH);
                    dateQ.add(ftfDateQ, BorderLayout.CENTER);

                    dynPanels.add(dateQ);
                    break;

                case "texto":
                    JPanel txtQ = new JPanel();
                    txtQ.setBackground(new Color(-1));
                    txtQ.setLayout(new BorderLayout(0, 0));


                    JTextArea taTxtQ = new JTextArea();
                    taTxtQ.setBackground(new Color((-1)));
                    dataComponents.add(taTxtQ);

                    JLabel lTxtQ = new JLabel();
                    lTxtQ.setText("<html>" + h + "</html>");
                    lTxtQ.setBackground(new Color((-1)));

                    txtQ.add(lTxtQ, BorderLayout.NORTH);
                    txtQ.add(taTxtQ, BorderLayout.CENTER);

                    dynPanels.add(txtQ);
                    break;
            }
        }
        JPanel contentsPanel = new JPanel();
        contentsPanel.setBackground(new Color(-1));
        contentsPanel.setLayout(new GridLayoutManager(dynPanels.size(), 1, new Insets(0, 0, 0, 0), -1, -1));

        for (JPanel jp : dynPanels) {
            contentsPanel.add(jp, new GridConstraints(dynPanels.indexOf(jp), 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
        scrollContentPanel.setViewportView(contentsPanel);
    }

    /**
     * @param iCtrlPresentacion: controlador de presentación para comunicación con la capa dominio
     * @fn añadirItem()
     * @brief vista con la que se añaden items a la lista
     */
    public añadirItem(CtrlPresentacion iCtrlPresentacion) {
        pCtrlPresentacion = iCtrlPresentacion;
        setContentsPanel();
        initMenu();


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                add(panel1);
                setJMenuBar(menubar);
                setVisible(true);
            }
        });

        añadirItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setAttributes();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                for (Atributo at : attributes.values()) {
                    System.out.println();
                }
                pCtrlPresentacion.addNewItem(id, attributes);
            }
        });
        modificarItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setAttributes();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                if (pCtrlPresentacion.existsId(Integer.parseInt(id))) {

                }


            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    iCtrlPresentacion.changeFrame("vistaItems");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setBackground(new Color(-1));
        panel1.setMaximumSize(new Dimension(750, 500));
        panel1.setMinimumSize(new Dimension(750, 500));
        panel1.setPreferredSize(new Dimension(750, 500));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-13421238));
        panel2.setMaximumSize(new Dimension(250, 500));
        panel2.setMinimumSize(new Dimension(250, 500));
        panel2.setPreferredSize(new Dimension(250, 500));
        panel1.add(panel2, BorderLayout.WEST);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-13421238));
        panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Bangla MN", -1, 36, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-1));
        label1.setText("Items");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(20, 20, 20, 20), -1, -1));
        panel4.setBackground(new Color(-1));
        panel4.setMaximumSize(new Dimension(500, 500));
        panel4.setMinimumSize(new Dimension(500, 500));
        panel4.setPreferredSize(new Dimension(500, 500));
        panel1.add(panel4, BorderLayout.CENTER);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel5.setBackground(new Color(-1));
        panel4.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-1));
        Font label2Font = this.$$$getFont$$$("Bangla MN", -1, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-13421238));
        label2.setText("<html> Introduzca la información requerida para añadir o modificar un ítem en el sistema </html>");
        panel5.add(label2, BorderLayout.NORTH);
        scrollContentPanel = new JScrollPane();
        scrollContentPanel.setBackground(new Color(-1));
        panel5.add(scrollContentPanel, BorderLayout.CENTER);
        contentsPanel = new JPanel();
        contentsPanel.setLayout(new GridLayoutManager(1, 1, new Insets(20, 20, 20, 20), -1, -1));
        contentsPanel.setBackground(new Color(-1));
        scrollContentPanel.setViewportView(contentsPanel);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-1));
        panel5.add(panel6, BorderLayout.SOUTH);
        añadirItemButton = new JButton();
        añadirItemButton.setBackground(new Color(-1));
        añadirItemButton.setText("Añadir Item");
        panel6.add(añadirItemButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarItemButton = new JButton();
        modificarItemButton.setBackground(new Color(-1));
        modificarItemButton.setText("Modificar Item");
        panel6.add(modificarItemButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        volverButton = new JButton();
        volverButton.setBackground(new Color(-1));
        volverButton.setText("Volver");
        panel4.add(volverButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
