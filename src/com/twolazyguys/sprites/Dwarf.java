package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.DownloadEvent;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.util.ColorSpritesheet;
import com.twolazyguys.util.Download;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public class Dwarf extends Sprite implements Listener {

    private Sprite[] sprites;
    private int current;
    private float count;
    private Download download;
    private boolean downloading = true;

    private static ColorSpritesheet sheet = new ColorSpritesheet(3, 2, "dwarfsheet");

    public Dwarf(int x, int y) {
        super(x, y);

        sprites = new Sprite[]{sheet.getSprite(0, 0), sheet.getSprite(0, 1)};

        // Léo, je te laisse corriger argument 2 et 3 (taille en Mb et BP serveur en Mb/s)
        download = new Download(2, 0.5f);
        Main.callEvent(new DownloadEvent(download));
    }

    public boolean isDownloading() {
        return downloading;
    }

    @EventHandler
    public void onGameTickEvent(GameTickEvent event) {
        if (download.hasFinished()) downloading = false;

        count += Main.delta;
        if (count > 0.5f) {
            if (downloading) {
                float[][] res = sprites[0].getColors();
                for (int x = 0; x < res.length; x++) {
                    for (int y = 2 + (int) (res[0].length * download.getDownloaded() / download.getSize()); y < res[0].length; y++) {
                        res[x][y] = 0;
                    }
                }
                setColors(res);
            } else {
                current = 1 - current;
                setColors(sprites[current].getColors());
                count = 0;
            }
        }
    }

}
