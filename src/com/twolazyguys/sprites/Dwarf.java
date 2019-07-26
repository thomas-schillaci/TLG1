package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.AttackEvent;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.gamestates.Game;
import com.twolazyguys.util.ColorSpritesheet;
import com.twolazyguys.util.CpuAllocation;
import com.twolazyguys.util.Download;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public class Dwarf extends Sprite implements Listener {

    private Sprite[] sprites;
    private int current;
    private float drawingCount;
    private float attackingCount;
    private Download download;
    private CpuAllocation cpuAllocation;
    private boolean registeredAllocation;

    // ANCIENNEMENT DWARFTACK
    private String name;
    private int dl;
    private float damage;
    private float cpuUsage;
    private float emergencyLevel;
    private int wantedLevel;

    private static Monitor monitor = ((Game) Main.getGameState()).getMonitor();
    private static ColorSpritesheet sheet = new ColorSpritesheet(3, 2, "dwarfsheet");

    public Dwarf(int x, int y) {
        super(x, y);

        name = "dwarftack";
        dl = 10;
        damage = 0;
        cpuUsage = 0.2f;
        emergencyLevel = 0.5f;
        wantedLevel = 100;

        sprites = new Sprite[]{sheet.getSprite(0, 0), sheet.getSprite(0, 1)};

        // Léo, je te laisse corriger argument 2 et 3 (taille en Mb et BP serveur en Mb/s)
        download = new Download(2, 0.5f);
        cpuAllocation = new CpuAllocation(20);

        monitor.addDownload(download);
    }

    public boolean isDownloading() {
        return !download.hasFinished();
    }

    @EventHandler
    public void onGameTickEvent(GameTickEvent event) {
        // DRAWING

        drawingCount += Main.delta;
        if (drawingCount > 0.5f) {
            if (download.hasFinished()) {
                if (cpuAllocation.isAllocated()) {
                    current = 1 - current;
                    setColors(sprites[current].getColors());
                    drawingCount = 0;
                }
            } else {
                float[][] res = sprites[0].getColors();
                for (int x = 0; x < res.length; x++) {
                    for (int y = 2 + (int) (res[0].length * download.getDownloaded() / download.getSize()); y < res[0].length; y++) {
                        res[x][y] = 0;
                    }
                }
                setColors(res);
            }
        }

        // ATTACKING

        if (download.hasFinished() && !registeredAllocation) {
            registeredAllocation = true;
            monitor.addCpuAllocation(cpuAllocation);
        }

        if (cpuAllocation.isAllocated()) {
            attackingCount += Main.delta;
            if (attackingCount >= 1) {
                AttackEvent attack = new AttackEvent(name, dl, damage, cpuUsage, emergencyLevel, wantedLevel);
                Main.callEvent(attack);
                attackingCount = 0;
            }
        }

    }

}
