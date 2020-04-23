package sk.romanstrazanec.minesweeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "minesweeper.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String I = "INTEGER", PK = "INTEGER PRIMARY KEY AUTOINCREMENT";
        createTable(db, Contract.Settings.TABLE_NAME,
                new String[]{
                        Contract.Settings.COLUMN_ID,
                        Contract.Settings.COLUMN_WIDTH,
                        Contract.Settings.COLUMN_HEIGHT,
                        Contract.Settings.COLUMN_MINES,
                        Contract.Settings.COLUMN_COLOR,
                        Contract.Settings.COLUMN_COLORFUL_NUMBERS
                },
                new String[]{PK, I, I, I, I, I});

        createSettings(db, new Settings(1, 9, 9, 10, 1, 1));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        dropTable(sqLiteDatabase, Contract.Settings.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    private void dropTable(SQLiteDatabase db, @SuppressWarnings("SameParameterValue") String tableName) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }

    private void createTable(SQLiteDatabase db, @SuppressWarnings("SameParameterValue") String tableName,
                             String[] columns, String[] types) {
        if (columns.length != types.length) return;

        StringBuilder c = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            c.append(columns[i]).append(" ").append(types[i]);
            if (i != columns.length - 1) c.append(", ");
        }

        String query = "CREATE TABLE " + tableName + " (" + c + ")";
        db.execSQL(query);
    }

    private ContentValues createValues(Settings s) {
        ContentValues values = new ContentValues();
        values.put(Contract.Settings.COLUMN_WIDTH, s.getWidth());
        values.put(Contract.Settings.COLUMN_HEIGHT, s.getHeight());
        values.put(Contract.Settings.COLUMN_MINES, s.getMines());
        values.put(Contract.Settings.COLUMN_COLOR, s.getColor());
        values.put(Contract.Settings.COLUMN_COLORFUL_NUMBERS, s.getColorfulNumbers());
        return values;
    }

    private void createSettings(SQLiteDatabase db, Settings s) {
        ContentValues values = createValues(s);

        if (db == null) {
            db = getWritableDatabase();
            db.insert(Contract.Settings.TABLE_NAME, null, values);
            db.close();
        } else db.insert(Contract.Settings.TABLE_NAME, null, values);
    }

    public void updateSettings(Settings s) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(Contract.Settings.TABLE_NAME, createValues(s), Contract.Settings.COLUMN_ID + " = ?", new String[]{"1"});
        db.close();
    }

    public Settings getSettings() {
        SQLiteDatabase db = getWritableDatabase();
        try (Cursor c = db.rawQuery("SELECT * FROM " + Contract.Settings.TABLE_NAME, null)) {
            c.moveToFirst();
            db.close();
            return new Settings(c.getLong(c.getColumnIndex(Contract.Settings.COLUMN_ID)),
                    c.getInt(c.getColumnIndex(Contract.Settings.COLUMN_WIDTH)),
                    c.getInt(c.getColumnIndex(Contract.Settings.COLUMN_HEIGHT)),
                    c.getInt(c.getColumnIndex(Contract.Settings.COLUMN_MINES)),
                    c.getInt(c.getColumnIndex(Contract.Settings.COLUMN_COLOR)),
                    c.getInt(c.getColumnIndex(Contract.Settings.COLUMN_COLORFUL_NUMBERS)));
        }
    }
}
