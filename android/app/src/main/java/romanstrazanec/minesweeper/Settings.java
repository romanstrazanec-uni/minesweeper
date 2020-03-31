package romanstrazanec.minesweeper;

public class Settings {
    private long ID;
    private int width, height, mines, color, colorfulnumbers;

    public Settings(long ID, int width, int height, int mines, int color, int colorfulnumbers) {
        this.ID = ID;
        this.width = width;
        this.height = height;
        this.mines = mines;
        this.color = color;
        this.colorfulnumbers = colorfulnumbers;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColorfulnumbers() {
        return colorfulnumbers;
    }

    public void setColorfulnumbers(int colorfulnumbers) {
        this.colorfulnumbers = colorfulnumbers;
    }
}
