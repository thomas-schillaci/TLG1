package com.twolazyguys.sprites;


import com.twolazyguys.Main;
import com.twolazyguys.events.AttackEvent;
import com.twolazyguys.events.DownloadEvent;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.util.ColorSpritesheet;

import com.twolazyguys.util.Download;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

import java.util.ArrayList;

public class Monitor extends Sprite implements Listener {

    /**
     * Usages in percent
     */
    private float cpuUsage = 0, bandwidthUsage = 0;
    /**
     * maxBandwidth in Mb/s
     */
    private float maxBandwidth = 1;
    private ArrayList<Download> downloads = new ArrayList<>();
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
        float[][] cpuColors = cpuSheet.getSprite(0, cpuIndex).getColors();
        for (int x = 0; x < cpuColors.length; x++) {
            for (int y = 0; y < cpuColors[0].length; y++) {
                res[x + 45][y] = cpuColors[x][y];
            }
        }

        int bandwidthIndex = (int) (bandwidthUsage * (bandwidthSheet.getColumns() - 1));
        float[][] bandwidthColors = bandwidthSheet.getSprite(0, bandwidthIndex).getColors();
        for (int x = 0; x < bandwidthColors.length; x++) {
            for (int y = 0; y < bandwidthColors[0].length; y++) {
                res[x + 45][y + 45] = bandwidthColors[x][y];
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

    /**
     * @return bandwidth usage in percent
     */
    public float getBandwidthUsage() {
        return bandwidthUsage;
    }

    /**
     * @return max bandwidth in Mb/S
     */
    public float getMaxBandwidth() {
        return maxBandwidth;
    }

    public void setMaxBandwidth(float maxBandwidth) {
        this.maxBandwidth = maxBandwidth;
    }

    public void removeDownload(Download download) {
        downloads.remove(download);
    }

    private void setBandwidthUsage(float bandwidthUsage) {
        if (this.bandwidthUsage != bandwidthUsage) {
            this.bandwidthUsage = bandwidthUsage;
            changed = true;
        }
    }

    public boolean hasChanged() {
        return changed;
    }

    @EventHandler
    public void onDownloadEvent(DownloadEvent e) {
        downloads.add(e.getDownload());
    }

    @EventHandler
    public void onGameTickEvent(GameTickEvent e) {
        // ALLOCATION

        float needs = 0;
        for (Download download : downloads) needs += download.getServerBandwith();

        float[] allocated = new float[downloads.size()];

        if (needs <= maxBandwidth) {
            for (int i = 0; i < downloads.size(); i++) {
                allocated[i] = downloads.get(i).getServerBandwith();
            }
            setBandwidthUsage(needs / maxBandwidth);
        } else {
            float free = maxBandwidth;
            for (int i = 0; i < allocated.length; i++) {
                float toUse = Math.min(free, downloads.get(i).getServerBandwith());
                allocated[i] = toUse;
                free -= toUse;
                if(free == 0) break;
                assert(free > 0);
            }
            setBandwidthUsage(1);
        }

        // DOWNLOADING

        for (int i = 0; i < downloads.size(); i++) {
            Download download = downloads.get(i);
            download.setDownloaded(download.getDownloaded() + allocated[i] * Main.delta);
        }

        // FREEING

        clearDownloads();
    }

    private void clearDownloads() {
        boolean test = false;
        for (Download download : downloads) {
            if (download.hasFinished()) {
                downloads.remove(download);
                test = true;
                break;
            }
        }
        if(test) clearDownloads();
    }

}


