package romanstrazanec.minesweeper;

import android.graphics.Color;
import android.graphics.Point;

public class Field {
    Point start;
    private float windowWidth, windowHeight;
    private int width, height, bombs, flags, widthoftile, opened, color;
    private boolean firstclick, dead, colorfulnumbers;
    private Tile[][] tiles;

    public Field(Point start, float windowWidth, float windowHeight) {
        this.start = start;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        width = 9;
        height = 9;
        bombs = 10;
        color = Color.BLUE;
        colorfulnumbers = true;
        setWidthOfTile();

        flags = 0;
        opened = 0;
        firstclick = false;
        makeField(width, height, bombs);
    }

    private void setWidthOfTile() {
        if (windowWidth > windowHeight) {
            if (width > height) widthoftile = (int) windowHeight / width;
            else widthoftile = (int) windowHeight / height;
        } else {
            if (width > height) widthoftile = (int) windowWidth / width;
            else widthoftile = (int) windowWidth / height;
        }
    }

    private void makeField(int width, int height, int bombs) {
        Point tileStart;
        tiles = new Tile[width][height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                tileStart = new Point(start.x + widthoftile * i, start.y + widthoftile * j);
                Tile tile = new Tile(tileStart, widthoftile, color, colorfulnumbers);
                tiles[i][j] = tile;
            }
        }
        placeBombs(bombs);
        setNumbers();
    }

    private void placeBombs(int numberofbombs) {
        for (int i = 0; i < numberofbombs; i++) placeBomb();
    }

    private void placeBomb() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        if (!tiles[x][y].isBomb()) tiles[x][y].setBomb(true);
        else placeBomb();
    }

    private void replaceBomb(int x, int y) {
        if (x < width && y < height) {
            if (tiles[x][y].isBomb()) placeBomb();
            tiles[x][y].setBomb(false);
            setNumbers();
        }
    }

    private void openTiles(int x, int y) {
        tiles[x][y].openTile();
        opened++;
        if (tiles[x][y].getNumberofnearbombs() == 0) {
            if (x > 0 && y > 0 && !tiles[x - 1][y - 1].isFlag() && !tiles[x - 1][y - 1].isOpen() && tiles[x - 1][y - 1].getNumberofnearbombs() >= 0)
                openTiles(x - 1, y - 1);
            if (y > 0 && !tiles[x][y - 1].isFlag() && !tiles[x][y - 1].isOpen() && tiles[x][y - 1].getNumberofnearbombs() >= 0)
                openTiles(x, y - 1);
            if (x < width - 1 && y > 0 && !tiles[x + 1][y - 1].isFlag() && !tiles[x + 1][y - 1].isOpen() && tiles[x + 1][y - 1].getNumberofnearbombs() >= 0)
                openTiles(x + 1, y - 1);
            if (x > 0 && !tiles[x - 1][y].isFlag() && !tiles[x - 1][y].isOpen() && tiles[x - 1][y].getNumberofnearbombs() >= 0)
                openTiles(x - 1, y);
            if (x < width - 1 && !tiles[x + 1][y].isFlag() && !tiles[x + 1][y].isOpen() && tiles[x + 1][y].getNumberofnearbombs() >= 0)
                openTiles(x + 1, y);
            if (x > 0 && y < height - 1 && !tiles[x - 1][y + 1].isFlag() && !tiles[x - 1][y + 1].isOpen() && tiles[x - 1][y + 1].getNumberofnearbombs() >= 0)
                openTiles(x - 1, y + 1);
            if (y < height - 1 && !tiles[x][y + 1].isFlag() && !tiles[x][y + 1].isOpen() && tiles[x][y + 1].getNumberofnearbombs() >= 0)
                openTiles(x, y + 1);
            if (x < width - 1 && y < height - 1 && !tiles[x + 1][y + 1].isFlag() && !tiles[x + 1][y + 1].isOpen() && tiles[x + 1][y + 1].getNumberofnearbombs() >= 0)
                openTiles(x + 1, y + 1);
        }
    }

    private void setNumbers() {
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                if (!tiles[x][y].isBomb())
                    tiles[x][y].setNumberofnearbombs(getNumberofnearbombs(x, y));
            }
    }

    private int getNumberofnearbombs(int x, int y) {
        int result = 0;
        if (x > 0 && y > 0 && tiles[x - 1][y - 1].isBomb()) result++;
        if (y > 0 && tiles[x][y - 1].isBomb()) result++;
        if (x < width - 1 && y > 0 && tiles[x + 1][y - 1].isBomb()) result++;
        if (x > 0 && tiles[x - 1][y].isBomb()) result++;
        if (x < width - 1 && tiles[x + 1][y].isBomb()) result++;
        if (x > 0 && y < height - 1 && tiles[x - 1][y + 1].isBomb()) result++;
        if (y < height - 1 && tiles[x][y + 1].isBomb()) result++;
        if (x < width - 1 && y < height - 1 && tiles[x + 1][y + 1].isBomb()) result++;
        return result;
    }

    private void placeFlag(int x, int y) {
        if (tiles[x][y].isFlag()) flags--;
        else flags++;
        tiles[x][y].setFlag(!tiles[x][y].isFlag());
    }

    public void openAllTiles() {
        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++) {
                if (!tiles[i][j].isOpen() && tiles[i][j].isBomb()) tiles[i][j].openTile();
            }
    }

    public void click(float x, float y) {
        int tileX = (int) ((x - start.x) / widthoftile);
        int tileY = (int) ((y - start.y) / widthoftile);
        if (!firstclick && tiles[tileX][tileY].isBomb()) replaceBomb(tileX, tileY);
        if (!firstclick) firstclick = true;

        if (tileX < width && tileY < height && !tiles[tileX][tileY].isFlag() && !tiles[tileX][tileY].isOpen()) {
            if (!tiles[tileX][tileY].isBomb()) openTiles(tileX, tileY);
            else {
                tiles[tileX][tileY].setState(4);
                dead = true;
                openAllTiles();
            }
        }
    }

    public void longClick(float x, float y) {
        int tileX = (int) ((x - start.x) / widthoftile);
        int tileY = (int) ((y - start.y) / widthoftile);
        if (x < width && y < height) placeFlag(tileX, tileY);
    }

    public int getWidthofField() {
        return width;
    }

    public int getHeightofField() {
        return height;
    }

    public int getBombs() {
        return bombs;
    }

    public int getWidthofTile() {
        return widthoftile;
    }

    public boolean getFirstClick() {
        return firstclick;
    }

    public int getFlags() {
        return flags;
    }

    public boolean isFlag(float x, float y) {
        int tileX = (int) ((x - start.x) / widthoftile);
        int tileY = (int) ((y - start.y) / widthoftile);
        return tiles[tileX][tileY].isFlag();
    }

    public boolean isDead() {
        return dead;
    }

    public int getOpened() {
        return opened;
    }
}
