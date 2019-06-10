package com.twolazyguys.sprites;

import com.twolazyguys.util.ColorSpritesheet;

public class Text extends Sprite {

    private String value;

    public final static String TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!?#.,()~@:'\"-_/$ ";

    private static ColorSpritesheet sheet = new ColorSpritesheet(1, TABLE.length(), "abc");
    private static int letterSizeX = sheet.getSizeX() / TABLE.length();
    private static int letterSizeY = sheet.getSizeY();

    public Text(int x, int y, String value) {
        super(x, y, genColors(value));
        this.value = value;
    }

    private static float[][] genColors(String value) {
        if (value.equals("")) return new float[0][0];

        float[][] colors = new float[letterSizeX * value.length()][letterSizeY];

        String[] letters = value.split("");
        for (int k = 0; k < letters.length; k++) {
            float[][] letter = sheet.getSprite(0, TABLE.indexOf(letters[k])).getColors();
            for (int i = 0; i < letterSizeX; i++)
                for (int j = 0; j < letterSizeY; j++) colors[k * letterSizeX + i][j] = letter[i][j];
        }

        return colors;
    }

    public void setValue(String value) {
        setColors(genColors(value));
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getSizeX() {
        return getLetterSizeX() * getValue().length();
    }

    public int getSizeY() {
        return getLetterSizeY();
    }

    public static int getLetterSizeX() {
        return letterSizeX;
    }

    public static int getLetterSizeY() {
        return letterSizeY;
    }

}
