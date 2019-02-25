package id.delta.whatsapp.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class MarginAnimation extends Animation {
    private View mView;
    private int mTargetTopMargin;
    private int mTargetLeftMargin;

    private int mStartTopMargin;
    private int mStartLeftMargin;

    public MarginAnimation(View view, int targetTopMargin, int targetLeftMargin) {
        mView = view;
        mTargetTopMargin = targetTopMargin;
        mTargetLeftMargin = targetLeftMargin;

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mView.getLayoutParams();
        mStartTopMargin = params.topMargin;
        mStartLeftMargin = params.leftMargin;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        // I assume the view is inside a RelativeLayout. Change as required.
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mView.getLayoutParams();

        params.topMargin = (int)(mStartTopMargin + (mTargetTopMargin - mStartTopMargin) * interpolatedTime);
        params.leftMargin = (int)(mStartLeftMargin + (mTargetLeftMargin - mStartLeftMargin) * interpolatedTime);

        mView.setLayoutParams(params);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
