package sk.romanstrazanec.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Banner {
    private final Rect rect;
    private String text;
    private boolean visible;
    private int color, textColor;

    public Banner(Rect rect) {
        this.rect = rect;
        text = "";
        visible = false;
        color = Color.WHITE;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void draw(Canvas canvas, Paint paint) {
        if (visible) {
            paint.setColor(color);
            canvas.drawRect(rect.left + 2, rect.top + 2, rect.right - 2, rect.bottom - 2, paint);
            paint.setColor(textColor);
            paint.setTextSize(((float)rect.right - rect.left) / text.length());
            canvas.drawText(text, rect.centerX() - (float)rect.width() / 4, rect.centerY(), paint);
        }
    }
}
