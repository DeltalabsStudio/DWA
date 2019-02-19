package id.delta.whatsapp.split;

import android.app.ProgressDialog;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import id.delta.whatsapp.activities.BaseActivity;
import id.delta.whatsapp.utils.Tools;

public class SplittingActivity extends BaseActivity implements SplitterDelegate{
    TextView mSource;
    Button mVideoSplit;

    MediaMetadataRetriever r;
    Double s = Double.valueOf(0.0d);
    boolean y = false;
    final ArrayList<Uri> z = new ArrayList();
    Thread x;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Tools.intLayout("delta_split_activity"));
        mSource = findViewById(Tools.intId("source"));
        mVideoSplit = findViewById(Tools.intId("mSplitVideo"));
        addObserverForVideoView();
        r = new MediaMetadataRetriever();
        y = getIntent().getBooleanExtra("TakeSelected", false);
        if (this.y) {
            this.r.setDataSource(this, Uri.parse(DataStorage.getInstance().getSelectedVideoPath()));
        } else {
            this.r.setDataSource(this, DataStorage.getInstance().getVideoPath());
        }
        String extractMetadata = this.r.extractMetadata(9);
        this.r.release();
        this.s = Double.valueOf(Double.parseDouble(extractMetadata));
        mSource.setText(String.valueOf(DataStorage.getInstance().getSplitDuration()));

        mVideoSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splitButtonClicked();
            }
        });
    }

    private void splitButtonClicked() {
        String str = "/storage/emulated/0/DCIM/VideoSplitter/Parts/";
        x = new Thread(new Runnable() {
            @Override
            public void run() {
                new Splitter(SplittingActivity.this, SplittingActivity.this).split("/storage/emulated/0/DCIM/VideoSplitter/Parts/", s.doubleValue(), y);
            }
        });
        x.start();
    }

    private void addObserverForVideoView() {
       NSNotificationCenter.getInstance().addObserver(NSNotificationName.VIDEO_PATH_CHANGED, new Observer() {
           @Override
           public void update(Observable o, Object arg) {
               if (DataStorage.getInstance().getVideoPath() == null) {
                   finish();
               }
           }
       });
    }

    @Override
    public void completed() {
        this.finish();
    }

    @Override
    public void progress(int i, String str) {

    }

    @Override
    public void started() {

    }
}
