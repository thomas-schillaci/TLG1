package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.util.ColorSpritesheet;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public class Bell extends Sprite implements Listener {

    private static ColorSpritesheet sheet = new ColorSpritesheet(1, 3, "bell");
    private boolean ringing = false;
    private boolean changed = false;
    private float count = 0;
    private int sprite = 1;

    public Bell(int x, int y) {
        super(x, y, sheet.getSprite(0, 1).getColors());
    }

    @Override
    public float[][] getColors() {
        changed = false;
        return super.getColors();
    }

    @EventHandler
    public void onGameTickEvent(GameTickEvent e) {
        if (ringing) {
            count += Main.delta;
            if (count > 0.5) {
                changed = true;
                sprite += 1;
                sprite %= 3;
                setColors(sheet.getSprite(0, sprite).getColors());
                count = 0;
            }
        }
    }

    public boolean isRinging() {
        return ringing;
    }

    public boolean hasChanged() {
        return changed;
    }

    public void setRinging(boolean ringing) {
        this.ringing = ringing;
        if (!ringing) {
            changed = true;
            setColors(sheet.getSprite(0, 1).getColors());
        }
    }

}
