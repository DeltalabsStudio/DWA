package id.delta.whatsapp.split;

import android.net.Uri;

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
        NSNotificationCenter.getInstance().post(NSNotificationName.VIDEO_PATH_CHANGED, uri);
    }
}
