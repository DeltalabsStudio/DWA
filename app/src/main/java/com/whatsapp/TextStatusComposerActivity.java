package com.whatsapp;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.whatsapp.util.da;

import id.delta.whatsapp.R;
import id.delta.whatsapp.implement.OnCustomPicker;
import id.delta.whatsapp.implement.OnStatusColorPicker;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.FancyText;
import id.delta.whatsapp.value.Themes;
import id.delta.whatsapp.views.colorpicker.AmbilWarnaDialog;

public class TextStatusComposerActivity extends DialogToastActivity {

    public int p = Colors.setWarnaPrimer();
    public ImageButton s;
    public int textColor = Colors.warnaPutih;
    public boolean isStyle;
    public int style;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Themes.setHomeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_status_composer);

        if (savedInstanceState == null) {
            p = Colors.setWarnaPrimer();
            h();
        }

    }

    private void k(){

    }
    private void h(){
        customColor();
    }

    private void customColor(){
        View mCustom = findViewById(Tools.intId("mCustomColor"));
        mCustom.setOnClickListener(new OnCustomPicker(this, p));

        View mTextButton = findViewById(Tools.intId("mTextColor"));
        mTextButton.setOnClickListener(new OnStatusColorPicker(this, textColor));

        View mTextStyle = findViewById(Tools.intId("mTextStyle"));
        mTextStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FancyText.onStatusClicked(TextStatusComposerActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(p);
        }
        getWindow().setBackgroundDrawable(new ColorDrawable(p));
        super.onResume();
    }
}
