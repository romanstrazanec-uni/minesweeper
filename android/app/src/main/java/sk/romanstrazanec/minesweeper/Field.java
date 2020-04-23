package sk.romanstrazanec.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Field {
    private final Point start;
    private final float windowWidth, windowHeight;
    private int width, height, flags, widthOfTile, opened;
    private final int bombs, color;
    private boolean isFirstClick, dead;
    private final boolean colorfulNumbers;
    private Tile[][] tiles;

    public Field(Point start, float windowWidth, float windowHeight, Settings settings) {
        this.start = start;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        if (settings != null) {
            width = settings.getWidth();
            height = settings.getHeight();
            bombs = settings.getMines();
            switch (settings.getColor()) {
                case 2:
                    color = Color.GREEN;
                    break;
                case 3:
                    color = Color.YELLOW;
                    break;
                case 4:
                    color = Color.RED;
                    break;
                case 1:
                default:
                    color = Color.BLUE;
                    break;
            }
            colorfulNumbers = settings.getColorfulNumbers() == 1;
        } else {
            width = 9;
            height = 9;
            bombs = 10;
            color = Color.BLUE;
            colorfulNumbers = true;
        }
        setWidthOfTile();
        flags = 0;
        opened = 0;
        isFirstClick = false;
        makeField(width, height, bombs);
    }

    private void setWidthOfTile() {
        if (width > height) {
            int c = width;
            //noinspection SuspiciousNameCombination
            width = height;
            height = c;
        }
        widthOfTile = (int) windowWidth / width;
        if (widthOfTile * height > windowHeight) widthOfTile = (int) (windowHeight * .75f) / height;
    }

    private void makeField(int width, int height, int bombs) {
        Point tileStart;
        tiles = new Tile[width][height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                tileStart = new Point(start.x + widthOfTile * i, start.y + widthOfTile * j);
                Tile tile = new Tile(tileStart, widthOfTile, color, colorfulNumbers);
                tiles[i][j] = tile;
            }
        }
        placeBombs(bombs);
        setNumbers();
    }

    private void placeBombs(int numberOfBombs) {
        for (int i = 0; i < numberOfBombs; i++) placeBomb();
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
        if (tiles[x][y].getNumberOfNearBombs() == 0) {
            if (x > 0 && y > 0 && !tiles[x - 1][y - 1].isFlag() && tiles[x - 1][y - 1].isClosed() && tiles[x - 1][y - 1].getNumberOfNearBombs() >= 0)
                openTiles(x - 1, y - 1);
            if (y > 0 && !tiles[x][y - 1].isFlag() && tiles[x][y - 1].isClosed() && tiles[x][y - 1].getNumberOfNearBombs() >= 0)
                openTiles(x, y - 1);
            if (x < width - 1 && y > 0 && !tiles[x + 1][y - 1].isFlag() && tiles[x + 1][y - 1].isClosed() && tiles[x + 1][y - 1].getNumberOfNearBombs() >= 0)
                openTiles(x + 1, y - 1);
            if (x > 0 && !tiles[x - 1][y].isFlag() && tiles[x - 1][y].isClosed() && tiles[x - 1][y].getNumberOfNearBombs() >= 0)
                openTiles(x - 1, y);
            if (x < width - 1 && !tiles[x + 1][y].isFlag() && tiles[x + 1][y].isClosed() && tiles[x + 1][y].getNumberOfNearBombs() >= 0)
                openTiles(x + 1, y);
            if (x > 0 && y < height - 1 && !tiles[x - 1][y + 1].isFlag() && tiles[x - 1][y + 1].isClosed() && tiles[x - 1][y + 1].getNumberOfNearBombs() >= 0)
                openTiles(x - 1, y + 1);
            if (y < height - 1 && !tiles[x][y + 1].isFlag() && tiles[x][y + 1].isClosed() && tiles[x][y + 1].getNumberOfNearBombs() >= 0)
                openTiles(x, y + 1);
            if (x < width - 1 && y < height - 1 && !tiles[x + 1][y + 1].isFlag() && tiles[x + 1][y + 1].isClosed() && tiles[x + 1][y + 1].getNumberOfNearBombs() >= 0)
                openTiles(x + 1, y + 1);
        }
    }

    private void setNumbers() {
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                if (!tiles[x][y].isBomb())
                    tiles[x][y].setNumberOfNearBombs(getNumberOfNearBombs(x, y));
    }

    private int getNumberOfNearBombs(int x, int y) {
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
            for (int i = 0; i < width; i++)
                if (tiles[i][j].isClosed() && tiles[i][j].isBomb()) tiles[i][j].openTile();
    }

    public void click(float x, float y) {
        int tileX = (int) ((x - start.x) / widthOfTile);
        int tileY = (int) ((y - start.y) / widthOfTile);
        if (!isFirstClick && tiles[tileX][tileY].isBomb()) replaceBomb(tileX, tileY);
        if (!isFirstClick) isFirstClick = true;

        if (tileX < width && tileY < height && !tiles[tileX][tileY].isFlag() && tiles[tileX][tileY].isClosed()) {
            if (!tiles[tileX][tileY].isBomb()) openTiles(tileX, tileY);
            else {
                tiles[tileX][tileY].setState(4);
                dead = true;
                openAllTiles();
            }
        }
    }

    public void longClick(float x, float y) {
        int tileX = (int) ((x - start.x) / widthOfTile);
        int tileY = (int) ((y - start.y) / widthOfTile);
        if (tileX < width && tileY < height) placeFlag(tileX, tileY);
    }

    public void draw(Canvas canvas, Paint paint) {
        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++)
                tiles[i][j].draw(canvas, paint);
    }

    public Point getStart() {
        return start;
    }

    public int getWidthOfField() {
        return width;
    }

    public int getHeightOfField() {
        return height;
    }

    public int getBombs() {
        return bombs;
    }

    public int getWidthOfTile() {
        return widthOfTile;
    }

    public int getFlags() {
        return flags;
    }

    public boolean isFlag(float x, float y) {
        int tileX = (int) ((x - start.x) / widthOfTile);
        int tileY = (int) ((y - start.y) / widthOfTile);
        return tiles[tileX][tileY].isFlag();
    }

    public boolean isWon() {
        return getWidthOfField() * getHeightOfField() - getOpened() == getBombs();
    }

    public boolean isDead() {
        return dead;
    }

    public int getOpened() {
        return opened;
    }

    public Rect getRect() {
        return new Rect(start.x, start.y, start.x + widthOfTile * width, start.y + widthOfTile * height);
    }
}
