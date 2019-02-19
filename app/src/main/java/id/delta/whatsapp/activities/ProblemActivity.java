package id.delta.whatsapp.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;

public class ProblemActivity extends BaseActivity implements View.OnClickListener {

    String error = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Tools.intLayout("delta_problem_activity"));

        Toolbar mToolbar = findViewById(Tools.intId("mToolbar"));
        setToolbar(mToolbar);

        TextView mErrorView = findViewById(Tools.intId("mErrorView"));
        error = getIntent().getStringExtra(Keys.KEY_PROBLEM);
        mErrorView.setText(error);

        Button mReset = findViewById(Tools.intId("mReset"));
        mBackground(mReset);
        mReset.setOnClickListener(this);

        Button mReport = findViewById(Tools.intId("mReport"));
        mBackground(mReport);
        mReport.setOnClickListener(this);

        Button mChat = findViewById(Tools.intId("mChat"));
        mBackground(mChat);
        mChat.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        Actions.restartApp();
    }

    private void mBackground(View view){
        Drawable mBackground = view.getBackground();
        mBackground.setColorFilter(Colors.setWarnaAksen(), PorterDuff.Mode.MULTIPLY);
        view.setBackgroundDrawable(mBackground);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==Tools.intId("mReset")){
            Prefs.clear();
            Actions.restartApp();
        }else if(v.getId()==Tools.intId("mReport")){
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"deltalabsreport@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, Tools.getString("delta_version"));
            i.putExtra(Intent.EXTRA_TEXT   , error);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(ProblemActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId()==Tools.intId("mChat")){
            Tools.showToast("Anda tidak diijinkan mengirim pesan whatsapp ke Developer");
        }
    }
}
