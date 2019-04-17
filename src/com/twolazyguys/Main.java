package com.twolazyguys;

import com.twolazyguys.gamestates.Game;
import net.colozz.engine2.MainClass;

public class Main extends MainClass {

    public static void main(String[] args) {
        new Main();
    }

    @Override
    protected void init() {
        Game game = new Game();
        setGameState(game);
        addListener(game);
    }

}
