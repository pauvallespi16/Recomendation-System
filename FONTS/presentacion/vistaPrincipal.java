package presentacion; /** @file vistaPrincipal.java
 * @brief Código de la vista principal
 */

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Locale;

/**
 * @author Carla Campàs Gené
 * @class vistaRecomendaciones
 * @brief La clase de la vista para añadir items
 * @date December 2021
 */
public class vistaPrincipal extends JFrame {
    private CtrlPresentacion iCtrlPresentacion;

    private JPanel panel1;
    private JTextField id;
    private JButton ENTRARButton;
    private JCheckBox evaluarRecomendacionesCheckBox;
    private JButton crearCuentaButton;
    private String absolutePath;
    private JMenuBar menubar;
    private JMenu actividad;
    private JMenuItem[] itemsMenu;

    public void goToUsrMain() {
        boolean eval = evaluarRecomendacionesCheckBox.isSelected();
        try {
            int userID = Integer.parseInt(id.getText());
            iCtrlPresentacion.enterNewUser(userID, eval);
            iCtrlPresentacion.changeFrame("usuarioMain");
            setVisible(false);
        } catch (Exception err) {
            hayError(err.toString());
        }
    }

    /**
     * @fn initMenu()
     * @brief generar codigo para el menu
     */
    public void initMenu() {
        menubar = new JMenuBar();
        actividad = new JMenu("actividad");

        itemsMenu = new JMenuItem[4];
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
        itemsMenu[1] = new JMenuItem("ver items");
        itemsMenu[1].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    iCtrlPresentacion.changeFrame("vistaItems");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemsMenu[2] = new JMenuItem("añadir item");
        itemsMenu[2].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    iCtrlPresentacion.changeFrame("añadirItem");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemsMenu[3] = new JMenuItem("salir");
        itemsMenu[3].addActionListener(new AbstractAction() {
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
     * @fn vistaPrincipal()
     * @brief vista con la que se añaden items a la lista
     */
    public vistaPrincipal(CtrlPresentacion pCtrlPresentacion) {
        iCtrlPresentacion = pCtrlPresentacion;
        setContentPane(panel1);
        initMenu();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ENTRARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToUsrMain();
            }
        });

        id.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    goToUsrMain();
                }
            }
        });

        crearCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userID = Integer.parseInt(id.getText());
                try {
                    iCtrlPresentacion.añadirUsuario(absolutePath, userID);
                    iCtrlPresentacion.changeFrame("usuarioMain");
                    setVisible(false);
                } catch (Exception err) {
                    hayError(err.toString());
                }
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setJMenuBar(menubar);
                setVisible(true);
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
        panel1.setMaximumSize(new Dimension(750, 500));
        panel1.setMinimumSize(new Dimension(750, 500));
        panel1.setOpaque(true);
        panel1.setPreferredSize(new Dimension(750, 500));
        panel1.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, panel1.getFont()), null));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setBackground(new Color(-13421238));
        panel2.setMaximumSize(new Dimension(300, 500));
        panel2.setMinimumSize(new Dimension(300, 500));
        panel2.setOpaque(true);
        panel2.setPreferredSize(new Dimension(300, 500));
        panel1.add(panel2, BorderLayout.WEST);
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-13421238));
        Font label1Font = this.$$$getFont$$$("Bangla MN", Font.PLAIN, 36, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-1));
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setPreferredSize(new Dimension(241, 66));
        label1.setText("Bienvenido");
        label1.setVerticalAlignment(0);
        panel2.add(label1, BorderLayout.WEST);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel3.setBackground(new Color(-1));
        panel3.setMaximumSize(new Dimension(450, 500));
        panel3.setMinimumSize(new Dimension(450, 500));
        panel3.setPreferredSize(new Dimension(450, 500));
        panel1.add(panel3, BorderLayout.CENTER);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(7, 2, new Insets(10, 10, 10, 10), -1, -1));
        panel4.setBackground(new Color(-1));
        panel4.setMaximumSize(new Dimension(-1, -1));
        panel4.setMinimumSize(new Dimension(-1, -1));
        panel4.setPreferredSize(new Dimension(-1, -1));
        panel3.add(panel4, BorderLayout.CENTER);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Bangla Sangam MN", -1, -1, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setHorizontalAlignment(2);
        label2.setHorizontalTextPosition(2);
        label2.setText("Id");
        panel4.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 2, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), 5, 5));
        panel5.setBackground(new Color(-1));
        panel4.add(panel5, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setAlignmentY(0.0f);
        label3.setAutoscrolls(false);
        label3.setBackground(new Color(-13421238));
        Font label3Font = this.$$$getFont$$$("Bangla MN", -1, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-13421238));
        label3.setHorizontalAlignment(0);
        label3.setHorizontalTextPosition(0);
        label3.setMaximumSize(new Dimension(100, 100));
        label3.setMinimumSize(new Dimension(100, 100));
        label3.setPreferredSize(new Dimension(100, 100));
        label3.setText("<html> Para empezar, introduce el ID del usuario para el que quieres recomendaciones:</html>");
        panel5.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        panel4.add(separator1, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(500, 100), new Dimension(500, 100), new Dimension(500, 100), 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel6.setBackground(new Color(-1));
        panel4.add(panel6, new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Bangla MN", -1, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-13421238));
        label4.setHorizontalAlignment(2);
        label4.setText("¿Eres nuevo? ¡No te proecupes!");
        panel6.add(label4);
        final Spacer spacer1 = new Spacer();
        panel6.add(spacer1);
        crearCuentaButton = new JButton();
        crearCuentaButton.setBackground(new Color(-1));
        Font crearCuentaButtonFont = this.$$$getFont$$$("Bangla MN", -1, -1, crearCuentaButton.getFont());
        if (crearCuentaButtonFont != null) crearCuentaButton.setFont(crearCuentaButtonFont);
        crearCuentaButton.setText("Crear cuenta");
        panel6.add(crearCuentaButton);
        id = new JTextField();
        id.setHorizontalAlignment(0);
        id.setMargin(new Insets(2, 6, 2, 6));
        id.setPreferredSize(new Dimension(300, 30));
        panel4.add(id, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        evaluarRecomendacionesCheckBox = new JCheckBox();
        evaluarRecomendacionesCheckBox.setBackground(new Color(-1));
        Font evaluarRecomendacionesCheckBoxFont = this.$$$getFont$$$("Bangla Sangam MN", -1, -1, evaluarRecomendacionesCheckBox.getFont());
        if (evaluarRecomendacionesCheckBoxFont != null)
            evaluarRecomendacionesCheckBox.setFont(evaluarRecomendacionesCheckBoxFont);
        evaluarRecomendacionesCheckBox.setForeground(new Color(-13421238));
        evaluarRecomendacionesCheckBox.setHorizontalAlignment(2);
        evaluarRecomendacionesCheckBox.setHorizontalTextPosition(2);
        evaluarRecomendacionesCheckBox.setText("Evaluar Recomendaciones");
        panel4.add(evaluarRecomendacionesCheckBox, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ENTRARButton = new JButton();
        ENTRARButton.setBackground(new Color(-1));
        Font ENTRARButtonFont = this.$$$getFont$$$("Bangla Sangam MN", -1, -1, ENTRARButton.getFont());
        if (ENTRARButtonFont != null) ENTRARButton.setFont(ENTRARButtonFont);
        ENTRARButton.setForeground(new Color(-13421238));
        ENTRARButton.setText("ENTRAR");
        panel4.add(ENTRARButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel4.add(spacer2, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
