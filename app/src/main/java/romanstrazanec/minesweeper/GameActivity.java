package romanstrazanec.minesweeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
            case R.id.menu_settings:
                startActivityForResult(new Intent(GameActivity.this, SettingsActivity.class), 1);
                return true;
            case R.id.menu_leave_game:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK)
                    Toast.makeText(this, R.string.changes_appear_in_new_game, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (gameCanvas.isTimer() && !gameCanvas.field.isDead() && !gameCanvas.field.isWon()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
            builder.setMessage(R.string.are_you_sure_you_want_to_leave_game).setTitle(R.string.leave_game);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        } else super.onBackPressed();
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
