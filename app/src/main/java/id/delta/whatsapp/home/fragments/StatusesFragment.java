package id.delta.whatsapp.home.fragments;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;

import com.whatsapp.HomeActivity;

import id.delta.whatsapp.activities.MainActivity;

public class StatusesFragment extends ListFragment {

    public void onNotifyDataSetChanged() {
        FragmentActivity activity = getActivity();

        if (activity instanceof MainActivity) {
            ((MainActivity) activity).mConversationsFragment.statusesDataSetChanged();
        }

        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).mStatusFragment.statusesDataSetChanged();
        }
    }



}
