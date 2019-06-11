package com.twolazyguys.sprites;

import com.twolazyguys.events.CommandEvent;
import com.twolazyguys.util.ColorSpritesheet;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.KeyboardInputEvent;
import net.colozz.engine2.events.Listener;
import sun.security.util.Length;

import java.lang.reflect.WildcardType;

public class Shortcut extends Sprite implements Listener {

    private Sprite[] sprites;
    private static int LENGTH = 90, WIDTH = 60;
    private static int NUMBER_OF_ROWS = 4;
    private static Text[] display = new Text[NUMBER_OF_ROWS];

    private final static int TEXT_OFFSET = 2;
    private final static int LINE_HEIGHT = Text.getLetterSizeY() + TEXT_OFFSET;


    static {
        display[0] = new Text(TEXT_OFFSET, 1 * LINE_HEIGHT + TEXT_OFFSET, "4. ");
        display[1] = new Text(TEXT_OFFSET, 2 * LINE_HEIGHT + TEXT_OFFSET, "3. ");
        display[2] = new Text(TEXT_OFFSET, 3 * LINE_HEIGHT + TEXT_OFFSET, "2. ");
        display[3] = new Text(TEXT_OFFSET, 4 * LINE_HEIGHT + TEXT_OFFSET, "1. ");
    }


    public Shortcut() {
        super(225, 100, genColors());
    }

    private static float[][] genColors() {
        float[][] res = new float[LENGTH][WIDTH];

        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (i == 0 || i == LENGTH - 1 || j == 0 || j == WIDTH - 1)
                    res[i][j] = 0.075f;
            }
        }

        for (Text text : display) {
            for (int x = 0; x < text.getColors().length; x++)
                for (int y = 0; y < text.getColors()[0].length; y++)
                    res[x + text.getX()][y + text.getY()] = text.getColors()[x][y];
        }

        return res;
    }

    private void setShortcut(int sc, String name) {
        if (sc >= 1 && sc <= 4) display[sc].setValue(sc + name);
    }

    @EventHandler(EventHandler.Priority.HIGHEST)
    public void onCommandEvent(CommandEvent event) {
        if (!event.isCanceled()) {
            event.setCanceled(true);

            String formatted = event.getCommand().toLowerCase();

            if (formatted.equals("shortcut")) {
                if (event.getArgs().length == 0) event.setOutput("Which shortcut?");
                else {
                    String sc = event.getArgs()[0];
                    String name = event.getArgs()[1];
                    switch (sc) {
                        case "1": setShortcut(1, name); break;
                        case "2": setShortcut(2, name); break;
                        case "3": setShortcut(3, name); break;
                        case "4": setShortcut(4, name); break;
                        default: event.setOutput("Only 4 shortcuts allowed"); break;
                    }
                }
            }
        }
    }

}
