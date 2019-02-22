package id.delta.whatsapp.split.service;

import android.net.Uri;

import id.delta.whatsapp.split.presenter.NotificationCenter;
import id.delta.whatsapp.split.presenter.NotificationName;


public class DataStorage {
    private static final DataStorage ourInstance = new DataStorage();
    private String selectedVideoPath;
    private int splitDuration = 30;
    private Uri videoPath;

    private DataStorage() {
    }

    public static DataStorage getInstance() {
        return ourInstance;
    }

    public String getSelectedVideoPath() {
        return this.selectedVideoPath;
    }

    public int getSplitDuration() {
        return this.splitDuration;
    }

    public Uri getVideoPath() {
        return this.videoPath;
    }

    public void setSelectedVideoPath(String str) {
        this.selectedVideoPath = str;
    }

    public void setSplitDuration(int i) {
        this.splitDuration = i;
    }

    public void setVideoPath(Uri uri) {
        this.videoPath = uri;
        NotificationCenter.getInstance().post(NotificationName.VIDEO_PATH_CHANGED, uri);
    }
}
