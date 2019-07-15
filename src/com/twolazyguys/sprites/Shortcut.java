package com.twolazyguys.sprites;

import com.twolazyguys.events.CommandEvent;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.util.ColorSpritesheet;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.KeyboardInputEvent;
import net.colozz.engine2.events.MouseInputEvent;
import net.colozz.engine2.events.Listener;
import sun.security.util.Length;

import java.lang.reflect.WildcardType;

public class Shortcut extends Sprite implements Listener {

    // private ColorSpritesheet sheet = new ColorSpritesheet(155, 178, "shortcuts");
    private static int LENGTH = 104, WIDTH = 69;
    private static int NUMBER_OF_ROWS = 4;
    private static Text[] display = new Text[NUMBER_OF_ROWS];
    private static Text[] display_2 = new Text[1];
    private static Text[] display_3 = new Text[1];
    private static Text[] display_4 = new Text[1];
    private static Text[][] displays = {display_2, display_3, display_4};

    private final static int TEXT_OFFSET = 2;
    private final static int LINE_HEIGHT = Text.getLetterSizeY() + TEXT_OFFSET;


    static {
        for (int i = 0; i < 4; i++) display[i] = new Text(TEXT_OFFSET, (i + 1) * LINE_HEIGHT + TEXT_OFFSET, String.format("%d. ", 4 - i));
    }


    public Shortcut() {
        super(155, 178, genColors());
    } // x = 405, y = 178

    private static float[][] genColors() {
        float[][] res = new float[LENGTH][WIDTH];

        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (i == 0 || i == LENGTH - 1 || j == 0 || j == WIDTH - 1)
                    res[i][j] = 0.5f;
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
        if (sc >= 0 && sc <= 3) display[sc].setValue((4 - sc) + "." + name);
        setColors(genColors());
    }

    private void resetShortcut(int sc) {
        if (sc >= 0 && sc <= 3) display[sc].setValue((4 - sc) + ".");
        setColors(genColors());
    }

    @EventHandler(EventHandler.Priority.LOWEST)
    public void onCommandEvent(CommandEvent event) {
        String formatted = event.getCommand().toLowerCase();

        if (formatted.equals("bind")) {
            if (!event.isCanceled()) {
                event.setCanceled(true);

                if (event.getArgs().length == 0) event.setOutput("Which shortcut?");
                else {
                    String sc = event.getArgs()[0];
                    String name = event.getArgs()[1];

                    if (name.equals("battalion")) {

                        switch (sc) {
                            case "1": setShortcut(3, name); break;
                            case "2": setShortcut(2, name); break;
                            case "3": setShortcut(1, name); break;
                            case "4": setShortcut(0, name); break;
                            default: event.setOutput("Only 4 shortcuts allowed"); break;
                        }
                    }
                    else {
                        event.setOutput(name + " is not an attack");
                    }

                }
            }
        }

        else if (formatted.equals("unbind")) {
            if (!event.isCanceled()) {
                event.setCanceled(true);

                if (event.getArgs().length == 0) event.setOutput("Which shortcut?");
                else {

                    String sc = event.getArgs()[0];

                    switch (sc) {
                        case "1": resetShortcut(3); break;
                        case "2": resetShortcut(2); break;
                        case "3": resetShortcut(1); break;
                        case "4": resetShortcut(0); break;
                        default: event.setOutput("There are only 4 shortcuts..."); break;
                    }
                }
            }
        }
    }

    /*
    @EventHandler
    public void onGameTicketEvent(GameTickEvent event) {

        int x = event.getX();
        int y = event.getY();

        int sx = sheet.getSprite().getX();
        int sy = sheet.getSprite().getY();

        int dx = sheet.getSizeX();
        int dy = sheet.getSizeY();
    }

     */

}
