package id.delta.whatsapp.split;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.util.Observable;
import java.util.Observer;

import id.delta.whatsapp.activities.BaseActivity;
import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.utils.Tools;

public class MainSpitActivity extends BaseActivity {
    VideoView mVideoView;
    Button mVideoPicker;
    Button mSplitVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Tools.intLayout("delta_mainsplit_activity"));
        Toolbar mToolbar = findViewById(Tools.intId("mToolbar"));
        setToolbar(mToolbar);
        mVideoView = findViewById(Tools.intId("mVideoView"));
        addObserverForVideoView();
        mVideoPicker = findViewById(Tools.intId("mVideoPicker"));
        mVideoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK", MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                intent.setTypeAndNormalize("video/*");
                startActivityForResult(intent, RequestId.VIDEO_SELECTOR_INTENT_REQUEST);
            }
        });

        mSplitVideo = findViewById(Tools.intId("mSplitVideo"));
        mSplitVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actions.startActivity(MainSpitActivity.this, SplittingActivity.class);
            }
        });

    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == RequestId.VIDEO_SELECTOR_INTENT_REQUEST) {
            videoPickerResultHandler(i2, intent);
        }
    }

    private void videoPickerResultHandler(int i, Intent intent) {
        if (i != -1) {
            DataStorage.getInstance().setVideoPath(null);
        }
        if (intent == null) {
            DataStorage.getInstance().setVideoPath(null);
        } else {
            DataStorage.getInstance().setVideoPath(intent.getData());
        }
        if (DataStorage.getInstance().getVideoPath() != null) {
        }
    }

    private void addObserverForVideoView(){
        NSNotificationCenter.getInstance().addObserver(NSNotificationName.VIDEO_PATH_CHANGED, new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                mVideoView.setVideoURI(DataStorage.getInstance().getVideoPath());
                if (DataStorage.getInstance().getVideoPath() == null) {
                    mVideoView.pause();
                } else {
                    mVideoView.start();
                }
            }
        });
    }
}
