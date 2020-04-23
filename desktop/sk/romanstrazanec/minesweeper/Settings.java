package sk.romanstrazanec.minesweeper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Settings extends javax.swing.JFrame {

    public Settings() {
        initComponents();
        reset();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Možnosti");
        setResizable(false);

        jLabel1.setText("Náročnosť");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Začiatočník (10 mín, obdĺžn. mriežka 9x9)");
        jRadioButton1.setToolTipText("");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Mierne pokročilý (40 mín, obdĺžn. mriežka 16x16)");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Pokročilý (99 mín, obdĺžn. mriežka 16x30)");

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("Vlastné");
        jRadioButton4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton4StateChanged(evt);
            }
        });

        jLabel2.setText("Výška (9-24):");

        jLabel3.setText("Šírka (9-30):");

        jLabel4.setText("Míny (10-706):");

        jTextField1.setText("9");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });

        jTextField2.setText("9");
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField2FocusLost(evt);
            }
        });

        jTextField3.setText("10");
        jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField3FocusLost(evt);
            }
        });

        jButton1.setText("Zrušiť");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("OK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton5);
        jRadioButton5.setText("Modré");

        buttonGroup2.add(jRadioButton6);
        jRadioButton6.setText("Zelené");

        buttonGroup2.add(jRadioButton7);
        jRadioButton7.setText("Žlté");

        buttonGroup2.add(jRadioButton8);
        jRadioButton8.setText("Červené");

        jCheckBox1.setText("Farebné čísla");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jRadioButton8)
                                        .addComponent(jRadioButton7)
                                        .addComponent(jRadioButton6)
                                        .addComponent(jRadioButton5)
                                        .addComponent(jRadioButton3)
                                        .addComponent(jLabel1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jRadioButton1)
                                                        .addComponent(jRadioButton2))
                                                .addGap(11, 11, 11)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jRadioButton4)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(3, 3, 3)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(jLabel2)
                                                                                                .addComponent(jLabel3))
                                                                                        .addGap(18, 18, 18))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(jLabel4)
                                                                                        .addGap(12, 12, 12)))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jCheckBox1)
                                                                                .addGap(12, 12, 12)))
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                                                        .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING))))))
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRadioButton1)
                                        .addComponent(jRadioButton4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRadioButton2)
                                        .addComponent(jLabel2)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRadioButton3)
                                        .addComponent(jLabel3)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addComponent(jRadioButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRadioButton8)
                                        .addComponent(jCheckBox1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2))
                                .addContainerGap())
        );

        pack();
    }

    public void reset() {
        try (BufferedReader file = new BufferedReader(new FileReader("settings.dat"))) {
            String riadok = file.readLine();
            if (riadok != null) {
                String[] row = riadok.split(";");
                switch (riadok.charAt(0)) {
                    case '1':
                        jRadioButton1.setSelected(true);
                        break;
                    case '2':
                        jRadioButton2.setSelected(true);
                        break;
                    case '3':
                        jRadioButton3.setSelected(true);
                        break;
                    case '4':
                        jRadioButton4.setSelected(true);
                        jTextField1.setText("" + row[2]);
                        jTextField2.setText("" + row[1]);
                        jTextField3.setText("" + row[3]);
                        break;
                    default:
                        break;
                }
                switch (row[4]) {
                    case "0":
                        jRadioButton5.setSelected(true);
                        break;
                    case "1":
                        jRadioButton6.setSelected(true);
                        break;
                    case "2":
                        jRadioButton7.setSelected(true);
                        break;
                    case "3":
                        jRadioButton8.setSelected(true);
                        break;
                    default:
                        break;
                }
                jCheckBox1.setSelected(!row[5].equals("0"));
            }
            file.close();
        } catch (IOException e) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("settings.dat"))) {
                bw.write("1;9;9;10;0;1");
                bw.close();
            } catch (IOException ex) {
            }
        }

        setComponentsEnabled(jRadioButton4.isSelected());
    }

    private void jRadioButton4StateChanged(javax.swing.event.ChangeEvent evt) {
        setComponentsEnabled(jRadioButton4.isSelected());
    }

    private void setComponentsEnabled(boolean b) {
        jTextField1.setEnabled(b);
        jTextField2.setEnabled(b);
        jTextField3.setEnabled(b);
        jLabel2.setEnabled(b);
        jLabel3.setEnabled(b);
        jLabel4.setEnabled(b);
    }

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {
        try {
            int number = Integer.parseInt(jTextField1.getText());
            if (number < 9) jTextField1.setText("9");
            if (number > 24) jTextField1.setText("24");
        } catch (NumberFormatException numberFormatException) {
            jTextField1.setText("9");
        }
    }

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {
        try {
            int number = Integer.parseInt(jTextField2.getText());
            if (number < 9) jTextField2.setText("9");
            if (number > 30) jTextField2.setText("30");
        } catch (NumberFormatException numberFormatException) {
            jTextField2.setText("9");
        }
    }

    private void jTextField3FocusLost(java.awt.event.FocusEvent evt) {
        try {
            int number = Integer.parseInt(jTextField3.getText());
            int width = Integer.parseInt(jTextField2.getText());
            int height = Integer.parseInt(jTextField1.getText());
            if (number < 10) jTextField3.setText("10");
            if (number > (width * height - 14)) jTextField3.setText("" + (width * height - 14));
        } catch (NumberFormatException numberFormatException) {
            jTextField3.setText("10");
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        try (BufferedWriter file = new BufferedWriter(new FileWriter("settings.dat"))) {
            int selectedrb = 0, h = 9, w = 9, m = 10, c = 0, cn = 1;
            if (jRadioButton1.isSelected()) selectedrb = 1, h = 9, w = 9, m = 10;
            if (jRadioButton2.isSelected()) selectedrb = 2, h = 16, w = 16, m = 40;
            if (jRadioButton3.isSelected()) selectedrb = 3, h = 16, w = 30, m = 99;
            if (jRadioButton4.isSelected()) {
                selectedrb = 4;
                h = Integer.parseInt(jTextField1.getText());
                w = Integer.parseInt(jTextField2.getText());
                m = Integer.parseInt(jTextField3.getText());
            }
            if (jRadioButton5.isSelected()) c = 0;
            if (jRadioButton6.isSelected()) c = 1;
            if (jRadioButton7.isSelected()) c = 2;
            if (jRadioButton8.isSelected()) c = 3;
            if (jCheckBox1.isSelected()) cn = 1;
            else cn = 0;
            file.write("" + selectedrb + ";" + w + ";" + h + ";" + m + ";" + c + ";" + cn);
            file.close();
        } catch (IOException e) {
        }
        JOptionPane.showMessageDialog(this, "Tieto nastavenia budú aplikované až na novú hru.");
        this.dispose();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Settings().setVisible(true);
            }
        });
    }

    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
}
