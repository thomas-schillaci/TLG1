package com.twolazyguys.util;

import com.twolazyguys.Main;
import com.twolazyguys.events.SpriteChangedEvent;
import net.colozz.engine2.util.Color;

public class Sprite {

    private int x, y;
    private Color[][] colors;

    public Sprite(Color[][] colors) {
        this(0, 0, colors);
    }

    public Sprite(int x, int y, Color[][] colors) {
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

    public Color[][] getColors() {
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

    public void setColors(Color[][] colors) {
        this.colors = colors;
        Main.callEvent(new SpriteChangedEvent(this));
    }
}
