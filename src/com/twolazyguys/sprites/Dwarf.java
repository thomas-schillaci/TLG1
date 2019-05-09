package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.util.ColorSpritesheet;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public class Dwarf extends Sprite implements Listener {

    private Sprite[] sprites;
    private int current;
    private float count;

    private static ColorSpritesheet sheet = new ColorSpritesheet(3, 2, "dwarfsheet");

    public Dwarf() {
        super(50, 100, sheet.getSprite(0, 0).getColors());

        sprites = new Sprite[]{sheet.getSprite(0, 0), sheet.getSprite(0, 1)};
    }

    @EventHandler
    public void onGameTickEvent(GameTickEvent event) {
        count += Main.delta;
        if (count > 0.5f) {
            current = 1 - current;
            setColors(sprites[current].getColors());
            count = 0;
        }
    }

}
