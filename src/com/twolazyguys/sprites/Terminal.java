package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.gamestates.Game;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.KeyboardInputEvent;
import net.colozz.engine2.events.Listener;

import static org.lwjgl.glfw.GLFW.*;

public class Terminal extends Sprite implements Listener {

    private Text input = new Text(10, 10, "");

    public Terminal() {
        super(
                10,
                10,
                genColors()
        );
    }

    private static float[][] genColors() {
        return genColors(null);
    }

    private static float[][] genColors(Text input) {
        float[][] res = new float[100][20];

        // outline
        for (int x = 0; x < res.length; x++) {
            res[x][0] = 0.2f;
            res[x][res[0].length - 1] = 0.2f;
        }
        for (int y = 0; y < res[0].length; y++) {
            res[0][y] = 0.2f;
            res[res.length - 1][y] = 0.2f;
        }

        // input
        if (input != null)
            for (int x = 0; x < input.getColors().length; x++)
                for (int y = 0; y < input.getColors()[0].length; y++)
                    res[2 + x][2 + y] = input.getColors()[x][y];

        return res;
    }

    @EventHandler
    public void onKeyboardInputEvent(KeyboardInputEvent e) {
        if (e.getAction() == GLFW_PRESS) {
            if (e.getKey() == GLFW_KEY_ENTER) {
                if (input.getValue().equals("dwarf")) {
                    Dwarf dwarf = new Dwarf();
                    ((Game) Main.getGameState()).getColormap().addSprite(dwarf);
                    Main.addListener(dwarf);
                }
                input.setValue("");
            } else input.setValue(input.getValue() + glfwGetKeyName(e.getKey(), e.getScancode()));
            setColors(genColors(input));
        }
    }

}
