package id.delta.whatsapp.implement;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.whatsapp.TextStatusComposerActivity;

import id.delta.whatsapp.views.colorpicker.AmbilWarnaDialog;

public class OnCustomPicker implements View.OnClickListener {
    int value;
    TextStatusComposerActivity activity;

    public OnCustomPicker(TextStatusComposerActivity context, int p){
        this.activity = context;
        this.value = p;
    }

    @Override
    public void onClick(View v) {
        new AmbilWarnaDialog(activity, value, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                try{
                    value = color;
                    activity.getWindow().setBackgroundDrawable(new ColorDrawable(value));
                    activity.p = color;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {}
        }).show();
    }
}
