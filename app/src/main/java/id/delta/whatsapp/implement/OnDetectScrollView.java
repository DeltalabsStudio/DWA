package id.delta.whatsapp.implement;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.whatsapp.HomeActivity;

import id.delta.whatsapp.ui.views.ListScrolView;
import id.delta.whatsapp.utils.Tools;

public class OnDetectScrollView implements ListScrolView.OnDetectScrollListener {
    Activity activity;

    public OnDetectScrollView(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onUpScrolling() {
        if(activity instanceof HomeActivity){
            View mTopLayout = activity.findViewById(Tools.intId("mStatusContainer"));
            mTopLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDownScrolling() {

    }

    @Override
    public void onBottomReached() {
        if(activity instanceof HomeActivity){
            View mTopLayout = activity.findViewById(Tools.intId("mStatusContainer"));
            mTopLayout.setVisibility(View.GONE);
        }
    }
}
