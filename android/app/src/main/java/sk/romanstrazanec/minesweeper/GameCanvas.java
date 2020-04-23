package sk.romanstrazanec.minesweeper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.Objects;

public class GameCanvas extends View {
    DBHelper dbh;
    Paint paint;
    Point size;
    Field field;
    Counter timeCounter, minesCounter;
    Banner banner;
    int time, longClickCount;
    boolean longClick, timer;

    public GameCanvas(Context context) {
        super(context);
        dbh = new DBHelper(context);
        paint = new Paint();
        setMaxPoint(context);
        newGame();
        longClickCount = 0;
        longClick = false;
    }

    private void setMaxPoint(Context context) {
        size = new Point();
        ((WindowManager) Objects.requireNonNull(context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getSize(size);
    }

    public void newGame() {
        timer = false;
        time = 0;
        field = new Field(new Point(0, (int) (size.y * .05)), size.x, size.y, dbh.getSettings());
        timeCounter = new Counter(5, size.y * .035f, (float)field.getWidthOfTile() / 2, time);
        minesCounter = new Counter(size.x - (float)field.getWidthOfTile() / 2 - 15, size.y * .035f, (float)field.getWidthOfTile() / 2, field.getBombs());
        banner = new Banner(new Rect(field.getStart().x + field.getWidthOfTile() * field.getWidthOfField() / 3, field.getStart().y + field.getWidthOfTile() * field.getHeightOfField() / 3,
                field.getStart().x + field.getWidthOfField() * field.getWidthOfTile() - field.getWidthOfTile() * field.getWidthOfField() / 3,
                field.getStart().y + field.getHeightOfField() * field.getWidthOfTile() - field.getWidthOfTile() * field.getHeightOfField() / 3));
    }

    public void update() {
        if (longClick) longClickCount++;
        if (timer) {
            time++;
            timeCounter.setNumber(time / 10);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (field.getRect().contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
            if (!field.isDead() && !field.isWon()) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        longClick = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        timer = true;
                        if (longClickCount > 3) {
                            if (field.getBombs() - field.getFlags() > 0 || field.isFlag(motionEvent.getX(), motionEvent.getY())) {
                                field.longClick(motionEvent.getX(), motionEvent.getY());
                                minesCounter.setNumber(field.getBombs() - field.getFlags());
                            }
                        } else {
                            field.click(motionEvent.getX(), motionEvent.getY());
                            if (field.isDead()) {
                                timer = false;
                                setLossBanner();
                            }
                            if (field.isWon()) {
                                timer = false;
                                field.openAllTiles();
                                setWinBanner();
                            }
                        }
                        longClick = false;
                        longClickCount = 0;
                        break;
                    default:
                        break;
                }
            } else newGame();
            invalidate();
            return true;
        }
        return false;
    }

    private void setWinBanner() {
        banner.setColor(Color.argb(60, 0, 255, 0));
        banner.setTextColor(Color.GREEN);
        banner.setText("WIN!");
        banner.setVisible(true);
    }

    private void setLossBanner() {
        banner.setColor(Color.argb(60, 255, 0, 0));
        banner.setTextColor(Color.RED);
        banner.setText("TRY AGAIN!");
        banner.setVisible(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        field.draw(canvas, paint);
        timeCounter.draw(canvas, paint);
        minesCounter.draw(canvas, paint);
        banner.draw(canvas, paint);
    }

    public boolean isTimer() {
        return timer;
    }
}
