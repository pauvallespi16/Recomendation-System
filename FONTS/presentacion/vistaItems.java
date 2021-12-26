package presentacion; /** @file vistaItems.java
 * @brief Código de la vista de items
 */
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.Vector;

/** @class vistaItems
 *
 * @author
 *
 */
public class vistaItems extends JFrame {
    private CtrlPresentacion iCtrlPresentacion;

    private JPanel panel1;
    private JTextField item;
    private JButton button;
    private JScrollPane scrolly;
    private JButton remItButton;
    private JButton volverButton;
    private JPanel cardPanel;
    private JPanel remItPanel;
    private JPanel listPanel;
    private JButton addModButton;
    private JMenuBar menubar;
    private JMenu actividad;
    private JMenuItem itemsMenu[];


    public void initComponents() {
        Vector headers = new Vector();
        headers.add("items id");
        headers.add("title");
        headers.add("genre");
        headers.add("popularity");
        Vector data = iCtrlPresentacion.getAllItemAtrbutes();

        JTable itTable = new JTable(data, headers);
        scrolly.setViewportView(itTable);
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
        itemsMenu[2] = new JMenuItem("elegir usuario/cambiar datos");
        itemsMenu[2].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    iCtrlPresentacion.changeFrame("vistaItems");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemsMenu[3] = new JMenuItem("ver/añadir valoraciones");
        itemsMenu[3].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    iCtrlPresentacion.changeFrame("usuarioMain");
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

    public vistaItems(CtrlPresentacion pCtrlPresentacion) {
        iCtrlPresentacion = pCtrlPresentacion;
        initComponents();
        initMenu();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setJMenuBar(menubar);
                add(panel1);
                setVisible(true);
            }
        });
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(listPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        remItButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(remItPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        addModButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    iCtrlPresentacion.changeFrame("añadirItem");
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
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setMaximumSize(new Dimension(750, 500));
        panel2.setMinimumSize(new Dimension(750, 500));
        panel2.setPreferredSize(new Dimension(750, 500));
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setBackground(new Color(-1));
        panel1.setMaximumSize(new Dimension(750, 500));
        panel1.setMinimumSize(new Dimension(750, 500));
        panel1.setPreferredSize(new Dimension(750, 500));
        panel2.add(panel1, BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel3.setBackground(new Color(-1));
        panel3.setMaximumSize(new Dimension(500, 250));
        panel3.setMinimumSize(new Dimension(500, 250));
        panel3.setPreferredSize(new Dimension(500, 250));
        panel1.add(panel3, BorderLayout.CENTER);
        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout(0, 0));
        cardPanel.setBackground(new Color(-1));
        panel3.add(cardPanel, BorderLayout.CENTER);
        listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout(0, 0));
        listPanel.setBackground(new Color(-1));
        cardPanel.add(listPanel, "Card2");
        scrolly = new JScrollPane();
        scrolly.setBackground(new Color(-1));
        listPanel.add(scrolly, BorderLayout.CENTER);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-1));
        listPanel.add(panel4, BorderLayout.SOUTH);
        remItButton = new JButton();
        remItButton.setBackground(new Color(-1));
        remItButton.setLabel("Eliminar Item(s)");
        remItButton.setText("Eliminar Item(s)");
        panel4.add(remItButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addModButton = new JButton();
        addModButton.setBackground(new Color(-1));
        addModButton.setText("Añadir/Modificar Item(s)");
        panel4.add(addModButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        remItPanel = new JPanel();
        remItPanel.setLayout(new GridLayoutManager(3, 1, new Insets(20, 20, 20, 20), -1, -1));
        remItPanel.setBackground(new Color(-1));
        cardPanel.add(remItPanel, "Card1");
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel5.setBackground(new Color(-1));
        remItPanel.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("    ITEM ID: ");
        panel5.add(label1);
        item = new JTextField();
        item.setPreferredSize(new Dimension(150, 30));
        item.setText("");
        panel5.add(item);
        button = new JButton();
        button.setBackground(new Color(-1));
        button.setText("BORARR ITEM");
        panel5.add(button);
        volverButton = new JButton();
        volverButton.setBackground(new Color(-1));
        volverButton.setText("Volver");
        remItPanel.add(volverButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Bangla MN", -1, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-13421238));
        label2.setText("<html> Por favor, introduzca el identificador de los ítems que desee eliminar </html>");
        remItPanel.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-13421238));
        panel6.setMaximumSize(new Dimension(250, 500));
        panel6.setMinimumSize(new Dimension(250, 500));
        panel6.setPreferredSize(new Dimension(250, 500));
        panel1.add(panel6, BorderLayout.WEST);
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Bangla MN", Font.PLAIN, 36, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-1));
        label3.setHorizontalAlignment(0);
        label3.setHorizontalTextPosition(0);
        label3.setText("Items");
        panel6.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

}
