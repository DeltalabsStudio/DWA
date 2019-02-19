package com.whatsapp;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import id.delta.whatsapp.value.Colors;


public class WaImageButton extends AppCompatImageButton {
    public WaImageButton(Context context) {
        super(context);
        initview();
    }

    public WaImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview();
    }

    public WaImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview();
    }

    private void initview(){
        try{
            this.setColorFilter(Colors.setWarnaAksen(), PorterDuff.Mode.SRC_ATOP);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
