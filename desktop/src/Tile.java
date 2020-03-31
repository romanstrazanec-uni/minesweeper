
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class Tile extends JComponent {
    private int status, nbombsaround;
    private boolean bomb, flag, colorfulnumbers;
    private Color cl;
    
    public Tile(int x, int y, int width, Color cl, boolean colorfulnumbers){
        nbombsaround = 0;
        status = 0;
        bomb = false;
        this.flag = false;
        this.cl = cl;
        this.colorfulnumbers = colorfulnumbers;
        setBounds(x, y, width, width);
    }

    private void paintTile(Graphics g, Color c){
        g.setColor(c);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.black);
        g.drawRect(0, 0, getWidth(), getHeight());
    }

    private void paintSelectedTile(Graphics g){
        if(cl == Color.blue) paintTile(g, Color.cyan);
        if(cl == Color.green) paintTile(g, Color.yellow);
        if(cl == Color.yellow) paintTile(g, Color.white);
        if(cl == Color.red) paintTile(g, Color.magenta);
    }

    private void paintBomb(Graphics g, Color c){
        paintTile(g, c);
        g.setColor(Color.darkGray);
        g.fillOval(3, 3, getWidth() - 6, getHeight() - 6);
    }
    private void paintBlownBomb(Graphics g){
        if(cl == Color.red) paintBomb(g, Color.yellow);
        else paintBomb(g, Color.red);
    }
    private void paintFlag(Graphics g){
        if(cl == Color.blue) paintTile(g, Color.gray);
        if(cl == Color.green) paintTile(g, Color.gray);
        if(cl == Color.yellow) paintTile(g, new Color(200,150,0));
        if(cl == Color.red) paintTile(g, Color.pink);
        if(cl == Color.red) g.setColor(Color.yellow);
        else g.setColor(Color.red);
        g.fillRect(3, 3, getWidth() - 6, getHeight()/2 - 6);
        g.setColor(Color.white);
        g.drawLine(getWidth() - 3, 3, getWidth() - 3, getHeight() - 3);
    }

    private void paintOpenTile(Graphics g){
        if(cl == Color.blue) paintTile(g, Color.gray);
        if(cl == Color.green) paintTile(g, Color.gray);
        if(cl == Color.yellow) paintTile(g, new Color(200,150,0));
        if(cl == Color.red) paintTile(g, Color.pink);
        if(colorfulnumbers){
            switch(nbombsaround){
                case 1: g.setColor(Color.BLUE);break;
                case 2: g.setColor(Color.GREEN);break;
                case 3: g.setColor(Color.RED);break;
                case 4: g.setColor(Color.MAGENTA);break;
                case 5: g.setColor(Color.ORANGE);break;
                case 6: g.setColor(Color.YELLOW);break;
                case 7: g.setColor(Color.PINK);break;
                case 8: g.setColor(Color.CYAN);break;
                default: break;
            }
        } else g.setColor(Color.BLACK);
        if(nbombsaround != 0) {
            g.drawString("" + nbombsaround, 12, 20);
            g.drawString("" + nbombsaround, 13, 20);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        switch(status){
            case 0: paintTile(g, cl); break;
            case 1: paintSelectedTile(g); break;
            case 2: paintOpenTile(g); break;
            case 3: if(cl == Color.blue) paintBomb(g, Color.gray);
                    if(cl == Color.green) paintBomb(g, Color.gray);
                    if(cl == Color.yellow) paintBomb(g, new Color(200,150,0));
                    if(cl == Color.red) paintBomb(g, Color.pink); break;
            case 4: paintBlownBomb(g); break;
            case 5: paintFlag(g); break;
            default: break;
        }
    }

    public boolean isBomb(){ return bomb; }
    public boolean isFlag(){ return flag; }
    public boolean isOpen(){ return (status==2 || status==4); }
    public void setBomb(boolean bomb){ this.bomb = bomb; }
    public void setFlag(boolean flag){
        if (!isOpen()) {
            this.flag = flag;
            status = (flag) ? 5 : 0;
            repaint();
        }
    }
    public int getstatus(){ return status; }
    public void setstatus(int status){
        this.status = status;
        repaint();
    }
    public void openTile(){
        if(bomb) status = 3;
        else status = 2;
        repaint();
    }
    public void selectTile(boolean select){
        status = (select) ? 1 : 0;
        repaint();
    }
    public void setnbombsaround(int amount){ nbombsaround = amount; }
    public int getnbombsaround(){ return nbombsaround; }
    public void setWidth(int width){
        setBounds(0, 0, width, width);
        repaint();
    }
    public void setColor(Color cl){
        this.cl = cl;
        repaint();
    }
    public void setColorfulNumbers(boolean cn){
        colorfulnumbers = cn;
        repaint();
    }
}
