package romanstrazanec.minesweeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        etw = findViewById(R.id.width);
        eth = findViewById(R.id.height);
        etm = findViewById(R.id.mines);
        setSettings();
    }

    public void save(View view) {
        Settings s = new Settings(1, checkWidth(), checkHeight(), checkMines(), checkColor(), checkColorfulNumbers());
        dbh.updateSettings(s);
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
        else if (s.getWidth() == 16 && s.getHeight() == 30 && s.getMines() == 99)
            rg.check(R.id.hard);
        else {
            rg.check(R.id.custom);
            setSizesEnabled(true);
        }
        etw.setText(String.valueOf(s.getWidth()));
        eth.setText(String.valueOf(s.getHeight()));
        etm.setText(String.valueOf(s.getMines()));

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
                etw.setText(String.valueOf(9));
                eth.setText(String.valueOf(9));
                etm.setText(String.valueOf(10));
                setSizesEnabled(false);
                break;
            case R.id.intermediate:
                etw.setText(String.valueOf(16));
                eth.setText(String.valueOf(16));
                etm.setText(String.valueOf(40));
                setSizesEnabled(false);
                break;
            case R.id.hard:
                etw.setText(String.valueOf(30));
                eth.setText(String.valueOf(16));
                etm.setText(String.valueOf(99));
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
}
