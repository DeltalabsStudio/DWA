package id.delta.whatsapp.home.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.delta.whatsapp.activities.MainActivity;
import id.delta.whatsapp.utils.Tools;

public class CallsFragment extends HomePageFragment {

    public com.whatsapp.CallsFragment mStockCallsFragment;

    public CallsFragment() {
        mFragmentType = MainActivity.Fragment.CALLS;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        if (mRootView != null)
            return mRootView;
        mHomeActivity = (MainActivity) layoutInflater.getContext();
        mStockCallsFragment = new com.whatsapp.CallsFragment();
        mRootView = layoutInflater.inflate(Tools.intLayout("delta_fragment_calls"), null);
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().add(Tools.intId("fragment"), mStockCallsFragment).commit();
        }
        return mRootView;

    }

}
