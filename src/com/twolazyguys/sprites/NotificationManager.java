package com.twolazyguys.sprites;

import com.twolazyguys.events.NotificationEvent;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public class NotificationManager extends Sprite {

    public NotificationManager(int x, int y) {
        super(x, y, genColors());
    }

    private static float[][] genColors() {
        float[][] res = new float[200][200];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
            }
        }
        return res;
    }


}
