package com.whatsapp.wallpaper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import id.delta.whatsapp.R;

public class WallPaperView extends ImageView {
    public WallPaperView(Context context) {
        super(context);
        init();
    }

    public WallPaperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WallPaperView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){
      /*  Resources res = this.getResources();
        int id = Chat.chatWallpaper();
        Bitmap b = BitmapFactory.decodeResource(res, id);
        setImageBitmap(b);
        Chat.hideWallpaper(this);*/
    }
}
