package id.delta.whatsapp.ui.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.whatsapp.WaButton;

import java.io.File;

import id.delta.whatsapp.utils.CopyTask;

public class RestoreButton extends WaButton implements View.OnClickListener {
    public RestoreButton(Context context) {
        super(context);
        init();
    }

    public RestoreButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RestoreButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOnClickListener(this);
    }

    public void onClick(final View view) {
        try{
            if (new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup").exists() && new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/"+getContext().getPackageName()).exists()) {
                new CopyTask(getContext(), false, new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/"+getContext().getPackageName()), new File(Environment.getDataDirectory(), "data/"+getContext().getPackageName())).execute(new File[0]);
            } else {
                Toast.makeText(getContext(), "Can't find a backup in '/sdcard/WhatsApp/DELTABackup'!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
