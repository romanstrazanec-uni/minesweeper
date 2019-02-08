
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.Timer;

public class main extends javax.swing.JFrame implements ActionListener, MouseListener {
    Timer tmr;
    int gamewidth, gameheight, time, oldx, oldy, mouseButton, oh, wins, ratio, nsv, nsp, as;
    stats st;
    settings se;
    Field fld;
    Counter timeCounter, minesCounter;
    
    public main() {
        initComponents();
        se = new settings();
        setLocation(250,150);
        fld= new Field(this, 30, 30);
        tmr = new Timer(1000, this);
        time = 0;
        add(fld);
        timeCounter = new Counter(0, 30, fld.getHeightofField()*fld.getWidthofTile()+40);
        minesCounter = new Counter(fld.getBombs(), fld.getWidthofField()*fld.getWidthofTile(), fld.getHeightofField()*fld.getWidthofTile()+40);
        add(timeCounter);
        add(minesCounter);
        addMouseListener(this);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jMenu1 = new javax.swing.JMenu();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Míny");
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });

        jMenu2.setText("Hra");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem1.setText("Nová hra");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);
        jMenu2.add(jSeparator1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItem2.setText("Štatistika");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem3.setText("Možnosti");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);
        jMenu2.add(jSeparator2);

        jMenuItem4.setText("Skončiť");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 804, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 554, Short.MAX_VALUE)
        );

        pack();
    }

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
            se.dispose();
            st = new stats();
            st.setLocationRelativeTo(this);
            st.setVisible(true);
    }

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
        if(st != null) st.dispose();
        se.setLocationRelativeTo(this);
        se.reset();
        se.setVisible(true);
    }

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }
    
    public void newGame(){
        tmr.stop();
        time = 0;
        remove(fld);
        remove(timeCounter);
        remove(minesCounter);
        repaint();
        fld = new Field(this, 30, 30);
        add(fld);
        timeCounter = new Counter(0, 30, fld.getHeightofField() * fld.getWidthofTile() + 40);
        minesCounter = new Counter(fld.getBombs(), fld.getWidthofField() * fld.getWidthofTile(), fld.getHeightofField() * fld.getWidthofTile() + 40);
        add(timeCounter);
        add(minesCounter);
    }
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        newGame();
    }

    private void formMouseDragged(java.awt.event.MouseEvent evt) {
        if(evt.getX() -7 > fld.getX() && evt.getX()<fld.getWidth() + 37 &&
                evt.getY() - 54 > fld.getY() && evt.getY() < fld.getHeight() + 82){
            if(mouseButton == 1){
                fld.mousePress(evt.getX() - 40, evt.getY() - 80, oldx, oldy);
                oldx = evt.getX() - 40;
                oldy = evt.getY() - 80;
            }
            if(mouseButton == 3 && fld.getBombs() - fld.getFlags() > 0){
                fld.mousePress(evt.getX() - 40, evt.getY() - 80, oldx, oldy);
                oldx = evt.getX() - 40;
                oldy = evt.getY() - 80;
            }
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        ++time;
        timeCounter.setNumber(time);
    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {
        if(me.getX() - 7 > fld.getX() && me.getX() < fld.getWidth() + 37 &&
                me.getY() - 54 > fld.getY() && me.getY() < fld.getHeight() + 82){
            if(me.getButton() == 1){
                oldx = me.getX() - 40;
                oldy = me.getY() - 80;
                fld.mousePress(me.getX() - 40, me.getY() - 80,me.getX() - 40, me.getY() - 80);
                mouseButton = me.getButton(); 
            }
            if(me.getButton() == 3 && fld.getBombs() - fld.getFlags() > 0 ){
                oldx = me.getX() - 40;
                oldy = me.getY() - 80;
                fld.mousePress(me.getX() - 40, me.getY() - 80,me.getX() - 40, me.getY() - 80);
                mouseButton = me.getButton(); 
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if(me.getX() - 7 > fld.getX() && me.getX() < fld.getWidth() + 37 &&
                me.getY() - 54 > fld.getY() && me.getY() < fld.getHeight() + 82) {
            if(!tmr.isRunning()) tmr.start();
            
            if(me.getButton() == 1) {
                fld.leftClick(me.getX() - 40, me.getY() - 80);

                if(fld.getDead()){
                    tmr.stop();
                    String title = null, date1 = "", date2 = "", date3 = "";
                    int besttime = -1, besttime2 = -1, besttime3 = -1;
                    if(fld.getWidthofField() == 9 && fld.getHeightofField() == 9 && fld.getBombs() == 10) title = "statseasy.dat";
                    if(fld.getWidthofField() == 16 && fld.getHeightofField() == 16 && fld.getBombs() == 40) title = "statsmedium.dat";
                    if(fld.getWidthofField() == 30 && fld.getHeightofField() == 16 && fld.getBombs() == 99) title = "statshard.dat";
                    if(title != null){
                        try(BufferedReader file = new BufferedReader(new FileReader(title))){
                            String riadok = file.readLine();
                            if(riadok != null) {
                                String[] row = riadok.split(";");
                                oh = Integer.parseInt(row[0]);
                                wins = Integer.parseInt(row[1]);
                                ratio = Integer.parseInt(row[2]);
                                nsv = Integer.parseInt(row[3]);
                                nsp = Integer.parseInt(row[4]);
                                as = Integer.parseInt(row[5]);

                                if(row.length > 7) besttime = Integer.parseInt(row[6]), date1 = row[7];
                                if(row.length > 9) besttime2 = Integer.parseInt(row[8]), date2 = row[9];
                                if(row.length > 11) besttime3 = Integer.parseInt(row[10]), date3 = row[11];
                            }
                            file.close();
                        } catch(IOException e){
                            try(BufferedWriter bw = new BufferedWriter(new FileWriter(title))){
                                bw.write("0;0;0;0;0;0");
                                bw.close();
                            } catch (IOException ex) {}
                        }
                        
                        ++oh;
                        ratio = 100 * wins / oh;
                        
                        if(as > 0) as = -1;
                        else --as;
                        
                        if(as * -1 > nsp) nsp = as * -1;
                        
                        String newstats = ""+oh+";"+wins+";"+ratio+";"+nsv+";"+nsp+";"+as;
                        if(besttime>-1) newstats+=";"+besttime+";"+date1;
                        if(besttime2>-1) newstats+=";"+besttime2+";"+date2;
                        if(besttime3>-1) newstats+=";"+besttime3+";"+date3;
                        try(BufferedWriter file = new BufferedWriter(new FileWriter(title))){
                            file.write(newstats);
                            file.close();
                        } catch (IOException e){}
                    }
                    lostgame lg = new lostgame(this);
                    lg.setVisible(true);
                    lg.setLocationRelativeTo(this);
                    this.setEnabled(false);
                }

                if(fld.getWidthofField() * fld.getHeightofField() - fld.getOpened() == fld.getBombs()){
                    tmr.stop();
                    fld.openAllTiles();
                    GregorianCalendar date = new GregorianCalendar();
                    int day = date.get(Calendar.DAY_OF_MONTH);
                    int month = date.get(Calendar.MONTH)+1;
                    int year = date.get(Calendar.YEAR);
                    int besttime = -1, besttime2= -1, besttime3= -1;
                    String date = "" + day + "." + month + "." + year, date1 = "", date2 = "", date3 = "";
                    String title = null;

                    if(fld.getWidthofField() == 9 && fld.getHeightofField() == 9 && fld.getBombs() == 10) title = "statseasy.dat";
                    if(fld.getWidthofField() == 16 && fld.getHeightofField() == 16 && fld.getBombs() == 40) title = "statsmedium.dat";
                    if(fld.getWidthofField() == 30 && fld.getHeightofField() == 16 && fld.getBombs() == 99) title = "statshard.dat";
                    if(title != null) {
                        try(BufferedReader file = new BufferedReader(new FileReader(title))) {
                            String riadok = file.readLine();
                            if(riadok != null) {
                                String[] row = riadok.split(";");
                                if(row.length > 5) {
                                    oh = Integer.parseInt(row[0]);
                                    wins = Integer.parseInt(row[1]);
                                    ratio = Integer.parseInt(row[2]);
                                    nsv = Integer.parseInt(row[3]);
                                    nsp = Integer.parseInt(row[4]);
                                    as = Integer.parseInt(row[5]);
                                }
                                if(row.length > 7) besttime = Integer.parseInt(row[6]), date1 = row[7];
                                if(row.length > 9) besttime2 = Integer.parseInt(row[8]), date2 = row[9];
                                if(row.length > 11) besttime3 = Integer.parseInt(row[10]), date3 = row[11];
                            }
                            file.close();
                        } catch (IOException e) {
                            try(BufferedWriter bw = new BufferedWriter(new FileWriter(title))){
                                bw.write("0;0;0;0;0;0");
                                bw.close();
                            } catch (IOException ex) {}
                        }
                        ++oh;
                        ++wins;
                        ratio = 100 * wins / oh;
                        if(as < 0) as = 1;
                        else ++as;
                        if(as > nsv) nsv = as;

                        if(besttime < 0) besttime = time, date1 = date;
                        else if(time < besttime) {
                                if(besttime2 > -1) besttime3 = besttime2, date3 = date2;
                                besttime2 = besttime;
                                date2 = date1;
                                besttime = time;
                                date1 = date;
                            } else if(besttime2 < 0) besttime2=time, date2 = date;
                            else if(time < besttime2) {
                                    besttime3 = besttime2;
                                    date3 = date2;
                                    besttime2 = time;
                                    date2 = date;
                                } else if(besttime3 < 0) besttime3 = time, date3 = date;
                                else if(time < besttime3) besttime3 = time, date3 = date;

                        String newstats = "" + oh + ";" + wins + ";" + ratio + ";" + nsv + ";" + nsp + ";" + as + ";"+besttime+";"+date1;
                        if(besttime2 > -1) newstats += ";" + besttime2 + ";" + date2;
                        if(besttime3 > -1) newstats += ";" + besttime3 + ";" + date3;
                        try(BufferedWriter file = new BufferedWriter(new FileWriter(title))) {
                            file.write(newstats);
                            file.close();
                        } catch (IOException e) {}
                    }

                    wongame wg;
                    if(title != null) wg = new wongame(this, time, besttime, oh, wins, ratio, date);
                    else wg = new wongame(this, time, date);

                    wg.setVisible(true);
                    wg.setLocationRelativeTo(this);
                    this.setEnabled(false);
                }
            }
            
            if(me.getButton() == 3)
                if(fld.getBombs() - fld.getFlags() > 0) {
                    fld.rightClick(me.getX() - 40, me.getY() - 80);
                    minesCounter.setNumber(fld.getBombs() - fld.getFlags());
                } else if(fld.isFlag(me.getX() - 40, me.getY() - 80)) {
                    fld.rightClick(me.getX() - 40, me.getY() - 80);
                    minesCounter.setNumber(fld.getBombs() - fld.getFlags());
                }
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
}
