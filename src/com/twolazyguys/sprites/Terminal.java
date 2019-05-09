package com.twolazyguys.sprites;

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
        float[][] res = new float[100][10];

        // outline
        for (int x = 0; x < res.length; x++) {
            res[x][0] = 0.1f;
            res[x][res[0].length - 1] = 0.2f;
        }
        for (int y = 0; y < res[0].length; y++) {
            res[0][y] = 0.1f;
            res[res.length - 1][y] = 0.2f;
        }

        return res;
    }

    @EventHandler
    public void onKeyboardInputEvent(KeyboardInputEvent e) {
        if (e.getAction() == GLFW_PRESS) {
            if (e.getKey() == GLFW_KEY_ENTER) {
//                if (input.getValue().equals("dwarf")) colormap.addSprite(new Dwarf());
                input.setValue("s ");
            } else input.setValue(input.getValue() + glfwGetKeyName(e.getKey(), e.getScancode()));
        }
    }

}
