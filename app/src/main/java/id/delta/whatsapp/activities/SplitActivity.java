package id.delta.whatsapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import id.delta.whatsapp.R;
import id.delta.whatsapp.split.presenter.NotificationCenter;
import id.delta.whatsapp.split.presenter.NotificationName;
import id.delta.whatsapp.split.service.DataStorage;
import id.delta.whatsapp.split.service.Splitter;
import id.delta.whatsapp.split.service.SplitterDelegate;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;


public class SplitActivity extends BaseActivity implements SplitterDelegate {

    Button mSplitButton;
    EditText mSplitDuration;
    TextView mPartValue;
    MediaMetadataRetriever mRetrieverMetadata;
    Double mDoublePart = 0.0d;
    boolean mPathSelected = false;
    final ArrayList<Uri> mUriList = new ArrayList();
    int mPart = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Tools.intLayout("delta_split_activity"));
        Toolbar mToolbar = findViewById(Tools.intId("mToolbar"));
        setToolbar(mToolbar);

        mSplitButton = findViewById(Tools.intId("mSplitButton"));
        mSplitDuration = findViewById(Tools.intId("mSplitDuration"));
        mPartValue = findViewById(Tools.intId("mPartValue"));
        addObserverForVideoView();

        mRetrieverMetadata = new MediaMetadataRetriever();
        mPathSelected = getIntent().getBooleanExtra("Selected", false);
        if (this.mPathSelected) {
            this.mRetrieverMetadata.setDataSource(this, Uri.parse(DataStorage.getInstance().getSelectedVideoPath()));
        } else {
            this.mRetrieverMetadata.setDataSource(this, DataStorage.getInstance().getVideoPath());
        }
        String extractMetadata = this.mRetrieverMetadata.extractMetadata(9);
        this.mRetrieverMetadata.release();
        this.mDoublePart = Double.parseDouble(extractMetadata);
        mSplitDuration.setText(String.valueOf(DataStorage.getInstance().getSplitDuration()));
        updateUIWithTextFieldData(String.valueOf(DataStorage.getInstance().getSplitDuration()));

        mSplitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SplitVideo().execute();
            }
        });

        mSplitDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateUIWithTextFieldData(s.toString());
            }
        });

    }

    private void updateUIWithTextFieldData(String str) {
        try {
            int parseInt = Integer.parseInt(str);
            if (((double) parseInt) >= mDoublePart / 1000.0d || parseInt <= 0) {
                mSplitDuration.setError("Invalid Duration or Duration greater than input video length");
                mSplitButton.setEnabled(false);
                mPartValue.setText("");
                return;
            }
            DataStorage.getInstance().setSplitDuration(parseInt);
            mSplitButton.setEnabled(true);
            mPart = ((int) (mDoublePart / 1000.0d)) / parseInt;
            if ((mDoublePart / 1000.0d) / ((double) parseInt) > ((double) this.mPart)) {
                mPart++;
            }
            mPartValue.setText("Approx " + mPart + " Parts");
        } catch (Exception e) {
            e.printStackTrace();
            mSplitDuration.setText("0");
        }
    }

    private void addObserverForVideoView() {
        NotificationCenter.getInstance().addObserver(NotificationName.VIDEO_PATH_CHANGED, new Observer() {
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isProses(false);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND_MULTIPLE");
                intent.putExtra("android.intent.extra.SUBJECT", "Status Video From Video Splitter");
                intent.setType("video/*");
                intent.putParcelableArrayListExtra("android.intent.extra.STREAM", mUriList);
                startActivity(intent);
            }
        });
    }

    @Override
    public void progress(final int i, final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUriList.add(Uri.parse(str));
                mSplitButton.setText("Progress " + i + "/" + mPart);
            }
        });
    }

    @Override
    public void started() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isProses(true);
            }
        });
    }

    private void isProses(boolean proses){
        if(proses){
            mSplitButton.setEnabled(false);
            mSplitButton.setText("Started");
            mSplitButton.setTextColor(Colors.warnaFab());
        }else {
            mSplitButton.setEnabled(true);
            mSplitButton.setText("Finished");
            Drawable drawable = mSplitButton.getBackground();
            if(drawable!=null){
                drawable.setColorFilter(Colors.warnaFab(), PorterDuff.Mode.MULTIPLY);
                mSplitButton.setBackground(drawable);
            }
            mSplitButton.setTextColor(Colors.warnaAutoIconFab());
        }
    }

    @SuppressLint("StaticFieldLeak")
    class SplitVideo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                new Splitter(SplitActivity.this, SplitActivity.this).split(VideoActivity.mPartLocation, mDoublePart.doubleValue(), mPathSelected);
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Unable splitting selected video", Toast.LENGTH_LONG).show();
                    }
                });
                finish();
            }
            return null;
        }
    }
}
