package com.twolazyguys.gamestates;

import com.twolazyguys.Main;
import com.twolazyguys.entities.Colormap;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.sprites.Battalion;
import com.twolazyguys.sprites.LoadingBar;
import com.twolazyguys.sprites.Monitor;
import com.twolazyguys.sprites.Shortcut;
import com.twolazyguys.sprites.Terminal;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.KeyboardInputEvent;
import net.colozz.engine2.events.Listener;
import net.colozz.engine2.gamestates.GameState;
import net.colozz.engine2.util.Color;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Game extends GameState implements Listener {

    private static boolean[] keys = new boolean[65536];

    public final static Color BRIGHT = new Color(92, 92, 48);
    public final static Color DARK = new Color(53, 53, 28);

    private final int X_PIXELS = 512, Y_PIXELS = 288;

    private Colormap colormap;

    public Game() {
        glClearColor(BRIGHT.r, BRIGHT.g, BRIGHT.b, BRIGHT.a);

        Terminal terminal = new Terminal();
        LoadingBar lb = new LoadingBar();
        Shortcut shortcut = new Shortcut();
        Monitor monitor = new Monitor();
        Battalion battalion = new Battalion();
        colormap = new Colormap(X_PIXELS, Y_PIXELS);

        Main.addListener(terminal);
        Main.addListener(lb);
        Main.addListener(shortcut);
        Main.addListener(battalion);
        Main.addListener(colormap);
        Main.addListener(monitor);

        entities.add(colormap);

        colormap.addSprite(terminal);
        colormap.addSprite(lb);
        colormap.addSprite(shortcut);
        colormap.addSprite(monitor);
    }

    @EventHandler
    public void onKeyboardInputEvent(KeyboardInputEvent e) {
        keys[e.getKey()] = e.getAction() != GLFW_RELEASE;

        if (e.getAction() == GLFW_PRESS) {
        }
    }

    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }

    @Override
    public void update() {
        GLFW.glfwSetWindowTitle(Main.window, getClass().getName() + " - " + Main.fps + "fps");
        Main.callEvent(new GameTickEvent());
    }

    public Colormap getColormap() {
        return colormap;
    }
}