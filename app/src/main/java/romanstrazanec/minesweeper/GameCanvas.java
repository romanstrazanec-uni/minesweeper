package romanstrazanec.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class GameCanvas extends View {
    Paint paint;
    Point size;
    Field field;
    Counter timeCounter, minesCounter;
    int time, longClickCount;
    boolean longClick, timer;


    public GameCanvas(Context context) {
        super(context);
        paint = new Paint();
        size = getMaxPoint(context);
        newGame();
        longClickCount = 0;
        longClick = false;
    }

    private Point getMaxPoint(Context context) {
        Point size = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        return size;
    }

    public void newGame() {
        timer = false;
        time = 0;
        field = new Field(new Point(0, 0), size.x, size.y);
        timeCounter = new Counter(0, 0, time);
        minesCounter = new Counter(size.x - 5, 0, field.getBombs());
    }

    public void update() {
        if (longClick) longClickCount++;
        if (timer) {
            time++;
            timeCounter.setNumber(time);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                longClick = true;
                break;
            case MotionEvent.ACTION_UP:
                timer = true;
                if (longClickCount > 1) {
                    if (field.getBombs() - field.getFlags() > 0 || field.isFlag(motionEvent.getX(), motionEvent.getY())) {
                        field.longClick(motionEvent.getX(), motionEvent.getY());
                        minesCounter.setNumber(field.getBombs() - field.getFlags());
                    }
                } else {
                    field.click(motionEvent.getX(), motionEvent.getY());

                    if (field.isDead()) {
                        timer = false;
                    }

                    if (field.getWidthofTile() * field.getHeightofField() - field.getOpened() == field.getBombs()) {
                        timer = false;
                        field.openAllTiles();
                    }
                }
                longClick = false;
                longClickCount = 0;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        field.draw(canvas, paint);
        timeCounter.draw(canvas, paint);
        minesCounter.draw(canvas, paint);
    }
}
