package id.delta.whatsapp.home.fragments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatsapp.ProfileInfoActivity;
import com.whatsapp.ThumbnailButton;

import id.delta.whatsapp.activities.MainActivity;
import id.delta.whatsapp.utils.Tools;

public class ProfileFragment extends HomePageFragment {

    public ProfileFragment() {
        mFragmentType = MainActivity.Fragment.PROFILE;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        if (mRootView != null)
            return mRootView;
        mHomeActivity = (MainActivity) layoutInflater.getContext();
        mRootView = layoutInflater.inflate(Tools.intLayout("delta_fragment_profile"), null);

        ThumbnailButton mAvatar = (ThumbnailButton)mRootView.findViewById(Tools.intId("mProfile"));
        mAvatar.setImageBitmap(mHomeActivity.pictureBitmap);
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeActivity.startActivity(new Intent(mHomeActivity, ProfileInfoActivity.class));
            }
        });
        TextView mInfo = (TextView)mRootView.findViewById(Tools.intId("mModder"));
        mInfo.setText(mHomeActivity.mInfo);
        return mRootView;
    }

}
