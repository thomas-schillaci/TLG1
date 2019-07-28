package com.twolazyguys.sprites;

import java.util.ArrayList;

public class Bandwidth extends Sprite {

    private Text text = new Text(0, 27, "Bandwidth");
    public final static int SIZE_X = 119;
    public final static int SIZE_Y = 25 + Text.getLetterSizeY() + 2;

    private ArrayList<Float> history = new ArrayList<>();

    public Bandwidth(int x, int y) {
        super(x, y);
        for (int i = 0; i < SIZE_X - 2; i++) {
            history.add(0f);
        }
    }

    private float[][] genColors() {
        float[][] res = new float[SIZE_X][SIZE_Y];

        storeColors(text, res);

        // OUTLINE
        for (int i = 0; i < SIZE_X; i++) {
            res[i][0] = 0.3f;
            res[i][SIZE_Y - Text.getLetterSizeY() - 2 - 1] = 0.3f;
        }
        for (int j = 0; j < SIZE_Y - Text.getLetterSizeY() - 2; j++) {
            res[0][j] = 0.3f;
            res[SIZE_X - 1][j] = 0.3f;
        }

        for (int i = 1; i < SIZE_X - 1; i++) {
            for (int j = 1; j < history.get(i-1) * (SIZE_Y - Text.getLetterSizeY() - 2) - 1; j++) {
                res[i][j] = 0.8f;
            }

        }

        return res;
    }

    @Override
    public float[][] getColors() {
        return genColors();
    }

    public void update(float bandwidthUsage) {
        history.add(bandwidthUsage);
        history.remove(0);
    }

}
