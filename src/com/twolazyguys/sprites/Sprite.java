package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.SpriteChangedEvent;

import java.util.Arrays;

public class Sprite {

    private int x, y;
    private float[][] colors;

    public Sprite() {
        this(0, 0);
    }

    public Sprite(int x, int y) {
        this(x, y, new float[0][0]);
    }

    public Sprite(float[][] colors) {
        this(0, 0, colors);
    }

    public Sprite(int x, int y, float[][] colors) {
        this.x = x;
        this.y = y;
        this.colors = colors;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSizeX() {
        return colors.length;
    }

    public int getSizeY() {
        if(colors.length==0) return 0;
        return colors[0].length;
    }

    public float[][] getColors() {
        float[][] res = new float[colors.length][];
        for (int x = 0; x < res.length; x++) res[x] = Arrays.copyOf(colors[x], colors[x].length);
        return res;
    }

    public void setX(int x) {
        this.x = x;
        Main.callEvent(new SpriteChangedEvent(this));
    }

    public void setY(int y) {
        this.y = y;
        Main.callEvent(new SpriteChangedEvent(this));
    }

    public void setColors(float[][] colors) {
        this.colors = colors;
        Main.callEvent(new SpriteChangedEvent(this));
    }

    public void storeColors(Sprite other) {
        storeColors(other, this.colors);
        Main.callEvent(new SpriteChangedEvent(this));
    }

    public static void storeColors(Sprite other, float[][] res) {
        float[][] colors = other.getColors();
        for (int x = 0; x < colors.length; x++) {
            for (int y = 0; y < colors[0].length; y++) {
                res[other.getX() + x][other.getY() + y] = colors[x][y];
            }
        }
    }

}
