package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.attacks.Dwarftack;
import com.twolazyguys.events.CommandEvent;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.gamestates.Game;
import com.twolazyguys.util.ColorSpritesheet;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.KeyboardInputEvent;
import net.colozz.engine2.events.MouseInputEvent;
import net.colozz.engine2.events.Listener;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.CallbackI;
import sun.security.util.Length;

import java.lang.reflect.WildcardType;

public class Shortcut extends Sprite implements Listener {

    private static int LENGTH = 104, WIDTH = 69;
    private static int NUMBER_OF_ROWS = 4;
    private static Text[] display = new Text[NUMBER_OF_ROWS];
    private final static int TEXT_OFFSET = 2;
    private final static int LINE_HEIGHT = Text.getLetterSizeY() + TEXT_OFFSET;


    static {
        for (int i = 0; i < 4; i++)
            display[i] = new Text(TEXT_OFFSET, (i + 1) * LINE_HEIGHT + TEXT_OFFSET, String.format("%d. ", 4 - i));
    }


    public Shortcut() {
        super(405, 178, genColors());
    }

    private static float[][] genColors() {
        float[][] res = new float[LENGTH][WIDTH];
        for (Text text : display) {
            storeColors(text, res);
        }
        return res;
    }

    private void setShortcut(int sc, String name) {
        if (sc >= 0 && sc <= 3) display[sc].setValue((4 - sc) + ". " + name);
        setColors(genColors());
    }

    private void resetShortcut(int sc) {
        if (sc >= 0 && sc <= 3) display[sc].setValue((4 - sc) + ". ");
        setColors(genColors());
    }

    private boolean isBinded(int i) {
        boolean binded = false;
        if (display[i].getValue().length() != 3) binded = true;
        return binded;
    }

    private int shortcutsAvailable() {
        int nbShorcuts = 4;
        for (int i = 0; i < 4; i++) {
            if (display[i].getValue().length() != 3) {
                nbShorcuts--;
            }
        }
        return nbShorcuts;
    }

    @EventHandler(EventHandler.Priority.LOWEST)
    public void onCommandEvent(CommandEvent event) {
        String formatted = event.getCommand().toLowerCase();

        if (formatted.equals("bind")) {
            if (!event.isCanceled()) {
                event.setCanceled(true);

                if (event.getArgs().length == 0) event.setOutput("Which shortcut?");
                else if (event.getArgs().length == 1) event.setOutput("No shortcut to bind :c");
                else {
                    String sc = event.getArgs()[0];
                    String name = event.getArgs()[1];
                    switch (sc) {
                            case "1":
                                setShortcut(3, name);
                                break;
                            case "2":
                                setShortcut(2, name);
                                break;
                            case "3":
                                setShortcut(1, name);
                                break;
                            case "4":
                                setShortcut(0, name);
                                break;
                            default:
                                event.setOutput("Only 4 shortcuts allowed :c");
                                break;
                    }
                }
            }
        } else if (formatted.equals("unbind")) {
            if (!event.isCanceled()) {
                event.setCanceled(true);

                if (event.getArgs().length == 0) event.setOutput("Which shortcut?");
                else {

                    String sc = event.getArgs()[0];

                    switch (sc) {
                        case "1":
                            resetShortcut(3);
                            break;
                        case "2":
                            resetShortcut(2);
                            break;
                        case "3":
                            resetShortcut(1);
                            break;
                        case "4":
                            resetShortcut(0);
                            break;
                        case "all":
                            for (int i = 0; i < 4; i++) resetShortcut(i);
                        default:
                            event.setOutput("There are only 4 shortcuts available :c");
                            break;
                    }
                }
            }
        }

        switch (shortcutsAvailable()) {
            case 0:
                event.setOutput("No more shortcuts avilable");
                break;
            case 1:
                event.setOutput(String.format("%d", shortcutsAvailable()) + " more shortcut available");
                break;
            default:
                event.setOutput(String.format("%d", shortcutsAvailable()) + " more shortcuts available");
                break;
        }
    }

    @EventHandler
    public void onMouseInputEvent(MouseInputEvent event) {

        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT && event.getAction() == GLFW.GLFW_RELEASE) {
            double[] xt = new double[1];
            double[] yt = new double[1];
            GLFW.glfwGetCursorPos(event.getWindow(), xt, yt);
            double x = xt[0] * Game.X_PIXELS / Main.width;
            double y = Game.Y_PIXELS - yt[0] * Game.Y_PIXELS / Main.height;

            float[] sx = {display[3].getX() + getX(), display[2].getX() + getX(), display[1].getX() + getX(), display[0].getX() + getX()};
            float[] sy = {display[3].getY() + getY(), display[2].getY() + getY(), display[1].getY() + getY(), display[0].getY() + getY()};
            float[] dx = {display[3].getSizeX(), display[2].getSizeX(), display[1].getSizeX(), display[0].getSizeX()};
            float[] dy = {display[3].getSizeY(), display[2].getSizeY(), display[1].getSizeY(), display[0].getSizeY()};


            for (int i = 0; i < 4; i++) {
                if (x >= sx[i] && x <= sx[i] + dx[i] && y >= sy[i] && y <= sy[i] + dy[i]) {
                    if (isBinded(4 - i - 1)) {
                        String shortcut = display[4 -i -1].getValue().substring(3).toLowerCase();
                        Main.callEvent(new CommandEvent(shortcut));
                    }
                }
            }
        }
    }

}
