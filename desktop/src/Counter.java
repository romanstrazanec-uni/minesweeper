
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class Counter extends JComponent {
    private int number;

    public Counter(int number, int x, int y) {
        this.number = number;
        setBounds(x, y, 30, 10);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
        g.drawString("" + number, 1, 10);
        g.drawString("" + number, 2, 10);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        repaint();
    }
}
