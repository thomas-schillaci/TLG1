package com.twolazyguys.events;

import com.twolazyguys.util.Download;
import net.colozz.engine2.events.Event;

/**
 * To trigger to launch a download
 */
public class DownloadEvent extends Event {

    private Download download;

    public DownloadEvent(Download download) {
        this.download = download;
    }

    public Download getDownload() {
        return download;
    }

}
