package com.whatsapp;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

import id.delta.whatsapp.value.Colors;


public class WaImageView extends AppCompatImageView {
    public WaImageView(Context context) {
        super(context);
        initview();
    }

    public WaImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview();
    }

    public WaImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
