package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.SpriteChangedEvent;

public class Sprite {

    private int x, y;
    private float[][] colors;

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

}
