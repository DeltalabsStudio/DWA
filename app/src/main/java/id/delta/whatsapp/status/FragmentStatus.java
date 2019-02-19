package id.delta.whatsapp.status;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatsapp.HomeActivity;
import com.whatsapp.StatusesFragment;
import com.whatsapp.camera.CameraActivity;
import com.whatsapp.data.ew;
import com.whatsapp.statusplayback.StatusPlaybackActivity;

import java.util.ArrayList;

import id.delta.whatsapp.activities.MainActivity;
import id.delta.whatsapp.home.fragments.BaseFragment;
import id.delta.whatsapp.home.fragments.StatusesAdapter;
import id.delta.whatsapp.utils.RTLUtils;
import id.delta.whatsapp.utils.Tools;

public class FragmentStatus extends BaseFragment {

    RecyclerView mStatusesRecyclerView;
    StatusesFragment mStockStatusesFragment;
    AdapterStatus mStatusesAdapter;
    View mRootView;
    HomeActivity mHomeActivity;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        mHomeActivity = (HomeActivity) layoutInflater.getContext();
        mRootView = layoutInflater.inflate(Tools.intLayout("delta_fragment_status"), null);
        mStockStatusesFragment = new StatusesFragment();
        mHomeActivity.getSupportFragmentManager().beginTransaction().replace(Tools.intId("stock_statuses_fragment"), mStockStatusesFragment).commit();

        mStatusesAdapter = new AdapterStatus(mHomeActivity, new View.OnClickListener() {
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

      /*  if(RTLUtils.isRTL()){
            LinearLayoutManager layoutManager = new LinearLayoutManager(mHomeActivity, LinearLayoutManager.HORIZONTAL, true);
            layoutManager.setReverseLayout(true);
            mStatusesRecyclerView.setLayoutManager(layoutManager);
        }else {
            mStatusesRecyclerView.setLayoutManager(new LinearLayoutManager(mHomeActivity, LinearLayoutManager.HORIZONTAL, false));

        }*/

        mStatusesRecyclerView.setLayoutManager(new LinearLayoutManager(mHomeActivity, LinearLayoutManager.HORIZONTAL, false));
        mStatusesRecyclerView.setAdapter(mStatusesAdapter);

        return mRootView;
    }

    public void statusesDataSetChanged() {
        mStatusesAdapter.notifyDataSetChanged();
    }

    public ArrayList<StatusesFragment.a> getStatusesDataSet() {
        ArrayList<StatusesFragment.a> array = mStockStatusesFragment.ap;
        return array;
    }
}
