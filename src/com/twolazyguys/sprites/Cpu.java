package com.twolazyguys.sprites;

public class Cpu extends Sprite {

    private Text text = new Text(0, 27, "CPU");
    private float cpuUsage;

    public final static int SIZE_X = 119;
    public final static int SIZE_Y = 25 + Text.getLetterSizeY() + 2;

    public Cpu(int x, int y) {
        super(x, y);
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

        // CONTENT

        int filled = (int) (cpuUsage * (SIZE_X - 1));
        for (int i = 1; i < filled; i++) {
            for (int j = 1; j < SIZE_Y - Text.getLetterSizeY() - 2 - 1; j++) {
                res[i][j] = 0.8f;
            }
        }

        return res;
    }

    @Override
    public float[][] getColors() {
        return genColors();
    }

    public void update(float cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

}
