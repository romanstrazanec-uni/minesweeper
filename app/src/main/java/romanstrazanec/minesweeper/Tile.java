package romanstrazanec.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Tile {
    private Point start;
    private float width;
    private int state, numberofnearbombs, color;
    private boolean bomb, flag, colorfulnumbers;

    public Tile(Point start, float width, int color, boolean colorfulnumbers) {
        numberofnearbombs = 0;
        state = 0;
        bomb = false;
        flag = false;

        this.start = start;
        this.width = width;
        this.color = color;
        this.colorfulnumbers = colorfulnumbers;
    }

    private void paintTile(Canvas canvas, Paint paint, int color) {
        paint.setColor(Color.BLACK);
        canvas.drawRect(start.x, start.y, start.x + width, start.y + width, paint);
        paint.setColor(color);
        canvas.drawRect(start.x + 2, start.y + 2, start.x + width - 2, start.y + width - 2, paint);
    }

    private void paintSelectedTile(Canvas canvas, Paint paint) {
        switch (color) {
            case Color.BLUE:
                paintTile(canvas, paint, Color.CYAN);
                break;
            case Color.GREEN:
                paintTile(canvas, paint, Color.YELLOW);
                break;
            case Color.YELLOW:
                paintTile(canvas, paint, Color.WHITE);
                break;
            case Color.RED:
                paintTile(canvas, paint, Color.MAGENTA);
                break;
        }
    }

    private void paintBomb(Canvas canvas, Paint paint, int color) {
        paintTile(canvas, paint, color);
        paint.setColor(Color.DKGRAY);
        canvas.drawCircle(start.x + width / 2, start.y + width / 2, (width - 4) / 2, paint);
    }

    private void paintBlownBomb(Canvas canvas, Paint paint) {
        if (color == Color.RED) paintBomb(canvas, paint, Color.YELLOW);
        else paintBomb(canvas, paint, Color.RED);
    }

    private void paintFlag(Canvas canvas, Paint paint) {
        switch (color) {
            case Color.BLUE | Color.GREEN:
                paintTile(canvas, paint, Color.GRAY);
                break;
            case Color.YELLOW:
                paintTile(canvas, paint, Color.rgb(200, 150, 0));
                break;
            case Color.RED:
                paintTile(canvas, paint, Color.MAGENTA);
                break;
        }

        if (color == Color.RED) paint.setColor(Color.YELLOW);
        else paint.setColor(Color.RED);

        canvas.drawRect(start.x + 4, start.y + 4, start.x + width - 4, start.y + width / 2 - 4, paint);
        paint.setColor(Color.WHITE);
        canvas.drawLine(start.x + width - 4, start.y + 4, start.x + width - 4, start.y + width - 4, paint);
    }

    private void paintOpenTile(Canvas canvas, Paint paint) {
        switch (color) {
            case Color.BLUE | Color.GREEN:
                paintTile(canvas, paint, Color.GRAY);
                break;
            case Color.YELLOW:
                paintTile(canvas, paint, Color.rgb(200, 150, 0));
                break;
            case Color.RED:
                paintTile(canvas, paint, Color.MAGENTA);
                break;
        }

        if (colorfulnumbers) {
            switch (numberofnearbombs) {
                case 1:
                    paint.setColor(Color.BLUE);
                    break;
                case 2:
                    paint.setColor(Color.GREEN);
                    break;
                case 3:
                    paint.setColor(Color.RED);
                    break;
                case 4:
                    paint.setColor(Color.MAGENTA);
                    break;
                case 5:
                    paint.setColor(Color.YELLOW);
                    break;
                case 6:
                    paint.setColor(Color.YELLOW);
                    break;
                case 7:
                    paint.setColor(Color.MAGENTA);
                    break;
                case 8:
                    paint.setColor(Color.CYAN);
                    break;
                default:
                    break;
            }
        } else paint.setColor(Color.BLACK);

        if (numberofnearbombs != 0) {
            canvas.drawText(String.valueOf(numberofnearbombs), start.x + width / 2 - 4, start.y + width / 2 - 4, paint);
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        switch (state) {
            case 0:
                paintTile(canvas, paint, color);
                break;
            case 1:
                paintSelectedTile(canvas, paint);
                break;
            case 2:
                paintOpenTile(canvas, paint);
                break;
            case 3:
                switch (color) {
                    case Color.BLUE | Color.GREEN:
                        paintTile(canvas, paint, Color.GRAY);
                        break;
                    case Color.YELLOW:
                        paintTile(canvas, paint, Color.rgb(200, 150, 0));
                        break;
                    case Color.RED:
                        paintTile(canvas, paint, Color.MAGENTA);
                        break;
                }
                break;
            case 4:
                paintBlownBomb(canvas, paint);
                break;
            case 5:
                paintFlag(canvas, paint);
                break;
            default:
                break;
        }
    }

    public boolean isBomb() {
        return bomb;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        if (!isOpen()) {
            this.flag = flag;
            state = flag ? 5 : 0;
        }
    }

    public boolean isOpen() {
        return (state == 2 || state == 4);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void openTile() {
        state = bomb ? 3 : 2;
    }

    public void selectTile(boolean select) {
        state = select ? 1 : 0;
    }

    public int getNumberofnearbombs() {
        return numberofnearbombs;
    }

    public void setNumberofnearbombs(int numberofnearbombs) {
        this.numberofnearbombs = numberofnearbombs;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setColorfulnumbers(boolean colorfulnumbers) {
        this.colorfulnumbers = colorfulnumbers;
    }
}
