package id.delta.whatsapp.home.stock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatsapp.About;
import com.whatsapp.ArchivedConversationsActivity;
import com.whatsapp.HomeActivity;
import com.whatsapp.ListMembersSelector;
import com.whatsapp.NewGroup;
import com.whatsapp.StarredMessagesActivity;
import com.whatsapp.WebSessionsActivity;

import id.delta.whatsapp.activities.PrivacyActivity;
import id.delta.whatsapp.activities.SettingsActivity;
import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.value.Main;


public class NavigationDrawerItem extends RelativeLayout implements View.OnClickListener {

    private HomeActivity mHomeActivity;
    private ImageView mIcon;
    private TextView mLabel;
    private Drawable mIconDrawable;
    private String mLabelString;

    public NavigationDrawerItem(Context context) {
        super(context);
        init(context, null, 0);
    }

    public NavigationDrawerItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public NavigationDrawerItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NavigationDrawerItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    @SuppressLint("ResourceType")
    private void init(Context context, AttributeSet attributeSet, int defStyleAttr) {
        mHomeActivity = (HomeActivity) context;

        if (attributeSet != null) {
            int[] attrs = {getID("icon", "attr"), getID("label", "attr")};
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attributeSet,
                    attrs,
                    defStyleAttr, 0);
            try {
                mLabelString = a.getString(1);
                mIconDrawable = a.getDrawable(0);
            } finally {
                a.recycle();
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = LayoutInflater.from(mHomeActivity).inflate(getID("delta_home_drawer_item", "layout"), null);
        addView(view);

        setClickable(true);
        setFocusable(true);

        mIcon = findViewById(getID("icon", "id"));
        mLabel = findViewById(getID("label", "id"));

        mIcon.setImageDrawable(mIconDrawable);
        mLabel.setText(mLabelString);
        mLabel.setTextColor(Main.drawerLabel());

        setGravity(Gravity.CENTER_VERTICAL);

        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = mHomeActivity.getResources().getResourceEntryName(view.getId());
        try{
            switch (name){
                case "archived_chats":
                    mHomeActivity.startActivity(new Intent(mHomeActivity, ArchivedConversationsActivity.class));
                    break;
                case "starred_messages":
                    mHomeActivity.startActivity(new Intent(mHomeActivity, StarredMessagesActivity.class));
                    break;
                case "whatsapp_web":
                    mHomeActivity.startActivity(new Intent(mHomeActivity, WebSessionsActivity.class));
                    break;
                case "nav_group":
                    NewGroup.a(mHomeActivity, 2, null);
                    break;
                case "nav_broadcast":
                    Actions.startActivity(mHomeActivity, ListMembersSelector.class);
                    break;
                case "nav_settings":
                    Actions.startSettings(mHomeActivity, SettingsActivity.STOCK);
                    break;
                case "nav_about":
                    mHomeActivity.startActivity(new Intent(mHomeActivity, About.class));
                    break;
                case "nav_privacy":
                    Actions.startActivity(mHomeActivity, PrivacyActivity.class);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        closeDrawer();

    }

    private void closeDrawer() {
        View view = ((Activity) mHomeActivity).findViewById(getID("drawer", "id"));
        if (view != null && view instanceof NavigationDrawer) {
            ((NavigationDrawer) view).setOpen(false);
        }
    }

    public int getID(String nama, String type) {
        return mHomeActivity.getResources().getIdentifier(nama, type, mHomeActivity.getPackageName());
    }

}