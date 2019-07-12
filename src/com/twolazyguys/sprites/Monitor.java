package com.twolazyguys.sprites;


import com.twolazyguys.events.AttackEvent;
import com.twolazyguys.util.ColorSpritesheet;

import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public class Monitor extends Sprite implements Listener {

    private float cpuUsage = 0f, bandwidthUsage = 1;
    private boolean changed = false;

    private ColorSpritesheet cpuSheet = new ColorSpritesheet(1, 5, "cpu");
    private ColorSpritesheet bandwidthSheet = new ColorSpritesheet(1, 4, "wifi");

    public Monitor(int x, int y) {
        super(x, y);
    }

    @Override
    public float[][] getColors() {
        changed = false;
        return genColors();
    }

    private float[][] genColors() {
        float[][] res = new float[90][90];

        int cpuIndex = (int) (cpuUsage * (cpuSheet.getColumns() - 1));
        System.out.println(cpuUsage * (cpuSheet.getColumns() - 1));
        float[][] cpuColors = cpuSheet.getSprite(0, cpuIndex).getColors();
        for (int x = 0; x < cpuColors.length; x++) {
            for (int y = 0; y < cpuColors[0].length; y++) {
                res[x+45][y] = cpuColors[x][y];
            }
        }

        int bandwidthIndex = (int) (bandwidthUsage * (bandwidthSheet.getColumns() - 1));
        float[][] bandwidthColors = bandwidthSheet.getSprite(0, bandwidthIndex).getColors();
        for (int x = 0; x < bandwidthColors.length; x++) {
            for (int y = 0; y < bandwidthColors[0].length; y++) {
                res[x+45][y + 45] = bandwidthColors[x][y];
            }
        }
        return res;
    }

    public float getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(float cpuUsage) {
        if (this.cpuUsage != cpuUsage) {
            this.cpuUsage = cpuUsage;
            changed = true;
        }
    }

    public float getBandwidthUsage() {
        return bandwidthUsage;
    }

    public void setBandwidthUsage(float bandwidthUsage) {
        if (this.bandwidthUsage != bandwidthUsage) {
            this.bandwidthUsage = bandwidthUsage;
            changed = true;
        }
    }

    public boolean hasChanged() {
        return changed;
    }
    
    @EventHandler
    public void onAttackEvent(AttackEvent e) {
    	this.setCpuUsage(this.getCpuUsage() + e.getCpuUsage());
    	this.setColors(this.genColors());
    }
    
}
