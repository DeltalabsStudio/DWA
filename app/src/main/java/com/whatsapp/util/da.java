package com.whatsapp.util;

import android.view.View;

public class da {

    public static Object a(){
        return null;
    }

    public static <T extends View> T a(T paramT)
    {
        if (paramT == null) {
            throw new NullPointerException();
        }
        return paramT;
    }
}
