package presentacion; /** @file vistaRecomendaciones.java
 @brief Codigo de la vista de recomendaciones
*/

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import presentacion.CtrlPresentacion;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

/**
 * @author Beatriz Gomes da Costa
 * @class vistaRecomendaciones
 * @brief La clase de la vista con las recomendaciones
 * @date December 2021
 */
public class vistaRecomendaciones extends JFrame {
    CtrlPresentacion pCtrlPresentacion;
    private JPanel panel1;
    private JRadioButton cb;
    private JRadioButton cf;
    private JRadioButton hybrid;
    private JButton recomButton;
    private JButton button1;
    private JScrollPane scrollPane;
    private JPanel scrollPanel;
    private JPanel mainPanel;
    private JButton volverButton;
    private JPanel recomPanel;
    private JPanel cardPanel;
    private JPanel infoCardPanel;
    private JPanel cfPanel;
    private JPanel cbPanel;
    private JPanel hybPanel;
    private JTable table1;
    private JScrollPane scrollPane1;
    private JTextField textField1;
    private JFormattedTextField formattedTextField1;
    private JMenuBar menubar;
    private JMenu actividad;
    private JMenuItem[] itemsMenu;

    public void initComponents() {
        cf.setSelected(true);
    }

    /**
     * @param type: tipo de recomendacion que quiere el usuarop
     * @fn createAndAddTable()
     */
    public void createAndAddTable(String type, String name, int size) {

        Vector headers = new Vector();
        headers.add("items id");
        headers.add("valoración esperada");

        Vector data = new Vector();
        try {
            data = pCtrlPresentacion.getPredicciones(type, name, size);
        } catch (Exception e) {
            hayError(e.toString());
        }
        JTable recomTable = new JTable(data, headers);
        scrollPane.setViewportView(recomTable);
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
        itemsMenu[1] = new JMenuItem("ver/añadir valoraciones");
        itemsMenu[1].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pCtrlPresentacion.changeFrame("usuarioMain");
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
        itemsMenu[4] = new JMenuItem("añadir item");
        itemsMenu[4].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pCtrlPresentacion.changeFrame("añadirItem");
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
                try {
                    pCtrlPresentacion.guardarSession();
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
     * @param iCtrlPresentacion: controlador de presentación para comunicación con la capa dominio
     * @fn vistaRecomendaciones()
     * @brief vista con las recomendaciones e interaciones de esta para el usuario
     */
    public vistaRecomendaciones(CtrlPresentacion iCtrlPresentacion) {
        pCtrlPresentacion = iCtrlPresentacion;
        initComponents();
        initMenu();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                add(panel1);
                setJMenuBar(menubar);
                setVisible(true);
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(recomPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(mainPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        recomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = 0;
                String name = textField1.getText();
                try {
                    size = Integer.parseInt(formattedTextField1.getText());
                } catch (Exception err) {
                    hayError(err.toString());
                }
                if (cb.isSelected()) {
                    createAndAddTable("cb", name, size);
                }
                if (cf.isSelected()) {
                    createAndAddTable("cf", name, size);
                }
                if (hybrid.isSelected()) {
                    createAndAddTable("hybrid", name, size);
                }

                double eval = iCtrlPresentacion.evaluarRecomendaciones(name);
                if (eval != -1.0) {
                    JOptionPane.showMessageDialog(new JFrame(), "DCG: " + eval);
                }
                cardPanel.removeAll();
                cardPanel.add(scrollPane);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        cf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cb.setSelected(false);
                hybrid.setSelected(false);
                infoCardPanel.removeAll();
                infoCardPanel.add(cfPanel);
                infoCardPanel.repaint();
                infoCardPanel.revalidate();
            }
        });
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cf.setSelected(false);
                hybrid.setSelected(false);
                infoCardPanel.removeAll();
                infoCardPanel.add(cbPanel);
                infoCardPanel.repaint();
                infoCardPanel.revalidate();
            }
        });
        hybrid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cb.setSelected(false);
                cf.setSelected(false);
                infoCardPanel.removeAll();
                infoCardPanel.add(hybPanel);
                infoCardPanel.repaint();
                infoCardPanel.revalidate();

            }
        });
    }

    public void hayError(String s) {
        JOptionPane.showMessageDialog(new JFrame(), s);
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
        panel1.setBackground(new Color(-13421238));
        panel1.setMaximumSize(new Dimension(500, 750));
        panel1.setMinimumSize(new Dimension(500, 750));
        panel1.setPreferredSize(new Dimension(500, 750));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setMaximumSize(new Dimension(750, 500));
        panel2.setMinimumSize(new Dimension(750, 500));
        panel2.setPreferredSize(new Dimension(750, 500));
        panel1.add(panel2, BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel3.setBackground(new Color(-13421238));
        panel3.setMaximumSize(new Dimension(250, 500));
        panel3.setMinimumSize(new Dimension(250, 500));
        panel3.setPreferredSize(new Dimension(250, 500));
        panel2.add(panel3, BorderLayout.WEST);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel4.setBackground(new Color(-13421238));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Bangla MN", -1, 26, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-1));
        label1.setText("<html>Nueva</html>");
        panel4.add(label1, BorderLayout.CENTER);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Bangla MN", -1, 26, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-1));
        label2.setText("Recomendacion");
        panel4.add(label2, BorderLayout.SOUTH);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(20, 20, 20, 20), -1, -1));
        panel5.setBackground(new Color(-1));
        panel2.add(panel5, BorderLayout.CENTER);
        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout(0, 0));
        cardPanel.setBackground(new Color(-1));
        panel5.add(cardPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(7, 1, new Insets(20, 20, 20, 20), -1, -1));
        mainPanel.setBackground(new Color(-1));
        mainPanel.setMaximumSize(new Dimension(500, 500));
        mainPanel.setMinimumSize(new Dimension(500, 500));
        mainPanel.setPreferredSize(new Dimension(500, 500));
        cardPanel.add(mainPanel, "Card1");
        recomButton = new JButton();
        recomButton.setBackground(new Color(-1));
        recomButton.setText("UEUE RECOMEND");
        mainPanel.add(recomButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-1));
        mainPanel.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setBackground(new Color(-1));
        Font label3Font = this.$$$getFont$$$("Bangla MN", -1, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-13421238));
        label3.setText("¿Qué tipo de Recomendación desea?");
        panel6.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        hybrid = new JRadioButton();
        hybrid.setBackground(new Color(-1));
        Font hybridFont = this.$$$getFont$$$("Bangla MN", -1, 14, hybrid.getFont());
        if (hybridFont != null) hybrid.setFont(hybridFont);
        hybrid.setForeground(new Color(-13421238));
        hybrid.setText("Hybrid Filtering");
        panel6.add(hybrid, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        cf = new JRadioButton();
        cf.setBackground(new Color(-1));
        Font cfFont = this.$$$getFont$$$("Bangla MN", -1, 14, cf.getFont());
        if (cfFont != null) cf.setFont(cfFont);
        cf.setForeground(new Color(-13421238));
        cf.setText("Collaborative Filtering");
        panel6.add(cf, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        cb = new JRadioButton();
        cb.setBackground(new Color(-1));
        Font cbFont = this.$$$getFont$$$("Bangla MN", -1, 14, cb.getFont());
        if (cbFont != null) cb.setFont(cbFont);
        cb.setForeground(new Color(-13421238));
        cb.setText("Content-Based Filtering");
        panel6.add(cb, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        mainPanel.add(separator1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(500, 10), new Dimension(500, 10), new Dimension(500, 10), 0, false));
        button1 = new JButton();
        button1.setBackground(new Color(-1));
        button1.setLabel("Mis Recomendaciones");
        button1.setText("Mis Recomendaciones");
        mainPanel.add(button1, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setBackground(new Color(-1));
        mainPanel.add(panel7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setBackground(new Color(-13421236));
        Font label4Font = this.$$$getFont$$$("Bangla MN", -1, 12, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("<html>Escoja un nombre para la recomendación </html>");
        panel7.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        textField1.setBackground(new Color(-1));
        panel7.add(textField1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setBackground(new Color(-13421236));
        Font label5Font = this.$$$getFont$$$("Bangla MN", -1, 12, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("<html>¿Tamaño de la recomendación?</html>");
        panel7.add(label5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        formattedTextField1 = new JFormattedTextField();
        panel7.add(formattedTextField1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        infoCardPanel = new JPanel();
        infoCardPanel.setLayout(new CardLayout(0, 0));
        mainPanel.add(infoCardPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(400, 100), new Dimension(400, 100), new Dimension(400, 100), 0, false));
        cfPanel = new JPanel();
        cfPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        cfPanel.setBackground(new Color(-1));
        cfPanel.setMaximumSize(new Dimension(400, 400));
        cfPanel.setMinimumSize(new Dimension(400, 400));
        cfPanel.setPreferredSize(new Dimension(400, 400));
        infoCardPanel.add(cfPanel, "Card3");
        final JLabel label6 = new JLabel();
        label6.setBackground(new Color(-1));
        Font label6Font = this.$$$getFont$$$("Bangla MN", -1, 14, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setForeground(new Color(-13421238));
        label6.setText("<html>Recomendación basada en los usuarios con valoraciones similares a las suyas</html>");
        cfPanel.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(400, 100), new Dimension(400, 100), new Dimension(400, 100), 0, false));
        cbPanel = new JPanel();
        cbPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        cbPanel.setBackground(new Color(-1));
        cbPanel.setMaximumSize(new Dimension(400, 400));
        cbPanel.setMinimumSize(new Dimension(400, 400));
        cbPanel.setPreferredSize(new Dimension(400, 400));
        infoCardPanel.add(cbPanel, "Card2");
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$("Bangla MN", -1, 14, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setForeground(new Color(-13421238));
        label7.setText("<html>Recomendación basada en los items que ya ha valorado</html>");
        cbPanel.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(400, 100), new Dimension(400, 100), new Dimension(400, 100), 0, false));
        hybPanel = new JPanel();
        hybPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        hybPanel.setBackground(new Color(-1));
        infoCardPanel.add(hybPanel, "Card1");
        final JLabel label8 = new JLabel();
        label8.setBackground(new Color(-1));
        Font label8Font = this.$$$getFont$$$("Bangla MN", -1, 14, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setForeground(new Color(-13421238));
        label8.setText("<html>Recomendación basada en la combinación de Content-based filtering y Collaborative filtering</html>");
        hybPanel.add(label8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(400, 100), new Dimension(400, 100), new Dimension(400, 100), 0, false));
        recomPanel = new JPanel();
        recomPanel.setLayout(new BorderLayout(0, 0));
        recomPanel.setBackground(new Color(-1));
        cardPanel.add(recomPanel, "Card2");
        scrollPanel = new JPanel();
        scrollPanel.setLayout(new BorderLayout(0, 0));
        scrollPanel.setBackground(new Color(-1));
        recomPanel.add(scrollPanel, BorderLayout.CENTER);
        scrollPane = new JScrollPane();
        scrollPane.setBackground(new Color(-1));
        scrollPanel.add(scrollPane, BorderLayout.CENTER);
        volverButton = new JButton();
        volverButton.setBackground(new Color(-1));
        volverButton.setText("Volver");
        scrollPanel.add(volverButton, BorderLayout.SOUTH);
        final JLabel label9 = new JLabel();
        label9.setBackground(new Color(-1));
        Font label9Font = this.$$$getFont$$$("Bangla MN", -1, 14, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setForeground(new Color(-13421238));
        label9.setText("<html>Mis Recomendaciones</html>");
        scrollPanel.add(label9, BorderLayout.NORTH);
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
