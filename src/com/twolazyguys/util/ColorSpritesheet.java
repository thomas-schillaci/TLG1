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

        float[][] colors = new float[dx][dy];
        for (int i = 0; i < colors.length; i++)
            for (int j = 0; j < colors[0].length; j++) colors[i][j] = this.colors[column * dx + i][row * dy + j];

        return new Sprite(colors);
    }

    private static float[][] transformTexture(String texture) {
        float[][] colors = null;
        try {
            InputStream in = new FileInputStream(Main.PATH + "tex/" + texture + ".png");
            PNGDecoder decoder = new PNGDecoder(in);

            int width = decoder.getWidth(), height = decoder.getHeight();

            ByteBuffer buffer = ByteBuffer.allocateDirect(width * height);
            decoder.decode(buffer, width, PNGDecoder.Format.LUMINANCE);
            buffer.flip();

            boolean binary = true;
            colors = new float[width][height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    float b = buffer.get();
                    colors[j][height - i - 1] = b;
                    if ((int) Math.abs(b) != 0 && (int) Math.abs(b) != 1) binary = false;
                }
            }

            // TODO FIXME
            for (int x = 0; x < colors.length; x++) {
                for (int y = 0; y < colors[0].length; y++) {
                    if (binary) colors[x][y] += 1;
                    else colors[x][y] = 1 + colors[x][y] / 50;
                }
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return colors;
    }

    public int getSizeX() {
        return colors.length;
    }

    public int getSizeY() {
        return colors[0].length;
    }

}
