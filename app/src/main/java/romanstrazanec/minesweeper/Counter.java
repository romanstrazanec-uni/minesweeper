package romanstrazanec.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Counter {
    private int number;
    private float x, y;

    public Counter(float x, float y, int number) {
        this.x = x;
        this.y = y;
        this.number = number;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(12);
        canvas.drawText(String.valueOf(number), x, y, paint);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
