package id.delta.whatsapp.implement;

import android.graphics.PorterDuff.Mode;
import android.support.v7.widget.ao;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.whatsapp.HomeActivity;

import id.delta.whatsapp.value.Main;

public class z38 implements Runnable {
    /* renamed from: a */
    private final HomeActivity f40a;
    String strCheck;
    String strColor;
    ViewGroup vg;

    public z38(HomeActivity a, ViewGroup viewg) {
        this.f40a = a;
        this.vg = viewg;
    }

    public void run() {
        try {
            ao LLC;
            if (this.vg.getChildAt(1) instanceof ao) {
                LLC = (ao) this.vg.getChildAt(1);
            } else {
                LLC = (ao) this.vg.getChildAt(2);
            }
            if ((this.vg.getChildAt(0) instanceof TextView)) {
                ((TextView) this.vg.getChildAt(0)).setTextColor(Main.titleColor());
            }
            for (int i = 0; i < LLC.getChildCount(); i++) {
                if (LLC.getChildAt(i) instanceof ImageView) {
                    ImageView img = (ImageView) LLC.getChildAt(i);
                    img.setColorFilter(Main.titleColor(), Mode.SRC_IN);
                }
            }
        } catch (Exception e) {
        }
    }
}
