package com.twolazyguys.sprites;

import com.twolazyguys.events.NotificationEvent;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public class NotificationManager extends Sprite implements Listener {

    private static final int TEXT_OFFSET = 2;
    private LogDisplay display = new LogDisplay(TEXT_OFFSET, TEXT_OFFSET, 5, 30);
    private boolean changed = false;

    public NotificationManager(int x, int y) {
        super(x, y);
    }

    @Override
    public float[][] getColors() {
        changed = false;
        return genColors();
    }

    private float[][] genColors() {
        float[][] res = new float[display.getColumns() * Text.getLetterSizeX() + 2 * TEXT_OFFSET][display.getRows() * Text.getLetterSizeY() + 2 * TEXT_OFFSET];

        for (int x = 0; x < res.length; x++) {
            res[x][0] = 0.3f;
            res[x][res[0].length - 1] = 0.3f;
        }
        for (int y = 0; y < res[0].length; y++) {
            res[0][y] = 0.3f;
            res[res.length - 1][y] = 0.3f;
        }

        storeColors(display, res);
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
