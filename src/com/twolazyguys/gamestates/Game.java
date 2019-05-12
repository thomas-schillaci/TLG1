package com.twolazyguys.gamestates;

import com.twolazyguys.Main;
import com.twolazyguys.entities.Colormap;
import com.twolazyguys.events.AttackEvent;
import com.twolazyguys.sprites.Dwarf;
import com.twolazyguys.sprites.LoadingBar;
import com.twolazyguys.sprites.Text;

import net.colozz.engine2.events.Event;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.KeyboardInputEvent;
import net.colozz.engine2.events.Listener;
import net.colozz.engine2.gamestates.GameState;
import net.colozz.engine2.util.Color;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Game extends GameState implements Listener {

    private Text input = new Text(10, 10, "s ");

    private boolean[] keys = new boolean[65536];

    public final static Color BRIGHT = new Color(92, 92, 48);
    public final static Color DARK = new Color(53, 53, 28);

    private final int X_PIXELS = 256, Y_PIXELS = 144;

    private Colormap colormap;

    public Game() {
        glClearColor(BRIGHT.r, BRIGHT.g, BRIGHT.b, BRIGHT.a);

        colormap = new Colormap(X_PIXELS, Y_PIXELS) {{
            addSprite(input);
        }};
//      modiff
        LoadingBar lb = new LoadingBar();
        colormap.addSprite(lb);
        Main.addListener(lb);
//
        entities.add(colormap);
        Main.addListener(colormap);
    }

    @EventHandler
    public void onKeyboardInputEvent(KeyboardInputEvent e) {
        keys[e.getKey()] = e.getAction() != GLFW_RELEASE;

        if (e.getAction() == GLFW_PRESS) {
//        	modiff
        	Main.callEvent(new AttackEvent(10,10));
//        	
            if (e.getKey() == GLFW_KEY_ENTER) {
                if (input.getValue().equals("s dwarf")) colormap.addSprite(new Dwarf());
                input.setValue("s ");
            } else input.setValue(input.getValue() + glfwGetKeyName(e.getKey(), e.getScancode()));
        }
    }

    public boolean isKeyDown(int keycode) {
        return keys[keycode];
    }

    @Override
    public void update() {
        GLFW.glfwSetWindowTitle(Main.window, getClass().getName() + " - " + Main.fps + "fps");
    }

}
