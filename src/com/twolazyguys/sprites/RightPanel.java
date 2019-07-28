package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.gamestates.Game;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;
import net.colozz.engine2.events.MouseInputEvent;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class RightPanel extends Sprite implements Listener {

    private Bell bell = new Bell(47, 134);
    private Monitor monitor = new Monitor(0, 0);
    private NotificationManager notificationManager = new NotificationManager(0, 0);
    private boolean mainMode = true; // mainMode is the monitor

    public RightPanel() {
        super(383, 5);

        Main.addListener(bell);
        Main.addListener(notificationManager);
        Main.addListener(monitor);

        genColors();
    }

    private void genColors() {
        float[][] res = new float[119][159];

        storeColors(bell, res);
        storeColors(mainMode ? monitor : notificationManager, res);

        // PURGING

        if (mainMode) notificationManager.getColors();
        else monitor.getColors();

        setColors(res);
    }

    @EventHandler
    public void onMouseInputEvent(MouseInputEvent e) {
        if (e.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT && e.getAction() == GLFW.GLFW_RELEASE) {
            double[] xList = new double[1];
            double[] yList = new double[1];
            glfwGetCursorPos(e.getWindow(), xList, yList);
            double x = xList[0] / Main.width * Game.X_PIXELS;
            double y = Game.Y_PIXELS - (yList[0] / Main.height * Game.Y_PIXELS);

            if (x >= bell.getX() + getX() && x <= bell.getX() + getX() + bell.getSizeX() && y >= getY() + bell.getY() && y <= getY() + bell.getY() + bell.getSizeY()) {
                mainMode = !mainMode;
                bell.setRinging(false);
                genColors();
            }
        }
    }

    @EventHandler
    public void onGameTickEvent(GameTickEvent e) {
        if (notificationManager.hasChanged() || monitor.hasChanged() || bell.hasChanged()) {
            if (notificationManager.hasChanged() && mainMode) bell.setRinging(true);
            genColors();
        }
    }

    public Monitor getMonitor() {
        return monitor;
    }

}
