package com.twolazyguys.sprites;

import com.twolazyguys.util.ColorSpritesheet;

public class Text extends Sprite {

    private String value;

    private final static String TABLE = "abcdefghijklmnopqrstuvwxyz ";

    private static ColorSpritesheet sheet = new ColorSpritesheet(1, TABLE.length(), "abc");
    private static int dx = sheet.getSizeX() / TABLE.length();
    private static int dy = sheet.getSizeY();

    public Text(int x, int y, String value) {
        super(x, y, getColors(value));
        this.value = value;
    }

    private static float[][] getColors(String value) {
        float[][] colors = new float[dx * value.length()][dy];

        String[] letters = value.split("");
        for (int k = 0; k < letters.length; k++) {
            float[][] letter = sheet.getSprite(0, TABLE.indexOf(letters[k])).getColors();
            for (int i = 0; i < dx; i++) for (int j = 0; j < dy; j++) colors[k * dx + i][j] = letter[i][j];
        }

        return colors;
    }

    public void setValue(String value) {
        setColors(getColors(value));
        this.value=value;
    }

    public String getValue() {
        return value;
    }

}
