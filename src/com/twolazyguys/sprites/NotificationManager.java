package com.twolazyguys.sprites;

import com.twolazyguys.events.NotificationEvent;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public class NotificationManager extends Sprite implements Listener {

    private static final int TEXT_OFFSET = 2;
    private LogDisplay display = new LogDisplay(TEXT_OFFSET, TEXT_OFFSET, 13, 19);
    private Text text = new Text(0, display.getY() + display.getSizeY() + 2 + TEXT_OFFSET, "Notifications");
    private boolean changed = false;

    public final static int SIZE_X = 119;
    public final static int SIZE_Y = 134;

    public NotificationManager(int x, int y) {
        super(x, y);
    }

    @Override
    public float[][] getColors() {
        changed = false;
        return genColors();
    }

    private float[][] genColors() {
        float[][] res = new float[SIZE_X][SIZE_Y];

        for (int x = 0; x < SIZE_X; x++) {
            res[x][0] = 0.3f;
            res[x][display.getSizeY() + 2 * TEXT_OFFSET - 1] = 0.3f;
        }
        for (int y = 0; y < display.getSizeY() + 2 * TEXT_OFFSET; y++) {
            res[0][y] = 0.3f;
            res[SIZE_X - 1][y] = 0.3f;
        }

        storeColors(display, res);
        storeColors(text, res);

        return res;
    }

    public boolean hasChanged() {
        return changed;
    }

    @EventHandler
    public void onNotificationEvent(NotificationEvent e) {
        display.pushOutput(e.getDescription());
        changed = true;
    }

}
