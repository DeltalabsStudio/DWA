package id.delta.whatsapp.implement;

import android.support.v4.view.ViewPager;

import com.whatsapp.HomeActivity;

public class PageListener {

    HomeActivity mHome;
    ViewPager mPager;

    public PageListener(HomeActivity homeActivity, ViewPager mPager){
       this.mHome = homeActivity;
       this.mPager = mPager;
    }

    public void onSelectPage(){
        mPager.setOnPageChangeListener(new onPageListener());
    }

    public class onPageListener extends ViewPager.SimpleOnPageChangeListener{

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if(position == 0){
                mHome.mCurvedNavigation.onStartSelected();
            }else if(position == 1){
                mHome.mCurvedNavigation.onStartSelected();
            }else if(position == 2) {
                mHome.mCurvedNavigation.onCenterSelected();
            }else if(position == 3){
                mHome.mCurvedNavigation.onEndSelected();
            }
        }
    }
}
