package id.delta.whatsapp.home.stock;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.whatsapp.HomeActivity;

import id.delta.whatsapp.dialog.DialogAdd;
import id.delta.whatsapp.home.content.BottomNavigationItemView;
import id.delta.whatsapp.utils.Tools;

public class NavigationView extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener, View.OnClickListener {

    // Properties

    private Context mContext;
    private HomeActivity mHomeActivity;
    private BottomNavigationItemView mHomeItem, mCallsItem, mMenuItem, mCreateItem;

    // Constructors

    public NavigationView(Context context) {
        super(context);
        init(context);
    }

    public NavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    // Methods

    private void init(Context context) {
        this.mContext = context;
        this.mHomeActivity = (HomeActivity) context;
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {

        mHomeItem = findViewById(Tools.intId("bnv_chats"));
        mCallsItem = findViewById(Tools.intId("bnv_calls"));
        mMenuItem = findViewById(Tools.intId("bnv_menu"));
        mCreateItem = findViewById(Tools.intId("bnv_create"));

        mHomeItem.setOnClickListener(this);
        mCallsItem.setOnClickListener(this);
        mCreateItem.setOnClickListener(this);

        mMenuItem.setOnClickListener(this);

        setItemActive(mHomeItem);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onClick(View view) {
        BottomNavigationItemView item = (BottomNavigationItemView) view;
        if (view == mHomeItem) {
            mHomeActivity.selectPage(1);
            setItemActive(mHomeItem);
        } else if (view == mCallsItem) {
            mHomeActivity.selectPage(3);
            setItemActive(mCallsItem);
        } else if(view == mMenuItem){
            mHomeActivity.mNavigationDrawer.setOpen(true);
        } else if(view == mCreateItem){
            mHomeActivity.createDialog();
        }

        else {
            Toast.makeText(mContext, "Unknown state.", Toast.LENGTH_LONG).show();
        }
    }

    private void setItemActive(BottomNavigationItemView item) {
        mHomeItem.setActive(mHomeItem == item);
        mCallsItem.setActive(mCallsItem == item);
    }

}
