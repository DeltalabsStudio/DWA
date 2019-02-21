package id.delta.whatsapp.implement;

import android.view.View;

import com.whatsapp.HomeActivity;

import id.delta.whatsapp.utils.Tools;

public class OnPageSelected {
    HomeActivity mHome;
    int mPosisi;

    public OnPageSelected(HomeActivity mHome, int mPosisi){
        this.mHome = mHome;
        this.mPosisi = mPosisi;
    }

    public void onPageListener(){
        View mTopLayout = mHome.findViewById(Tools.intId("mTopLayout"));
        if(mPosisi == 0){
            mTopLayout.setVisibility(View.GONE);
            mHome.mCurvedNavigation.setVisibility(View.GONE);
            mHome.mCurvedNavigation.onStartSelected();
        }else {
            mTopLayout.setVisibility(View.VISIBLE);
            mHome.mCurvedNavigation.setVisibility(View.VISIBLE);
            if(mPosisi == 1){
                mHome.mCurvedNavigation.onStartSelected();
            }else if(mPosisi == 2) {
                mHome.mCurvedNavigation.onCenterSelected();
            }else if(mPosisi == 3){
                mHome.mCurvedNavigation.onEndSelected();
            }
        }
    }
}
