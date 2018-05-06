package romanstrazanec.minesweeper;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    UpdateThread updateThread;
    Handler updateHandler;
    GameCanvas gameCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        gameCanvas = new GameCanvas(this);
        setContentView(gameCanvas);
        createHandler();
        updateThread = new UpdateThread(updateHandler);
        updateThread.start();
    }

    private void createHandler() {
        updateHandler = new Handler() {
            public void handleMessage(Message msg) {
                gameCanvas.update();
                gameCanvas.invalidate();
                super.handleMessage(msg);
            }
        };
    }
}
