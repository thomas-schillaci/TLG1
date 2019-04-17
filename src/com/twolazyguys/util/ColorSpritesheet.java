package com.twolazyguys.util;

import com.twolazyguys.Main;
import de.matthiasmann.twl.utils.PNGDecoder;
import net.colozz.engine2.util.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ColorSpritesheet {

    private int rows, columns;
    private Color[][] colors;

    public ColorSpritesheet(int rows, int columns, String texture) {
        this(rows, columns, transformTexture(texture));
    }

    private ColorSpritesheet(int rows, int columns, Color[][] colors) {
        this.rows = rows;
        this.columns = columns;
        this.colors = colors;
    }

    public Sprite getSprite(int row, int column) {
        int dx = colors.length / columns, dy = colors[0].length / rows;

        Color[][] sprite = new Color[dx][dy];
        for (int i = 0; i < sprite.length; i++)
            for (int j = 0; j < sprite[0].length; j++) sprite[i][j] = colors[column * dx + i][row * dy + j];

        return new Sprite(sprite);
    }

    private static Color[][] transformTexture(String texture) {
        Color[][] color = null;
        try {
            InputStream in = new FileInputStream(Main.PATH + "tex/" + texture + ".png");
            PNGDecoder decoder = new PNGDecoder(in);

            int width = decoder.getWidth(), height = decoder.getHeight();

            ByteBuffer buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
            buffer.flip();

            color = new Color[width][height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    float r = (float) buffer.get() / 255;
                    float g = (float) buffer.get() / 255;
                    float b = (float) buffer.get() / 255;
                    float a = (float) -buffer.get();
                    color[j][height - i - 1] = new Color(r, g, b, a);
                }
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return color;
    }

}
