package sk.romanstrazanec.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Counter {
    private int number;
    private final float x, y, textSize;

    public Counter(float x, float y, float textSize, int number) {
        this.x = x;
        this.y = y;
        this.number = number;
        this.textSize = textSize;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(textSize);
        canvas.drawText(String.valueOf(number), x, y, paint);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
