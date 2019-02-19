package id.delta.whatsapp.implement;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.whatsapp.data.ft;
import com.whatsapp.dw;

public class z3 implements OnClickListener {
    Activity activity;
    /* renamed from: c */
    boolean f41c;
    ft ej;
    dw gy;
    /* renamed from: m */
    Integer f42m;
    /* renamed from: z */
    boolean f43z;

    public z3(dw gy, ft ej, Activity activity, Integer m, boolean z, boolean c) {
        this.ej = ej;
        this.activity = activity;
        this.f42m = m;
        this.f43z = z;
        this.f41c = c;
        this.gy = gy;
    }

    public void onClick(DialogInterface arg0, int arg1) {
        this.gy.a(this.ej, this.activity, this.f42m, this.f43z, this.f41c);
    }
}
