package com.twolazyguys.sprites;

import com.twolazyguys.util.ColorSpritesheet;
import sun.security.util.Length;

import java.lang.reflect.WildcardType;

public class Shortcut extends Sprite {

    private Sprite[] sprites;
    private static int LENGTH = 90, WIDTH = 65;
    private static int NUMBER_OF_ROWS = 4;
    private static Text[] display = new Text[NUMBER_OF_ROWS];

    private final static int TEXT_OFFSET = 2;
    private final static int LINE_HEIGHT = Text.getLetterSizeY() + TEXT_OFFSET;

    //private static ColorSpritesheet sheet = new ColorSpritesheet(1, 27, "abc");


    static {
        display[0] = new Text(TEXT_OFFSET, 1 * LINE_HEIGHT + TEXT_OFFSET, "4. Attack 4");
        display[1] = new Text(TEXT_OFFSET, 2 * LINE_HEIGHT + TEXT_OFFSET, "3. Attack 3");
        display[2] = new Text(TEXT_OFFSET, 3 * LINE_HEIGHT + TEXT_OFFSET, "2. Attack 2");
        display[3] = new Text(TEXT_OFFSET, 4 * LINE_HEIGHT + TEXT_OFFSET, "1. Attack 1");
    }


    public Shortcut() {
        super(225, 90, genColors());
        //sprites = new Sprite[]{sheet.getSprite(0, 0), sheet.getSprite(0,1)};

    }

    private static float[][] genColors() {
        float[][] res = new float[LENGTH][WIDTH];

        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (i == 0 || i == LENGTH - 1 || j == 0 || j == WIDTH - 1)
                    res[i][j] = 0.1f;
            }
        }


        for (Text text : display) {
            for (int x = 0; x < text.getColors().length; x++)
                for (int y = 0; y < text.getColors()[0].length; y++)
                    res[x + text.getX()][y + text.getY()] = text.getColors()[x][y];
        }









        return res;
    }


}
