package android.support.design.widget;

import android.content.Context;
import android.support.annotation.NonNull;

public class c extends BottomSheetDialog {
    public c(@NonNull Context context) {
        super(context);
    }

    public c(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected c(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
