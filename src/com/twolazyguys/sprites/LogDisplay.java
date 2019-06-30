package com.twolazyguys.sprites;

public class LogDisplay extends Sprite {

    private int rows, columns;
    private Text[] display;
    private int filledRows = 0;

    public LogDisplay(int x, int y, int rows, int columns) {
        super(x, y);
        this.rows = rows;
        this.columns = columns;

        display = new Text[rows];
        for (int i = 0; i < display.length; i++)
            display[display.length - i - 1] = new Text(0, i * Text.getLetterSizeY(), "");
    }

    @Override
    public float[][] getColors() {
        float[][] res = new float[columns * Text.getLetterSizeX()][rows * Text.getLetterSizeY()];

        for (Text text : display) storeColors(text, res);

        return res;
    }

    public void pushOutput(String[] str) {
        for (int i = Math.max(0, str.length - rows); i < str.length; i++) pushOutput(str[i]);
    }

    public void pushOutput(String str) {
        String res = "";
        if (str.length() > columns) {
            res = str.substring(columns - 1);
            str = str.substring(0, columns - 1);
        }

        if (filledRows < rows) {
            display[filledRows].setValue(str);
            filledRows++;
        } else {
            for (int i = 0; i < rows - 1; i++) {
                display[i].setValue(display[i + 1].getValue());
            }
            display[rows - 1].setValue(str);
        }

        if (!res.equals("")) pushOutput(res);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getFilledRows() {
        return filledRows;
    }

    public String[] getDisplay() {
        String[] res = new String[filledRows];
        for (int i = 0; i < res.length; i++) {
            res[i] = display[i].getValue();
        }
        return res;
    }

    public void clear() {
        filledRows = 0;
        for (Text text : display) text.setValue("");
    }
}
