package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.events.NotificationEvent;
import com.twolazyguys.gamestates.Game;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;
import net.colozz.engine2.events.MouseInputEvent;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class RightPanel extends Sprite implements Listener {

    private Bell bell = new Bell(90, 200);
    private Monitor monitor = new Monitor(0, 0);
    private NotificationManager notificationManager = new NotificationManager(0, 0);
    private boolean mainMode = true;

    public RightPanel() {
        super(300, 0);
    }

    private void genColors() {
        this.setColors(new float[200][220]);

        storeColors(bell);
        storeColors(mainMode?monitor:notificationManager);
    }

    @EventHandler
    public void onMouseInputEvent(MouseInputEvent e) {
        if (e.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT && e.getAction() == GLFW.GLFW_RELEASE) {
            double[] xList = new double[1];
            double[] yList = new double[1];
            glfwGetCursorPos(e.getWindow(), xList, yList);
            double x = xList[0] / Main.width * Game.X_PIXELS;
            double y = Game.Y_PIXELS - (yList[0] / Main.height * Game.Y_PIXELS);

            if (x >= getX() && x <= getX() + getSizeX() && y >= getY() && y <= getY() + getSizeY()) {
                mainMode = !mainMode;
            }
        }
    }


    @EventHandler
    public void onNotificationEvent(NotificationEvent e) {
    }

    @EventHandler
    public void onGameTickEvent(GameTickEvent e) {
        genColors();
    }

}
