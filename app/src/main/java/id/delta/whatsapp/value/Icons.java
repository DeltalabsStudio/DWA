package id.delta.whatsapp.value;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Environment;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import id.delta.whatsapp.R;
import id.delta.whatsapp.ui.views.FloatingActionButton;
import id.delta.whatsapp.utils.AssetCopier;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;

public class Icons {

    public static String deltaPath = "WhatsApp/DELTA/";

    private static String iconAssets= "file:///android_asset/icons/";

    public static String iconPath = deltaPath+"icons/";

    public static String fileType = ".png";

    public static void copyIcons(Context context){
       // int count = -1;
        try{
            if (!new File(Environment.getExternalStorageDirectory(), deltaPath).exists()){
                new File(Environment.getExternalStorageDirectory(), deltaPath).mkdir();
            }
            if (!new File(Environment.getExternalStorageDirectory(), iconPath).exists()){
                new File(Environment.getExternalStorageDirectory(), iconPath).mkdir();
            }
            new AssetCopier(context).withFileScanning().copy("icons", new File(Environment.getExternalStorageDirectory(), iconPath));
            //  count = new AssetCopier(context).withFileScanning().copy("icons", new File(Environment.getExternalStorageDirectory(), iconPath));
        }catch (Exception e){
            e.printStackTrace();
            Tools.showToast("Please, allow storage permission!");
        }
       // Tools.showToast((count==-1 ? "There was an error copying" : "files copied " + count));
    }

    public static void customIcons(Context mContext, int i, ImageView mImage, final FloatingActionButton mFab){
        if(!new File(Environment.getExternalStorageDirectory(), iconPath+mIconName[i]+fileType).exists()){
            copyIcons(mContext);
            Picasso.with(mContext).load(mImageIcon[i]).into(mImage);
            Picasso.with(mContext).load(mImageIcon[i]).into(mFab);
            return;
        }
        if(Prefs.getBoolean(Keys.KEY_CUSTOM_ICON, false)){
            Picasso.with(mContext).load(new File(Environment.getExternalStorageDirectory(), iconPath+mIconName[i]+fileType)).into(mImage);
            Picasso.with(mContext).load(new File(Environment.getExternalStorageDirectory(), iconPath+mIconName[i]+fileType)).into(mFab);
        }else {
            Picasso.with(mContext).load(mImageIcon[i]).into(mImage);
            Picasso.with(mContext).load(mImageIcon[i]).into(mFab);
        }
    }

    public static int [] mItemResource = {
            Tools.intDrawable("delta_ic_more"),
            Tools.intDrawable("ic_action_archive"),
            Tools.intDrawable("ic_action_star"),
            Tools.intDrawable("notify_web_client_connected"),
            Tools.intDrawable("delta_ic_addgroup"),
            Tools.intDrawable("delta_ic_notif"),
            Tools.intDrawable("delta_ic_security"),
            Tools.intDrawable("delta_ic_settings"),
            Tools.intDrawable("ic_account_box"),
            Tools.intDrawable("ic_action_compose"),
            Tools.intDrawable("ic_settings_notifications"),
            Tools.intDrawable("ic_settings_data"),
            Tools.intDrawable("ic_settings_help")
    };


    public static String [] mItemName = {
            "ic_stock",
            "ic_archive",
            "ic_star",
            "ic_web",
            "ic_addgroup",
            "ic_broadcast",
            "ic_privacy",
            "ic_settings",
            "ic_account",
            "ic_msg",
            "ic_notif",
            "ic_storage",
            "ic_help"
    };

    private static int [] mImageIcon = {
            Tools.intDrawable("delta_ic_msg"),
            Tools.intDrawable("delta_ic_status"),
            Tools.intDrawable("ic_action_call"),
            Tools.intDrawable("delta_ic_menus"),
            Tools.intDrawable("delta_ic_create")
    };

    private static String [] mIconName = {
            "ic_chats",
            "ic_cameras",
            "ic_calls",
            "ic_menu",
            "ic_add"
    };


}


