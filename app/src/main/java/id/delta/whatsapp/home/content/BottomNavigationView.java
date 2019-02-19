package id.delta.whatsapp.home.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.whatsapp.HomeActivity;
import com.whatsapp.TextStatusComposerActivity;
import com.whatsapp.camera.CameraActivity;

import id.delta.whatsapp.activities.MainActivity;
import id.delta.whatsapp.ui.views.FloatingActionButton;
import id.delta.whatsapp.utils.Tools;

public class BottomNavigationView extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener, View.OnClickListener, View.OnLongClickListener {

    // Properties

    private Context mContext;
    private MainActivity mHomeActivity;
    private BottomNavigationItemView mHomeItem, mCallsItem, mMenuItem, mCreateItem;
    private FloatingActionButton mCameraItem;

    // Constructors

    public BottomNavigationView(Context context) {
        super(context);
        init(context);
    }

    public BottomNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    // Methods

    private void init(Context context) {
        this.mContext = context;
        this.mHomeActivity = (MainActivity) context;
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {

        mHomeItem = findViewById(Tools.intId("bnv_chats"));
        mCallsItem = findViewById(Tools.intId("bnv_calls"));
        mCameraItem = findViewById(Tools.intId("bnv_camera"));
        mMenuItem = findViewById(Tools.intId("bnv_menu"));
        mCreateItem = findViewById(Tools.intId("bnv_create"));

        mCameraItem.setImageResource(Tools.intDrawable("ic_camera_status_compose"));

        mHomeItem.setOnClickListener(this);
        mCallsItem.setOnClickListener(this);
        mCreateItem.setOnClickListener(this);
        mCameraItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CameraActivity.class);
                mContext.startActivity(intent);
            }
        });
        mMenuItem.setOnClickListener(this);

        mHomeItem.setOnLongClickListener(this);
        mCameraItem.setOnLongClickListener(this);

        setItemActive(mHomeItem);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onClick(View view) {

        BottomNavigationItemView item = (BottomNavigationItemView) view;

        if (view == mHomeItem) {
            mHomeActivity.showFragment(MainActivity.Fragment.HOME);
            mHomeActivity.mToolbar.setVisibility(VISIBLE);
            setItemActive(item);
        } else if (view == mCallsItem) {
            mHomeActivity.showFragment(MainActivity.Fragment.CALLS);
            mHomeActivity.mToolbar.setVisibility(VISIBLE);
            setItemActive(item);
        } else if(view == mMenuItem){
            mHomeActivity.mNavigationDrawer.setOpen(true);
        } else if(view == mCreateItem){
            mHomeActivity.createDialog();
        }

        else {
            Toast.makeText(mContext, "Unknown state.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view == mHomeItem) {
            Intent intent = new Intent(mContext, HomeActivity.class);
            intent.putExtra("delta_home", true);
            mContext.startActivity(intent);
        } else if (mCameraItem == view) {
            Intent intent = new Intent(mContext, TextStatusComposerActivity.class);
            mContext.startActivity(intent);
        }

        return false;
    }

    public void setItemActive(MainActivity.Fragment fragmentButton) {
        switch (fragmentButton) {
            case HOME:
                setItemActive(mHomeItem);
                break;
            case CALLS:
                setItemActive(mCallsItem);
                break;
        }
    }

    private void setItemActive(BottomNavigationItemView item) {
        mHomeItem.setActive(mHomeItem == item);
        mCallsItem.setActive(mCallsItem == item);
    }

}
