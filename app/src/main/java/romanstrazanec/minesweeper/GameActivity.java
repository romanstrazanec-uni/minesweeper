package romanstrazanec.minesweeper;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_game:
                gameCanvas.newGame();
                return true;
            case R.id.menu_leave_game:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
