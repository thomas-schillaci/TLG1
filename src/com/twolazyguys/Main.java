package com.twolazyguys;

import com.twolazyguys.gamestates.Game;
import net.colozz.engine2.MainClass;

import static org.lwjgl.opengl.GL11.*;

public class Main extends MainClass {

    public static void main(String[] args) {
        new Main();
    }

    @Override
    protected void init() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        getTextureManager().loadAllTextures();
        Game game = new Game();
        setGameState(game);
        addListener(game);
    }

}
