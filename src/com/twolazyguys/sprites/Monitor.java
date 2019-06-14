package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.util.ColorSpritesheet;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public class Monitor extends Sprite implements Listener {

    private static float cpuUsage, bandwidthUsage;

    private static ColorSpritesheet cpuSheet = new ColorSpritesheet(1, 5, "cpu");
    private static ColorSpritesheet bandwidthSheet = new ColorSpritesheet(1, 4, "wifi");

    private float count;

    public Monitor() {
        super(390, 20, genColors());
    }

    private static float[][] genColors() {
        float[][] res = new float[90][90];

        int cpuIndex = (int) (cpuUsage * cpuSheet.getColumns());
        float[][] cpuColors = cpuSheet.getSprite(0, cpuIndex).getColors();
        for (int x = 0; x < cpuColors.length; x++) {
            for (int y = 0; y < cpuColors[0].length; y++) {
                res[x][y] = cpuColors[x][y];
            }
        }

        int bandwidthIndex = (int) (bandwidthUsage * bandwidthSheet.getColumns());
        float[][] bandwidthColors = bandwidthSheet.getSprite(0, bandwidthIndex).getColors();
        for (int x = 0; x < bandwidthColors.length; x++) {
            for (int y = 0; y < bandwidthColors[0].length; y++) {
                res[x][y + 45] = bandwidthColors[x][y];
            }
        }

        return res;
    }

    public float getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(float cpuUsage) {
        this.cpuUsage = cpuUsage;

    }

    public float getBandwidthUsage() {
        return bandwidthUsage;
    }

    public void setBandwidthUsage(float bandwidthUsage) {
        this.bandwidthUsage = bandwidthUsage;
    }

    @EventHandler
    public void onGameTickEvent(GameTickEvent e) {
        count += Main.delta;
        setBandwidthUsage((float) Math.abs(Math.cos(count)));
        setCpuUsage((float) Math.abs(Math.sin(2 * count)));
        setColors(genColors());
    }

}
