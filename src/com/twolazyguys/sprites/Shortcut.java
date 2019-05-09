package com.twolazyguys.sprites;

import com.twolazyguys.util.ColorSpritesheet;

public class Shortcut extends Sprite {

    private Sprite[] sprites;

    private static ColorSpritesheet sheet = new ColorSpritesheet(1, 27, "abc");

    public Shortcut() {
        super(150, 100, sheet.getSprite(0, 0).getColors());

        sprites = new Sprite[]{sheet.getSprite(0, 0), sheet.getSprite(0,1)};
    }


}
