package com.twolazyguys.sprites;

import com.twolazyguys.util.ColorSpritesheet;

public class Bell extends Sprite {

    private static ColorSpritesheet sheet = new ColorSpritesheet(1, 1, "bell");

    public Bell(int x, int y) {
        super(x, y, sheet.getSprite(0, 0).getColors());
    }

}
