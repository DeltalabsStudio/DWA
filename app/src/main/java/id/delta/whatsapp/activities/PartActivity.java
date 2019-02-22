package id.delta.whatsapp.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import id.delta.whatsapp.R;
import id.delta.whatsapp.split.service.DataStorage;
import id.delta.whatsapp.split.service.DateUtil;
import id.delta.whatsapp.split.service.Splitter;
import id.delta.whatsapp.split.service.SplitterDelegate;
import id.delta.whatsapp.ui.views.PlayVideoView;
import id.delta.whatsapp.ui.views.SeekBarRangedView;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;

public class PartActivity extends BaseActivity {

    MediaMetadataRetriever mRetrieverMetadata;
    PlayVideoView mVideoVew;
    TextView mCurrentTime;
    TextView mMaxTime;
    TextView mMinTime;
    SeekBarRangedView mRangeSeek;
    Button mSplitButton;
    Thread mThread;
    long x = 0;
    long y = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Tools.intLayout("delta_part_activity"));
        mVideoVew = findViewById(Tools.intId("mVideoView"));
        mCurrentTime = findViewById(Tools.intId("mCurrentTime"));
        mMaxTime = findViewById(Tools.intId("maxTime"));
        mMinTime = findViewById(Tools.intId("minTime"));
        mRangeSeek = findViewById(Tools.intId("mRangeSeek"));
        mSplitButton = findViewById(Tools.intId("mSplitButton"));

        mRetrieverMetadata = new MediaMetadataRetriever();
        mRetrieverMetadata.setDataSource(this, DataStorage.getInstance().getVideoPath());
        String extractMetadata = mRetrieverMetadata.extractMetadata(9);
        mRetrieverMetadata.release();

        Toolbar mToolbar = findViewById(Tools.intId("mToolbar"));
        setToolbar(mToolbar);

        mVideoVew.setVideoURI(DataStorage.getInstance().getVideoPath());
        startTimerThread();

        mRangeSeek.setMinValue(0.0f);
        mRangeSeek.setMaxValue(100.0f);
        mRangeSeek.setOnSeekBarRangedChangeListener(new SeekBarRangedView.OnSeekBarRangedChangeListener() {
            @Override
            public void onChanged(SeekBarRangedView view, float minValue, float maxValue) {
                int i = (int) ((((double) (y / 1000)) * minValue) / 100.0d);
                mVideoVew.seekTo(i * 1000);
                if (mThread.getState() == Thread.State.TERMINATED) {
                    startTimerThread();
                }
                mVideoVew.start();
                x = (long) ((int) ((((double) (y / 1000)) * maxValue) / 100.0d));
            }

            @Override
            public void onChanging(SeekBarRangedView view, float minValue, float maxValue) {

            }
        });
        y = Long.parseLong(extractMetadata);
        mMaxTime.setText(DateUtil.getInstance().timeConversion(this.y / 1000));

        Drawable drawable = mSplitButton.getBackground();
        if(drawable!=null){
            drawable.setColorFilter(Colors.warnaFab(), PorterDuff.Mode.MULTIPLY);
            mSplitButton.setBackground(drawable);
        }
        mSplitButton.setTextColor(Colors.warnaAutoIconFab());
        mSplitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TrimVideo().execute();
            }
        });

    }

    private void startTimerThread() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mVideoVew.getStatus() == PlayVideoView.Status.Playing) {
                    try {
                        if ((y / 1000 > mVideoVew.getSecondsPlayed() || y <= 0) && (x > mVideoVew.getSecondsPlayed() || x <= 0)) {
                            Thread.sleep(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mCurrentTime.setText(DateUtil.getInstance().timeConversion(mVideoVew.getSecondsPlayed()));
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mVideoVew.pause();
                                }
                            });
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        });
        mVideoVew.start();
        mThread.start();
    }

    @SuppressLint("StaticFieldLeak")
    class TrimVideo extends AsyncTask<Void, Void, Void> {
        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(PartActivity.this);
            mDialog.setCancelable(false);
            mDialog.setMessage("Please wait, processing trim the video");
            mDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            final String mSelectedFile = VideoActivity.mPartLocation + "Selected.mp4";
            try {
                new Splitter(PartActivity.this, new SplitterDelegate() {
                    public void completed() {
                        DataStorage.getInstance().setSelectedVideoPath(mSelectedFile);
                        Intent intent = new Intent(getBaseContext(), SplitActivity.class);
                        intent.putExtra("Selected", true);
                        startActivity(intent);
                        finish();
                    }

                    public void progress(int i, String str) {

                    }

                    public void started() {
                    }
                }).trim(VideoActivity.mPartLocation, mSelectedFile, ((int) ((mRangeSeek.getSelectedMinValue() * ((double) (y / 1000))) / 100.0d)) * 1000, (int) (x * 1000));
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Unable trimming selected video", Toast.LENGTH_LONG).show();
                    }
                });

                finish();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mDialog.dismiss();
        }
    }

}
