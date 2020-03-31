package romanstrazanec.minesweeper;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {
    DBHelper dbh = new DBHelper(this);
    EditText etw, eth, etm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etw = findViewById(R.id.width);
        eth = findViewById(R.id.height);
        etm = findViewById(R.id.mines);
        setSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_last_saved:
                setSettings();
                return true;
            case R.id.menu_default_settings:
                ((RadioGroup) findViewById(R.id.difficulty)).check(R.id.easy);
                setValuesToSizes(9, 9, 10);
                setSizesEnabled(false);
                ((RadioGroup) findViewById(R.id.color)).check(R.id.blue);
                ((CheckBox) findViewById(R.id.colorful_numbers)).setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void save(View view) {
        dbh.updateSettings(new Settings(1, checkWidth(), checkHeight(), checkMines(), checkColor(), checkColorfulNumbers()));
        setResult(RESULT_OK);
        finish();
    }

    private void setSettings() {
        Settings s = dbh.getSettings();
        RadioGroup rg = findViewById(R.id.difficulty);
        setSizesEnabled(false);

        if (s.getWidth() == 9 && s.getHeight() == 9 && s.getMines() == 10) rg.check(R.id.easy);
        else if (s.getWidth() == 16 && s.getHeight() == 16 && s.getMines() == 40)
            rg.check(R.id.intermediate);
        else if (s.getWidth() == 30 && s.getHeight() == 16 && s.getMines() == 99)
            rg.check(R.id.hard);
        else {
            rg.check(R.id.custom);
            setSizesEnabled(true);
        }
        setValuesToSizes(s.getWidth(), s.getHeight(), s.getMines());

        rg = findViewById(R.id.color);
        switch (s.getColor()) {
            case 1:
                rg.check(R.id.blue);
                break;
            case 2:
                rg.check(R.id.green);
                break;
            case 3:
                rg.check(R.id.yellow);
                break;
            case 4:
                rg.check(R.id.red);
                break;
            default:
                rg.check(R.id.blue);
                break;
        }
        ((CheckBox) findViewById(R.id.colorful_numbers)).setChecked(s.getColorfulnumbers() == 1);
    }

    private int checkWidth() {
        try {
            int width = Integer.parseInt(etw.getText().toString());
            if (width < 9) width = 9;
            else if (width > 30) width = 30;
            return width;
        } catch (NumberFormatException e) {
            return 9;
        }
    }

    private int checkHeight() {
        try {
            int height = Integer.parseInt(eth.getText().toString());
            if (height < 9) height = 9;
            else if (height > 24) height = 24;
            return height;
        } catch (NumberFormatException e) {
            return 9;
        }
    }

    private int checkMines() {
        try {
            int mines = Integer.parseInt(etm.getText().toString());
            if (mines < 10) mines = 10;
            else if (mines > checkWidth() * checkHeight() - 10)
                mines = checkWidth() * checkHeight() - 10;
            return mines;
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    private int checkColor() {
        switch (((RadioGroup) findViewById(R.id.color)).getCheckedRadioButtonId()) {
            case R.id.blue:
                return 1;
            case R.id.green:
                return 2;
            case R.id.yellow:
                return 3;
            case R.id.red:
                return 4;
            default:
                return 1;
        }
    }

    private int checkColorfulNumbers() {
        return ((CheckBox) findViewById(R.id.colorful_numbers)).isChecked() ? 1 : 0;
    }

    public void onRadioGroupClick(View view) {
        switch (((RadioGroup) findViewById(R.id.difficulty)).getCheckedRadioButtonId()) {
            case R.id.easy:
                setValuesToSizes(9, 9, 10);
                setSizesEnabled(false);
                break;
            case R.id.intermediate:
                setValuesToSizes(16, 16, 40);
                setSizesEnabled(false);
                break;
            case R.id.hard:
                setValuesToSizes(30, 16, 99);
                setSizesEnabled(false);
                break;
            case R.id.custom:
                setSizesEnabled(true);
            default:
                break;
        }
    }

    private void setSizesEnabled(boolean enabled) {
        etw.setEnabled(enabled);
        eth.setEnabled(enabled);
        etm.setEnabled(enabled);
    }

    private void setValuesToSizes(int width, int height, int mines) {
        etw.setText(String.valueOf(width));
        eth.setText(String.valueOf(height));
        etm.setText(String.valueOf(mines));
    }
}
