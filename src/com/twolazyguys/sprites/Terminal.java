package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.CommandEvent;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.events.NotificationEvent;
import com.twolazyguys.gamestates.Game;
import com.twolazyguys.util.GFile;
import net.colozz.engine2.events.CharInputEvent;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.KeyboardInputEvent;
import net.colozz.engine2.events.Listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Terminal extends Sprite implements Listener {


    public static final String ROOT = "res";

    private GFile root = new GFile(ROOT);

    private GFile currentDirectory = root;

    private String userName = "root";
    private String machine = "X";

    private static final int ROWS = 16;
    private static final int COLUMNS = 61;
    private static final int TEXT_OFFSET = 2;

    private Text input;

    private LogDisplay display;

    private int cursorIndex;

    private float displayLagCount;
    private float cursorCount;

    private ArrayList<String> lastCommands = new ArrayList<>();
    private int lastCommandsIndex = -1;
    private String lastPendingInput;

    public Terminal() {
        super(
                5,
                5
        );

        display = new LogDisplay(TEXT_OFFSET, TEXT_OFFSET + Text.getLetterSizeY(), ROWS - 1, COLUMNS);
        input = new Text(TEXT_OFFSET, TEXT_OFFSET + (ROWS - 1) * Text.getLetterSizeY(), "$ ");
        setPrefix();

        genColors();
    }

    private void genColors() {
        float[][] res = new float[2 * TEXT_OFFSET + COLUMNS * Text.getLetterSizeX()][ROWS * Text.getLetterSizeY() + 2 * TEXT_OFFSET];

        // outline
        for (int x = 0; x < res.length; x++) {
            res[x][0] = 0.3f;
            res[x][res[0].length - 1] = 0.3f;
        }
        for (int y = 0; y < res[0].length; y++) {
            res[0][y] = 0.3f;
            res[res.length - 1][y] = 0.3f;
        }

        storeColors(display, res);

        setUserInput(getUserInput() + " ");

        float[][] colors = input.getColors();
        for (int x = 0; x < colors.length; x++) {
            for (int y = 0; y < colors[0].length; y++) {
                int letterIndex = x * input.getValue().length() / colors.length;
                letterIndex -= getPrefix().length();
                res[x + input.getX()][y + input.getY()] = (letterIndex == cursorIndex && cursorCount < 0.75 ? 1 - colors[x][y] : colors[x][y]);
            }
        }

        setUserInput(getUserInput().substring(0, getUserInput().length() - 1));

        setColors(res);
    }

    @EventHandler
    public void onGameTickEvent(GameTickEvent e) {
        cursorCount += Main.delta;
        if (cursorCount > 1.5) cursorCount = 0;

        displayLagCount += Main.delta;
        if (displayLagCount > 0) genColors(); // à améliorer si on commence à avoir des baisses de fps
    }

    @EventHandler
    public void onKeyboardInputEvent(KeyboardInputEvent e) {
        if (e.getAction() == GLFW_PRESS || e.getAction() == GLFW_REPEAT) {
            if (e.getKey() == GLFW_KEY_ENTER) {
                String[] split = getUserInput().split(" ");
                String[] args = Arrays.copyOfRange(split, 1, split.length);

                String in = input.getValue();

                CommandEvent event = new CommandEvent(split[0], args);
                Main.callEvent(event);
                String[] output = event.getOutput();

                display.pushOutput(in);
                display.pushOutput(output);
                input.setY((ROWS - 1 - display.getFilledRows()) * Text.getLetterSizeY() + TEXT_OFFSET);

                displayLagCount = -0.15f;

                if (e.getAction() == GLFW_REPEAT) genColors();

                setUserInput("");
                lastPendingInput = null;
                cursorIndex = 0;
            } else if (e.getKey() == GLFW_KEY_BACKSPACE) {
                if (cursorIndex > 0) {
                    setUserInput(getUserInput().substring(0, cursorIndex - 1) + getUserInput().substring(cursorIndex));
                    cursorIndex--;
                    cursorIndex = Math.max(0, cursorIndex);
                }
            } else if (e.getKey() == GLFW_KEY_DELETE) {
                if (cursorIndex < getUserInput().length())
                    setUserInput(getUserInput().substring(0, cursorIndex) + getUserInput().substring(cursorIndex + 1));
            } else if (e.getKey() == GLFW_KEY_LEFT) {
                cursorCount = 0;
                cursorIndex--;
                cursorIndex = Math.max(0, cursorIndex);
            } else if (e.getKey() == GLFW_KEY_RIGHT) {
                cursorCount = 0;
                cursorIndex++;
                cursorIndex = Math.max(0, Math.min(cursorIndex, getUserInput().length()));
            } else if (e.getKey() == GLFW_KEY_UP) {
                if (lastCommandsIndex == -1) lastPendingInput = getUserInput();
                lastCommandsIndex++;
                if (lastCommandsIndex >= lastCommands.size()) lastCommandsIndex--;
                setUserInput(lastCommands.get(lastCommands.size() - lastCommandsIndex - 1));
                cursorIndex = getUserInput().length();
            } else if (e.getKey() == GLFW_KEY_DOWN) {
                lastCommandsIndex--;
                if (lastCommandsIndex < 0) {
                    lastCommandsIndex = -1;
                    if (lastPendingInput != null) {
                        setUserInput(lastPendingInput);
                        lastPendingInput = null;
                    }
                } else {
                    setUserInput(lastCommands.get(lastCommands.size() - lastCommandsIndex - 1));
                }
                cursorIndex = getUserInput().length();
            } else if (Game.isKeyDown(GLFW_KEY_LEFT_CONTROL) || Game.isKeyDown(GLFW_KEY_RIGHT_CONTROL)) {
                if (e.getKey() == GLFW_KEY_L) {
                    clear();
                } else if (e.getKey() == GLFW_KEY_D) {
                    Main.exit();
                }
                // FIXME AZERTY
                else if (e.getKey() == GLFW_KEY_W) {
                    setUserInput("");
                }
            }
        }
    }

    @EventHandler
    public void onCharInputEvent(CharInputEvent e) {
        if (e.getCharacterCallback() < 32) return;
        String c = "" + (char) e.getCharacterCallback();
        if (Text.TABLE.contains(c)) {
            setUserInput(getUserInput().substring(0, cursorIndex) + c + getUserInput().substring(cursorIndex));
            cursorIndex++;
        }
    }

    @EventHandler(EventHandler.Priority.HIGHEST)
    public void onCommandEvent(CommandEvent event) {
        if (!event.getCommand().equals("")) lastCommands.add(event.getCommand());
        lastCommandsIndex = -1;
        if (!event.isCanceled()) {
            event.setCanceled(true);

            String formatted = event.getCommand().toLowerCase();

            if (formatted.equals("help")) event.setOutput("Help coming soon!");
            else if (formatted.equals("cd")) {
                if (event.getArgs().length > 0) {
                    String dest = event.getArgs()[0];
                    if (dest.equals("..")) {
                        if (!currentDirectory.isRoot()) {
                            changeDirectory(currentDirectory.getParent());
                        }
                    } else {
                        boolean test = true;
                        for (GFile file : currentDirectory.listFiles()) {
                            if (file.isDirectory() && file.getName().equals(dest)) {
                                changeDirectory(file);
                                test = false;
                                break;
                            }
                        }
                        if (test) event.setOutput(event.getArgs()[0] + ": no such directory");
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
                    if (file.isFile()) {
                        try {
                            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
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
            } else if (formatted.equals("testnotif")) {
                Main.callEvent(new NotificationEvent("Test " + Math.random()));
            } else if (formatted.equals("")) {
            } else event.setOutput(event.getCommand() + ": command not found.");
        }
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
        display.clear();
        input.setY(TEXT_OFFSET + (ROWS - 1) * Text.getLetterSizeY());
        genColors();
    }

}
