package com.whatsapp.util;

import android.os.SystemClock;
import android.view.View;

public abstract class cv implements View.OnClickListener {

    private long a;

    public abstract void a(View var1);

    public void onClick(View view) {
        long l2 = SystemClock.elapsedRealtime();
        if (l2 - this.a > 1000L) {
            this.a = l2;
            this.a(view);
        }
    }
}
