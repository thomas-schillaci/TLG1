package com.twolazyguys.events;

import com.twolazyguys.sprites.Sprite;
import net.colozz.engine2.events.Event;

public class SpriteChangedEvent extends Event {

    private Sprite sprite;

    public SpriteChangedEvent(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

}
