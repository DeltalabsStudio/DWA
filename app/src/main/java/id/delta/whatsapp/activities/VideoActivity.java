package id.delta.whatsapp.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

import id.delta.whatsapp.R;
import id.delta.whatsapp.dialog.DialogSelect;
import id.delta.whatsapp.split.presenter.NotificationCenter;
import id.delta.whatsapp.split.presenter.NotificationName;
import id.delta.whatsapp.split.service.DataStorage;
import id.delta.whatsapp.ui.views.CurvedBottom;
import id.delta.whatsapp.ui.views.FloatingActionButton;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Themes;

public class VideoActivity extends BaseActivity implements View.OnClickListener {

    //Permission
    public boolean hasPermission = false;
    private Object[] WRITE_STORAGE;
    private int REQ_ALLOW = 100;
    private final int REQUEST_PICKED_VIDEO = 102;

    public static String mPartLocation = "/storage/emulated/0/DCIM/Splitter/Videos/";

    VideoView mVideoView;
    FloatingActionButton mSelectVideo;
    boolean isHasVideo = false;
    boolean isPicked = false;
    View mSplit, mTrim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Tools.intLayout("delta_video_activity"));

        Toolbar mToolbar = findViewById(Tools.intId("mToolbar"));
        setToolbar(mToolbar);

        if (Build.VERSION.SDK_INT < 23) {
            hasPermission = true;
            pickVideo();
        }
        requestPermission();

        mVideoView = findViewById(Tools.intId("mVideoView"));
        addObserverForVideoView();

        CurvedBottom mCurved = findViewById(Tools.intId("mCurved"));
        mCurved.setDefaultPosition(CurvedBottom.CENTER);

        mVideoView.setVisibility(View.GONE);
        mSelectVideo = findViewById(Tools.intId("mSelectVideo"));
        mSelectVideo.setOnClickListener(this);
        if(!isHasVideo){
            mSelectVideo.setImageResource(Tools.intDrawable("delta_ic_videocam"));
        }else {
            mSelectVideo.setImageResource(Tools.intDrawable("delta_ic_delete"));
        }

        mSplit = findViewById(Tools.intId("mSplitButton"));
        mTrim = findViewById(Tools.intId("mTrimButton"));

    }

    private void pickVideo(){
        Intent intent = new Intent("android.intent.action.PICK", MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        intent.setTypeAndNormalize("video/*");
        startActivityForResult(intent, REQUEST_PICKED_VIDEO);
    }

    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                if (Integer.parseInt(Activity.class.getMethod("checkSelfPermission", new Class[]{String.class}).invoke(this, new Object[]{"android.permission.WRITE_EXTERNAL_STORAGE"}).toString()) == 0) {
                    hasPermission = true;
                }
            } catch (Exception e) {}
            if (!hasPermission) {
                try {
                    Method q = Activity.class.getMethod("requestPermissions", new Class[]{String[].class, Integer.TYPE});
                    WRITE_STORAGE = new Object[2];
                    WRITE_STORAGE[0] = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"};
                    WRITE_STORAGE[1] = Integer.valueOf(74565);
                    q.invoke(this, WRITE_STORAGE);
                    //  finish();
                } catch (Exception e2) {
                }
            }else {
                //Diijinkan
                pickVideo();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mSelectVideo){
            if(!isHasVideo){
                if (Build.VERSION.SDK_INT < 23) {
                    hasPermission = true;
                    pickVideo();
                }
                requestPermission();
            }else {
                DataStorage.getInstance().setVideoPath(null);
                mVideoView.setVisibility(View.GONE);
                isHasVideo = false;
                mSelectVideo.setImageResource(Tools.intDrawable("delta_ic_videocam"));
            }
        }
        if(v == mSplit){
            pindah(Tools.intId("mSplitButton"));
        }
        if(v == mTrim){
            pindah(Tools.intId("mTrimButton"));
        }
    }

    private void pindah(int id){
        isPicked = false;
        if(id == Tools.intId("mSplitButton")){
            startActivity(new Intent(this, SplitActivity.class));
        }else {
            startActivity(new Intent(this, PartActivity.class));
        }
    }

    private void addObserverForVideoView(){
        NotificationCenter.getInstance().addObserver(NotificationName.VIDEO_PATH_CHANGED, new Observer() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICKED_VIDEO) {
            onPickedVideo(resultCode, data);
        }
    }

    private void onPickedVideo(int resultCode, @Nullable Intent data){
        if(data!=null){
            if (resultCode != -1) {
                DataStorage.getInstance().setVideoPath(null);
            }else {
                isPicked = true;
                isHasVideo = true;
                DataStorage.getInstance().setVideoPath(data.getData());
                mSelectVideo.setImageResource(Tools.intDrawable("delta_ic_delete"));
                mVideoView.setVisibility(View.VISIBLE);
                mSplit.setOnClickListener(this);
                mTrim.setOnClickListener(this);
            }
            if (DataStorage.getInstance().getVideoPath() != null) {
                Log.d("MainActivity", "path null");
            }
        }else {

            DataStorage.getInstance().setVideoPath(null);
            Toast.makeText(this, "No video picked", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isPicked){
            mVideoView.setVisibility(View.GONE);
            isHasVideo = false;
            mSelectVideo.setImageResource(Tools.intDrawable("delta_ic_videocam"));
            mSplit.setEnabled(false);
            mTrim.setEnabled(false);
        }else {
            mSplit.setEnabled(true);
            mTrim.setEnabled(true);
        }
    }
}
