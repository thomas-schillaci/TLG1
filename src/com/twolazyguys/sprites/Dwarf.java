package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.util.ColorSpritesheet;

public class Dwarf extends Sprite {

    private Sprite[] sprites;
    private int current;
    private float count;

    private static ColorSpritesheet sheet = new ColorSpritesheet(1, 2, "spritesheet");

    public Dwarf() {
        super(50, 100, sheet.getSprite(0, 0).getColors());

        sprites = new Sprite[]{sheet.getSprite(0, 0), sheet.getSprite(0, 1)};
    }

    @Override
    public void update() {
        count += Main.delta;
        if (count > 0.5f) {
            current = 1 - current;
            setColors(sprites[current].getColors());
            count = 0;
        }
    }

}
