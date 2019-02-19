package com.whatsapp.contact.a;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.whatsapp.AppShell;
import com.whatsapp.data.ft;

import id.delta.whatsapp.R;

//Picture
public class d {

    //getPicture
    public static d a() {
        return new d();
    }

    //getBitmap
    public Bitmap a(ft contactInfo, int pixelSize, float idk, boolean idk2) {

        Drawable myDrawable = null;

        switch (contactInfo.mJabberId) {
            case "":
                myDrawable = AppShell.ctx.getDrawable(R.drawable.delta_ic_profile);
                break;
            case "someone":
                myDrawable = AppShell.ctx.getDrawable(R.drawable.avatar_contact);
        }

        if (myDrawable != null) {
            return ((BitmapDrawable) myDrawable).getBitmap();
        }

        return null;
    }
}
