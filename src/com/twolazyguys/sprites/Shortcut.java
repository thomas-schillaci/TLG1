package com.twolazyguys.sprites;

import com.twolazyguys.Main;
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
        super(155, 178, genColors());
    } // x = 405, y = 178

    // Il y a une nouvelle manière de procéder sans faire appel à des méthode statiques
    // Cf Discord
    private static float[][] genColors() {
        float[][] res = new float[LENGTH][WIDTH];

        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (i == 0 || i == LENGTH - 1 || j == 0 || j == WIDTH - 1)
                    res[i][j] = 0.5f;
            }
        }

        // Penses à utiliser super.storeColors
/*        for (Text text : display) {
            for (int x = 0; x < text.getColors().length; x++)
                for (int y = 0; y < text.getColors()[0].length; y++)
                    res[x + text.getX()][y + text.getY()] = text.getColors()[x][y];
        }*/
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

                    // On peut bind sur ce qu'on veut comme commande non ?
                    // if (name.equals("battalion")) {

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
                                event.setOutput("Only 4 shortcuts allowed");
                                break;
                        }
                    /*
                    } else {
                        event.setOutput(name + " is not an attack");
                    }
                    */

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
                        default:
                            event.setOutput("There are only 4 shortcuts...");
                            break;
                    }
                }
            }
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

            /*
            float sx_1 = display[3].getX() + getX();
            float sy_1 = display[3].getY() + getY();
            float dx_1 = display[3].getSizeX();
            float dy_1 = display[3].getSizeY();

            float sx_2 = display[2].getX() + getX();
            float sy_2 = display[2].getY() + getY();
            float dx_2 = display[2].getSizeX();
            float dy_2 = display[2].getSizeY();

            float sx_3 = display[1].getX() + getX();
            float sy_3 = display[1].getY() + getY();
            float dx_3 = display[1].getSizeX();
            float dy_3 = display[1].getSizeY();

            float sx_4 = display[0].getX() + getX();
            float sy_4 = display[0].getY() + getY();
            float dx_4 = display[0].getSizeX();
            float dy_4 = display[0].getSizeY();

            if (x >= sx_1 && x <= sx_1 + dx_1 && y >= sy_1 && y <= sy_1 + dy_1) {
                if (isBinded(3)) {
                    // System.out.println(display[3].getValue().substring(3));
                    if (display[3].getValue().substring(3).equals("battalion")) {
                            Battalion battalion = new Battalion();
                            CommandEvent attack = new CommandEvent("dwarf");
                            battalion.onCommandEvent(attack);
                    }
                }
            } else if (x >= sx_2 && x <= sx_2 + dx_2 && y >= sy_2 && y <= sy_2 + dy_2) {
                if (isBinded(2)) {
                    if (display[2].getValue().substring(3).equals("battalion")) {
                        Battalion battalion = new Battalion();
                        CommandEvent attack = new CommandEvent("dwarf");
                        battalion.onCommandEvent(attack);
                    }
                }
            } else if (x >= sx_3 && x <= sx_3 + dx_3 && y >= sy_3 && y <= sy_3 + dy_3) {
                if (isBinded(1)) {
                    if (display[1].getValue().substring(3).equals("battalion")) {
                        Battalion battalion = new Battalion();
                        CommandEvent attack = new CommandEvent("dwarf");
                        battalion.onCommandEvent(attack);
                    }
                }
            } else if (x >= sx_4 && x <= sx_4 + dx_4 && y >= sy_4 && y <= sy_4 + dy_4) {
                if (isBinded(0)) {
                    if (display[0].getValue().substring(3).equals("battalion")) {
                        Battalion battalion = new Battalion();
                        CommandEvent attack = new CommandEvent("dwarf");
                        battalion.onCommandEvent(attack);
                    }
                }
            }
            */

            for (int i = 0; i < 4; i++) {
                if (x >= sx[i] && x <= sx[i] + dx[i] && y >= sy[i] && y <= sy[i] + dy[i]) {
                    if (isBinded(4 - i - 1)) {
                        if (display[4 - i - 1].getValue().substring(3).equals("battalion")) {
                            Battalion battalion = new Battalion();
                            CommandEvent attack = new CommandEvent("dwarf");
                            battalion.onCommandEvent(attack);
                        }
                    }
                }
            }

        }
    }

}
