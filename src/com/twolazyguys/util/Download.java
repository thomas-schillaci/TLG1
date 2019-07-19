package com.twolazyguys.util;

public class Download {

    private float size;
    private float serverBandwith;
    private float downloaded;

    public Download(float size, float serverBandwith) {
        this.size = size;
        this.serverBandwith = serverBandwith;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getServerBandwith() {
        return serverBandwith;
    }

    public void setServerBandwith(float serverBandwith) {
        this.serverBandwith = serverBandwith;
    }

    public float getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(float downloaded) {
        this.downloaded = downloaded;
    }

    public boolean hasFinished() {
        return getDownloaded() >= getSize();
    }

}