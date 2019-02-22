package id.delta.whatsapp.implement;

import android.view.View;

import com.whatsapp.HomeActivity;

public class NewChatListener implements View.OnClickListener {

    HomeActivity mHome;

    public NewChatListener(HomeActivity mHome){
        this.mHome = mHome;
    }

    @Override
    public void onClick(View v) {
        mHome.createNew();
    }
}
