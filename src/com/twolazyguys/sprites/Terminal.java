package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.CommandEvent;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.gamestates.Game;
import com.twolazyguys.util.GFile;
import net.colozz.engine2.events.CharInputEvent;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.KeyboardInputEvent;
import net.colozz.engine2.events.Listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Terminal extends Sprite implements Listener {


    public final static String ROOT = "res";

    private static GFile root = new GFile(ROOT);

    private static GFile currentDirectory = root;

    private static String userName = "root";
    private static String machine = "X";

    private final static int TEXT_OFFSET = 2;
    private final static int LINE_HEIGHT = Text.getLetterSizeY() + TEXT_OFFSET;
    private final static int NUMBER_OF_ROWS = 8;
    private static Text input = new Text(TEXT_OFFSET, TEXT_OFFSET + (NUMBER_OF_ROWS - 1) * LINE_HEIGHT, "$ ");
    private static Text[] display = new Text[NUMBER_OF_ROWS - 1];
    private static int inputIndex = NUMBER_OF_ROWS - 1;

    private static int cursorIndex; // TODO

    private static float count;

    static {
        for (int i = 0; i < display.length; i++)
            display[i] = new Text(TEXT_OFFSET, (i + 1) * LINE_HEIGHT + TEXT_OFFSET, "");
    }

    public Terminal() {
        super(
                10,
                10,
                genColors()
        );
        setPrefix();
        setColors(genColors());
    }

    private static float[][] genColors() {
        float[][] res = new float[200][NUMBER_OF_ROWS * LINE_HEIGHT + TEXT_OFFSET];

        // background
        for (int x = 0; x < res.length; x++) for (int y = 0; y < res[0].length; y++) res[x][y] = 0.0f;

        // outline
        for (int x = 0; x < res.length; x++) {
            res[x][0] = 0.3f;
            res[x][res[0].length - 1] = 0.3f;
        }
        for (int y = 0; y < res[0].length; y++) {
            res[0][y] = 0.3f;
            res[res.length - 1][y] = 0.3f;
        }

        for (Text text : display) {
            for (int x = 0; x < text.getColors().length; x++)
                for (int y = 0; y < text.getColors()[0].length; y++)
                    res[x + text.getX()][y + text.getY()] = text.getColors()[x][y];
        }

        for (int x = 0; x < input.getColors().length; x++)
            for (int y = 0; y < input.getColors()[0].length; y++)
                res[x + input.getX()][y + input.getY()] = input.getColors()[x][y];

        return res;
    }

    @EventHandler
    public void onGameTickEvent(GameTickEvent e) {
        count += Main.delta;
        if (count > 0) setColors(genColors());
    }

    @EventHandler
    public void onKeyboardInputEvent(KeyboardInputEvent e) {
        if (e.getAction() == GLFW_PRESS) {
            if (e.getKey() == GLFW_KEY_ENTER) {
                String[] split = getUserInput().split(" ");
                String[] args = Arrays.copyOfRange(split, 1, split.length);

                CommandEvent event = new CommandEvent(split[0], args);
                Main.callEvent(event);
                String[] output = event.getOutput();

                pushOutput(input.getValue());
                for (String str : output) pushOutput(str);

                count = -0.15f;

                setUserInput("");
            } else if (e.getKey() == GLFW_KEY_BACKSPACE) {
                if (!getUserInput().equals("")) {
                    setUserInput(getUserInput().substring(0, getUserInput().length() - 1));
                }
            } else if (Game.isKeyDown(GLFW_KEY_LEFT_CONTROL) || Game.isKeyDown(GLFW_KEY_RIGHT_CONTROL)) {
                if (e.getKey() == GLFW_KEY_L) {
                    clear();
                } else if (e.getKey() == GLFW_KEY_D) {
                    Main.exit();
                }
            }
        }
    }

    @EventHandler
    public void onCharInputEvent(CharInputEvent e) {
        if (e.getCharacterCallback() < 32) return;
        String c = "" + (char) e.getCharacterCallback();
        if (Text.TABLE.contains(c)) setUserInput(getUserInput() + c);
    }

    @EventHandler(EventHandler.Priority.HIGHEST)
    public void onCommandEvent(CommandEvent event) {
        if (!event.isCanceled()) {
            event.setCanceled(true);

            String formatted = event.getCommand().toLowerCase();

            if (formatted.equals("help")) event.setOutput("Help coming soon!");
            else if (formatted.equals("dwarf")) {
                Dwarf dwarf = new Dwarf();
                ((Game) Main.getGameState()).getColormap().addSprite(dwarf);
                Main.addListener(dwarf);
            } else if (formatted.equals("cd")) {
                if (event.getArgs().length > 0) {
                    String dest = event.getArgs()[0];
                    if (dest.equals("..")) {
                        if (!currentDirectory.isRoot()) {
                            changeDirectory(currentDirectory.getParent());
                        }
                    } else {
                        for (GFile file : currentDirectory.listFiles()) {
                            if (file.isDirectory() && file.getName().equals(dest)) {
                                changeDirectory(file);
                            }
                        }
                    }
                }
            } else if (formatted.equals("ls")) {
                GFile[] files = currentDirectory.listFiles();
                String[] output = new String[files.length];
                for (int i = 0; i < files.length; i++) output[i] = files[i].getName();
                event.setOutput(output);
            } else if (formatted.equals("cat")) {
                if (event.getArgs().length > 0) {
                    File file = new File(ROOT + "/" + currentDirectory.getPath().substring(1) + "/" + event.getArgs()[0]);
                    System.out.println(currentDirectory.getPath().replaceFirst("/", ROOT) + event.getArgs()[0]);
                    if (file.isFile()) {
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            ArrayList<String> out = new ArrayList<>();
                            String line;
                            while ((line = br.readLine()) != null) out.add(line);
                            event.setOutput(out.toArray(new String[0]));
                            br.close();
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (formatted.equals("")) {
            } else event.setOutput(event.getCommand() + ": command not found.");
        }
    }

    // PREVENT OVERFLOW
    private void pushOutput(String str) {
        int max = 200 / Text.getLetterSizeX();
        String res = "";
        if(str.length()>=max) {
            res = str.substring(max - 1);
            str = str.substring(0, max - 1);
        }

        if (inputIndex > 0) {
            inputIndex--;
            input.setY(input.getY() - LINE_HEIGHT);
        } else {
            for (int i = NUMBER_OF_ROWS - 2; i >= 1; i--) {
                display[i].setValue(display[i - 1].getValue());
            }
        }
        display[inputIndex].setValue(str);

        if(!res.equals("")) pushOutput(res);
    }

    private void changeDirectory(GFile newDirectory) {
        currentDirectory = newDirectory;
        setPrefix();
    }

    private String getPrefix() {
        return input.getValue().substring(0, input.getValue().indexOf("$ ") + 2);
    }

    private void setPrefix() {
        String userInput = getUserInput();
        input.setValue(userName + "@" + machine + ":" + currentDirectory.getPath() + "$ " + userInput);
    }

    private String getUserInput() {
        return input.getValue().substring(input.getValue().indexOf("$ ") + 2);
    }

    private void setUserInput(String userInput) {
        String prefix = getPrefix();
        input.setValue(prefix + userInput);
    }

    private void clear() {
        for (Text text : display) text.setValue("");
        inputIndex = NUMBER_OF_ROWS - 1;
        input.setY(TEXT_OFFSET + (NUMBER_OF_ROWS - 1) * LINE_HEIGHT);
        setColors(genColors());
    }

}