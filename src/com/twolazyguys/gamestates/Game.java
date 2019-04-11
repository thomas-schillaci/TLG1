package com.twolazyguys.gamestates;

import net.colozz.engine2.entities.Sprite;
import net.colozz.engine2.gamestates.GameState;

public class Game extends GameState {

    public Game() {
        entities.add(new Sprite(0, 0, 3, 4));
    }

    @Override
    public void update() {

    }

}
