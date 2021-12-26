package presentacion;

import clases.Recomendacion;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

public class historialRecomendaciones extends JFrame {
    private CtrlPresentacion iCtrlPresentacion;
    private JPanel panel1;
    private JScrollPane scrolly;
    private JPanel scrollPanel;
    private JPanel recomsPanel;
    private JButton volverButton;
    private JScrollPane recomScrolly;
    private JPanel cardP;
    private JButton eliminarRecomendaciónButton;
    private JButton eliminarArchivoButton;
    private JPanel removePanel;
    private JLabel fileLabel;
    private JButton volverButton2;
    private JButton remRecomItem;
    private JComboBox comboBox1;
    private ArrayList<JButton> JButts;
    private JRadioButton[] radios;
    ArrayList<String> rs;
    String currentR = "";

    public void initComponents() throws Exception {
        JButts = new ArrayList<>();
        JPanel cP = new JPanel();
        rs = iCtrlPresentacion.getHistorialRecomendaciones();
        for (String r : rs) {
            JButton verButton = new JButton();
            String s = "<html>Ver " + r + "</html>";
            verButton.setText(s);
            JButts.add(verButton);
        }

        try {
            cP.setLayout(new GridLayoutManager(JButts.size(), 1, new Insets(0, 0, 0, 0), -1, -1));
            for (JButton b : JButts) {
                cP.add(b, new GridConstraints(JButts.indexOf(b), 0, 1, 1, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(500, 50), null, null, 0, false));
            }
            scrolly.setViewportView(cP);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), "No hay recomendaciones");
            setVisible(false);
            iCtrlPresentacion.changeFrame("usuarioMain");
        }

    }

    public void setTable(Recomendacion r) {

    }

    private JMenuBar menubar;
    private JMenu actividad;
    private JMenuItem[] itemsMenu;

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
        itemsMenu[5] = new JMenuItem("ver/añadir valoraciones");
        itemsMenu[5].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    iCtrlPresentacion.changeFrame("usuarioMain");
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

    /*+
        1. cuando clican ver historial cargar todas las recomendaciones con la funcion que ha hecho pau antes
        2. que puedan seleccionar un historial y ver que recomendaciones hay
        3. pueden borrar cualquiera que piden
     */
    public historialRecomendaciones(CtrlPresentacion pCtrlPresentacion) throws Exception {
        iCtrlPresentacion = pCtrlPresentacion;
        initComponents();
        initMenu();

        for (JButton b : JButts) {
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    Vector headers = new Vector();
                    headers.add("ID");
                    headers.add("Valoración");

                    String r = rs.get(JButts.indexOf(b));
                    currentR = r;
                    Vector data = iCtrlPresentacion.getRecomsVector(r);

                    JTable recomTable = new JTable(data, headers);

                    recomScrolly.setViewportView(recomTable);

                    cardP.removeAll();
                    cardP.add(recomsPanel);
                    cardP.repaint();
                    cardP.revalidate();
                }
            });
        }

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardP.removeAll();
                cardP.add(scrollPanel);
                cardP.repaint();
                cardP.revalidate();
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

        eliminarRecomendaciónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileLabel.setText(currentR);
                comboBox1.removeAllItems();
                ArrayList<Integer> r = pCtrlPresentacion.getItemsRecomendacion(currentR);
                r.forEach(k -> {
                    comboBox1.addItem(k);
                });

                cardP.removeAll();
                cardP.add(removePanel);
                cardP.repaint();
                cardP.revalidate();
            }
        });

        volverButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardP.removeAll();
                cardP.add(scrollPanel);
                cardP.repaint();
                cardP.revalidate();
            }
        });

        remRecomItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    iCtrlPresentacion.eliminarRecomItem(currentR, (Integer) comboBox1.getSelectedItem());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                cardP.removeAll();
                cardP.add(scrollPanel);
                cardP.repaint();
                cardP.revalidate();

            }
        });
        eliminarArchivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentR != "") pCtrlPresentacion.borrarRecomendacionHistorial(currentR);
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
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-13421238));
        panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Bangla MN", -1, 24, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-1));
        label1.setText("<html>Historial</html>");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Bangla MN", -1, 24, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-1));
        label2.setText("Recomendaciones");
        panel3.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(20, 20, 20, 20), -1, -1));
        panel4.setBackground(new Color(-1));
        panel1.add(panel4, BorderLayout.CENTER);
        cardP = new JPanel();
        cardP.setLayout(new CardLayout(0, 0));
        cardP.setBackground(new Color(-1));
        panel4.add(cardP, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPanel.setBackground(new Color(-1));
        cardP.add(scrollPanel, "Card1");
        scrolly = new JScrollPane();
        scrolly.setBackground(new Color(-1));
        scrollPanel.add(scrolly, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        recomsPanel = new JPanel();
        recomsPanel.setLayout(new BorderLayout(0, 0));
        recomsPanel.setBackground(new Color(-1));
        cardP.add(recomsPanel, "Card2");
        recomScrolly = new JScrollPane();
        recomScrolly.setBackground(new Color(-1));
        recomsPanel.add(recomScrolly, BorderLayout.CENTER);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-1));
        recomsPanel.add(panel5, BorderLayout.SOUTH);
        volverButton = new JButton();
        volverButton.setBackground(new Color(-1));
        volverButton.setText("Volver");
        panel5.add(volverButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        eliminarRecomendaciónButton = new JButton();
        eliminarRecomendaciónButton.setBackground(new Color(-1));
        eliminarRecomendaciónButton.setText("Eliminar Recomendación...");
        panel5.add(eliminarRecomendaciónButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        removePanel = new JPanel();
        removePanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        removePanel.setBackground(new Color(-1));
        cardP.add(removePanel, "Card3");
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-1));
        removePanel.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Bangla MN", -1, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-13421238));
        label3.setText("<html>Eliminar recomendación por ítem</html>");
        panel6.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setBackground(new Color(-1));
        panel6.add(panel7, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        remRecomItem = new JButton();
        remRecomItem.setBackground(new Color(-1));
        remRecomItem.setLabel("Eliminar ítem");
        remRecomItem.setText("Eliminar ítem");
        panel7.add(remRecomItem, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel7.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel7.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        panel7.add(comboBox1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel6.add(spacer3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        volverButton2 = new JButton();
        volverButton2.setBackground(new Color(-1));
        volverButton2.setLabel("Volver");
        volverButton2.setText("Volver");
        panel6.add(volverButton2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel6.add(spacer4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel8.setBackground(new Color(-1));
        removePanel.add(panel8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        eliminarArchivoButton = new JButton();
        eliminarArchivoButton.setBackground(new Color(-1));
        eliminarArchivoButton.setText("Eliminar archivo");
        panel8.add(eliminarArchivoButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setBackground(new Color(-1));
        Font label4Font = this.$$$getFont$$$("Bangla MN", -1, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-13421238));
        label4.setText("<html>Eliminar recomendación por archivo</html>");
        panel8.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Bangla MN", -1, 12, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Atención: se encuentra usted dentro del archivo:");
        panel8.add(label5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fileLabel = new JLabel();
        fileLabel.setText("Filename");
        panel8.add(fileLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel8.add(spacer5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel8.add(spacer6, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        removePanel.add(separator1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
