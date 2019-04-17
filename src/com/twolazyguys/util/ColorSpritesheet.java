package com.twolazyguys.util;

import com.twolazyguys.Main;
import com.twolazyguys.sprites.Sprite;
import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ColorSpritesheet {

    private int rows, columns;
    private float[][] colors;

    public ColorSpritesheet(int rows, int columns, String texture) {
        this(rows, columns, transformTexture(texture));
    }

    private ColorSpritesheet(int rows, int columns, float[][] colors) {
        this.rows = rows;
        this.columns = columns;
        this.colors = colors;
    }

    public Sprite getSprite(int row, int column) {
        int dx = colors.length / columns, dy = colors[0].length / rows;

        float[][] sprite = new float[dx][dy];
        for (int i = 0; i < sprite.length; i++)
            for (int j = 0; j < sprite[0].length; j++) sprite[i][j] = colors[column * dx + i][row * dy + j];

        return new Sprite(sprite);
    }

    private static float[][] transformTexture(String texture) {
        float[][] color = null;
        try {
            InputStream in = new FileInputStream(Main.PATH + "tex/" + texture + ".png");
            PNGDecoder decoder = new PNGDecoder(in);

            int width = decoder.getWidth(), height = decoder.getHeight();

            ByteBuffer buffer = ByteBuffer.allocateDirect(width * height);
            decoder.decode(buffer, width, PNGDecoder.Format.LUMINANCE);
            buffer.flip();

            color = new float[width][height];
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++) color[j][height - i - 1] = (float) -buffer.get();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return color;
    }

    public int getSizeX() {
        return colors.length;
    }

    public int getSizeY() {
        return colors[0].length;
    }

}
