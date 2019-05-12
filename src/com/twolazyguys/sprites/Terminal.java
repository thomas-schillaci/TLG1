package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.AttackEvent;
import com.twolazyguys.gamestates.Game;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.KeyboardInputEvent;
import net.colozz.engine2.events.Listener;

import static org.lwjgl.glfw.GLFW.*;

public class Terminal extends Sprite implements Listener {

    private static String user = "user";
    private static String machine = "machine";
    private static String directory = "shome";

    private static Text prefix = new Text(0, 0, user + "a" + machine + "d" + directory + "s ");
    private static Text input = new Text(0, 0, "");

    public Terminal() {
        super(
                10,
                10,
                genColors()
        );
    }

    private static float[][] genColors() {
        float[][] res = new float[200][20];

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
        for (int i = 0; i < prefix.getColors().length; i++)
            for (int j = 0; j < prefix.getColors()[0].length; j++)
                res[2 + i][2 + j] = prefix.getColors()[i][j];

        for (int i = 0; i < input.getColors().length; i++)
            for (int j = 0; j < input.getColors()[0].length; j++)
                res[2 + i + prefix.getColors().length + 2][2 + j] = input.getColors()[i][j];

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
                if (input.getValue().equals("attack")) {
                    AttackEvent attack = new AttackEvent(0, 10);
                    Main.callEvent(attack);
                }
                input.setValue("");
            } else if (e.getKey() == GLFW_KEY_BACKSPACE) {
                if (!input.getValue().equals(""))
                    input.setValue(input.getValue().substring(0, input.getValue().length() - 1));
            } else input.setValue(input.getValue() + glfwGetKeyName(e.getKey(), e.getScancode()));
            setColors(genColors());
        }
    }

}
