package sk.romanstrazanec.minesweeper;

public interface Contract {
    interface Settings {
        String TABLE_NAME = "Settings";
        String COLUMN_ID = "_id";
        String COLUMN_WIDTH = "width";
        String COLUMN_HEIGHT = "height";
        String COLUMN_MINES = "mines";
        String COLUMN_COLOR = "color";
        String COLUMN_COLORFUL_NUMBERS = "colorful_numbers";
    }
}
