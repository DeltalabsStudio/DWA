package id.delta.whatsapp.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;
import java.lang.Thread.State;

public class PlayVideoView extends VideoView {
    private PlayVideoViewInteface pVideoViewInteface = null;
    private Thread playSecondsCounterThread;
    private long secondsPlayed = 0;
    private Status status = Status.Untouched;

    public interface PlayVideoViewInteface {
        void statusChanged(Status status);
    }

    public enum Status {
        Playing,
        Paused,
        Untouched
    }

    public PlayVideoView(Context context) {
        super(context);
        setup();
    }

    public PlayVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setup();
    }

    public PlayVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setup();
    }

    private void setup() {
        this.playSecondsCounterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (status == Status.Playing) {
                    try {
                        Thread.sleep(1000);
                        secondsPlayed = secondsPlayed + 1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public long getSecondsPlayed() {
        return this.secondsPlayed;
    }

    public Status getStatus() {
        return this.status;
    }

    public void pause() {
        super.pause();
        setStatus(Status.Paused);
    }

    public void seekTo(int i) {
        super.seekTo(i);
        this.secondsPlayed = (long) (i / 1000);
    }

    public void setStatus(Status status) {
        this.status = status;
        if (this.pVideoViewInteface != null) {
            this.pVideoViewInteface.statusChanged(status);
        }
    }

    public void start() {
        Log.d("playSecondsThread", this.playSecondsCounterThread.getState().name());
        if (this.playSecondsCounterThread.getState().equals(State.TERMINATED)) {
            setup();
        }
        if (this.playSecondsCounterThread.getState() == State.NEW) {
            this.playSecondsCounterThread.start();
        }
        super.start();
        setStatus(Status.Playing);
    }
}
