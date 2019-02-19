package id.delta.whatsapp.ui.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.whatsapp.WaButton;

import java.io.File;

import id.delta.whatsapp.utils.CopyTask;

public class PermissionButton extends WaButton implements View.OnClickListener {
    public PermissionButton(Context context) {
        super(context);
        init();
    }

    public PermissionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PermissionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOnClickListener(this);
    }

    public void onClick(final View view) {
        try{
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
            intent.setData(uri);
            getContext().startActivity(intent);
            Toast.makeText(getContext(), "Please allow Storage Permission!", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
