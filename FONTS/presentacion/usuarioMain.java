package presentacion; /** @file usuarioMain.java
 * @brief Código del main del usuario
 */
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

/**
 * @author Carla Campàs Gené
 * @class usuarioMain
 * @brief La clase para el control principal de la aplicación
 * @date December 2021
 */
public class usuarioMain extends JFrame {
    private CtrlPresentacion iCtrlPresentacion;

    private JPanel panel1;
    private JTextField item;
    private JTextField value;
    private JMenuBar menubar;
    private JMenu actividad;
    private JMenuItem[] itemsMenu;
    private JPanel panelpanel;
    private JButton verMisValoracionesButton;
    private JRadioButton añadirValoraciónRadioButton;
    private JRadioButton borrarValoraciónRadioButton;
    private JLabel labVal;
    private JLabel labID;
    private JPanel contentsPanel;
    private JPanel valPanel;
    private JPanel idPanel;
    private JLabel userId;
    private JButton updButton;
    private JPanel tablePanel;
    private JButton regresaButton;
    private JScrollPane scrolly;
    private JPanel cardPanel;
    private JPanel actionPanel;

    /**
     * @fn initComponents()
     * @brief generar código de las interacciones principales de la página
     */
    public void initComponents() {
        añadirValoraciónRadioButton.setSelected(true);
        userId.setText(iCtrlPresentacion.getuId().toString());
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
                    iCtrlPresentacion.changeFrame("cargarDatos");
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
                    iCtrlPresentacion.changeFrame("vistaRecomendaciones");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemsMenu[2] = new JMenuItem("elegir usuario");
        itemsMenu[2].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CtrlPresentacion.changeFrame("vistaPrincipal");
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
                    iCtrlPresentacion.changeFrame("vistaItems");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemsMenu[4] = new JMenuItem("añadir item");
        itemsMenu[4].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    iCtrlPresentacion.changeFrame("añadirItem");
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
                    iCtrlPresentacion.changeFrame("historialRecomendaciones");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemsMenu[6] = new JMenuItem("salir");
        itemsMenu[6].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    iCtrlPresentacion.guardarSession();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
            }
        });

        menubar.add(actividad);
        for (JMenuItem it : itemsMenu) {
            actividad.add(it);
        }
    }

    /**
     * @param pCtrlPresentacion: controlador de presentación para comunicación con la capa dominio
     * @fn usuarioMain()
     * @brief vista con la que se añaden items a la lista
     */
    public usuarioMain(CtrlPresentacion pCtrlPresentacion) {
        iCtrlPresentacion = pCtrlPresentacion;
        $$$setupUI$$$();
        initComponents();
        initMenu();

        añadirValoraciónRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (añadirValoraciónRadioButton.isSelected()) {
                    borrarValoraciónRadioButton.setSelected(false);
                    //añadirValoraciónRadioButton.setSelected(true);
                    valPanel.setVisible(true);
                }
            }
        });

        borrarValoraciónRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (borrarValoraciónRadioButton.isSelected()) {
                    añadirValoraciónRadioButton.setSelected(false);
                    valPanel.setVisible(false);
                }
            }
        });


        updButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (añadirValoraciónRadioButton.isSelected()) {
                    try {
                        int itemID = Integer.parseInt(item.getText());
                        System.out.println(itemID);
                        Double valoracion = Double.parseDouble(value.getText());
                        System.out.println(valoracion);
                        iCtrlPresentacion.createNewValoracion(itemID, valoracion);
                        iCtrlPresentacion.changeFrame("usuarioMain");
                    } catch (Exception err) {
                        hayError(err.toString());
                    }
                } else {
                    try {
                        int itemID = Integer.parseInt(item.getText());
                        System.out.println(itemID);
                        iCtrlPresentacion.quitarValoracion(itemID);
                        iCtrlPresentacion.changeFrame("usuarioMain");
                    } catch (Exception err) {
                        hayError(err.toString());
                    }
                }
            }
        });
        verMisValoracionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Integer, Double> val = iCtrlPresentacion.getValoracionesDeUsuario();

                Vector headers = new Vector();
                headers.add("items id");
                headers.add("valoracion usuario");
                Vector data = new Vector();

                for (Map.Entry<Integer, Double> valEntSet : val.entrySet()) {
                    Vector aux = new Vector();
                    aux.add(valEntSet.getKey());
                    aux.add(valEntSet.getValue());
                    data.add(aux);
                }
                JTable valTable = new JTable(data, headers);

                scrolly.setViewportView(valTable);

                cardPanel.removeAll();
                cardPanel.add(tablePanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        regresaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(actionPanel);
                cardPanel.repaint();
                cardPanel.revalidate();

            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                add(panel1);
                setJMenuBar(menubar);
                setVisible(true);
            }
        });
    }

    public void hayError(String s) {
        JOptionPane.showMessageDialog(new JFrame(), s);
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
        panel1.setOpaque(true);
        panel1.setPreferredSize(new Dimension(750, 500));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-13421238));
        panel2.setMaximumSize(new Dimension(250, 500));
        panel2.setMinimumSize(new Dimension(250, 500));
        panel2.setPreferredSize(new Dimension(250, 500));
        panel1.add(panel2, BorderLayout.WEST);
        panelpanel = new JPanel();
        panelpanel.setLayout(new BorderLayout(0, 0));
        panelpanel.setBackground(new Color(-1));
        panelpanel.setMaximumSize(new Dimension(500, 250));
        panelpanel.setMinimumSize(new Dimension(500, 250));
        panelpanel.setPreferredSize(new Dimension(500, 250));
        panel1.add(panelpanel, BorderLayout.CENTER);
        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout(0, 0));
        cardPanel.setBackground(new Color(-1));
        panelpanel.add(cardPanel, BorderLayout.CENTER);
        actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        actionPanel.setBackground(new Color(-1));
        actionPanel.setMaximumSize(new Dimension(500, 500));
        actionPanel.setMinimumSize(new Dimension(500, 500));
        actionPanel.setOpaque(true);
        actionPanel.setPreferredSize(new Dimension(500, 500));
        cardPanel.add(actionPanel, "Card1");
        contentsPanel = new JPanel();
        contentsPanel.setLayout(new GridLayoutManager(7, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentsPanel.setBackground(new Color(-1));
        actionPanel.add(contentsPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-1));
        contentsPanel.add(panel3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        idPanel = new JPanel();
        idPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        idPanel.setBackground(new Color(-1));
        panel3.add(idPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        item = new JTextField();
        item.setPreferredSize(new Dimension(150, 30));
        idPanel.add(item, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labID = new JLabel();
        labID.setBackground(new Color(-1));
        Font labIDFont = this.$$$getFont$$$("Bangla Sangam MN", -1, -1, labID.getFont());
        if (labIDFont != null) labID.setFont(labIDFont);
        labID.setForeground(new Color(-13421238));
        labID.setText("ITEM ID: ");
        idPanel.add(labID, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        valPanel = new JPanel();
        valPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        valPanel.setBackground(new Color(-1));
        panel3.add(valPanel, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labVal = new JLabel();
        Font labValFont = this.$$$getFont$$$("Bangla Sangam MN", -1, -1, labVal.getFont());
        if (labValFont != null) labVal.setFont(labValFont);
        labVal.setForeground(new Color(-13421238));
        labVal.setText("VALORACIÓN:");
        valPanel.add(labVal, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        value = new JTextField();
        value.setPreferredSize(new Dimension(150, 30));
        valPanel.add(value, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Bangla MN", -1, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-13421238));
        label1.setText("<html> Por favor, introduzca la información requerida </html>");
        contentsPanel.add(label1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        updButton = new JButton();
        updButton.setBackground(new Color(-1));
        updButton.setLabel("Actualizar");
        updButton.setText("Actualizar");
        contentsPanel.add(updButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        verMisValoracionesButton = new JButton();
        verMisValoracionesButton.setBackground(new Color(-1));
        Font verMisValoracionesButtonFont = this.$$$getFont$$$("Bangla Sangam MN", -1, -1, verMisValoracionesButton.getFont());
        if (verMisValoracionesButtonFont != null) verMisValoracionesButton.setFont(verMisValoracionesButtonFont);
        verMisValoracionesButton.setForeground(new Color(-13421238));
        verMisValoracionesButton.setText("Ver mis valoraciones");
        contentsPanel.add(verMisValoracionesButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        contentsPanel.add(separator1, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-1));
        contentsPanel.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-1));
        Font label2Font = this.$$$getFont$$$("Bangla MN", -1, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-13421238));
        label2.setText("<html> ¿Qué desea hacer?</html>");
        panel4.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 1, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-1));
        panel4.add(panel5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setBackground(new Color(-1));
        Font label3Font = this.$$$getFont$$$("Bangla MN", -1, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-13421238));
        label3.setText("<html>Has iniciado sesión como: </html>");
        panel5.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel6.setBackground(new Color(-1));
        panel5.add(panel6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Bangla MN", -1, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-13421238));
        label4.setText("Usuario");
        panel6.add(label4);
        userId = new JLabel();
        userId.setBackground(new Color(-1));
        Font userIdFont = this.$$$getFont$$$("Bangla MN", -1, 14, userId.getFont());
        if (userIdFont != null) userId.setFont(userIdFont);
        userId.setForeground(new Color(-13421238));
        userId.setText("idUs");
        panel6.add(userId);
        final JSeparator separator2 = new JSeparator();
        panel5.add(separator2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new FlowLayout(FlowLayout.CENTER, -1, -1));
        panel7.setBackground(new Color(-1));
        contentsPanel.add(panel7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 1, false));
        añadirValoraciónRadioButton = new JRadioButton();
        añadirValoraciónRadioButton.setBackground(new Color(-1));
        Font añadirValoraciónRadioButtonFont = this.$$$getFont$$$("Bangla Sangam MN", -1, -1, añadirValoraciónRadioButton.getFont());
        if (añadirValoraciónRadioButtonFont != null)
            añadirValoraciónRadioButton.setFont(añadirValoraciónRadioButtonFont);
        añadirValoraciónRadioButton.setForeground(new Color(-13421238));
        añadirValoraciónRadioButton.setText("Añadir valoración");
        panel7.add(añadirValoraciónRadioButton);
        final Spacer spacer1 = new Spacer();
        panel7.add(spacer1);
        borrarValoraciónRadioButton = new JRadioButton();
        borrarValoraciónRadioButton.setBackground(new Color(-1));
        Font borrarValoraciónRadioButtonFont = this.$$$getFont$$$("Bangla Sangam MN", -1, -1, borrarValoraciónRadioButton.getFont());
        if (borrarValoraciónRadioButtonFont != null)
            borrarValoraciónRadioButton.setFont(borrarValoraciónRadioButtonFont);
        borrarValoraciónRadioButton.setForeground(new Color(-13421238));
        borrarValoraciónRadioButton.setText("Borrar valoración");
        panel7.add(borrarValoraciónRadioButton);
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout(0, 0));
        tablePanel.setBackground(new Color(-1));
        cardPanel.add(tablePanel, "Card2");
        regresaButton = new JButton();
        regresaButton.setBackground(new Color(-1));
        regresaButton.setText("Regresar");
        tablePanel.add(regresaButton, BorderLayout.SOUTH);
        scrolly = new JScrollPane();
        scrolly.setBackground(new Color(-1));
        tablePanel.add(scrolly, BorderLayout.CENTER);
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
