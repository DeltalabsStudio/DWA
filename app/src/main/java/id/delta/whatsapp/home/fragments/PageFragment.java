package id.delta.whatsapp.home.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import id.delta.whatsapp.activities.MainActivity;

public class PageFragment extends BaseFragment implements View.OnClickListener {

    public MainActivity mHomeActivity;
    public View mRootView;
    public HomePageFragment mHomePageFragment;
    public int mFrameLayoutId = 0;
    public boolean mLightStatusBar = true;

    private boolean mOnResumeCalled = false;

    public static PageFragment create(HomePageFragment homePageFragment) {
        PageFragment pageFragment = new PageFragment();
        pageFragment.mHomePageFragment = homePageFragment;
        pageFragment.mHomeActivity = homePageFragment.mHomeActivity;
        return pageFragment;
    }

    @Override
    public final void onResume() {
        super.onResume();

        if (!mOnResumeCalled) {
            onPageResume();
            mOnResumeCalled = true;
        }
    }

    public void onPageResume() {
      //  mHomeActivity.setLightStatusBar(mLightStatusBar);
    }

    public void destroy() {
        FrameLayout frameLayout = getFrameLayout();
        if (frameLayout != null) {
            mHomePageFragment.mContent.removeView(getFrameLayout());
        }
        mHomePageFragment.getChildFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return super.onCreateView(layoutInflater, viewGroup);
    }

    public FrameLayout getFrameLayout() {
        View view = getView();
        return view != null? (FrameLayout) getView().getParent() : null;
    }

    @Deprecated
    public void close(boolean animated) {
        mHomePageFragment.closeOpenedPage();
    }

    @Override
    public void onClick(View view) {
        close(true);
    }

    public class NavigationClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mHomePageFragment.closeOpenedPage();
        }
    }

}
