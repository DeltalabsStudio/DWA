package com.whatsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class MentionableEntry extends EditText {

    public MentionableEntry(Context context) {
        super(context);
    }

    public MentionableEntry(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MentionableEntry(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
