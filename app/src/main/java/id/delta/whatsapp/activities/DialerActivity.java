package id.delta.whatsapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toolbar;

import com.whatsapp.Conversation;
import com.whatsapp.DialogToastActivity;
import com.whatsapp.data.at;
import com.whatsapp.data.aw;
import com.whatsapp.dw;

import id.delta.whatsapp.implement.OnContinuousClickListener;
import id.delta.whatsapp.ui.views.FloatingActionButton;
import id.delta.whatsapp.utils.Call;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Themes;

public class DialerActivity extends BaseActivity implements View.OnClickListener {
    EditText mEditText;
    FloatingActionButton mPhoneCall, mVoiceCall, mVideoCall, mChat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Tools.intLayout("delta_dialer_activity"));

        mEditText = findViewById(Tools.intId("mEditText"));

        mPhoneCall = findViewById(Tools.intId("mPhoneCall"));
        mVoiceCall = findViewById(Tools.intId("mVoiceCall"));
        mVideoCall = findViewById(Tools.intId("mVideoCall"));
        mChat = findViewById(Tools.intId("mChat"));

        mPhoneCall.setColorNormal(Color.RED);
        mVoiceCall.setColorNormal(Color.RED);
        mVideoCall.setColorNormal(Color.RED);
        mChat.setColorNormal(Color.RED);
        mPhoneCall.setColorPressed(Color.RED);
        mVoiceCall.setColorPressed(Color.RED);
        mVideoCall.setColorPressed(Color.RED);
        mChat.setColorPressed(Color.RED);


        mPhoneCall.setOnClickListener(this);
        mVideoCall.setOnClickListener(this);
        mVoiceCall.setOnClickListener(this);
        mChat.setOnClickListener(this);

        FloatingActionButton mDelete = findViewById(Tools.intId("mDelete"));
        mDelete.setOnLongClickListener(new OnContinuousClickListener() {
            @Override
            public void onContinuousClick(View v) {
                deleteNumber();
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNumber();
            }
        });


    }

    public void inputNumber(View view) {
        mEditText.getText().insert(mEditText.getSelectionStart(), view.getTag().toString());
    }

    public void deleteNumber() {
        if (mEditText.getText().toString().length() != 0) {
            int i = mEditText.getSelectionEnd();
            if (i > 0) {
                mEditText.setText(mEditText.getText().delete(i - 1, i));
                mEditText.setSelection(i - 1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        String id = getResources().getResourceEntryName(v.getId());
        String editNumber = mEditText.getText().toString();
        if(editNumber.startsWith("0")){
            Tools.showToast("Please add number in International Format exe: 08 = +62");
        }else {
            String replace = editNumber.trim().replace("+", "").replace(" ", "").replace("-", "");
            StringBuilder number = new StringBuilder();
            if (!replace.isEmpty()) {
                number.append(replace);
                number.append("@s.whatsapp.net");
                try{
                    switch (id){
                        case "mPhoneCall":
                            Intent i = new Intent("android.intent.action.DIAL");
                            i.setData(Uri.parse("tel:" + mEditText.getText().toString()));
                            startActivity(i);
                            break;
                        case "mVideoCall":
                            Call.CallDialog(dw.a(), aw.a().a(number.toString()), this, 8, false, true);
                            break;
                        case "mVoiceCall":
                            Call.CallDialog(dw.a(), aw.a().a(number.toString()), this, 8, false, false);
                            break;
                        case "mChat":
                            startActivity(Conversation.a(this, number.toString()));
                            break;

                    }

                    // Tools.showToast(number.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    public void a(View v) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
