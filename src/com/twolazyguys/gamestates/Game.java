package com.twolazyguys.gamestates;

import com.twolazyguys.Main;
import com.twolazyguys.entities.Colormap;
import com.twolazyguys.util.ColorSpritesheet;
import com.twolazyguys.util.Sprite;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.KeyboardInputEvent;
import net.colozz.engine2.events.Listener;
import net.colozz.engine2.gamestates.GameState;
import net.colozz.engine2.util.Color;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Game extends GameState implements Listener {

    private ColorSpritesheet sheet;
    private Colormap colormap;
    private Sprite[] sprites;
    private int current;
    private float count;

    private boolean[] keys = new boolean[65536];

    private final Color bright = new Color(92, 92, 48);
    private final Color dark = new Color(53, 53, 28);

    private final int X_PIXELS = 128, Y_PIXELS = 72;

    public Game() {
        glClearColor(bright.r, bright.g, bright.b, bright.a);

        sheet = new ColorSpritesheet(1, 2, "spritesheet");
        colormap = new Colormap(X_PIXELS, Y_PIXELS, bright);

        sprites = new Sprite[]{sheet.getSprite(0, 0), sheet.getSprite(0, 1)};
        colormap.addSprite(sprites[0]);

        entities.add(colormap);
        Main.addListener(colormap);
    }

    @EventHandler
    public void onKeyboardInputEvent(KeyboardInputEvent e) {
        keys[e.getKey()] = e.getAction() != GLFW_RELEASE;
    }

    public boolean isKeyDown(int keycode) {
        return keys[keycode];
    }

    @Override
    public void update() {
        GLFW.glfwSetWindowTitle(Main.window, getClass().getName() + " - " + Main.fps + "fps");

        count += Main.delta;
        if (count > 0.5f) {
            colormap.removeSprite(sprites[current]);
            colormap.addSprite(sprites[1 - current]);
            current = 1 - current;
            count = 0;
        }

        if (isKeyDown(GLFW_KEY_LEFT) || isKeyDown(GLFW_KEY_RIGHT))
            for (Sprite sprite : sprites) sprite.setX(sprite.getX() + (isKeyDown(GLFW_KEY_LEFT) ? -1 : 1));
    }

    public Color getColor(float intensity) {
        return new Color((bright.r - dark.r) * intensity + dark.r, (bright.g - dark.g) * intensity + dark.g, (bright.b - dark.b) * intensity + dark.b);
    }

}
