package id.delta.whatsapp.implement;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.EditText;

import com.whatsapp.TextStatusComposerActivity;

import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.views.colorpicker.AmbilWarnaDialog;

public class OnStatusColorPicker implements View.OnClickListener {
    int value;
    TextStatusComposerActivity activity;

    public OnStatusColorPicker(TextStatusComposerActivity context, int textColor){
        this.activity = context;
        this.value = textColor;
    }

    @Override
    public void onClick(View v) {
        new AmbilWarnaDialog(activity, value, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                try{
                    value = color;
                    activity.textColor = color;
                    EditText mEntry = activity.findViewById(Tools.intId("entry"));
                    mEntry.setTextColor(color);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {}
        }).show();
    }
}
