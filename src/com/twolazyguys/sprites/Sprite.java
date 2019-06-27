package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.SpriteChangedEvent;

public class Sprite {

    private int x, y;
    private float[][] colors;

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
        return colors[0].length;
    }

    public float[][] getColors() {
        return colors;
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
        for (int x = 0; x < other.getColors().length; x++) {
            for (int y = 0; y < other.getColors()[0].length; y++) {
                this.colors[other.getX() + x][other.getY() + y] = other.getColors()[x][y];
            }
        }
        Main.callEvent(new SpriteChangedEvent(this));
    }

}
