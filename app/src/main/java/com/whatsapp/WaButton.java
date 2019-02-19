package com.whatsapp;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

import id.delta.whatsapp.value.Colors;


public class WaButton extends AppCompatButton {
    public WaButton(Context context) {
        super(context);
        initview();
    }

    public WaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview();
    }

    public WaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview();
    }

    private void initview(){

    }
}
