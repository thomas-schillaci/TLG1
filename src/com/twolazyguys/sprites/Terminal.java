package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.AttackEvent;
import com.twolazyguys.events.CommandEvent;
import com.twolazyguys.gamestates.Game;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.KeyboardInputEvent;
import net.colozz.engine2.events.Listener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Terminal extends Sprite implements Listener {

    private static String user = "user";
    private static String machine = "machine";
    private static String directory = "/home";

    private static Text prefix = new Text(0, 0, user + "@" + machine + ":" + directory + "$ ");
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
                String[] split = input.getValue().split(" ");
                String[] args = new String[split.length-1];
                if(split.length>1) args = Arrays.copyOfRange(split, 1, args.length);
                Main.callEvent(new CommandEvent(split[0], args));
                input.setValue("");
            } else if (e.getKey() == GLFW_KEY_BACKSPACE) {
                if (!input.getValue().equals(""))
                    input.setValue(input.getValue().substring(0, input.getValue().length() - 1));
            } else {
                String key = glfwGetKeyName(e.getKey(), e.getScancode());
                if(e.getKey() == GLFW_KEY_SPACE) key = " ";
                if(key != null) {
                    if(Game.isKeyDown(GLFW_KEY_LEFT_SHIFT) || Game.isKeyDown(GLFW_KEY_RIGHT_SHIFT) || Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) key = key.toUpperCase();
                    if (Text.TABLE.contains(key)) input.setValue(input.getValue() + key);
                }
            }
            setColors(genColors());
        }
    }

    @EventHandler(EventHandler.Priority.HIGHEST)
    public void onCommandEvent(CommandEvent e) {
        if(!e.isCanceled()) {
            e.setCanceled(true);

            String formatted = e.getCommand().toLowerCase();

            if(formatted.equals("help")) System.out.println("Help coming soon!");
            else if(formatted.equals("dwarf")) {
                Dwarf dwarf = new Dwarf();
                ((Game) Main.getGameState()).getColormap().addSprite(dwarf);
                Main.addListener(dwarf);
            }
            else if(formatted.equals("attack")) {
                AttackEvent attack = new AttackEvent(0, 30);
                Main.callEvent(attack);
            }
            else System.out.println(e.getCommand() + ": command not found.");
        }
    }

}
