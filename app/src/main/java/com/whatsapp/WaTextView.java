package com.whatsapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class WaTextView extends TextView {
    public WaTextView(Context context) {
        super(context);
    }

    public WaTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
