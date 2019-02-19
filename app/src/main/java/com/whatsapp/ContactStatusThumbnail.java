package com.whatsapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


public class ContactStatusThumbnail extends ThumbnailButton {

    public ContactStatusThumbnail(Context context) {
        super(context);
    }

    public ContactStatusThumbnail(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactStatusThumbnail(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //setRingValues
    public void a(int unreadCount, int total) {

    }

}
