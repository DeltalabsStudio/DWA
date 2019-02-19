package id.delta.whatsapp.home.fragments;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatsapp.StatusesFragment;
import com.whatsapp.camera.CameraActivity;
import com.whatsapp.data.ew;
import com.whatsapp.statusplayback.StatusPlaybackActivity;

import java.util.ArrayList;

import id.delta.whatsapp.activities.MainActivity;
import id.delta.whatsapp.utils.Tools;

public class ConversationsFragment extends HomePageFragment{
    RecyclerView mStatusesRecyclerView;
    StatusesFragment mStockStatusesFragment;
    StatusesAdapter mStatusesAdapter;

    public static NestedScrollView mTopView;

    com.whatsapp.ConversationsFragment mStockConversationsFragment;

    public ConversationsFragment() {
        mFragmentType = MainActivity.Fragment.HOME;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        if (mRootView != null)
            return mRootView;

        mHomeActivity = (MainActivity) layoutInflater.getContext();
        mRootView = layoutInflater.inflate(Tools.intLayout("delta_fragment_conversations"), null);

        // Stock Statuses fragment
        mStockStatusesFragment = new StatusesFragment();
        mHomeActivity.getSupportFragmentManager().beginTransaction().replace(Tools.intId("stock_statuses_fragment"), mStockStatusesFragment).commit();
        mStatusesAdapter = new StatusesAdapter(mHomeActivity, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatusesFragment.a status = getStatusesDataSet().get(mStatusesRecyclerView.getChildAdapterPosition(view));

                if (status instanceof StatusesFragment.f) {
                    ew statusInfo = ((StatusesFragment.f) status).b;

                    Intent intent;

                    if (statusInfo.a.contentEquals("") && statusInfo.j == 0) {
                        intent = new Intent(mHomeActivity, CameraActivity.class);
                    } else {
                        intent = new Intent(mHomeActivity, StatusPlaybackActivity.class);
                        intent.putExtra("jid", statusInfo.a);
                    }

                    mHomeActivity.startActivity(intent);
                }
            }
        });
        mStatusesRecyclerView = mRootView.findViewById(Tools.intId("statuses_recyclerview"));
        mStatusesRecyclerView.setLayoutManager(new LinearLayoutManager(mHomeActivity, LinearLayoutManager.HORIZONTAL, false));
        mStatusesRecyclerView.setAdapter(mStatusesAdapter);

        stockConversationsFragment();
        mTopView = mRootView.findViewById(Tools.intId("mNestedScroll"));

        return mRootView;

    }

    private void stockConversationsFragment(){
        mStockConversationsFragment = new com.whatsapp.ConversationsFragment();
        mHomeActivity.getSupportFragmentManager().beginTransaction().replace(Tools.intId("fragment"), mStockConversationsFragment).commit();

    }

    public void statusesDataSetChanged() {
        mStatusesAdapter.notifyDataSetChanged();
    }

    public ArrayList<StatusesFragment.a> getStatusesDataSet() {
        ArrayList<StatusesFragment.a> array = mStockStatusesFragment.ap;
        return array;
    }

}
