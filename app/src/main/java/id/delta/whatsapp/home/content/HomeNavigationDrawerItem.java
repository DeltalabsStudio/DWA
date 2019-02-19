package id.delta.whatsapp.home.content;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

import com.whatsapp.ArchivedConversationsActivity;
import com.whatsapp.ListMembersSelector;
import com.whatsapp.NewGroup;
import com.whatsapp.StarredMessagesActivity;
import com.whatsapp.WebSessionsActivity;

import id.delta.whatsapp.activities.MainActivity;
import id.delta.whatsapp.activities.SettingsActivity;
import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.utils.Tools;

public class HomeNavigationDrawerItem extends RelativeLayout implements View.OnClickListener {

    private MainActivity mHomeActivity;
    private ImageView mIcon;
    private TextView mLabel;
    private Drawable mIconDrawable;
    private String mLabelString;

    public HomeNavigationDrawerItem(Context context) {
        super(context);
        init(context, null, 0);
    }

    public HomeNavigationDrawerItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public HomeNavigationDrawerItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HomeNavigationDrawerItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    @SuppressLint("ResourceType")
    private void init(Context context, AttributeSet attributeSet, int defStyleAttr) {
        mHomeActivity = (MainActivity) context;

        if (attributeSet != null) {
            int[] attrs = {Tools.intAttr("icon"), Tools.intAttr("label")};
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
        View view = LayoutInflater.from(mHomeActivity).inflate(Tools.intLayout("delta_home_drawer_item"), null);
        addView(view);

        setClickable(true);
        setFocusable(true);

        mIcon = findViewById(Tools.intId("icon"));
        mLabel = findViewById(Tools.intId("label"));

        mIcon.setImageDrawable(mIconDrawable);
        mLabel.setText(mLabelString);

        setGravity(Gravity.CENTER_VERTICAL);

        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = mHomeActivity.getResources().getResourceEntryName(view.getId());
        try{
            switch (name) {
                case "archived_chats":
                    Actions.startActivity(mHomeActivity, ArchivedConversationsActivity.class);
                    break;
                case "starred_messages":
                    Actions.startActivity(mHomeActivity, StarredMessagesActivity.class);
                    break;
                case "whatsapp_web":
                    Actions.startActivity(mHomeActivity, WebSessionsActivity.class);
                    break;
                case "nav_group":
                    NewGroup.a(mHomeActivity, 2, null);
                    break;
                case "nav_broadcast":
                    Actions.startActivity(mHomeActivity, ListMembersSelector.class);
                    break;
                case "nav_privacy":
                  //  Privacy.showDialogPrivacy(mHomeActivity);
                    break;
                case "nav_settings":
                    Actions.startSettings(mHomeActivity, SettingsActivity.DWA);
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        closeDrawer();

    }

    private void closeDrawer() {
        View view = ((Activity) mHomeActivity).findViewById(Tools.intId("drawer"));
        if (view != null && view instanceof HomeNavigationDrawer) {
            ((HomeNavigationDrawer) view).setOpen(false);
        }
    }
}
