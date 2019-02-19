package id.delta.whatsapp.implement;

import android.app.Activity;
import android.view.View;

public class ToolbarInterfaces implements View.OnClickListener {

    Activity activity;

    public ToolbarInterfaces (Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        activity.onBackPressed();
    }
}
