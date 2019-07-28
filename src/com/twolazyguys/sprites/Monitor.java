package com.twolazyguys.sprites;


import com.twolazyguys.Main;
import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.util.CpuAllocation;
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
    /**
     * cpuFrequency in MHz
     */
    private float cpuFrequency = 100;
    private ArrayList<CpuAllocation> cpuAllocations = new ArrayList<>();

    private boolean changed = false;

    private Cpu cpu = new Cpu(0, 14);
    private Bandwidth bandwidth = new Bandwidth(0, 81);

    private float graphCount;

    public Monitor(int x, int y) {
        super(x, y);
    }

    @Override
    public float[][] getColors() {
        changed = false;
        return genColors();
    }

    private float[][] genColors() {
        float[][] res = new float[119][134];

        storeColors(cpu, res);
        storeColors(bandwidth, res);

        return res;
    }

    public float getCpuUsage() {
        return cpuUsage;
    }

    public float getCpuFrequency() {
        return cpuFrequency;
    }

    public void setCpuFrequency(float cpuFrequency) {
        this.cpuFrequency = cpuFrequency;
        refreshCpuUsage();
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

    private void setBandwidthUsage(float bandwidthUsage) {
        if (this.bandwidthUsage != bandwidthUsage) {
            this.bandwidthUsage = bandwidthUsage;
            changed = true;
        }
    }

    private void setCpuUsage(float cpuUsage) {
        if (cpuUsage != this.cpuUsage) {
            this.cpuUsage = cpuUsage;
            changed = true;
        }
    }

    public boolean hasChanged() {
        return changed;
    }

    public void addDownload(Download download) {
        downloads.add(download);
    }

    public void removeDownload(Download download) {
        downloads.remove(download);
    }

    public void addCpuAllocation(CpuAllocation cpuAllocation) {
        cpuAllocations.add(cpuAllocation);
        refreshCpuUsage();
    }

    public void removeCpuAllocation(CpuAllocation cpuAllocation) {
        cpuAllocations.remove(cpuAllocation);
        refreshCpuUsage();
    }

    public void refreshCpuUsage() {
        setCpuUsage(0);
        for (int i = 0; i < cpuAllocations.size(); i++) {
            CpuAllocation cpuAllocation = cpuAllocations.get(i);
            // Use of rounding to avoid float precision errors
            float availableFrequency = Math.round(100 * (1f - cpuUsage) * cpuFrequency) / 100f;
            float requiredFrequency = cpuAllocation.getRequiredFrequency();

            if (requiredFrequency <= availableFrequency) {
                setCpuUsage(getCpuUsage() + Math.round(100 * requiredFrequency / cpuFrequency) / 100f);
                cpuAllocation.setAllocated(true);
            } else {
                cpuAllocation.setAllocated(false);
            }

            if (getCpuUsage() == 1) {
                for (int j = i + 1; j < cpuAllocations.size(); j++) {
                    cpuAllocations.get(j).setAllocated(false);
                }
                break;
            }

            assert (getCpuUsage() <= 1);
        }
    }


    @EventHandler
    public void onGameTickEvent(GameTickEvent e) {
        // BW ALLOCATION

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
                if (free == 0) break;
                assert (free > 0);
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

        // ELEMENTS CHECKING

        graphCount += Main.delta;
        if (graphCount > 0.5) {
            graphCount = 0;
            cpu.update(cpuUsage);
            bandwidth.update(bandwidthUsage);
            changed = true;
        }
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
        if (test) clearDownloads();
    }

}


